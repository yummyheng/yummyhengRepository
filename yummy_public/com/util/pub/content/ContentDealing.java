package com.util.pub.content;

import com.util.pub.container.ChangableString;
import com.util.pub.container.StringConvert;
import com.util.pub.other.Search2Char;


/**
 * 文字内容操作相关工具类 
 * @author hengzai 2013-7-16
 * @ann 被Final 可直接调用
 */

final public class ContentDealing {
	
	/**
	 * 操作类别
	 */
	
	//在字符串的每个字符之间加入字符c值
	public static String putCharBetweenString(String s, char c) {
		//条件判断
		if(s == null || s.length() < 1) {
			return "";
		}
		//初始化参数
		char[] ca = s.toCharArray();
		int newLen = ca.length + ca.length - 1;
		char[] rc = new char[newLen];
		boolean f = false;
		int n = 0;
		//往逐个字符后面插入c值
		for(int i = 0; i < ca.length - 1; i++) {
			rc[n] = ca[i];
			rc[n + 1] = c;
			n += 2;
		}
		//将原数组的最后一个值放入输出数组的末尾，上面的for循环并没有输出原数组的最后一个值
		rc[n] = ca[ca.length - 1];
		
		String result = String.valueOf(rc);
		return result;
	}
	
	//用所有的字符串 r 替换 字符串s 
	public static String replaceChar(String st, char s, char r) {
		if(st == null) {
			return null;
		}
		
		if(s == r) {
			return st;
		}
		
		//将字符串拆分为字符串数组
		char[] vc = st.toCharArray();
		
		//遍历并将制定字符串替换
		for(int i = 0; i < vc.length; i++) {
			if(vc[i] == s) {
				vc[i] = r;
			}
		}
		
		//将char[]类型转换成字符串类型
		return String.valueOf(vc);
	}
	
	//将字符串s中从位置p之后的指定的子串置换成另外的子串
	public static String changeString(String s,int pos, String s1, String s2, boolean cAll) {
		int k;
		k = s.indexOf(s1, pos);
		//搜寻不到关键字符串
		if(k < 0) {
			return s;
		}
		//删除指定的字符串并增加另外指定的字符串
		s = s.substring(0, k) + s2 + s.substring(k + s1.length());
		if(cAll) {
			//迭代程序
			pos = k + s2.length();
			if((pos - s1.length()) >= s.length()) {
				return s;
			}
			return changeString(s, pos, s1, s2, cAll);
		} else {
			return s;
		}
	}
	
	//从字符串s中从位置position开始搜索匹配的括号，并将括号内的子串放入sub 重载方法1 private修饰 对外发布为方法2
	private static int SearchBracketsPositionAndContent(String s, int position,int pS, char lBrackets, char rBrackets,  int deep, StringConvert target) {
		Search2Char s2c = new Search2Char();
		int rc = 0;
		if(deep == 0) {
			pS = position;
		}
		
		while(position < s.length()) {
			//找到括号返回true 找不到括号返回false
			if(ContentFinding.search2Item(s, position, lBrackets, rBrackets, s2c)) {
				//先找到了左括号否则找到右括号
				if(s2c.whatS == 1) {
					deep++;
					//num已经变成了左括号的位置，运行该方法后理论上返回右括号的位置
					rc = SearchBracketsPositionAndContent(s, s2c.fAddr + 1, pS, lBrackets, rBrackets, deep, target);
					
					//左括号和右括号的位置不符合规定
					if(rc < 0 || (rc - 1) - (pS + 1) < 0) {
						//抛出异常
						
					}
					//截取括号内的内容
					if(target != null) {
						target.s = s.substring(pS + 1, rc -1);
					}
					//返回右括号的位置
					return rc;
				} else {
					//找到右括号的情况
					deep--;
					//返回位置
					position = s2c.SAddr + 1;
					//若之前已经找到左括号的话deep为1，自减后变零 没有找到重新循环
					if(deep == 0) {
						return position;
					}
					continue;
				}
			}
			//寻找下一位
			position++;
		}
		//遍历之后没有发现括号
		return -1;
	}
	
	//从字符串s中从位置p开始搜索匹配的括号，并将括号内的子串放入sub 重载方法2
	public static int SearchBracketsPositionAndContent(String s, int position, char lBrackets, char rBrackets, StringConvert target) {
		int rc;
		rc = SearchBracketsPositionAndContent(s, position, 0, lBrackets, rBrackets, 0, target);
		return rc;
	}
	
