package com.epm.gestepm.model.timecontrol.service;

import com.epm.gestepm.lib.locale.LocaleProvider;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.masterdata.api.activitycenter.dto.ActivityCenterDto;
import com.epm.gestepm.masterdata.api.activitycenter.dto.finder.ActivityCenterByIdFinderDto;
import com.epm.gestepm.masterdata.api.activitycenter.service.ActivityCenterService;
import com.epm.gestepm.masterdata.api.holiday.dto.HolidayDto;
import com.epm.gestepm.masterdata.api.holiday.dto.filter.HolidayFilterDto;
import com.epm.gestepm.masterdata.api.holiday.service.HolidayService;
import com.epm.gestepm.model.common.excel.ExcelStyles;
import com.epm.gestepm.model.common.excel.ExcelUtils;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.timecontrol.dto.TimeControlExportDto;
import com.epm.gestepm.modelapi.timecontrol.dto.WoffuReasonDto;
import com.epm.gestepm.modelapi.timecontrol.exception.TimeControlExportException;
import com.epm.gestepm.modelapi.timecontrol.service.TimeControlWoffuExportService;
import com.epm.gestepm.modelapi.subrole.dto.SubRole;
import com.epm.gestepm.modelapi.subrole.service.SubRoleService;
import com.epm.gestepm.modelapi.timecontrol.dto.TimeControlDto;
import com.epm.gestepm.modelapi.timecontrol.dto.TimeControlTypeEnumDto;
import com.epm.gestepm.modelapi.timecontrol.dto.filter.TimeControlFilterDto;
import com.epm.gestepm.modelapi.timecontrol.service.TimeControlService;
import com.epm.gestepm.modelapi.user.dto.UserDto;
import com.epm.gestepm.modelapi.user.dto.finder.UserByIdFinderDto;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.epm.gestepm.modelapi.userholiday.dto.UserHoliday;
import com.epm.gestepm.modelapi.userholiday.service.UserHolidaysService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;

@Deprecated
@RequiredArgsConstructor
@Validated
@Service("signingWoffuExportService")
@EnableExecutionLog(layerMarker = SERVICE)
public class TimeControlWoffuExportServiceImpl implements TimeControlWoffuExportService {

    private static final String DATE_FORMAT = "dd/MM/yyyy";

    private static final String TIME_FORMAT = "HH:mm:ss";

    private static final Set<TimeControlTypeEnumDto> signingTypes = Set.of(
            TimeControlTypeEnumDto.CONSTRUCTION_SHARES,
            TimeControlTypeEnumDto.DISPLACEMENT_SHARES,
            TimeControlTypeEnumDto.PROGRAMMED_SHARES,
            TimeControlTypeEnumDto.INSPECTIONS,
            TimeControlTypeEnumDto.OFFICE_SIGNING,
            TimeControlTypeEnumDto.PERSONAL_SIGNINGS,
            TimeControlTypeEnumDto.TELEWORKING_SIGNING,
            TimeControlTypeEnumDto.WORK_SHARES
    );

    private final ActivityCenterService activityCenterService;

    private final HolidayService holidayService;

    private final LocaleProvider localeProvider;

    private final MessageSource messageSource;

    private final SubRoleService subRoleService;

    private final TimeControlService timeControlService;

    private final UserService userService;

    private final UserHolidaysService userHolidaysService;

    private ExcelStyles.Styles styles;

    @Override
    public String buildFileName(final TimeControlExportDto timeControlExportDto) {

        final UserDto user = this.userService.findOrNotFound(new UserByIdFinderDto(timeControlExportDto.getUserId()));

        return "Signing_Woffu_" + user.getFullName() + "_" + Utiles.getDateFormatted(timeControlExportDto.getStartDate()) + "_"
                + Utiles.getDateFormatted(timeControlExportDto.getEndDate()) + ".xlsx";
    }

