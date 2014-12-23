package com.util.pub.container;

import java.io.File;
import java.util.HashMap;

/**
 * �ɱ���ַ��������� 
 * @author hengzai 2013-7-16
 */

public class ChangableString {
	
	//����
	public int l;
	public String[] sa;
	public int cp;
	//Ĭ����С��50��
	public final int minLineNo = 50;
	
	//���캯�� �Դ���������ֵ�����ټӹ���ʼ��
	public  ChangableString(int c) {
		//cΪ����������������cС��Ĭ��ʱ����ΪĬ��
		if(c < minLineNo) {
			c = minLineNo;
		}
		
		//��������Ϊc���ַ�������
		sa = new String[c];
		
		l = 0;
		cp = c;
	}
	
	//�ַ����������� ԭ��:����ǰ���ַ������鱣�沢�����½�������
	public void extend() {
		int n = cp;
		//������������������
		cp = cp + cp/2;
		String ns[] = new String [cp];
		//nΪδ�����������
		for(int i = 0; i < n; i++) {
			ns[i] = sa[i];
		}
		sa = ns;
	}
	
	//����l��������cpʱ��������
	public void addLine(String s) {
		sa[l] = s;
		l++;
		
		if(l >= cp) {
			extend();
		}
	}
	
	
}
