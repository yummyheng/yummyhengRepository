package pub;

import java.io.File;
import java.io.FileReader;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.thoughtworks.xstream.XStream;

import UB.pub.UBViewDesCG;

import cdc.uap.user.NUAPUser;

import sgiSEC.UBUCManager;

import ADM.Factory.ABParmFactory;
import ADM.ViewDes.UBVDUCV;
import AF.pub.UBExcObj;

public class UcvWindow {

	protected Shell shell;
	private String uid;
	private String username;
	private UBExcObj initData;

	public UcvWindow(String uid,String username,UBExcObj initData) {
		this.uid = uid;
		this.username = username;
		this.initData = initData;
	}
	/**
	 * Open the window
	 */
	public void open() {
		final Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	/**
	 * Create contents of the window
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setLayout(new FillLayout());
		shell.setSize(1024, 650);
//		shell.setMaximized(true);
		shell.setText("SWT Application");

		final Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new FillLayout());
				 
		try {
			//ģ���¼
			XStream xs = new XStream();
			String filename = "./userdata/"+username;
			File file = new File(filename);
			if(file.exists()){
				FileReader fr = new FileReader(file);
				System.out.println("�����û� : "+username);
				NUAPUser uapuser = (NUAPUser) xs.fromXML(fr);
			    UBViewDesCG.ObjFct.sendObj(uapuser, "uapuser");
			}else{
				System.err.println("�ļ�"+filename+"������");
			}
			
			//����UCV
			UBUCManager ucm = UBUCManager.getUBUCManager();
			UBVDUCV ucv = (UBVDUCV) ABParmFactory.Factory.getItemByGUID(uid);
			System.out.println("��UCV : "+uid);
			ucm.showUC(uid, composite, ucv, initData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
