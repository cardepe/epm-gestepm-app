package com.epm.gestepm.model.common.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class ExcelUtils {

    public static void setCell(final Row row, final int cellIndex, final Object value, final CellStyle style) {
        Cell cell = row.getCell(cellIndex);
        if (cell == null) {
            cell = row.createCell(cellIndex);
        }

        if (style != null) {
            cell.setCellStyle(style);
        }

        if (value == null) {
            cell.setBlank();
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Number) {
            cell.setCellValue(((Number) value).doubleValue());
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof LocalDateTime) {
            cell.setCellValue((LocalDateTime) value);
        } else if (value instanceof LocalDate) {
            cell.setCellValue((LocalDate) value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
        } else {
            cell.setCellValue(value.toString());
        }
    }

    public static void createRowAsSeparation(final Sheet sheet, final Integer rowNumber, final int height) {
        sheet.createRow(rowNumber).setHeightInPoints((short) height);
    }
}
