package zpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.cdc.esb.api.ESBClient;
import com.cdc.esb.api.ESBDataSet;
import com.cdc.esb.api.ESBRequest;
import com.cdc.esb.api.ESBRequestImpl;
import com.cdc.esb.api.ESBResponse;

public class ClientTest2 {

	public static void main(String[] args) {

		try {
			//����ESBClient���󣬲���ΪchannelID�������ַ��
			ESBClient client = new ESBClient("1","http://10.41.240.6:9083/cwrkWS/services/cwrkWebServiceForESB");
			// ����ESBRequestImpl����
			ESBRequest esbReq = new ESBRequestImpl();
			//����ServiceID
			
			/*��ѯ��������Ϣ*/
//			esbReq.setServiceId("9000000001");
			
			/*��ѯ������*/
			//esbReq.setServiceId("9000000002");
			
			/*ͳ�Ʒ�������¶���񱨱����ϸ����ڲ����������ͺ�ͳ������ ��������:jb(����),nb(�걨)*/
			esbReq.setServiceId("9000000003");
			esbReq.setParameter("statDate", "2011-04-30");//20110430
			esbReq.setParameter("bblx", "nb");
			esbReq.setParameter("tjrq", "2010��");
			
			/*ͳ�Ʒ����˲��񱨱�������*/
//			esbReq.setServiceId("9000000004");
//			esbReq.setParameter("statDate", "2010-10-10");
//			esbReq.setParameter("bblx", "jb");
//			esbReq.setParameter("tjrq", "2010");
//			
			/*��ѯ��������Ϣ����ڲ���������*/
			//esbReq.setServiceId("9000000005");
			//esbReq.setParameter("bbbh", "22");

			/*��ѯ�ֽ���������ڲ���������*/
			//esbReq.setServiceId("9000000006");
			//esbReq.setParameter("bbbh", "22");
			
			/*��ѯ�������ڲ���������*/
			//esbReq.setServiceId("9000000007");
			//esbReq.setParameter("bbbh", "22");

			/*��ѯ�ʲ����ر���ڲ���������*/
			//esbReq.setServiceId("9000000008");
			//esbReq.setParameter("bbbh", "22");
			
			/*��ѯ���������ϱ���ڲ���������*/
			//esbReq.setServiceId("9000000009");
			//esbReq.setParameter("bbbh", "22");
			
			/*��ѯͬ�������̡�ͬ�����ˡ�ͬ���񱨱���ϴ��ļ�����ڲ�����λ���ơ������˱�š�����*/
//			esbReq.setServiceId("9000000010");
//			esbReq.setParameter("dwmc", "�ƲƲ�������");
//			esbReq.setParameter("fxrbh", "0000000942");
//			esbReq.setParameter("bblx", "2009��");
			
			/*��ѯ���񱨱�*/
//			esbReq.setServiceId("9000000011");
//			esbReq.setParameter("stime", "20010101");
//			esbReq.setParameter("etime", "20111231");
//			esbReq.setParameter("kjnd", "2011���1��");
			/*��ѯ���񱨱������*/
//			esbReq.setServiceId("9000000015");
//			esbReq.setParameter("bbbh", "999999");
			
			
			
			/*�����������*/
//			esbReq.setServiceId("9000000014");
//			esbReq.setParameter("rybh","123456");
//			esbReq.setParameter("mc","Ǯ��Ȩ");
//			esbReq.setParameter("dwmc","����");
//			esbReq.setParameter("bblx","2011��");
//			esbReq.setParameter("fxrbh","0000012102");
//			esbReq.setParameter("bbbb","1.1");
//			esbReq.setParameter("fzbsjdw","�����Ԫ");
//			esbReq.setParameter("lrbsjdw","�����Ԫ");
//			esbReq.setParameter("llbsjdw","�����Ԫ");
//			ESBDataSet cwbbDataSet= new ESBDataSet();
//			String[] cwbbDataSetCols = {"fxrzlqymc","fxrzlkjnd","fxrzlsjsws","fxrzlzckjs","fxrzlqydm","fxrzlsjdm","fxrzlzbdm","fxrzlgxdm",
//					"fxrzlbsdm","fxrzldqdm","fxrzljjlx","fxrzlzzxs","fxrzljylx","FXRZLSJYJ","fxrzlclnf","fxrzlbblx","fzbdw","fzbrq",
//					"fzbzyyhkqm","fzbtykqm","fzbgjsqm","fzbcczjqm","fzbjyzcqm","fzbyszcqm","fzbmrfszcqm","FZBZQTSQM","FZBYSLXQM","FZBDKDKQM",
//					"FZBKCSZCQM","FZBCYTZQM","FZBGQTZQM","FZBTZXFDCQM","FZBGDZCQM","FZBWXZCQM","FZBSYQM","FZBDYSDSQM","FZBQTZCQM","FZBZCZJQM",
//					"FZBZYYHKNC","FZBTYKNC","FZBGJSNC","FZBCCZJNC","FZBJYZCNC","FZBYSZCNC","FZBMRFSZCNC","FZBZQTSNC","FZBYSLXNC","FZBDKDKNC",
//					"FZBKCSZCNC","FZBCYTZNC","FZBGQTZNC","FZBTZXFDCNC","FZBGDZCNC","FZBWXZCNC","FZBSYNC","FZBDYSDSNC","FZBQTZCNC","FZBZCZJNC",
//					"FZBZYYHJKQM","FZBQTCFDKQM","FZBCRZJQM","FZBJYJRFZQM","FZBYSJRFZQM","FZBMCHGZCQM","FZBXSCKQM","FZBZGXCQM","FZBYJFSQM",
//					"FZBYFLXQM","FZBYJFZQM","FZBYFZQQM","FZBSDSFZQM","FZBQTFZQM","FZBFZHJQM","FZBSSZBQM","FZBZBGJQM","FZBKCGQM","FZBYYGJQM",
//					"FZBFXZBQM","FZBWFPLRQM","FZBZSCEQM","FZBSYZQYHJQM","FZBSSGDQYQM","FZBQYHJQM","FZBFZQYZJQM","FZBZYYHJKNC","FZBQTCFDKNC",
//					"FZBCRZJNC","FZBJYJRFZNC","FZBYSJRFZNC","FZBMCHGZCNC","FZBXSCKNC","FZBZGXCNC","FZBYJFSNC","FZBYFLXNC","FZBYJFZNC","FZBYFZQNC",
//					"FZBSDSFZNC","FZBQTFZNC","FZBFZHJNC","FZBSSZBNC","FZBZBGJNC","FZBKCGNC","FZBYYGJNC","FZBFXZBNC","FZBWFPLRNC","FZBZSCENC",
//					"FZBSYZQYHJNC","FZBSSGDQYNC","FZBQYHJNC","FZBFZQYZJNC","LRBZDW","LRBRQ","LRBYYSRBQ","LRBLXJSRBQ","LRBLXSRBQ",
//					"LRBLXZCBQ","LRBSXFYJSRBQ","LRBYJSRBQ","LRBYJZCBQ","LRBTZSYBQ","LRBQYTZSYBQ","LRBJZBDSYBQ","LRBHDSYBQ","LRBQTSRBQ","LRBYYZCBQ",
//					"LRBYYSJBQ","LRBYWGLFBQ","LRBZCJCSSBQ","LRBQTCBBQ","LRBYYLRBQ","LRBYYWSRBQ","LRBYYWZCBQ","LRBLRZEBQ","LRBSDSFYBQ","LRBJLRBQ",
//					"LRBMGSJLRBQ","LRBGDSYBQ","LRBMGSYBQ","LRBJBSYBQ","LRBXSSYBQ","LRBQTZHSYBQ","LRBZHSYZEBQ","LRBMGSSYBQ","LRBGDZHSYBQ",
//					"LRBYYSRSQ","LRBLXJSRSQ","LRBLXSRSQ","LRBLXZCSQ","LRBSXFYJSRSQ","LRBYJSRSQ","LRBYJZCSQ","LRBTZSYSQ","LRBQYTZSYSQ",
//					"LRBJZBDSYSQ","LRBHDSYSQ","LRBQTSRSQ","LRBYYZCSQ","LRBYYSJSQ","LRBYWGLFSQ","LRBZCJCSSSQ","LRBQTCBSQ","LRBYYLRSQ","LRBYYWSRSQ",
//					"LRBYYWZCSQ","LRBLRZESQ","LRBSDSFYSQ","LRBJLRSQ","LRBMGSJLRSQ","LRBGDSYSQ","LRBMGSYSQ","LRBJBSYSQ","LRBXSSYSQ","LRBQTZHSYSQ",
//					"LRBZHSYZESQ","LRBMGSSYSQ","LRBGDZHSYSQ","LLBBZDW","LLBRQ","LLBCKJZJEBQ","LLBJKJZJEBQ","LLBCRZJZJEBQ","LLBLXSXFXJBQ",
//					"LLBJYHDXJBQ","LLBJYXJLRBQ","LLBKHDJZJBQ","LLBZYYHTYZJBQ","LLBZFSYJYBQ","LLBZFZGXJBQ","LLBGXSFBQ","LLBZFJYHDBQ",
//					"LLBJYXJLCXJBQ","LLBJYXJLLBQ","LLBSRTZHSXJBQ","LLBQDSYXJBQ","LLBSDQTHDXJBQ","LLBTZHDXJLRBQ","LLBTZZFXJBQ","LLBGDWXQTXJBQ",
//					"LLBZFQTHDXJBQ","LLBTZHDXJCHBQ","LLBTZHDXJLLBQ","LLBXSTZSDXJBQ","LLBFXZQSDXJBQ","LLBSDQTCJHDXJBQ","LLBCZHDXJLRBQ",
//					"LLBCHZWZFBQ","LLBFPGLLRZFXJBQ","LLBZFQTCZHDXJBQ","LLBCZHDXJLXBQ","LLBCZHDXJLLBQ","LLBHLBDDJWBQ","LLBXJDJWZJBQ","LLBQCXJDJBQ",
//					"LLBCKJZJESQ","LLBJKJZJESQ","LLBCRZJZJESQ","LLBLXSXFXJSQ","LLBJYHDXJSQ","LLBJYXJLRSQ","LLBKHDJZJSQ","LLBZYYHTYZJSQ",
//					"LLBZFSYJYSQ","LLBZFZGXJSQ","LLBGXSFSQ","LLBZFJYHDSQ","LLBJYXJLCXJSQ","LLBJYXJLLSQ","LLBSRTZHSXJSQ","LLBQDSYXJSQ",
//					"LLBSDQTHDXJSQ","LLBTZHDXJLRSQ","LLBTZZFXJSQ","LLBGDWXQTXJSQ","LLBZFQTHDXJSQ","LLBTZHDXJCHSQ","LLBTZHDXJLLSQ",
//					"LLBXSTZSDXJSQ","LLBFXZQSDXJSQ","LLBSDQTCJHDXJSQ","LLBCZHDXJLRSQ","LLBCHZWZFSQ","LLBFPGLLRZFXJSQ","LLBZFQTCZHDXJSQ",
//					"LLBCZHDXJLXSQ","LLBCZHDXJLLSQ","LLBHLBDDJWSQ","LLBXJDJWZJSQ","LLBQCXJDJSQ","LLBQMXJDJSQ","LLBQMXJDJBQ"};
//			cwbbDataSet.setColumn(cwbbDataSetCols);
//			cwbbDataSet.addRow();
//			for (String column : cwbbDataSetCols) {
//				cwbbDataSet.putData(column, "1");
//			}
//			esbReq.setDataSet(cwbbDataSet);
//			String filePath="C:\\test.xlsx";			
//			String fileStr=Util.readFile2CpStr(filePath);//��ȡ�ļ�,ת��Ϊѹ���ַ�����ʽ
//			esbReq.setParameter("file",fileStr);

			
			//�����ϴ�����
//			esbReq.setServiceId("9000000016");
//			esbReq.setParameter("bbbh","999999");
//			String filePath="C:\\test.xlsx";			
//			String fileStr=Util.readFile2CpStr(filePath);//��ȡ�ļ�,ת��Ϊѹ���ַ�����ʽ
//			esbReq.setParameter("xlsfileStr",fileStr);
			
			//����ɾ�����񱨱�
//			esbReq.setServiceId("9000000013");
//			esbReq.setParameter("rybh","123456"); //��Ա���
//			esbReq.setParameter("mc","Ǯ��Ȩ");  //�ϴ�������
//			esbReq.setParameter("dwmc","����"); //�ϴ��˵�λ
//			esbReq.setParameter("bh","1006");  //������
			
			
			/*���ݱ����Ų�ѯ���񱨱���������*/
//			esbReq.setServiceId("9000000017");
//			esbReq.setParameter("bbbh","526");
			
			//����������serviceId,ESBRequest���󣬳�ʱʱ�䣬�Ƿ���м��
			ESBResponse esbRsp = client.call(esbReq,true);
			// ������ĵ���������
			System.out.println("errCode: " + esbRsp.getErrCode());
			System.out.println("errText: " + esbRsp.getErrText());
			// ��ñ��ĵ�����
			HashMap map = (HashMap) esbRsp.getParameterMap();
			
			//�鿴���������Ƿ�Ϊ��ҳ���ݡ�ҳ���ʹ����ο��������ծESBƽ̨-��ҳ���ƹ淶˵���顷
			//System.out.println("�������ݶ����ҳ�ţ�"+esbRsp.getPagination());

			/*��ѯ��������Ϣ*/
//			ESBDataSet dataset = esbRsp.getDataSet("queryFXR_DataSet");
			
			/*��ѯ������*/
			//ESBDataSet dataset = esbRsp.getDataSet("queryKjndInfo_DataSet");
			
			/*ͳ�Ʒ�������¶���񱨱����ϸ*/
			ESBDataSet dataset = esbRsp.getDataSet("statFxrDetail_DataSet");
			
			/*ͳ�Ʒ����˲��񱨱�������*/
			//ESBDataSet dataset = esbRsp.getDataSet("statFxr_DataSet");
			
			/*��ѯ��������Ϣ*/
			//ESBDataSet dataset = esbRsp.getDataSet("queryFxrInfo_DataSet");
			
			/*��ѯ�ֽ�������*/
			//ESBDataSet dataset = esbRsp.getDataSet("queryXjllb_DataSet");
			
			/*��ѯ�����*/
			//ESBDataSet dataset = esbRsp.getDataSet("queryLrb_DataSet");
			
			/*��ѯ�ʲ����ر�*/
			//ESBDataSet dataset = esbRsp.getDataSet("queryZcfzb_DataSet");
			
			/*��ѯ���������ϱ�*/
			//ESBDataSet dataset = esbRsp.getDataSet("queryFxrzlb_DataSet");
			
			/*��ѯͬ�������̡�ͬ�����ˡ�ͬ���񱨱���ϴ��ļ�*/
			//ESBDataSet dataset = esbRsp.getDataSet("queryCwbb_DataSet");
			
			/*��ѯ���񱨱�*/
//			ESBDataSet dataset = esbRsp.getDataSet("queryCWBB_DataSet");
			
			/*��ѯ���񱨱������*/
//			ESBDataSet dataset=null;
//			String writePathCom="c:\\writeBackCom.xlsx";
//			String xlsFileStr=(String)esbRsp.getParameter("file");
//			Util.writeCpStr2File(writePathCom, xlsFileStr);//��ѹ�����ַ���ת����д���ļ�
			
			/*�����������*/
//			ESBDataSet dataset=null;
//			String result=(String)esbRsp.getParameter("result");
			
			/*�����ϴ��ļ�*/
//			ESBDataSet dataset=null;
//			String result=(String)esbRsp.getParameter("result");
			
			/*����ɾ�����񱨱�*/
//			ESBDataSet dataset=null;
//			String result=(String)esbRsp.getParameter("delCWBB_result");
			
			/*���ݱ����Ų�ѯ���񱨱���������*/
//			ESBDataSet dataset = esbRsp.getDataSet("queryCwbbByBbbh_DataSet");
			//�ж�DataSet�����Ƿ�Ϊ��
			if (dataset == null) {
				System.out.println("��̨���ص�DataSet����Ϊ�գ�");
			} else {
				//��ȡ���ݵ�����				
				String[] columns = dataset.getColumn();
				//�������
				 for (String column : columns) {
					 System.out.print(column+" ,");
				 }
				 System.out.println();
				 //ѭ�����DataSet�����е�����.
				while (dataset.next()) {
					for (String column : columns) {
						//��� DataSet�����е�����.
						System.out.print(dataset.getString(column) + ",");
					}
					System.out.println();
				}
			}
			//ѭ�����ParameterMap�еĲ���.
			Set set = map.keySet();
			if (!map.isEmpty()) {
				Iterator it = set.iterator();
				while (it.hasNext()) {
					String key = (String) (it.next());
					System.out.println(key+"   "+map.get(key));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�쳣��Ϣ��" + e.getMessage());
		}
	}
}