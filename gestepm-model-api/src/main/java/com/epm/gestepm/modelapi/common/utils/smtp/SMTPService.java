package com.epm.gestepm.modelapi.common.utils.smtp;

import com.epm.gestepm.modelapi.common.utils.smtp.dto.OpenPersonalExpenseSheetMailTemplateDto;
import com.epm.gestepm.modelapi.expensecorrective.dto.ExpenseCorrective;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.userold.dto.User;
import com.epm.gestepm.modelapi.userholiday.dto.UserHoliday;
import com.epm.gestepm.modelapi.usermanualsigning.dto.UserManualSigning;

import java.util.Locale;

public interface SMTPService {
	
	void sendCreateHolidaysRRHHMail(String to, User user, String holidays, Locale locale);
	void sendDeleteHolidaysRRHHMail(String to, User user, String holidays, Locale locale);
	
	void sendPersonalExpenseSheetSendMail(final OpenPersonalExpenseSheetMailTemplateDto dto);

	void sendSigningManualMail(String to, UserManualSigning userManualSigning, Locale locale);

	void sendHolidayDeclineMail(String to, User user, UserHoliday userHoliday, Locale locale);
	
	void sendCorrectiveTeamLeaderMail(String to, User user, ExpenseCorrective corrective, Project project, Locale locale);

}
