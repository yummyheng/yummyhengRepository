package com.util.pub.content;

import com.util.pub.other.Search2Char;

/**
 * 文字内容查找相关工具类
 * @author hengzai 2013-7-18
 * @ann 被Final 可直接调用
 */

final public class ContentFinding {
	/**
	 *	搜索单值 
	 */
	
	//从某位置判断是否是某值,空格则跳过判断下一位，空行返回0
	public static int defineAItem(char[] cArray, int pos, char c) {
		char vc;
		
		for(int i = pos; i < cArray.length; i++) {
			vc = cArray[i];
			//如果char数组的某值是要寻找的某值
			if(vc == c) {
				//找到了
				return 1;
			}
			if(vc == ' ') {
				continue;
			}
			return -1;
		}
		return 0;
	}
	
	//从某位置判断是否是某值,空格则跳过判断下一位，空行返回0
	public static int defineAItem(String s, int pos, char c) {
		char [] cArray = s.toCharArray();
		return defineAItem(cArray, pos, c);
	}
	
	//从某位置开始判断是否是char数组内的某值,空格则跳过判断下一位，空行返回0
	public static int defineAItem(String s, int pos, char[] in_cArray) {
		char vc;
		char[] cArray = s.toCharArray();
		for(int i = pos; i < cArray.length; i++) {
			vc = cArray[i];
			//空格则跳过
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
		//空行
		return 0;
	}	
	
	//从某位置开始判断是否是普通的项，是表达式最基本单元，它或者是常量，或者是变量
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
	
	//判断字符串是否包含某组字符的任何一个，并返回第一个出现的位置
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
	
	//从末端搜索某值并返回某值的位置 略去尾部的空格
	public static int searchACharFromEnd(String st, char c) {
		//用于置入st中与c比较的对象
		char vc;
		//用于判断尾部是不是空格用
		boolean f = false;
		
		//搜索对象为空
		if(st == null) {
			return -1;
		}
		
		//索引从0开始需要减去1
		int l = st.length() - 1;
		
		//从尾部搜索 即 i = l
		for(int i = l; i >= 0; i--) {
			//charAt方法返回参数 + 1的字符作为和c比较的对象，因为之前 l - 1了，即不用再加1
			vc = st.charAt(i);
			//只要f是false或者要比较的对象是空格continue 此if循环将不计算尾部是空格的占位
			if(!f && (vc == ' ')) {
				continue;
			} else {
				//一旦索引到尾部不是空格即开始累计
				f = true;
			}
			//返回索引位置
			if(vc == c) {
				return i;
			}
		}
		//未找到
		return -1;
	}
	
	//搜索最后一个字符是不是某值，如果是则返回某值的位置 略去尾部的空格
	public static int searchACharAtEnd(String st, char c) {
		//用于置入st中与c比较的对象
		char vc;
		//用于判断尾部是不是空格用
		boolean f = false;
		
		//搜索对象为空
		if(st == null) {
			return -1;
		}
		
		//索引从0开始需要减去1
		int l = st.length() - 1;
		for(int i = l; i >= 0; i--) {
			//charAt方法返回参数 + 1的字符作为和c比较的对象，因为之前 l - 1了，即不用再加1
			vc = st.charAt(i);
			//只要f是false或者要比较的对象是空格continue 此if循环将不计算尾部是空格的占位
			if(!f && (vc == ' ')) {
				continue;
			} else {
				//一旦索引到尾部不是空格即开始累计
				f = true;
			}
			
			//如果匹配则返回位置，不匹配则返回-1
			if((vc == c)){
				return i;
			}
			return -1;
		}
		return -1;
		
	}
	
	//搜索最后一个字符数组是否符合里面的值并返回该值的位置 计算时略去尾部的空格
	public static int searchACharAtEnd(String st, char[] c) {
		char vc;
		int p;
		
		//遍历char[]数组
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
	 *	搜索双值 
	 */
	
	//从byte[]中寻找两个字符 需排除掉单引号和双引号间的内容 重载方法1
	private static boolean search2Item(char[] c, int num, char c1, char c2, Search2Char s2r) {
		char vc;
		boolean dQuotationMarks = false;
		boolean sQuotationMarks = false;
		
		s2r.fAddr = -1;
		s2r.SAddr = -1;
		s2r.whatS = 0;
		
		//从位置s开始搜索
	ML:	for(int i = num; i < c.length; i++) {
			vc = c[i];
			
			//略去双引号之间的内容
			if(vc == '"') {
				//第一个"号转变为false
				dQuotationMarks = (!dQuotationMarks);	
				continue;
			}
			if(dQuotationMarks) {
				//只出现第一个"号时一直continue，直到第二个的出现，为了滤掉双引号内的内容，否则一直continue至跳出for循环
				continue;	
			}
			
			////略去单引号之间的内容 转义单引号字符，如果为单引号也将所有引号内容略去 通过判断末尾是否为转义字符/'
			if(vc == '\'') {
				//观察此单引号是不是末尾的那一个 是则标志位不变退出
				if(i + 1 >= c.length) {
					break;
				}
				//设立逻辑位
				sQuotationMarks = false;
				
				for(int m = i + 1; m < c.length; m++) {
					//观察是否是转义符
					if(c[m] == '\\') {
						//设立逻辑位为true 如果下一位是'则进入ML循环略去单引号的内容 否则将逻辑位置回false
						sQuotationMarks = true;
						//进入下一位的判断
						continue;
					}
					if(c[m] == '\'') {
						//通过逻辑位判断前面的是否为转义字符
						if(sQuotationMarks) {
							i = m;
							//continue 为ML的for循环非单引号的for循环
							continue ML;
						} else {
							sQuotationMarks = false;
						}
					}
				}
				//一直都是单引号的内容 跳出循环
				break;
			}
			
			if(vc == c1) {
				//返回当前位置
				s2r.fAddr = i;
				s2r.whatS = 1;
				return true;
			}
			
			if(vc == c2) {
				//返回当前位置
				s2r.SAddr = i;
				s2r.whatS = 2;
				return true;
			}
		}
		//找不到内容
		return false;
	}
	
	//从byte[]中寻找两个字符 需排除掉单引号和双引号间的内容 重载方法2
	public static boolean search2Item(String s, int num, char c1, char c2, Search2Char s2c) {
		char [] vc = s.toCharArray();
		return search2Item(vc, num, c1, c2, s2c);
	}
	
}
