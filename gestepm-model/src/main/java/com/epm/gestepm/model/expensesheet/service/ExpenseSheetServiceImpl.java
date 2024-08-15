package com.epm.gestepm.model.expensesheet.service;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.epm.gestepm.model.expensefile.dao.ExpenseFileRepository;
import com.epm.gestepm.model.expense.dao.ExpenseRepository;
import com.epm.gestepm.model.expensesheet.dao.ExpenseSheetRepository;
import com.epm.gestepm.modelapi.common.utils.ExcelUtils;
import com.epm.gestepm.modelapi.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.expensesheet.dto.ExpenseSheetTableDTO;
import com.epm.gestepm.modelapi.project.dto.ProjectExpenseSheetDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gestepm.modelapi.expense.dto.Expense;
import com.epm.gestepm.modelapi.expensefile.dto.ExpenseFile;
import com.epm.gestepm.modelapi.expensesheet.dto.ExpenseSheet;
import com.epm.gestepm.modelapi.pricetype.dto.PriceType;
import com.epm.gestepm.modelapi.expensesheet.service.ExpenseSheetService;
import com.epm.gestepm.lib.file.FileUtils;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.model.common.utils.classes.SingletonUtil;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;

@Service
@Transactional
public class ExpenseSheetServiceImpl implements ExpenseSheetService {

	private static final Log log = LogFactory.getLog(ExpenseSheetServiceImpl.class);

	private static final Integer EXPENSES_LIMITER_COUNT = 15;

	private static final Integer EXPENSES_MAX_LIMIT = 40;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ExpenseSheetRepository expenseSheetRepository;
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Autowired
	private ExpenseFileRepository expenseFileRepository;
	
	@Autowired
	private SingletonUtil singletonUtil;

	@Override
	public ExpenseSheet save(ExpenseSheet expenseSheet) {
		return expenseSheetRepository.save(expenseSheet);
	}
	
	@Override
	public ExpenseSheet create(ExpenseSheet expenseSheet) {
		
		expenseSheet = expenseSheetRepository.save(expenseSheet);
		
		List<Expense> expenses = expenseSheet.getExpenses();
		
		if (expenses != null && !expenses.isEmpty()) {
			
			for (Expense expense : expenses) {

				expense.setExpenseSheet(expenseSheet);
				expense = expenseRepository.save(expense);
				
				List<ExpenseFile> expensesFile = expense.getFiles();
				
				if (expensesFile != null && !expensesFile.isEmpty()) {
					
					for (ExpenseFile expenseFile : expensesFile) {
						
						expenseFile.setExpense(expense);
						expenseFileRepository.save(expenseFile);
					}
				}
			}		
		}
		
		return expenseSheet;
	}
	
	@Override
	public ExpenseSheet getExpenseSheetById(Long expenseSheetId) {
		return expenseSheetRepository.findById(expenseSheetId).orElse(null);
	}
	
	@Override
	public List<ExpenseSheet> getExpenseSheetsByProjectId(Long projectId) {
		return expenseSheetRepository.findExpenseSheetsByProjectId(projectId);
	}
	
	@Override
	public ExpenseSheet getExpenseSheetByIdAndUserId(Long expenseSheetId, Long userId) {
		return expenseSheetRepository.findByIdAndUserId(expenseSheetId, userId);
	}
	
	@Override
	public ExpenseSheet getExpenseSheetByIdAndProjectId(Long expenseSheetId, Long projectId) {
		return expenseSheetRepository.findByIdAndProjectId(expenseSheetId, projectId);
	}

	@Override
	public List<ExpenseSheet> getExpenseSheetsByUser(Long userId) {
		return expenseSheetRepository.findExpenseSheetsByUserId(userId);
	}
	
	@Override
	public List<ExpenseSheet> getExpenseSheetsByUserAndStatus(Long userId, String status) {
		return expenseSheetRepository.findExpenseSheetsByUserIdAndStatus(userId, status);
	}
	
	@Override
	public Long getExpenseSheetsCountByUser(Long userId) {
		return expenseSheetRepository.findExpenseSheetsCountByUserId(userId);
	}
	
