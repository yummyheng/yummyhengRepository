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
import ADM.ViewDes.UBVDUCV;
import AF.po.AFAppInfo;
import AF.po.AFUserInfo;
import AF.pub.AFexcDataArea;
import Dx.Lsn.DxCommon;
import UB.pubEPL.UBCG;

/**
 * Table 表描述管理维护程序
 *  @author 任凯 2011-2-22
 *  @comments:
 *
 */

public class Table_Manage extends ApplicationWindow {

	UBVDUCV ucvEditUC;
	static String WinName = null;                           

	public Table_Manage() throws Exception {
		super(null);
		ucvEditUC = (UBVDUCV) ABParmFactory.Factory.getItemByGUID("sd#UID2000");  
	}

	public static void main(String[] args) throws Exception {
		AFexcDataArea xda = AFexcDataArea.getExcFct();
//		xda.putData("#AppCode","sd");//设置运行的系统代码
//		xda.putData("#AppCode","ts");//设置运行的系统代码
//		xda.putData("#AppCode","spm");//设置运行的系统代码
		xda.putData("#AppCode","cdc");//设置运行的系统代码
		
		
		Table_Manage test = new Table_Manage();                                   // 构造一个DtManage对象
		WinName = test.ucvEditUC.getText();                               // 取出显示名称
		AFUserInfo user = AFAppInfo.Info.getUser();                       // 取出系统使用者
		user.setID("Jon");                                                // 更新user信息
		test.setShellStyle(SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.RESIZE);   // 设置shell
		test.handleShellCloseEvent();                                     // 调用关闭事件处理
		test.setBlockOnOpen(true);                                        // 设置open方法直到windows是否阻塞
		test.open();                                                      // 打开窗口
		Display.getCurrent().dispose();                                   // 操作系统资源进行显示
	}

	/*
	 * 处理窗口关闭事件
	 */
	protected void handleShellCloseEvent(){
		List<ABTreeRoot_I> data = PECommMethod.gainChangedRoot();
		if (data != null && data.size() > 0) {
			DxCommon.refreshSavLst(data);
		}else{
			ABParmFactory.Factory.shutdown();
			super.handleShellCloseEvent();
		}
	}
	
	/*
	 * 设置窗口属性
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setSize(1020, 608);
		// shell.setMaximized(true);
		shell.setText(WinName);
	}
	
	/*
	 * 创建控件
	 */
	protected Control createContents(Composite parent) {
		try {
			UBCG.VPPrePrsor.preProcess(ucvEditUC);
			UBCG.VPPrsor.getScreen(parent, ucvEditUC);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parent;
	}	

}