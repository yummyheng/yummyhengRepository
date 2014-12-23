package com.util.pub.content;

import com.util.pub.container.ChangableString;
import com.util.pub.container.StringConvert;
import com.util.pub.other.Search2Char;


/**
 * �������ݲ�����ع����� 
 * @author hengzai 2013-7-16
 * @ann ��Final ��ֱ�ӵ���
 */

final public class ContentDealing {
	
	/**
	 * �������
	 */
	
	//���ַ�����ÿ���ַ�֮������ַ�cֵ
	public static String putCharBetweenString(String s, char c) {
		//�����ж�
		if(s == null || s.length() < 1) {
			return "";
		}
		//��ʼ������
		char[] ca = s.toCharArray();
		int newLen = ca.length + ca.length - 1;
		char[] rc = new char[newLen];
		boolean f = false;
		int n = 0;
		//������ַ��������cֵ
		for(int i = 0; i < ca.length - 1; i++) {
			rc[n] = ca[i];
			rc[n + 1] = c;
			n += 2;
		}
		//��ԭ��������һ��ֵ������������ĩβ�������forѭ����û�����ԭ��������һ��ֵ
		rc[n] = ca[ca.length - 1];
		
		String result = String.valueOf(rc);
		return result;
	}
	
	//�����е��ַ��� r �滻 �ַ���s 
	public static String replaceChar(String st, char s, char r) {
		if(st == null) {
			return null;
		}
		
		if(s == r) {
			return st;
		}
		
		//���ַ������Ϊ�ַ�������
		char[] vc = st.toCharArray();
		
		//���������ƶ��ַ����滻
		for(int i = 0; i < vc.length; i++) {
			if(vc[i] == s) {
				vc[i] = r;
			}
		}
		
		//��char[]����ת�����ַ�������
		return String.valueOf(vc);
	}
	
	//���ַ���s�д�λ��p֮���ָ�����Ӵ��û���������Ӵ�
	public static String changeString(String s,int pos, String s1, String s2, boolean cAll) {
		int k;
		k = s.indexOf(s1, pos);
		//��Ѱ�����ؼ��ַ���
		if(k < 0) {
			return s;
		}
		//ɾ��ָ�����ַ�������������ָ�����ַ���
		s = s.substring(0, k) + s2 + s.substring(k + s1.length());
		if(cAll) {
			//��������
			pos = k + s2.length();
			if((pos - s1.length()) >= s.length()) {
				return s;
			}
			return changeString(s, pos, s1, s2, cAll);
		} else {
			return s;
		}
	}
	
	//���ַ���s�д�λ��position��ʼ����ƥ������ţ����������ڵ��Ӵ�����sub ���ط���1 private���� ���ⷢ��Ϊ����2
	private static int SearchBracketsPositionAndContent(String s, int position,int pS, char lBrackets, char rBrackets,  int deep, StringConvert target) {
		Search2Char s2c = new Search2Char();
		int rc = 0;
		if(deep == 0) {
			pS = position;
		}
		
		while(position < s.length()) {
			//�ҵ����ŷ���true �Ҳ������ŷ���false
			if(ContentFinding.search2Item(s, position, lBrackets, rBrackets, s2c)) {
				//���ҵ��������ŷ����ҵ�������
				if(s2c.whatS == 1) {
					deep++;
					//num�Ѿ�����������ŵ�λ�ã����и÷����������Ϸ��������ŵ�λ��
					rc = SearchBracketsPositionAndContent(s, s2c.fAddr + 1, pS, lBrackets, rBrackets, deep, target);
					
					//�����ź������ŵ�λ�ò����Ϲ涨
					if(rc < 0 || (rc - 1) - (pS + 1) < 0) {
						//�׳��쳣
						
					}
					//��ȡ�����ڵ�����
					if(target != null) {
						target.s = s.substring(pS + 1, rc -1);
					}
					//���������ŵ�λ��
					return rc;
				} else {
					//�ҵ������ŵ����
					deep--;
					//����λ��
					position = s2c.SAddr + 1;
					//��֮ǰ�Ѿ��ҵ������ŵĻ�deepΪ1���Լ������ û���ҵ�����ѭ��
					if(deep == 0) {
						return position;
					}
					continue;
				}
			}
			//Ѱ����һλ
			position++;
		}
		//����֮��û�з�������
		return -1;
	}
	
	//���ַ���s�д�λ��p��ʼ����ƥ������ţ����������ڵ��Ӵ�����sub ���ط���2
	public static int SearchBracketsPositionAndContent(String s, int position, char lBrackets, char rBrackets, StringConvert target) {
		int rc;
		rc = SearchBracketsPositionAndContent(s, position, 0, lBrackets, rBrackets, 0, target);
		return rc;
	}
	
