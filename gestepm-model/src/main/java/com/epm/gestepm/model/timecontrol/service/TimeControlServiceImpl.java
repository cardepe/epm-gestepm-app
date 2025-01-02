package com.epm.gestepm.model.timecontrol.service;

import com.epm.gestepm.model.displacementshare.dao.DisplacementShareRepository;
import com.epm.gestepm.model.holiday.dao.HolidayRepository;
import com.epm.gestepm.model.personalsigning.dao.PersonalSigningRepository;
import com.epm.gestepm.model.user.dao.UserRepository;
import com.epm.gestepm.model.userholiday.dao.UserHolidaysRepository;
import com.epm.gestepm.model.usermanualsigning.dao.UserManualSigningRepository;
import com.epm.gestepm.model.usersigning.dao.UserSigningRepository;
import com.epm.gestepm.modelapi.common.helpers.DatesModel;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShare;
import com.epm.gestepm.modelapi.holiday.dto.Holiday;
import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigning;
import com.epm.gestepm.modelapi.timecontrol.dto.TimeControlDetailTableDTO;
import com.epm.gestepm.modelapi.timecontrol.dto.TimeControlTableDTO;
import com.epm.gestepm.modelapi.timecontrol.service.TimeControlService;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.userholiday.dto.UserHoliday;
import com.epm.gestepm.modelapi.usermanualsigning.dto.UserManualSigning;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class TimeControlServiceImpl implements TimeControlService {
	
	@Autowired
	private DisplacementShareRepository displacementShareRepository;
	
	@Autowired
	private HolidayRepository holidayRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private PersonalSigningRepository personalSigingRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserHolidaysRepository userHolidaysRepository;
	
	@Autowired
	private UserSigningRepository userSigningRepository;

	@Autowired
	private UserManualSigningRepository userManualSigningRepository;
	
	@Override
	public List<TimeControlTableDTO> getTimeControlTableDTOByDateAndUser(int month, int year, Long userId, Long activityCenter, Locale locale) {
		
		int minDay = 1;
		int maxDay = Utiles.getDaysOfMonth(month, year);

		final LocalDateTime startDate = LocalDateTime.of(year, month, minDay, 0, 0, 0);
		final LocalDateTime endDate = LocalDateTime.of(year, month, maxDay, 23, 59, 59);

		User user = userRepository.findById(userId).get();
		String username = user.getName() + " " + user.getSurnames();
		long journeyMillis = (long) (user.getWorkingHours() * (60 * 60 * 1000));
		
		List<TimeControlTableDTO> timeControlsMap = new ArrayList<>();

		List<DisplacementShare> displacementShares = displacementShareRepository.findWeekSigningsByUserId(startDate, endDate, userId, 1);
		List<PersonalSigning> personalSignings = personalSigingRepository.findWeekSigningsByUserId(startDate, endDate, userId);
		List<UserSigning> userSignings = userSigningRepository.findWeekSigningsByUserId(startDate, endDate, userId);
		List<UserManualSigning> userManualSignings = userManualSigningRepository.findWeekManualSigningsByUserId(startDate, endDate, userId);

		if (displacementShares.isEmpty() && personalSignings.isEmpty() && userSignings.isEmpty() && userManualSignings.isEmpty()) {
			return new ArrayList<>();
		}
		
		List<Holiday> holidays = holidayRepository.findHolidaysByActivityCenter(activityCenter);
		List<UserHoliday> userHolidays = userHolidaysRepository.findHolidaysByUserId(userId, year);
		
		for (int i = 1; i <= maxDay; i++) {
			
			Date timeControlDate = Utiles.transformSimpleStringToDate((i < 10 ? "0" : "") + i + "-" + (month < 10 ? "0" : "") + month + "-" + year);
			
			if (timeControlDate.after(new Date())) {
				break;
			}
			
			TimeControlTableDTO timeControl = new TimeControlTableDTO();
			timeControl.setCustomId(userId, timeControlDate);
			timeControl.setDate(LocalDateTime.from(timeControlDate.toInstant().atOffset(ZoneOffset.UTC)));
			timeControl.setUsername(username);

			UserManualSigning userManualFullDay = isUserManualFullDay(timeControlDate, userManualSignings);

			if (Utiles.isWeekend(timeControlDate)) {
				timeControl.setReason("1" + messageSource.getMessage("time.control.weekend", null, locale));
			} else if (isHoliday(i, month, holidays)) {
				timeControl.setReason("1" + messageSource.getMessage("time.control.holiday", null, locale));
			} else if (isUserHoliday(timeControlDate, userHolidays)) {
				timeControl.setReason("1" + messageSource.getMessage("time.control.user.holidays", null, locale));
			} else if (userManualFullDay != null) {
				timeControl.setReason("1" + userManualFullDay.getManualSigningType().getName());
			} else {
				timeControl.setReason("2" + messageSource.getMessage("time.control.laboral", null, locale));
			}
			
			timeControl.setJourney(user.getWorkingHours());
			
			List<DisplacementShare> todayDisplacementShares = displacementShares.stream()
					.filter(s -> DateUtils.isSameDay(Date.from(s.getDisplacementDate().toInstant(ZoneOffset.UTC)), timeControlDate)).collect(Collectors.toList());
			
			List<PersonalSigning> todayPersonalSignings = personalSignings.stream()
					.filter(s -> DateUtils.isSameDay(Date.from(s.getStartDate().toInstant(ZoneOffset.UTC)), timeControlDate)).collect(Collectors.toList());
			
			List<UserSigning> todayUserSignings = userSignings.stream()
					.filter(s -> DateUtils.isSameDay(Date.from(s.getStartDate().toInstant(ZoneOffset.UTC)), timeControlDate)).collect(Collectors.toList());

			List<UserManualSigning> todayUserManualSignings = userManualSignings.stream()
					.filter(s -> DateUtils.isSameDay(Date.from(s.getStartDate().toInstant(ZoneOffset.UTC)), timeControlDate)).collect(Collectors.toList());
			
			Date checkInDate = null;
			Date checkOutDate = null;
			
			long breaks = 0;
			long totalHours = 0;
			
			List<DatesModel> todayDates = new ArrayList<>();
			
			for (DisplacementShare ds : todayDisplacementShares) {
				
				Calendar date = Calendar.getInstance();
				date.setTime(Date.from(ds.getDisplacementDate().toInstant(ZoneOffset.UTC)));
				long t = date.getTimeInMillis();
				Date afterAddingMins = new Date(t + (ds.getManualHours() * 60000));
				
				DatesModel dm = new DatesModel();
				dm.setStartDate(Date.from(ds.getDisplacementDate().toInstant(ZoneOffset.UTC)));
				dm.setEndDate(afterAddingMins);

				todayDates.add(dm);
			}
			
			for (PersonalSigning ps : todayPersonalSignings) {
				DatesModel dm = new DatesModel();
				dm.setStartDate(Date.from(ps.getStartDate().toInstant(ZoneOffset.UTC)));
				dm.setEndDate(Date.from(ps.getEndDate().toInstant(ZoneOffset.UTC)));

				todayDates.add(dm);
			}
			
			for (UserSigning ps : todayUserSignings) {
				DatesModel dm = new DatesModel();
				dm.setStartDate(Date.from(ps.getStartDate().toInstant(ZoneOffset.UTC)));
				dm.setEndDate(Date.from(ps.getEndDate().toInstant(ZoneOffset.UTC)));

				todayDates.add(dm);
			}

			todayDates.sort(Comparator.comparing(DatesModel::getStartDate));

			final List<DatesModel> datesModelList = new ArrayList<>();

			for (final DatesModel dm : todayDates) {

				if (datesModelList.isEmpty()) {

					datesModelList.add(dm);

				} else {

					final Date dmStartDate = dm.getStartDate();
					final Date dmEndDate = dm.getEndDate();

					final DatesModel startInterval = datesModelList.stream().filter(f -> dmStartDate.after(f.getStartDate()) && dmStartDate.before(f.getEndDate())).findFirst().orElse(null);

					if (startInterval != null) {

						if (dmEndDate.after(startInterval.getEndDate())) {
							startInterval.setEndDate(dmEndDate);
						}
					}

					final DatesModel endInterval = datesModelList.stream().filter(f -> dmEndDate.after(f.getStartDate()) && dmEndDate.before(f.getEndDate())).findFirst().orElse(null);

					if (endInterval != null) {

						if (dmStartDate.before(endInterval.getStartDate())) {
							endInterval.setStartDate(dmStartDate);
						}
					}

					if (startInterval == null && endInterval == null) {
						datesModelList.add(dm);
					}
				}
			}

			datesModelList.sort(Comparator.comparing(DatesModel::getStartDate));

			Date lastEndDate = null;
			for (DatesModel dm : datesModelList) {

				if (checkInDate == null || (dm.getStartDate() != null && dm.getStartDate().before(checkInDate))) {
					checkInDate = dm.getStartDate();
				}

				if (checkOutDate == null || (dm.getEndDate() != null && dm.getEndDate().after(checkOutDate))) {
					checkOutDate = dm.getEndDate();
				}

				totalHours += dm.getEndDate().getTime() - dm.getStartDate().getTime();

				if (lastEndDate != null) {
					breaks += (dm.getStartDate().getTime() - lastEndDate.getTime());
				}

				lastEndDate = dm.getEndDate();
			}

			timeControl.setStartHour(checkInDate != null
					? LocalDateTime.from(checkInDate.toInstant().atOffset(ZoneOffset.UTC))
					: null);
			timeControl.setEndHour(checkOutDate != null
					? LocalDateTime.from(checkOutDate.toInstant().atOffset(ZoneOffset.UTC))
					: null);
			
			if (timeControl.getReason().startsWith("2")) {
				timeControl.setBreaks(Utiles.getStringDateWithMillis(breaks));
				timeControl.setDifference(Utiles.getStringDateWithMillis(totalHours - journeyMillis));
			} else {
				timeControl.setBreaks("");
				timeControl.setDifference("");
			}
			
			timeControl.setTotalHours(Utiles.getStringDateWithMillis(totalHours));			
			
			timeControlsMap.add(timeControl);

			if (userManualFullDay == null && !todayUserManualSignings.isEmpty()) {

				for (UserManualSigning ums : todayUserManualSignings) {

					TimeControlTableDTO manualTimeControl = new TimeControlTableDTO();
					manualTimeControl.setCustomId(userId, timeControlDate);
					manualTimeControl.setDate(LocalDateTime.from(timeControlDate.toInstant().atOffset(ZoneOffset.UTC)));
					manualTimeControl.setUsername(username);
					manualTimeControl.setReason("1" + ums.getManualSigningType().getName());
					manualTimeControl.setStartHour(ums.getStartDate());
					manualTimeControl.setEndHour(ums.getEndDate());

					timeControlsMap.add(manualTimeControl);
				}
			}
		}
			
		return timeControlsMap;
	}
	
	@Override
	public TimeControlTableDTO getTimeControlDetail(LocalDateTime startDate, Long userId) {
		
		final User user = userRepository.findById(userId).get();
		final LocalDateTime endDate = startDate.withHour(23).withMinute(59).withSecond(59);

		final List<DisplacementShare> displacementShares = displacementShareRepository.findWeekSigningsByUserId(startDate, endDate, userId, 1);
		final List<PersonalSigning> personalSignings = personalSigingRepository.findWeekSigningsByUserId(startDate, endDate, userId);
		final List<UserSigning> userSignings = userSigningRepository.findWeekSigningsByUserId(startDate, endDate, userId);

		final TimeControlTableDTO timeControl = new TimeControlTableDTO();
		timeControl.setDate(startDate);
		timeControl.setUsername(user.getName() + " " + user.getSurnames());
		timeControl.setJourney(user.getWorkingHours());

		final long journeyMillis = (long) (user.getWorkingHours() * (60 * 60 * 1000));
		
		Date checkInDate = null;
		Date checkOutDate = null;
		
		long breaks = 0;
		long totalHours = 0;
		
		List<DatesModel> todayDates = new ArrayList<>();
		
		for (DisplacementShare ds : displacementShares) {

			final Calendar datee = Calendar.getInstance();
			datee.setTime(Date.from(ds.getDisplacementDate().toInstant(ZoneOffset.UTC)));

			final long t = datee.getTimeInMillis();
			final Date afterAddingMins = new Date(t + (ds.getManualHours() * 60000));

			final DatesModel dm = new DatesModel();
			dm.setStartDate(Date.from(ds.getDisplacementDate().toInstant(ZoneOffset.UTC)));
			dm.setEndDate(afterAddingMins);

			todayDates.add(dm);
		}
		
		for (PersonalSigning ps : personalSignings) {

			final DatesModel dm = new DatesModel();
			dm.setStartDate(Date.from(ps.getStartDate().toInstant(ZoneOffset.UTC)));
			dm.setEndDate(Date.from(ps.getEndDate().toInstant(ZoneOffset.UTC)));

			todayDates.add(dm);
		}
		
		for (UserSigning ps : userSignings) {

			final DatesModel dm = new DatesModel();
			dm.setStartDate(Date.from(ps.getStartDate().toInstant(ZoneOffset.UTC)));
			dm.setEndDate(Date.from(ps.getEndDate().toInstant(ZoneOffset.UTC)));

			todayDates.add(dm);
		}
		
		todayDates.sort(Comparator.comparing(DatesModel::getStartDate));

		final List<DatesModel> datesModelList = new ArrayList<>();

		for (final DatesModel dm : todayDates) {

			if (datesModelList.isEmpty()) {

				datesModelList.add(dm);

			} else {

				final Date dmStartDate = dm.getStartDate();
				final Date dmEndDate = dm.getEndDate();

				final DatesModel startInterval = datesModelList.stream().filter(f -> dmStartDate.after(f.getStartDate()) && dmStartDate.before(f.getEndDate())).findFirst().orElse(null);

				if (startInterval != null) {

					if (dmEndDate.after(startInterval.getEndDate())) {
						startInterval.setEndDate(dmEndDate);
					}
				}

				final DatesModel endInterval = datesModelList.stream().filter(f -> dmEndDate.after(f.getStartDate()) && dmEndDate.before(f.getEndDate())).findFirst().orElse(null);

				if (endInterval != null) {

					if (dmStartDate.before(endInterval.getStartDate())) {
						endInterval.setStartDate(dmStartDate);
					}
				}

				if (startInterval == null && endInterval == null) {
					datesModelList.add(dm);
				}
			}
		}

		datesModelList.sort(Comparator.comparing(DatesModel::getStartDate));

		Date lastEndDate = null;
		for (DatesModel dm : datesModelList) {
			
			if (checkInDate == null || (dm.getStartDate() != null && dm.getStartDate().before(checkInDate))) {
				checkInDate = dm.getStartDate();
			}
			
			if (checkOutDate == null || (dm.getEndDate() != null && dm.getEndDate().after(checkOutDate))) {
				checkOutDate = dm.getEndDate();
			}
			
			totalHours += dm.getEndDate().getTime() - dm.getStartDate().getTime();

			if (lastEndDate != null) {
				breaks += (dm.getStartDate().getTime() - lastEndDate.getTime());
			}

			lastEndDate = dm.getEndDate();
		}

		timeControl.setStartHour(checkInDate != null ? LocalDateTime.from(checkInDate.toInstant().atOffset(ZoneOffset.UTC)) : null);
		timeControl.setEndHour(checkOutDate != null ? LocalDateTime.from(checkOutDate.toInstant().atOffset(ZoneOffset.UTC)) : null);
		timeControl.setBreaks(Utiles.getStringDateWithMillis(breaks));
		timeControl.setDifference(Utiles.getStringDateWithMillis(totalHours - journeyMillis));
		timeControl.setTotalHours(Utiles.getStringDateWithMillis(totalHours));
		
		return timeControl;
	}
	
	@Override
	public List<TimeControlDetailTableDTO> getTimeControlDetailTableDTOByDateAndUser(LocalDateTime startDate, Long userId, Locale locale) {

		final LocalDateTime endDate = startDate.withHour(23).withMinute(59).withSecond(59);
		
		List<TimeControlDetailTableDTO> registers = new ArrayList<>();

		List<DisplacementShare> displacementShares = displacementShareRepository.findWeekSigningsByUserId(startDate, endDate, userId, 1);
		List<PersonalSigning> personalSignings = personalSigingRepository.findWeekSigningsByUserId(startDate, endDate, userId);
		List<UserSigning> userSignings = userSigningRepository.findWeekSigningsByUserId(startDate, endDate, userId);
		List<UserManualSigning> userManualSignings = userManualSigningRepository.findWeekManualSigningsByUserId(startDate, endDate, userId);
		
		for (DisplacementShare ds : displacementShares) {
			
			Calendar datee = Calendar.getInstance();
			datee.setTime(Date.from(ds.getDisplacementDate().toInstant(ZoneOffset.UTC)));
			long t = datee.getTimeInMillis();
			Date afterAddingMins = new Date(t + (ds.getManualHours() * 60000));
			
			TimeControlDetailTableDTO tcDTO = new TimeControlDetailTableDTO();
			tcDTO.setStartHour(Date.from(ds.getDisplacementDate().toInstant(ZoneOffset.UTC)));
			tcDTO.setEndHour(afterAddingMins);
			tcDTO.setType(messageSource.getMessage("shares.displacement.title", null, locale));
			
			registers.add(tcDTO);
		}
		
		for (PersonalSigning ps : personalSignings) {
			TimeControlDetailTableDTO tcDTO = new TimeControlDetailTableDTO();
			tcDTO.setStartHour(Date.from(ps.getStartDate().toInstant(ZoneOffset.UTC)));
			tcDTO.setEndHour(Date.from(ps.getEndDate().toInstant(ZoneOffset.UTC)));
			tcDTO.setType(messageSource.getMessage("signing.personal.title", null, locale));
			
			registers.add(tcDTO);
		}
		
		for (UserSigning ps : userSignings) {
			TimeControlDetailTableDTO tcDTO = new TimeControlDetailTableDTO();
			tcDTO.setStartHour(Date.from(ps.getStartDate().toInstant(ZoneOffset.UTC)));
			tcDTO.setEndHour(Date.from(ps.getEndDate().toInstant(ZoneOffset.UTC)));
			tcDTO.setType(messageSource.getMessage("signing.calendar.title", null, locale));
			
			registers.add(tcDTO);
		}

		for (UserManualSigning ums : userManualSignings) {

			final TimeControlDetailTableDTO tcDTO = new TimeControlDetailTableDTO();
			tcDTO.setStartHour(Date.from(ums.getStartDate().toInstant(ZoneOffset.UTC)));
			tcDTO.setEndHour(Date.from(ums.getEndDate().toInstant(ZoneOffset.UTC)));
			tcDTO.setType(ums.getManualSigningType().getName());

			registers.add(tcDTO);
		}

		registers.sort(Comparator.comparing(TimeControlDetailTableDTO::getStartHour));
		
		return registers;
	}
	
	private boolean isHoliday(int day, int month, List<Holiday> holidays) {
		return holidays.stream().anyMatch(h -> h.getDay() == day && h.getMonth() == month);
	}
	
	private boolean isUserHoliday(Date date, List<UserHoliday> userHolidays) {
		return userHolidays.stream().anyMatch(h -> h.getDate().compareTo(date) == 0);
	}

	private UserManualSigning isUserManualFullDay(Date date, List<UserManualSigning> userManualSignings) {

		for (UserManualSigning ums : userManualSignings) {

			final Date startDate = Date.from(ums.getStartDate().toInstant(ZoneOffset.UTC));

			final Calendar cal1 = Calendar.getInstance();
			final Calendar cal2 = Calendar.getInstance();

			cal1.setTime(date);
			cal2.setTime(startDate);

			boolean sameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);

			if (sameDay) {
				final long workingHours = ums.getUser().getWorkingHours().longValue();
				final long journeyDefaultTime = workingHours * 60 * 60 * 1000;
				final long journeyTime = Duration.between(ums.getStartDate(), ums.getEndDate()).toMillis();

				return journeyTime >= journeyDefaultTime ? ums : null;
			}
		}

		return null;
	}
}