	@Override
	public Long getExpenseSheetsCountByRRHH(Long userId) {
		return expenseSheetRepository.findExpenseSheetsCountByRRHH(userId);
	}

	@Override
	public void deleteById(Long expenseSheetId) {
		expenseSheetRepository.deleteById(expenseSheetId);
	}
	
	@Override
	public List<ExpenseSheetTableDTO> getExpenseSheetsByUserDataTables(Long userId, PaginationCriteria pagination) {
		return expenseSheetRepository.findExpenseSheetsByUserDataTables(userId, pagination);
	}
	
	@Override
	public List<ExpenseSheetTableDTO> getExpenseSheetsByRRHHDataTables(Long userId, PaginationCriteria pagination) {
		return expenseSheetRepository.findExpenseSheetsByRRHHDataTables(userId, pagination);
	}

	@Override
	public List<ProjectExpenseSheetDTO> getProjectExpenseSheetDTOsByProjectId(Long projectId, PaginationCriteria pagination) {
		return expenseSheetRepository.findProjectExpenseSheetDTOsByProjectId(projectId, pagination);
	}
	
	@Override
	public Long getProjectExpenseSheetDTOsCountByProjectId(Long projectId) {
		return expenseSheetRepository.findProjectExpenseSheetDTOsCountByProjectId(projectId);
	}
	
	@Override
	public List<ExpensesMonthDTO> getExpensesMonthDTOByProjectId(Long projectId, Integer year) {
		return expenseSheetRepository.findExpensesMonthDTOByProjectId(projectId, year);
	}
	
	@Override
	public Double getTotalYearExpensesByProjectId(Long projectId, Integer year) {
		return expenseSheetRepository.findTotalYearExpensesByProjectId(projectId, year);
	}
	
	@Override
	public XSSFWorkbook generateExpenseSheetExcel(Long expenseId, Long userId, ExpenseSheet expenseSheet, Locale locale) {
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		Sheet sheet = workbook.createSheet(messageSource.getMessage("expense.excel.sheet.1", new Object[] {}, locale));
		
		// Main Project Info
		generateHeaderInfo(workbook, sheet, expenseSheet, locale);
		
		generateExpensesInfo(workbook, sheet, expenseSheet, locale);
		
		// Columns C - N Width
		for (int i = 0; i < 10; i++) {
			sheet.autoSizeColumn(i);
		}
		
		return workbook;
	}
	
