package com.util.pub.container;

/**
 * �Զ�ת���������͵�String��
 * ���������ΪString��������
 * @author hengzai ʱ�䣺2013��7��21��
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
