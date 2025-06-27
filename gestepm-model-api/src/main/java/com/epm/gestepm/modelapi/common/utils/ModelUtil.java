package com.epm.gestepm.modelapi.common.utils;

import com.epm.gestepm.modelapi.common.config.ApplicationContextProvider;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.modelapi.deprecated.expense.dto.ExpenseUserValidateDTO;
import com.epm.gestepm.modelapi.deprecated.expense.dto.ExpenseValidateDTO;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import com.epm.gestepm.modelapi.deprecated.user.service.UserServiceOld;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ModelUtil {
	
	private ModelUtil() {
		
	}

	public static User loadConstants(Locale locale, Model model, HttpServletRequest request) {
		
		// Identicicaciones del organismo al modelo
		model.addAttribute("locale", locale.getLanguage());
		
		String path = request.getRequestURI().substring(request.getContextPath().length());
		model.addAttribute("pageName", path);

		// Cargamos el usuario
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getDetails();

		UserServiceOld userServiceOld = ApplicationContextProvider.getBean(UserServiceOld.class);

		// Reload from DB
		user = userServiceOld.getUserById(user.getId());

		if (user != null) {

			model.addAttribute("user", user);
			model.addAttribute("sessionRoleId", user.getRole().getId());

			// If user is Team Leader, load expenses count to validate
			if (user.getRole().getId() == Constants.ROLE_PL_ID) {

				List<ExpenseValidateDTO> expensesPending = userServiceOld.getExpensesToValidateByUserId(user.getId());
				model.addAttribute("expensesPending", expensesPending);

			} else if (user.getRole().getId() == Constants.ROLE_ADMINISTRATION_ID || user.getRole().getId() == Constants.ROLE_ADMIN_ID) {

				List<ExpenseUserValidateDTO> expensesPending = userServiceOld.getExpensesToPay();
				model.addAttribute("expensesPending", expensesPending);
			}
		}

		return user;
	}

	public static String getTableFileAndTrashActionButtons() {
		return "<em class=\\\"fas fa-file-pdf\\\"></em>" +
				"<em class=\\\"far fa-trash-alt\\\"></em>";
	}
	
	public static String getTableActionButtons() {
		return "<em class=\\\"fas fa-file-pdf\\\"></em>" +
			   "<em class=\\\"far fa-eye\\\"></em>" +
			   "<em class=\\\"fas fa-edit\\\"></em>" +
			   "<em class=\\\"far fa-trash-alt\\\"></em>";
	}
	
	public static String getExpensesTableActionButtons() {
		return "<em class=\\\"fas fa-file-pdf\\\"></em>" +
			   "<em class=\\\"far fa-eye\\\"></em>" +
			   "<em class=\\\"fas fa-thumbs-up\\\"></em>" +
			   "<em class=\\\"far fa-thumbs-down\\\"></em>";
	}

	public static String getThumbsTableActionButtons() {
		return "<em class=\\\"fas fa-thumbs-up\\\"></em>" +
				"<em class=\\\"far fa-thumbs-down\\\"></em>";
	}
	
	public static String getTableModifyActionButtons() {
		return "<em class=\\\"fas fa-edit\\\"></em>" +
			   "<em class=\\\"far fa-trash-alt\\\"></em>";
	}
	
	public static String getTableActionButtonsOnlyTrash() {
		return "<em class=\\\"far fa-trash-alt\\\"></em>";
	}
	
	public static String getTableValidateActionButtons() {
		return "<em class=\\\"fas fa-thumbs-up\\\"></em>" +
			   "<em class=\\\"fas fa-thumbs-down\\\"></em>" + 
			   "<em class=\\\"far fa-trash-alt\\\"></em>";
	}
	
	public static String getTableExpenseActionButtons() {
		return "<em class=\\\"fas fa-eye\\\"></em>" +
			   "<em class=\\\"fas fa-thumbs-up\\\"></em>" +
			   "<em class=\\\"fas fa-thumbs-down\\\"></em>";
	}
	
	public static String getViewActionButton() {
		return "<em class=\\\"fas fa-eye\\\"></em>";
	}
	
	public static Map<Integer, String> loadMonths(MessageSource messageSource, Locale locale) {
		Map<Integer, String> months = new HashMap<>();
		
		for (int i = 1; i <= 12; i++) {
			months.put(i, messageSource.getMessage("month." + i, null, locale));
		}
		
		return months;
	}
}
