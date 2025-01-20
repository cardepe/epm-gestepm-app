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
import com.epm.gestepm.modelapi.user.service.UserService;
import com.mysql.jdbc.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

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
	private PersonalSigningService personalSigningService;
	
	@Autowired
	private UserService userService;
	
	// @Scheduled(cron = "0 * * * * *") // TEST: every minute
	@Scheduled(cron = "0 30 21 * * *") // 21:30:00 Every Day
	public void dailyPersonalSigningProcess() {
	
		log.info("Iniciando el proceso de carga de fichajes personales.");
		
		List<Long> signingUserIdList = new ArrayList<>();
		
		try {

			final int lunchIntervalStartHour = Integer.parseInt(signingLunchIntervalStart.split(":")[0]);
			final int lunchIntervalStartMinutes = Integer.parseInt(signingLunchIntervalStart.split(":")[1]);

			final LocalDateTime lunchIntervalStart = LocalDateTime.now()
					.withHour(lunchIntervalStartHour)
					.withMinute(lunchIntervalStartMinutes);

			final int lunchIntervalEndHour = Integer.parseInt(signingLunchIntervalEnd.split(":")[0]);
			final int lunchIntervalEndMinutes = Integer.parseInt(signingLunchIntervalEnd.split(":")[1]);

			final LocalDateTime lunchIntervalEnd = LocalDateTime.now()
					.withHour(lunchIntervalEndHour)
					.withMinute(lunchIntervalEndMinutes);
			
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
								final LocalDateTime signingDate = Utiles.transform(parts[1], "yyyy-MM-dd HH:mm:ss");
								int signingValue = Integer.parseInt(parts[2]);
								
								final SigningScheduledDTO signingScheduled = new SigningScheduledDTO(userSigningId, signingDate, signingValue);
								
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
										LocalDateTime startDate = signing.getDate();

										if (hasLastSigningEnded) {
											
											personalSigning = personalSigningService.save(personalSigning);
											
											log.info("Registrado el fichaje " + personalSigning.getId() + " por parte del usuario " + user.getId());

											final LocalDateTime endDate = personalSigning.getEndDate();
											final boolean isLunchInterval = endDate.isAfter(lunchIntervalStart) && endDate.isBefore(lunchIntervalEnd);

											if (isLunchInterval) {

												long diffInMinutes = Duration.between(startDate, endDate).toMinutes();

												if (diffInMinutes < signingLunchIntervalTime) {
													startDate = endDate.plusMinutes(signingLunchIntervalTime);
												}
											}

											personalSigning = null;
										}
										
										if (personalSigning == null) {
											personalSigning = new PersonalSigning();
											personalSigning.setUser(user);
											personalSigning.setStartDate(startDate);
										}
										
									} else if (signing.getValue() == 1) {

										if (personalSigning != null) {
											personalSigning.setEndDate(signing.getDate());
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

		} catch (Exception e) {
			log.error(e);
		}
		
		log.info("Finalizada la carga de fichajes personales en la base de datos.");
	}
}
