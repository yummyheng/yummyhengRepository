package zpl;

import AF.pub.AFexcDataArea;
import pub.UcvWindow;

public class ZPL_UID_Manage {
	public static void main(String[] args) {
		try {
			//��������
//			UBExcObj initData = new UBExcObj();
//			initData.putData("@InitParam", new AFKeyVal("aa=true,bb=false"));
			
			
			AFexcDataArea xda = AFexcDataArea.getExcFct();
			xda.putData("#AppCode","zpl");//���ø�ֵ��Ŀ����Ҫ��Ϊ�˻�ȡ�ض������ݿ���������
			

//			UcvWindow window = new UcvWindow("cdc#UIDzplZqpl01001","GL",null);//ծȯ��¶��Ϣ��ҳ��
//			UcvWindow window = new UcvWindow("cdc#UIDzplZqpl01002","GL",null);//ծȯ��¶��Ϣ�༭ҳ��
//			UcvWindow window = new UcvWindow("cdc#UIDzplZqpl01003","GL",null);//ծȯ��¶�����ؼ�¼�鿴ҳ��
			UcvWindow window = new UcvWindow("cdc#UIDzplSjsh01001","RK",null);//�ҵ�����ҳ��
//			UcvWindow window = new UcvWindow("cdc#UIDzplCwsj01002","GL",null);//�������ݲ�ѯ
//			UcvWindow window = new UcvWindow("cdc#UIDzplYwtz01001","GL",null);//���Ķ�֪ͨ
//			UcvWindow window = new UcvWindow("cdc#UIDzplYwtz01003","GL",null);//�ͻ���֪ͨ
//			UcvWindow window = new UcvWindow("cdc#UIDzplZqpl01003","GL",null);//ծȯ��¶�������ݲ鿴
//			UcvWindow window = new UcvWindow("cdc#UIDzplSjsh01001","GL",null);//ծȯ��¶�ҵ�����ҳ��
//			UcvWindow window = new UcvWindow("cdc#UIDzplSjsh01003","RK",null);//ծȯ��¶�Ҳ����������ҳ��
//			UcvWindow window = new UcvWindow("cdc#UIDzplSjsh01005","GL",null);//ծȯ��¶������ʵ������
//			
//			UcvWindow window = new UcvWindow("cdc#UIDzplCsdj01004","GL",null);//��ʼ�Ǽǲ��ϲ������ݲ鿴
//	 		UcvWindow window = new UcvWindow("cdc#UIDzplCsdj01001","GL",null);//��ʼ�Ǽ�	
//			UcvWindow window = new UcvWindow("cdc#UIDzplCsdj01003","RK",null);//��ʼ�Ǽ������б�
//			UcvWindow window = new UcvWindow("cdc#UIDzplYwtz01001","GL",null);//ҵ��֪ͨ��ѯ
//			UcvWindow window = new UcvWindow("cdc#UIDzplYwtz01001","GL",null);//���Ķ�֪ͨ	 
//			UcvWindow window = new UcvWindow("cdc#UIDzplYwtz01003","RK",null);//�ͻ���֪ͨ

// 		   UcvWindow window = new UcvWindow("cdc#UIDzplgzlys01001","GL",null);//������ӳ��
// 		   UcvWindow window = new UcvWindow("cdc#UIDzplFxzc01001","GL",null);//������
// 		   UcvWindow window = new UcvWindow("cdc#UIDzplCommon01004","GL",null);//��������
			
//		   UcvWindow window = new UcvWindow("cdc#UIDzplBPM01001","RK",null);//������ģ�͹���(���Ķ�)
			
//			UcvWindow window = new UcvWindow("cdc#UIDzplRight01001","GL",null);//ծȯ�������
//			UcvWindow window = new UcvWindow("cdc#UIDzplRight01003","GL",null);//ծȯ����������
			
//			UcvWindow window = new UcvWindow("cdc#UIDzplRight01005","GL",null);//�Ǽǲ��Ϲ���
//			UcvWindow window = new UcvWindow("cdc#UIDzplRight01008","GL",null);//�Ǽǲ��Ϸ������

//			UcvWindow window = new UcvWindow("cdc#UIDzplRight01009","GL",null);//�ͻ���Ϣ��ѯ
//			UcvWindow window = new UcvWindow("cdc#UIDzplRight01010","GL",null);//�ͻ��ʸ����
			
//			UcvWindow window = new UcvWindow("cdc#UIDzplCommon01003","GL",null);//����ҳ�淢���˲�ѯ
//			UcvWindow window = new UcvWindow("cdc#UIDzplFxzc01001","GL",null);//������ά����ѯ
//			UcvWindow window = new UcvWindow("cdc#UIDzplCwsj01001","GL",null);//���������ϴ���ѯ
//			UcvWindow window = new UcvWindow("cdc#UIDzplCwsj01002","GL",null);//�������ݲ�ѯ
//			UcvWindow window = new UcvWindow("cdc#UIDzplCwsj01006","GL",null);//�������ݲ�ѯ(���Ķ�)
			
//			UcvWindow window = new UcvWindow("cdc#UIDzplBPM01001","GL",null);//������ģ�͹���(���Ķ�)
			
//			UcvWindow window = new UcvWindow("cdc#UIDzplCommon01008","GL",null);// ���ù���(�ļ��ߴ��趨��)
//			UcvWindow window = new UcvWindow("cdc#UIDzplCwsj01001","GL",null);//���������ϴ�
//			UcvWindow window = new UcvWindow("cdc#UIDzplCwsj01002","GL",null);//�������ݲ�ѯ
//			UcvWindow window = new UcvWindow("cdc#UIDzplCwsj01005","GL",null);//��������ģ���ϴ�
			
//		   UcvWindow window = new UcvWindow("cdc#UIDzplCwsj01006","GL",null);//�������ݲ�ѯ�����Ķˣ�			
//		   UcvWindow window = new UcvWindow("cdc#UIDzplCwsj01004","GL",null);//��������ͳ�ƣ����Ķˣ�			
		   
			
 		   window.open();   
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}