	@Override
	public byte[] generateExpensePdf(ExpenseSheet expenseSheet, Locale locale) {
		
		try {

			final int expensesCount = expenseSheet.getExpenses().size();

			final PdfReader pdfTemplate = new PdfReader("/templates/pdf/expenses_" + locale.getLanguage() + (expensesCount > EXPENSES_LIMITER_COUNT ? "_full" : "") + ".pdf");
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        final PdfStamper stamper = new PdfStamper(pdfTemplate, baos);
	        
	        stamper.getAcroFields().setField("pageNumber", expenseSheet.getId().toString());
	        stamper.getAcroFields().setField("creationDate", Utiles.getDateFormatted(expenseSheet.getCreationDate()));
	        stamper.getAcroFields().setField("userName", (expenseSheet.getUser().getName() + " " + expenseSheet.getUser().getSurnames()).toUpperCase());
	        stamper.getAcroFields().setField("projectName", (expenseSheet.getProject().getName()).toUpperCase());
	        
	        if (!expenseSheet.getExpenses().isEmpty()) {

	        	List<PriceType> priceTypes = singletonUtil.getPriceTypes();
				PriceType kmPriceType = priceTypes.stream().filter(p -> "price.type.4".equals(p.getName())).collect(Collectors.toList()).get(0);
				
				double kmPrice = kmPriceType.getAmount();
				double totalSum = 0;
				
	        	int i = 1;
	        	for (Expense expense : expenseSheet.getExpenses()) {
	        		
	        		stamper.getAcroFields().setField("expenseDate" + i, Utiles.getDateFormatted(expense.getStartDate()));
	        		stamper.getAcroFields().setField("expenseDescription" + i, expense.getJustification());
					stamper.getAcroFields().setField("paymentType" + i, messageSource.getMessage(expense.getPaymentType().getName(), new Object[] { }, locale));

					double expensePrice = expense.getTotal();

	        		if (expense.getPriceType().getId() == 4) {
		        		stamper.getAcroFields().setField("expenseKms" + i, String.valueOf(expense.getKms()));
		        		stamper.getAcroFields().setField("expenseEurKms" + i, new DecimalFormat("#.## €").format(kmPrice));
	        		}

					stamper.getAcroFields().setField("expenseWithIva" + i, "");
					stamper.getAcroFields().setField("expenseWithoutIva" + i, new DecimalFormat("#.## €").format(expensePrice));
					stamper.getAcroFields().setField("expenseTotal" + i, new DecimalFormat("#.## €").format(expensePrice));
	        		
	        		totalSum += expensePrice;

	        		if (i++ > EXPENSES_MAX_LIMIT) {
	        			log.info("The expense " + expenseSheet.getId() + " has more than " + EXPENSES_MAX_LIMIT + " expense rows.");
						break;
	        		}
	        	}
	        	
	        	stamper.getAcroFields().setField("total", new DecimalFormat("#.## €").format(totalSum));
	        	
	        	for (Expense expense : expenseSheet.getExpenses()) {
	        		
	        		if (expense.getFiles() != null && !expense.getFiles().isEmpty()) {
	        			
	        			int pageNumber = pdfTemplate.getNumberOfPages();
			        	
			        	float pageWidth = pdfTemplate.getPageSize(pageNumber).getWidth();
			        	float pageHeight = pdfTemplate.getPageSize(pageNumber).getHeight();
			        	
			        	float topMargin = 50;
			        	float leftMargin = 40;
		
				        for (ExpenseFile expenseFile : expense.getFiles()) {
				        	
				        	if (expenseFile.getContent() != null) {

								if (expenseFile.getExt().equalsIgnoreCase("pdf")) {

									byte[] pdfBytes = FileUtils.decompressBytes(expenseFile.getContent());

									InputStream inputStream = new ByteArrayInputStream(pdfBytes);

									PdfReader pdfReader = new PdfReader(inputStream);

									for (int j = 1; j <= pdfReader.getNumberOfPages(); j++) {

										stamper.insertPage(++pageNumber, pdfTemplate.getPageSizeWithRotation(1));

										PdfImportedPage page = stamper.getImportedPage(pdfReader, j);
										PdfContentByte canvas = stamper.getOverContent(pageNumber);

										if (page.getRotation() == 0) {
											canvas.addTemplate(page, 0, -1f, 1f, 0, 0, pageHeight);
										} else if (page.getRotation() == 90) {
											canvas.addTemplate(page, -1f, 0, 0, -1f, pageWidth, pageHeight);
										} else if (page.getRotation() == 180) {
											canvas.addTemplate(page, 0, 1.0F, -1.0F, 0, pageWidth, 0);
										} else if (page.getRotation() == 270) {
											canvas.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
										}
									}

								} else {

									stamper.insertPage(++pageNumber, pdfTemplate.getPageSizeWithRotation(1));

									Image image = Image.getInstance(FileUtils.decompressBytes(expenseFile.getContent()));

									Rectangle maxImageSize = new Rectangle(PageSize.A4.getHeight() - (leftMargin * 2), PageSize.A4.getWidth() - (topMargin * 2));

									if (image.getWidth() > pageWidth || image.getHeight() > pageHeight) {
										image.scaleToFit(maxImageSize);
									}

									image.setAbsolutePosition(leftMargin, (PageSize.A4.getWidth() - image.getScaledHeight()) - topMargin);
									PdfContentByte canvas = stamper.getOverContent(pageNumber);
									canvas.addImage(image);
								}
				        	}
				        }
	        		}
	        	}
	        }
	        
	        stamper.getAcroFields().setGenerateAppearances(true);
	        stamper.setFormFlattening(true);
	        
	        stamper.close();
	        pdfTemplate.close();
	        
	        return baos.toByteArray();
	        
		} catch (IOException | DocumentException e) {
			log.error("Error creating expenses PDF file: ", e);
		}
        
		return null;
	}

