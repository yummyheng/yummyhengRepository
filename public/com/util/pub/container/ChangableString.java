package com.util.pub.container;

import java.io.File;
import java.util.HashMap;

/**
 * 可变的字符串数组类 
 * @author hengzai 2013-7-16
 */

public class ChangableString {
	
	//行数
	public int l;
	public String[] sa;
	public int cp;
	//默认最小有50行
	public final int minLineNo = 50;
	
	//构造函数 对传进来的数值进行再加工初始化
	public  ChangableString(int c) {
		//c为传进来的行数，当c小于默认时设置为默认
		if(c < minLineNo) {
			c = minLineNo;
		}
		
		//创建容量为c的字符串数组
		sa = new String[c];
		
		l = 0;
		cp = c;
	}
	
	//字符串容量扩充 原理:将此前的字符串数组保存并放入新建的数组
	public void extend() {
		int n = cp;
		//对数组容量进行扩充
		cp = cp + cp/2;
		String ns[] = new String [cp];
		//n为未扩充的容量数
		for(int i = 0; i < n; i++) {
			ns[i] = sa[i];
		}
		sa = ns;
	}
	
	//行数l大于容量cp时进行扩充
	public void addLine(String s) {
		sa[l] = s;
		l++;
		
		if(l >= cp) {
			extend();
		}
	}
	
	
}
