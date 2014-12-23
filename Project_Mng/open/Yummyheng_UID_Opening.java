package open;

import AF.pub.AFexcDataArea;
import pub.UcvWindow;

public class Yummyheng_UID_Opening {
	
	public static void main(String[] args) {
			
		String uid = "10020100"; 
		
		AFexcDataArea xda = AFexcDataArea.getExcFct();	//static的变量
		xda.putData("#AppCode","cdc");	//运行数据库提前配置的文件 运行包UC文件夹下的init文件	
										//向交换区放入对象
		UcvWindow window = new UcvWindow("cdc#UID" + uid, "yummy_heng", null);	//final的display
		
		window.open();   
			
	}
}
