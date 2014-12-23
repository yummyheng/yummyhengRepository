package com.view.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 按钮的属性
 * @author hengzai
 * @date 2014-9-2
 */
public class YummyButton {
	/**
	 * 按钮名字
	 */
	private String name;
	
	/**
	 * 显示名称
	 */
	private String text;
	
	/**
	 * 父容器标识
	 */
	private String father;
	
	/**
	 * 按钮所属监听器
	 */
	private String listener;
	
	/**
	 * 语义代码 用来生成路径
	 */
	private String scCode;
	
	/**
	 * 字段的中文译名的哈希表
	 */
	private Map<String, List> tWord = new HashMap<String, List>();
	
	/**
	 * 构造函数
	 */
	public YummyButton() {
		//建立中英翻译映射
		translate();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Map<String, List> gettWord() {
		return tWord;
	}

	public void settWord(Map<String, List> tWord) {
		this.tWord = tWord;
	}

	public String getFather() {
		return father;
	}

	public void setFather(String father) {
		this.father = father;
	}

	public String getListener() {
		return listener;
	}

	public void setListener(String listener) {
		this.listener = listener;
	}

	public String getScCode() {
		return scCode;
	}

	public void setScCode(String scCode) {
		this.scCode = scCode;
	}
	
	private void translate() {
		ArrayList<String> tempList = new ArrayList<String>();
		ArrayList list = null;
		
		tempList.add("按钮名字");
		tempList.add("1");
		list = (ArrayList<String>)tempList.clone();
		tWord.put("name", list);
		tempList.clear();
		
		tempList.add("显示名称");
		tempList.add("3");
		list = (ArrayList)tempList.clone();
		tWord.put("text", list);
		tempList.clear();
		
		tempList.add("父容器标识");
		tempList.add("2");
		list = (ArrayList)tempList.clone();
		tWord.put("father", list);
		tempList.clear();
		
		tempList.add("按钮所属监听器");
		tempList.add("1");
		list = (ArrayList)tempList.clone();
		tWord.put("listener", list);
		tempList.clear();
		
		tempList.add("语义代码");
		tempList.add("1");
		list = (ArrayList)tempList.clone();
		tWord.put("scCode", list);
		tempList.clear();
		
	}
}
