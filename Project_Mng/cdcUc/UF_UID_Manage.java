package cdcUc;

import pub.UcvWindow;

public class UF_UID_Manage {
	public static void main(String[] args) {
		try {
			//Æô¶¯²ÎÊý
//			UBExcObj initData = new UBExcObj();
//			initData.putData("@InitParam", new AFKeyVal("aa=true,bb=false"));
			
			UcvWindow window = new UcvWindow("cdc#UID10020500","GL",null);
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}