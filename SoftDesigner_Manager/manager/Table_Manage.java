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
 * Table ����������ά������
 *  @author �ο� 2011-2-22
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
//		xda.putData("#AppCode","sd");//�������е�ϵͳ����
//		xda.putData("#AppCode","ts");//�������е�ϵͳ����
//		xda.putData("#AppCode","spm");//�������е�ϵͳ����
		xda.putData("#AppCode","cdc");//�������е�ϵͳ����
		
		
		Table_Manage test = new Table_Manage();                                   // ����һ��DtManage����
		WinName = test.ucvEditUC.getText();                               // ȡ����ʾ����
		AFUserInfo user = AFAppInfo.Info.getUser();                       // ȡ��ϵͳʹ����
		user.setID("Jon");                                                // ����user��Ϣ
		test.setShellStyle(SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.RESIZE);   // ����shell
		test.handleShellCloseEvent();                                     // ���ùر��¼�����
		test.setBlockOnOpen(true);                                        // ����open����ֱ��windows�Ƿ�����
		test.open();                                                      // �򿪴���
		Display.getCurrent().dispose();                                   // ����ϵͳ��Դ������ʾ
	}

	/*
	 * �����ڹر��¼�
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
	 * ���ô�������
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setSize(1020, 608);
		// shell.setMaximized(true);
		shell.setText(WinName);
	}
	
	/*
	 * �����ؼ�
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