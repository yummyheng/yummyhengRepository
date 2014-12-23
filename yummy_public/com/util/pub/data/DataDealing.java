package com.util.pub.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import com.util.pub.content.ContentDealing;

/**
 * 数据处理
 * @author hengzai 时间：2013-7-31
 */
public class DataDealing {
	private static BigDecimal 	ZeroBD = new BigDecimal("0.000000001");
	
	//获得合理的BigDecimal字符串
	public static String getRealBD(String s) {
		if(s == null) {
			return s;
		}
		
		//初始化数据类型
		BigDecimal bd = null;
		bd = new BigDecimal(s);
		//小数点后超过7位都是0时转化成0.0
		if(bd.compareTo(ZeroBD) <= 0) {
			return "0.0";
		} else {
			s = strToFormatedStr(s, 8, true, false);
			return s;
		}
	}
	
	//将字符串转化为带固定小数位的字符串 重载方法1
	public static String strToFormatedStr(String in_s, int dec, boolean halfUp, boolean millSpt) {
		return strToFormatedStr(in_s, dec, halfUp, millSpt, false);
	}
	
	//将字符串转化为带固定小数位的字符串 重载方法2
	public static String strToFormatedStr(String in_s, int dec, boolean halfUp, boolean millSpt, boolean zero2Back) {
		//把所有,取消
		in_s = ContentDealing.delOneChar(in_s, ',');
		//初始化属性
		String out = null;
		String form = null;
		char[] ca = null; 
		
		if(dec < 0) {
			dec = 0;
		}
		
		if(dec > 0) {
			ca = new char[dec];
			for(int i = 0; i < ca.length; i++) {
				ca[i] = '0';
			}
		}
		//是否进行千分位处理
		if(millSpt) {
			if(dec == 0) {
				form = "#,##0";
			//是否有小数位
			} else {
				String zero = String.valueOf(ca);
				form = "#,##0." + zero;
			}
		//不进行千分位处理	
		} else {
			if(dec == 0) {
				form = "#0";
			} else {
				String zero = String.valueOf(ca);
				form = "#0." + zero;
			}
		}
		
		DecimalFormat DecFormat = new DecimalFormat(form);
		
		BigDecimal result = new BigDecimal(in_s);
		
		//是否使小数点后超过7位都是0取消该值
		if(zero2Back) {
			if(result.abs().compareTo(new BigDecimal("0.00000000001")) < 1) {
				return "";
			}
		}
		
		//四舍五入
		if(halfUp) {
			result = result.setScale(dec, RoundingMode.HALF_UP);
		} else {
			result = result.setScale(dec, RoundingMode.DOWN);
		}
		
		out = DecFormat.format(result);
		
		return out;
	}
	
	//将字符串转化为日期
	public static Date gainDateByStr(String inDate) {
		//使用默认时区和语言环境获得一个日历
		Calendar Cld = Calendar.getInstance();
		//返回一个历元到现在的毫秒偏移量
		Date cDate = Cld.getTime();
		
		int y = Cld.get(Calendar.YEAR);
		int m = Cld.get(Calendar.MONTH);
		int d = Cld.get(Calendar.DATE);
		
		int yc = y / 100;
		
		//如果没有输入日期，则返回当前日期
		if(inDate == null || inDate.length() < 1) {
			return cDate;
		}
		
		int len = inDate.length();
		for(int i = 0; i < len; i++) {
			//如果字符串内容不是阿拉伯数字则返回当前日期
			if(inDate.charAt(i) > '9' || inDate.charAt(i) < '0') {
				return cDate;
			}
		}
		
		if(len < 3) {
			d = Integer.parseInt(inDate);
			if(d > 31) {
				d = 31;
			}
			if(d < 1) {
				d = 1;
			}
			//将给定的字段设置为给定值(即日)
			Cld.set(Calendar.DATE, d);
			cDate = Cld.getTime();
			//返回值 Date类型
			return cDate;
		} else if(len == 3) {
			//第一位
			m = Integer.parseInt(inDate.substring(0, 1));
			//第二三位
			d = Integer.parseInt(inDate.substring(1));
			if(m > 12) {
				m = 12;
			}
			if(d > 31) {
				d = 31;
			}
			if(d < 1) {
				d = 1;
			}
			if(m < 1) {
				m = 1;
			}
			//Calendar类中月份是从0开始算起的
			m--;
			//将给定的字段设置为给定值(即日)
			Cld.set(y, d, m);
			cDate = Cld.getTime();
			//返回值 Date类型
			return cDate;
		} else if(len == 4) {
			m = Integer.parseInt(inDate.substring(0, 2));
			d = Integer.parseInt(inDate.substring(2));
			if (m > 12)
				m = 12;
			if (d > 31)
				d = 31;
			if (d < 1)
				d = 1;
			if (m < 1)
				m = 1;
			//Calendar类中月份是从0开始算起的
			m--;
			//将给定的字段设置为给定值(即日)
			Cld.set(y, d, m);
			cDate = Cld.getTime();
			//返回值 Date类型
			return cDate;
		} else if(len == 6) {
			//分别分割12位 34位 56位
			y = Integer.parseInt(inDate.substring(0, 2));
			m = Integer.parseInt(inDate.substring(2, 4));
			d = Integer.parseInt(inDate.substring(4, 6));
			y = yc * 100 + y;
			if (y > 9999)
				y = 9999;
			if (m > 12)
				m = 12;
			if (d > 31)
				d = 31;
			if (d < 1)
				d = 1;
			if (m < 1)
				m = 1;
			//Calendar类中月份是从0开始算起的
			m--;
			//将给定的字段设置为给定值(即日)
			Cld.set(y, d, m);
			cDate = Cld.getTime();
			//返回值 Date类型
			return cDate;
		} else if(len == 8){
			//分别分割1234位 56位 78位
			y = Integer.parseInt(inDate.substring(0, 4));
			m = Integer.parseInt(inDate.substring(4, 6));
			d = Integer.parseInt(inDate.substring(6, 8));
			if (y > 9999)
				y = 9999;
			if (m > 12)
				m = 12;
			if (d > 31)
				d = 31;
			if (d < 1)
				d = 1;
			if (m < 1)
				m = 1;
			//Calendar类中月份是从0开始算起的
			m--;
			//将给定的字段设置为给定值(即日)
			Cld.set(y, d, m);
			cDate = Cld.getTime();
			//返回值 Date类型
			return cDate;
		}
		//都不符合则返回当前时间
		return cDate;
	}
	
	//md5加密
	public final static String MD5(String s) {
		byte[] btInput = s.getBytes();
		//抽象类实例化
		try {
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < md.length; i++) {
				//只取低八位
				int val = ((int) md[i]) & 0xff;
				//可能会出现0x0f--0x00只有四位的情况
				if(val < 16) {
					sb.append("0");
				}
				sb.append(Integer.toHexString(val));
			}
			return (sb.toString().toUpperCase());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//941084   910323
}
