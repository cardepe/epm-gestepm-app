package com.epm.gestepm.scheduled;

import com.epm.gestepm.lib.ftp.FtpClient;
import com.epm.gestepm.modelapi.deprecated.activitycenter.dto.ActivityCenter;
import com.epm.gestepm.modelapi.deprecated.activitycenter.service.ActivityCenterService;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.smtp.SMTPService;
import com.epm.gestepm.modelapi.holiday.service.HolidayService;
import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigning;
import com.epm.gestepm.modelapi.personalsigning.service.PersonalSigningService;
import com.epm.gestepm.modelapi.timecontrol.dto.SigningScheduledDTO;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.dto.UserDTO;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.mysql.jdbc.StringUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class SigningScheduled {

	private static final Log log = LogFactory.getLog(SigningScheduled.class);
	
	@Value("${signing.ftp.server}")
	private String signingFtpServer;
	
	@Value("${signing.ftp.port}")
	private int signingFtpPort;
	
	@Value("${signing.ftp.user}")
	private String signingFtpUser;
	
	@Value("${signing.ftp.password}")
	private String signingFtpPassword;

	@Value("${signing.lunch.interval.start}")
	private String signingLunchIntervalStart;

	@Value("${signing.lunch.interval.end}")
	private String signingLunchIntervalEnd;

	@Value("${signing.lunch.interval.time}")
	private Integer signingLunchIntervalTime;
	
	@Autowired
	private ActivityCenterService activityCenterServiceOld;
	
	@Autowired
	private HolidayService holidayService;
	
	@Autowired
	private PersonalSigningService personalSigningService;
	
	@Autowired
	private SMTPService smtpService;
	
	@Autowired
	private UserService userService;
	
	// @Scheduled(cron = "0 * * * * *") // TEST: every minute
	@Scheduled(cron = "0 30 21 * * *") // 21:30:00 Every Day
	public void dailyPersonalSigningProcess() {
	
		log.info("Iniciando el proceso de carga de fichajes personales.");
		
		List<Long> signingUserIdList = new ArrayList<>();
		
		try {

			final Integer lunchIntervalStartHour = Integer.parseInt(signingLunchIntervalStart.split(":")[0]);
			final Integer lunchIntervalStartMinutes = Integer.parseInt(signingLunchIntervalStart.split(":")[1]);

			final Date lunchIntervalStart = new Date();
			lunchIntervalStart.setHours(lunchIntervalStartHour);
			lunchIntervalStart.setMinutes(lunchIntervalStartMinutes);

			final Integer lunchIntervalEndHour = Integer.parseInt(signingLunchIntervalEnd.split(":")[0]);
			final Integer lunchIntervalEndMinutes = Integer.parseInt(signingLunchIntervalEnd.split(":")[1]);

			final Date lunchIntervalEnd = new Date();
			lunchIntervalEnd.setHours(lunchIntervalEndHour);
			lunchIntervalEnd.setMinutes(lunchIntervalEndMinutes);
			
			// Open connection to FTP
			FtpClient ftpClient = new FtpClient(signingFtpServer, signingFtpPort, signingFtpUser, signingFtpPassword);
			
			String dateInString = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			
			log.info("Se procede a cargar los fichajes del día " + dateInString);
			
			List<ActivityCenter> activityCenters = activityCenterServiceOld.findAll();
			
			for (ActivityCenter activityCenter : activityCenters) {
				
				String fileName = activityCenter.getName().toUpperCase() + "_" + dateInString + ".txt";
				
				log.info("Se va a cargar el fichero " + fileName);
				
				try {
					
					Map<Long, List<SigningScheduledDTO>> activityCenterSignings = new HashMap<>();
					
					ftpClient.open();
					String fileContent = ftpClient.readFileContent(fileName);
					
					log.info("Se ha cargado el fichero " + fileName);
					
					if (!StringUtils.isNullOrEmpty(fileContent)) {

						String[] fileLanes = fileContent.split("\n");
						
						log.info("El fichero " + fileName + " contiene un total de " + fileLanes + " fichajes");

						for (String lane : fileLanes) {

							if (StringUtils.isNullOrEmpty(lane)) {
								continue;
							}
							
							if (lane.contains(",")) {
								
								String[] parts = lane.split(",");
								
								Long userSigningId = Long.parseLong(parts[0]);
								Date signingDate = Utiles.transformSigningStringToDate(parts[1]);
								int signingValue = Integer.parseInt(parts[2]);
								
								SigningScheduledDTO signingScheduled = new SigningScheduledDTO(userSigningId, signingDate, signingValue);
								
								if (activityCenterSignings.containsKey(userSigningId)) {
									activityCenterSignings.get(userSigningId).add(signingScheduled);
									
								} else {
									
									List<SigningScheduledDTO> signingsList = new ArrayList<>();
									signingsList.add(signingScheduled);
									
									activityCenterSignings.put(userSigningId, signingsList);
								}
								
								if (!signingUserIdList.contains(userSigningId)) {
									signingUserIdList.add(userSigningId);
								}
							}
						}
						
						for (List<SigningScheduledDTO> signingLists : activityCenterSignings.values()) {

							Long userSigningId = signingLists.get(0).getUserSigningId();
							signingLists.sort((d1, d2) -> d1.getDate().compareTo(d2.getDate()));
							
							User user = userService.getUserBySigningId(userSigningId);
							
							if (user == null) {
								log.error("El usuario con Signing Id " + userSigningId + " no se ha encontrado en la base de datos.");
								continue;
							}
							
							PersonalSigning personalSigning = null;
							int i = 0;
							
							for (SigningScheduledDTO signing : signingLists) {
								
								try {
									
									i++;
									
									if (signing.getValue() == 0) {

										final boolean hasLastSigningEnded = personalSigning != null && personalSigning.getEndDate() != null;
										Date startDate = signing.getDate();

										if (hasLastSigningEnded) {
											
											personalSigning = personalSigningService.save(personalSigning);
											
											log.info("Registrado el fichaje " + personalSigning.getId() + " por parte del usuario " + user.getId());

											final Date endDate = Date.from(personalSigning.getEndDate().toInstant(ZoneOffset.UTC));
											final boolean isLunchInterval = endDate.after(lunchIntervalStart) && endDate.before(lunchIntervalEnd);

											if (isLunchInterval) {

												long duration  = endDate.getTime() - startDate.getTime();

												long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);

												if (diffInMinutes < signingLunchIntervalTime) {
													startDate = DateUtils.addMinutes(endDate, signingLunchIntervalTime);
												}
											}

											personalSigning = null;
										}
										
										if (personalSigning == null) {
											personalSigning = new PersonalSigning();
											personalSigning.setUser(user);
											personalSigning.setStartDate(LocalDateTime.from(startDate.toInstant().atOffset(ZoneOffset.UTC)));
										}
										
									} else if (signing.getValue() == 1) {

										if (personalSigning != null) {
											personalSigning.setEndDate(LocalDateTime.from(signing.getDate().toInstant().atOffset(ZoneOffset.UTC)));
										} else {
											log.info("El usuario " + signing.getUserSigningId() + " ha imputado una salida sin entrada previa a las " + signing.getDate());
										}
									}
									
									if (i == signingLists.size() && personalSigning != null) {
										
										if (personalSigning.getEndDate() == null) {
											personalSigning.setEndDate(LocalDateTime.now());
										}
										
										personalSigning = personalSigningService.save(personalSigning);
										
										log.info("Registrado el fichaje " + personalSigning.getId() + " por parte del usuario " + user.getId());
									}
									
								} catch (Exception e) {
									log.error("Error al procesar la línea: " + signing.getUserSigningId() + "," + signing.getDate() + "," + signing.getValue(), e);
								}
							}
						}
					}
					
				} catch (NullPointerException e) {
					log.error("No se ha encontrado el fichero " + fileName + " en el servidor FTP");
				} catch (Exception e) {
					log.error("Error al leer el fichero " + fileName, e);
				} finally {
					
					if (ftpClient.getFtp().isConnected()) {
						ftpClient.close();
					}
				}
			}
			
			ftpClient.open();
			ftpClient.cleanFolder("/");
			ftpClient.close();
			
			log.info("Carpeta limpiada con éxito");
			
			// Send Mail to Invalid User Signings
			// sendMailsToInvalidSignings(signingUserIdList);

		} catch (Exception e) {
			log.error(e);
		}
		
		log.info("Finalizada la carga de fichajes personales en la base de datos.");
	}
	
	public void sendMailsToInvalidSignings(List<Long> signingUserIdList) {
		
		try {
			
			if (!signingUserIdList.isEmpty()) {
				
				List<UserDTO> userDTOs = userService.getAllUserDTOs();
				
				if (userDTOs != null && !userDTOs.isEmpty()) {
					
					// Filter Active Users
					userDTOs = userDTOs.stream().filter(u -> u.getState() == 0 && u.getSigningId() != null).collect(Collectors.toList());
				
					// Map DTO to Id List
					List<Long> userSigningIds = userDTOs.stream().map(UserDTO::getSigningId).collect(Collectors.toList());
					
					// Remove Signing Users
					List<Long> invalidSigningUserId = ListUtils.removeAll(userSigningIds, signingUserIdList);
					
					// Get today Date
					Date date = Utiles.atStartOfDay(new Date());
					
					for (Long userSigningId : invalidSigningUserId) {
						
						User userSigning = userService.getUserBySigningId(userSigningId);
						
						if (userSigning != null) {

							Boolean workingDay = holidayService.isWorkingDay(userSigning.getId(), userSigning.getActivityCenter().getCountry().getId(), userSigning.getActivityCenter().getId(), date);
							
							if (workingDay) {
								
								Locale userLocale = null;
								
								if (userSigning.getActivityCenter() != null && userSigning.getActivityCenter().getCountry() != null) {
									userLocale = new Locale(userSigning.getActivityCenter().getCountry().getTag());
								}
								
								if (userLocale == null) {
									userLocale = new Locale("es");
								}
								
								// Send Mail
								smtpService.sendSigningInvalidMail(userSigning.getEmail(), userSigning, userLocale);
							}							
						}
					}
				}					
			}
							
		} catch (Exception e) {
			log.error(e);
		}
	}
}
