package com.test.shell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class HomeComp extends Composite {
	//构造方法
	public HomeComp(Composite parent, int style) {
		super(parent, style);
		setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		setLayout(new GridLayout(1, false));
		
		Label l = new Label(this, SWT.NONE);
		l.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		l.setImage(SWTResourceManager.getImage("./icons/table.jpg"));
	}
}
