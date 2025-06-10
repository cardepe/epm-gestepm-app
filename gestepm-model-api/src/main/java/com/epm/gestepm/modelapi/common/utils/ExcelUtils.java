package com.epm.gestepm.modelapi.common.utils;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	private ExcelUtils() {

	}
	
	public static void setHeaderStyle(XSSFWorkbook workbook, Row row, int lastIndex) {
		CellStyle cellStyle = workbook.createCellStyle();

		for (int i = 1; i < lastIndex; i++) {
			Cell cell = row.getCell(i);
			if (cell == null) {
				cell = row.createCell(i);
			}
			
			if (i == 1) {
				cellStyle.setBorderLeft(BorderStyle.MEDIUM);
			} else if (i == lastIndex - 1) {
				cellStyle.setBorderRight(BorderStyle.MEDIUM);				
			}
			
			cellStyle.setBorderTop(BorderStyle.MEDIUM);
			cellStyle.setBorderBottom(BorderStyle.MEDIUM);
			
			cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			XSSFFont font = workbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setBold(true);
			cellStyle.setFont(font);
			
			cell.setCellStyle(cellStyle);
		}
	}
	
	public static void setMainStyle(XSSFWorkbook workbook, Row row, int lastIndex) {
		
		CellStyle cellLeftStyle = workbook.createCellStyle();
		CellStyle cellRightStyle = workbook.createCellStyle();
		Cell cell = row.createCell(1);
		
		if (row.getRowNum() == 3) {
			cellLeftStyle.setBorderTop(BorderStyle.MEDIUM);
		} else if(row.getRowNum() == 7) {
			cellLeftStyle.setBorderBottom(BorderStyle.MEDIUM);
		}
		
		cellLeftStyle.setBorderLeft(BorderStyle.MEDIUM);
		cellLeftStyle.setBorderRight(BorderStyle.MEDIUM);
		
		cellLeftStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		cellLeftStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		cellLeftStyle.setAlignment(HorizontalAlignment.CENTER);
		
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setBold(true);
		cellLeftStyle.setFont(font);
		
		cell.setCellStyle(cellLeftStyle);
		
		for (int i = 2; i < lastIndex; i++) {

			Cell rightCell = row.getCell(i);
			if (rightCell == null) {
				rightCell = row.createCell(i);
			}
			
			if (i == 15) {
				cellRightStyle.setBorderRight(BorderStyle.MEDIUM);
			}
			
			cellRightStyle.setBorderTop(BorderStyle.MEDIUM);
			cellRightStyle.setBorderBottom(BorderStyle.MEDIUM);
			
			cellRightStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
			cellRightStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			cellRightStyle.setAlignment(HorizontalAlignment.CENTER);
			
			rightCell.setCellStyle(cellRightStyle);
		}
	}
	
	public static CellStyle getLightYellowStyle(XSSFWorkbook workbook, HorizontalAlignment alignment, boolean isFirst, boolean isLast) {
		
		CellStyle cellStyle = workbook.createCellStyle();
		
		if (isFirst) {
			cellStyle.setBorderTop(BorderStyle.MEDIUM);
		}
		
		if (isLast) {
			cellStyle.setBorderBottom(BorderStyle.MEDIUM);
		}
		
		cellStyle.setBorderLeft(BorderStyle.MEDIUM);
		cellStyle.setBorderRight(BorderStyle.MEDIUM);
		
		cellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		
		cellStyle.setAlignment(alignment);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		cellStyle.setFont(font);

		return cellStyle;
	}
	
	public static CellStyle getLightYellowStyle(XSSFWorkbook workbook, HorizontalAlignment alignment, boolean isFirst, boolean isLast, BorderStyle borderStyle) {
		
		CellStyle cellStyle = workbook.createCellStyle();
		
		if (isFirst) {
			cellStyle.setBorderTop(borderStyle);
		}
		
		if (isLast) {
			cellStyle.setBorderBottom(borderStyle);
		}
		
		cellStyle.setBorderLeft(borderStyle);
		cellStyle.setBorderRight(borderStyle);
		
		cellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		
		cellStyle.setAlignment(alignment);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		cellStyle.setFont(font);

		return cellStyle;
	}
	
	public static CellStyle getWhiteStyle(XSSFWorkbook workbook, HorizontalAlignment alignment, boolean isFirst, boolean isLast) {
		
		CellStyle cellStyle = workbook.createCellStyle();
		
		if (isFirst) {
			cellStyle.setBorderTop(BorderStyle.MEDIUM);
		}
		
		if (isLast) {
			cellStyle.setBorderBottom(BorderStyle.MEDIUM);
		}
		
		cellStyle.setBorderLeft(BorderStyle.MEDIUM);
		cellStyle.setBorderRight(BorderStyle.MEDIUM);
		
		cellStyle.setAlignment(alignment);
		
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		cellStyle.setFont(font);

		return cellStyle;
	}

	public static CellStyle getStyle(XSSFWorkbook workbook, HorizontalAlignment alignment,
			VerticalAlignment verticalAlignment, boolean topBorder, boolean bottomBorder, boolean leftBorder,
			boolean rightBorder, BorderStyle borderStyle, Short borderColorIndex, Short backgroundColorIndex, Short colorIndex, int fontSize, boolean isBold, boolean isItalic) {

		CellStyle cellStyle = workbook.createCellStyle();
		
		if (topBorder) {
			cellStyle.setBorderTop(borderStyle);
			
			if (borderColorIndex != null) {
				cellStyle.setTopBorderColor(borderColorIndex);
			}
		}
		
		if (bottomBorder) {
			cellStyle.setBorderBottom(borderStyle);
			
			if (borderColorIndex != null) {
				cellStyle.setBottomBorderColor(borderColorIndex);
			}
		}
		
		if (leftBorder) {
			cellStyle.setBorderLeft(borderStyle);
			
			if (borderColorIndex != null) {
				cellStyle.setLeftBorderColor(borderColorIndex);
			}
		}
		
		if (rightBorder) {
			cellStyle.setBorderRight(borderStyle);
			
			if (borderColorIndex != null) {
				cellStyle.setRightBorderColor(borderColorIndex);
			}
		}
		
		if (backgroundColorIndex != null) {
			cellStyle.setFillForegroundColor(backgroundColorIndex);
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		}
		
		if (alignment != null) {
			cellStyle.setAlignment(alignment);
		}
		
		if (verticalAlignment != null) {
			cellStyle.setVerticalAlignment(verticalAlignment);
		}
		
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) fontSize);
		if (colorIndex != null) {
			font.setColor(colorIndex);
		}
		font.setBold(isBold);
		font.setItalic(isItalic);
		cellStyle.setFont(font);

		return cellStyle;
	}
	
	public static CellStyle getGreenMonthStyle(XSSFWorkbook workbook) {
		
		CellStyle cellStyle = workbook.createCellStyle();
		
		cellStyle.setBorderTop(BorderStyle.MEDIUM);
		cellStyle.setBorderBottom(BorderStyle.MEDIUM);
		
		cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setBold(true);
		cellStyle.setFont(font);

		return cellStyle;
	}
	
	public static void getExpensesTitleStyle(XSSFWorkbook workbook, Row row, int lastIndex) {
		
		CellStyle cellStyle = workbook.createCellStyle();
		Cell cell = row.createCell(lastIndex);
		
		if (lastIndex == 1) {
			cellStyle.setBorderLeft(BorderStyle.MEDIUM);
		} else if (lastIndex == 15) {
			cellStyle.setBorderRight(BorderStyle.MEDIUM);
		}
		
		cellStyle.setBorderTop(BorderStyle.MEDIUM);
		cellStyle.setBorderBottom(BorderStyle.MEDIUM);
		
		cellStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setBold(true);
		cellStyle.setFont(font);

		cell.setCellStyle(cellStyle);
	}
	
	public static void getLightGreenTitleStyle(XSSFWorkbook workbook, Row row, int lastIndex) {
		
		CellStyle cellStyle = workbook.createCellStyle();
		Cell cell = row.createCell(lastIndex);
		
		if (lastIndex == 1) {
			cellStyle.setBorderLeft(BorderStyle.MEDIUM);
		} else if (lastIndex == 15) {
			cellStyle.setBorderRight(BorderStyle.MEDIUM);
		}
		
		cellStyle.setBorderTop(BorderStyle.MEDIUM);
		cellStyle.setBorderBottom(BorderStyle.MEDIUM);
		
		cellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setBold(true);
		cellStyle.setFont(font);

		cell.setCellStyle(cellStyle);
	}
	
	public static void getWhiteTitleStyle(XSSFWorkbook workbook, Row row, int lastIndex) {
		
		CellStyle cellStyle = workbook.createCellStyle();
		Cell cell = row.createCell(lastIndex);
		
		if (lastIndex == 1) {
			cellStyle.setBorderLeft(BorderStyle.MEDIUM);
		} else if (lastIndex == 15) {
			cellStyle.setBorderRight(BorderStyle.MEDIUM);
		}
		
		cellStyle.setBorderTop(BorderStyle.MEDIUM);
		cellStyle.setBorderBottom(BorderStyle.MEDIUM);
		
		cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setBold(true);
		cellStyle.setFont(font);

		cell.setCellStyle(cellStyle);
	}
}
