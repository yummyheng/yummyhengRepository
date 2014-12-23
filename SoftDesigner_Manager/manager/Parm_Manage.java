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
 * Parm 系统参数描述管理维护程序
 *  @author 任凯 2011-2-22
 *  @comments:
 *
 */
public class Parm_Manage extends ApplicationWindow
{
	UBVDGUItree treeDes;
	UBVDUCV ucvEditUC;
	static AFfrObjFactory ObjFactory = UBViewDesCG.ObjFct;
	static PMTreeElement_B editTT = null;		
	static String WinName=null;
	
	public Parm_Manage ( ) throws Exception
	{
		super(null);
		ucvEditUC = (UBVDUCV) ABParmFactory.Factory.getItemByGUID("sd#UID512");//拿到UID的CAT
	}  
	public static void main(String[] args) throws Exception
	{


		AFexcDataArea xda = AFexcDataArea.getExcFct();
		xda.putData("#AppCode","sd");
//		xda.putData("#AppCode","buc");
//		xda.putData("#AppCode","icp");
//		xda.putData("#AppCode","ts");
		
		Parm_Manage test = new Parm_Manage();
		WinName=test.ucvEditUC.getText();
		AFUserInfo user = AFAppInfo.Info.getUser();
		user.setID("RK");
				
//		test.setShellStyle(SWT.MAX);
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
		shell.setText(WinName);
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



