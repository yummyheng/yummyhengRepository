package excelOut;

import java.util.ArrayList;
import java.util.List;

import AF.rf.AFfrObjFactory;
import AF.ut.AFFileSys;
import ExcelDAO.ExcelOperDAO;
import UB.pub.UBViewDesCG;
import cdc.uc.cms.beo.IpnMng;
import cn.sgi.tf.tm.SvcRes;

import com.thoughtworks.xstream.XStream;

public class Tout {
	static AFfrObjFactory objFct = UBViewDesCG.ObjFct;
	static String dataRootPath="E:/MyWork/00-Ӧ��ϵͳ�ĵ�/����Ʒ��Ŀ/����ƷExcle���/��ӡ��������/";
	static String outRootPath="E:/MyWork/00-Ӧ��ϵͳ�ĵ�/����Ʒ��Ŀ/����ƷExcle���/��ӡ��������/";
	
	public static void main(String[] args) {
		try {
		
			dbpOut();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void dbpOut() throws Exception{
		
		dbpTest1();							//�����г�Ѻ��̨�ˣ���Ѻ̨��1-1��/�����г�Ѻ��̨�ˣ���Ѻ̨��1-1��
	
//		dbpTest2();							//��Ѻ��̨��(���Ķ�)/����ɳ�Ѻ��̨��
//		
//		dbpTest3();							//��Ѻ����̨��(���Ķ�)/�����г�Ѻ����̨��(��ҵ�����)
//		
//		dbpTest4();							//��Ѻ����̨��(���Ķ�)/�����г�Ѻ����̨��(��ҵ������)
//		
//		dbpTest5();							//��Ѻ����̨��(���Ķ�)/����ɳ�Ѻ����̨�ˣ���ҵ�����)
//		
//		dbpTest6();							//��Ѻ����̨��(���Ķ�)/����ɳ�Ѻ����̨�ˣ���ҵ������)
//		
//		dbpTest7();							//��Ѻ��̨��(���Ķ�)/��������Ѻ��̨��
//		
//		dbpTest8();							//��Ѻ��̨��(���Ķ�)/�������Ѻ��̨��
//		
//		dbpTest9();							//��Ѻ����̨��(���Ķ�)/��������Ѻ����̨�ˣ���ҵ�����)
//		
//		dbpTest10();						//��Ѻ����̨��(���Ķ�)/��������Ѻ����̨�ˣ���ҵ������)
//		
//		dbpTest11();						//��Ѻ����̨��(���Ķ�)/�������Ѻ����̨�ˣ���ҵ�����)
//		
//		dbpTest12();						//��Ѻ����̨��(���Ķ�)/�������Ѻ����̨�ˣ���ҵ�����ƣ�
		
//		dbpTest13();
	}
	
	
	//�����г�Ѻ��̨�ˣ���Ѻ̨��1-1��
	public static void dbpTest1() throws Exception{
		String outFileName;
		String dataPath;
		ExcelFormatOut_I out=new ExcelFormatOut01();//
		
		
		outFileName="�����г�Ѻ��̨�ˣ���Ѻ̨��1-1��/A0001000001(20120101-20130220)";
		dataPath="�����г�Ѻ��̨�ˣ���Ѻ̨��1-1��/ReponseA0001000001(20120101-20130220)/Reponse_10081250";
		doExport(dataPath,outFileName,out);
		
		outFileName="�����г�Ѻ��̨�ˣ���Ѻ̨��1-1��/A0002000001(20120101-20130220)";
		dataPath="�����г�Ѻ��̨�ˣ���Ѻ̨��1-1��/ReponseA0002000001(20120101-20130220)/Reponse_10081250";
		doExport(dataPath,outFileName,out);
		
		outFileName="�����г�Ѻ��̨�ˣ���Ѻ̨��1-1��/A0004000001(20120101-20130220)";
		dataPath="�����г�Ѻ��̨�ˣ���Ѻ̨��1-1��/ReponseA0004000001(20120101-20130220)/Reponse_10081250";
		doExport(dataPath,outFileName,out);
		
	}
	//��Ѻ��̨��(���Ķ�)/����ɳ�Ѻ��̨��
	public static void dbpTest2() throws Exception{
		String outFileName;
		String dataPath;
		ExcelFormatOut_I out=new ExcelFormatOut02();//
		
		
		outFileName="��Ѻ��̨��(���Ķ�)/����ɳ�Ѻ��̨��(A0001000001)";
		dataPath="��Ѻ��̨��(���Ķ�)/Reponse-����ɳ�Ѻ��̨��(A0001000001)/Reponse_10081260";
		doExport(dataPath,outFileName,out);
		
		outFileName="��Ѻ��̨��(���Ķ�)/����ɳ�Ѻ��̨��(A0002000001)";
		dataPath="��Ѻ��̨��(���Ķ�)/Reponse-����ɳ�Ѻ��̨��(A0002000001)/Reponse_10081260";
		doExport(dataPath,outFileName,out);
		
		outFileName="��Ѻ��̨��(���Ķ�)/����ɳ�Ѻ��̨��(A0004000001)";
		dataPath="��Ѻ��̨��(���Ķ�)/Reponse-����ɳ�Ѻ��̨��(A0004000001)/Reponse_10081260";
		doExport(dataPath,outFileName,out);
	}
	//��Ѻ����̨��(���Ķ�)/�����г�Ѻ����̨��(��ҵ�����)
	public static void dbpTest3() throws Exception{
		String outFileName;
		String dataPath;
		ExcelFormatOut_I out=new ExcelFormatOut03();//
		
		
		outFileName="��Ѻ����̨��(���Ķ�)/�����г�Ѻ����̨�ˣ���ҵ�����A0001000001��";
		dataPath="��Ѻ����̨��(���Ķ�)/Reponse-�����г�Ѻ����̨�ˣ���ҵ�����A0001000001��/Reponse_10081280";
		doExport(dataPath,outFileName,out);
		
		outFileName="��Ѻ����̨��(���Ķ�)/�����г�Ѻ����̨�ˣ���ҵ�����A0002000001��";
		dataPath="��Ѻ����̨��(���Ķ�)/Reponse-�����г�Ѻ����̨�ˣ���ҵ�����A0002000001��/Reponse_10081280";
		doExport(dataPath,outFileName,out);
		
		outFileName="��Ѻ����̨��(���Ķ�)/�����г�Ѻ����̨�ˣ���ҵ�����A0004000001��";
		dataPath="��Ѻ����̨��(���Ķ�)/Reponse-�����г�Ѻ����̨�ˣ���ҵ�����A0004000001��/Reponse_10081280";
		doExport(dataPath,outFileName,out);
		
	}
	
	//��Ѻ����̨��(���Ķ�)/�����г�Ѻ����̨��(��ҵ������)
	public static void dbpTest4() throws Exception{
		String outFileName;
		String dataPath;
		ExcelFormatOut_I out=new ExcelFormatOut04();//
		
		outFileName="��Ѻ����̨��(���Ķ�)/�����г�Ѻ����̨�ˣ���ҵ������A0001000001��";
		dataPath="��Ѻ����̨��(���Ķ�)/Reponse-�����г�Ѻ����̨�ˣ���ҵ������A0001000001��/Reponse_10081270";
		doExport(dataPath,outFileName,out);
		
		outFileName="��Ѻ����̨��(���Ķ�)/�����г�Ѻ����̨�ˣ���ҵ������A0002000001��";
		dataPath="��Ѻ����̨��(���Ķ�)/Reponse-�����г�Ѻ����̨�ˣ���ҵ������A0002000001��/Reponse_10081270";
		doExport(dataPath,outFileName,out);
		
		outFileName="��Ѻ����̨��(���Ķ�)/�����г�Ѻ����̨�ˣ���ҵ������A0004000001��";
		dataPath="��Ѻ����̨��(���Ķ�)/Reponse-�����г�Ѻ����̨�ˣ���ҵ������A0004000001��/Reponse_10081270";
		doExport(dataPath,outFileName,out);
		
	}
	
	//��Ѻ����̨��(���Ķ�)/����ɳ�Ѻ����̨�ˣ���ҵ�����)
	public static void dbpTest5() throws Exception{
		String outFileName;
		String dataPath;
		ExcelFormatOut_I out=new ExcelFormatOut05();//
		
		outFileName="��Ѻ����̨��(���Ķ�)/����ɳ�Ѻ����̨�ˣ���ҵ�����A0001000001��";
		dataPath="��Ѻ����̨��(���Ķ�)/Reponse-����ɳ�Ѻ����̨�ˣ���ҵ�����A0001000001��/Reponse_10081300";
		doExport(dataPath,outFileName,out);
		
		outFileName="��Ѻ����̨��(���Ķ�)/����ɳ�Ѻ����̨�ˣ���ҵ�����A0002000001��";
		dataPath="��Ѻ����̨��(���Ķ�)/Reponse-����ɳ�Ѻ����̨�ˣ���ҵ�����A0002000001��/Reponse_10081300";
		doExport(dataPath,outFileName,out);
		
		outFileName="��Ѻ����̨��(���Ķ�)/����ɳ�Ѻ����̨�ˣ���ҵ�����A0004000001��";
		dataPath="��Ѻ����̨��(���Ķ�)/Reponse-����ɳ�Ѻ����̨�ˣ���ҵ�����A0004000001��/Reponse_10081300";
		doExport(dataPath,outFileName,out);
		
	}
	
	
	//��Ѻ����̨��(���Ķ�)/����ɳ�Ѻ����̨�ˣ���ҵ������)
	public static void dbpTest6() throws Exception{
		String outFileName;
		String dataPath;
		ExcelFormatOut_I out=new ExcelFormatOut06();//
		
		outFileName="��Ѻ����̨��(���Ķ�)/����ɳ�Ѻ����̨�ˣ���ҵ������A0001000001��";
		dataPath="��Ѻ����̨��(���Ķ�)/Reponse-����ɳ�Ѻ����̨�ˣ���ҵ������A0001000001��/Reponse_10081290";
		doExport(dataPath,outFileName,out);
		
		outFileName="��Ѻ����̨��(���Ķ�)/����ɳ�Ѻ����̨�ˣ���ҵ������A0002000001��";
		dataPath="��Ѻ����̨��(���Ķ�)/Reponse-����ɳ�Ѻ����̨�ˣ���ҵ������A0002000001��/Reponse_10081290";
		doExport(dataPath,outFileName,out);
		
		outFileName="��Ѻ����̨��(���Ķ�)/����ɳ�Ѻ����̨�ˣ���ҵ������A0004000001��";
		dataPath="��Ѻ����̨��(���Ķ�)/Reponse-����ɳ�Ѻ����̨�ˣ���ҵ������A0004000001��/Reponse_10081290";
		doExport(dataPath,outFileName,out);
		
	}
	
	//��Ѻ��̨��(���Ķ�)/��������Ѻ��̨��
	public static void dbpTest7() throws Exception{
		String outFileName;
		String dataPath;
		ExcelFormatOut_I out=new ExcelFormatOut07();//
		
		outFileName="��Ѻ��̨��(���Ķ�)/��������Ѻ��̨��(A0076000013)";
		dataPath="��Ѻ��̨��(���Ķ�)/Reponse-��������Ѻ��̨��(A0076000013)/Reponse_10081310";
		doExport(dataPath,outFileName,out);
		
		outFileName="��Ѻ��̨��(���Ķ�)/��������Ѻ��̨��(Z0010000000)";
		dataPath="��Ѻ��̨��(���Ķ�)/Reponse-��������Ѻ��̨��(Z0010000000)/Reponse_10081310";
		doExport(dataPath,outFileName,out);
		
		outFileName="��Ѻ��̨��(���Ķ�)/��������Ѻ��̨��(Z1002000000)";
		dataPath="��Ѻ��̨��(���Ķ�)/Reponse-��������Ѻ��̨��(Z1002000000)/Reponse_10081310";
		doExport(dataPath,outFileName,out);
		
	}
	//��Ѻ��̨��(���Ķ�)/�������Ѻ��̨��
	public static void dbpTest8() throws Exception{
		String outFileName;
		String dataPath;
		ExcelFormatOut_I out=new ExcelFormatOut08();//
		
		outFileName="��Ѻ��̨��(���Ķ�)/�������Ѻ��̨��(A0076000013)";
		dataPath="��Ѻ��̨��(���Ķ�)/Reponse-�������Ѻ��̨��(A0076000013)/Reponse_10081320";
		doExport(dataPath,outFileName,out);
		
		outFileName="��Ѻ��̨��(���Ķ�)/�������Ѻ��̨��(Z0010000000)";
		dataPath="��Ѻ��̨��(���Ķ�)/Reponse-�������Ѻ��̨��(Z0010000000)/Reponse_10081320";
		doExport(dataPath,outFileName,out);
		
		outFileName="��Ѻ��̨��(���Ķ�)/�������Ѻ��̨��(Z1002000000)";
		dataPath="��Ѻ��̨��(���Ķ�)/Reponse-�������Ѻ��̨��(Z1002000000)/Reponse_10081320";
		doExport(dataPath,outFileName,out);
		
	}

	//��Ѻ����̨��(���Ķ�)/��������Ѻ����̨�ˣ���ҵ�����)
	public static void dbpTest9() throws Exception{
		String outFileName;
		String dataPath;
		ExcelFormatOut_I out=new ExcelFormatOut09();//
		
		outFileName="��Ѻ����̨��(���Ķ�)/��������Ѻ����̨�ˣ���ҵ�����A0076000013��";
		dataPath="��Ѻ����̨��(���Ķ�)/Reponse-��������Ѻ����̨�ˣ���ҵ�����A0076000013��/Reponse_10081340";
		doExport(dataPath,outFileName,out);
		
		outFileName="��Ѻ����̨��(���Ķ�)/��������Ѻ����̨�ˣ���ҵ�����Z0010000000��";
		dataPath="��Ѻ����̨��(���Ķ�)/Reponse-��������Ѻ����̨�ˣ���ҵ�����Z0010000000��/Reponse_10081340";
		doExport(dataPath,outFileName,out);
		
		outFileName="��Ѻ����̨��(���Ķ�)/��������Ѻ����̨�ˣ���ҵ�����Z1002000000��";
		dataPath="��Ѻ����̨��(���Ķ�)/Reponse-��������Ѻ����̨�ˣ���ҵ�����Z1002000000��/Reponse_10081340";
		doExport(dataPath,outFileName,out);
		
		
		
	}
	//��Ѻ����̨��(���Ķ�)/��������Ѻ����̨�ˣ���ҵ������)
	public static void dbpTest10() throws Exception{
		String outFileName;
		String dataPath;
		ExcelFormatOut_I out=new ExcelFormatOut10();//
		
		outFileName="��Ѻ����̨��(���Ķ�)/��������Ѻ����̨�ˣ���ҵ������A0076000013��";
		dataPath="��Ѻ����̨��(���Ķ�)/Reponse-��������Ѻ����̨�ˣ���ҵ������A0076000013��/Reponse_10081330";
		doExport(dataPath,outFileName,out);
		
		outFileName="��Ѻ����̨��(���Ķ�)/��������Ѻ����̨�ˣ���ҵ������Z0010000000��";
		dataPath="��Ѻ����̨��(���Ķ�)/Reponse-��������Ѻ����̨�ˣ���ҵ������Z0010000000��/Reponse_10081330";
		doExport(dataPath,outFileName,out);
		
		outFileName="��Ѻ����̨��(���Ķ�)/��������Ѻ����̨�ˣ���ҵ������Z1002000000��";
		dataPath="��Ѻ����̨��(���Ķ�)/Reponse-��������Ѻ����̨�ˣ���ҵ������Z1002000000��/Reponse_10081330";
		doExport(dataPath,outFileName,out);
		
		
		
	}
	
	//��Ѻ����̨��(���Ķ�)/�������Ѻ����̨�ˣ���ҵ�����)
	public static void dbpTest11() throws Exception{
		String outFileName;
		String dataPath;
		ExcelFormatOut_I out=new ExcelFormatOut11();//
		
		outFileName="��Ѻ����̨��(���Ķ�)/�������Ѻ����̨�ˣ���ҵ�����A0076000013��";
		dataPath="��Ѻ����̨��(���Ķ�)/Reponse-�������Ѻ����̨�ˣ���ҵ�����A0076000013��/Reponse_10081360";
		doExport(dataPath,outFileName,out);
		
		outFileName="��Ѻ����̨��(���Ķ�)/�������Ѻ����̨�ˣ���ҵ�����Z0010000000��";
		dataPath="��Ѻ����̨��(���Ķ�)/Reponse-�������Ѻ����̨�ˣ���ҵ�����Z0010000000��/Reponse_10081360";
		doExport(dataPath,outFileName,out);
		
		outFileName="��Ѻ����̨��(���Ķ�)/�������Ѻ����̨�ˣ���ҵ�����Z1002000000��";
		dataPath="��Ѻ����̨��(���Ķ�)/Reponse-�������Ѻ����̨�ˣ���ҵ�����Z1002000000��/Reponse_10081360";
		doExport(dataPath,outFileName,out);
		
		
	}
	
	//��Ѻ����̨��(���Ķ�)/�������Ѻ����̨�ˣ���ҵ�����ƣ�
	public static void dbpTest12() throws Exception{
		String outFileName;
		String dataPath;
		ExcelFormatOut_I out=new ExcelFormatOut12();//
		
		outFileName="��Ѻ����̨��(���Ķ�)/�������Ѻ����̨�ˣ���ҵ������A0076000013��";
		dataPath="��Ѻ����̨��(���Ķ�)/Reponse-�������Ѻ����̨�ˣ���ҵ������A0076000013��/Reponse_10081350";
		doExport(dataPath,outFileName,out);
		
		outFileName="��Ѻ����̨��(���Ķ�)/�������Ѻ����̨�ˣ���ҵ������Z0010000000��";
		dataPath="��Ѻ����̨��(���Ķ�)/Reponse-�������Ѻ����̨�ˣ���ҵ������Z0010000000��/Reponse_10081350";
		doExport(dataPath,outFileName,out);
		
		outFileName="��Ѻ����̨��(���Ķ�)/�������Ѻ����̨�ˣ���ҵ������Z1002000000��";
		dataPath="��Ѻ����̨��(���Ķ�)/Reponse-�������Ѻ����̨�ˣ���ҵ������Z1002000000��/Reponse_10081350";
		doExport(dataPath,outFileName,out);
		
	}
	public static void dbpTest13() throws Exception{
		String outFileName;
		ExcelFormatOut_I out=new ExcelFormatOut13();//

		
		List demoList=new ArrayList();
		demoList=ExcelOperDAO.findList(IpnMng.class.getName());//��excel�ж�ȡ����
    	
		IpnMng aRcd=(IpnMng)demoList.get(0);
		
		objFct.sendObj(aRcd, "10080420Print1");
		objFct.sendObj(demoList, "10080420Print2");
		
		outFileName="��Ѻ�����ѯ/��Ѻ�����ѯ�б�(A0076000013).xls";
		out.export(outRootPath+outFileName, "sheet");
		
		
	}
	
	
	public static void doExport(String dataPath,String outFileName,ExcelFormatOut_I oper) throws Exception{
		XStream aX=new XStream();
		
		String  loadPath=dataRootPath+dataPath;
		String xml = AFFileSys.readFileToString(loadPath);
		SvcRes svcRes= (SvcRes)aX.fromXML(xml);
		
		Object reponse =svcRes.getBusObj("response");
		objFct.sendObj(reponse,"ESBReponseInFct");
		
		String outPath=outRootPath+outFileName+".xls";
		oper.export(outPath, "sheet1");
		
		
		
		
		System.out.println("");
		System.out.println("loadPath="+loadPath+"\n outFileName="+outFileName+" ������ɣ�");
		System.out.println("");
	}


	
		
		
	 private void saveResponse(){

			
			
			//��ESB���ؽ�����л�����.����ʹ��
//		 	response = doSendToClient(req, UServerUrl);
//			XStream st = new XStream();
//			String path = "./Reponse";
//			File file2 = new File(path);
//			if(!file2.exists()){
//				file2.mkdirs();
//			}
//			AFFileSys.writeStringToFile(path+"/Reponse_"+SvcDes.getID(), st.toXML(response));
	 }
	 
	 
	 
}
