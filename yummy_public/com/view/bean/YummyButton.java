package com.view.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ��ť������
 * @author hengzai
 * @date 2014-9-2
 */
public class YummyButton {
	/**
	 * ��ť����
	 */
	private String name;
	
	/**
	 * ��ʾ����
	 */
	private String text;
	
	/**
	 * ��������ʶ
	 */
	private String father;
	
	/**
	 * ��ť����������
	 */
	private String listener;
	
	/**
	 * ������� ��������·��
	 */
	private String scCode;
	
	/**
	 * �ֶε����������Ĺ�ϣ��
	 */
	private Map<String, List> tWord = new HashMap<String, List>();
	
	/**
	 * ���캯��
	 */
	public YummyButton() {
		//������Ӣ����ӳ��
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
		
		tempList.add("��ť����");
		tempList.add("1");
		list = (ArrayList<String>)tempList.clone();
		tWord.put("name", list);
		tempList.clear();
		
		tempList.add("��ʾ����");
		tempList.add("3");
		list = (ArrayList)tempList.clone();
		tWord.put("text", list);
		tempList.clear();
		
		tempList.add("��������ʶ");
		tempList.add("2");
		list = (ArrayList)tempList.clone();
		tWord.put("father", list);
		tempList.clear();
		
		tempList.add("��ť����������");
		tempList.add("1");
		list = (ArrayList)tempList.clone();
		tWord.put("listener", list);
		tempList.clear();
		
		tempList.add("�������");
		tempList.add("1");
		list = (ArrayList)tempList.clone();
		tWord.put("scCode", list);
		tempList.clear();
		
	}
}
