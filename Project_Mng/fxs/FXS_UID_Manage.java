package fxs;

import pub.UcvWindow;
import AF.pub.AFexcDataArea;

public class FXS_UID_Manage{
	
	public static void main(String[] args) {
		try {
			//启动参数
//			UBExcObj initData = new UBExcObj();
//			initData.putData("@InitParam", new AFKeyVal("aa=true,bb=false"));
			
			
			AFexcDataArea xda = AFexcDataArea.getExcFct();
			xda.putData("#AppCode","cdc");//设置该值的目的主要是为了获取特定的数据库连接配置			
			
			String uid = "cxc01001"; // 当期追加类别管理
//			String uid = "cxc01007"; // 最低承销额类别管理
//			String uid = "cxc01009"; // 投标限额类别管理
//			String uid = "cxt01001"; // 承销团管理
			
			UcvWindow window = new UcvWindow("cdc#UID" + uid, "LB", null);
			
			window.open();   
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
