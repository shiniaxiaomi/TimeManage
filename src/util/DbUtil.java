package util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 数据库工具类
 * @author lyj80
 *
 */
public class DbUtil {

	// 数据库地址
	private static String dbUrl="jdbc:mysql://localhost:3306/timemanager";
	// 用户名
	private static String dbUserName="root";
	// 密码
	private static String dbPassword="123456";
	// 驱动名称
	private static String jdbcName="com.mysql.jdbc.Driver";
	
	/**
	 * 获取数据库连接
	 * @return
	 * @throws Exception
	 */
	public Connection getCon()throws Exception{
		Class.forName(jdbcName);//加载驱动
		Connection con=DriverManager.getConnection(dbUrl, dbUserName, dbPassword);//驱动管理器
		
		return con;
	}
	
	/**
	 * 关闭数据库连接
	 * @param con
	 * @throws Exception
	 */
	public void closeCon(Connection con)throws Exception{
		if(con!=null) {
			con.close();
		}
	}
	
	public static void main(String[] args) {
		DbUtil dbUtil=new DbUtil();
		try {
			dbUtil.getCon();
			System.out.println("数据库连接成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("数据库连接失败");
		}
	}
	
	
}
