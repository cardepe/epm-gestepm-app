package com.epm.gestepm.model.userholiday.service;

import com.epm.gestepm.model.holiday.service.mapper.HolidayMapper;
import com.epm.gestepm.model.userholiday.dao.UserHolidaysRepository;
import com.epm.gestepm.model.userold.dao.UserRepository;
import com.epm.gestepm.modelapi.holiday.dto.YearCalendarDTO;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import com.epm.gestepm.modelapi.userholiday.dto.UserHoliday;
import com.epm.gestepm.modelapi.userholiday.dto.UserHolidayDTO;
import com.epm.gestepm.modelapi.userholiday.service.UserHolidaysService;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.epm.gestepm.modelapi.common.utils.classes.Constants.STATUS_PENDING;

@Service
@Transactional
public class UserHolidaysServiceImpl implements UserHolidaysService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserHolidaysRepository userHolidaysRepository;
	
	@Override
	public UserHoliday save(UserHoliday userHoliday) {	
		return userHolidaysRepository.save(userHoliday);
	}
	
	@Override
	public UserHoliday getUserHolidayById(Long userHolidayId) {
		return userHolidaysRepository.findById(userHolidayId).orElse(null);
	}

	@Override
	public void createHolidays(List<Date> holidays, User user) throws Exception {

		final Integer currentYearHolidaysCount = user.getCurrentYearHolidaysCount();
		final Integer lastYearHolidaysCount = user.getLastYearHolidaysCount();

		final int requestedHolidaysCount = holidays.size();

		if (requestedHolidaysCount > currentYearHolidaysCount + lastYearHolidaysCount) {
			throw new Exception("No se pueden solicitar más vacaciones de las disponibles"); // FIXME: Custom Exception
		}

		final List<UserHoliday> userHolidays = HolidayMapper.mapCreateDatesToUserHolidays(holidays, user);

		userHolidays.forEach(this::save);

		if (lastYearHolidaysCount > 0) {
			user.setLastYearHolidaysCount(Math.max(lastYearHolidaysCount - requestedHolidaysCount, 0));
		}

		if (lastYearHolidaysCount == 0 || requestedHolidaysCount > lastYearHolidaysCount) {
			user.setCurrentYearHolidaysCount(Math.max(currentYearHolidaysCount - lastYearHolidaysCount - requestedHolidaysCount, 0));
		}

		this.userRepository.save(user);
	}

	@Override
	public void deleteHolidays(List<Date> holidays, User user) {

		final Integer currentYearHolidaysCount = user.getCurrentYearHolidaysCount();

		final int requestedHolidaysCount = holidays.size();

		holidays.forEach(h -> {

			final UserHoliday userHoliday = userHolidaysRepository.findByUserIdAndDateAndStatus(user.getId(), h, STATUS_PENDING);

			userHolidaysRepository.delete(userHoliday);
		});

		user.setCurrentYearHolidaysCount(currentYearHolidaysCount + requestedHolidaysCount);

		this.userRepository.save(user);
	}

	@Override
	public List<UserHoliday> getHolidaysByUser(Long userId, Integer year) {
		return userHolidaysRepository.findHolidaysByUserId(userId, year);
	}

	@Override
	public List<YearCalendarDTO> getHolidaysByUserList(List<Long> userIds, Integer year) {
		
		final List<YearCalendarDTO> usersCalendarDtos = new ArrayList<>();
		
		userIds.forEach(userId -> {

			final List<UserHoliday> userHolidays = this.userHolidaysRepository.findHolidaysByUserId(userId, year);

			userHolidays.forEach(uh -> {

				final List<YearCalendarDTO> yearCalendarDtos = usersCalendarDtos.stream().filter(uc -> uc.getDate().equals(uh.getDate())).collect(Collectors.toList());

				if (!yearCalendarDtos.isEmpty()) {

					final YearCalendarDTO yearCalendarDto = yearCalendarDtos.get(0);
					yearCalendarDto.addUsername(uh.getUser().getFullName());

				} else {

					final YearCalendarDTO yearCalendarDto = HolidayMapper.mapUserHolidayToYearCalendarDTO(uh);

					usersCalendarDtos.add(yearCalendarDto);
				}
			});
		});
		
		return usersCalendarDtos;
	}

	@Override
	public void deleteById(Long userHolidayId) {
		userHolidaysRepository.deleteById(userHolidayId);
	}

	@Override
	public List<UserHolidayDTO> getUserHolidaysDTOsByUserId(Long userId, PaginationCriteria pagination) {
		return userHolidaysRepository.findUserHolidaysDTOsByUserId(userId, pagination);
	}
	
	@Override
	public Long getUserHolidaysCountByUser(Long userId) {
		return userHolidaysRepository.findUserHolidaysCountByUserId(userId);
	}
}
