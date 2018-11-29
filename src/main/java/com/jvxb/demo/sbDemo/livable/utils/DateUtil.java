package com.jvxb.demo.sbDemo.livable.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期相关工具类
 * 
 * @author 抓娃小兵
 */
public class DateUtil {
	
	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

	private final static SimpleDateFormat YY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");

	private final static SimpleDateFormat YYMMDD = new SimpleDateFormat("yyyyMMdd");

	private final static SimpleDateFormat YY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private final static SimpleDateFormat YYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");

	private final static SimpleDateFormat HHMMSS = new SimpleDateFormat("HH:mm:ss");

	public final static SimpleDateFormat ymsdf = new SimpleDateFormat("yyyy/MM");

	/**
	 * 获取YYYY格式
	 * 
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}

	/**
	 * 获取YYYY格式
	 *
	 * @return
	 */
	public static String getYear(Date date) {
		return sdfYear.format(date);
	}

	/**
	 * 获取当前时间的YYYY-MM-DD格式
	 * 
	 * @return
	 */
	public static String getDay() {
		return YY_MM_DD.format(new Date());
	}

	/**
	 * 获取输入时间的YYYY-MM-DD格式
	 *
	 * @return
	 */
	public static String getDay(Date date) {
		return YY_MM_DD.format(date);
	}

	/**
	 * 获取当前时间的YYYYMMDD格式
	 * 
	 * @return
	 */
	public static String getDays() {
		return YYMMDD.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * 
	 * @return
	 */
	public static String getTime() {
		return YY_MM_DD_HH_MM_SS.format(new Date());
	}

	/**
	 * 获取输入时间的YYYY-MM-DD HH:mm:ss格式
	 *
	 * @return
	 */
	public static String getTime(Date date) {
		return YY_MM_DD_HH_MM_SS.format(date);
	}

	/**
	 * 获取YYYYMMDDHHmmss格式
	 *
	 * @return
	 */
	public static String getTimes() {
		return YYMMDDHHMMSS.format(new Date());
	}

	/**
	 * 获取HH:mm:ss格式
	 * 
	 * @return
	 */
	public static String getTimeHour() {
		return HHMMSS.format(new Date());
	}

	/**
	 * @Title: compareDate
	 * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	 * @param s
	 * @param e
	 * @return boolean
	 * @throws @author luguosui
	 */
	public static boolean compareDate(String s, String e) {
		if (fomatDate(s) == null || fomatDate(e) == null) {
			return false;
		}
		return fomatDate(s).getTime() >= fomatDate(e).getTime();
	}

	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 校验日期是否合法
	 * 
	 * @return
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}

	public static int getDiffYear(String startTime, String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24))
					/ 365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 * 
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = null;
		Date endDate = null;

		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		// System.out.println("相隔的天数="+day);

		return day;
	}
	
	/**
	 * <li>功能描述：时间相减得到年数
	 * 
	 */
	public static long getYearSub(String beginDateStr, String endDateStr) {
		int year = 0;
		try {
		    Calendar  beginDate  =  Calendar.getInstance();
		    beginDate.setTime(YY_MM_DD.parse(beginDateStr));
		    Calendar  endDate  =  Calendar.getInstance();
		    endDate.setTime(YY_MM_DD.parse(endDateStr));
		    int beginYear = beginDate.get(Calendar.YEAR);
		    int endYear = endDate.get(Calendar.YEAR);
		    year = endYear - beginYear;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return year;
	}

	/**
	 * 得到n天之后的日期
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayDate(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	/**
	 * 得到n天之后是周几
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);

		return dateStr;
	}

	/**
	 * 判断当前日期是星期几
	 * 
	 * @param pTime 修要判断的时间
	 * @return dayForWeek 判断结果
	 * @throws ParseException
	 * @Exception 发生异常
	 */
	public static String dayForWeek(String pTime) throws ParseException {
		Calendar c = Calendar.getInstance();
		c.setTime(YY_MM_DD.parse(pTime));
		Date date = c.getTime();
		// 中文周几
		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);

		// 数字周几
//		  int dayForWeek = 0;
//		  if(c.get(Calendar.DAY_OF_WEEK) == 1){
//		   dayForWeek = 7;
//		  }else{
//		   dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
//		  }
		return dateStr;
	}

	/**
	 * 
	 * @param time(HH:mm:ss)
	 * @return 0上午1下午
	 * @throws ParseException
	 */
	public static int dayOfAM_PM(String time) throws ParseException {
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
		Date date = sdfTime.parse(time);
		GregorianCalendar ca = new GregorianCalendar();
		ca.setTime(date);
		int am_pm = ca.get(GregorianCalendar.AM_PM);
		return am_pm;
	}

	/**
	 * 获取当前时间 距离month个月yyyy/MM形式的时间
	 * @param month
	 * @return
	 */
	public static String getYearMonth(int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, month);
		return ymsdf.format(calendar.getTime());
	}

}
