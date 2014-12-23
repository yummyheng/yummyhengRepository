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
 * QVC ͨ�ò�ѯ������������ά������
 *  @author �ο� 2011-2-22
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
//		xda.putData("#AppCode",APSwicth.AP);//�������е�ϵͳ����
		xda.putData("#AppCode","spm");//�������е�ϵͳ����
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

	// ���ô�������
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setSize(1020, 608);
		shell.setMaximized(true);
		shell.setText(WinName);
	}

	// �����ؼ�
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