	public List<ExpenseSheet> getFilteredExpenseSheetsByStatus(List<ExpenseSheet> expenseSheets, String status) {
		return expenseSheets.stream().filter(expenseSheet -> status.equals(expenseSheet.getStatus())).collect(Collectors.toList());
	}

	public double getTotalPendingAmount(List<ExpenseSheet> expenseSheets) {
		double amount = 0;

		for (ExpenseSheet expenseSheet : expenseSheets) {
			for (Expense expense : expenseSheet.getExpenses()) {	
				amount += expense.getTotal();
			}
		}

		return amount;

	}
	
	public Date getLastExpenseSheetDate (List<ExpenseSheet> expenseSheets) {
		
		if (expenseSheets.isEmpty()) {
			return null;
		}
			
		Collections.sort(expenseSheets, new Comparator<ExpenseSheet>() {
			  public int compare(ExpenseSheet o1, ExpenseSheet o2) {
			      return o1.getCreationDate().compareTo(o2.getCreationDate());
			  }
			});
		
		return expenseSheets.get(expenseSheets.size() - 1).getCreationDate();
	}

	private void generateHeaderInfo(XSSFWorkbook workbook, Sheet sheet, ExpenseSheet expenseSheet, Locale locale) {
		
		// Create rows
		Row numberDateRow = sheet.createRow(1);
		Row titleRow = sheet.createRow(2);
		// Row paymentTypeRow = sheet.createRow(3);
		Row nameRow = sheet.createRow(4);
		Row projectRow = sheet.createRow(6);
		Row adminRow = sheet.createRow(8);
		Row paymentDateRow = sheet.createRow(9);
		
		// First row
		Cell cellNumberDate1 = numberDateRow.createCell(0);
		cellNumberDate1.setCellStyle(ExcelUtils.getCalibriSizeStyle(workbook, 10, false, false));
		cellNumberDate1.setCellValue(messageSource.getMessage("expense.excel.number", new Object[] { expenseSheet.getId() }, locale).toUpperCase());
		
		Cell cellNumberDate2 = numberDateRow.createCell(1);
		cellNumberDate2.setCellStyle(ExcelUtils.getCalibriSizeStyle(workbook, 10, false, false));
		cellNumberDate2.setCellValue(messageSource.getMessage("expense.excel.created", new Object[] { Utiles.getDateFormatted(expenseSheet.getCreationDate()) }, locale));

		// Second row
		
		// Join row cells
		CellRangeAddress rowTitleCRA = new CellRangeAddress(2, 2, 0, 1);
		sheet.addMergedRegion(rowTitleCRA);
		
		Cell cellTitle = titleRow.createCell(0);
		cellTitle.setCellStyle(ExcelUtils.getCalibriSizeStyle(workbook, 14, true, false));
		cellTitle.setCellValue(messageSource.getMessage("expense.excel.title", new Object[] { }, locale).toUpperCase());
		
		// Third row
		
		// Join row cells
		// CellRangeAddress rowPaymentTypeCRA = new CellRangeAddress(3, 3, 9, 11);
		// sheet.addMergedRegion(rowPaymentTypeCRA);
				
		// Cell cellPaymentType = paymentTypeRow.createCell(9);
		// cellPaymentType.setCellStyle(ExcelUtils.getCalibriSizeStyle(workbook, 10, false, true));
		// cellPaymentType.setCellValue(messageSource.getMessage("expense.excel.payment.type", new Object[] { }, locale));

		// Fourth row	
		Cell cellName1 = nameRow.createCell(0);
		cellName1.setCellStyle(ExcelUtils.getCalibriSizeStyle(workbook, 10, true, false));
		cellName1.setCellValue(messageSource.getMessage("expense.excel.name", new Object[] { }, locale));
	
		// Join row cells
		CellRangeAddress rowNameCRA = new CellRangeAddress(4, 4, 1, 6);
		sheet.addMergedRegion(rowNameCRA);
				
		ExcelUtils.setWhiteStyle(workbook, nameRow, HorizontalAlignment.CENTER, 1, 6);
		Cell cellName2 = nameRow.getCell(1);
		cellName2.setCellValue((expenseSheet.getUser().getName() + " " + expenseSheet.getUser().getSurnames()).toUpperCase());
				
		// Join row cells
		// CellRangeAddress rowName2CRA = new CellRangeAddress(4, 4, 9, 11);
		// sheet.addMergedRegion(rowName2CRA);
				
		// ExcelUtils.getLightBlueStyleWithBorders(workbook, nameRow, 9, 11);
		// Cell cellName3 = nameRow.getCell(9);
		// cellName3.setCellValue(messageSource.getMessage("expense.excel.payment.method", new Object[] { }, locale).toUpperCase());
		
		// Sixth row
		Cell cellProject1 = projectRow.createCell(0);
		cellProject1.setCellStyle(ExcelUtils.getCalibriSizeStyle(workbook, 10, true, false));
		cellProject1.setCellValue(messageSource.getMessage("expense.excel.project", new Object[] { }, locale));
	
		// Join row cells
		CellRangeAddress rowProjectCRA = new CellRangeAddress(6, 6, 1, 6);
		sheet.addMergedRegion(rowProjectCRA);
				
		ExcelUtils.setWhiteStyle(workbook, projectRow, HorizontalAlignment.CENTER, 1, 6);
		Cell cellProject2 = projectRow.getCell(1);
		cellProject2.setCellValue((expenseSheet.getProject().getName()).toUpperCase());		
		
		// Eighth row	
		
		// Join row cells
		CellRangeAddress adminRowCRA = new CellRangeAddress(8, 8, 1, 3);
		sheet.addMergedRegion(adminRowCRA);
		
		ExcelUtils.setWhiteStyleTop(workbook, adminRow, HorizontalAlignment.CENTER, false, true, 1, 3);
		Cell cellAdmin = adminRow.getCell(1);
		cellAdmin.setCellValue(messageSource.getMessage("expense.excel.admin.complete", new Object[] { }, locale));
		
		// Ninth row	
		
		// Join row cells
		CellRangeAddress paymentDateRowCRA = new CellRangeAddress(9, 9, 2, 3);
		sheet.addMergedRegion(paymentDateRowCRA);
		
		ExcelUtils.setWhiteStyleBottom(workbook, paymentDateRow, HorizontalAlignment.CENTER, true, false, 1, 1);
		Cell cellPaymentDate1 = paymentDateRow.getCell(1);
		cellPaymentDate1.setCellValue(messageSource.getMessage("expense.excel.payed.date", new Object[] { }, locale));
		
		ExcelUtils.setWhiteStyle(workbook, paymentDateRow, HorizontalAlignment.CENTER, 2, 3);
		
		// Twelfth row	
		
		// Join row cells
		CellRangeAddress expensesFreeRowCRA = new CellRangeAddress(12, 13, 5, 7);
		sheet.addMergedRegion(expensesFreeRowCRA);
		
		ExcelUtils.setWhiteStyleLRMultiRows(workbook, sheet, 12, 2, HorizontalAlignment.CENTER, VerticalAlignment.BOTTOM, true, false, 5, 7);
		Row expenseFreeRow = sheet.getRow(12);
		Cell cellExpensesFree = expenseFreeRow.getCell(5);
		cellExpensesFree.setCellValue(messageSource.getMessage("expense.excel.expenses.ticket", new Object[] { }, locale));
	}
	
