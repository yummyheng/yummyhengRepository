package com.view.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Layout;

/**
 * 主窗口的属性
 * @author hengzai
 * @date 2014-7-29
 */
public class YShell {
	//布局方式
	private Layout layout;
	//布局类型
	private String layoutType;
	//宽
	private int width;
	//高度
	private int height;
	//颜色
	private int colorCode;
	//图像路径
	private String imagePath;
	
	/**
	 * 语义代码 用来生成储存路径
	 */
	private String scCode;
	
	//字段的中文译名的哈希表
	private Map<String, List> tWord = new HashMap<String, List>();
	
	/**
	 * 构造函数
	 */
	public YShell() {
		//建立中英翻译映射
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
		
		tempList.add("语义代码");
		tempList.add("2");
		list = (ArrayList)tempList.clone();
		tWord.put("scCode", list);
		tempList.clear();
		
		tempList.add("布局方式");
		tempList.add("3");
		list = (ArrayList)tempList.clone();
		tWord.put("layout", list);
		tempList.clear();
		
		tempList.add("布局类型");
		tempList.add("2");
		list = (ArrayList)tempList.clone();
		tWord.put("layoutType", list);
		tempList.clear();
		
		tempList.add("宽度");
		tempList.add("1");
		list = (ArrayList)tempList.clone();
		tWord.put("width", list);
		tempList.clear();
		
		tempList.add("高度");
		tempList.add("1");
		list = (ArrayList)tempList.clone();
		tWord.put("height", list);
		tempList.clear();
		
		tempList.add("颜色代码");
		tempList.add("2");
		list = (ArrayList)tempList.clone();
		tWord.put("colorCode", list);
		tempList.clear();
		
		tempList.add("图像路径");
		tempList.add("2");
		list = (ArrayList)tempList.clone();
		tWord.put("imagePath", list);
		tempList.clear();
	}
}
