package zpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import oracle.jdbc.OracleTypes;

public class JdbcTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			String conStr="jdbc:oracle:thin:@//12.12.1.180:1521/TJDB.chinabond.com.cn";
//			String conStr="jdbc:oracle:thin:@//10.14.192.51:1521/ads.chinabond.com.cn";
			
			String userName="zzplcms";
			String password="zzplcms";
			
			String sql="Select * from EXTEND_DICTIONARY_TYPE where TYPE_NAME=?";
			
			
			Connection conn = DriverManager.getConnection(conStr, userName, password);
//			Statement sts = conn.createStatement();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setObject(1, "¹úÕ®");
			ResultSet rs = pstmt.executeQuery();
			
			
			
			
//			ResultSet rs = sts.executeQuery(sql);
//			CallableStatement stat = conn.prepareCall("{CALL DATAGATE.SP_TJYB_SJXLCX(201001,201005,002)}");
//			stat.registerOutParameter(1, OracleTypes.CURSOR);
//			stat.execute();
//			ResultSet rs = (ResultSet) stat.getObject(1);
			rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

