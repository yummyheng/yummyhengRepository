package fxs;

import pub.UcvWindow;
import AF.pub.AFexcDataArea;

public class FXS_UID_Manage{
	
	public static void main(String[] args) {
		try {
			//��������
//			UBExcObj initData = new UBExcObj();
//			initData.putData("@InitParam", new AFKeyVal("aa=true,bb=false"));
			
			
			AFexcDataArea xda = AFexcDataArea.getExcFct();
			xda.putData("#AppCode","cdc");//���ø�ֵ��Ŀ����Ҫ��Ϊ�˻�ȡ�ض������ݿ���������			
			
			String uid = "cxc01001"; // ����׷��������
//			String uid = "cxc01007"; // ��ͳ�����������
//			String uid = "cxc01009"; // Ͷ���޶�������
//			String uid = "cxt01001"; // �����Ź���
			
			UcvWindow window = new UcvWindow("cdc#UID" + uid, "LB", null);
			
			window.open();   
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
