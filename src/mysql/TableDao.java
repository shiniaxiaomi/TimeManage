package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;

import util.DbUtil;

public class TableDao {

	public static int getTypeId(Connection con,String tableName) throws SQLException{//获取种类的编号
		
		ResultSet rs=null;
		int typeId=0;
		String sql="select typeId from table_manager where tableName=?";
		
		PreparedStatement pstmt=con.prepareStatement(sql);//对sql语句进行预编译
		pstmt.setString(1,tableName);
		
		rs=pstmt.executeQuery();//返回一个结果集
		
		while(rs.next()){//遍历
			typeId=rs.getInt("typeId");
		}
		
		return typeId;
		
	}
	
	public static String getTableName(DbUtil dbUtil,int typeId){//获取对应的任务的任务类别的名称
		
		ResultSet rs=null;
		String sql="select tableName from table_manager where typeId=?";
		String tableName = null;
		
		Connection con=null;
		PreparedStatement pstmt;
		try {
			con=dbUtil.getCon();

			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1,typeId);
			
			rs=pstmt.executeQuery();//返回一个结果集
			
			while(rs.next()){//遍历
				tableName=rs.getString("tableName");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//获取数据库连接
		
		return tableName;
		
	}
	
	public static int getTypeId(DbUtil dbUtil,String tableName){//获取对应的任务的任务类别的名称
		
		ResultSet rs=null;
		String sql="select typeId from table_manager where tableName=?";
		int typeId=0;
		
		Connection con=null;
		PreparedStatement pstmt;
		try {
			con=dbUtil.getCon();

			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1,tableName);
			
			rs=pstmt.executeQuery();//返回一个结果集
			
			while(rs.next()){//遍历
				typeId=rs.getInt("typeId");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//获取数据库连接
		
		return typeId;
		
	}
	
	
	public void updateTableName(DbUtil dbUtil,String tableName,String nowName){
//		String sql="alter table "+tableName+" rename "+nowName+" ;";	
		
		String sql2="update table_manager set tableName=? where tableName=?;";
		
		Connection con=null;
		PreparedStatement pstmt;
		try {
			con=dbUtil.getCon();//获取数据库连接
			
//			pstmt=con.prepareStatement(sql);//对sql语句进行预编译
//			pstmt.executeUpdate();//执行sql语句
			
			
			pstmt=con.prepareStatement(sql2);//对sql语句进行预编译
			pstmt.setString(1, nowName);
			pstmt.setString(2, tableName);
			pstmt.executeUpdate();//执行sql语句

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	public int addTable(DbUtil dbUtil,String name){
		int n=0;
		String sql_1="insert into table_manager value(null,?)";
		
//		String sql_2="CREATE TABLE "+name+"(id INT PRIMARY KEY AUTO_INCREMENT,title VARCHAR(40),detail TEXT,todayTime INT,allTime INT)";
			
		
		Connection con=null;
		try {
			con=dbUtil.getCon();//获取数据库连接
			
			PreparedStatement pstmt=con.prepareStatement(sql_1);//对sql语句进行预编译
			pstmt.setString(1,name);
			n= pstmt.executeUpdate();//执行sql语句
			
//			pstmt=con.prepareStatement(sql_2);//对sql语句进行预编译
//			System.out.println(sql_2);
//			n= pstmt.executeUpdate();//执行sql语句
					
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return n;
		
	}
	
	public boolean isExited(DbUtil dbUtil,String tableName){
		
		ResultSet rs=null;
		String sql="select * from table_manager where tableName=?";
		int num=0;
		
		Connection con=null;
		PreparedStatement pstmt;
		try {
			con=dbUtil.getCon();

			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1,tableName);
			
			rs=pstmt.executeQuery();//返回一个结果集
			
			while(rs.next()){//遍历
				num++;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//获取数据库连接
		

		if(num==1){
			return true;
		}else if(num==0){
			return false;
		}else {
			System.out.println("存在相同的任务别类!");
			return true;
		}
		
		
		
	}
	
	
	public int deleteTable(DbUtil dbUtil,String tableName){
		int n=0;
		String sql="delete from task_manager where typeId=?";
		String sql_1="delete from table_manager where typeId=?";
		
		Connection con=null;
		try {
			con=dbUtil.getCon();//获取数据库连接
			
			int typeId=getTypeId(con, tableName);
			
			PreparedStatement pstmt=con.prepareStatement(sql);//对sql语句进行预编译
			pstmt.setInt(1,typeId);
			n= pstmt.executeUpdate();//执行sql语句
			
			pstmt=con.prepareStatement(sql_1);//对sql语句进行预编译
			pstmt.setInt(1,typeId);
			n= pstmt.executeUpdate();//执行sql语句
			
//			pstmt=con.prepareStatement(sql_2);//对sql语句进行预编译
//			n= pstmt.executeUpdate();//执行sql语句
					
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return n;
		
	}
	
	public DefaultListModel listTable(DbUtil dbUtil){
		ResultSet rs=null;
		DefaultListModel dlm=new DefaultListModel();
		String sql="select * from table_manager";
		
		Connection con=null;
		try {
			con=dbUtil.getCon();//获取数据库连接
			
			PreparedStatement pstmt=con.prepareStatement(sql);//对sql语句进行预编译

			rs=pstmt.executeQuery();//返回一个结果集
	
	
			while(rs.next()){//遍历
				dlm.addElement(rs.getString("tableName"));
			}
	
					
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return dlm;
		
	}
	
	
	public String[] listMenu(DbUtil dbUtil){
		ResultSet rs=null;
		String values[]=null;
		String sql="select * from table_manager";
		
		Connection con=null;
		try {
			con=dbUtil.getCon();//获取数据库连接
			
			PreparedStatement pstmt=con.prepareStatement(sql);//对sql语句进行预编译

			rs=pstmt.executeQuery();//返回一个结果集
	
			rs.last();
			values=new String[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next()){//遍历				
				values[i]=rs.getString("tableName");
				i++;
			}
			
					
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return values;
		
	}
	

}
