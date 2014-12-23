package manager;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

import sgi.basePi.ABTreeRoot_I;
import ADM.Factory.ABParmFactory;
import ADM.ViewDes.UBDragDes;
import ADM.ViewDes.UBShowItem;
import ADM.ViewDes.UBTitleDes;
import ADM.ViewDes.UBVDCPSsash;
import ADM.ViewDes.UBVDCPSsw;
import ADM.ViewDes.UBVDCPStab;
import ADM.ViewDes.UBVDCtl;
import ADM.ViewDes.UBVDGUI;
import ADM.ViewDes.UBVDGUIbutton;
import ADM.ViewDes.UBVDGUItable;
import ADM.ViewDes.UBVDGUItree;
import ADM.ViewDes.UBVDUCV;
import ADM.ViewDes.UBVDWV;
import ADM.ViewDes.UBWDGridData;
import ADM.ViewDes.UBWDLayout;
import ADM.ViewDes.UBWDLayoutFill;
import ADM.ViewDes.UBWDLayoutGrid;
import ADM.ViewDes.UBWindowAtt;
import ADM.md.PMStandardCodeNode;
import ADM.md.PMStandardCodeRoot;
import ADM.po.AFBindingData;
import ADM.po.AFShowData;
import ADM.po.PMTreeElement_B;
import AF.po.AFAppInfo;
import AF.po.AFUserInfo;
import AF.pub.AFexcDataArea;
import AF.rf.AFfrObjFactory;
import UB.pub.UBViewDesCG;
import UB.pubEPL.UBCG;
import cdc.uap.user.NUAPUser;

/**
 * UID(User Interface Description)�û�������������ά������
 * 
 * @author �ο�  2011/2/17
 */
public class DEV_Manage extends ApplicationWindow
{
	UBVDGUItree treeDes;
	UBVDUCV ucvEditUC;
	static AFfrObjFactory ObjFactory = UBViewDesCG.ObjFct;
	static PMTreeElement_B editTT = null;		
	static String WinName=null;
	
	public DEV_Manage ( ) throws Exception
	{
		super(null);
		ucvEditUC = (UBVDUCV) ABParmFactory.Factory.getItemByGUID("sd#UID20000");//�����������༭����
	}
	public DEV_Manage (UBVDUCV ucvEditUCnew ) throws Exception
	{
		
		super(null);
		this.ucvEditUC  =ucvEditUCnew;  
		WinName=ucvEditUC.getText().trim();
	}
	
	public static void main(String[] args) throws Exception
	{ 
		
		AFexcDataArea xda = AFexcDataArea.getExcFct();
//		xda.putData("#AppCode","buc");//�������е�ϵͳ����
//		xda.putData("#AppCode","spm");//�������е�ϵͳ����
		xda.putData("#AppCode","buc");//�������е�ϵͳ����
//		xda.putData("#AppCode","bla");//�������е�ϵͳ����
		
		DEV_Manage test = new DEV_Manage();
		WinName=test.ucvEditUC.getText().trim();
		AFUserInfo user = AFAppInfo.Info.getUser();
		user.setID("RK");
		
		//���༭�Ķ������� �� ��������ڵ����ʾ��񣬷��뽻����
		test.setShellStyle(SWT.CLOSE|SWT.MIN|SWT.MAX|SWT.RESIZE);
		test.handleShellCloseEvent();
		test.setBlockOnOpen(true);
		test.open();
		Display.getCurrent().dispose();  
		
	}

  
	// ���ô�������
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setSize(1020, 500);
		shell.setMaximized(true);
		shell.setText(WinName);
		//shell.setLayout(new FillLayout());
		
	}

	// �����ؼ�
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
		ABParmFactory.Factory.shutdown();
		super.handleShellCloseEvent();
	}
	public void setUCV(UBVDUCV ucs) {
		this.ucvEditUC=(UBVDUCV)ucs;
	}

}



