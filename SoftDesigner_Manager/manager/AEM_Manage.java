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
 * AEM��������ά������
 *  @author �ο� 2011-2-22
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
		 * ��ʼ������Ҫ�����Ա�̵ķ�ʽ��дuser��Ϣ
		 * �༭�Ķ�����������
		 * �򹤳��з���һ�������ȫ���ַ�������Ϊ���ӣ�Seed�����Ա����������������ܹ�
		 * �����ͻ�ȡ��ȷ�Ķ���
		 */
		AFexcDataArea xda = AFexcDataArea.getExcFct();
//		xda.putData("#AppCode",APSwicth.AP);//�������е�ϵͳ����
//		xda.putData("#AppCode","buc");//�������е�ϵͳ����
		xda.putData("#AppCode","buc");//�������е�ϵͳ����
//		xda.putData("#AppCode","cdc");//�������е�ϵͳ����
//		xda.putData("#AppCode","spm");//�������е�ϵͳ����
		
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

  

	// ���ô�������
	protected void configureShell(Shell shell)
	{
		super.configureShell(shell);
		shell.setSize(1020, 500);
		shell.setMaximized(true);
//		shell.setText(WinName);
		shell.setText(ucvEditUC.getText());
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
		List<ABTreeRoot_I> data = PECommMethod.gainChangedRoot();	
		for (int i = 0; i < data.size(); i++) {
			PMTreeElement_B rt = (PMTreeElement_B)data.get(i);
			rt.setSlt(true);
		}
		if (data != null && data.size() > 0) {
			DxCommon.refreshSavLst(data);
		} else {
			// ----------------2009��3��16��weny���
			ABParmFactory.Factory.shutdown();
			super.handleShellCloseEvent();
		}
	}
	

}
