package com.springtour.otg.infrastructure.time;

import java.text.SimpleDateFormat;
import java.util.*;

import org.joda.time.*;

public final class DateUtils {

	private DateUtils() {

	}

	public static Date copyWithoutSencondsAndMillis(Date prototype) {
		Calendar c = Calendar.getInstance();
		c.setTime(prototype);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static long durationMillisBetween(Date start, Date end) {
		return new Interval(new DateTime(start), new DateTime(end))
				.toDuration().getMillis();
	}

	/**
	 * 注意这里返回的是 {@link DateUtils#durationMillisBetween(Date,Date)}的商，并不是四舍五入
	 */
	public static long durationMinutesBetween(Date start, Date end) {
		return durationMillisBetween(start, end) / 60000;
	}
	
	/**
     * 转变日期格式
     * 
     * @return 日期，格式如：20070110
     */
    public static String getDate(Date date) {
        SimpleDateFormat d = new SimpleDateFormat();
        d.applyPattern("yyyyMMdd");
        String strDate = d.format(date);
        return strDate;
    }

    /**
     * 转变时间格式
     * 
     * @return 返回格式为HHMISS HH为24小时制 (eg: 165838)
     */
    public static String getTime(Date date) {
    	SimpleDateFormat d = new SimpleDateFormat();
        d.applyPattern("HHmmss");
        String strTime = d.format(date);
        return strTime;
    }
    
    public static String getDateAndTime(Date date) {
    	SimpleDateFormat d = new SimpleDateFormat();
        d.applyPattern("yyyyMMddHHmmss");
        String strTime = d.format(date);
        return strTime;
    }
    
    public static String getDateAndTimeWithoutYear(Date date) {
    	SimpleDateFormat d = new SimpleDateFormat();
        d.applyPattern("MMddHHmmss");
        String strTime = d.format(date);
        return strTime;
    }
    
}