    @Override
    public byte[] generate(final TimeControlExportDto signingExport) {

        try {

            final String language = this.localeProvider.getLocale().orElse("es");
            final Locale locale = new Locale(language);

            final UserDto userDto = this.userService.findOrNotFound(new UserByIdFinderDto(signingExport.getUserId()));

            final ByteArrayOutputStream file = new ByteArrayOutputStream();
            final XSSFWorkbook workbook = new XSSFWorkbook();

            this.styles = ExcelStyles.create(workbook);

            final Sheet sheet = workbook.createSheet(messageSource.getMessage("signing.woffu.excel.sheet.1", new Object[]{}, locale));

            this.writeHeaderInfo(sheet);
            this.writeContent(sheet, userDto, signingExport);

            this.setColumnsWidth(sheet);

            workbook.write(file);

            return file.toByteArray();
        } catch (IOException ex) {
            throw new TimeControlExportException(signingExport.getStartDate(), signingExport.getEndDate(), signingExport.getUserId(), ex.getMessage());
        }
    }

    private void writeHeaderInfo(final Sheet sheet) {
        final String language = this.localeProvider.getLocale().orElse("es");
        final Locale locale = new Locale(language);

        final Row headerRow = sheet.createRow(0);

        final String[] headerKeys = {
                "signing.woffu.excel.date",
                "signing.woffu.excel.name",
                "signing.woffu.excel.surnames",
                "signing.woffu.excel.role",
                "signing.woffu.excel.calendar",
                "signing.woffu.excel.reason",
                "signing.woffu.excel.reason.hours",
                "signing.woffu.excel.start.date",
                "signing.woffu.excel.start.date.adj",
                "signing.woffu.excel.end.date",
                "signing.woffu.excel.end.date.adj",
                "signing.woffu.excel.pauses",
                "signing.woffu.excel.theorical.schedule",
                "signing.woffu.excel.night.hours",
                "signing.woffu.excel.worked.hours",
                "signing.woffu.excel.difference",
                "signing.woffu.excel.signings"
        };

        for (int i = 0; i < headerKeys.length; i++) {
            final Cell cell = headerRow.createCell(i);
            cell.setCellStyle(styles.woffuHeaderStyle);
            cell.setCellValue(messageSource.getMessage(headerKeys[i], null, locale));
        }
    }

    private void writeContent(final Sheet sheet, final UserDto user, final TimeControlExportDto signingExport) {
        final List<TimeControlDto> signings = this.loadSignings(signingExport);

        if (signings.isEmpty()) {
            return;
        }

        int currentRow = 1;

        // TODO: cache
        final ActivityCenterDto activityCenter = this.activityCenterService.findOrNotFound(new ActivityCenterByIdFinderDto(user.getActivityCenterId()));
        final SubRole level = this.subRoleService.getSubRoleById(user.getLevelId().longValue());

        // TODO: when refactor, remember to change inside the void, change to find by date and add cache.
        final List<UserHoliday> userHolidays = this.userHolidaysService.getHolidaysByUser(user.getId().longValue(), signingExport.getStartDate().getYear());

        for (LocalDateTime date = signingExport.getStartDate(); !date.isAfter(signingExport.getEndDate()); date = date.plusDays(1)) {
            if (date.isAfter(LocalDateTime.now())) {
                break;
            }

            LocalDateTime finalDate = date;
            final List<TimeControlDto> currentDaySignings = signings.stream()
                    .filter(signing -> finalDate.toLocalDate().isEqual(signing.getStartDate().toLocalDate()))
                    .sorted(Comparator.comparing(TimeControlDto::getStartDate))
                    .collect(Collectors.toList());

            this.createSigningDayRow(sheet, currentRow++, user, date, currentDaySignings, activityCenter, level, userHolidays);
        }

        this.addSigningsSum(sheet, currentRow);
    }

