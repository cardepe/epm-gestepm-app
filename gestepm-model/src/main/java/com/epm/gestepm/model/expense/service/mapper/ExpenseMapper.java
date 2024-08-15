package com.epm.gestepm.model.expense.service.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.modelapi.expense.dto.ExpenseDTO;
import com.epm.gestepm.modelapi.expense.dto.FileDTO;
import com.epm.gestepm.modelapi.expensesheet.dto.ExpenseSheetDTO;
import com.epm.gestepm.modelapi.user.dto.User;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.entity.ContentType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.epm.gestepm.modelapi.expense.dto.Expense;
import com.epm.gestepm.modelapi.expensefile.dto.ExpenseFile;
import com.epm.gestepm.modelapi.expensesheet.dto.ExpenseSheet;
import com.epm.gestepm.modelapi.paymenttype.dto.PaymentType;
import com.epm.gestepm.modelapi.pricetype.dto.PriceType;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.lib.file.FileUtils;
import com.epm.gestepm.model.common.utils.classes.SingletonUtil;

public class ExpenseMapper {

	private static final Log log = LogFactory.getLog(ExpenseMapper.class);

	public static ExpenseSheet mapDTOToExpenseSheet(ExpenseSheetDTO expenseSheetDTO, User user, Project project,
													SingletonUtil singletonUtil) {

		ExpenseSheet expenseSheet = new ExpenseSheet();
		List<Expense> expenses = ExpenseMapper.mapExpensesDTOToExpenses(expenseSheetDTO.getExpenses(), singletonUtil);
		Date now = new Date();

		expenseSheet.setId(expenseSheetDTO.getId());
		expenseSheet.setCreationDate(now);
		expenseSheet.setName(expenseSheetDTO.getName());
		expenseSheet.setProject(project);
		expenseSheet.setUser(user);
		expenseSheet.setStatus(Constants.STATUS_PENDING);
		expenseSheet.setExpenses(expenses);

		return expenseSheet;
	}

	public static ExpenseSheetDTO expenseSheetToMapDTO(ExpenseSheet expenseSheet, Long projectId, Long userId) {
		
		ExpenseSheetDTO expenseSheetDTO = new ExpenseSheetDTO();
		
		expenseSheetDTO.setId(expenseSheet.getId());
		expenseSheetDTO.setName(expenseSheet.getName());
		expenseSheetDTO.setCreationDate(expenseSheet.getCreationDate());
		expenseSheetDTO.setProject(projectId);
		expenseSheetDTO.setUser(userId);
		
		List<ExpenseDTO> expensesDTO = new ArrayList<>();
		for (Expense expense : expenseSheet.getExpenses()) {
			expensesDTO.add(mapExpenseToDTO(expense));
		}
		expenseSheetDTO.setExpenses(expensesDTO);
		
		return expenseSheetDTO;
	}
	
	public static List<Expense> mapExpensesDTOToExpenses(List<ExpenseDTO> expensesDTO, SingletonUtil singletonUtil) {
		List<Expense> expenses = new ArrayList<>();

		for (ExpenseDTO expenseDTO : expensesDTO) {
			Expense expense = ExpenseMapper.mapDTOToExpense(expenseDTO, singletonUtil);
			expenses.add(expense);
		}

		return expenses;
	}

	public static Expense mapDTOToExpense(ExpenseDTO expenseDTO, SingletonUtil singletonUtil) {

		final Expense expense = new Expense();
		final Date now = new Date();
		final PriceType priceType = singletonUtil.getPriceTypeById(expenseDTO.getPriceType());
		PaymentType paymentType;

		if (expenseDTO.getPaymentType() == null) {
			paymentType = singletonUtil.getPaymentTypeById(1L); // Default Type = Tarjeta EPM
		} else {
			paymentType = singletonUtil.getPaymentTypeById(expenseDTO.getPaymentType());
		}

		final Date startDate = expenseDTO.getStartDate();
		final Date endDate = expenseDTO.getEndDate() != null ? expenseDTO.getEndDate() : expenseDTO.getStartDate();

		expense.setId(expenseDTO.getId());
		expense.setStartDate(startDate);
		expense.setEndDate(endDate);
		expense.setDate(now);
		expense.setPriceType(priceType);
		expense.setPaymentType(paymentType);

		if ("price.type.4".equals(priceType.getName())) {
			expense.setKms(expenseDTO.getTotal());
			expense.setTotal(expenseDTO.getTotal() * priceType.getAmount());
		} else {
			expense.setTotal(expenseDTO.getTotal());
		}

		expense.setJustification(expenseDTO.getJustification());

		return expense;
	}

	public static ExpenseDTO mapExpenseToDTO(Expense expense) {

		ExpenseDTO expenseDTO = new ExpenseDTO();

		PaymentType paymentType = expense.getPaymentType();
		PriceType priceType = expense.getPriceType();

		expenseDTO.setId(expense.getId());
		expenseDTO.setEndDate(expense.getEndDate());
		expenseDTO.setJustification(expense.getJustification());
		expenseDTO.setPaymentType(paymentType.getId());
		expenseDTO.setPriceType(priceType.getId());
		expenseDTO.setStartDate(expense.getStartDate());
		expenseDTO.setKms(expense.getKms());
		expenseDTO.setTotal(expense.getTotal());
		
		List<FileDTO> expensesDTO = new ArrayList<>();
		if (expense.getFiles() != null) {
			for (ExpenseFile expenseFile : expense.getFiles()) {
				expensesDTO.add(mapExpenseFileToFileDTO(expenseFile));
			} 
		}		
		expenseDTO.setFiles(expensesDTO);

		return expenseDTO;
	}

	public static String mapAndSerializePriceTypesToJson(List<PriceType> priceTypes) {
		JSONArray arr = new JSONArray();
		JSONObject tmp;

		for (PriceType priceType : priceTypes) {
			try {
				tmp = new JSONObject();
				tmp.put("id", priceType.getId());
				tmp.put("name", priceType.getName());
				tmp.put("amount", priceType.getAmount());
				arr.put(tmp);
			} catch (Exception e) {
				log.error(e);
			}
		}

		return arr.toString();
	}

	public static ExpenseFile mapMultipartFileToExpenseFile(MultipartFile file, Expense expense) throws IOException {

		final String fileName = file.getOriginalFilename();
		final String ext = FilenameUtils.getExtension(fileName);

		final List<String> imageContentTypes = Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(), ContentType.IMAGE_PNG.getMimeType(), ContentType.IMAGE_GIF.getMimeType());
		final byte[] data = FileUtils.compressBytes(imageContentTypes.contains(file.getContentType())
				? FileUtils.compressImage(file)
				: file.getBytes());

		final ExpenseFile expenseFile = new ExpenseFile();
		expenseFile.setName(fileName);
		expenseFile.setExt(ext);
		expenseFile.setContent(data);
		expenseFile.setExpense(expense);

		return expenseFile;
	}

	public static FileDTO mapExpenseFileToFileDTO(ExpenseFile expenseFile) {
		if (expenseFile == null || expenseFile.getContent() == null) {
			return null;
		}

		byte[] data = FileUtils.decompressBytes(expenseFile.getContent());
		FileDTO fileDTO = new FileDTO();

		fileDTO.setExt(expenseFile.getExt());
		fileDTO.setName(expenseFile.getName());
		fileDTO.setContent(data);

		return fileDTO;
	}
}
