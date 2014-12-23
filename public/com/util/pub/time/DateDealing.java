package com.util.pub.time;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.util.pub.data.DataDealing;

/**
 * ���ڴ�����
 * @author hengzai ʱ�䣺2013-8-1
 */

public class DateDealing {
	//������������֮����������
	public static int getDatesDifference(String date1, String date2) {
		//�����ж�
		if(date1 == null || date1.trim().equals("")) {
			return -1;
		}
		if(date2 == null || date2.trim().equals("")) {
			return -1;
		}
		
		int days = 0;
		
		//����long����
		Date d1 = DataDealing.gainDateByStr(date1);
		Date d2 = DataDealing.gainDateByStr(date2);
		//���ݳ��Ƚϴ� ��Ϊd1��d2�Ǻ��뼶 
		long i1 = d1.getTime();
		long i2 = d2.getTime();
		//������������������ǿת��ת��Ϊint����
		long temp = (i1 - i2) / (24 * 60 * 60 * 1000);
		temp = Math.abs(temp);
		days = (int) temp;
		
		//���ز���
		return days;
	}
	
	//��һ�����ڼ��ϻ��߼�ȥ���죬�����������
	public static String dateCount(String in_date, int number) {
		//���ý�һ���ʱ��ת���ɺ��������
		long dayms = 24 * 60 * 60 * 1000;
		
		if(in_date == null || in_date.trim().equals("")) {
			return in_date;
		}
		
		Date date = DataDealing.gainDateByStr(in_date);
		//ȡ��ʱ�䣬��λΪ����
		long baseDatems = date.getTime();
		//���м���
		long result = number * dayms + baseDatems;
		
		//���и�ʽת��������
		SimpleDateFormat formatter = new SimpleDateFormat();
		return formatter.format(result);
		
	}
}