	private void generateExpensesInfo(XSSFWorkbook workbook, Sheet sheet, ExpenseSheet expenseSheet, Locale locale) {
		
		// Create rows
		int rowCount = 14;
		
		Row headerRow = sheet.createRow(rowCount++);
				
		// Header Styles
		CellStyle headerLeftStyle = ExcelUtils.getStyle(workbook, null, null, false, false, false, false, null, null, IndexedColors.LIGHT_TURQUOISE.getIndex(), null, 10, true, false);
		CellStyle headerCenterStyle = ExcelUtils.getStyle(workbook, HorizontalAlignment.CENTER, null, false, false, false, false, null, null, IndexedColors.LIGHT_TURQUOISE.getIndex(), null, 10, true, false);

		// Generating Headers
		for (int i = 0; i <= 8; i++) {
			
			Cell cell = headerRow.createCell(i);
			
			if (i == 0) {
				cell.setCellStyle(headerLeftStyle);
			} else {
				cell.setCellStyle(headerCenterStyle);
			}
			
			cell.setCellValue(messageSource.getMessage("expense.excel.info." + (i + 1), new Object[] { }, locale));
		}
		
		// Expense Info
		if (expenseSheet.getExpenses() != null && !expenseSheet.getExpenses().isEmpty()) {

			List<PriceType> priceTypes = singletonUtil.getPriceTypes();
			PriceType kmPriceType = priceTypes.stream().filter(p -> "price.type.4".equals(p.getName())).collect(Collectors.toList()).get(0);
			double kmPrice = kmPriceType.getAmount();
			
			double kmSum = 0;
			double totalSum = 0;
			
			for (Expense expense : expenseSheet.getExpenses()) {
				
				Row row = sheet.createRow(rowCount++);
				int columnCount = 0;
				
				CellStyle leftStyle = ExcelUtils.getStyle(workbook, null, null, false, false, false, false, null, null, null, null, 10, false, false);
				CellStyle centerStyle = ExcelUtils.getStyle(workbook, HorizontalAlignment.CENTER, null, false, false, false, false, null, null, null, null, 10, false, false);
				CellStyle rightStyle = ExcelUtils.getStyle(workbook, HorizontalAlignment.RIGHT, null, false, false, false, false, null, null, null, null, 10, false, false);
				CellStyle codStyle = ExcelUtils.getStyle(workbook, null, null, true, true, true, true, BorderStyle.THIN, null, IndexedColors.GREY_50_PERCENT.getIndex(), null, 10, false, false);
				
				// Date
				Cell dateCell = row.createCell(columnCount++);
				dateCell.setCellStyle(leftStyle);
				dateCell.setCellValue(Utiles.getDateFormatted(expense.getDate()));
				
				// Description
				Cell descriptionCell = row.createCell(columnCount++);
				descriptionCell.setCellStyle(leftStyle);
				descriptionCell.setCellValue(expense.getJustification());
				
				// Metodo de pago
				Cell paymentTypeCell = row.createCell(columnCount++);
				paymentTypeCell.setCellStyle(centerStyle);
				paymentTypeCell.setCellValue(messageSource.getMessage(expense.getPaymentType().getName(), new Object[] { }, locale));
				
				// Cod
				Cell codCell = row.createCell(columnCount++);
				codCell.setCellStyle(codStyle);
				
				// Kms
				Cell kmsCell = row.createCell(columnCount++);
				kmsCell.setCellStyle(centerStyle);
				if (expense.getPriceType().getId() == 4) {
					kmsCell.setCellValue(expense.getTotal() / kmPrice);
				}
				
				// EUR/Kms
				Cell eurKmsCell = row.createCell(columnCount++);
				eurKmsCell.setCellStyle(rightStyle);
				if (expense.getPriceType().getId() == 4) {
					eurKmsCell.setCellValue(kmPrice);
					kmSum += kmPrice;
				}
				
				// Without IVA
				Cell withIvaCell = row.createCell(columnCount++);
				withIvaCell.setCellStyle(rightStyle);
				
				// With IVA
				Cell withoutIvaCell = row.createCell(columnCount++);
				withoutIvaCell.setCellStyle(rightStyle);
				withoutIvaCell.setCellValue(expense.getTotal());
				totalSum += expense.getTotal();
				
				// Total
				Cell totalCell = row.createCell(columnCount++);
				totalCell.setCellStyle(rightStyle);
				totalCell.setCellValue(expense.getTotal());
			}
			
			// Expenses Sum
			
			Row sumRow = sheet.createRow(rowCount++);
			int columnCount = 0;
			
			CellStyle sumLeftStyle = ExcelUtils.getStyle(workbook, null, null, false, false, false, false, null, null, IndexedColors.GREY_40_PERCENT.getIndex(), null, 10, false, false);
			CellStyle sumRightStyle = ExcelUtils.getStyle(workbook, HorizontalAlignment.RIGHT, null, false, false, false, false, null, null, IndexedColors.GREY_40_PERCENT.getIndex(), null, 10, false, false);
			CellStyle sumCenterStyle = ExcelUtils.getStyle(workbook, HorizontalAlignment.CENTER, null, false, false, false, false, null, null, IndexedColors.GREY_40_PERCENT.getIndex(), null, 10, false, false);
			CellStyle sumRightDarkStyle = ExcelUtils.getStyle(workbook, HorizontalAlignment.RIGHT, null, false, false, false, false, null, null, IndexedColors.GREY_80_PERCENT.getIndex(), null, 10, false, false);

			// Date
			Cell dateCell = sumRow.createCell(columnCount++);
			dateCell.setCellStyle(sumLeftStyle);
			
			// Description
			Cell descriptionCell = sumRow.createCell(columnCount++);
			descriptionCell.setCellStyle(sumLeftStyle);
			
			// Description
			Cell paymentTypeCell = sumRow.createCell(columnCount++);
			paymentTypeCell.setCellStyle(sumLeftStyle);
			
			// Cod
			Cell codCell = sumRow.createCell(columnCount++);
			codCell.setCellStyle(sumCenterStyle);
			
			// Kms
			Cell kmsCell = sumRow.createCell(columnCount++);
			kmsCell.setCellStyle(sumCenterStyle);
			kmsCell.setCellValue(kmSum);
			
			// EUR/Kms
			Cell eurKmsCell = sumRow.createCell(columnCount++);
			eurKmsCell.setCellStyle(sumRightStyle);
			
			// Without IVA
			Cell withIvaCell = sumRow.createCell(columnCount++);
			withIvaCell.setCellStyle(sumRightStyle);
			
			// With IVA
			Cell withoutIvaCell = sumRow.createCell(columnCount++);
			withoutIvaCell.setCellStyle(sumRightStyle);
			withoutIvaCell.setCellValue(totalSum);
			
			// Total
			Cell totalCell = sumRow.createCell(columnCount);
			totalCell.setCellStyle(sumRightDarkStyle);
			
			// Details
			
			Row subTotalRow = sheet.createRow(rowCount++);
			Row advancesRow = sheet.createRow(rowCount++);
			Row totalRow = sheet.createRow(rowCount++);
			Row cashRow = sheet.createRow(rowCount++);
			Row transferRow = sheet.createRow(rowCount++);
			
			CellStyle defaultCenterStyle = ExcelUtils.getStyle(workbook, HorizontalAlignment.CENTER, null, false, false, false, false, null, null, null, null, 10, false, false);
			CellStyle defaultRightStyle = ExcelUtils.getStyle(workbook, HorizontalAlignment.RIGHT, null, false, false, false, false, null, null, null, null, 10, false, false);
			CellStyle defaultRightBoldStyle = ExcelUtils.getStyle(workbook, HorizontalAlignment.RIGHT, null, false, false, false, false, null, null, null, null, 10, true, false);
			
			CellStyle borderBottomBoldStyle = ExcelUtils.getStyle(workbook, null, null, false, true, false, false, BorderStyle.MEDIUM, null, null, null, 10, true, false);
			CellStyle centerBorderBottomStyle = ExcelUtils.getStyle(workbook, HorizontalAlignment.CENTER, null, false, true, false, false, BorderStyle.MEDIUM, null, null, null, 10, false, false);
			CellStyle centerBorderBottomBoldStyle = ExcelUtils.getStyle(workbook, HorizontalAlignment.CENTER, null, false, true, false, false, BorderStyle.MEDIUM, null, null, null, 10, true, false);

			CellStyle borderGrayRightStyle = ExcelUtils.getStyle(workbook, HorizontalAlignment.RIGHT, null, true, true, true, true, BorderStyle.THIN, null, IndexedColors.GREY_40_PERCENT.getIndex(), null, 10, false, false);

			// SubTotal Row
			
			// Subtotal Cell
			Cell subTotalCell = subTotalRow.createCell(6);
			subTotalCell.setCellStyle(defaultRightStyle);
			subTotalCell.setCellValue(messageSource.getMessage("expense.excel.subtotal", new Object[] { }, locale));
			
			// Subtotal Cell
			Cell stTotalCell = subTotalRow.createCell(8);
			stTotalCell.setCellStyle(sumRightStyle);
			stTotalCell.setCellValue(totalSum);
			
			// Advances Row
			
			// Aproved Cell
			Cell aprovedCell = advancesRow.createCell(0);
			aprovedCell.setCellStyle(borderBottomBoldStyle);
			aprovedCell.setCellValue(messageSource.getMessage(expenseSheet.getStatus(), new Object[] { }, locale));

			// Bank Cell
			Cell bankCell = advancesRow.createCell(1);
			bankCell.setCellStyle(centerBorderBottomBoldStyle);
			bankCell.setCellValue(messageSource.getMessage("expense.excel.bank", new Object[] { }, locale));
			
			// Extra
			Cell extraCell1 = advancesRow.createCell(2);
			extraCell1.setCellStyle(centerBorderBottomBoldStyle);
			
			// Aproved Cell
			Cell advancesCell = advancesRow.createCell(6);
			advancesCell.setCellStyle(defaultRightStyle);
			advancesCell.setCellValue(messageSource.getMessage("expense.excel.advancement", new Object[] { }, locale));

			// Total Row
			
			// Heel Cell
			Cell heelCell = totalRow.createCell(1);
			heelCell.setCellStyle(centerBorderBottomStyle);
			heelCell.setCellValue(messageSource.getMessage("expense.excel.heel", new Object[] { }, locale));
			
			// Extra
			Cell extraCell2 = totalRow.createCell(2);
			extraCell2.setCellStyle(centerBorderBottomBoldStyle);
			
			// Total Cell 
			Cell stTotalCell2 = totalRow.createCell(6);
			stTotalCell2.setCellStyle(defaultRightBoldStyle);
			stTotalCell2.setCellValue(messageSource.getMessage("expense.excel.total", new Object[] { }, locale).toUpperCase());

			// Total Cell 
			Cell stTotalCell3 = totalRow.createCell(8);
			stTotalCell3.setCellStyle(borderGrayRightStyle);
			stTotalCell3.setCellValue(totalSum);
			
			// Cash Row
			
			// Cash Cell
			Cell cashCell = cashRow.createCell(1);
			cashCell.setCellStyle(defaultCenterStyle);
			cashCell.setCellValue(messageSource.getMessage("expense.excel.cash", new Object[] { }, locale));
			
			// Transfer Row
			
			// Transfer Cell
			Cell transferCell = transferRow.createCell(1);
			transferCell.setCellStyle(centerBorderBottomStyle);
			transferCell.setCellValue(messageSource.getMessage("expense.excel.transfer", new Object[] { }, locale));
						
			// Extra
			Cell extraCell3 = transferRow.createCell(2);
			extraCell3.setCellStyle(centerBorderBottomBoldStyle);
		}
		
		// Generate Footer
		generateFooterInfo(workbook, sheet, rowCount, locale);
	}
	
