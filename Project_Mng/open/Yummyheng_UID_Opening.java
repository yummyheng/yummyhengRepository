package open;

import AF.pub.AFexcDataArea;
import pub.UcvWindow;

public class Yummyheng_UID_Opening {
	
	public static void main(String[] args) {
			
		String uid = "10020100"; 
		
		AFexcDataArea xda = AFexcDataArea.getExcFct();	//static�ı���
		xda.putData("#AppCode","cdc");	//�������ݿ���ǰ���õ��ļ� ���а�UC�ļ����µ�init�ļ�	
										//�򽻻����������
		UcvWindow window = new UcvWindow("cdc#UID" + uid, "yummy_heng", null);	//final��display
		
		window.open();   
			
	}
}
