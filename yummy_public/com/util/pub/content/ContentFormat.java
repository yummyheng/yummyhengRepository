package com.util.pub.content;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * ��ʽ���������
 * @author hengzai ʱ�䣺2013-7-31
 */

public class ContentFormat {
	
	//��ֵ(������������)ת��Ϊ�̶����ȵĴ����Ҷ��룬ͨ�����ڶ������
	public static String initToString(int i, int l) {
		//��ʼ��ϣ�������鲢���Ͽ�ֵ
		char ca[] = new char[l];
		for(int j = 0; j < l; j++) {
			ca[j] = ' ';
		}
		
		//ת��Ϊ�ַ�������
		String s = String.valueOf(ca);
		
		s = s + i;
		
		//�����Ǵӵ���λ��ʼ
		int k = s.length() - 1;
		if(s.charAt(k) != ' ') {
			//�����ֵ����lλ������λ��#��Ϊ���� ������kλ��  ��Ϊ����#���k + 1λ
			s = "#" + s.substring(k + 1);
		} else {
			s = s.substring(k);
		}
		
		return s;
	}
	
	//���ַ���ת��Ϊ�̶����ȵĴ�������Ĳ��ֲ��ո�
	public static String stringToFix(String in_s, int l, char fillC, char dir) {
		String result;
		char ca[] = new char[l];
		if(in_s != null && in_s.length() == l) {
			return in_s;
		}
		
		//��Ҫ�����ַ�����char������
		for(int i = 0; i < l; i++) {
			ca[i] = fillC;
		}
		String s= String.valueOf(ca);
		
		int k;
		
		//�Ҷ�������,�������Ҫ��Ķ���
		if(dir!='l' && dir!='L') {
			result = s + in_s;
			//�ж��Ƿ񳬳�����
			k = result.length() - 1;
			//�����������޵ĵ�һ��ֵ�Ƿ������ֵ
			if(result.charAt(k) != fillC) {
				//�����ֵ����Ԥ��ı�������l������λ��#��Ϊ���� ������kλ��  ��Ϊ����#���k + 1λ ����ȡ��k + 1λ�����е�ֵ
				result = "#" + result.substring(k + 1);
			} else {
				//��ȡ��kλ�����е�ֵ
				result = result.substring(k);
			} 
		//���������
		} else {
			result = in_s + s;
			//�жϳ�����������
			if(s.charAt(l - 1)!= fillC) {
				//��ȡ��0λ����l - 1λ��ֵ
				result = result.substring(0, l - 1) + "#";
			} else {
				//��ȡ��0λ��lλ��ֵ
				result = result.substring(0, l);
			}
		}
		
		return s;
	}
	
	//��һ�����ܺܳ����ַ�����ͨ�����ӻ��з�����ʽ���ɿ����l�ڵ��ַ����Է�����ʾ
	public static String lineFeedString(String s, int l) {
		//������ַ��������ж�
		if(s == null) {
			return "";
		} else if(s.length() < 1){
			return s;
		}
		//��ʼ�� lenΪ�ַ����ܳ���
		int len = s.length();
		int j = 0;
		//��StringBuffer����
		StringBuilder result = new StringBuilder();
		
		//��ѭ�� ���ַ����б���
		while(true) {
			//�ж��Ƿ����㻻������
			int p = j + l;
			//ͬ����p == len���������
			if(p > len) {
				p = len;
			}
			//p����Ϊ�ַ����ܳ���ʱ����Ӳ��˳�
			if(p == len) {
				//�Ѹ�λ�õ��ַ������ȥ���˳�
				result.append(s.substring(j, p));
				break;
			} else {
				//����ϻ��з�
				result.append(s.subSequence(j,  p) + "\n");
			}
		}
		//��������String����
		return result.substring(0);
	}
	
	//��ԭ�е�list��������
	public synchronized static <T> List<?> sortList(List l, Comparator cmp) {
		//�����ж�
		if(l == null) {
			return null;
		}
		if(l.size() < 1) {
			return l;
		}
		//����ת����һ������
		T[] elements = (T[]) l.toArray();
		//�������� ����ָ���Ƚ���������˳���ָ�����������������
		Arrays.sort(elements, cmp);
		//�Ƴ�����list����
		l.clear();
		for(int i = 0; i < elements.length; i++) {
			l.add(elements[i]);
		}
		return l;
	}
	
	//����һ��list���Դ˽�������
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
