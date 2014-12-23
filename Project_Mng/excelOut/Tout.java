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
	static String dataRootPath="E:/MyWork/00-应用系统文档/担保品项目/担保品Excle输出/打印测试数据/";
	static String outRootPath="E:/MyWork/00-应用系统文档/担保品项目/担保品Excle输出/打印测试数据/";
	
	public static void main(String[] args) {
		try {
		
			dbpOut();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void dbpOut() throws Exception{
		
		dbpTest1();							//管理中出押总台账（出押台账1-1）/管理中出押总台账（出押台账1-1）
	
//		dbpTest2();							//出押总台帐(中心端)/已完成出押总台帐
//		
//		dbpTest3();							//出押分类台帐(中心端)/管理中出押分类台账(按业务类别)
//		
//		dbpTest4();							//出押分类台帐(中心端)/管理中出押分类台账(按业务名称)
//		
//		dbpTest5();							//出押分类台帐(中心端)/已完成出押分类台账（按业务类别)
//		
//		dbpTest6();							//出押分类台帐(中心端)/已完成出押分类台账（按业务名称)
//		
//		dbpTest7();							//受押总台帐(中心端)/管理中受押总台账
//		
//		dbpTest8();							//受押总台帐(中心端)/已完成受押总台账
//		
//		dbpTest9();							//受押分类台帐(中心端)/管理中受押分类台账（按业务类别)
//		
//		dbpTest10();						//受押分类台帐(中心端)/管理中受押分类台账（按业务名称)
//		
//		dbpTest11();						//受押分类台帐(中心端)/已完成受押分类台账（按业务类别)
//		
//		dbpTest12();						//受押分类台帐(中心端)/已完成受押分类台账（按业务名称）
		
//		dbpTest13();
	}
	
	
	//管理中出押总台账（出押台账1-1）
	public static void dbpTest1() throws Exception{
		String outFileName;
		String dataPath;
		ExcelFormatOut_I out=new ExcelFormatOut01();//
		
		
		outFileName="管理中出押总台账（出押台账1-1）/A0001000001(20120101-20130220)";
		dataPath="管理中出押总台账（出押台账1-1）/ReponseA0001000001(20120101-20130220)/Reponse_10081250";
		doExport(dataPath,outFileName,out);
		
		outFileName="管理中出押总台账（出押台账1-1）/A0002000001(20120101-20130220)";
		dataPath="管理中出押总台账（出押台账1-1）/ReponseA0002000001(20120101-20130220)/Reponse_10081250";
		doExport(dataPath,outFileName,out);
		
		outFileName="管理中出押总台账（出押台账1-1）/A0004000001(20120101-20130220)";
		dataPath="管理中出押总台账（出押台账1-1）/ReponseA0004000001(20120101-20130220)/Reponse_10081250";
		doExport(dataPath,outFileName,out);
		
	}
	//出押总台帐(中心端)/已完成出押总台帐
	public static void dbpTest2() throws Exception{
		String outFileName;
		String dataPath;
		ExcelFormatOut_I out=new ExcelFormatOut02();//
		
		
		outFileName="出押总台帐(中心端)/已完成出押总台帐(A0001000001)";
		dataPath="出押总台帐(中心端)/Reponse-已完成出押总台帐(A0001000001)/Reponse_10081260";
		doExport(dataPath,outFileName,out);
		
		outFileName="出押总台帐(中心端)/已完成出押总台帐(A0002000001)";
		dataPath="出押总台帐(中心端)/Reponse-已完成出押总台帐(A0002000001)/Reponse_10081260";
		doExport(dataPath,outFileName,out);
		
		outFileName="出押总台帐(中心端)/已完成出押总台帐(A0004000001)";
		dataPath="出押总台帐(中心端)/Reponse-已完成出押总台帐(A0004000001)/Reponse_10081260";
		doExport(dataPath,outFileName,out);
	}
	//出押分类台帐(中心端)/管理中出押分类台账(按业务类别)
	public static void dbpTest3() throws Exception{
		String outFileName;
		String dataPath;
		ExcelFormatOut_I out=new ExcelFormatOut03();//
		
		
		outFileName="出押分类台帐(中心端)/管理中出押分类台账（按业务类别A0001000001）";
		dataPath="出押分类台帐(中心端)/Reponse-管理中出押分类台账（按业务类别A0001000001）/Reponse_10081280";
		doExport(dataPath,outFileName,out);
		
		outFileName="出押分类台帐(中心端)/管理中出押分类台账（按业务类别A0002000001）";
		dataPath="出押分类台帐(中心端)/Reponse-管理中出押分类台账（按业务类别A0002000001）/Reponse_10081280";
		doExport(dataPath,outFileName,out);
		
		outFileName="出押分类台帐(中心端)/管理中出押分类台账（按业务类别A0004000001）";
		dataPath="出押分类台帐(中心端)/Reponse-管理中出押分类台账（按业务类别A0004000001）/Reponse_10081280";
		doExport(dataPath,outFileName,out);
		
	}
	
	//出押分类台帐(中心端)/管理中出押分类台账(按业务名称)
	public static void dbpTest4() throws Exception{
		String outFileName;
		String dataPath;
		ExcelFormatOut_I out=new ExcelFormatOut04();//
		
		outFileName="出押分类台帐(中心端)/管理中出押分类台账（按业务名称A0001000001）";
		dataPath="出押分类台帐(中心端)/Reponse-管理中出押分类台账（按业务名称A0001000001）/Reponse_10081270";
		doExport(dataPath,outFileName,out);
		
		outFileName="出押分类台帐(中心端)/管理中出押分类台账（按业务名称A0002000001）";
		dataPath="出押分类台帐(中心端)/Reponse-管理中出押分类台账（按业务名称A0002000001）/Reponse_10081270";
		doExport(dataPath,outFileName,out);
		
		outFileName="出押分类台帐(中心端)/管理中出押分类台账（按业务名称A0004000001）";
		dataPath="出押分类台帐(中心端)/Reponse-管理中出押分类台账（按业务名称A0004000001）/Reponse_10081270";
		doExport(dataPath,outFileName,out);
		
	}
	
	//出押分类台帐(中心端)/已完成出押分类台账（按业务类别)
	public static void dbpTest5() throws Exception{
		String outFileName;
		String dataPath;
		ExcelFormatOut_I out=new ExcelFormatOut05();//
		
		outFileName="出押分类台帐(中心端)/已完成出押分类台账（按业务类别A0001000001）";
		dataPath="出押分类台帐(中心端)/Reponse-已完成出押分类台账（按业务类别A0001000001）/Reponse_10081300";
		doExport(dataPath,outFileName,out);
		
		outFileName="出押分类台帐(中心端)/已完成出押分类台账（按业务类别A0002000001）";
		dataPath="出押分类台帐(中心端)/Reponse-已完成出押分类台账（按业务类别A0002000001）/Reponse_10081300";
		doExport(dataPath,outFileName,out);
		
		outFileName="出押分类台帐(中心端)/已完成出押分类台账（按业务类别A0004000001）";
		dataPath="出押分类台帐(中心端)/Reponse-已完成出押分类台账（按业务类别A0004000001）/Reponse_10081300";
		doExport(dataPath,outFileName,out);
		
	}
	
	
	//出押分类台帐(中心端)/已完成出押分类台账（按业务名称)
	public static void dbpTest6() throws Exception{
		String outFileName;
		String dataPath;
		ExcelFormatOut_I out=new ExcelFormatOut06();//
		
		outFileName="出押分类台帐(中心端)/已完成出押分类台账（按业务名称A0001000001）";
		dataPath="出押分类台帐(中心端)/Reponse-已完成出押分类台账（按业务名称A0001000001）/Reponse_10081290";
		doExport(dataPath,outFileName,out);
		
		outFileName="出押分类台帐(中心端)/已完成出押分类台账（按业务名称A0002000001）";
		dataPath="出押分类台帐(中心端)/Reponse-已完成出押分类台账（按业务名称A0002000001）/Reponse_10081290";
		doExport(dataPath,outFileName,out);
		
		outFileName="出押分类台帐(中心端)/已完成出押分类台账（按业务名称A0004000001）";
		dataPath="出押分类台帐(中心端)/Reponse-已完成出押分类台账（按业务名称A0004000001）/Reponse_10081290";
		doExport(dataPath,outFileName,out);
		
	}
	
	//受押总台帐(中心端)/管理中受押总台账
	public static void dbpTest7() throws Exception{
		String outFileName;
		String dataPath;
		ExcelFormatOut_I out=new ExcelFormatOut07();//
		
		outFileName="受押总台帐(中心端)/管理中受押总台账(A0076000013)";
		dataPath="受押总台帐(中心端)/Reponse-管理中受押总台账(A0076000013)/Reponse_10081310";
		doExport(dataPath,outFileName,out);
		
		outFileName="受押总台帐(中心端)/管理中受押总台账(Z0010000000)";
		dataPath="受押总台帐(中心端)/Reponse-管理中受押总台账(Z0010000000)/Reponse_10081310";
		doExport(dataPath,outFileName,out);
		
		outFileName="受押总台帐(中心端)/管理中受押总台账(Z1002000000)";
		dataPath="受押总台帐(中心端)/Reponse-管理中受押总台账(Z1002000000)/Reponse_10081310";
		doExport(dataPath,outFileName,out);
		
	}
	//受押总台帐(中心端)/已完成受押总台账
	public static void dbpTest8() throws Exception{
		String outFileName;
		String dataPath;
		ExcelFormatOut_I out=new ExcelFormatOut08();//
		
		outFileName="受押总台帐(中心端)/已完成受押总台账(A0076000013)";
		dataPath="受押总台帐(中心端)/Reponse-已完成受押总台账(A0076000013)/Reponse_10081320";
		doExport(dataPath,outFileName,out);
		
		outFileName="受押总台帐(中心端)/已完成受押总台账(Z0010000000)";
		dataPath="受押总台帐(中心端)/Reponse-已完成受押总台账(Z0010000000)/Reponse_10081320";
		doExport(dataPath,outFileName,out);
		
		outFileName="受押总台帐(中心端)/已完成受押总台账(Z1002000000)";
		dataPath="受押总台帐(中心端)/Reponse-已完成受押总台账(Z1002000000)/Reponse_10081320";
		doExport(dataPath,outFileName,out);
		
	}

	//受押分类台帐(中心端)/管理中受押分类台账（按业务类别)
	public static void dbpTest9() throws Exception{
		String outFileName;
		String dataPath;
		ExcelFormatOut_I out=new ExcelFormatOut09();//
		
		outFileName="受押分类台帐(中心端)/管理中受押分类台账（按业务类别A0076000013）";
		dataPath="受押分类台帐(中心端)/Reponse-管理中受押分类台账（按业务类别A0076000013）/Reponse_10081340";
		doExport(dataPath,outFileName,out);
		
		outFileName="受押分类台帐(中心端)/管理中受押分类台账（按业务类别Z0010000000）";
		dataPath="受押分类台帐(中心端)/Reponse-管理中受押分类台账（按业务类别Z0010000000）/Reponse_10081340";
		doExport(dataPath,outFileName,out);
		
		outFileName="受押分类台帐(中心端)/管理中受押分类台账（按业务类别Z1002000000）";
		dataPath="受押分类台帐(中心端)/Reponse-管理中受押分类台账（按业务类别Z1002000000）/Reponse_10081340";
		doExport(dataPath,outFileName,out);
		
		
		
	}
	//受押分类台帐(中心端)/管理中受押分类台账（按业务名称)
	public static void dbpTest10() throws Exception{
		String outFileName;
		String dataPath;
		ExcelFormatOut_I out=new ExcelFormatOut10();//
		
		outFileName="受押分类台帐(中心端)/管理中受押分类台账（按业务名称A0076000013）";
		dataPath="受押分类台帐(中心端)/Reponse-管理中受押分类台账（按业务名称A0076000013）/Reponse_10081330";
		doExport(dataPath,outFileName,out);
		
		outFileName="受押分类台帐(中心端)/管理中受押分类台账（按业务名称Z0010000000）";
		dataPath="受押分类台帐(中心端)/Reponse-管理中受押分类台账（按业务名称Z0010000000）/Reponse_10081330";
		doExport(dataPath,outFileName,out);
		
		outFileName="受押分类台帐(中心端)/管理中受押分类台账（按业务名称Z1002000000）";
		dataPath="受押分类台帐(中心端)/Reponse-管理中受押分类台账（按业务名称Z1002000000）/Reponse_10081330";
		doExport(dataPath,outFileName,out);
		
		
		
	}
	
	//受押分类台帐(中心端)/已完成受押分类台账（按业务类别)
	public static void dbpTest11() throws Exception{
		String outFileName;
		String dataPath;
		ExcelFormatOut_I out=new ExcelFormatOut11();//
		
		outFileName="受押分类台帐(中心端)/已完成受押分类台账（按业务类别A0076000013）";
		dataPath="受押分类台帐(中心端)/Reponse-已完成受押分类台账（按业务类别A0076000013）/Reponse_10081360";
		doExport(dataPath,outFileName,out);
		
		outFileName="受押分类台帐(中心端)/已完成受押分类台账（按业务类别Z0010000000）";
		dataPath="受押分类台帐(中心端)/Reponse-已完成受押分类台账（按业务类别Z0010000000）/Reponse_10081360";
		doExport(dataPath,outFileName,out);
		
		outFileName="受押分类台帐(中心端)/已完成受押分类台账（按业务类别Z1002000000）";
		dataPath="受押分类台帐(中心端)/Reponse-已完成受押分类台账（按业务类别Z1002000000）/Reponse_10081360";
		doExport(dataPath,outFileName,out);
		
		
	}
	
	//受押分类台帐(中心端)/已完成受押分类台账（按业务名称）
	public static void dbpTest12() throws Exception{
		String outFileName;
		String dataPath;
		ExcelFormatOut_I out=new ExcelFormatOut12();//
		
		outFileName="受押分类台帐(中心端)/已完成受押分类台账（按业务名称A0076000013）";
		dataPath="受押分类台帐(中心端)/Reponse-已完成受押分类台账（按业务名称A0076000013）/Reponse_10081350";
		doExport(dataPath,outFileName,out);
		
		outFileName="受押分类台帐(中心端)/已完成受押分类台账（按业务名称Z0010000000）";
		dataPath="受押分类台帐(中心端)/Reponse-已完成受押分类台账（按业务名称Z0010000000）/Reponse_10081350";
		doExport(dataPath,outFileName,out);
		
		outFileName="受押分类台帐(中心端)/已完成受押分类台账（按业务名称Z1002000000）";
		dataPath="受押分类台帐(中心端)/Reponse-已完成受押分类台账（按业务名称Z1002000000）/Reponse_10081350";
		doExport(dataPath,outFileName,out);
		
	}
	public static void dbpTest13() throws Exception{
		String outFileName;
		ExcelFormatOut_I out=new ExcelFormatOut13();//

		
		List demoList=new ArrayList();
		demoList=ExcelOperDAO.findList(IpnMng.class.getName());//从excel中读取数据
    	
		IpnMng aRcd=(IpnMng)demoList.get(0);
		
		objFct.sendObj(aRcd, "10080420Print1");
		objFct.sendObj(demoList, "10080420Print2");
		
		outFileName="质押结果查询/质押结果查询列表(A0076000013).xls";
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
		System.out.println("loadPath="+loadPath+"\n outFileName="+outFileName+" 处理完成！");
		System.out.println("");
	}


	
		
		
	 private void saveResponse(){

			
			
			//将ESB返回结果序列化保存.测试使用
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
