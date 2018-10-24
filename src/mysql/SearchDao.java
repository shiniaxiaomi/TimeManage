package mysql;

import gui.MyPanel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Task;
import util.DbUtil;

public class SearchDao {

//	public ArrayList<Task> listAllTask(DbUtil dbUtil,String information){//罗列查找的对象
//		ResultSet rs=null;
//		MyPanel myPanel=null;
//		ArrayList<Task> values=new ArrayList<Task>();
//		Task task=null;
//		
//		String sql="select * from table_manager";
//		
//		Connection con=null;
//		try {
//			con=dbUtil.getCon();//获取数据库连接
//			
//			PreparedStatement pstmt=con.prepareStatement(sql);//对sql语句进行预编译
//			rs=pstmt.executeQuery();//返回一个结果集	
//			
//			while(rs.next()){//遍历	
//				String s="select * from "+rs.getString("tableName")+" where title like "+"'%"+information+"%' "+"or detail like "+"'%"+information+"%';";
//				pstmt=con.prepareStatement(s);//对sql语句进行预编译
//				ResultSet r=pstmt.executeQuery();//返回一个结果集
//				while(r.next()){
//					task=new Task(r.getInt("id"),r.getString("title"),r.getString("detail") , r.getInt("todayTime"),  r.getInt("allTime"));
//					values.add(task);
//				}
//				
//			}
//					
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			try {
//				dbUtil.closeCon(con);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		
//		
//		return values;
//		
//	}
	
	public ArrayList<Task> listAllTask(DbUtil dbUtil,String information){//罗列查找的对象
		ResultSet rs=null;
		MyPanel myPanel=null;
		ArrayList<Task> values=new ArrayList<Task>();
		Task task=null;
		
		String s="select * from task_manager where title like "+"'%"+information+"%' "+"or detail like "+"'%"+information+"%';";
		
		Connection con=null;
		try {
			
			con=dbUtil.getCon();//获取数据库连接
			
			PreparedStatement pstmt=con.prepareStatement(s);//对sql语句进行预编译
			ResultSet r=pstmt.executeQuery();//返回一个结果集
			while(r.next()){
				task=new Task(r.getInt("id"),r.getInt("typeId"),r.getString("title"),r.getString("detail") , r.getInt("todayTime"),  r.getInt("allTime"),r.getInt("isToday"));
				values.add(task);
			}				
								
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return values;
		
	}

	
	
	
}