    private void setColumnsWidth(final Sheet sheet) {
        for (int i = 0; i <= 16; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private List<TimeControlDto> loadSignings(final TimeControlExportDto signingExport) {

        final TimeControlFilterDto filterDto = new TimeControlFilterDto();
        filterDto.setStartDate(signingExport.getStartDate());
        filterDto.setEndDate(signingExport.getEndDate());
        filterDto.setUserId(signingExport.getUserId());
        filterDto.setTypes(signingTypes);

        return this.timeControlService.list(filterDto);
    }

    private void createSigningDayRow(final Sheet sheet, final Integer rowNumber, final UserDto user, final LocalDateTime dateTime, final List<TimeControlDto> signings, final ActivityCenterDto activityCenter, final SubRole level, final List<UserHoliday> userHolidays) {
        final Row row = sheet.createRow(rowNumber);

        this.populateSigningRow(row, user, dateTime, signings, activityCenter, level, userHolidays);
    }

    private void populateSigningRow(final Row row, final UserDto user, final LocalDateTime dateTime, final List<TimeControlDto> signings, final ActivityCenterDto activityCenter, final SubRole level, final List<UserHoliday> userHolidays) {
        final List<TimeControlDto> manualSignings = signings.stream()
                .filter(signing -> TimeControlTypeEnumDto.MANUAL_SIGNINGS.equals(signing.getType()))
                .collect(Collectors.toList());

        final List<TimeControlDto> imputableSignings = signings.stream()
                .filter(signing -> !TimeControlTypeEnumDto.MANUAL_SIGNINGS.equals(signing.getType()))
                .collect(Collectors.toList());

        final LocalDateTime startDate = imputableSignings.stream()
                .map(TimeControlDto::getStartDate)
                .filter(Objects::nonNull)
                .min(LocalDateTime::compareTo)
                .orElse(null);

        final LocalDateTime endDate = imputableSignings.stream()
                .map(TimeControlDto::getEndDate)
                .filter(Objects::nonNull)
                .max(LocalDateTime::compareTo)
                .orElse(null);

        final WoffuReasonDto reason = this.getReason(dateTime.toLocalDate(), user, userHolidays, manualSignings);
        final boolean isLaborableDay = WoffuReasonDto.LABORAL.equals(reason);
        final int totalBreaksInMinutes = this.getTotalBreaksInMinutes(imputableSignings);
        final int totalHoursInMinutes = this.getTotalHoursInMinutes(imputableSignings);
        final int difference = totalHoursInMinutes - (int) (user.getWorkingHours() * 60);
        final String signingsText = this.getSigningsText(imputableSignings);

        ExcelUtils.setCell(row, 0, Utiles.getDateFormatted(dateTime, DATE_FORMAT), null);
        ExcelUtils.setCell(row, 1, user.getName(), null);
        ExcelUtils.setCell(row, 2, user.getSurnames(), null);
        ExcelUtils.setCell(row, 3, level.getRol(), null);
        ExcelUtils.setCell(row, 4, activityCenter.getName(), null);
        ExcelUtils.setCell(row, 5, this.reasonToString(reason), null);
        ExcelUtils.setCell(row, 6, 0, null);
        ExcelUtils.setCell(row, 7, Utiles.getDateFormatted(startDate, TIME_FORMAT), null);
        ExcelUtils.setCell(row, 8, Utiles.getDateFormatted(startDate, TIME_FORMAT), null);
        ExcelUtils.setCell(row, 9, Utiles.getDateFormatted(endDate, TIME_FORMAT), null);
        ExcelUtils.setCell(row, 10, Utiles.getDateFormatted(endDate, TIME_FORMAT), null);
        ExcelUtils.setCell(row, 11, isLaborableDay ? totalBreaksInMinutes / 60.0 : 1, null);
        ExcelUtils.setCell(row, 12, isLaborableDay ? user.getWorkingHours() : 0, null);
        ExcelUtils.setCell(row, 13, 0, null);
        ExcelUtils.setCell(row, 14, isLaborableDay ? totalBreaksInMinutes / 60.0 : 0, null);
        ExcelUtils.setCell(row, 15, isLaborableDay ? difference / 60.0 : 0, null);
        ExcelUtils.setCell(row, 16, signingsText, null);
    }

    private void addSigningsSum(final Sheet sheet, final Integer rowNumber) {
        final Row row = sheet.createRow(rowNumber);

        final Cell cell = row.createCell(15);
        cell.setCellFormula("SUM(P1:P" + rowNumber + ")");
        cell.setCellStyle(styles.woffuDifferenceSumStyle);
    }

    private WoffuReasonDto getReason(final LocalDate dateTime, final UserDto user, final List<UserHoliday> userHolidays, final List<TimeControlDto> manualSignings) {

        final HolidayFilterDto holidayFilterDto = new HolidayFilterDto();
        holidayFilterDto.setDay(dateTime.getDayOfMonth());
        holidayFilterDto.setMonth(dateTime.getMonthValue());
        holidayFilterDto.setCountryIds(List.of(user.getCountryId()));
        holidayFilterDto.setActivityCenterIds(List.of(user.getActivityCenterId()));

        final List<HolidayDto> holidays = this.holidayService.list(holidayFilterDto);

        if (Utiles.isWeekend(dateTime)) {
            return WoffuReasonDto.WEEKEND;
        } else if (this.isHoliday(dateTime, holidays)) {
            return WoffuReasonDto.HOLIDAY;
        } else if (this.isUserHoliday(dateTime, userHolidays)) {
            return WoffuReasonDto.USER_HOLIDAY;
        } else if (this.isUserFullDayAbsence(manualSignings, user.getWorkingHours() * 60)) {
            return WoffuReasonDto.FREE_DAY;
        }

        return WoffuReasonDto.LABORAL;
    }

    private boolean isHoliday(final LocalDate dateTime, final List<HolidayDto> holidays) {
        return holidays.stream().anyMatch(h -> h.getDay() == dateTime.getDayOfMonth() && h.getMonth() == dateTime.getMonthValue());
    }

    private boolean isUserHoliday(final LocalDate date, final List<UserHoliday> userHolidays) {
        return userHolidays.stream().anyMatch(h -> Utiles.convertToLocalDateViaInstant(h.getDate()).isEqual(date));
    }

    private boolean isUserFullDayAbsence(final List<TimeControlDto> userManualSignings, final Double journey) {
        return userManualSignings.stream()
                .anyMatch(absence ->
                        journey > Duration.between(absence.getStartDate(), absence.getEndDate()).toMinutes());
    }

    private int getTotalBreaksInMinutes(final List<TimeControlDto> imputableSignings) {
        int totalBreaksInMinutes = 0;

        List<TimeControlDto> sortedSignings = imputableSignings.stream()
                .sorted(Comparator.comparing(TimeControlDto::getStartDate))
                .collect(Collectors.toList());

        for (int i = 0; i < sortedSignings.size() - 1; i++) {
            LocalDateTime currentEnd = sortedSignings.get(i).getEndDate();
            LocalDateTime nextStart = sortedSignings.get(i + 1).getStartDate();

            if (currentEnd != null && nextStart != null && nextStart.isAfter(currentEnd)) {
                totalBreaksInMinutes += (int) Duration.between(currentEnd, nextStart).toMinutes();
            }
        }

        return totalBreaksInMinutes;
    }

    private int getTotalHoursInMinutes(final List<TimeControlDto> imputableSignings) {
        return (int) imputableSignings.stream()
                .filter(timeControl -> timeControl.getEndDate() != null)
                .mapToLong(dto -> Duration.between(dto.getStartDate(), dto.getEndDate()).toMinutes())
                .sum();

    }

    private String getSigningsText(final List<TimeControlDto> imputableSignings) {
        return imputableSignings.stream()
                .map(signing -> Utiles.getDateFormatted(signing.getStartDate(), TIME_FORMAT) + "•" + Utiles.getDateFormatted(signing.getEndDate(), TIME_FORMAT))
                .collect(Collectors.joining(" "));
    }

    private String reasonToString(final WoffuReasonDto reason) {
        final String language = this.localeProvider.getLocale().orElse("es");
        final Locale locale = new Locale(language);

        if (reason == WoffuReasonDto.WEEKEND) {
            return messageSource.getMessage("time.control.weekend", null, locale);
        } else if (reason == WoffuReasonDto.HOLIDAY) {
            return messageSource.getMessage("time.control.holiday", null, locale);
        } else if (reason == WoffuReasonDto.USER_HOLIDAY) {
            return messageSource.getMessage("time.control.user.holidays", null, locale);
        } else if (reason == WoffuReasonDto.FREE_DAY) {
            return messageSource.getMessage("time.control.free.day", null, locale);
        }

        return messageSource.getMessage("time.control.laboral", null, locale);
    }
}