	//���ַ����дӵ�һλ��ʼ�������Ե���������������ȡ�� ���ط���1
	public static String getBracketsInside(String s, char lBrackets, char rBrackets) {
		//�����Ǵ�0��ʼ��
		int l = s.length() - 1;
		//�ַ���û��ֵ
		if(l < 0) {
			return s;
		}
		return getBracketsInside(s, 0, lBrackets, rBrackets, true);
	}
	
	//���ַ����дӹ̶�λ��p��ʼ�������Ե���������������ȡ�� ���ط���2
	public static String getBracketsInside(String s, int in_position, char lBrackets, char rBrackets, boolean onEnd) {
		int l = s.length() - 1;
		//�ַ���û��ֵ
		if(l < 0) {
			return s;
		}
		
		//�̶�ҪѰ�ҵ�λ���Ҳ�����Ӧ������
		if(s.charAt(in_position) != lBrackets) {
			return s;
		}
		//�½��Զ�ת����String�� �����ַ��� ������SearchBracketsPositionAndContent��������֮���ַ��������
		StringConvert target = new StringConvert();
		//����λ��
		int position = SearchBracketsPositionAndContent(s, in_position, lBrackets, rBrackets, target);
		
		if(onEnd) {
			if(position < 1) {
				return s;
			}
		}
		//������Ҫ���ַ���
		return target.s;
	}
	
	/**
	 * �ַ�����ȥ��ĳ���ַ�ϵ��
	 */
	
	//ȥ�����ʵ�ĳ���ַ� ����������Ļ�������if�Ӿ��м���||
	public static String delOneChar(String s, char out_c) {
		//���õ��ʵ���󳤶�Ϊ200
		char word[] = new char[200];
		int j = 0;
		char c;
		
		//��Stringת����char����
		char [] arrayChar = s.toCharArray();
		//����char����
		for(int i = 0; i < arrayChar.length; i++) {
			c = arrayChar[i];
			//����Ǹ�ɾȥ���ַ����Թ�������Ž��µ�������
			if(c == out_c) {
				continue;
			}
			word[j] = c;
			j++;
		}
		
//		//δ֪��;
//		word[j] = 0;
		
		//�൱��valueOf
		return String.copyValueOf(word, 0, j);
	}
	
	//ɾ���ַ���������������ַ�
	public static String delNonNumber(String s) {
		int sL = s.length();
		char target[] = new char[sL];
		int j = 0;
		char c;
		
		char[] charArray = s.toCharArray();
		// ����ԭ�ַ���Ԫ��
		for(int i = 0; i < s.length(); i++) {
			c = charArray[i];
			if(c < '0' || c > '9') {
				continue;
			}
			target[j] = c;
			j++;
		}
		return String.copyValueOf(target, 0, j);
	}
	
	//ɾ���ַ����еĿո����quotationΪtrue��""����Ŀո�ɾ��
	public static String delSpace(String s, boolean quotation) {
		int sL = s.length();
		char target[] = new char[sL];
		int n = 0;
		char c;
		
		char[] charArray = s.toCharArray();
		// ����ԭ�ַ���Ԫ��
		for(int i = 0; i < s.length(); i++) {
			c = charArray[i];
			if(c == ' ') {
				continue;
			}
			target[n] = c;
			n++;
			//�ж��Ƿ�����˫������ص����� ����ѭ�������ո����еĶ������ƽ�ȥ
			if(c == '"' && quotation) {
				//Ѱ����һ��˫����
				for(int j = i + 1; j < charArray.length; j++) {
					target[n] = charArray[j];
					n++;
					//����˫�����˳� ��˫�����Ѿ���¼��ȥ�����
					if(charArray[j] == '"') {
						i = j;
						break;
					}
				}
				continue;
			}
		}
		if(n < sL) {
			target[n] = 0;
		}
		
		return String.copyValueOf(target, 0, n);
	}
	
	//���ַ���������������c��λ�ã��Թ�ǰ���ո��""�ڵ��ַ�,����λ��
	public static int searchACharOLSB1(String s, int pos, char c) {
		char[] vc = s.toCharArray();
		boolean flag = false;
		//��posλ�ú���ַ����б���
		for(int i = pos; i < vc.length; i++) {
			if(!flag && (vc[i] == ' ')) {
				continue;
			} else {
				flag = true;
			}
			
			//˫���ŵ����
			if(vc[i] == '"') {
				for(int j = i + 1; j < vc.length; j++) {
					if(vc[i] == '"') {
						i = j;
						break;
					}
				}
			}
			
			//�ҵ��ض��ַ��򷵻ظ��ַ���λ��
			if((vc[i] == c)) {
				return i;
			}
		}
		
		return -1;
	}
	
	//����β���Ƿ�Ϊ���ַ���s1�������滻s2
	public static String replaceLastStr(String in_s, String s1, String s2) {
		String out_s = null;

		//�ж��Ƿ���Ѱ��s1
		if(in_s.endsWith(s1)) {
			int p = in_s.lastIndexOf(s1);
			out_s = in_s.substring(0, p) + s2;
		} else {
			out_s = in_s;
		}
		
		return out_s;
	}
}
