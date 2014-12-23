package com.test.model;

import org.eclipse.swt.layout.GridLayout;

public class YummyLabelText {

	private String labelText;
	private GridLayout gridLayout;
	
	public String getLabelText() {
		return labelText;
	}
	public void setLabelText(String labelText) {
		this.labelText = labelText;
	}
	public GridLayout getGridLayout() {
		return gridLayout;
	}
	public void setGridLayout(GridLayout gridLayout) {
		this.gridLayout = gridLayout;
	}
	
	public YummyLabelText(GridLayout gridLayout, String labelText) {
		this.gridLayout = gridLayout;
		this.labelText = labelText;
	}
}
