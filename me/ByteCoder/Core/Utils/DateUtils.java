package me.ByteCoder.Core.Utils;

import java.util.Calendar;

public class DateUtils {

	public static String fromIntToStr(Integer i){
		return String.valueOf(i);
	}
	
	public static Integer fromStringToInteger(String s){
		return Integer.valueOf(s);
	}
	
	public static boolean fromStringToBoolean(String s){
		boolean p = false;
		
		if(s.equalsIgnoreCase("true")){
			p = true;
		}else if(s.equalsIgnoreCase("false")){
			p = false;
		}
		return p;
	}
	
	public static String getCurrentTime(){
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int mon = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);
		 
		String time = String.format("%04d-%02d-%02d | %02d:%02d:%02d", year, mon, day, hour, min, sec);
		return time;
	}
	
	public static String getCurrentTime1(){
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);
		 
		String time = String.format("%02d:%02d:%02d", hour, min, sec);
		return time;
	}
	
	public static String getCurrentTime2(){
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int mon = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		 
		String time = String.format("%04d-%02d-%02d", year, mon, day);
		return time;
	}
}
