package CompareAPI.myAPI.data.jpa.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	
	//날짜 유효성 체크
		public static boolean dateCheck(String date) {
			SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyymmdd", Locale.KOREA);
			dateFormatParser.setLenient(false);
			try {
				dateFormatParser.parse(date);
				return true;
			} catch (Exception Ex) {
				return false;
			}
		}

		public static String addDate(String processDt, int add) {
			Calendar cal = Calendar.getInstance();
	        DateFormat df = new SimpleDateFormat("yyyyMMdd");
	        Date date = null;
	        try {
	            date = df.parse(processDt);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        cal.setTime(date);
	        cal.add(Calendar.DATE, add);

			return df.format(cal.getTime());
		}
		
		public static String addMonth(String processDt, int add) {
			Calendar cal = Calendar.getInstance();
	        DateFormat df = new SimpleDateFormat("yyyyMMdd");
	        Date date = null;
	        try {
	            date = df.parse(processDt);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        cal.setTime(date);
	        cal.add(Calendar.MONTH, add);

			return df.format(cal.getTime());
		}

}
