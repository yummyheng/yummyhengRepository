package com.test.util;

import java.lang.reflect.Field;
import java.util.HashMap;


public class AutoProducting {
	
	/**
	 * �Զ�����set����
	 * ������������ַ���parm�ķ���
	 * @param in_data
	 * @param fields
	 * @param i
	 * @param parm
	 */
	public static <T> void AutoMakeSetMethod(T in_data, Field[] fields, int i, String parm) {
		 String fieldsName = fields[i].getName();
		 //�Զ�����set����
		 StringBuffer sb = new StringBuffer();       
		 sb.append("set");   
		 //ȡ�����ʵ�һλ��д
		 sb.append(fields[i].getName().substring(0, 1).toUpperCase());    
		 //ȡ��ʣ�൥�ʵĺ��沿�ֲ����һλ�ϲ�
		 sb.append(fields[i].getName().substring(1));
		 //�Զ����ɶ�Ӧ����������
		 Class[] parameterTypes = new Class[1];       
         parameterTypes[0] = fields[i].getType();
		 try {
			String methodName = sb.toString();
			String fieldType = fields[i].getType().toString();
			//�����������Ϊ���������int��set����
			if(fields[i].getType().equals(int.class)) {
	        	 int i_parm = Integer.parseInt(parm);
	        	 //������-��������-������-��� 
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
	 * �Զ�����get����
	 * �����ڳ������ַ����ķ���
	 * @param in_data
	 * @param fields
	 * @param i
	 * @param parm
	 */
	public static <T> String autoMakeGetMethod(T in_data, Field[] fields, int i) {
		String fieldsName = fields[i].getName();
		//�Զ�����get����
		StringBuffer sb = new StringBuffer();       
		sb.append("get");   
		//ȡ�����ʵ�һλ��д
		sb.append(fields[i].getName().substring(0, 1).toUpperCase());    
		//ȡ��ʣ�൥�ʵĺ��沿�ֲ����һλ�ϲ�
		sb.append(fields[i].getName().substring(1));
		//�Զ����ɶ�Ӧ����������
		Class[] parameterTypes = new Class[1];       
        parameterTypes[0] = fields[i].getType();
		try {
			String methodName = sb.toString();
			String fieldType = fields[i].getType().toString();
			//�����������Ϊ���������int��set����
			if(fields[i].getType().equals(int.class)) {
	        	 //������-��������-������-��� 
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
