package org.papdt.countdown.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.util.Log;

public class MyCalendar {
	
	private static Calendar c;
	
	private static final String TAG = "MyCalendar";
	
	private static void refreshTime(){
		c = Calendar.getInstance();
	}

	public static Calendar getTime(){
		return c;
	}
	
	public static int get(int id){
		refreshTime();
		
		switch (id){
		case Calendar.MONTH: return c.get(Calendar.MONTH)+1;
		}
		
		return c.get(id);
	}
	
	public static String getDateString(Locale l){
		refreshTime();
		Log.i("MyCalendar", "Locale Country:" + l.getCountry() + ", Locale Display:" + l.getDisplayName());
		if (l.getCountry().indexOf("CN") != -1){
			return c.getDisplayName(Calendar.MONTH, Calendar.LONG, l)
					+ c.get(Calendar.DAY_OF_MONTH) + "日";
		} else {
			return c.getDisplayName(Calendar.MONTH, Calendar.SHORT, l)
					+ c.get(Calendar.DAY_OF_MONTH);
		}
	}
	
	public static String getLunarString(){
		refreshTime();
		LunarCalendar l = new LunarCalendar(c);
		
		return l.toString();
	}
	
	public static boolean isLeapYear(int year){  
        return (year % 4 == 0 & year % 100 != 0) || (year % 400 == 0);  
    }
	
	@SuppressWarnings("deprecation")
	public static boolean isLastDayOfMonth(Date d){
		switch (d.getMonth() + 1){
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return d.getDay() == 31 ? true : false;
		case 2:
			return (isLeapYear(d.getYear())) ?
				((d.getDay() == 29) ? true : false) : ((d.getDay() == 28) ? true :false);
		case 4:
		case 6:
		case 9:
		case 11:
			return d.getDay() == 30 ? true : false;
		}
		return false;
	}
	
	public static String format(String source, boolean isLunar){
		//TODO 完善农历倒计时机制
		refreshTime();
		
		Log.i(TAG, "源数据:" + source);
		
		if (source.length() == 2){
			Log.i(TAG, "源数据为每月" + source + "日");
			if (c.get(Calendar.DAY_OF_MONTH) <= Integer.parseInt(source)){
				return c.get(Calendar.YEAR)
						+ "-" + (c.get(Calendar.MONTH) + 1)
						+ "-" + Integer.parseInt(source);
			} else {
				if (c.get(Calendar.MONTH) != 11){
					return c.get(Calendar.YEAR)
							+ "-" + (c.get(Calendar.MONTH) + 2)
							+ "-" + Integer.parseInt(source);
				} else {
					return (c.get(Calendar.YEAR) + 1)
							+ "-01"
							+ "-" + Integer.parseInt(source);
				}
			}
		}
		
		if (source.length() == 5){
			Log.i(TAG, "源数据为每年" + source);
			if ((c.get(Calendar.MONTH) + 1) < Integer.parseInt(source.substring(0, 2))){
				Log.i(TAG, "源数据大于当月,返回今年的" + source);
				return c.get(Calendar.YEAR) + "-" + source;
			}
			
			if ((c.get(Calendar.MONTH) + 1) == Integer.parseInt(source.substring(0, 2))){
				Log.i(TAG, "源数据为当月,判断当天是否大于或等于" + source.substring(3, 4));
				if (c.get(Calendar.DAY_OF_MONTH) <= Integer.parseInt(source.substring(3, 4))){
					Log.i(TAG, "源数据大于当天,返回今年的" + source);
					return c.get(Calendar.YEAR) + "-" + source;
				} else {
					Log.i(TAG, "源数据小于当天,返回明年的" + source);
					return (c.get(Calendar.YEAR) + 1) + "-" + source;
				}
			}
			
			if ((c.get(Calendar.MONTH) + 1) > Integer.parseInt(source.substring(0, 2))){
				Log.i(TAG, "源数据小于当月,返回明年的" + source);
				return (c.get(Calendar.YEAR) + 1) + "-" + source;
			}
		}
		
		Log.i(TAG, "源数据为绝对日期.");
		return source;
	}
	
	@SuppressWarnings("deprecation")
	public static long countdown(Date d){
		refreshTime();
		
		Date nowdate = c.getTime();
		Calendar setdate = Calendar.getInstance();
		setdate.setTime(d);
		long compare = d.getTime() - nowdate.getTime();
		
		Log.i(TAG, "");
		if (nowdate.getYear() == d.getYear()){
			if (isLastDayOfMonth(nowdate) & d.getDay() == 1 & d.getMonth() - nowdate.getMonth() == 1){
				return 1;
			}
			if (isLastDayOfMonth(d) & nowdate.getDay() == 1 & d.getMonth() - nowdate.getMonth() == -1){
				return -1;
			}
			if (d.getMonth() == nowdate.getMonth()){
				return setdate.get(Calendar.DAY_OF_MONTH) - c.get(Calendar.DAY_OF_MONTH);
			}
		}
		return Math.round(compare / 1000 / 3600 / 24);
		
	}
	
	public class Field{
		public final static int DAY=0,
				MONTH=1, 
				YEAR=2,
				HOUR=3,
				MINUTE=4,
				SECOND=5,
				MILESECOND=6;
	}
	
}
