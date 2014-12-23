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
import ADM.ViewDes.UBVDGUItable;
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
 * C ����������ά������
 *  @author �ο� 2011-2-22
 *  @comments:
 *
 */
public class Clz_Manage extends ApplicationWindow {
	UBVDGUItree treeDes;
	private UBVDUCV ucvEditUC;
	static AFfrObjFactory ObjFactory = UBViewDesCG.ObjFct;
	static PMTreeElement_B editTT = null;
	static String WinName = null;

	public Clz_Manage() throws Exception {
		super(null);
		ucvEditUC = (UBVDUCV) ABParmFactory.Factory.getItemByGUID("sd#UID1020");
	}

	public static void main(String[] args) throws Exception {
		AFexcDataArea xda = AFexcDataArea.getExcFct();
		xda.putData("#AppCode","sd");//�������е�ϵͳ����
		
		
		Clz_Manage test = new Clz_Manage();
		WinName = test.ucvEditUC.getText();
		AFUserInfo user = AFAppInfo.Info.getUser();
		user.setID("RK");
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

	//���ô�������
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setSize(1020, 608);
		// shell.setMaximized(true);
		shell.setText(WinName);
	}

	//�����ؼ�
	protected Control createContents(Composite parent) {
		try {
			UBCG.VPPrePrsor.preProcess(ucvEditUC);
			UBCG.VPPrsor.getScreen(parent, ucvEditUC);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return parent;
	}

	@Override
	protected void handleShellCloseEvent() {
		// TODO Auto-generated method stub
		if (PECommMethod.gainChangedRoot().size() > 0) {
			List<ABTreeRoot_I> data = PECommMethod.gainChangedRoot();
			if (data != null && data.size() > 0) {
				DxCommon.refreshSavLst(data);
			} else {
				// ���д�����Ϊ��ˢ��δ�����б�������
				UBVDGUItable td = (UBVDGUItable) UBCG.VPPrsor
						.getCtlDesByUseCode("UnSaveRecord", "UnSavedTable");
				if (td == null)
					return;
				td.setStyleName("UnSaveList");
				td.setInData(data);
				UBCG.VPPrsor.updataTableDes(td);
				try {
					UBCG.VPPrsor.changeTabItem("UnSaveRecord");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			// ----------------2009��3��16��weny���
			ABParmFactory.Factory.shutdown();
			super.handleShellCloseEvent();
		}

	}

}