	private void generateFooterInfo(XSSFWorkbook workbook, Sheet sheet, int rowCount, Locale locale) {

		// Rows
		rowCount += 2;
		Row hrRow = sheet.createRow(rowCount);
		
		// Styles
		CellStyle bottomThickBorderStyle = ExcelUtils.getStyle(workbook, null, null, false, true, false, false, BorderStyle.THICK, null, null, null, 10, false, false);
		CellStyle uniqueStyle = ExcelUtils.getStyle(workbook, null, VerticalAlignment.BOTTOM, false, false, false, false, null, null, IndexedColors.GREY_50_PERCENT.getIndex(), null, 10, false, false);

		for (int i = 0; i <= 7; i++) {
			
			// Extra
			Cell extraCell3 = hrRow.createCell(i);
			extraCell3.setCellStyle(bottomThickBorderStyle);
		}
		
		// Rows
		rowCount += 3;
		Row uniqueRow = sheet.createRow(rowCount);
		
		// Join row cells
		CellRangeAddress uniqueRowCRA = new CellRangeAddress(rowCount, rowCount + 2, 0, 2);
		sheet.addMergedRegion(uniqueRowCRA);
		
		// Unique
		Cell uniqueCell = uniqueRow.createCell(0);
		uniqueCell.setCellStyle(uniqueStyle);
		uniqueCell.setCellValue(messageSource.getMessage("expense.excel.unique", new Object[] { }, locale));
	}
}
