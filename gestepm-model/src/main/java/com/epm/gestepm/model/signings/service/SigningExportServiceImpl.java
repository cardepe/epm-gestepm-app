package com.epm.gestepm.model.signings.service;

import com.epm.gestepm.lib.locale.LocaleProvider;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.modelapi.common.utils.ExcelUtils;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.shares.share.dto.ShareDto;
import com.epm.gestepm.modelapi.shares.share.dto.filter.ShareFilterDto;
import com.epm.gestepm.modelapi.shares.share.service.ShareService;
import com.epm.gestepm.modelapi.signings.dto.SigningExportDto;
import com.epm.gestepm.modelapi.signings.service.SigningExportService;
import com.epm.gestepm.modelapi.user.dto.UserDto;
import com.epm.gestepm.modelapi.user.dto.finder.UserByIdFinderDto;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;

@AllArgsConstructor
@Validated
@Service("signingExportService")
@EnableExecutionLog(layerMarker = SERVICE)
public class SigningExportServiceImpl implements SigningExportService {

    private final LocaleProvider localeProvider;

    private final MessageSource messageSource;

    private final ShareService shareService;

    private final UserService userService;

    @Override
    public String buildFileName(final SigningExportDto signingExportDto) {

        final UserDto user = this.userService.findOrNotFound(new UserByIdFinderDto(signingExportDto.getUserId()));

        return "Signings_" + user.getFullName() + "_" + Utiles.getDateFormatted(signingExportDto.getStartDate()) + "_"
                + Utiles.getDateFormatted(signingExportDto.getEndDate()) + ".xlsx";
    }

    @Override
    public byte[] generate(final SigningExportDto signingExport) {

        try {

            final String language = this.localeProvider.getLocale().orElse("es");
            final java.util.Locale locale = new java.util.Locale(language);

            final UserDto userDto = this.userService.findOrNotFound(new UserByIdFinderDto(signingExport.getUserId()));

            final ByteArrayOutputStream file = new ByteArrayOutputStream();
            final XSSFWorkbook workbook = new XSSFWorkbook();

            final Sheet sheet = workbook.createSheet(messageSource.getMessage("signing.excel.sheet.1", new Object[]{}, locale));

            this.writeHeaderInfo(workbook, sheet, userDto, signingExport, locale);

            // generateMonthsSigningsInfo(workbook, sheet, user, month, year, locale);

            // setColumnsWidth(sheet);

            workbook.write(file);

            return file.toByteArray();
        } catch (Exception ex) {
            return null;
        // } catch (IOException | DocumentException ex) {
            // throw new SigningExportException(signingExport.getStartDate(), signingExport.getEndDate(), signingExport.getUserId(), ex.getMessage());
        }
    }

    private List<ShareDto> loadSignings(final SigningExportDto signingExport) {

        final ShareFilterDto shareFilterDto = new ShareFilterDto();
        shareFilterDto.setStartDate(signingExport.getStartDate());
        shareFilterDto.setEndDate(signingExport.getEndDate());
        shareFilterDto.setUserIds(List.of(signingExport.getUserId()));

        return this.shareService.list(shareFilterDto);
    }

    private void writeHeaderInfo(final XSSFWorkbook workbook, final Sheet sheet, final UserDto user, final LocalDateTime startDate, final LocalDateTime endDate, Locale locale) {
        final int TITLE_ROW_INDEX = 1;
        final int INFO_ROW_INDEX = 3;

        final CellStyle titleStyle = ExcelUtils.getStyle(workbook, null, null, false, false, false, false, null, null, null, null, 16, true, false);
        final CellStyle defaultStyle = ExcelUtils.getStyle(workbook, null, null, false, false, false, false, null, null, null, null, 10, false, false);

        final Row titleRow = sheet.createRow(TITLE_ROW_INDEX);
        final Row infoRow = sheet.createRow(INFO_ROW_INDEX);

        sheet.addMergedRegion(new CellRangeAddress(TITLE_ROW_INDEX, TITLE_ROW_INDEX, 1, 7));

        this.createCell(titleRow, 1, titleStyle, this.messageSource.getMessage("signing.excel.title", new Object[] { Utiles.getDateFormatted(startDate), Utiles.getDateFormatted(endDate) }, locale));
        this.createRowAsSeparation(sheet, 2);

        this.createCell(infoRow, 1, defaultStyle, this.messageSource.getMessage("signing.excel.user.name", new Object[] { user.getFullName() }, locale));
        this.createCell(infoRow, 4, defaultStyle, this.messageSource.getMessage("signing.excel.user.email", new Object[] { user.getEmail() }, locale));
    }

    private void writeContent(final SigningExportDto signingExport) {

        final List<ShareDto> shares = this.loadSignings(signingExport);

        Month currentMonth = signingExport.getStartDate().getMonth();
        int weekOfMonth = 0;

        final WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);

        for (LocalDateTime date = signingExport.getStartDate(); !date.isAfter(signingExport.getEndDate()); date = date.plusDays(1)) {

            final boolean isNewMonth = !date.getMonth().equals(currentMonth);
            if (isNewMonth) {
                currentMonth = date.getMonth();
            }

            final Integer currentWeekOfMonth = date.get(weekFields.weekOfMonth());
            final boolean isNewWeek = !currentWeekOfMonth.equals(weekOfMonth);
            if (isNewWeek) {
                weekOfMonth = currentWeekOfMonth;
            }

            
        }
    }

    private void createCell(final Row row, int columnIndex, final CellStyle style, final String value) {
        final Cell cell = row.createCell(columnIndex);
        cell.setCellStyle(style);
        cell.setCellValue(value);
    }

    private void createRowAsSeparation(final Sheet sheet, final Integer rowNumber) {
        sheet.createRow(rowNumber).setHeightInPoints((short) 5);
    }

}
