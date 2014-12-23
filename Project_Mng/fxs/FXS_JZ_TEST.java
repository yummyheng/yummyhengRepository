package fxs;

import pub.UcvWindow;

public class FXS_JZ_TEST{
	
	public static void main(String[] args) {
		try {
			//启动参数
			UcvWindow window = new UcvWindow("cdc#UIDjkg01001","GL",null);//(发)投标限额类别管理
			
			window.open();   
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
