package com.epm.gestepm.modelapi.common.utils;

import com.epm.gestepm.modelapi.common.config.ApplicationContextProvider;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.exception.InvalidUserSessionException;
import com.epm.gestepm.modelapi.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Utiles {

	private static final Log log = LogFactory.getLog(Utiles.class);
	
	private Utiles() { }	

	/**
	 * Converter of bytes to base64.
	 * @param bytes
	 * @return
	 */
	public static String convertBytesToBase64(byte[] bytes) {
		if (bytes == null)
			return "";

		return new String(Base64.getEncoder().encode(bytes));
	}
	
	/**
	 * Encript String to MD5 String.
	 * @param text
	 * @return
	 */
	public static String textToMD5(String text) {
		
		try {
			
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes());
			byte[] digest = md.digest();
			return DatatypeConverter.printHexBinary(digest).toUpperCase();
			
		} catch (NoSuchAlgorithmException e) {
			log.error(e);
			return null;
		}
	}
	
	/**
	 * Get Actual DateTime formated like dd/MM/yyyy HH:mm.
	 * @return
	 */
	public static String getActualDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return dateFormat.format(new Date());
	}
	
	public static Date transformSimpleStringToDate(String dateInString) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			return formatter.parse(dateInString);
		} catch (ParseException e) {
			log.error(e);
			return null;
		}
	}
	
	public static LocalDateTime transformSimpleStringToLocalDateTime(String dateTimeInString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		return LocalDateTime.parse(dateTimeInString, formatter);
	}
	
	public static Date transformSigningStringToDate(String dateTimeInString) {
		
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // dd-MM-yyyy HH:mm:ss
			return simpleDateFormat.parse(dateTimeInString);
		} catch (ParseException ex) {
			log.error("Error al parsear la fecha de fichaje: " + ex);
		}
		
		return null;
	}

	public static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
		
		if (dateToConvert == null) {
			return null;
		}
		
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
	/**
	 * Get DateTime String as Timestamp
	 * @param timestamp
	 * @return
	 */
	public static String transformTimestampToString(Timestamp timestamp) {
		Date date = new Date();
		date.setTime(timestamp.getTime());
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return dateFormat.format(date);
	}

	public static String transformToString(final OffsetDateTime offsetDateTime) {
		return offsetDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
	}
	
	/**
	 * Get DateTime String as Date
	 * @param date
	 * @return
	 */
	public static String transformFormattedDateToString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return dateFormat.format(date);
	}
	
	/**
	 * Transform String (ISO) to Date.
	 * @param date
	 * @return
	 */
	public static Date transformStringToDate(String date) {
		OffsetDateTime odt = OffsetDateTime.parse(date);
		Instant instant = odt.toInstant();
		return Date.from(instant);
	}
	
	/**
	 * Transform Date to String (ISO).
	 * @param date
	 * @return
	 */
	public static String transformDateToString(Date date) {
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		df.setTimeZone(tz);
		return df.format(date);
	}
	
	/**
	 * Get Date as start of day.
	 * @param date
	 * @return
	 */
	public static Date atStartOfDay(Date date) {
	    LocalDateTime localDateTime = dateToLocalDateTime(date);
	    LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
	    return localDateTimeToDate(startOfDay);
	}

	/**
	 * Get Date as end of day.
	 * @param date
	 * @return
	 */
	public static Date atEndOfDay(Date date) {
	    LocalDateTime localDateTime = dateToLocalDateTime(date);
	    LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
	    return localDateTimeToDate(endOfDay);
	}

	/**
	 * Converter Date to LocalDateTime.
	 * @param date
	 * @return
	 */
	private static LocalDateTime dateToLocalDateTime(Date date) {
	    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	/**
	 * Converter LocalDateTime to Date.
	 * @param localDateTime
	 * @return
	 */
	public static Date localDateTimeToDate(LocalDateTime localDateTime) {
	    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	/**
	 * Get Date as String from timestamp
	 * @param timestamp
	 * @return
	 */
	public static String getDateFormatted(Timestamp timestamp) {
		Date date = new Date();
		date.setTime(timestamp.getTime());
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
		return dateFormat.format(date);  
	}

	public static String getDateFormatted(final OffsetDateTime offsetDateTime) {
		return offsetDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}
	
	/**
	 * Get Date as String from timestamp
	 * @param timestamp
	 * @return
	 */
	public static String getDateFormattedForForum(Timestamp timestamp) {
		Date date = new Date();
		date.setTime(timestamp.getTime());
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd");  
		return dateFormat.format(date);  
	}
	
	/**
	 * Get Date as String from Date
	 * @param date
	 * @return
	 */
	public static String getDateFormattedENG(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		return dateFormat.format(date);  
	}
	
	/**
	 * Get Date as String from Date ESP
	 * @param date
	 * @return
	 */
	public static String getDateFormattedESP(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
		return dateFormat.format(date);  
	}
	
	/**
	 * Get Date as String from Date
	 * @param date
	 * @return
	 */
	public static String getDateFormatted(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
		return dateFormat.format(date);  
	}
	
	/**
	 * Get Date as String from Date
	 * @param date
	 * @return
	 */
	public static String getDateTimeFormatted(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		return dateFormat.format(date);  
	}
	
	/**
	 * Get Time as String from Date
	 * @param date
	 * @return
	 */
	public static String getTimeFormatted(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");  
		return dateFormat.format(date);  
	}

	public static String getTimeFullFormatted(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		return dateFormat.format(date);
	}
	
	private static String getStringDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}
	
	public static int hourTimeToMinutes(String hourTime) {

		if (hourTime == null || hourTime.isEmpty()) {
			return 0;
		}

		boolean isNegative = hourTime.startsWith("-");

		if (isNegative) {
			hourTime = hourTime.substring(1);
		}

		int hours = parseInt(hourTime.split(":")[0]);
		int minutes = parseInt(hourTime.split(":")[1]);
		
		int totalMinutes = (hours * 60) + minutes;

		return isNegative ? totalMinutes * -1 : totalMinutes;
	}
	
	public static Timestamp getTimeStamp(Date date, String hour) {
		
		try {
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			// Parse for Edge Chronium
			if (hour.length() > 5) {
				hour = hour.substring(0, 5);
			}
			
			String stringDate = getStringDate(date) + StringUtils.SPACE + hour;
		    Date parsedDate = dateFormat.parse(stringDate);
		    return new Timestamp(parsedDate.getTime());
		} catch (ParseException e) {
			log.error(e);
			return null;
		}
	}
	
	public static LocalDateTime getLocalDateTime(Date date, String hour) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		// Parse for Edge Chronium
		if (hour.length() > 8) {
			hour = hour.substring(0, 8);
		} else if (hour.length() == 5) {
			hour += ":00";
		}
		
		String stringDate = getStringDate(date) + StringUtils.SPACE + hour;
	    return LocalDateTime.parse(stringDate, formatter);
	}
	
	public static String getDateAsText(int month, int year, Locale locale, MessageSource messageSource) {
		return messageSource.getMessage("month." + month, null, locale) + "-" + year;
	}
	
	public static long calculateMinutesBetweenDates(LocalDateTime date1, LocalDateTime date2) {
		return ChronoUnit.MINUTES.between(date1, date2);
	}

	public static User getUsuario() throws InvalidUserSessionException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getDetails();

		if (user == null) {
			throw new InvalidUserSessionException();
		}
		
		// Load Service
		UserService userService = ApplicationContextProvider.getContext().getBean(UserService.class);
		
		// Reload from DB
		return userService.getUserById(user.getId());
	}
	
	public static byte[] base64PngToByteArray(String base64Img) {
		String base64 = base64Img.replace("data:image/png;base64,", "");
		return base64ToByteArray(base64);
	}
	
	/**
	 * String base64 to byte[]
	 * @param base64
	 * @return
	 */
	public static byte[] base64ToByteArray(String base64) {
		return Base64.getDecoder().decode(base64);
	}
	
	public static String minutesToHoursAndMinutesString(int t) {
		int hours = t / 60;
		int minutes = t % 60;
		return String.format("%02d:%02d", hours, minutes);
	}

	public static String secondsToHoursAndMinutesAndSecondsString(int t) {

		int hours = t / 3600;
		int secondsLeft = t - hours * 3600;
		int minutes = secondsLeft / 60;
		int seconds = secondsLeft - minutes * 60;

		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}
	
	public static int getDaysOfMonth(int month, int year) {
		YearMonth yearMonthObject = YearMonth.of(year, month);
		return yearMonthObject.lengthOfMonth();
	}
	
	public static boolean isWeekend(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		return c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
	}
	
	public static String getStringDateWithMillis(long millis) {
		if (millis < 0) {
			millis = Math.abs(millis);
			return String.format("-%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1));
		} else {
			return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1));
		}
	}

	public static Date getStartDayDate(Calendar cal) {

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	public static void setStartDay(Calendar cal) {

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
	}

	public static Date getEndDayDate(Date date) {

		final Calendar cal = Calendar.getInstance();

		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);

		return cal.getTime();
	}

	public static Date getEndDayDate(Calendar cal) {

		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);

		return cal.getTime();
	}

	public static void setEndDay(Calendar cal) {

		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
	}
	
	public static String convert(InputStream inputStream, Charset charset) throws IOException {

		StringBuilder stringBuilder = new StringBuilder();
		String line = null;

		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		}

		return stringBuilder.toString();
	}

	public static boolean havePrivileges(String role) {
		
		boolean havePrivileges = false;
		
		if ("NIVEL 3".equals(role)) {
			havePrivileges = true;
		}
		
		return havePrivileges;
	}
	
	public static String getExceptionDump(Exception ex) {
	    StringBuilder result = new StringBuilder();

	    for (Throwable cause = ex; cause != null; cause = cause.getCause()) {
	    	result = new StringBuilder(); // Get only last Cause
	        result.append(cause.getMessage());
	    }
	    return result.toString();
	}

	public static final int SECONDS_PER_MINUTE = 60;
	public static final int MINUTES_PER_HOUR = 60;
	public static final int HOURS_PER_DAY = 24;
	public static final int SECONDS_PER_DAY = (HOURS_PER_DAY * MINUTES_PER_HOUR * SECONDS_PER_MINUTE);

	private static final Pattern TIME_SEPARATOR_PATTERN = Pattern.compile(":");

	public static double convertTimeInternal(String timeStr) {
		int len = timeStr.length();
		if (len < 4 || len > 9) {
			return 0;
		}
		String[] parts = TIME_SEPARATOR_PATTERN.split(timeStr);

		String secStr;
		switch (parts.length) {
			case 2: secStr = "00"; break;
			case 3: secStr = parts[2]; break;
			default:
				return 0;
		}
		String hourStr = parts[0];
		String minStr = parts[1];
		int hours = parseInt(hourStr);
		int minutes = parseInt(minStr);
		int seconds = parseInt(secStr);

		double totalSeconds = seconds + (minutes + (hours * 60.0)) * 60.0;
		return totalSeconds / (SECONDS_PER_DAY);
	}
}
