package pub;

import AF.pub.AFKeyVal;
import AF.pub.UBExcObj;

public class TestWin {
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
