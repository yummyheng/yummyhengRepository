package com.util.pub.content;

import com.util.pub.other.Search2Char;

/**
 * �������ݲ�����ع�����
 * @author hengzai 2013-7-18
 * @ann ��Final ��ֱ�ӵ���
 */

final public class ContentFinding {
	/**
	 *	������ֵ 
	 */
	
	//��ĳλ���ж��Ƿ���ĳֵ,�ո��������ж���һλ�����з���0
	public static int defineAItem(char[] cArray, int pos, char c) {
		char vc;
		
		for(int i = pos; i < cArray.length; i++) {
			vc = cArray[i];
			//���char�����ĳֵ��ҪѰ�ҵ�ĳֵ
			if(vc == c) {
				//�ҵ���
				return 1;
			}
			if(vc == ' ') {
				continue;
			}
			return -1;
		}
		return 0;
	}
	
	//��ĳλ���ж��Ƿ���ĳֵ,�ո��������ж���һλ�����з���0
	public static int defineAItem(String s, int pos, char c) {
		char [] cArray = s.toCharArray();
		return defineAItem(cArray, pos, c);
	}
	
	//��ĳλ�ÿ�ʼ�ж��Ƿ���char�����ڵ�ĳֵ,�ո��������ж���һλ�����з���0
	public static int defineAItem(String s, int pos, char[] in_cArray) {
		char vc;
		char[] cArray = s.toCharArray();
		for(int i = pos; i < cArray.length; i++) {
			vc = cArray[i];
			//�ո�������
			if(vc == ' ') {
				continue;
			}
			
			for(int j = 0; j < in_cArray.length; j++) {
				if((vc == in_cArray[j])) {
					return 1;
				}
				
				return -1;
			}
		}
		//����
		return 0;
	}	
	
