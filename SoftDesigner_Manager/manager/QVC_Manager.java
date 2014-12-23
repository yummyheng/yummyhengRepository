package manager;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import ADM.Factory.ABParmFactory;
import ADM.ViewDes.UBVDGUItree;
import ADM.ViewDes.UBVDUCV;
import ADM.po.PMTreeElement_B;
import AF.po.AFAppInfo;
import AF.po.AFUserInfo;
import AF.pub.AFexcDataArea;
import AF.rf.AFfrObjFactory;
import UB.pub.UBViewDesCG;
import UB.pubEPL.UBCG;

/**
 * QVC 通用查询对象描述管理维护程序
 *  @author 任凯 2011-2-22
 *  @comments:
 *
 */
public class QVC_Manager extends ApplicationWindow {
	UBVDGUItree treeDes;
	private UBVDUCV ucvEditUC;
	static AFfrObjFactory ObjFactory = UBViewDesCG.ObjFct;
	static PMTreeElement_B editTT = null;
	static String WinName = null;

	public QVC_Manager() throws Exception {
		super(null);
		ucvEditUC = (UBVDUCV) ABParmFactory.Factory.getItemByGUID("sd#UID1070");
	}

	public static void main(String[] args) throws Exception {
		AFexcDataArea xda = AFexcDataArea.getExcFct();
//		xda.putData("#AppCode",APSwicth.AP);//设置运行的系统代码
		xda.putData("#AppCode","spm");//设置运行的系统代码
		QVC_Manager test = new QVC_Manager();
		WinName = test.ucvEditUC.getText();
		AFUserInfo user = AFAppInfo.Info.getUser();
		user.setID("Jon");

		try {
			test.setShellStyle(SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.RESIZE);
			test.handleShellCloseEvent();
			test.setBlockOnOpen(true);
			test.open();
			Display.getCurrent().dispose();
		} catch (RuntimeException e) {
			// // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 设置窗口属性
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setSize(1020, 608);
		shell.setMaximized(true);
		shell.setText(WinName);
	}

	// 创建控件
	protected Control createContents(Composite parent) {
		try {
			UBCG.VPPrePrsor.preProcess(ucvEditUC);
			UBCG.VPPrsor.getScreen(parent, ucvEditUC);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return parent;
	}

	
	protected void handleShellCloseEvent() {
		// TODO Auto-generated method stub
//		ABStandardCodeFactory.Factory.closeDB();
		ABParmFactory.Factory.shutdown();
		super.handleShellCloseEvent();
	}
	
}
