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
		//Ԥ��
		if(in_button.getData("id").equals("1")) {
			preview();
			return;
		}
		//�������
		if(in_button.getData("id").equals("2")) {
			preview();
			return;
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		
	}
	
	/**
	 * Ԥ��
	 */
	private void preview() {
		Display display = Display.getCurrent();
		Shell shell = new Shell(display, SWT.SHELL_TRIM);
		shell.getSize();
		//���񲼾ֲ���:���� �Ƿ�ȷ־���
		GridLayout gl = new GridLayout(1, false);
		gl.verticalSpacing = 0;
		//�������ݺͱ߽����
		gl.marginWidth = 0;
		gl.marginHeight = 0;
		//��ȡ�ĵ�����
		YShell in_shell = (YShell)RelateToFile.ReadingFile("K:/YummyData/Shell/YShell");
		shell.setSize(in_shell.getWidth(), in_shell.getHeight());
		Composite newC = setNewComposite1(shell);
		newC.layout();
		shell.open();
		shell.layout();
		//��Ϣѭ�� �������һֱ������
		while(!shell.isDisposed()) {
			//����������Ķ�ȡ�ͷ���
			if(!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	/**
	 * �������
	 * @param shell
	 * @return
	 */
	private void saveData() {
		
	}
	
	private Composite setNewComposite1(Shell shell) {
		Composite newComposite = new Composite(shell, SWT.NONE);
		GridLayout gridLayout = new GridLayout(2, false);
		//���ַ�ʽ
		newComposite.setLayout(gridLayout);
		newComposite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		return newComposite;
	}
}
