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
 * ���ݴ���
 * @author hengzai ʱ�䣺2013-7-31
 */
public class DataDealing {
	private static BigDecimal 	ZeroBD = new BigDecimal("0.000000001");
	
	//��ú����BigDecimal�ַ���
	public static String getRealBD(String s) {
		if(s == null) {
			return s;
		}
		
		//��ʼ����������
		BigDecimal bd = null;
		bd = new BigDecimal(s);
		//С����󳬹�7λ����0ʱת����0.0
		if(bd.compareTo(ZeroBD) <= 0) {
			return "0.0";
		} else {
			s = strToFormatedStr(s, 8, true, false);
			return s;
		}
	}
	
	//���ַ���ת��Ϊ���̶�С��λ���ַ��� ���ط���1
	public static String strToFormatedStr(String in_s, int dec, boolean halfUp, boolean millSpt) {
		return strToFormatedStr(in_s, dec, halfUp, millSpt, false);
	}
	
	//���ַ���ת��Ϊ���̶�С��λ���ַ��� ���ط���2
	public static String strToFormatedStr(String in_s, int dec, boolean halfUp, boolean millSpt, boolean zero2Back) {
		//������,ȡ��
		in_s = ContentDealing.delOneChar(in_s, ',');
		//��ʼ������
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
		//�Ƿ����ǧ��λ����
		if(millSpt) {
			if(dec == 0) {
				form = "#,##0";
			//�Ƿ���С��λ
			} else {
				String zero = String.valueOf(ca);
				form = "#,##0." + zero;
			}
		//������ǧ��λ����	
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
		
		//�Ƿ�ʹС����󳬹�7λ����0ȡ����ֵ
		if(zero2Back) {
			if(result.abs().compareTo(new BigDecimal("0.00000000001")) < 1) {
				return "";
			}
		}
		
		//��������
		if(halfUp) {
			result = result.setScale(dec, RoundingMode.HALF_UP);
		} else {
			result = result.setScale(dec, RoundingMode.DOWN);
		}
		
		out = DecFormat.format(result);
		
		return out;
	}
	
	//���ַ���ת��Ϊ����
	public static Date gainDateByStr(String inDate) {
		//ʹ��Ĭ��ʱ�������Ի������һ������
		Calendar Cld = Calendar.getInstance();
		//����һ����Ԫ�����ڵĺ���ƫ����
		Date cDate = Cld.getTime();
		
		int y = Cld.get(Calendar.YEAR);
		int m = Cld.get(Calendar.MONTH);
		int d = Cld.get(Calendar.DATE);
		
		int yc = y / 100;
		
		//���û���������ڣ��򷵻ص�ǰ����
		if(inDate == null || inDate.length() < 1) {
			return cDate;
		}
		
		int len = inDate.length();
		for(int i = 0; i < len; i++) {
			//����ַ������ݲ��ǰ����������򷵻ص�ǰ����
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
			//���������ֶ�����Ϊ����ֵ(����)
			Cld.set(Calendar.DATE, d);
			cDate = Cld.getTime();
			//����ֵ Date����
			return cDate;
		} else if(len == 3) {
			//��һλ
			m = Integer.parseInt(inDate.substring(0, 1));
			//�ڶ���λ
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
			//Calendar�����·��Ǵ�0��ʼ�����
			m--;
			//���������ֶ�����Ϊ����ֵ(����)
			Cld.set(y, d, m);
			cDate = Cld.getTime();
			//����ֵ Date����
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
			//Calendar�����·��Ǵ�0��ʼ�����
			m--;
			//���������ֶ�����Ϊ����ֵ(����)
			Cld.set(y, d, m);
			cDate = Cld.getTime();
			//����ֵ Date����
			return cDate;
		} else if(len == 6) {
			//�ֱ�ָ�12λ 34λ 56λ
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
			//Calendar�����·��Ǵ�0��ʼ�����
			m--;
			//���������ֶ�����Ϊ����ֵ(����)
			Cld.set(y, d, m);
			cDate = Cld.getTime();
			//����ֵ Date����
			return cDate;
		} else if(len == 8){
			//�ֱ�ָ�1234λ 56λ 78λ
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
			//Calendar�����·��Ǵ�0��ʼ�����
			m--;
			//���������ֶ�����Ϊ����ֵ(����)
			Cld.set(y, d, m);
			cDate = Cld.getTime();
			//����ֵ Date����
			return cDate;
		}
		//���������򷵻ص�ǰʱ��
		return cDate;
	}
	
	//md5����
	public final static String MD5(String s) {
		byte[] btInput = s.getBytes();
		//������ʵ����
		try {
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < md.length; i++) {
				//ֻȡ�Ͱ�λ
				int val = ((int) md[i]) & 0xff;
				//���ܻ����0x0f--0x00ֻ����λ�����
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