	//��ĳλ�ÿ�ʼ�ж��Ƿ�����ͨ����Ǳ��ʽ�������Ԫ���������ǳ����������Ǳ���
	public static boolean isNetItem(String s) {
		char c[] = {
				'+','-','*','/','%','&',
				'|','!','~','>','<','=',
				'(',')','?','#'
		};
		
		char r;
		
		for(int i = 0; i < s.length(); i++) {
			r = s.charAt(i);
			for(int j = 0; j < c.length; j++) {
				if(r == c[j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	//�ж��ַ����Ƿ����ĳ���ַ����κ�һ���������ص�һ�����ֵ�λ��
	public static int checkAndBackItem(String s, char[] cArray) {
		char r;
		for(int i = 0; i < s.length(); i++) {
			r = s.charAt(i);
			for(int j = 0; j < cArray.length; j++) {
				if(r == cArray[j]) {
					return i;
				}
			}
		}
		return -1;
	}
	
	//��ĩ������ĳֵ������ĳֵ��λ�� ��ȥβ���Ŀո�
	public static int searchACharFromEnd(String st, char c) {
		//��������st����c�ȽϵĶ���
		char vc;
		//�����ж�β���ǲ��ǿո���
		boolean f = false;
		
		//��������Ϊ��
		if(st == null) {
			return -1;
		}
		
		//������0��ʼ��Ҫ��ȥ1
		int l = st.length() - 1;
		
		//��β������ �� i = l
		for(int i = l; i >= 0; i--) {
			//charAt�������ز��� + 1���ַ���Ϊ��c�ȽϵĶ�����Ϊ֮ǰ l - 1�ˣ��������ټ�1
			vc = st.charAt(i);
			//ֻҪf��false����Ҫ�ȽϵĶ����ǿո�continue ��ifѭ����������β���ǿո��ռλ
			if(!f && (vc == ' ')) {
				continue;
			} else {
				//һ��������β�����ǿո񼴿�ʼ�ۼ�
				f = true;
			}
			//��������λ��
			if(vc == c) {
				return i;
			}
		}
		//δ�ҵ�
		return -1;
	}
	
	//�������һ���ַ��ǲ���ĳֵ��������򷵻�ĳֵ��λ�� ��ȥβ���Ŀո�
	public static int searchACharAtEnd(String st, char c) {
		//��������st����c�ȽϵĶ���
		char vc;
		//�����ж�β���ǲ��ǿո���
		boolean f = false;
		
		//��������Ϊ��
		if(st == null) {
			return -1;
		}
		
		//������0��ʼ��Ҫ��ȥ1
		int l = st.length() - 1;
		for(int i = l; i >= 0; i--) {
			//charAt�������ز��� + 1���ַ���Ϊ��c�ȽϵĶ�����Ϊ֮ǰ l - 1�ˣ��������ټ�1
			vc = st.charAt(i);
			//ֻҪf��false����Ҫ�ȽϵĶ����ǿո�continue ��ifѭ����������β���ǿո��ռλ
			if(!f && (vc == ' ')) {
				continue;
			} else {
				//һ��������β�����ǿո񼴿�ʼ�ۼ�
				f = true;
			}
			
			//���ƥ���򷵻�λ�ã���ƥ���򷵻�-1
			if((vc == c)){
				return i;
			}
			return -1;
		}
		return -1;
		
	}
	
	//�������һ���ַ������Ƿ���������ֵ�����ظ�ֵ��λ�� ����ʱ��ȥβ���Ŀո�
	public static int searchACharAtEnd(String st, char[] c) {
		char vc;
		int p;
		
		//����char[]����
		for(int i = 0; i < c.length; i++) {
			vc = c[i];
			p = searchACharAtEnd(st,vc);
			if( p > -1) {
				return p;
			}
		}
		
		return -1;
	}
	
	/**
	 *	����˫ֵ 
	 */
	
	//��byte[]��Ѱ�������ַ� ���ų��������ź�˫���ż������ ���ط���1
	private static boolean search2Item(char[] c, int num, char c1, char c2, Search2Char s2r) {
		char vc;
		boolean dQuotationMarks = false;
		boolean sQuotationMarks = false;
		
		s2r.fAddr = -1;
		s2r.SAddr = -1;
		s2r.whatS = 0;
		
		//��λ��s��ʼ����
	ML:	for(int i = num; i < c.length; i++) {
			vc = c[i];
			
			//��ȥ˫����֮�������
			if(vc == '"') {
				//��һ��"��ת��Ϊfalse
				dQuotationMarks = (!dQuotationMarks);	
				continue;
			}
			if(dQuotationMarks) {
				//ֻ���ֵ�һ��"��ʱһֱcontinue��ֱ���ڶ����ĳ��֣�Ϊ���˵�˫�����ڵ����ݣ�����һֱcontinue������forѭ��
				continue;	
			}
			
			////��ȥ������֮������� ת�嵥�����ַ������Ϊ������Ҳ����������������ȥ ͨ���ж�ĩβ�Ƿ�Ϊת���ַ�/'
			if(vc == '\'') {
				//�۲�˵������ǲ���ĩβ����һ�� �����־λ�����˳�
				if(i + 1 >= c.length) {
					break;
				}
				//�����߼�λ
				sQuotationMarks = false;
				
				for(int m = i + 1; m < c.length; m++) {
					//�۲��Ƿ���ת���
					if(c[m] == '\\') {
						//�����߼�λΪtrue �����һλ��'�����MLѭ����ȥ�����ŵ����� �����߼�λ�û�false
						sQuotationMarks = true;
						//������һλ���ж�
						continue;
					}
					if(c[m] == '\'') {
						//ͨ���߼�λ�ж�ǰ����Ƿ�Ϊת���ַ�
						if(sQuotationMarks) {
							i = m;
							//continue ΪML��forѭ���ǵ����ŵ�forѭ��
							continue ML;
						} else {
							sQuotationMarks = false;
						}
					}
				}
				//һֱ���ǵ����ŵ����� ����ѭ��
				break;
			}
			
			if(vc == c1) {
				//���ص�ǰλ��
				s2r.fAddr = i;
				s2r.whatS = 1;
				return true;
			}
			
			if(vc == c2) {
				//���ص�ǰλ��
				s2r.SAddr = i;
				s2r.whatS = 2;
				return true;
			}
		}
		//�Ҳ�������
		return false;
	}
	
	//��byte[]��Ѱ�������ַ� ���ų��������ź�˫���ż������ ���ط���2
	public static boolean search2Item(String s, int num, char c1, char c2, Search2Char s2c) {
		char [] vc = s.toCharArray();
		return search2Item(vc, num, c1, c2, s2c);
	}
	
}
