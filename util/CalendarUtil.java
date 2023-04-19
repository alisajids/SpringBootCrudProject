import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CalendarUtil {

	final static Logger logger = LoggerFactory.getLogger(FusionCalendarUtil.class);

	/**
	 * This method determines that the given timestamp is 
	 * greater than 24 hrs and returns true or false
	 * 
	 * @param ts
	 * @return
	 */
	public static boolean isGreaterThan24Hours(Timestamp ts) {
		logger.debug("Entering FusionCalendarUtil::isGreaterThan24Hours");

		long time = ts.getTime();

		long currentTime = System.currentTimeMillis();

		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
//		long hoursInMilli = minutesInMilli * 60;

		long difference = currentTime - time;
		logger.debug("Token time difference: " + difference);

//		long hrs =  difference / hoursInMilli;

		logger.debug("Exiting CalendarUtil::isGreaterThan24Hours");
		if (difference > 86400000) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * This method determines if the timestamp has passed the sent in hours.
	 * 
	 * @param ts
	 * @param hours
	 * @return
	 */
	public static boolean isPassedMoreThanHours(Timestamp ts, int hours) {

		long time = ts.getTime();
		long currentTime = System.currentTimeMillis();
		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		long hrs = (currentTime - time) / hoursInMilli;
		if (hrs >= hours) 
			return true;
		else 
			return false;
	}

	/* Return today's date. */
	public static Calendar scanDateToday() {
		Calendar today = new GregorianCalendar();
		return today;
	}

	/* Return the maximum datetime for today. */	
	public static Calendar maxDateToday() {
		Calendar dateTime = new GregorianCalendar();
		dateTime.setTime(new Date());
		dateTime.set(Calendar.HOUR_OF_DAY, 23);
		dateTime.set(Calendar.MINUTE, 59);
		dateTime.set(Calendar.SECOND, 59);
		dateTime.set(Calendar.MILLISECOND, 999);
		return dateTime;
	}	

	/* Return the minimum datetime for today. */
	public static Calendar minDateToday() {
		Calendar dateTime = new GregorianCalendar();
		dateTime.setTime(new Date());
		dateTime.set(Calendar.HOUR_OF_DAY, 0);
		dateTime.set(Calendar.MINUTE, 0);
		dateTime.set(Calendar.SECOND, 0);
		dateTime.set(Calendar.MILLISECOND, 0);
		return dateTime;
	}

	/* Return the maximum datetime for a Date. */	
	public static Date getMaxDate(Date date) {
		Calendar dateTime = new GregorianCalendar();
		dateTime.setTime(date);
		dateTime.set(Calendar.HOUR_OF_DAY, 23);
		dateTime.set(Calendar.MINUTE, 59);
		dateTime.set(Calendar.SECOND, 59);
		dateTime.set(Calendar.MILLISECOND, 999);
		Date retDate = dateTime.getTime(); // Calendar-to-Date.
		return retDate;
	}	

	/* Return the minimum datetime for a Date. */
	public static Date getMinDate(Date date) {
		Calendar dateTime = new GregorianCalendar();
		dateTime.setTime(date);
		dateTime.set(Calendar.HOUR_OF_DAY, 0);
		dateTime.set(Calendar.MINUTE, 0);
		dateTime.set(Calendar.SECOND, 0);
		dateTime.set(Calendar.MILLISECOND, 0);
		Date retDate = dateTime.getTime(); // Calendar-to-Date.
		return retDate;
	}	

	/* Return the leapyear status for a given year. */
	public static boolean isLeapYear(int year) {
		/* Based on the Gregorian calendar, when each
		 * leap year has 366 days instead of the usual 365.*/
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
	}

	public static String convertToString (Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String format = formatter.format(date);
		return format;
	}

	public static Date toDate(String dateStr) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.parse(dateStr);
	}

	public static Calendar subtract30Day (Date date) {
		Calendar thirtydays = new GregorianCalendar();
		thirtydays.set(Calendar.HOUR_OF_DAY, 0);
		thirtydays.set(Calendar.MINUTE, 0);
		thirtydays.set(Calendar.SECOND, 0);
		thirtydays.set(Calendar.MILLISECOND, 0);		
		thirtydays.add(Calendar.DATE, -30);
		return thirtydays; // Calendar to Date.
	}
	public static Date subtract30Days (Date date) {
		Calendar thirtydays = new GregorianCalendar();
		thirtydays.set(Calendar.HOUR_OF_DAY, 0);
		thirtydays.set(Calendar.MINUTE, 0);
		thirtydays.set(Calendar.SECOND, 0);
		thirtydays.set(Calendar.MILLISECOND, 0);		
		thirtydays.add(Calendar.DATE, -30);
		return thirtydays.getTime(); // Calendar to Date.
	}

	public static String dateDifference(Date d1,Date d2){
		long diff = d2.getTime() - d1.getTime();

//		long diffMinutes = diff / (60 * 1000) % 60;
//		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);

		return diffDays+"d ago";
	}

	public static Date getTimeWithOffSet(Date dateObject, Integer timeZoneOffset) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime( dateObject );
		calendar.add( Calendar.MINUTE, timeZoneOffset );
		return calendar.getTime();
	}
	
	public static Map<Date, Date> initializeDates(List<Date> dateList) {
		
		Calendar beginCalendar = Calendar.getInstance();
		Calendar finishCalendar = Calendar.getInstance();
		LinkedHashMap<Date, Date> result = new LinkedHashMap<>();
		beginCalendar.setTime(dateList.get(0));
		finishCalendar.setTime(dateList.get(dateList.size() - 1));
		List<Date> monthList = new ArrayList<>();
		do {
			monthList.add(beginCalendar.getTime());
			beginCalendar.add(Calendar.MONTH, 1);
		} while (beginCalendar.before(finishCalendar));
		monthList.add(finishCalendar.getTime());

		
			LinkedHashMap<Date, Date> dateMap = new LinkedHashMap<>();
			for (Date date : monthList) {
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum((Calendar.DAY_OF_MONTH)));
				Date startDate = c.getTime();
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
				Date endDate = c.getTime();
				dateMap.put(startDate, endDate);
			}
			dateMap.entrySet().stream().sorted( Map.Entry.<Date, Date>comparingByValue().reversed() )
			.forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
			return result;
	}
	
	public static int dateDifferenceUsingLocaldate(Date d1,Date d2){
		LocalDate date1 = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate date2 = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Period diff = Period.between(date1, date2);	
		return diff.getDays();
	}
	
	public static long dateDifferenceLong(Date d1,Date d2){
		long diff = d2.getTime() - d1.getTime();

		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);


		return diffDays;
	}

	
	public static String convertDateToString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		return formatter.format(date);
	}

	public static Date convertStringToDate(String date) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		return formatter.parse(date);
	}
	
	public static List<String> convertDateToString (List<Date> dates) {
		List<String> convertedDates = new ArrayList<>();
		if(dates != null && !dates.isEmpty()) {
		for(Date date : dates) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String format = formatter.format(date);
		convertedDates.add(format);
		  }
		}
		return convertedDates;
	}
	
	public static List<String> convertDateToStringInMMddyyyy(List<Date> dates) {
		List<String> convertedDates = new ArrayList<>();
		if (dates != null && !dates.isEmpty()) {
			for (Date date : dates) {
				SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
				String format = formatter.format(date);
				convertedDates.add(format);
			}
		}
		return convertedDates;
	}

	public static Date StringToDate(String dateStr) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		return formatter.parse(dateStr);
	}
	
	public static List<String> getRequired30Dates(Date startDate, Date endDate){
		List<String> allDates = new ArrayList<>();
		Calendar calendar = new GregorianCalendar();
		Date currentScanDate = startDate;
		calendar.setTime(endDate);
		DateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		while (calendar.getTime().before(currentScanDate) || calendar.getTime().equals(currentScanDate)) {
			Date result = calendar.getTime();
			allDates.add(sm.format(result));
			calendar.add(Calendar.DATE, 1);
		}
		return allDates.subList(allDates.size() > 30 ? allDates.size() - 30 : 0, allDates.size());
	}
	
	public static List<String> get30Dates(Date startDate){
		List<String> allDates = new ArrayList<>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startDate);
		calendar.add(Calendar.DATE, -29); //Including today's date it will be 30 days
		Date currentScanDate = startDate;
		DateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		while (calendar.getTime().before(currentScanDate) || calendar.getTime().equals(currentScanDate)) {
			Date result = calendar.getTime();
			allDates.add(sm.format(result));
			calendar.add(Calendar.DATE, 1);
		}
		return allDates;
	}

	public static String getPreviousDate(String stringDate) throws ParseException {
		DateFormat sm = new SimpleDateFormat(MlxConstants.SIMPLE_DATE_FORMAT);
		Date date = toDate(stringDate);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		return sm.format(calendar.getTime());
	}
	
	public static Map<Date, Date> getYearStartAndEndDate(String year) {
		LinkedHashMap<Date, Date> result = new LinkedHashMap<>();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.DAY_OF_YEAR, 1);
		Date startDate = cal.getTime();
		cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
		Date endDate = cal.getTime();
		result.put(startDate, endDate);
		return result;
	}

	public static String changeFormat(String date, String initDateFormat, String endDateFormat) throws ParseException {
		Date initDate = new SimpleDateFormat(initDateFormat).parse(date);
		SimpleDateFormat formatter = new SimpleDateFormat(endDateFormat);
		return formatter.format(initDate);
	}
	
	/**
	 * Get next date
	 * @param currentDate
	 * @return
	 * @throws ParseException
	 */
	public static String getNextDate(String currentDate) throws ParseException {
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		Date scanDate = df.parse(currentDate);
		cal.setTime(scanDate);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return df.format(cal.getTime());
	}
	
	/**
	 * Get next date
	 * @param currentDate
	 * @return Date 
	 */
	public static Date getNextDate(Date currentDate) {
		Calendar c = GregorianCalendar.getInstance(); 
		c.setTime(currentDate); 
		c.add(Calendar.DATE, 1);
		return c.getTime();
	}
}
