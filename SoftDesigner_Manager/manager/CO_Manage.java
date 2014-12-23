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
 * CO ���ö�����������ά������
 *  @author �ο� 2011-2-22
 *  @comments:
 *
 */
public class CO_Manage extends ApplicationWindow
{
	UBVDGUItree treeDes;
	UBVDUCV ucvEditUC;
	static AFfrObjFactory ObjFactory = UBViewDesCG.ObjFct;
	static PMTreeElement_B editTT = null;		
	static String WinName=null;
	
	public CO_Manage ( ) throws Exception
	{
		super(null);
		ucvEditUC = (UBVDUCV) ABParmFactory.Factory.getItemByGUID("sd#UID1159");
	}
	public CO_Manage (UBVDUCV ucvEditUCnew ) throws Exception
	{
		
		super(null);
		this.ucvEditUC  =ucvEditUCnew;  
		WinName=ucvEditUC.getText().trim();
	}
	
	public static void main(String[] args) throws Exception
	{ 
		
		AFexcDataArea xda = AFexcDataArea.getExcFct();
		xda.putData("#AppCode","ts");//�������е�ϵͳ����
		
		CO_Manage test = new CO_Manage();
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



