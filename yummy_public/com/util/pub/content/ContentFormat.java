package com.util.pub.content;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 格式对齐相关类
 * @author hengzai 时间：2013-7-31
 */

public class ContentFormat {
	
	//将值(整形数据类型)转换为固定长度的串，右对齐，通常用于对齐输出
	public static String initToString(int i, int l) {
		//初始化希望的数组并赋上空值
		char ca[] = new char[l];
		for(int j = 0; j < l; j++) {
			ca[j] = ' ';
		}
		
		//转化为字符串类型
		String s = String.valueOf(ca);
		
		s = s + i;
		
		//遍历是从第零位开始
		int k = s.length() - 1;
		if(s.charAt(k) != ' ') {
			//如果数值超出l位，则首位置#作为提醒 本来是k位的  因为加上#变成k + 1位
			s = "#" + s.substring(k + 1);
		} else {
			s = s.substring(k);
		}
		
		return s;
	}
	
	//将字符串转换为固定长度的串，不足的部分补空格
	public static String stringToFix(String in_s, int l, char fillC, char dir) {
		String result;
		char ca[] = new char[l];
		if(in_s != null && in_s.length() == l) {
			return in_s;
		}
		
		//将要填充的字符放入char数组中
		for(int i = 0; i < l; i++) {
			ca[i] = fillC;
		}
		String s= String.valueOf(ca);
		
		int k;
		
		//右对齐的情况,左边添上要填的东西
		if(dir!='l' && dir!='L') {
			result = s + in_s;
			//判断是否超出界限
			k = result.length() - 1;
			//看看超出界限的第一个值是否是填充值
			if(result.charAt(k) != fillC) {
				//如果数值超出预想的变量界限l，则首位置#作为提醒 本来是k位的  因为加上#变成k + 1位 即截取第k + 1位后所有的值
				result = "#" + result.substring(k + 1);
			} else {
				//截取第k位后所有的值
				result = result.substring(k);
			} 
		//左对齐的情况
		} else {
			result = in_s + s;
			//判断超出变量界限
			if(s.charAt(l - 1)!= fillC) {
				//截取第0位到第l - 1位的值
				result = result.substring(0, l - 1) + "#";
			} else {
				//截取第0位到l位的值
				result = result.substring(0, l);
			}
		}
		
		return s;
	}
	
	//将一个可能很长的字符串，通过增加换行符，格式化成宽度在l内的字符串以方便显示
	public static String lineFeedString(String s, int l) {
		//输入的字符串条件判断
		if(s == null) {
			return "";
		} else if(s.length() < 1){
			return s;
		}
		//初始化 len为字符串总长度
		int len = s.length();
		int j = 0;
		//比StringBuffer更快
		StringBuilder result = new StringBuilder();
		
		//死循环 逐字符进行遍历
		while(true) {
			//判断是否满足换行条件
			int p = j + l;
			//同属于p == len的情况处理
			if(p > len) {
				p = len;
			}
			//p长度为字符串总长度时，添加并退出
			if(p == len) {
				//把该位置的字符添加上去并退出
				result.append(s.substring(j, p));
				break;
			} else {
				//添加上换行符
				result.append(s.subSequence(j,  p) + "\n");
			}
		}
		//返回整个String类型
		return result.substring(0);
	}
	
	//对原有的list进行排序
	public synchronized static <T> List<?> sortList(List l, Comparator cmp) {
		//条件判断
		if(l == null) {
			return null;
		}
		if(l.size() < 1) {
			return l;
		}
		//将其转化成一个数组
		T[] elements = (T[]) l.toArray();
		//数组排序 根据指定比较器产生的顺序对指定对象数组进行排序
		Arrays.sort(elements, cmp);
		//移除所有list的项
		l.clear();
		for(int i = 0; i < elements.length; i++) {
			l.add(elements[i]);
		}
		return l;
	}
	
	//复制一个list并对此进行排序
	public synchronized static <T> List<?> copyThenSortList(List l, Comparator cmp) {
		if(l == null) {
			return null;
		}
		if(l.size() < 1) {
			return l;
		}
		T[] elements = (T[]) l.toArray();
		Arrays.sort(elements, cmp);
		
		List newL = new ArrayList(l.size());
		for(int i = 0; i < elements.length; i++) {
			newL.add(elements[i]);
		}
		return newL;
	}
}
