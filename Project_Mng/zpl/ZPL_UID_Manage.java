package zpl;

import AF.pub.AFexcDataArea;
import pub.UcvWindow;

public class ZPL_UID_Manage {
	public static void main(String[] args) {
		try {
			//启动参数
//			UBExcObj initData = new UBExcObj();
//			initData.putData("@InitParam", new AFKeyVal("aa=true,bb=false"));
			
			
			AFexcDataArea xda = AFexcDataArea.getExcFct();
			xda.putData("#AppCode","zpl");//设置该值的目的主要是为了获取特定的数据库连接配置
			

//			UcvWindow window = new UcvWindow("cdc#UIDzplZqpl01001","GL",null);//债券披露信息主页面
//			UcvWindow window = new UcvWindow("cdc#UIDzplZqpl01002","GL",null);//债券披露信息编辑页面
//			UcvWindow window = new UcvWindow("cdc#UIDzplZqpl01003","GL",null);//债券披露被驳回记录查看页面
			UcvWindow window = new UcvWindow("cdc#UIDzplSjsh01001","RK",null);//我的任务页面
//			UcvWindow window = new UcvWindow("cdc#UIDzplCwsj01002","GL",null);//财务数据查询
//			UcvWindow window = new UcvWindow("cdc#UIDzplYwtz01001","GL",null);//中心端通知
//			UcvWindow window = new UcvWindow("cdc#UIDzplYwtz01003","GL",null);//客户端通知
//			UcvWindow window = new UcvWindow("cdc#UIDzplZqpl01003","GL",null);//债券披露驳回数据查看
//			UcvWindow window = new UcvWindow("cdc#UIDzplSjsh01001","GL",null);//债券披露我的任务页面
//			UcvWindow window = new UcvWindow("cdc#UIDzplSjsh01003","RK",null);//债券披露我参与过的任务页面
//			UcvWindow window = new UcvWindow("cdc#UIDzplSjsh01005","GL",null);//债券披露工作流实例管理
//			
//			UcvWindow window = new UcvWindow("cdc#UIDzplCsdj01004","GL",null);//初始登记材料驳回数据查看
//	 		UcvWindow window = new UcvWindow("cdc#UIDzplCsdj01001","GL",null);//初始登记	
//			UcvWindow window = new UcvWindow("cdc#UIDzplCsdj01003","RK",null);//初始登记任务列表
//			UcvWindow window = new UcvWindow("cdc#UIDzplYwtz01001","GL",null);//业务通知查询
//			UcvWindow window = new UcvWindow("cdc#UIDzplYwtz01001","GL",null);//中心端通知	 
//			UcvWindow window = new UcvWindow("cdc#UIDzplYwtz01003","RK",null);//客户端通知

// 		   UcvWindow window = new UcvWindow("cdc#UIDzplgzlys01001","GL",null);//工作流映射
// 		   UcvWindow window = new UcvWindow("cdc#UIDzplFxzc01001","GL",null);//发行人
// 		   UcvWindow window = new UcvWindow("cdc#UIDzplCommon01004","GL",null);//上网编码
			
//		   UcvWindow window = new UcvWindow("cdc#UIDzplBPM01001","RK",null);//工作流模型管理(中心端)
			
//			UcvWindow window = new UcvWindow("cdc#UIDzplRight01001","GL",null);//债券种类管理
//			UcvWindow window = new UcvWindow("cdc#UIDzplRight01003","GL",null);//债券种类分组管理
			
//			UcvWindow window = new UcvWindow("cdc#UIDzplRight01005","GL",null);//登记材料管理
//			UcvWindow window = new UcvWindow("cdc#UIDzplRight01008","GL",null);//登记材料分组管理

//			UcvWindow window = new UcvWindow("cdc#UIDzplRight01009","GL",null);//客户信息查询
//			UcvWindow window = new UcvWindow("cdc#UIDzplRight01010","GL",null);//客户资格管理
			
//			UcvWindow window = new UcvWindow("cdc#UIDzplCommon01003","GL",null);//公共页面发行人查询
//			UcvWindow window = new UcvWindow("cdc#UIDzplFxzc01001","GL",null);//发行人维护查询
//			UcvWindow window = new UcvWindow("cdc#UIDzplCwsj01001","GL",null);//财务数据上传查询
//			UcvWindow window = new UcvWindow("cdc#UIDzplCwsj01002","GL",null);//财务数据查询
//			UcvWindow window = new UcvWindow("cdc#UIDzplCwsj01006","GL",null);//财务数据查询(中心端)
			
//			UcvWindow window = new UcvWindow("cdc#UIDzplBPM01001","GL",null);//工作流模型管理(中心端)
			
//			UcvWindow window = new UcvWindow("cdc#UIDzplCommon01008","GL",null);// 配置管理(文件尺寸设定等)
//			UcvWindow window = new UcvWindow("cdc#UIDzplCwsj01001","GL",null);//财务数据上传
//			UcvWindow window = new UcvWindow("cdc#UIDzplCwsj01002","GL",null);//财务数据查询
//			UcvWindow window = new UcvWindow("cdc#UIDzplCwsj01005","GL",null);//财务数据模版上传
			
//		   UcvWindow window = new UcvWindow("cdc#UIDzplCwsj01006","GL",null);//财务数据查询（中心端）			
//		   UcvWindow window = new UcvWindow("cdc#UIDzplCwsj01004","GL",null);//财务数据统计（中心端）			
		   
			
 		   window.open();   
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}