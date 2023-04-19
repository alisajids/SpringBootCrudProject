import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DateUtil {
	
  public static final String DEFAULT_TIMEZONE_OFFSET = "+05:30";
	/**
	 * This method is used to return current local date time with offset
	 * 
	 * @return
	 */
	public static OffsetDateTime getCurrentDateTimeWithOffset(){
		/*
		 * Date date = new Date(); OffsetDateTime offsetDateTime = date.toInstant()
		 * .atOffset(ZoneOffset.of(DEFAULT_TIMEZONE_OFFSET)); return offsetDateTime;
		 */
		OffsetDateTime now = OffsetDateTime.now();
		return now;
	}
	
	public static OffsetDateTime getCurrentUTCDateTime(String offSet){
		 Date date = new Date();
		 OffsetDateTime offsetDateTime = date.toInstant().atOffset(ZoneOffset.of(offSet)); 
		 return offsetDateTime;
	}
	
	public static String getUTCDateTime(){
		 Instant instant = Instant.now();  
		 return instant.toString();
	}
	
	/**
	 * This method is used to calculate the minutes difference between two dates.
	 * 
	 * @param startDateTime
	 * @param endDateTime
	 * @return Long
	 */
	
	public static long getTimeDifferenceInMinutes(Temporal startDateTime, Temporal endDateTime) {
		return ChronoUnit.MINUTES.between(startDateTime,endDateTime);
	}
	
	/**
	 * This method is used to get the local time-zone of machine.
	 * 
	 * @return ZoneOffset
	 */
	public static ZoneOffset getLocalTimeZone() {
		return
		ZoneOffset.systemDefault().getRules().getOffset(LocalDateTime.now());
	}
	
	/**
	 * This method is used to convert offsetdatetime into provided timezone and
	 * dateformat
	 * 
	 * @param offsetDateTime
	 * @param zoneOffSet
	 * @param langCode
	 * @param dateFormat
	 * @return formattedDate
	 */
	public static String getFormattedDate(OffsetDateTime offsetDateTime, ZoneOffset zoneOffSet, String langCode, int dateFormat) {
		DateTimeFormatter formatter = null;
		offsetDateTime = offsetDateTime.toInstant().atOffset(zoneOffSet);
		formatter = DateTimeFormatter.ofPattern(DateUtil.getDateFormatList().get(dateFormat), Locale.forLanguageTag(langCode));
		return formatter.format(offsetDateTime);
	}
	
	/**
	 * This method is used to convert offsetdatetime into provided timezone and dateformat
	 * @param offsetDateTime
	 * @param offsetId
	 * @param langCode
	 * @param dateFormat
	 * @return formattedDate
	 */
	public static String getFormattedDate(OffsetDateTime offsetDateTime, String offsetId, String langCode, int dateFormat) {
		DateTimeFormatter formatter = null;
		ZoneOffset zoneOffset  = ZoneOffset.of(offsetId);
		offsetDateTime = offsetDateTime.toInstant().atOffset(zoneOffset);
		formatter = DateTimeFormatter.ofPattern(DateUtil.getDateFormatList().get(dateFormat), Locale.forLanguageTag(langCode));
		return formatter.format(offsetDateTime);
	}
	
	
	/**
	 * This method return list of date formats.
	 * https://help.talend.com/r/en-US/8.0/data-preparation-user-guide/list-of-date-and-date-time-formats
	 * @return
	 */
	public static Map<Integer, String> getDateFormatList(){
		Map<Integer, String> dateFormatList = new HashMap<Integer, String>();
		
		dateFormatList.put(1, "M/d/yy");
		dateFormatList.put(2, "MM/dd/yy");
		dateFormatList.put(3, "MM-dd-yy");
		dateFormatList.put(4, "M-d-yy");
		dateFormatList.put(5, "MMM d, yyyy");
		dateFormatList.put(6, "MMMM d, yyyy");
		dateFormatList.put(7, "EEEE, MMMM d, yyyy");
		dateFormatList.put(8, "MMM d yyyy");
		dateFormatList.put(9, "M-d-yyyy");
		dateFormatList.put(10, "yyyy-MM-ddXXX");
		dateFormatList.put(11, "dd/MM/yyyy");
		dateFormatList.put(12, "d/M/yyyy");
		dateFormatList.put(13, "MM/dd/yyyy");
		dateFormatList.put(14, "M/d/yyyy");
		dateFormatList.put(15, "yyyy/M/d");
		dateFormatList.put(16, "M/d/yy h:mm a");
		dateFormatList.put(17, "MM/dd/yy h:mm a");
		dateFormatList.put(18, "MM-dd-yy h:mm a");
		dateFormatList.put(19, "M-d-yy h:mm a");
		dateFormatList.put(20, "MMM d, yyyy h:mm:ss a");
		dateFormatList.put(23, "EEE, d MMM yyyy HH:mm:ss Z");
		dateFormatList.put(24, "d MMM yyyy HH:mm:ss Z");
		dateFormatList.put(25, "MM-dd-yyyy h:mm:ss a");
		dateFormatList.put(26, "M-d-yyyy h:mm:ss a");
		dateFormatList.put(27, "yyyy-MM-dd h:mm:ss a");
		dateFormatList.put(28, "yyyy-M-d h:mm:ss a");
		dateFormatList.put(29, "yyyy-MM-dd HH:mm:ss.S");
		dateFormatList.put(30, "dd/MM/yyyy h:mm:ss a");
		dateFormatList.put(31, "d/M/yyyy h:mm:ss a");
		dateFormatList.put(32, "MM/dd/yyyy h:mm:ss a");
		dateFormatList.put(33, "M/d/yyyy h:mm:ss a");
		dateFormatList.put(34, "MM/dd/yy h:mm:ss a");
		dateFormatList.put(35, "MM/dd/yy H:mm:ss");
		dateFormatList.put(36, "M/d/yy H:mm:ss");
		dateFormatList.put(37, "dd/MM/yyyy h:mm a");
		dateFormatList.put(38, "d/M/yyyy h:mm a");
		dateFormatList.put(39, "MM/dd/yyyy h:mm a");
		dateFormatList.put(40, "M/d/yyyy h:mm a");
		dateFormatList.put(41, "MM-dd-yy h:mm:ss a");
		dateFormatList.put(42, "M-d-yy h:mm:ss a");
		dateFormatList.put(43, "MM-dd-yyyy h:mm a");
		dateFormatList.put(44, "M-d-yyyy h:mm a");
		dateFormatList.put(45, "yyyy-MM-dd h:mm a");
		dateFormatList.put(46, "yyyy-M-d h:mm a");
		dateFormatList.put(47, "MMM.dd.yyyy");
		dateFormatList.put(48, "d/MMM/yyyy H:mm:ss Z");
		dateFormatList.put(49, "dd/MMM/yy h:mm a");
		dateFormatList.put(50, "d MMM yyyy");
		return dateFormatList;
	}

}
