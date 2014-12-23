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
			//创建ESBClient对象，参数为channelID，服务地址。
			ESBClient client = new ESBClient("1","http://10.41.240.6:9083/cwrkWS/services/cwrkWebServiceForESB");
			// 创建ESBRequestImpl对象
			ESBRequest esbReq = new ESBRequestImpl();
			//设置ServiceID
			
			/*查询发行人信息*/
//			esbReq.setServiceId("9000000001");
			
			/*查询会计年度*/
			//esbReq.setServiceId("9000000002");
			
			/*统计发行人披露财务报表的明细：入口参数报表类型和统计日期 报表类型:jb(季报),nb(年报)*/
			esbReq.setServiceId("9000000003");
			esbReq.setParameter("statDate", "2011-04-30");//20110430
			esbReq.setParameter("bblx", "nb");
			esbReq.setParameter("tjrq", "2010年");
			
			/*统计发行人财务报表发布数量*/
//			esbReq.setServiceId("9000000004");
//			esbReq.setParameter("statDate", "2010-10-10");
//			esbReq.setParameter("bblx", "jb");
//			esbReq.setParameter("tjrq", "2010");
//			
			/*查询发行人信息：入口参数报表编号*/
			//esbReq.setServiceId("9000000005");
			//esbReq.setParameter("bbbh", "22");

			/*查询现金流量表：入口参数报表编号*/
			//esbReq.setServiceId("9000000006");
			//esbReq.setParameter("bbbh", "22");
			
			/*查询利润表：入口参数报表编号*/
			//esbReq.setServiceId("9000000007");
			//esbReq.setParameter("bbbh", "22");

			/*查询资产负载表：入口参数报表编号*/
			//esbReq.setServiceId("9000000008");
			//esbReq.setParameter("bbbh", "22");
			
			/*查询发行人资料表：入口参数报表编号*/
			//esbReq.setServiceId("9000000009");
			//esbReq.setParameter("bbbh", "22");
			
			/*查询同主承销商、同发行人、同财务报表的上传文件：入口参数单位名称、发行人编号、报表*/
//			esbReq.setServiceId("9000000010");
//			esbReq.setParameter("dwmc", "计财部，董办");
//			esbReq.setParameter("fxrbh", "0000000942");
//			esbReq.setParameter("bblx", "2009年");
			
			/*查询财务报表*/
//			esbReq.setServiceId("9000000011");
//			esbReq.setParameter("stime", "20010101");
//			esbReq.setParameter("etime", "20111231");
//			esbReq.setParameter("kjnd", "2011年第1季");
			/*查询财务报表的内容*/
//			esbReq.setServiceId("9000000015");
//			esbReq.setParameter("bbbh", "999999");
			
			
			
			/*保存财务数据*/
//			esbReq.setServiceId("9000000014");
//			esbReq.setParameter("rybh","123456");
//			esbReq.setParameter("mc","钱炳权");
//			esbReq.setParameter("dwmc","新锐");
//			esbReq.setParameter("bblx","2011年");
//			esbReq.setParameter("fxrbh","0000012102");
//			esbReq.setParameter("bbbb","1.1");
//			esbReq.setParameter("fzbsjdw","人民币元");
//			esbReq.setParameter("lrbsjdw","人民币元");
//			esbReq.setParameter("llbsjdw","人民币元");
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
//			String fileStr=Util.readFile2CpStr(filePath);//读取文件,转换为压缩字符串形式
//			esbReq.setParameter("file",fileStr);

			
			//测试上传报表
//			esbReq.setServiceId("9000000016");
//			esbReq.setParameter("bbbh","999999");
//			String filePath="C:\\test.xlsx";			
//			String fileStr=Util.readFile2CpStr(filePath);//读取文件,转换为压缩字符串形式
//			esbReq.setParameter("xlsfileStr",fileStr);
			
			//测试删除财务报表
