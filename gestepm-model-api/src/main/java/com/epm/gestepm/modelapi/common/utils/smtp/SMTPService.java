package com.epm.gestepm.modelapi.common.utils.smtp;

import com.epm.gestepm.modelapi.common.utils.smtp.dto.CloseInspectionMailTemplateDto;
import com.epm.gestepm.modelapi.common.utils.smtp.dto.CloseNoProgrammedShareMailTemplateDto;
import com.epm.gestepm.modelapi.common.utils.smtp.dto.OpenNoProgrammedShareMailTemplateDto;
import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.expensecorrective.dto.ExpenseCorrective;
import com.epm.gestepm.modelapi.expensesheet.dto.ExpenseSheet;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShare;
import com.epm.gestepm.modelapi.modifiedsigning.dto.ModifiedSigning;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.userholiday.dto.UserHoliday;
import com.epm.gestepm.modelapi.usermanualsigning.dto.UserManualSigning;
import com.epm.gestepm.modelapi.workshare.dto.WorkShare;

import java.util.Locale;

public interface SMTPService {
	
	void sendCreateHolidaysRRHHMail(String to, User user, String holidays, Locale locale);
	void sendDeleteHolidaysRRHHMail(String to, User user, String holidays, Locale locale);
	
	void sendCloseConstructionShareMail(String to, ConstructionShare share, byte[] pdfGenerated, Locale locale);
	
	void openNoProgrammedShareSendMail(final OpenNoProgrammedShareMailTemplateDto dto);
	void closeNoProgrammedShareSendMail(final CloseNoProgrammedShareMailTemplateDto dto);
	void closeInspectionSendMail(final CloseInspectionMailTemplateDto dto);
	
	void sendCloseProgrammedShareMail(String to, InterventionPrShare share, byte[] pdfGenerated, Locale locale);

	void sendCloseWorkShareMail(String to, WorkShare share, byte[] pdfGenerated, Locale locale);
	
	void sendExpenseUserMail(String to, ExpenseSheet expenseSheet, Locale locale);
	void sendExpenseTeamLeaderMail(String to, User teamLeader, ExpenseSheet expenseSheet, Locale locale);
	void sendExpenseRRHHMail(String to, User rrhh, ExpenseSheet expenseSheet, Locale locale);
	
	void sendSigningInvalidMail(String to, User user, Locale locale);
	void sendSigningManualMail(String to, UserManualSigning userManualSigning, Locale locale);

	void sendSigningModifyMail(String to, ModifiedSigning modifiedSigning, Locale locale);
	
	void sendExpenseDeclineMail(String to, User user, ExpenseSheet expenseSheet, Locale locale);
	void sendHolidayDeclineMail(String to, User user, UserHoliday userHoliday, Locale locale);
	
	void sendCorrectiveTeamLeaderMail(String to, User user, ExpenseCorrective corrective, Project project, Locale locale);

	void sendProjectWeeklySharesResume(String to, Project project, Locale locale);

}
