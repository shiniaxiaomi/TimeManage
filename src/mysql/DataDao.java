package mysql;

import gui.MainFrm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.Task;

import util.ChangeUtil;
import util.DbUtil;


public class DataDao {
	
	public MainFrm mainFrm=null;
	public SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//设置日期的格式
	
	
	
	public DataDao(MainFrm mainFrm) {
		super();
		this.mainFrm = mainFrm;
	}


	public void updateDate(DbUtil dbUtil){
		
		Date date=new Date();//创建一个日期对象
		String today=sdf.format(date);//获取到今天的日期

		String sql="update data_manager set date=?,todayAllTime=?,allAllTime=? where id=1";

		Connection con=null;
		try {
			con=dbUtil.getCon();//获取数据库连接
			
			PreparedStatement pstmt=con.prepareStatement(sql);//对sql语句进行预编译
			pstmt.setString(1, today);
			pstmt.setInt(2, mainFrm.todayAllTime);
			pstmt.setInt(3, mainFrm.allAllTime);
			
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
	
	public void addDate(DbUtil dbUtil){
		
		Date date=new Date();//创建一个日期对象
		String today=sdf.format(date);//获取到今天的日期

		String sql="insert data_manager values(?,?,?,?)";

		Connection con=null;
		try {
			con=dbUtil.getCon();//获取数据库连接
			
			PreparedStatement pstmt=con.prepareStatement(sql);//对sql语句进行预编译
			pstmt.setInt(1, 1);
			pstmt.setString(2, today);
			pstmt.setInt(3, 0);
			pstmt.setInt(4, 0);
			
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
	
	public void readDate(DbUtil dbUtil){

		Date date=new Date();//创建一个日期对象
		String today=sdf.format(date);//获取到今天的日期
		
		ResultSet rs=null;
		String sql="select * from data_manager where id=1";

		Connection con=null;
		try {
			con=dbUtil.getCon();//获取数据库连接
			
			PreparedStatement pstmt=con.prepareStatement(sql);//对sql语句进行预编译
			
			rs=pstmt.executeQuery();//返回一个结果集
			
			rs.last();			
			
			if(rs.getRow()==0){
				addDate(mainFrm.dbUtil);//把今日的日期写进去
			}else {

				if(today.equals(rs.getString("date"))){	//如果日期相等
					mainFrm.todayAllTime=rs.getInt("todayAllTime");
					mainFrm.allAllTime=rs.getInt("allAllTime");
				}else {	//如果日期不相等
					mainFrm.todayAllTime=0;
					mainFrm.allAllTime=rs.getInt("allAllTime");
					updateDate(mainFrm.dbUtil);//把今日的日期写进去
					
					mainFrm.taskDao.cleanToday(mainFrm.dbUtil);//去除掉今天的任务
					cleanToday(dbUtil);
				}
			
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

	}
	
	
	public void cleanToday(DbUtil dbUtil){
		
		String sql="update task_manager set todayTime=0";
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
	
	
	
	
	
	
	
	
}
