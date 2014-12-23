package com.view.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Layout;

/**
 * �����ڵ�����
 * @author hengzai
 * @date 2014-7-29
 */
public class YShell {
	//���ַ�ʽ
	private Layout layout;
	//��������
	private String layoutType;
	//��
	private int width;
	//�߶�
	private int height;
	//��ɫ
	private int colorCode;
	//ͼ��·��
	private String imagePath;
	
	/**
	 * ������� �������ɴ���·��
	 */
	private String scCode;
	
	//�ֶε����������Ĺ�ϣ��
	private Map<String, List> tWord = new HashMap<String, List>();
	
	/**
	 * ���캯��
	 */
	public YShell() {
		//������Ӣ����ӳ��
		translate();
	}
	
	
	public Map<String, List> gettWord() {
		return tWord;
	}
	public void settWord(Map<String, List> tWord) {
		this.tWord = tWord;
	}
	public int getColorCode() {
		return colorCode;
	}
	public void setColorCode(int colorCode) {
		this.colorCode = colorCode;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getScCode() {
		return scCode;
	}
	public void setScCode(String scCode) {
		this.scCode = scCode;
	}
	public Layout getLayout() {
		return layout;
	}
	public void setLayout(Layout layout) {
		this.layout = layout;
	}
	public String getLayoutType() {
		return layoutType;
	}
	public void setLayoutType(String layoutType) {
		this.layoutType = layoutType;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	private void translate() {
		ArrayList<String> tempList = new ArrayList<String>();
		ArrayList list = null;
		
		tempList.add("�������");
		tempList.add("2");
		list = (ArrayList)tempList.clone();
		tWord.put("scCode", list);
		tempList.clear();
		
		tempList.add("���ַ�ʽ");
		tempList.add("3");
		list = (ArrayList)tempList.clone();
		tWord.put("layout", list);
		tempList.clear();
		
		tempList.add("��������");
		tempList.add("2");
		list = (ArrayList)tempList.clone();
		tWord.put("layoutType", list);
		tempList.clear();
		
		tempList.add("���");
		tempList.add("1");
		list = (ArrayList)tempList.clone();
		tWord.put("width", list);
		tempList.clear();
		
		tempList.add("�߶�");
		tempList.add("1");
		list = (ArrayList)tempList.clone();
		tWord.put("height", list);
		tempList.clear();
		
		tempList.add("��ɫ����");
		tempList.add("2");
		list = (ArrayList)tempList.clone();
		tWord.put("colorCode", list);
		tempList.clear();
		
		tempList.add("ͼ��·��");
		tempList.add("2");
		list = (ArrayList)tempList.clone();
		tWord.put("imagePath", list);
		tempList.clear();
	}
}
