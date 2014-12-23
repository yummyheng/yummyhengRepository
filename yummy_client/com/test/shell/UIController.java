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
		//实例为空才创建
		if(tabItem == null) {
			try {
				tabItem.setText(title);
				tabItem.setImage(SWTResourceManager.getImage("./icons/three.png"));
				//反射机制构造器创建对象 利用uid
				Constructor constructor = Class.forName(uid).getConstructor(Composite.class, int.class);
				Composite composite = (Composite)constructor.newInstance(TestShell.getContainer(), SWT.NONE);
				tabItem.setControl(composite);
				TestShell.itemMap.put(uid, tabItem);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//添加销毁监听器
			tabItem.addDisposeListener(new DisposeListener() {
				//去除HashMap的参数
				public void widgetDisposed(DisposeEvent e) {
					TestShell.itemMap.remove(uid);
				}
			});
		}
		//设置该控件
		TestShell.getContainer().setSelection(tabItem);
	}
	
	public static FormColors getFormColors(final Display display) {
		if(formColors == null) {
			formColors = new FormColors(display);
			//创建白色
			formColors.createColor(IFormColors.TB_BG, ColorDealing.getRGB("192,192,192"));
		}
		return formColors;
	}
	
	//打开页面
	public static void openUCV(String uid, Composite parent) {
		if(uid == null||uid.trim().equals("")) {
			return;
		}
		
		try {
			//模拟登陆 根据userdata中的文件读取相关参数
			String username = "yummy_heng";
			XStream xs = new XStream();
			String filename = "./userdata/"+username;
			File file = new File(filename);
			if(file.exists()){
				FileReader fr = new FileReader(file);
				System.out.println("加载用户 : "+username);
				NUAPUser uapuser = (NUAPUser) xs.fromXML(fr);
				//对象工厂 保存登陆用户的缓存
			    UBViewDesCG.ObjFct.sendObj(uapuser, "uapuser");
			}else{
				System.err.println("文件"+filename+"不存在");
			}
			
			//加载并获取UBUC对象 
			UBUCManager ubucManager = UBUCManager.getUBUCManager();
			//加载计时器
			int watch = Timer.start();
			//加载并获取UCV 从参数工厂中通过GUID进行加载  GUID ap.tt.id 应用体系代码.树节点的第一标识.树节点的唯一标识符  例子:ap-#-tt-id cam # UID 010301
			//利用tt找到对应的ABXparmServer对象 
			UBVDUCV ucv = (UBVDUCV) ABParmFactory.getFactory().getItemByGUID(uid.trim());
			if(ucv == null){
				MessageBox mb= new MessageBox(parent.getShell(), SWT.ICON_ERROR);
				mb.setMessage("页面["+uid+"]不存在");
				mb.setText("错误");
				mb.open();
			}
			double time = Timer.stop(watch);
			System.out.println("加载UID耗时：" + time);
			
			ubucManager.showUC(uid, parent, ucv, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
