package mysql;

import gui.MainFrm;
import gui.MyPanel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Task;
import util.DbUtil;

public class TaskDao {
	
	public MainFrm mainFrm=null;
	
//	public Task readTask(DbUtil dbUtil,String tableName,int id){
//		ResultSet rs=null;
//		Task task=null;
//		String sql="select * from "+tableName+" where id=?";
//		Connection con=null;
//		try {
//			con=dbUtil.getCon();//获取数据库连接
//			
//			PreparedStatement pstmt=con.prepareStatement(sql);//对sql语句进行预编译
//			pstmt.setInt(1, id);
//			
//			rs=pstmt.executeQuery();//返回一个结果集	
//			while(rs.next()){//遍历				
//				task=new Task(rs.getInt("id"),rs.getString("title"),rs.getString("detail") , rs.getInt("todayTime"),  rs.getInt("allTime"));
//			}
//			
//					
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			try {
//				dbUtil.closeCon(con);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		return task;
//		
//	}
	
	public Task readTask(DbUtil dbUtil,String tableName,String title){
		ResultSet rs=null;
		Task task=null;
//		String sql="select * from table_manager where tableName=?";
		String sql2="select * from task_manager where typeId=? and title=?";
		Connection con=null;
		PreparedStatement pstmt;
		try {
			con=dbUtil.getCon();//获取数据库连接

//			pstmt=con.prepareStatement(sql);//对sql语句进行预编译
//			pstmt.setString(1, tableName);
//			rs=pstmt.executeQuery();//返回一个结果集	
//			while(rs.next()){//遍历				
//				typeId=rs.getInt("typeId");
//			}
			
			int typeId=TableDao.getTypeId(con, tableName);
			
			
			pstmt=con.prepareStatement(sql2);//对sql语句进行预编译
			pstmt.setInt(1, typeId);
			pstmt.setString(2, title);
			
			rs=pstmt.executeQuery();//返回一个结果集	
			while(rs.next()){//遍历				
//				task=new Task(rs.getInt("id"),rs.getString("title"),rs.getString("detail") , rs.getInt("todayTime"),  rs.getInt("allTime"));
				task=new Task(rs.getInt("id"),rs.getInt("typeId"),rs.getString("title"),rs.getString("detail") , rs.getInt("todayTime"),  rs.getInt("allTime"),rs.getInt("isToday"));
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
		return task;
		
	}
	
	public Task readTask(DbUtil dbUtil,int typeId,String title){
		ResultSet rs=null;
		Task task=null;
//		String sql="select * from table_manager where tableName=?";
		String sql2="select * from task_manager where typeId=? and title=?";
		Connection con=null;
		PreparedStatement pstmt;
		try {
			con=dbUtil.getCon();//获取数据库连接

			pstmt=con.prepareStatement(sql2);//对sql语句进行预编译
			pstmt.setInt(1, typeId);
			pstmt.setString(2, title);
			
			rs=pstmt.executeQuery();//返回一个结果集	
			while(rs.next()){//遍历				
//				task=new Task(rs.getInt("id"),rs.getString("title"),rs.getString("detail") , rs.getInt("todayTime"),  rs.getInt("allTime"));
				task=new Task(rs.getInt("id"),rs.getInt("typeId"),rs.getString("title"),rs.getString("detail") , rs.getInt("todayTime"),  rs.getInt("allTime"),rs.getInt("isToday"));
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
		return task;
		
	}
	
	public void cleanToday(DbUtil dbUtil){
		
		String sql="update task_manager set isToday=0 where isToday=1";
		Connection con=null;
		try {
			con=dbUtil.getCon();//获取数据库连接
			
			PreparedStatement pstmt=con.prepareStatement(sql);//对sql语句进行预编译
			
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
	
	public void updateTask(DbUtil dbUtil,String tableName,String title,Task task){

		String sql="update task_manager set title=?,detail=?,todayTime=?,allTime=?,isToday=? where typeId=? and title=?";
		
		Connection con=null;
		try {
			con=dbUtil.getCon();//获取数据库连接
			
			int typeId=TableDao.getTypeId(con, tableName);
			
			PreparedStatement pstmt=con.prepareStatement(sql);//对sql语句进行预编译
			pstmt.setString(1, task.title);
			pstmt.setString(2, task.detail);
			pstmt.setInt(3, task.todayTime);
			pstmt.setInt(4, task.allTime);
			pstmt.setInt(5, task.isToday);
			pstmt.setInt(6, typeId);
			pstmt.setString(7, title);
			
			
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
	
	public void updateTask(DbUtil dbUtil,int typeId,String title,Task task){

		String sql="update task_manager set title=?,detail=?,todayTime=?,allTime=?,isToday=? where typeId=? and title=?";
		
		Connection con=null;
		try {
			con=dbUtil.getCon();//获取数据库连接
			
			PreparedStatement pstmt=con.prepareStatement(sql);//对sql语句进行预编译
			pstmt.setString(1, task.title);
			pstmt.setString(2, task.detail);
			pstmt.setInt(3, task.todayTime);
			pstmt.setInt(4, task.allTime);
			pstmt.setInt(5, task.isToday);
			pstmt.setInt(6, typeId);
			pstmt.setString(7, title);
			
			
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
	
	public void updateTask(DbUtil dbUtil,Task task){

		String sql="update task_manager set title=?,detail=?,todayTime=?,allTime=?,isToday=? where typeId=? and title=?";
		
		Connection con=null;
		try {
			con=dbUtil.getCon();//获取数据库连接
			
			PreparedStatement pstmt=con.prepareStatement(sql);//对sql语句进行预编译
			pstmt.setString(1, task.title);
			pstmt.setString(2, task.detail);
			pstmt.setInt(3, task.todayTime);
			pstmt.setInt(4, task.allTime);
			pstmt.setInt(5, task.isToday);
			pstmt.setInt(6, task.typeId);
			pstmt.setString(7, task.title);
			
			
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
	
	public void updateTask(DbUtil dbUtil,String tableName,String title,int todayTime,int allTime){

		String sql="update task_manager set todayTime=?,allTime=? where typeId=? and title=?";
		
		Connection con=null;
		try {
			con=dbUtil.getCon();//获取数据库连接
			
			int typeId=TableDao.getTypeId(con, tableName);
			
			PreparedStatement pstmt=con.prepareStatement(sql);//对sql语句进行预编译
			
			pstmt.setInt(1, todayTime);
			pstmt.setInt(2, allTime);
			pstmt.setInt(3, typeId);
			pstmt.setString(4, title);
			
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
	
	public void updateTask(DbUtil dbUtil,String tableName,String oldTitle,String newTitle){

		String sql="update task_manager set title=? where typeId=? and title=?";
		
		Connection con=null;
		try {
			con=dbUtil.getCon();//获取数据库连接
			
			int typeId=TableDao.getTypeId(con, tableName);
			
			PreparedStatement pstmt=con.prepareStatement(sql);//对sql语句进行预编译
			
			pstmt.setString(1, newTitle);
			pstmt.setInt(2, typeId);
			pstmt.setString(3, oldTitle);
			
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

	public int addTask(DbUtil dbUtil,String tableName,Task task){
		int n=0;
		String sql="insert into task_manager values(null,?,?,?,?,?,?)";
//		String sql="insert into "+tableName+" values(null,"+"'"+title+"',"+"'"+detail+"',"+todayTime+","+allTime+");";
		Connection con=null;
		try {
			con=dbUtil.getCon();//获取数据库连接
			
			int typeId=TableDao.getTypeId(con, tableName);
			
			PreparedStatement pstmt=con.prepareStatement(sql);//对sql语句进行预编译

			pstmt.setInt(1,typeId);
			pstmt.setString(2,task.title);
			pstmt.setString(3,task.detail);
			pstmt.setInt(4, task.todayTime);
			pstmt.setInt(5, task.allTime);
			pstmt.setInt(6, task.isToday);

			n= pstmt.executeUpdate();//执行sql语句
					
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
	
	public int deleteTask(DbUtil dbUtil,String tableName,String title){
		int n=0;
		String sql="delete from task_manager where typeId=? and title=?";
//		String sql="insert into "+tableName+" values(null,"+"'"+title+"',"+"'"+detail+"',"+todayTime+","+allTime+");";
		Connection con=null;
		try {
			con=dbUtil.getCon();//获取数据库连接
			
			int typeId=TableDao.getTypeId(con, tableName);
			
			PreparedStatement pstmt=con.prepareStatement(sql);//对sql语句进行预编译
			pstmt.setInt(1, typeId);
			pstmt.setString(2, title);
			n= pstmt.executeUpdate();//执行sql语句
					
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
	
	
	public ArrayList<Task> listTask(DbUtil dbUtil,String tableName){//遍历任务面板
		ResultSet rs=null;
		MyPanel myPanel=null;
		ArrayList<Task> values=new ArrayList<Task>();
		Task task=null;
		String sql="select * from task_manager where typeId=?";
		Connection con=null;
		try {
			con=dbUtil.getCon();//获取数据库连接
			
			int typeId=TableDao.getTypeId(con, tableName);
			
			PreparedStatement pstmt=con.prepareStatement(sql);//对sql语句进行预编译
			pstmt.setInt(1,typeId);
			rs=pstmt.executeQuery();//返回一个结果集	
			while(rs.next()){//遍历				
				task=new Task(rs.getInt("id"),rs.getInt("typeId"),rs.getString("title"),rs.getString("detail") , rs.getInt("todayTime"),  rs.getInt("allTime"),rs.getInt("isToday"));
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
	
	
	public ArrayList<Task> listTodayTask(DbUtil dbUtil){//遍历任务面板
		ResultSet rs=null;
		MyPanel myPanel=null;
		ArrayList<Task> values=new ArrayList<Task>();
		Task task=null;
		String sql="select * from task_manager where isToday=1";
		Connection con=null;
		try {
			con=dbUtil.getCon();//获取数据库连接
			
			PreparedStatement pstmt=con.prepareStatement(sql);//对sql语句进行预编译
			rs=pstmt.executeQuery();//返回一个结果集	
			while(rs.next()){//遍历				
				task=new Task(rs.getInt("id"),rs.getInt("typeId"),rs.getString("title"),rs.getString("detail") , rs.getInt("todayTime"),  rs.getInt("allTime"),rs.getInt("isToday"));
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

	public String[] listTaskWithString(DbUtil dbUtil,String tableName){//遍历任务面板
		ResultSet rs=null;
		MyPanel myPanel=null;
		String values[]=null;
		String sql="select * from task_manager where typeId=?";
		int num=0;
		
		Connection con=null;
		try {
			con=dbUtil.getCon();//获取数据库连接
			
			int typeId=TableDao.getTypeId(con, tableName);
			
			PreparedStatement pstmt=con.prepareStatement(sql);//对sql语句进行预编译
			pstmt.setInt(1,typeId);
			rs=pstmt.executeQuery();//返回一个结果集	
			rs.last();
			values=new String[rs.getRow()];
			rs.beforeFirst();
			int i=0;
			while(rs.next()){//遍历				
				values[i]=rs.getString("title");
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
	
	public int moveTo(DbUtil dbUtil,String tableName,String tableName2,String title){
		int n=0;
		String sql="update task_manager set typeId=? where typeId=? and title=?";
//		String sql="insert into "+tableName2+" values(null,?,?,?,?)";
//		String sql="insert into "+tableName+" values(null,"+"'"+title+"',"+"'"+detail+"',"+todayTime+","+allTime+");";
		Connection con=null;
		try {
			con=dbUtil.getCon();//获取数据库连接
			
			PreparedStatement pstmt=con.prepareStatement(sql);//对sql语句进行预编译
			
			int typeId=TableDao.getTypeId(con, tableName);
			int typeId2=TableDao.getTypeId(con, tableName2);
			
			pstmt.setInt(1, typeId2);
			pstmt.setInt(2, typeId);
			pstmt.setString(3,title);

//			pstmt.setString(1,task.title);
//			pstmt.setString(2,task.detail);
//			pstmt.setInt(3, task.todayTime);
//			pstmt.setInt(4, task.allTime);

			n= pstmt.executeUpdate();//执行sql语句,做插入操作

					
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
	
	public boolean isExited(DbUtil dbUtil,String tableName,String title){//判断任务是否已经存在
		ResultSet rs=null;

		String sql="select * from task_manager where typeId=? and title=?";
		int num=0;
		
		Connection con=null;
		try {
			con=dbUtil.getCon();//获取数据库连接
			
			int typeId=TableDao.getTypeId(con, tableName);
			
			PreparedStatement pstmt=con.prepareStatement(sql);//对sql语句进行预编译
			pstmt.setInt(1,typeId);
			pstmt.setString(2,title);
			rs=pstmt.executeQuery();//返回一个结果集	

			int t=0;
			t++;
			
			while(rs.next()){//遍历				
				num++;
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
		
		if(num==1){
			return true;
		}else if(num==0){
			return false;
		}else {
			System.out.println("存在相同的多个任务!");
			return true;
		}
		
		
	}
	
}