//			esbReq.setServiceId("9000000013");
//			esbReq.setParameter("rybh","123456"); //人员编号
//			esbReq.setParameter("mc","钱炳权");  //上传人姓名
//			esbReq.setParameter("dwmc","新锐"); //上传人单位
//			esbReq.setParameter("bh","1006");  //报表编号
			
			
			/*根据报表编号查询财务报表所有数据*/
//			esbReq.setServiceId("9000000017");
//			esbReq.setParameter("bbbh","526");
			
			//参数包括：serviceId,ESBRequest对象，超时时间，是否进行监控
			ESBResponse esbRsp = client.call(esbReq,true);
			// 输出报文的主体内容
			System.out.println("errCode: " + esbRsp.getErrCode());
			System.out.println("errText: " + esbRsp.getErrText());
			// 获得报文的数据
			HashMap map = (HashMap) esbRsp.getParameterMap();
			
			//查看返回数据是否为分页数据。页码的使用请参考《中央国债ESB平台-分页机制规范说明书》
			//System.out.println("返回数据对象的页号："+esbRsp.getPagination());

			/*查询发行人信息*/
//			ESBDataSet dataset = esbRsp.getDataSet("queryFXR_DataSet");
			
			/*查询会计年度*/
			//ESBDataSet dataset = esbRsp.getDataSet("queryKjndInfo_DataSet");
			
			/*统计发行人披露财务报表的明细*/
			ESBDataSet dataset = esbRsp.getDataSet("statFxrDetail_DataSet");
			
			/*统计发行人财务报表发布数量*/
			//ESBDataSet dataset = esbRsp.getDataSet("statFxr_DataSet");
			
			/*查询发行人信息*/
			//ESBDataSet dataset = esbRsp.getDataSet("queryFxrInfo_DataSet");
			
			/*查询现金流量表*/
			//ESBDataSet dataset = esbRsp.getDataSet("queryXjllb_DataSet");
			
			/*查询利润表*/
			//ESBDataSet dataset = esbRsp.getDataSet("queryLrb_DataSet");
			
			/*查询资产负载表*/
			//ESBDataSet dataset = esbRsp.getDataSet("queryZcfzb_DataSet");
			
			/*查询发行人资料表*/
			//ESBDataSet dataset = esbRsp.getDataSet("queryFxrzlb_DataSet");
			
			/*查询同主承销商、同发行人、同财务报表的上传文件*/
			//ESBDataSet dataset = esbRsp.getDataSet("queryCwbb_DataSet");
			
			/*查询财务报表*/
//			ESBDataSet dataset = esbRsp.getDataSet("queryCWBB_DataSet");
			
			/*查询财务报表的内容*/
//			ESBDataSet dataset=null;
//			String writePathCom="c:\\writeBackCom.xlsx";
//			String xlsFileStr=(String)esbRsp.getParameter("file");
//			Util.writeCpStr2File(writePathCom, xlsFileStr);//将压缩的字符串转换后写入文件
			
			/*保存财务数据*/
//			ESBDataSet dataset=null;
//			String result=(String)esbRsp.getParameter("result");
			
			/*测试上传文件*/
//			ESBDataSet dataset=null;
//			String result=(String)esbRsp.getParameter("result");
			
			/*测试删除财务报表*/
//			ESBDataSet dataset=null;
//			String result=(String)esbRsp.getParameter("delCWBB_result");
			
			/*根据报表编号查询财务报表所有数据*/
//			ESBDataSet dataset = esbRsp.getDataSet("queryCwbbByBbbh_DataSet");
			//判断DataSet对象是否为空
			if (dataset == null) {
				System.out.println("后台返回的DataSet对象为空！");
			} else {
				//获取数据的列名				
				String[] columns = dataset.getColumn();
				//输出列名
				 for (String column : columns) {
					 System.out.print(column+" ,");
				 }
				 System.out.println();
				 //循环输出DataSet对象中的数据.
				while (dataset.next()) {
					for (String column : columns) {
						//输出 DataSet对象中的数据.
						System.out.print(dataset.getString(column) + ",");
					}
					System.out.println();
				}
			}
			//循环输出ParameterMap中的参数.
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
			System.out.println("异常信息：" + e.getMessage());
		}
	}
}