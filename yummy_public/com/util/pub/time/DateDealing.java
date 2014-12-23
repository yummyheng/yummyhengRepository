package com.util.pub.time;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.util.pub.data.DataDealing;

/**
 * 日期处理类
 * @author hengzai 时间：2013-8-1
 */

public class DateDealing {
	//返回两个日期之间相差的天数
	public static int getDatesDifference(String date1, String date2) {
		//条件判断
		if(date1 == null || date1.trim().equals("")) {
			return -1;
		}
		if(date2 == null || date2.trim().equals("")) {
			return -1;
		}
		
		int days = 0;
		
		//返回long类型
		Date d1 = DataDealing.gainDateByStr(date1);
		Date d2 = DataDealing.gainDateByStr(date2);
		//数据长度较大 因为d1和d2是毫秒级 
		long i1 = d1.getTime();
		long i2 = d2.getTime();
		//计算相差的天数并进行强转，转化为int类型
		long temp = (i1 - i2) / (24 * 60 * 60 * 1000);
		temp = Math.abs(temp);
		days = (int) temp;
		
		//返回参数
		return days;
	}
	
	//给一个日期加上或者减去几天，返回这个日期
	public static String dateCount(String in_date, int number) {
		//设置将一天的时间转化成毫秒的容量
		long dayms = 24 * 60 * 60 * 1000;
		
		if(in_date == null || in_date.trim().equals("")) {
			return in_date;
		}
		
		Date date = DataDealing.gainDateByStr(in_date);
		//取得时间，单位为毫秒
		long baseDatems = date.getTime();
		//进行计算
		long result = number * dayms + baseDatems;
		
		//进行格式转换并返回
		SimpleDateFormat formatter = new SimpleDateFormat();
		return formatter.format(result);
		
	}
}
