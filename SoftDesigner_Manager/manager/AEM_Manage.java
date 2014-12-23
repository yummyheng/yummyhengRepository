package manager;

import java.util.List;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import sgi.basePi.ABTreeRoot_I;
import ADM.Factory.ABParmFactory;
import ADM.Public.PECommMethod;
import ADM.ViewDes.UBVDGUItree;
import ADM.ViewDes.UBVDUCV;
import ADM.po.PMTreeElement_B;
import AF.po.AFAppInfo;
import AF.po.AFUserInfo;
import AF.pub.AFexcDataArea;
import AF.rf.AFfrObjFactory;
import Dx.Lsn.DxCommon;
import UB.pub.UBViewDesCG;
import UB.pubEPL.UBCG;
/**
 * AEM描述管理维护程序
 *  @author 任凯 2011-2-22
 *  @comments:
 *
 */
public class AEM_Manage extends ApplicationWindow{

	UBVDGUItree treeDes;
	UBVDUCV ucvEditUC;
	static AFfrObjFactory ObjFactory = UBViewDesCG.ObjFct;
	static PMTreeElement_B editTT = null;
	static String WinName=null;
	
	public AEM_Manage ( ) throws Exception
	{
		super(null);
		ucvEditUC = (UBVDUCV) ABParmFactory.Factory.getItemByGUID("sd#UID1023");
	}
	public static void main(String[] args) throws Exception
	{

		/**
		 * 初始化，主要是先以编程的方式填写user信息
		 * 编辑的顶级代码类型
		 * 向工厂中方置一个该类的全名字符串，作为种子（Seed），以便其他几个监听器能够
		 * 创建和获取正确的对象。
		 */
		AFexcDataArea xda = AFexcDataArea.getExcFct();
//		xda.putData("#AppCode",APSwicth.AP);//设置运行的系统代码
//		xda.putData("#AppCode","buc");//设置运行的系统代码
		xda.putData("#AppCode","buc");//设置运行的系统代码
//		xda.putData("#AppCode","cdc");//设置运行的系统代码
//		xda.putData("#AppCode","spm");//设置运行的系统代码
		
		AEM_Manage test = new AEM_Manage();
		AFUserInfo user = AFAppInfo.Info.getUser();
		user.setID("Jon");
				
		test.setShellStyle(SWT.MAX);
		test.setShellStyle(SWT.CLOSE|SWT.MIN|SWT.MAX|SWT.RESIZE);
		test.handleShellCloseEvent();
		test.setBlockOnOpen(true);
		test.open();
		Display.getCurrent().dispose();
	}

  

	// 设置窗口属性
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setSize(1020, 500);
		shell.setMaximized(true);
//		shell.setText(WinName);
		shell.setText(ucvEditUC.getText());
	}

	// 创建控件
	protected Control createContents(Composite parent)
	{
		try {
			UBCG.VPPrePrsor.preProcess(ucvEditUC);
			UBCG.VPPrsor.getScreen(parent, ucvEditUC);
		}catch (Exception e){
			e.printStackTrace();
		}
		return parent;
	}
	
	 
	protected void handleShellCloseEvent() {
		// TODO Auto-generated method stub
//		ABStandardCodeFactory.Factory.closeDB();
		List<ABTreeRoot_I> data = PECommMethod.gainChangedRoot();	
		for (int i = 0; i < data.size(); i++) {
			PMTreeElement_B rt = (PMTreeElement_B)data.get(i);
			rt.setSlt(true);
		}
		if (data != null && data.size() > 0) {
			DxCommon.refreshSavLst(data);
		} else {
			// ----------------2009年3月16日weny添加
			ABParmFactory.Factory.shutdown();
			super.handleShellCloseEvent();
		}
	}
	

}
