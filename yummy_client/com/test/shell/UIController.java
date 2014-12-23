package com.test.shell;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Constructor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.IFormColors;

import sgiSEC.UBUCManager;
import ADM.Factory.ABParmFactory;
import ADM.ViewDes.UBVDUCV;
import UB.pub.UBViewDesCG;
import cdc.uap.user.NUAPUser;

import com.thoughtworks.xstream.XStream;
import com.util.pub.color.ColorDealing;
import com.util.pub.time.Timer;

public class UIController {
	private static FormColors formColors;
	
	public static void openPage(final String uid, String title) {
		if(uid == null||uid.trim().equals("")) {
			return;
		}
		
		CTabItem tabItem = TestShell.itemMap.get(uid);
		//ʵ��Ϊ�ղŴ���
		if(tabItem == null) {
			try {
				tabItem.setText(title);
				tabItem.setImage(SWTResourceManager.getImage("./icons/three.png"));
				//������ƹ������������� ����uid
				Constructor constructor = Class.forName(uid).getConstructor(Composite.class, int.class);
				Composite composite = (Composite)constructor.newInstance(TestShell.getContainer(), SWT.NONE);
				tabItem.setControl(composite);
				TestShell.itemMap.put(uid, tabItem);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//������ټ�����
			tabItem.addDisposeListener(new DisposeListener() {
				//ȥ��HashMap�Ĳ���
				public void widgetDisposed(DisposeEvent e) {
					TestShell.itemMap.remove(uid);
				}
			});
		}
		//���øÿؼ�
		TestShell.getContainer().setSelection(tabItem);
	}
	
	public static FormColors getFormColors(final Display display) {
		if(formColors == null) {
			formColors = new FormColors(display);
			//������ɫ
			formColors.createColor(IFormColors.TB_BG, ColorDealing.getRGB("192,192,192"));
		}
		return formColors;
	}
	
	//��ҳ��
	public static void openUCV(String uid, Composite parent) {
		if(uid == null||uid.trim().equals("")) {
			return;
		}
		
		try {
			//ģ���½ ����userdata�е��ļ���ȡ��ز���
			String username = "yummy_heng";
			XStream xs = new XStream();
			String filename = "./userdata/"+username;
			File file = new File(filename);
			if(file.exists()){
				FileReader fr = new FileReader(file);
				System.out.println("�����û� : "+username);
				NUAPUser uapuser = (NUAPUser) xs.fromXML(fr);
				//���󹤳� �����½�û��Ļ���
			    UBViewDesCG.ObjFct.sendObj(uapuser, "uapuser");
			}else{
				System.err.println("�ļ�"+filename+"������");
			}
			
			//���ز���ȡUBUC���� 
			UBUCManager ubucManager = UBUCManager.getUBUCManager();
			//���ؼ�ʱ��
			int watch = Timer.start();
			//���ز���ȡUCV �Ӳ���������ͨ��GUID���м���  GUID ap.tt.id Ӧ����ϵ����.���ڵ�ĵ�һ��ʶ.���ڵ��Ψһ��ʶ��  ����:ap-#-tt-id cam # UID 010301
			//����tt�ҵ���Ӧ��ABXparmServer���� 
			UBVDUCV ucv = (UBVDUCV) ABParmFactory.getFactory().getItemByGUID(uid.trim());
			if(ucv == null){
				MessageBox mb= new MessageBox(parent.getShell(), SWT.ICON_ERROR);
				mb.setMessage("ҳ��["+uid+"]������");
				mb.setText("����");
				mb.open();
			}
			double time = Timer.stop(watch);
			System.out.println("����UID��ʱ��" + time);
			
			ubucManager.showUC(uid, parent, ucv, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
