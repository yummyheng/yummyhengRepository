package fxs;

import pub.UcvWindow;

public class FXS_JZ_TEST{
	
	public static void main(String[] args) {
		try {
			//��������
			UcvWindow window = new UcvWindow("cdc#UIDjkg01001","GL",null);//(��)Ͷ���޶�������
			
			window.open();   
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
