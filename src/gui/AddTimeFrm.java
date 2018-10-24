package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import model.Task;
import mysql.TableDao;
import util.ChangeUtil;

public class AddTimeFrm extends JFrame {

	private JPanel contentPane;
	public MainFrm mainFrm;
	private JComboBox taskKindBox;
	private JComboBox taskBox;
	
	public DefaultComboBoxModel taskKindBoxModel=new DefaultComboBoxModel();
	public DefaultComboBoxModel taskBoxModel=new DefaultComboBoxModel();
			
			
	public int state=0;

	/**
	 * Create the frame.
	 */
	public AddTimeFrm(MainFrm mainFrm) {
		
		try { // 使用Windows的界面风格
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.mainFrm=mainFrm;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				
				clean();
				
				setVisible(false);
			}
		});
		
		
		setAlwaysOnTop(true);
		setResizable(false);
		setBounds(100, 100, 242, 208);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		{
			JPanel panel = new JPanel();
			contentPane.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			{
				JSeparator separator = new JSeparator();
				separator.setBounds(0, 41, 226, 2);
				panel.add(separator);
			}
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBounds(14, 0, 201, 42);
				panel.add(panel_1);
				panel_1.setLayout(null);
				{
					JLabel label = new JLabel("\u63D0\u793A:\r\n");
					label.setBounds(0, 0, 38, 18);
					panel_1.add(label);
				}
				{
					JLabel label = new JLabel("\u53EF\u76F4\u63A5\u5728\u7A97\u53E3\u9009\u53D6\u4EFB\u52A1");
					label.setBounds(44, 0, 150, 18);
					panel_1.add(label);
				}
				{
					JLabel label = new JLabel("\u4E5F\u53EF\u76F4\u63A5\u70B9\u51FB\u4EFB\u52A1\u9009\u53D6");
					label.setBounds(44, 22, 154, 18);
					panel_1.add(label);
				}
			}
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBounds(10, 41, 205, 77);
				panel.add(panel_1);
				panel_1.setLayout(null);
				{
					JLabel label = new JLabel("\u4EFB\u52A1\u7C7B\u522B:");
					label.setBounds(0, 16, 72, 18);
					panel_1.add(label);
				}
				{
					taskKindBox = new JComboBox();			
					taskKindBox.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							taskKindItemStateChanged();							
							
						}
					});
					taskKindBox.setBounds(74, 13, 117, 24);
					panel_1.add(taskKindBox);
				}
				{
					JLabel label = new JLabel("\u5B50\u4EFB\u52A1:");
					label.setBounds(0, 47, 72, 18);
					panel_1.add(label);
				}
				{
					taskBox = new JComboBox();
					taskBox.setBounds(74, 44, 117, 24);
					panel_1.add(taskBox);
				}
			}
			{
				JSeparator separator = new JSeparator();
				separator.setBounds(0, 119, 226, 2);
				panel.add(separator);
			}
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBounds(14, 127, 198, 36);
				panel.add(panel_1);
				{
					JButton button = new JButton("\u786E\u5B9A");
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							confirmActionPerformed();
						}
					});
					panel_1.add(button);
				}
				{
					JButton button = new JButton("\u91CD\u7F6E");
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							
							resetActionPerformed();
						}
					});
					panel_1.add(button);
				}
			}
		}
	}
	
	public void clean(){
		
		
		mainFrm.timer.time=0;
		mainFrm.timer.timeString=ChangeUtil.changeTime(mainFrm.timer.time);
		mainFrm.timeTxt.setText(mainFrm.timer.timeString);
	}
	
	
	
	public void showAddTimeFrm(){//显示窗口
	
		taskKindItemChange();
		taskItemChange();
		
		mainFrm.addTimeFrm.setVisible(true);//显示窗口
	}
	
	public void closeAddTimeFrm(){//关闭窗口
		
		mainFrm.updateDisplayPanel(mainFrm.getSelectItem());
		
		mainFrm.addTimeFrm.setVisible(false);//显示窗口
	}
	
	
	protected void confirmActionPerformed() {//确定
		
		String tableName=getTaskKindSelectedItem();
		String title=getTaskSelectedItem();
		
		if(!title.equals("")){
			Task task=mainFrm.taskDao.readTask(mainFrm.dbUtil, tableName, title);
			
			int todayTime=task.todayTime+mainFrm.timer.time;
			int allTime=task.allTime+mainFrm.timer.time;

			Task task2=new Task(task.typeId, task.title, task.detail, todayTime, allTime, MainFrm.IS_TODAY);
			
//			mainFrm.taskDao.updateTask(mainFrm.dbUtil, tableName, title, todayTime, allTime);
			mainFrm.taskDao.updateTask(mainFrm.dbUtil, tableName, title, task2);
			
			mainFrm.allAllTime=mainFrm.allAllTime+mainFrm.timer.time;
			mainFrm.dataDao.updateDate(mainFrm.dbUtil);//更新总时间和今日总时间
				
			mainFrm.timer.time=0;
			mainFrm.timer.timeString=ChangeUtil.changeTime(mainFrm.timer.time);
			mainFrm.timeTxt.setText(mainFrm.timer.timeString);
			
			mainFrm.displayTaskLocation(task2);
			
			closeAddTimeFrm();//关闭窗口
		}
		
		
	}



	protected void resetActionPerformed() {//重置按钮
		
		taskKindBox.setSelectedIndex(0);//设置选中第一个可选项
	}



	protected void taskKindItemStateChanged() {//如果任务类别改变,子任务跟着改变
		state++;
		if(state==2){
			state=0;	
			
			taskItemChange();//改变子任务
		
		}
	}
	
	public void taskItemChange(){//改变子任务
		//子任务
		taskBoxModel.removeAllElements();//全部移除

		String values[]=mainFrm.taskDao.listTaskWithString(mainFrm.dbUtil, getTaskKindSelectedItem());
		for(int i=0;i<values.length;i++){
			
			taskBoxModel.addElement(values[i]);//添加要可选项
		}
		
		taskBox.setModel(taskBoxModel);//显示可选项
		if(taskBoxModel.getSize()>=1){
			taskBox.setSelectedIndex(0);//设置选中第一个可选项
		}else {
			taskBox.setSelectedIndex(-1);//设置选中第一个可选项
		}
		
	}
	
	public void taskKindItemChange(){//改变任务类别
		//任务类别
		taskKindBoxModel.removeAllElements();//全部移除

		String values[]=mainFrm.tableDao.listMenu(mainFrm.dbUtil);
		for(int i=0;i<values.length;i++){
			taskKindBoxModel.addElement(values[i]);//添加要可选项
		}
		
		taskKindBox.setModel(taskKindBoxModel);//显示可选项
		if(taskKindBoxModel.getSize()>=1){
			taskKindBox.setSelectedIndex(0);//设置选中第一个可选项
		}else {
			taskKindBox.setSelectedIndex(-1);//设置选中第一个可选项
		}
		
	}

	
	public String getTaskKindSelectedItem(){
		
		return taskKindBox.getSelectedItem().toString();
	}
	
	public String getTaskSelectedItem(){
		
		if(taskBox.getSelectedItem()==null){
			return "";
		}else {
			return taskBox.getSelectedItem().toString();
		}
		
	}


	public void updateAddTime(MyPanel myPanel) {
		
		int typeId=myPanel.typeId;
		
		String tableName=TableDao.getTableName(mainFrm.dbUtil, typeId);
		String title=myPanel.title;
		
		
		String s=null;
		for(int i=0;i<taskKindBoxModel.getSize();i++){			
			s=(String) taskKindBoxModel.getElementAt(i);			
			if(s.equals(tableName)){				
				taskKindBox.setSelectedIndex(i);
				break;
			}
		}
		
		for(int i=0;i<taskBoxModel.getSize();i++){			
			s=(String) taskBoxModel.getElementAt(i);			
			if(s.equals(title)){				
				taskBox.setSelectedIndex(i);
				break;
			}
		}
		
	}
	
	
	public void updateAddTime() {
		
		String tableName=mainFrm.taskList.getSelectedValue().toString();
		
		String s=null;
		for(int i=0;i<taskKindBoxModel.getSize();i++){			
			s=(String) taskKindBoxModel.getElementAt(i);			
			if(s.equals(tableName)){				
				taskKindBox.setSelectedIndex(i);
				break;
			}
		}
		
	}
	
	
}
