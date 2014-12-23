package open;


import pub.UcvWindow;

public class Tree_UID_Opening {

	
	public static void main(String[] args) {
			String uid = "80010270"; // 当期追加类别管理
			
			UcvWindow window = new UcvWindow("cdc#UID" + uid, "yummy_heng", null);
			
			window.open();  
	}
}


