package cdcUc;

import pub.UcvWindow;


public class CMS_UID_Manage {
	public static void main(String[] args) {
		try {
			//启动参数
//			UBExcObj initData = new UBExcObj();
//			initData.putData("@InitParam", new AFKeyVal("aa=true,bb=false"));
			
			/**
			 * 公共管理
			 *
			 */
			//全部来报查询
//			UcvWindow window = new UcvWindow("cdc#UID10070061","GL",null);
			/**
			 * 基础参数管理
			 *
			 */
			//城市信息查询
//			UcvWindow window = new UcvWindow("cdc#UID10070014","GL",null);
			//节点信息查询
//			UcvWindow window = new UcvWindow("cdc#UID10070015","GL",null);
			/**
			 * 系统参数管理
			 *
			 */
			/**
			 * 公共控制管理
			 *
			 */
			
			UcvWindow window = new UcvWindow("cdc#UIDtrainBondTypeMng01010","GL",null);
			//业务查询(业务界面)
//			UcvWindow window = new UcvWindow("cdc#UID10071023","GL",null);
			//业务查复应答(业务界面)
//			UcvWindow window = new UcvWindow("cdc#UID10071024","GL",null);
			//自由格式 - 查询
//			UcvWindow window = new UcvWindow("cdc#UID10070021","GL",null);
			//业务退回申请(业务界面)
			
//			UcvWindow window = new UcvWindow("cdc#UID10071030","GL",null);// TODO 目前测试中
//			UcvWindow window = new UcvWindow("cdc#UID10071032","GL",null);
//			UcvWindow window = new UcvWindow("cdc#UID10070034","GL",null);
//			UcvWindow window = new UcvWindow("cdc#UID10070036","GL",null);
//			UcvWindow window = new UcvWindow("cdc#UID10070038","GL",null);
//			UcvWindow window = new UcvWindow("cdc#UID10070039","GL",null);
			
			
			
			/**
			 * 大额支付管理
			 *
			 */
			// 汇兑业务主界面(业务界面)
//			UcvWindow window = new UcvWindow("cdc#UID10071040","GL",null);
			//即时转账(业务界面)
//			UcvWindow window = new UcvWindow("cdc#UID10071043","GL",null);
			
			/**
			 * 清算账户管理
			 *
			 */
			//质押融资管理查询 OK
//			UcvWindow window = new UcvWindow("cdc#UID10070074","GL",null);
			//质押融资管理新增 OK
//			UcvWindow window = new UcvWindow("cdc#UID10070124","GL",null);
			//人工质押融资申请查询 OK
//			UcvWindow window = new UcvWindow("cdc#UID10070075","GL",null);
//			UcvWindow window = new UcvWindow("cdc#UID10070072","GL",null);
//			UcvWindow window = new UcvWindow("cdc#UID10070123","GL",null);
			//质押融资债券调整通知查询 无数据
//			UcvWindow window = new UcvWindow("cdc#UID10070085","GL",null);
//			UcvWindow window = new UcvWindow("cdc#UID10070020","GL",null);
//			UcvWindow window = new UcvWindow("cdc#UID10070106","GL",null);
			
			//cms
//			UcvWindow window = new UcvWindow("cdc#UID10080640","GL",null);
//			UcvWindow window = new UcvWindow("cdc#UID10080711","GL",null);
//			UcvWindow window = new UcvWindow("cdc#UID10080821","GL",null);
//			UcvWindow window = new UcvWindow("cdc#UID10080620","GL",null);
//			UcvWindow window = new UcvWindow("cdc#UID10080680","GL",null);
//			UcvWindow window = new UcvWindow("cdc#UID10080836","GL",null);
//			UcvWindow window = new UcvWindow("cdc#UID10080752","GL",null);
//			UcvWindow window = new UcvWindow("cdc#UID10080711","GL",null);
//			UcvWindow window = new UcvWindow("cdc#UID10071040","GL",null);
			
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		int aaa= 875000;
//		double bbb = aaa/10000.0;
//		System.out.println(bbb);
//		BigDecimal ccc= new BigDecimal("875000");
//		BigDecimal ccc1= new BigDecimal("10000");
//		BigDecimal dddd = ccc.divide(ccc1);
//		System.out.println(dddd);
		
	}
}