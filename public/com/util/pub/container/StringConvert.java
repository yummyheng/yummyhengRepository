package com.util.pub.container;

/**
 * 自动转化数据类型的String类
 * 此类可以作为String的容器类
 * @author hengzai 时间：2013年7月21日
 */

public class StringConvert {
	public String s;
	public boolean isChanged;
	
	public StringConvert () {
		
	}
	
	public StringConvert (String in_s) {
		s = in_s;
	}
	
	public StringConvert (int in_s) {
		s = String.valueOf(in_s);
	}
}
