package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import model.Task;
import mysql.TableDao;
import util.ChangeUtil;

public class MyPanel extends JPanel {
	
	public boolean selected=false;
	public MainFrm mainFrm=null;

	public int id;
	public int typeId;	
	public String title;
	public String detail;
	public int todayTime;
	public int allTime;
	public int isToday;
	
	public JTextField textField;
	public JButton button;
	public JLabel todayTimeLabel;
	public JLabel allTimeLabel;
	


	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detailTxt) {
		this.detail = detailTxt;
	}

	public String getTitleTxt() {
		return title;
	}

	

	public MyPanel(Task task,MainFrm mainFrm) {
		
		this.id=task.id;
		this.typeId=task.typeId;
		this.title = task.title;
		this.detail = task.detail;
		this.todayTime=task.todayTime;
		this.allTime=task.allTime;
		this.isToday=task.isToday;
		this.mainFrm=mainFrm;
		
		setBackground(Color.WHITE);
		

		int panel_pref_size=40;
		setAlignmentY(Component.TOP_ALIGNMENT);
		setMaximumSize(new Dimension(30000, panel_pref_size));
		setPreferredSize(new Dimension(811, 40));
		
		{
			JTextField titleTxt_1 = new JTextField();
			titleTxt_1.setEditable(false);
			titleTxt_1.setFont(new Font("微软雅黑", Font.PLAIN, 19));
			titleTxt_1.setText(title);
			add(titleTxt_1);
			titleTxt_1.setColumns(30);
		}
		{
			JLabel label = new JLabel("今日时间");
			add(label);
		}
		{
			todayTimeLabel = new JLabel(ChangeUtil.changeTime(todayTime));
			add(todayTimeLabel);
		}
		{
			JLabel label = new JLabel("总时间");
			add(label);
		}
		{
			allTimeLabel = new JLabel(ChangeUtil.changeTime(allTime));
			add(allTimeLabel);
		}
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.getButton()==MouseEvent.BUTTON1){
					
					MyPanel myPanel=null;
					
					for(int i=0;i<MainFrm.myPanels.size();i++){
						myPanel=MainFrm.myPanels.get(i);
						
						if(myPanel.isSelected()){
							myPanel.setSelected(false);
							myPanel.setBackground(Color.WHITE);
						}
					}
					setSelected(true);
					setBackground(SystemColor.textHighlight);
					
					updateAddTimeFrm();//点击任务即可更新添加时间的窗口
					
					
					displayChange();
				}else if(e.getButton()==MouseEvent.BUTTON3){
					if(isSelected()){
						showRightMenu(e);
						disableMune();
					}	
				}

				
			}
		});

		
		
	}
	
	public void updateAddTimeFrm(){//点击任务即可更新添加时间的窗口
		
		mainFrm.addTimeFrm.updateAddTime(this);
		
	}
	
	
	public void setSelectTask(){
		
		setSelected(true);
		setBackground(SystemColor.textHighlight);
	}
	
	public void showRightMenu(MouseEvent e){
		mainFrm.displayPopupMenu.show(this, e.getX(), e.getY());
		
	}
	
	public void disableMune(){
		
		int i=0;
		String s=null;
		String tableName=null;
		
		tableName=TableDao.getTableName(mainFrm.dbUtil, mainFrm.getSelectTask().typeId);
		
		for(i=0;i<mainFrm.moveToMenu.getItemCount();i++){
			s=mainFrm.moveToMenu.getItem(i).getText();

			if(s.equals(tableName)){
				mainFrm.moveToMenu.getItem(i).setEnabled(false);
			}else{
				mainFrm.moveToMenu.getItem(i).setEnabled(true);
			}
		}
		
		
		if(mainFrm.getSelectTask().isToday==MainFrm.IS_TODAY){
			mainFrm.addTodayItem.setEnabled(false);
			mainFrm.cancelTodayItem.setEnabled(true);
		}else{
			mainFrm.addTodayItem.setEnabled(true);
			mainFrm.cancelTodayItem.setEnabled(false);
		}

		
	}
	

	public void displayChange(){
		
		
		Task task=mainFrm.taskDao.readTask(mainFrm.dbUtil, this.typeId, this.title);
		
		mainFrm.titleTxt.setText(task.title);
		mainFrm.detailTxt.setText(task.detail);
		
	}


	
	

}
