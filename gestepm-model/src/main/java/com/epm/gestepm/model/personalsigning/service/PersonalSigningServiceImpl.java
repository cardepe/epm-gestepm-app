package com.epm.gestepm.model.personalsigning.service;

import com.epm.gestepm.model.constructionshare.dao.ConstructionShareRepository;
import com.epm.gestepm.model.displacementshare.dao.DisplacementShareRepository;
import com.epm.gestepm.model.interventionprshare.dao.InterventionPrShareRepository;
import com.epm.gestepm.model.interventionshare.dao.InterventionShareRepository;
import com.epm.gestepm.model.personalsigning.dao.PersonalSigningRepository;
import com.epm.gestepm.model.timecontrol.service.TimeControlServiceImpl;
import com.epm.gestepm.model.user.service.mapper.SigningMapper;
import com.epm.gestepm.model.usersigning.dao.UserSigningRepository;
import com.epm.gestepm.model.workshare.dao.WorkShareRepository;
import com.epm.gestepm.modelapi.common.helpers.DatesModel;
import com.epm.gestepm.modelapi.common.utils.ExcelUtils;
import com.epm.gestepm.modelapi.common.utils.PixelUtils;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShare;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShare;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.InterventionShare;
import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigning;
import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigningResumeDTO;
import com.epm.gestepm.modelapi.personalsigning.service.PersonalSigningService;
import com.epm.gestepm.modelapi.timecontrol.dto.TimeControlDto;
import com.epm.gestepm.modelapi.timecontrol.dto.TimeControlTypeEnumDto;
import com.epm.gestepm.modelapi.timecontrol.dto.filter.TimeControlFilterDto;
import com.epm.gestepm.modelapi.timecontrol.service.TimeControlService;
import com.epm.gestepm.modelapi.timecontrolold.dto.TimeControlTableDTO;
import com.epm.gestepm.modelapi.timecontrolold.service.TimeControlOldService;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;
import com.epm.gestepm.modelapi.workshare.dto.WorkShare;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormatSymbols;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonalSigningServiceImpl implements PersonalSigningService {

	private static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

	private static final String DATE_FORMAT = "dd/MM/yyyy";

	private static final String TIME_FORMAT = "HH:mm:ss";

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ConstructionShareRepository constructionShareRepository;
	
	@Autowired
	private DisplacementShareRepository displacementShareRepository;
	
	@Autowired
	private InterventionPrShareRepository interventionPrShareRepository;
	
	@Autowired
	private InterventionShareRepository interventionShareRepository;
	
	@Autowired
	private PersonalSigningRepository personalSigingRepository;

	@Autowired
	private TimeControlOldService timeControlOldService;

	@Autowired
	private WorkShareRepository workShareRepository;

    @Autowired
    private TimeControlService timeControlService;

	@Override
	public PersonalSigning save(PersonalSigning personalSigning) {
		return personalSigingRepository.save(personalSigning);
	}
	
	@Override
	public PersonalSigning getById(Long id) {
		return personalSigingRepository.findById(id).get();
	}
	
	@Override
	public List<PersonalSigning> getWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId) {
		return personalSigingRepository.findWeekSigningsByUserId(startDate, endDate, userId);
	}
	
	@Override
	public XSSFWorkbook generateSigningSheetExcel(final Integer month, final Integer year, final User user, final Locale locale) {
		
		final XSSFWorkbook workbook = new XSSFWorkbook();
					
		final Sheet sheet = workbook.createSheet(messageSource.getMessage("signing.excel.sheet.1", new Object[] { year }, locale));

		generateHeaderInfo(workbook, sheet, user, month, year, locale);

		generateMonthsSigningsInfo(workbook, sheet, user, month, year, locale);

		setColumnsWidth(sheet);

		return workbook;
	}

	@Override
	public XSSFWorkbook generateSigningSheetWoffuExcel(final Integer month, final Integer year, final User user, final Locale locale) {

		final XSSFWorkbook workbook = new XSSFWorkbook();

		final Sheet sheet = workbook.createSheet(messageSource.getMessage("signing.woffu.excel.sheet.1", new Object[] { year }, locale));

		generateWoffuHeaderInfo(workbook, sheet, locale);

		generateMonthsSigningsWoffuInfo(workbook, sheet, user, month, year, locale);

		for (int i = 0; i <= 16; i++) {
			sheet.autoSizeColumn(i);
		}

		return workbook;
	}
	
	private void generateRowSpace(Sheet sheet, int rowNumber) {
		
		Row rowSpace = sheet.createRow(rowNumber);
		rowSpace.setHeightInPoints((short) 5);
	}
	
	private void setColumnsWidth(Sheet sheet) {

		sheet.setColumnWidth(1, PixelUtils.pixel2WidthUnits(148));

		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);

		sheet.setColumnWidth(4, PixelUtils.pixel2WidthUnits(140));
		sheet.setColumnWidth(5, PixelUtils.pixel2WidthUnits(140));

		sheet.autoSizeColumn(6);
		sheet.autoSizeColumn(7);
	}

	private void generateHeaderInfo(XSSFWorkbook workbook, Sheet sheet, User user, int month, int year, Locale locale) {

		final Row titleRow = sheet.createRow(1);
		final Row infoRow = sheet.createRow(3);

		final CellStyle titleStyle = ExcelUtils.getStyle(workbook, null, null, false, false, false, false, null, null, null, null, 16, true, false);
		final CellStyle defaultStyle = ExcelUtils.getStyle(workbook, null, null, false, false, false, false, null, null, null, null, 10, false, false);

		final CellRangeAddress titleCRA = new CellRangeAddress(1, 1, 1, 7);
		sheet.addMergedRegion(titleCRA);

		final String monthName = new DateFormatSymbols().getMonths()[month - 1].toUpperCase();
		final String dateName = monthName + " " + year;

		final Cell titleCell = titleRow.createCell(1);
		titleCell.setCellStyle(titleStyle);
		titleCell.setCellValue(messageSource.getMessage("signing.excel.title", new Object[] { dateName }, locale));
		
		generateRowSpace(sheet, 2);

		final Cell nameCell = infoRow.createCell(1);
		nameCell.setCellStyle(defaultStyle);
		nameCell.setCellValue(messageSource.getMessage("signing.excel.user.name", new Object[] { user.getName() + " " + user.getSurnames() }, locale));

		final Cell emailCell = infoRow.createCell(4);
		emailCell.setCellStyle(defaultStyle);
		emailCell.setCellValue(messageSource.getMessage("signing.excel.user.email", new Object[] { user.getEmail() }, locale));
	}

	private void generateWoffuHeaderInfo(XSSFWorkbook workbook, Sheet sheet, Locale locale) {

		final Row row = sheet.createRow(0);

		final CellStyle style = ExcelUtils.getStyle(workbook, HorizontalAlignment.CENTER, null, false, false, false, false, null, null, IndexedColors.ROYAL_BLUE.index, IndexedColors.WHITE.index, 11, true, false);

		Cell cell = row.createCell(0);
		cell.setCellStyle(style);
		cell.setCellValue(messageSource.getMessage("signing.woffu.excel.date", new Object[] { }, locale));

		cell = row.createCell(1);
		cell.setCellStyle(style);
		cell.setCellValue(messageSource.getMessage("signing.woffu.excel.name", new Object[] { }, locale));

		cell = row.createCell(2);
		cell.setCellStyle(style);
		cell.setCellValue(messageSource.getMessage("signing.woffu.excel.surnames", new Object[] { }, locale));

		cell = row.createCell(3);
		cell.setCellStyle(style);
		cell.setCellValue(messageSource.getMessage("signing.woffu.excel.role", new Object[] { }, locale));

		cell = row.createCell(4);
		cell.setCellStyle(style);
		cell.setCellValue(messageSource.getMessage("signing.woffu.excel.calendar", new Object[] { }, locale));

		cell = row.createCell(5);
		cell.setCellStyle(style);
		cell.setCellValue(messageSource.getMessage("signing.woffu.excel.reason", new Object[] { }, locale));

		cell = row.createCell(6);
		cell.setCellStyle(style);
		cell.setCellValue(messageSource.getMessage("signing.woffu.excel.reason.hours", new Object[] { }, locale));

		cell = row.createCell(7);
		cell.setCellStyle(style);
		cell.setCellValue(messageSource.getMessage("signing.woffu.excel.start.date", new Object[] { }, locale));

		cell = row.createCell(8);
		cell.setCellStyle(style);
		cell.setCellValue(messageSource.getMessage("signing.woffu.excel.start.date.adj", new Object[] { }, locale));

		cell = row.createCell(9);
		cell.setCellStyle(style);
		cell.setCellValue(messageSource.getMessage("signing.woffu.excel.end.date", new Object[] { }, locale));

		cell = row.createCell(10);
		cell.setCellStyle(style);
		cell.setCellValue(messageSource.getMessage("signing.woffu.excel.end.date.adj", new Object[] { }, locale));

		cell = row.createCell(11);
		cell.setCellStyle(style);
		cell.setCellValue(messageSource.getMessage("signing.woffu.excel.pauses", new Object[] { }, locale));

		cell = row.createCell(12);
		cell.setCellStyle(style);
		cell.setCellValue(messageSource.getMessage("signing.woffu.excel.theorical.schedule", new Object[] { }, locale));

		cell = row.createCell(13);
		cell.setCellStyle(style);
		cell.setCellValue(messageSource.getMessage("signing.woffu.excel.night.hours", new Object[] { }, locale));

		cell = row.createCell(14);
		cell.setCellStyle(style);
		cell.setCellValue(messageSource.getMessage("signing.woffu.excel.worked.hours", new Object[] { }, locale));

		cell = row.createCell(15);
		cell.setCellStyle(style);
		cell.setCellValue(messageSource.getMessage("signing.woffu.excel.difference", new Object[] { }, locale));

		cell = row.createCell(16);
		cell.setCellStyle(style);
		cell.setCellValue(messageSource.getMessage("signing.woffu.excel.signings", new Object[] { }, locale));
	}
	
	private void generateMonthsSigningsInfo(XSSFWorkbook workbook, Sheet sheet, User user, int month, int year, Locale locale) {

		int rowCount = 5;
		int dayOfMonth = 1;
		month = month - 1;

		final Calendar selectedCal = Calendar.getInstance();
		selectedCal.set(Calendar.MONTH, month);
		selectedCal.set(Calendar.YEAR, year);
		selectedCal.setMinimalDaysInFirstWeek(1);

		final Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(Calendar.MONTH, month);
		startCalendar.set(Calendar.YEAR, year);
		startCalendar.set(Calendar.DAY_OF_MONTH, selectedCal.getActualMinimum(Calendar.DAY_OF_MONTH));
		Utiles.setStartDay(startCalendar);

		final Integer lastDayOfMonth = selectedCal.getActualMaximum(Calendar.DAY_OF_MONTH);

		final Calendar endCalendar = Calendar.getInstance();
		endCalendar.set(Calendar.MONTH, month);
		endCalendar.set(Calendar.YEAR, year);
		endCalendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
		Utiles.setEndDay(startCalendar);

		final LocalDateTime startDate = LocalDateTime.ofInstant(startCalendar.toInstant(), ZoneId.systemDefault());
		final LocalDateTime endDate = LocalDateTime.ofInstant(endCalendar.toInstant(), ZoneId.systemDefault());

		final Integer numberOfWeeks = selectedCal.getActualMaximum(Calendar.WEEK_OF_MONTH);

		final List<ConstructionShare> monthlyConstructionSigningList = constructionShareRepository.findWeekSigningsByUserId(startDate, endDate, user.getId());
		final List<DisplacementShare> monthlyDisplacementSigningList = displacementShareRepository.findWeekSigningsByUserId(startDate, endDate, user.getId(), null);
		final List<InterventionShare> monthlyInterventionSigningList = interventionShareRepository.findWeekSigningsByUserId(startDate, endDate, user.getId());
		final List<InterventionPrShare> monthlyInterventionPrSigningList = interventionPrShareRepository.findWeekSigningsByUserId(startDate, endDate, user.getId());
		final List<PersonalSigning> monthlyPersonalSigningList = personalSigingRepository.findWeekSigningsByUserId(startDate, endDate, user.getId());
		final List<WorkShare> monthlyWorkSigningList = workShareRepository.findWeekSigningsByUserId(startDate, endDate, user.getId());

		final CellStyle trimesterTitleStyle = ExcelUtils.getStyle(workbook, null, VerticalAlignment.CENTER, false, false, false, false, null, null, IndexedColors.LIME.getIndex(), null, 11, true, false);
		final CellStyle weekInfoTitleStyle = ExcelUtils.getStyle(workbook, null, null, true, false, false, false, BorderStyle.MEDIUM, IndexedColors.WHITE.getIndex(), IndexedColors.AQUA.getIndex(), IndexedColors.WHITE.getIndex(), 10, false, false);
		final CellStyle weekInfoTitleCenterStyle = ExcelUtils.getStyle(workbook, HorizontalAlignment.CENTER, null, true, false, false, false, BorderStyle.MEDIUM, IndexedColors.WHITE.getIndex(), IndexedColors.AQUA.getIndex(), IndexedColors.WHITE.getIndex(), 10, false, false);
		final CellStyle totalDayStyle = ExcelUtils.getStyle(workbook, HorizontalAlignment.CENTER, null, true, false, false, false, BorderStyle.THIN, IndexedColors.WHITE.getIndex(), IndexedColors.LIME.getIndex(), null, 10, true, false);
		final CellStyle totalWeekStyle = ExcelUtils.getStyle(workbook, HorizontalAlignment.CENTER, null, true, true, false, false, BorderStyle.THIN, IndexedColors.WHITE.getIndex(), IndexedColors.VIOLET.getIndex(), IndexedColors.WHITE.getIndex(), 10, true, false);
		final CellStyle totalWeekRightStyle = ExcelUtils.getStyle(workbook, HorizontalAlignment.RIGHT, null, true, true, false, false, BorderStyle.THIN, IndexedColors.WHITE.getIndex(), IndexedColors.VIOLET.getIndex(), IndexedColors.WHITE.getIndex(), 10, true, false);
		final CellStyle totalMonthStyle = ExcelUtils.getStyle(workbook, HorizontalAlignment.CENTER, null, true, true, false, false, BorderStyle.THIN, IndexedColors.WHITE.getIndex(), IndexedColors.GREEN.getIndex(), IndexedColors.WHITE.getIndex(), 10, true, false);
		final CellStyle totalMonthRightStyle = ExcelUtils.getStyle(workbook, HorizontalAlignment.RIGHT, null, true, true, false, false, BorderStyle.THIN, IndexedColors.WHITE.getIndex(), IndexedColors.GREEN.getIndex(), IndexedColors.WHITE.getIndex(), 10, true, false);
		final CellStyle dayTitleStyle = ExcelUtils.getStyle(workbook, null, null, true, true, true, true, BorderStyle.THIN, IndexedColors.WHITE.getIndex(), IndexedColors.SKY_BLUE.getIndex(), null, 10, false, false);
		final CellStyle dayHoursBlueRightStyle = ExcelUtils.getStyle(workbook, HorizontalAlignment.CENTER, null, true, true, true, true, BorderStyle.THIN, IndexedColors.WHITE.getIndex(), IndexedColors.LIGHT_TURQUOISE.getIndex(), null, 10, false, false);
		final CellStyle dayHoursGreenRightStyle = ExcelUtils.getStyle(workbook, HorizontalAlignment.CENTER, null, true, true, true, true, BorderStyle.THIN, IndexedColors.WHITE.getIndex(), IndexedColors.LIGHT_GREEN.getIndex(), null, 10, false, false);
		final CellStyle dayTimeBlueRightStyle = ExcelUtils.getStyle(workbook, HorizontalAlignment.CENTER, null, true, true, true, true, BorderStyle.THIN, IndexedColors.WHITE.getIndex(), IndexedColors.LIGHT_TURQUOISE.getIndex(), null, 10, false, false);
		final CellStyle dayTimeGreenRightStyle = ExcelUtils.getStyle(workbook, HorizontalAlignment.CENTER, null, true, true, true, true, BorderStyle.THIN, IndexedColors.WHITE.getIndex(), IndexedColors.LIGHT_GREEN.getIndex(), null, 10, false, false);
		final CellStyle whiteBordersStyle = ExcelUtils.getStyle(workbook, null, null, true, true, true, true, BorderStyle.THIN, IndexedColors.WHITE.getIndex(), null, null, 10, false, false);

		final CreationHelper createHelper = workbook.getCreationHelper();
		dayTimeBlueRightStyle.setDataFormat(createHelper.createDataFormat().getFormat("hh:mm:ss"));
		dayTimeGreenRightStyle.setDataFormat(createHelper.createDataFormat().getFormat("hh:mm:ss"));
		totalWeekStyle.setDataFormat(createHelper.createDataFormat().getFormat("[h]:mm:ss"));
		totalMonthStyle.setDataFormat(createHelper.createDataFormat().getFormat("[h]:mm:ss"));

		final CellRangeAddress monthTitleCRA = new CellRangeAddress(rowCount, rowCount, 1, 7);
		sheet.addMergedRegion(monthTitleCRA);

		final Row monthRow = sheet.createRow(rowCount++);
		monthRow.setHeightInPoints((short) 18);

		final String monthName = new DateFormatSymbols().getMonths()[month].toUpperCase();

		final Cell monthTitleCell = monthRow.createCell(1);
		monthTitleCell.setCellStyle(trimesterTitleStyle);
		monthTitleCell.setCellValue(monthName);

		int monthTotalTimeMillis = 0;

		for (int week = 1; week <= numberOfWeeks; week++) {

			int weekTotalTimeMillis = 0;

			final Row weekTitleRow = sheet.createRow(rowCount++);
			weekTitleRow.setHeightInPoints((short) 15);

			final String weekTitle = messageSource.getMessage("signing.excel.week", new Object[]{week}, locale);

			final Cell weekTitleCell = weekTitleRow.createCell(1);
			weekTitleCell.setCellStyle(weekInfoTitleStyle);
			weekTitleCell.setCellValue(weekTitle);

			final Cell projectTitleCell = weekTitleRow.createCell(2);
			projectTitleCell.setCellStyle(weekInfoTitleCenterStyle);
			projectTitleCell.setCellValue(messageSource.getMessage("signing.project.table.project", new Object[]{ }, locale));

			final Cell typeTitleCell = weekTitleRow.createCell(3);
			typeTitleCell.setCellStyle(weekInfoTitleCenterStyle);
			typeTitleCell.setCellValue(messageSource.getMessage("signing.project.table.type", new Object[]{ }, locale));

			final Cell startDateTitleCell = weekTitleRow.createCell(4);
			startDateTitleCell.setCellStyle(weekInfoTitleCenterStyle);
			startDateTitleCell.setCellValue(messageSource.getMessage("signing.project.table.start.date", new Object[]{ }, locale));

			final Cell endDateTitleCell = weekTitleRow.createCell(5);
			endDateTitleCell.setCellStyle(weekInfoTitleCenterStyle);
			endDateTitleCell.setCellValue(messageSource.getMessage("signing.project.table.end.date", new Object[]{ }, locale));

			final Cell totalTimeTitleCell = weekTitleRow.createCell(6);
			totalTimeTitleCell.setCellStyle(weekInfoTitleCenterStyle);
			totalTimeTitleCell.setCellValue(messageSource.getMessage("signing.project.table.total", new Object[]{ }, locale));

			final Cell emptyTitleCell = weekTitleRow.createCell(7);
			emptyTitleCell.setCellStyle(weekInfoTitleCenterStyle);

			selectedCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

			int currentDayOfWeek = selectedCal.get(Calendar.DAY_OF_WEEK);

			for (int dayOfWeek = currentDayOfWeek; dayOfWeek >= Calendar.SUNDAY; dayOfWeek = selectedCal.get(Calendar.DAY_OF_WEEK)) {

				final String dayName = new DateFormatSymbols().getWeekdays()[dayOfWeek];
				final String dayText = StringUtils.capitalize(dayName) + ", " + dayOfMonth;

				Row signingRow = sheet.createRow(rowCount++);
				signingRow.setHeightInPoints((short) 15);

				final Cell dayCell = signingRow.createCell(1);
				dayCell.setCellStyle(dayTitleStyle);
				dayCell.setCellValue(dayText);

				final List<PersonalSigningResumeDTO> dailySigningList = new ArrayList<>();

				final List<ConstructionShare> dailyConstructionList = monthlyConstructionSigningList.stream().filter(s -> {

					final Calendar filterCalendar = Calendar.getInstance();
					filterCalendar.setTime(Date.from(s.getStartDate().toInstant(ZoneOffset.UTC)));
					Utiles.setStartDay(filterCalendar);

					return Utiles.getStartDayDate(selectedCal).equals(filterCalendar.getTime());

				}).collect(Collectors.toList());

				final List<DisplacementShare> dailyDisplacementList = monthlyDisplacementSigningList.stream().filter(s -> {

					final Calendar filterCalendar = Calendar.getInstance();
					filterCalendar.setTime(Date.from(s.getDisplacementDate().toInstant(ZoneOffset.UTC)));
					Utiles.setStartDay(filterCalendar);

					return Utiles.getStartDayDate(selectedCal).equals(filterCalendar.getTime());

				}).collect(Collectors.toList());

				final List<InterventionShare> dailyInterventionList = monthlyInterventionSigningList.stream().filter(s -> {

					final Calendar filterCalendar = Calendar.getInstance();
					filterCalendar.setTime(Date.from(s.getNoticeDate().toInstant(ZoneOffset.UTC)));
					Utiles.setStartDay(filterCalendar);

					return Utiles.getStartDayDate(selectedCal).equals(filterCalendar.getTime());

				}).collect(Collectors.toList());

				final List<InterventionPrShare> dailyInterventionPrList = monthlyInterventionPrSigningList.stream().filter(s -> {

					final Calendar filterCalendar = Calendar.getInstance();
					filterCalendar.setTime(Date.from(s.getStartDate().toInstant(ZoneOffset.UTC)));
					Utiles.setStartDay(filterCalendar);

					return Utiles.getStartDayDate(selectedCal).equals(filterCalendar.getTime());

				}).collect(Collectors.toList());

				final List<PersonalSigning> dailyPersonalList = monthlyPersonalSigningList.stream().filter(s -> {

					final Calendar filterCalendar = Calendar.getInstance();
					filterCalendar.setTime(Date.from(s.getStartDate().toInstant(ZoneOffset.UTC)));
					Utiles.setStartDay(filterCalendar);

					return Utiles.getStartDayDate(selectedCal).equals(filterCalendar.getTime());

				}).collect(Collectors.toList());

				final List<WorkShare> dailyWorkList = monthlyWorkSigningList.stream().filter(s -> {

					final Calendar filterCalendar = Calendar.getInstance();
					filterCalendar.setTime(Date.from(s.getStartDate().toInstant(ZoneOffset.UTC)));
					Utiles.setStartDay(filterCalendar);

					return Utiles.getStartDayDate(selectedCal).equals(filterCalendar.getTime());

				}).collect(Collectors.toList());

				final List<PersonalSigningResumeDTO> dailyConstructionPSRList = SigningMapper.mapConstructionShareToPSRDTO(dailyConstructionList);
				final List<PersonalSigningResumeDTO> dailyDisplacementPSRList = SigningMapper.mapDisplacementShareToPSRDTO(dailyDisplacementList);
				final List<PersonalSigningResumeDTO> dailyInterventionPSRList = SigningMapper.mapInterventionShareToPSRDTO(dailyInterventionList);
				final List<PersonalSigningResumeDTO> dailyInterventionPrPSRList = SigningMapper.mapInterventionPrShareToPSRDTO(dailyInterventionPrList);
				final List<PersonalSigningResumeDTO> dailyPersonalPSRList = SigningMapper.mapPersonalSigningToPSRDTO(dailyPersonalList);
				final List<PersonalSigningResumeDTO> dailyWorkPSRList = SigningMapper.mapWorkShareToPSRDTO(dailyWorkList);

				dailySigningList.addAll(dailyConstructionPSRList);
				dailySigningList.addAll(dailyDisplacementPSRList);
				dailySigningList.addAll(dailyInterventionPSRList);
				dailySigningList.addAll(dailyInterventionPrPSRList);
				dailySigningList.addAll(dailyPersonalPSRList);
				dailySigningList.addAll(dailyWorkPSRList);

				dailySigningList.sort(Comparator.comparing(PersonalSigningResumeDTO::getStartDate));

				int i = 2;
				boolean isFirstRow = true;

				for (final PersonalSigningResumeDTO psrDTO : dailySigningList) {

					if (!isFirstRow) {
						signingRow = sheet.createRow(rowCount++);
						signingRow.setHeightInPoints((short) 15);

						final Cell whiteCell = signingRow.createCell(1);
						whiteCell.setCellStyle(whiteBordersStyle);
					}

					final Cell projectCell = signingRow.createCell(i++);
					projectCell.setCellStyle(i % 2 != 0 ? dayHoursBlueRightStyle : dayHoursGreenRightStyle);
					projectCell.setCellValue(psrDTO.getProjectName());

					final Cell typeCell = signingRow.createCell(i++);
					typeCell.setCellStyle(i % 2 != 0 ? dayHoursBlueRightStyle : dayHoursGreenRightStyle);
					typeCell.setCellValue(messageSource.getMessage(psrDTO.getType(), new Object[]{ }, locale));

					final Cell startDateCell = signingRow.createCell(i++);
					startDateCell.setCellStyle(i % 2 != 0 ? dayHoursBlueRightStyle : dayHoursGreenRightStyle);
					startDateCell.setCellValue(Utiles.transform(psrDTO.getStartDate(), DATE_TIME_FORMAT));

					final Cell endDateCell = signingRow.createCell(i++);
					endDateCell.setCellStyle(i % 2 != 0 ? dayHoursBlueRightStyle : dayHoursGreenRightStyle);
					endDateCell.setCellValue(Utiles.transform(psrDTO.getEndDate(), DATE_TIME_FORMAT));

					final Long diffInSeconds = Duration.between(psrDTO.getStartDate(), psrDTO.getEndDate()).toSeconds();
					final String totalTimeText = Utiles.secondsToHoursAndMinutesAndSecondsString(diffInSeconds.intValue());

					final Cell totalTimeCell = signingRow.createCell(i++);
					totalTimeCell.setCellStyle(i % 2 != 0 ? dayTimeBlueRightStyle : dayTimeGreenRightStyle);
					totalTimeCell.setCellValue(Utiles.convertTimeInternal(totalTimeText));

					final Cell emptyCell = signingRow.createCell(i++);
					emptyCell.setCellStyle(i % 2 != 0 ? dayHoursBlueRightStyle : dayHoursGreenRightStyle);

					i = 2;
					isFirstRow = false;
				}

				if (!isFirstRow) {

					final Integer duration = dayTotalHours(dailySigningList);
					final Long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
					final String totalTimeText = Utiles.secondsToHoursAndMinutesAndSecondsString(diffInSeconds.intValue());

					weekTotalTimeMillis += duration;

				} else {

					while (i <= 7) {

						final Cell emptyCell = signingRow.createCell(i++);
						emptyCell.setCellStyle(i % 2 != 0 ? dayHoursBlueRightStyle : dayHoursGreenRightStyle);
					}
				}

				selectedCal.set(Calendar.DAY_OF_MONTH, ++dayOfMonth);

				final Boolean isEndMonth = lastDayOfMonth == (dayOfMonth - 1);

				if (dayOfWeek == Calendar.SUNDAY || isEndMonth) {

					final Row totalWeekHoursRow = sheet.createRow(rowCount++);

					final CellRangeAddress totalWeekHoursCRA = new CellRangeAddress(rowCount - 1, rowCount - 1, 1, 7);
					sheet.addMergedRegion(totalWeekHoursCRA);

					final Cell totalWeekHoursCell = totalWeekHoursRow.createCell(1);
					totalWeekHoursCell.setCellStyle(totalWeekRightStyle);

					monthTotalTimeMillis += weekTotalTimeMillis;

					final Long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(weekTotalTimeMillis);
					final String totalTimeText = Utiles.secondsToHoursAndMinutesAndSecondsString(diffInSeconds.intValue());

					totalWeekHoursCell.setCellValue(messageSource.getMessage("signing.excel.total.hours", new Object[] { totalTimeText }, locale));

					final Cell totalWeekHoursDataCell = totalWeekHoursRow.createCell(8);
					totalWeekHoursDataCell.setCellStyle(totalWeekStyle);

					totalWeekHoursDataCell.setCellValue(Utiles.convertTimeInternal(totalTimeText));

					if (!isEndMonth) {

						final Row emptyRow = sheet.createRow(rowCount++);

						for (int cell = 1; cell <= 7; cell++) {

							final Cell whiteCell = emptyRow.createCell(cell);
							whiteCell.setCellStyle(whiteBordersStyle);
						}

					} else {

						final Row totalMonthHoursRow = sheet.createRow(rowCount++);

						final CellRangeAddress totalMonthHoursCRA = new CellRangeAddress(rowCount - 1, rowCount - 1, 1, 7);
						sheet.addMergedRegion(totalMonthHoursCRA);

						final Cell totalMonthHoursCell = totalMonthHoursRow.createCell(1);
						totalMonthHoursCell.setCellStyle(totalMonthRightStyle);

						for (int ii = 2; ii <= 7; ii++) {

							final Cell totalMonthHoursHelperCell = totalMonthHoursRow.createCell(ii);
							totalMonthHoursHelperCell.setCellStyle(totalMonthRightStyle);
						}

						final Long monthDiffInSeconds = TimeUnit.MILLISECONDS.toSeconds(monthTotalTimeMillis);
						final String totalMonthTimeText = Utiles.secondsToHoursAndMinutesAndSecondsString(monthDiffInSeconds.intValue());

						totalMonthHoursCell.setCellValue(messageSource.getMessage("signing.excel.total.month.hours", new Object[] { totalMonthTimeText }, locale));

						final Cell totalMonthHoursDataCell = totalMonthHoursRow.createCell(8);
						totalMonthHoursDataCell.setCellStyle(totalMonthStyle);

						totalMonthHoursDataCell.setCellValue(Utiles.convertTimeInternal(totalMonthTimeText));
					}

					break;
				}
			}
		}
	}

	private void generateMonthsSigningsWoffuInfo(XSSFWorkbook workbook, Sheet sheet, User user, int month, int year, Locale locale) {

		final List<TimeControlTableDTO> timeControlList = this.timeControlOldService.getTimeControlTableDTOByDateAndUser(month, year, user.getId(), user.getActivityCenter().getId(), locale);
		final List<DatesModel> signingDates = getSigningDates(user.getId(), month, year);

		int rowNumber = 1;
		int totalDifferenceMinutes = 0;

		for (TimeControlTableDTO timeControl : timeControlList) {

			final Row row = sheet.createRow(rowNumber++);

			Cell cell = row.createCell(0);
			cell.setCellValue(Utiles.transform(timeControl.getDate(), DATE_FORMAT));

			cell = row.createCell(1);
			cell.setCellValue(user.getName());

			cell = row.createCell(2);
			cell.setCellValue(user.getSurnames());

			cell = row.createCell(3);
			cell.setCellValue(user.getSubRole().getRol());

			cell = row.createCell(4);
			cell.setCellValue(user.getActivityCenter().getName());

			final String defaultReason = messageSource.getMessage("time.control.laboral", new Object[] { }, locale);
			final Boolean workingDay = timeControl.getReason().substring(1).equals(defaultReason);

			if (timeControl.getReason() != null && !workingDay) {

				cell = row.createCell(5);
				cell.setCellValue(timeControl.getReason().substring(1));
			}

			cell = row.createCell(6);
			cell.setCellValue(0);

			if (timeControl.getStartHour() != null) {

				cell = row.createCell(7);
				cell.setCellValue(Utiles.transform(timeControl.getStartHour(), TIME_FORMAT));

				cell = row.createCell(8);
				cell.setCellValue(Utiles.transform(timeControl.getStartHour(), TIME_FORMAT));
			}

			if (timeControl.getEndHour() != null) {

				cell = row.createCell(9);
				cell.setCellValue(Utiles.transform(timeControl.getEndHour(), TIME_FORMAT));

				cell = row.createCell(10);
				cell.setCellValue(Utiles.transform(timeControl.getEndHour(), TIME_FORMAT));
			}

			cell = row.createCell(11);
			cell.setCellValue(workingDay ? (Utiles.hourTimeToMinutes(timeControl.getBreaks()) / 60.0) : 1);

			cell = row.createCell(12);
			cell.setCellValue(workingDay ? timeControl.getJourney() : 0);

			cell = row.createCell(13);
			cell.setCellValue(0);

			cell = row.createCell(14);
			cell.setCellValue(Utiles.hourTimeToMinutes(timeControl.getTotalHours()) / 60.0);

			final int differenceMinutes = Utiles.hourTimeToMinutes(timeControl.getDifference());
			final double difference = differenceMinutes / 60.0;

			totalDifferenceMinutes += differenceMinutes;

			cell = row.createCell(15);
			cell.setCellValue(difference);

			final List<DatesModel> todaySignings = signingDates.stream()
                    .filter(s -> DateUtils.isSameDay(Date.from(s.getStartDate().toInstant()), Date.from(timeControl.getDate().toInstant(ZoneOffset.UTC))))
					.sorted(Comparator.comparing(DatesModel::getStartDate))
					.collect(Collectors.toList());

            String signingsText = "";

			for (DatesModel dm : todaySignings) {
				signingsText += Utiles.getTimeFormatted(dm.getStartDate()) + "•" + Utiles.getTimeFormatted(dm.getEndDate()) + " ";
			}

			if (!signingsText.isEmpty()) {
				signingsText = signingsText.substring(0, signingsText.length() - 1);
			}

			cell = row.createCell(16);
			cell.setCellValue(signingsText);
		}

		final Row row = sheet.createRow(rowNumber++);

		for (int i = 1; i <= 16; i++) {
			row.createCell(i).setCellValue("-");
		}

		if (totalDifferenceMinutes < 0) {

			final CellStyle style = ExcelUtils.getStyle(workbook, null, null, false, false, false, false, null, null, IndexedColors.YELLOW.index, IndexedColors.RED.index, 11, false, false);

			final double totalDifference = totalDifferenceMinutes / 60.0;
			row.getCell(15).setCellStyle(style);
			row.getCell(15).setCellValue(totalDifference);
		}
	}

	private List<DatesModel> getSigningDates(Long userId, int month, int year) {

		final int minDay = 1;
		final int maxDay = Utiles.getDaysOfMonth(month, year);

		final LocalDateTime startDate = LocalDateTime.of(year, month, minDay, 0, 0, 0);
		final LocalDateTime endDate = LocalDateTime.of(year, month, maxDay, 23, 59, 59);

		final TimeControlFilterDto filterDto = new TimeControlFilterDto();
		filterDto.setUserId(userId.intValue());
		filterDto.setStartDate(startDate);
		filterDto.setEndDate(endDate);

		final List<TimeControlDto> timeControls = this.timeControlService.list(filterDto);

		final List<TimeControlDto> imputableTimeControls = timeControls.stream()
				.filter(tc -> !TimeControlTypeEnumDto.MANUAL_SIGNINGS.equals(tc.getType()))
				.collect(Collectors.toList());

		final List<DatesModel> todayDates = new ArrayList<>();

		for (TimeControlDto dto : imputableTimeControls) {
			final DatesModel dm = new DatesModel();
			dm.setStartDate(Date.from(dto.getStartDate().toInstant(ZoneOffset.UTC)));
			dm.setEndDate(Date.from(dto.getEndDate().toInstant(ZoneOffset.UTC)));

			todayDates.add(dm);
		}

		return todayDates;
	}

	private Integer dayTotalHours(final List<PersonalSigningResumeDTO> dailySigningList) {

		final List<DatesModel> todayDates = SigningMapper.mapPersonalSigningResumeToDM(dailySigningList);

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

		int totalHours = 0;

		for (DatesModel dm : datesModelList) {
			totalHours += dm.getEndDate().getTime() - dm.getStartDate().getTime();
		}

		return totalHours;
	}
}