	//在字符串中从第一位开始如果有配对的括号则将括号内容取出 重载方法1
	public static String getBracketsInside(String s, char lBrackets, char rBrackets) {
		//索引是从0开始的
		int l = s.length() - 1;
		//字符串没有值
		if(l < 0) {
			return s;
		}
		return getBracketsInside(s, 0, lBrackets, rBrackets, true);
	}
	
	//在字符串中从固定位置p开始如果有配对的括号则将括号内容取出 重载方法2
	public static String getBracketsInside(String s, int in_position, char lBrackets, char rBrackets, boolean onEnd) {
		int l = s.length() - 1;
		//字符串没有值
		if(l < 0) {
			return s;
		}
		
		//固定要寻找的位置找不到对应左括号
		if(s.charAt(in_position) != lBrackets) {
			return s;
		}
		//新建自动转换的String类 储存字符串 不至于SearchBracketsPositionAndContent方法结束之后字符串被清空
		StringConvert target = new StringConvert();
		//返回位置
		int position = SearchBracketsPositionAndContent(s, in_position, lBrackets, rBrackets, target);
		
		if(onEnd) {
			if(position < 1) {
				return s;
			}
		}
		//返回所要的字符串
		return target.s;
	}
	
	/**
	 * 字符串中去除某个字符系列
	 */
	
	//去掉单词的某个字符 如果是两个的话可以在if从句中加上||
	public static String delOneChar(String s, char out_c) {
		//设置单词的最大长度为200
		char word[] = new char[200];
		int j = 0;
		char c;
		
		//将String转化成char数组
		char [] arrayChar = s.toCharArray();
		//遍历char数组
		for(int i = 0; i < arrayChar.length; i++) {
			c = arrayChar[i];
			//如果是该删去的字符则略过，否则放进新的数组中
			if(c == out_c) {
				continue;
			}
			word[j] = c;
			j++;
		}
		
//		//未知用途
//		word[j] = 0;
		
		//相当于valueOf
		return String.copyValueOf(word, 0, j);
	}
	
	//删除字符串中数字以外的字符
	public static String delNonNumber(String s) {
		int sL = s.length();
		char target[] = new char[sL];
		int j = 0;
		char c;
		
		char[] charArray = s.toCharArray();
		// 遍历原字符串元素
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
	
	//删除字符串中的空格，如果quotation为true则""里面的空格不删除
	public static String delSpace(String s, boolean quotation) {
		int sL = s.length();
		char target[] = new char[sL];
		int n = 0;
		char c;
		
		char[] charArray = s.toCharArray();
		// 遍历原字符串元素
		for(int i = 0; i < s.length(); i++) {
			c = charArray[i];
			if(c == ' ') {
				continue;
			}
			target[n] = c;
			n++;
			//判断是否满足双引号相关的条件 进入循环则保留空格将所有的东西复制进去
			if(c == '"' && quotation) {
				//寻找下一个双引号
				for(int j = i + 1; j < charArray.length; j++) {
					target[n] = charArray[j];
					n++;
					//遇到双引号退出 在双引号已经收录进去的情况
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
	
	//从字符串后面搜索出现c的位置，略过前导空格和""内的字符,返回位置
	public static int searchACharOLSB1(String s, int pos, char c) {
		char[] vc = s.toCharArray();
		boolean flag = false;
		//对pos位置后的字符进行遍历
		for(int i = pos; i < vc.length; i++) {
			if(!flag && (vc[i] == ' ')) {
				continue;
			} else {
				flag = true;
			}
			
			//双引号的情况
			if(vc[i] == '"') {
				for(int j = i + 1; j < vc.length; j++) {
					if(vc[i] == '"') {
						i = j;
						break;
					}
				}
			}
			
			//找到特定字符则返回该字符的位置
			if((vc[i] == c)) {
				return i;
			}
		}
		
		return -1;
	}
	
	//搜索尾部是否为该字符串s1，是则替换s2
	public static String replaceLastStr(String in_s, String s1, String s2) {
		String out_s = null;

		//判断是否搜寻到s1
		if(in_s.endsWith(s1)) {
			int p = in_s.lastIndexOf(s1);
			out_s = in_s.substring(0, p) + s2;
		} else {
			out_s = in_s;
		}
		
		return out_s;
	}
}
