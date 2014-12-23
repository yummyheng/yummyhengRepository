package com.test.main;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.test.shell.SWTResourceManager;
import com.test.util.RelateToFile;
import com.view.bean.YShell;
import com.view.bean.YummyButton;
import com.view.bean.YummyButton_I;

public class ShellButtonListener implements YummyButton_I{
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		System.out.println(this);
		System.out.println(e.getSource());
		Object in = e.getSource();
		Button in_button = (Button)in;
		//预览
		if(in_button.getData("id").equals("1")) {
			preview();
			return;
		}
		//保存参数
		if(in_button.getData("id").equals("2")) {
			preview();
			return;
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		
	}
	
	/**
	 * 预览
	 */
	private void preview() {
		Display display = Display.getCurrent();
		Shell shell = new Shell(display, SWT.SHELL_TRIM);
		shell.getSize();
		//网格布局参数:列数 是否等分距离
		GridLayout gl = new GridLayout(1, false);
		gl.verticalSpacing = 0;
		//设置内容和边界距离
		gl.marginWidth = 0;
		gl.marginHeight = 0;
		//读取文档配置
		YShell in_shell = (YShell)RelateToFile.ReadingFile("K:/YummyData/Shell/YShell");
		shell.setSize(in_shell.getWidth(), in_shell.getHeight());
		Composite newC = setNewComposite1(shell);
		newC.layout();
		shell.open();
		shell.layout();
		//消息循环 如果界面一直不销毁
		while(!shell.isDisposed()) {
			//如果不反复的读取和分派
			if(!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	/**
	 * 保存参数
	 * @param shell
	 * @return
	 */
	private void saveData() {
		
	}
	
	private Composite setNewComposite1(Shell shell) {
		Composite newComposite = new Composite(shell, SWT.NONE);
		GridLayout gridLayout = new GridLayout(2, false);
		//布局方式
		newComposite.setLayout(gridLayout);
		newComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		return newComposite;
	}
}
