package com.epm.gestepm.model.project.service;

import com.epm.gestepm.model.displacementshare.dao.DisplacementShareRepository;
import com.epm.gestepm.model.expensesheet.dao.ExpenseSheetRepository;
import com.epm.gestepm.model.interventionprshare.dao.InterventionPrShareRepository;
import com.epm.gestepm.model.project.dao.ProjectRepository;
import com.epm.gestepm.model.subrole.dao.SubRoleRepository;
import com.epm.gestepm.model.user.dao.UserRepository;
import com.epm.gestepm.model.usersigning.dao.UserSigningRepository;
import com.epm.gestepm.model.workshare.dao.WorkShareRepository;
import com.epm.gestepm.modelapi.common.utils.ExcelUtils;
import com.epm.gestepm.modelapi.common.utils.PixelUtils;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.project.dto.ProjectDTO;
import com.epm.gestepm.modelapi.project.dto.ProjectListDTO;
import com.epm.gestepm.modelapi.project.dto.ProjectTableDTO;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.subrole.dto.SubRole;
import com.epm.gestepm.modelapi.user.dto.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

	@Value("${gestepm.displacements.project-ids}")
	private List<Long> displacementProjectIds;

	@Autowired
	private DisplacementShareRepository displacementShareRepository;
	
	@Autowired
	private ExpenseSheetRepository expenseSheetRepository;
	
	@Autowired
	private InterventionPrShareRepository interventionPrShareRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private SubRoleRepository subRoleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserSigningRepository userSigningRepository;
	
	@Autowired
	private WorkShareRepository workShareRepository;
	 
	@Override
	public Project getProjectById(Long projectId) {
		return projectRepository.findById(projectId).orElse(null);
	}

	@Override
	public List<Project> getAllProjects() {
		return (List<Project>) projectRepository.findAll();
	}

	@Override
	public List<Project> findDisplacementProjects() {
		// FIXME
		final List<Project> displacementProjects = new ArrayList<>();
		this.displacementProjectIds.forEach(id -> {
			displacementProjects.add(this.getProjectById(id));
		});
		return displacementProjects;
	}

	@Override
	public Project getProjectByIdAndUserId(Long projectId, Long userid) {
		return projectRepository.findByIdAndUserId(projectId, userid);
	}
	
	@Override
	public Project getProjectByIdAndBossId(Long projectId, Long bossId) {
		return projectRepository.findByIdAndBossId(projectId, bossId);
	}
	
	@Override
	public Project save(Project project) {
		return projectRepository.save(project);
	}
	
	@Override
	public void delete(Long id) {
		projectRepository.deleteById(id);
	}
	
	@Override
	public List<ProjectListDTO> getAllProjectsDTOs() {
		return projectRepository.findAllProjectsDTOs();
	}
	
	@Override
	public List<ProjectListDTO> getProjectsDTOByUserId(Long userId) {
		return projectRepository.findProjectsDTOByUserId(userId);
	}
	
	@Override
	public List<ProjectListDTO> getBossProjectsDTOByUserId(Long userId) {
		return projectRepository.findBossProjectsDTOByUserId(userId);
	}
	
	@Override
	public List<ProjectListDTO> getStationDTOs() {
		return projectRepository.findStationDTOs();
	}
	
	@Override
	public List<ProjectTableDTO> getProjectsByUserMemberDataTables(Long userId, PaginationCriteria pagination, Object[] params) {
		return projectRepository.findProjectsByUserMemberDataTables(userId, pagination, params);
	}
	
	@Override
	public List<ProjectTableDTO> getProjectsByUserBossDataTables(Long userId, PaginationCriteria pagination, Object[] params) {
		return projectRepository.findProjectsByUserBossDataTables(userId, pagination, params);
	}
	
	@Override
	public List<ProjectTableDTO> getAllProjectsDataTables(PaginationCriteria pagination, Object[] params) {
		return projectRepository.findAllProjectsDataTables(pagination, params);
	}
	
	@Override
	public Long getProjectsCountByUserMember(Long userId, Object[] params) {
		return projectRepository.findProjectsCountByUserMember(userId, params);
	}
	
	@Override
	public Long getProjectsCountByUserBoss(Long userId, Object[] params) {
		return projectRepository.findProjectsCountByUserBoss(userId, params);
	}
	
	@Override
	public List<ProjectDTO> getNotProjectDTOsByUserId(Long userId) {
		return projectRepository.findNotProjectDTOsByUserId(userId);
	}
	
	@Override
	public Long getAllProjectsCount(Object[] params) {
		return projectRepository.findAllProjectsCount(params);
	}
	
	@Override
	public void createMember(Long projectId, Long userId) {
		projectRepository.createMember(projectId, userId);
	}
	
	@Override
	public void deleteMember(Long projectId, Long userId) {
		projectRepository.deleteMember(projectId, userId);
	}
	
	@Override
	public void createUserBoss(Long projectId, Long userId) {
		projectRepository.createUserBoss(projectId, userId);
	}
	
	@Override
	public void deleteUserBoss(Long projectId, Long userId) {
		projectRepository.deleteUserBoss(projectId, userId);
	}
	
	@Override
	public void deleteAllUserBossByUserId(Long userId) {
		projectRepository.deleteAllUserBossByUserId(userId);
	}
	
	@Override
	public XSSFWorkbook generateProjectExcel(Long projectId, Long userId, Project project, Integer year, Locale locale) {
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		Sheet sheet = workbook.createSheet(messageSource.getMessage("project.excel.sheet.1", new Object[] {}, locale));
		
		// Columns Width
		setColumnsWidth(sheet);
		
		// Header
		generateHeader(workbook, sheet, locale);
		
		// Row Space
		generateRowSpace(sheet, 2);
		
		// Main Project Info
		generateMainInfo(workbook, sheet, project, locale);
		
		// Row Space
		generateRowSpace(sheet, 8);
		
		// Date
		generateDateInfo(year, workbook, sheet, locale);
		
		// Row Space
		generateRowSpace(sheet, 10);
		
		// Expenses Title
		generateExpensesTitle(workbook, sheet, 11, "project.excel.expenses.title", locale);
		
		// Row Space
		generateRowSpace(sheet, 12);
		
		// Personal Expenses
		generateLightGreenTitle(workbook, sheet, locale, "project.excel.personal.expenses.title", 13);
		
		// Row Space
		generateRowSpace(sheet, 14);
		
		int variableIndex = 15;
		
		// Member Expense Month 
		List<ExpensesMonthDTO> expensesMonthList = expenseSheetRepository.findExpensesMonthDTOByProjectId(projectId, year);
		Double totalExpenses = expenseSheetRepository.findTotalYearExpensesByProjectId(projectId, year);
		
		List<ExpensesMonthDTO> workShareMonthList = workShareRepository.findExpensesMonthDTOByProjectId(projectId, year);
		
		if (!workShareMonthList.isEmpty()) {
			
			for (ExpensesMonthDTO workExpenseDTO : workShareMonthList) {
				
				long expenseUserId = workExpenseDTO.getUserId();
				boolean existsInList = false;
				
				SubRole subRole = subRoleRepository.findByUserId(expenseUserId);
				
				for (ExpensesMonthDTO expenseMonthDTO : expensesMonthList) {
					
					if (expenseMonthDTO.getUserId().equals(expenseUserId)) {
						existsInList = true;

						if (subRole != null) {
							
							double workTotalExpense = workExpenseDTO.transformTimeToValueAndUpdate(subRole.getPrice(), expenseMonthDTO);
							totalExpenses += workTotalExpense;
						}
					}
				}
				
				if (!existsInList && subRole != null) {

					double workTotalExpense = workExpenseDTO.transformTimeToValue(subRole.getPrice());
					totalExpenses += workTotalExpense;
					expensesMonthList.add(workExpenseDTO);
				}
			}
		}
		
		List<ExpensesMonthDTO> interventionPrShareMonthList = interventionPrShareRepository.findExpensesMonthDTOByProjectId(projectId, year);
		
		if (!interventionPrShareMonthList.isEmpty()) {
			
			for (ExpensesMonthDTO interventionPrExpenseDTO : interventionPrShareMonthList) {
				
				long expenseUserId = interventionPrExpenseDTO.getUserId();
				boolean existsInList = false;
				
				SubRole subRole = subRoleRepository.findByUserId(expenseUserId);
				
				for (ExpensesMonthDTO expenseMonthDTO : expensesMonthList) {
					
					if (expenseMonthDTO.getUserId().equals(expenseUserId)) {
						existsInList = true;

						if (subRole != null) {
							
							double interventionPrTotalExpense = interventionPrExpenseDTO.transformTimeToValueAndUpdate(subRole.getPrice(), expenseMonthDTO);
							totalExpenses += interventionPrTotalExpense;
						}
					}
				}
				
				if (!existsInList && subRole != null) {

					double interventionPrTotalExpense = interventionPrExpenseDTO.transformTimeToValue(subRole.getPrice());
					totalExpenses += interventionPrTotalExpense;
					expensesMonthList.add(interventionPrExpenseDTO);
				}
			}
		}
		
		if (totalExpenses != null) {
			
			int i = 0;
			for (ExpensesMonthDTO expenseMonthDTO : expensesMonthList) {
				generateMemberDateInfo(expenseMonthDTO, totalExpenses, workbook, sheet, variableIndex++, i == 0, i == expensesMonthList.size() - 1);
				i++;
			}
		}
		
		// Row Space
		generateRowSpace(sheet, variableIndex++);
		
		// Personal Expenses
		generateLightGreenTitle(workbook, sheet, locale, "project.excel.other.expenses.title", variableIndex++);

		// Row Space
		generateRowSpace(sheet, variableIndex++);
				
		// Hours Title
		generateExpensesTitle(workbook, sheet, variableIndex++, "project.excel.hours.title", locale);
		
		// Row Space
		generateRowSpace(sheet, variableIndex++);

		// Personal Expenses
		generateLightGreenTitle(workbook, sheet, locale, "project.excel.personal.hours.title", variableIndex++);

		// Row Space
		generateRowSpace(sheet, variableIndex++);

		final List<ExpensesMonthDTO> userSigningMonthsByProjectList = userSigningRepository.findExpensesMonthDTOByProjectId(projectId, year);
		final List<ExpensesMonthDTO> displacementTimesByProjectList = displacementShareRepository.findTimeMonthDTOByProjectId(projectId, year);

		final List<ExpensesMonthDTO> totalHoursList = joinExpensesDTOList(userSigningMonthsByProjectList, displacementTimesByProjectList);

		if (!totalHoursList.isEmpty()) {

			int i = 0;

			final Double totalUserTime = getTotalExpenseList(totalHoursList);

			for (ExpensesMonthDTO userSigningExpenseDTO : totalHoursList) {
				generateMemberTimeDateInfo(userSigningExpenseDTO, totalUserTime, workbook, sheet, variableIndex++, i == 0, i == totalHoursList.size() - 1);
				i++;
			}
		}
	
		// Row Space
		generateRowSpace(sheet, variableIndex++);
				
		generateWhiteTitle(workbook, sheet, locale, "project.excel.hitos", variableIndex++);
		
		return workbook;
	}
	
	private void setColumnsWidth(Sheet sheet) {
		
		// First A width
		sheet.setColumnWidth(0, PixelUtils.pixel2WidthUnits(15));
		
		// Column B Width
		sheet.setColumnWidth(1, PixelUtils.pixel2WidthUnits(200));
	}
	
	private void generateHeader(XSSFWorkbook workbook, Sheet sheet, Locale locale) {
		
		// Create header row
		Row headerRow = sheet.createRow(1);
		
		// Join all header cells
		CellRangeAddress headerCRA = new CellRangeAddress(1, 1, 1, 15);
		sheet.addMergedRegion(headerCRA);
		
		// Styling header cells
		Cell headerCell = CellUtil.createCell(headerRow, 1, messageSource.getMessage("project.excel.title", new Object[] {}, locale).toUpperCase());
		ExcelUtils.setHeaderStyle(workbook, headerRow, 16);
		CellUtil.setAlignment(headerCell, HorizontalAlignment.CENTER);
	}
	
	private void generateRowSpace(Sheet sheet, int rowNumber) {
		
		Row rowSpace = sheet.createRow(rowNumber);
		rowSpace.setHeightInPoints((short) 5);
	}
	
	private void generateMainInfo(XSSFWorkbook workbook, Sheet sheet, Project project, Locale locale) {
		
		// Create rows
		Row projectNameRow = sheet.createRow(3);
		Row responsableRow = sheet.createRow(4);
		Row objCostRow = sheet.createRow(5);
		Row startDateRow = sheet.createRow(6);
		Row objDateRow = sheet.createRow(7);
		
		// Join row 4 cells
		CellRangeAddress rowFourCRA = new CellRangeAddress(3, 3, 2, 15);
		sheet.addMergedRegion(rowFourCRA);
				
		// Styling row 4 cell
		ExcelUtils.setMainStyle(workbook, projectNameRow, 16);
		projectNameRow.getCell(1).setCellValue(messageSource.getMessage("project.excel.main.np", new Object[] {}, locale) + ":");
		projectNameRow.getCell(2).setCellValue(project.getName());
		
		// Join row 5 cells
		CellRangeAddress rowFiveCRA = new CellRangeAddress(4, 4, 2, 15);
		sheet.addMergedRegion(rowFiveCRA);

		// Styling row 5 cell
		ExcelUtils.setMainStyle(workbook, responsableRow, 16);
		responsableRow.getCell(1).setCellValue(messageSource.getMessage("project.excel.main.rp", new Object[] {}, locale) + ":");
		responsableRow.getCell(2).setCellValue(getResponsablesToList(project.getResponsables()));
		
		// Join row 6 cells
		CellRangeAddress rowSixCRA = new CellRangeAddress(5, 5, 2, 15);
		sheet.addMergedRegion(rowSixCRA);

		// Styling row 6 cell
		ExcelUtils.setMainStyle(workbook, objCostRow, 16);
		objCostRow.getCell(1).setCellValue(messageSource.getMessage("project.excel.main.co", new Object[] {}, locale) + ":");
		objCostRow.getCell(2).setCellValue(project.getObjectiveCost());
		
		// Join row 7 cells
		CellRangeAddress rowSevenCRA = new CellRangeAddress(6, 6, 2, 15);
		sheet.addMergedRegion(rowSevenCRA);

		// Styling row 7 cell
		ExcelUtils.setMainStyle(workbook, startDateRow, 16);
		startDateRow.getCell(1).setCellValue(messageSource.getMessage("project.excel.main.fi", new Object[] {}, locale) + ":");
		startDateRow.getCell(2).setCellValue(Utiles.getDateFormatted(project.getStartDate()));
		
		// Join row 8 cells
		CellRangeAddress rowEightCRA = new CellRangeAddress(7, 7, 2, 15);
		sheet.addMergedRegion(rowEightCRA);

		// Styling row 8 cell
		ExcelUtils.setMainStyle(workbook, objDateRow, 16);
		objDateRow.getCell(1).setCellValue(messageSource.getMessage("project.excel.main.fo", new Object[] {}, locale) + ":");
		objDateRow.getCell(2).setCellValue(Utiles.getDateFormatted(project.getObjectiveDate()));
	}
	
	private void generateDateInfo(Integer year, XSSFWorkbook workbook, Sheet sheet, Locale locale) {
		
		// Create header row
		Row dateRow = sheet.createRow(9);
		
		// Blue cell
		Cell blueCell = dateRow.createCell(1);
		CellStyle blueCellStyle = ExcelUtils.getStyle(workbook, HorizontalAlignment.CENTER, null, true, true, true, true, BorderStyle.MEDIUM, null, IndexedColors.LIGHT_TURQUOISE.getIndex(), null, 11, true, false);
		blueCell.setCellStyle(blueCellStyle);
		
		// Month cell
		for (int i = 2; i < 14; i++) {

			Cell monthCell = dateRow.createCell(i);
			monthCell.setCellStyle(ExcelUtils.getGreenMonthStyle(workbook));

			dateRow.getCell(i).setCellValue(Utiles.getDateAsText(i - 1, year, locale, messageSource));
		}
		
		// Blue cell
		Cell totalCell = dateRow.createCell(14);
		totalCell.setCellStyle(blueCellStyle);
		dateRow.getCell(14).setCellValue(messageSource.getMessage("project.excel.total", new Object[] {}, locale).toUpperCase());
		
		// Blue cell
		Cell percentCell = dateRow.createCell(15);
		percentCell.setCellStyle(blueCellStyle);
		dateRow.getCell(15).setCellValue("%");
		
		// Columns C - N Width
		for (int i = 2; i < 14; i++) {
			sheet.autoSizeColumn(i);
		}
	}
	
	private void generateExpensesTitle(XSSFWorkbook workbook, Sheet sheet, int rowIndex, String message, Locale locale) {
		
		// Create header row
		Row titleRow = sheet.createRow(rowIndex);
		
		// Join row cells
		CellRangeAddress rowCRA = new CellRangeAddress(rowIndex, rowIndex, 1, 15);
		sheet.addMergedRegion(rowCRA);
		
		for (int i = 1; i < 16; i++) {
			ExcelUtils.getExpensesTitleStyle(workbook, titleRow, i);
		}
		
		titleRow.getCell(1).setCellValue(messageSource.getMessage(message, new Object[] {}, locale).toUpperCase());
	}
	
	private void generateLightGreenTitle(XSSFWorkbook workbook, Sheet sheet, Locale locale, String message, int rowIndex) {

		// Create header row
		Row titleRow = sheet.createRow(rowIndex);
				
		// Join row cells
		CellRangeAddress rowCRA = new CellRangeAddress(rowIndex, rowIndex, 1, 15);
		sheet.addMergedRegion(rowCRA);
		
		for (int i = 1; i < 16; i++) {
			ExcelUtils.getLightGreenTitleStyle(workbook, titleRow, i);
		}
		
		titleRow.getCell(1).setCellValue(messageSource.getMessage(message, new Object[] {}, locale).toUpperCase());
	}
	
	private void generateWhiteTitle(XSSFWorkbook workbook, Sheet sheet, Locale locale, String message, int rowIndex) {

		// Create header row
		Row titleRow = sheet.createRow(rowIndex);
				
		// Join row cells
		CellRangeAddress rowCRA = new CellRangeAddress(rowIndex, rowIndex, 1, 15);
		sheet.addMergedRegion(rowCRA);
		
		for (int i = 1; i < 16; i++) {
			ExcelUtils.getWhiteTitleStyle(workbook, titleRow, i);
		}
		
		titleRow.getCell(1).setCellValue(messageSource.getMessage(message, new Object[] {}, locale).toUpperCase());
	}
	
	private void generateMemberDateInfo(ExpensesMonthDTO expensesMonth, double totalExpenses, XSSFWorkbook workbook, Sheet sheet, int rowIndex, boolean isFirst, boolean isLast) {
		
		// Create header row
		Row dateRow = sheet.createRow(rowIndex);
		
		// Blue cell
		Cell blueCell = dateRow.createCell(1);
		
		if (rowIndex % 2 != 0) {
			blueCell.setCellStyle(ExcelUtils.getLightYellowStyle(workbook, HorizontalAlignment.LEFT, isFirst, isLast));
		} else {
			blueCell.setCellStyle(ExcelUtils.getWhiteStyle(workbook, HorizontalAlignment.LEFT, isFirst, isLast));
		}
		
		String fullName = userRepository.findFullNameById(expensesMonth.getUserId());
		dateRow.getCell(1).setCellValue(fullName);
		
		// Month cell
		for (int i = 2; i < 16; i++) {
			Cell monthCell = dateRow.createCell(i);
			
			if (rowIndex % 2 != 0) {
				monthCell.setCellStyle(ExcelUtils.getLightYellowStyle(workbook, HorizontalAlignment.CENTER, isFirst, isLast));
			} else {
				monthCell.setCellStyle(ExcelUtils.getWhiteStyle(workbook, HorizontalAlignment.CENTER, isFirst, isLast));
			}
			
			switch (i) {
			
				case 2:
					dateRow.getCell(i).setCellValue((expensesMonth.getJanTotal() == null ? "0.00" : (Math.round(expensesMonth.getJanTotal() * 100.0) / 100.0)).toString() + '€');
					break;
					
				case 3:
					dateRow.getCell(i).setCellValue((expensesMonth.getFebTotal() == null ? "0.00" : (Math.round(expensesMonth.getFebTotal() * 100.0) / 100.0)).toString() + '€');
					break;
					
				case 4:
					dateRow.getCell(i).setCellValue((expensesMonth.getMarTotal() == null ? "0.00" : (Math.round(expensesMonth.getMarTotal() * 100.0) / 100.0)).toString() + '€');
					break;
					
				case 5:
					dateRow.getCell(i).setCellValue((expensesMonth.getAprTotal() == null ? "0.00" : (Math.round(expensesMonth.getAprTotal() * 100.0) / 100.0)).toString() + '€');
					break;
					
				case 6:
					dateRow.getCell(i).setCellValue((expensesMonth.getMayTotal() == null ? "0.00" : (Math.round(expensesMonth.getMayTotal() * 100.0) / 100.0)).toString() + '€');
					break;
					
				case 7:
					dateRow.getCell(i).setCellValue((expensesMonth.getJunTotal() == null ? "0.00" : (Math.round(expensesMonth.getJunTotal() * 100.0) / 100.0)).toString() + '€');
					break;
					
				case 8:
					dateRow.getCell(i).setCellValue((expensesMonth.getJulTotal() == null ? "0.00" : (Math.round(expensesMonth.getJulTotal() * 100.0) / 100.0)).toString() + '€');
					break;
					
				case 9:
					dateRow.getCell(i).setCellValue((expensesMonth.getAugTotal() == null ? "0.00" : (Math.round(expensesMonth.getAugTotal() * 100.0) / 100.0)).toString() + '€');
					break;
					
				case 10:
					dateRow.getCell(i).setCellValue((expensesMonth.getSepTotal() == null ? "0.00" : (Math.round(expensesMonth.getSepTotal() * 100.0) / 100.0)).toString() + '€');
					break;
					
				case 11:
					dateRow.getCell(i).setCellValue((expensesMonth.getOctTotal() == null ? "0.00" : (Math.round(expensesMonth.getOctTotal() * 100.0) / 100.0)).toString() + '€');
					break;
					
				case 12:
					dateRow.getCell(i).setCellValue((expensesMonth.getNovTotal() == null ? "0.00" : (Math.round(expensesMonth.getNovTotal() * 100.0) / 100.0)).toString() + '€');
					break;
					
				case 13:
					dateRow.getCell(i).setCellValue((expensesMonth.getDecTotal() == null ? "0.00" : (Math.round(expensesMonth.getDecTotal() * 100.0) / 100.0)).toString() + '€');
					break;
					
				case 14:
					dateRow.getCell(i).setCellValue(String.valueOf(DoubleRounder.round(getTotalExpenses(expensesMonth), 2)) + '€');
					break;
					
				case 15:
					dateRow.getCell(i).setCellValue(String.valueOf(DoubleRounder.round(((getTotalExpenses(expensesMonth) * 100) / totalExpenses), 2)) + '%');
					break;
					
				default:
					break;
			}
		}
		
		// Columns C - N Width
		for (int i = 2; i < 16; i++) {
			sheet.autoSizeColumn(i);
		}
	}

	private void generateMemberTimeDateInfo(ExpensesMonthDTO expensesMonth, Double totalUserTime, XSSFWorkbook workbook, Sheet sheet, int rowIndex, boolean isFirst, boolean isLast) {

		// Create header row
		Row dateRow = sheet.createRow(rowIndex);

		// Blue cell
		Cell blueCell = dateRow.createCell(1);

		if (rowIndex % 2 != 0) {
			blueCell.setCellStyle(ExcelUtils.getLightYellowStyle(workbook, HorizontalAlignment.LEFT, isFirst, isLast));
		} else {
			blueCell.setCellStyle(ExcelUtils.getWhiteStyle(workbook, HorizontalAlignment.LEFT, isFirst, isLast));
		}

		String fullName = userRepository.findFullNameById(expensesMonth.getUserId());
		dateRow.getCell(1).setCellValue(fullName);

		String timeString = "00:00";
		Double totalTime = 0.0;

		// Month cell
		for (int i = 2; i < 16; i++) {
			Cell monthCell = dateRow.createCell(i);

			if (rowIndex % 2 != 0) {
				monthCell.setCellStyle(ExcelUtils.getLightYellowStyle(workbook, HorizontalAlignment.CENTER, isFirst, isLast));
			} else {
				monthCell.setCellStyle(ExcelUtils.getWhiteStyle(workbook, HorizontalAlignment.CENTER, isFirst, isLast));
			}

			switch (i) {

				case 2:

					timeString = "00:00";

					if (expensesMonth.getJanTotal() != null) {

						totalTime += expensesMonth.getJanTotal();

						int hours = expensesMonth.getJanTotal().intValue() / 3600;
						int minutes = (expensesMonth.getJanTotal().intValue() % 3600) / 60;

						timeString = String.format("%02d:%02d", hours, minutes);
					}

					dateRow.getCell(i).setCellValue(timeString);
					break;

				case 3:

					timeString = "00:00";

					if (expensesMonth.getFebTotal() != null) {

						totalTime += expensesMonth.getFebTotal();

						int hours = expensesMonth.getFebTotal().intValue() / 3600;
						int minutes = (expensesMonth.getFebTotal().intValue() % 3600) / 60;

						timeString = String.format("%02d:%02d", hours, minutes);
					}

					dateRow.getCell(i).setCellValue(timeString);
					break;

				case 4:

					timeString = "00:00";

					if (expensesMonth.getMarTotal() != null) {

						totalTime += expensesMonth.getMarTotal();

						int hours = expensesMonth.getMarTotal().intValue() / 3600;
						int minutes = (expensesMonth.getMarTotal().intValue() % 3600) / 60;

						timeString = String.format("%02d:%02d", hours, minutes);
					}

					dateRow.getCell(i).setCellValue(timeString);
					break;

				case 5:

					timeString = "00:00";

					if (expensesMonth.getAprTotal() != null) {

						totalTime += expensesMonth.getAprTotal();

						int hours = expensesMonth.getAprTotal().intValue() / 3600;
						int minutes = (expensesMonth.getAprTotal().intValue() % 3600) / 60;

						timeString = String.format("%02d:%02d", hours, minutes);
					}

					dateRow.getCell(i).setCellValue(timeString);
					break;

				case 6:

					timeString = "00:00";

					if (expensesMonth.getMayTotal() != null) {

						totalTime += expensesMonth.getMayTotal();

						int hours = expensesMonth.getMayTotal().intValue() / 3600;
						int minutes = (expensesMonth.getMayTotal().intValue() % 3600) / 60;

						timeString = String.format("%02d:%02d", hours, minutes);
					}

					dateRow.getCell(i).setCellValue(timeString);
					break;

				case 7:

					timeString = "00:00";

					if (expensesMonth.getJunTotal() != null) {

						totalTime += expensesMonth.getJunTotal();

						int hours = expensesMonth.getJunTotal().intValue() / 3600;
						int minutes = (expensesMonth.getJunTotal().intValue() % 3600) / 60;

						timeString = String.format("%02d:%02d", hours, minutes);
					}

					dateRow.getCell(i).setCellValue(timeString);
					break;

				case 8:

					timeString = "00:00";

					if (expensesMonth.getJulTotal() != null) {

						totalTime += expensesMonth.getJulTotal();

						int hours = expensesMonth.getJulTotal().intValue() / 3600;
						int minutes = (expensesMonth.getJulTotal().intValue() % 3600) / 60;

						timeString = String.format("%02d:%02d", hours, minutes);
					}

					dateRow.getCell(i).setCellValue(timeString);
					break;

				case 9:

					timeString = "00:00";

					if (expensesMonth.getAugTotal() != null) {

						totalTime += expensesMonth.getAugTotal();

						int hours = expensesMonth.getAugTotal().intValue() / 3600;
						int minutes = (expensesMonth.getAugTotal().intValue() % 3600) / 60;

						timeString = String.format("%02d:%02d", hours, minutes);
					}

					dateRow.getCell(i).setCellValue(timeString);
					break;

				case 10:

					timeString = "00:00";

					if (expensesMonth.getSepTotal() != null) {

						totalTime += expensesMonth.getSepTotal();

						int hours = expensesMonth.getSepTotal().intValue() / 3600;
						int minutes = (expensesMonth.getSepTotal().intValue() % 3600) / 60;

						timeString = String.format("%02d:%02d", hours, minutes);
					}

					dateRow.getCell(i).setCellValue(timeString);
					break;

				case 11:

					timeString = "00:00";

					if (expensesMonth.getOctTotal() != null) {

						totalTime += expensesMonth.getOctTotal();

						int hours = expensesMonth.getOctTotal().intValue() / 3600;
						int minutes = (expensesMonth.getOctTotal().intValue() % 3600) / 60;

						timeString = String.format("%02d:%02d", hours, minutes);
					}

					dateRow.getCell(i).setCellValue(timeString);
					break;

				case 12:

					timeString = "00:00";

					if (expensesMonth.getNovTotal() != null) {

						totalTime += expensesMonth.getNovTotal();

						int hours = expensesMonth.getNovTotal().intValue() / 3600;
						int minutes = (expensesMonth.getNovTotal().intValue() % 3600) / 60;

						timeString = String.format("%02d:%02d", hours, minutes);
					}

					dateRow.getCell(i).setCellValue(timeString);
					break;

				case 13:

					timeString = "00:00";

					if (expensesMonth.getDecTotal() != null) {

						totalTime += expensesMonth.getDecTotal();

						int hours = expensesMonth.getDecTotal().intValue() / 3600;
						int minutes = (expensesMonth.getDecTotal().intValue() % 3600) / 60;

						timeString = String.format("%02d:%02d", hours, minutes);
					}

					dateRow.getCell(i).setCellValue(timeString);
					break;

				case 14:

					int hours = totalTime.intValue() / 3600;
					int minutes = (totalTime.intValue() % 3600) / 60;

					timeString = String.format("%02d:%02d", hours, minutes);

					dateRow.getCell(i).setCellValue(timeString);
					break;

				case 15:
					dateRow.getCell(i).setCellValue(String.valueOf(DoubleRounder.round(((getTotalExpenses(expensesMonth) * 100) / totalUserTime), 2)) + '%');
					break;

				default:
					break;
			}
		}

		// Columns C - N Width
		for (int i = 2; i < 16; i++) {
			sheet.autoSizeColumn(i);
		}
	}

	private double getTotalExpenses(ExpensesMonthDTO expensesMonth) {
		double totalExpenses = 0.0;
		
		if (expensesMonth.getJanTotal() != null) {
			totalExpenses += expensesMonth.getJanTotal();
		}
		
		if (expensesMonth.getFebTotal() != null) {
			totalExpenses += expensesMonth.getFebTotal();
		}
		
		if (expensesMonth.getMarTotal() != null) {
			totalExpenses += expensesMonth.getMarTotal();
		}
		
		if (expensesMonth.getAprTotal() != null) {
			totalExpenses += expensesMonth.getAprTotal();
		}
		
		if (expensesMonth.getMayTotal() != null) {
			totalExpenses += expensesMonth.getMayTotal();
		}
		
		if (expensesMonth.getJunTotal() != null) {
			totalExpenses += expensesMonth.getJunTotal();
		}
		
		if (expensesMonth.getJulTotal() != null) {
			totalExpenses += expensesMonth.getJulTotal();
		}
		
		if (expensesMonth.getAugTotal() != null) {
			totalExpenses += expensesMonth.getAugTotal();
		}
		
		if (expensesMonth.getSepTotal() != null) {
			totalExpenses += expensesMonth.getSepTotal();
		}
		
		if (expensesMonth.getOctTotal() != null) {
			totalExpenses += expensesMonth.getOctTotal();
		}
		
		if (expensesMonth.getNovTotal() != null) {
			totalExpenses += expensesMonth.getNovTotal();
		}
		
		if (expensesMonth.getDecTotal() != null) {
			totalExpenses += expensesMonth.getDecTotal();
		}
		
		return totalExpenses;
	}

	private double getTotalExpenseList(List<ExpensesMonthDTO> expensesMonths) {

		double totalExpenses = 0.0;

		for (ExpensesMonthDTO expensesMonth : expensesMonths) {

			if (expensesMonth.getJanTotal() != null) {
				totalExpenses += expensesMonth.getJanTotal();
			}

			if (expensesMonth.getFebTotal() != null) {
				totalExpenses += expensesMonth.getFebTotal();
			}

			if (expensesMonth.getMarTotal() != null) {
				totalExpenses += expensesMonth.getMarTotal();
			}

			if (expensesMonth.getAprTotal() != null) {
				totalExpenses += expensesMonth.getAprTotal();
			}

			if (expensesMonth.getMayTotal() != null) {
				totalExpenses += expensesMonth.getMayTotal();
			}

			if (expensesMonth.getJunTotal() != null) {
				totalExpenses += expensesMonth.getJunTotal();
			}

			if (expensesMonth.getJulTotal() != null) {
				totalExpenses += expensesMonth.getJulTotal();
			}

			if (expensesMonth.getAugTotal() != null) {
				totalExpenses += expensesMonth.getAugTotal();
			}

			if (expensesMonth.getSepTotal() != null) {
				totalExpenses += expensesMonth.getSepTotal();
			}

			if (expensesMonth.getOctTotal() != null) {
				totalExpenses += expensesMonth.getOctTotal();
			}

			if (expensesMonth.getNovTotal() != null) {
				totalExpenses += expensesMonth.getNovTotal();
			}

			if (expensesMonth.getDecTotal() != null) {
				totalExpenses += expensesMonth.getDecTotal();
			}
		}

		return totalExpenses;
	}
	
	private String getResponsablesToList(List<User> responsables) {
		
		String names = "";
		
		if (responsables != null && !responsables.isEmpty()) {
			
			for (User responsable : responsables) {
				names += responsable.getName() + " " + responsable.getSurnames() + ", ";
			}
		
			names = names.substring(0, names.length() - 2);
		}
		
		return names;
	}

	private List<ExpensesMonthDTO> joinExpensesDTOList(List<ExpensesMonthDTO> firstList, List<ExpensesMonthDTO> secondList) {

		for (ExpensesMonthDTO e : secondList) {

			final ExpensesMonthDTO ee = firstList.stream().filter(l -> Objects.equals(l.getUserId(), e.getUserId())).collect(Collectors.toList()).get(0);

			ee.sum(e);
		}

		return firstList;
	}
}
