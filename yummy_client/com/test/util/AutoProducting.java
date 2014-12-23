package com.test.util;

import java.lang.reflect.Field;
import java.util.HashMap;


public class AutoProducting {
	
	/**
	 * 自动生成set方法
	 * 适用于入参是字符串parm的方法
	 * @param in_data
	 * @param fields
	 * @param i
	 * @param parm
	 */
	public static <T> void AutoMakeSetMethod(T in_data, Field[] fields, int i, String parm) {
		 String fieldsName = fields[i].getName();
		 //自动生成set方法
		 StringBuffer sb = new StringBuffer();       
		 sb.append("set");   
		 //取出单词第一位大写
		 sb.append(fields[i].getName().substring(0, 1).toUpperCase());    
		 //取出剩余单词的后面部分并与第一位合并
		 sb.append(fields[i].getName().substring(1));
		 //自动生成对应的数据类型
		 Class[] parameterTypes = new Class[1];       
         parameterTypes[0] = fields[i].getType();
		 try {
			String methodName = sb.toString();
			String fieldType = fields[i].getType().toString();
			//如果数据类型为整形则调用int的set方法
			if(fields[i].getType().equals(int.class)) {
	        	 int i_parm = Integer.parseInt(parm);
	        	 //方法名-参数类型-调用类-入参 
	        	 in_data.getClass().getMethod(methodName, parameterTypes[0]).invoke(in_data, i_parm);
	         } else {
	        	 return;
//	        	 in_shell.getClass().getMethod(methodName, parameterTypes[0]).invoke(in_shell, parm);
	         }
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
	}
	
	/**
	 * 自动生成get方法
	 * 适用于出参是字符串的方法
	 * @param in_data
	 * @param fields
	 * @param i
	 * @param parm
	 */
	public static <T> String autoMakeGetMethod(T in_data, Field[] fields, int i) {
		String fieldsName = fields[i].getName();
		//自动生成get方法
		StringBuffer sb = new StringBuffer();       
		sb.append("get");   
		//取出单词第一位大写
		sb.append(fields[i].getName().substring(0, 1).toUpperCase());    
		//取出剩余单词的后面部分并与第一位合并
		sb.append(fields[i].getName().substring(1));
		//自动生成对应的数据类型
		Class[] parameterTypes = new Class[1];       
        parameterTypes[0] = fields[i].getType();
		try {
			String methodName = sb.toString();
			String fieldType = fields[i].getType().toString();
			//如果数据类型为整形则调用int的set方法
			if(fields[i].getType().equals(int.class)) {
	        	 //方法名-参数类型-调用类-入参 
	        	 int out_data = (Integer)in_data.getClass().getMethod(methodName).invoke(in_data);
	        	 //
	        	 return String.valueOf(out_data);
	        } else {
	        	 return "";
//	        	 in_shell.getClass().getMethod(methodName, parameterTypes[0]).invoke(in_shell, parm);
	        }
		} catch (Exception e1) {
			e1.printStackTrace();
			return "ERROR";
		}
	}
}
