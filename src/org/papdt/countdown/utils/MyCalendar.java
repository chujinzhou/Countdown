package org.papdt.countdown.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.util.Log;

public class MyCalendar {
	
	private static Calendar c;
	
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
	
	public static long countdown(Date d, int field){
		refreshTime();
		
		Date nowdate = c.getTime();
		long compare = d.getTime() - nowdate.getTime();
		
		switch (field){
		case Field.DAY:
			return (Long) compare / 1000 / 3600 / 24 + 1;
		case Field.MONTH:
			// TODO 很不科学的返回方法 需要改进
			return (Long) compare / 1000 / 3600 / 24 / 30;
		case Field.YEAR:
			// TODO 也是很不科学- - 我太懒了
			return (Long) (compare + (c.get(Calendar.YEAR) % 4 == 0 ? 1 : 0)) / 1000 / 3600 / 24 / 30 / 12;
		case Field.SECOND:
			return (Long) compare / 1000;
		case Field.MILESECOND:
			return (Long) compare;
		case Field.MINUTE:
			return (Long) compare / 1000 / 60;
		case Field.HOUR:
			return (Long) compare / 1000 / 3600;
		}
		
		return -1;
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
