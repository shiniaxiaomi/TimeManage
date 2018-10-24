package gui;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import model.Task;
import mysql.TaskDao;
import util.ChangeUtil;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class EditFrm extends JFrame {

	public Task task;
	public MainFrm mainFrm;
	
	private JPanel contentPane;
	private JTextField titleTxt;
	private JTextField todayTimeTxt;
	private JTextField allTimeTxt;
	private JTextArea detailTxt;
	private JComboBox todayTimeBox;
	private JComboBox allTimeBox;



	/**
	 * Create the frame.
	 */
	public EditFrm(MainFrm mainFrm) {
		
		try { // 使用Windows的界面风格
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.mainFrm=mainFrm;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {		
				init();				
				setVisible(false);
			}
		});
		setAlwaysOnTop(true);
		setResizable(false);
		setBounds(100, 100, 366, 365);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		{
			JLabel label = new JLabel("\u6807\u9898:");
			label.setBounds(14, 13, 72, 18);
			contentPane.add(label);
		}
		{
			titleTxt = new JTextField();
			titleTxt.setBounds(60, 10, 290, 24);
			contentPane.add(titleTxt);
			titleTxt.setColumns(10);
		}
		{
			JLabel label = new JLabel("\u5185\u5BB9:");
			label.setBounds(14, 60, 72, 18);
			contentPane.add(label);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setBounds(60, 60, 290, 107);
			contentPane.add(scrollPane);
			{
				detailTxt = new JTextArea();
				scrollPane.setViewportView(detailTxt);
				detailTxt.setLineWrap(true);
				detailTxt.setWrapStyleWord(true);
			}
		}
		{
			JSeparator separator = new JSeparator();
			separator.setBounds(0, 44, 360, 2);
			contentPane.add(separator);
		}
		{
			JSeparator separator = new JSeparator();
			separator.setBounds(0, 180, 360, 2);
			contentPane.add(separator);
		}
		{
			JLabel label = new JLabel("\u603B\u65F6\u95F4:");
			label.setBounds(14, 241, 72, 18);
			contentPane.add(label);
		}
		{
			JPanel panel = new JPanel();
			panel.setBounds(10, 287, 340, 39);
			contentPane.add(panel);
			{
				JButton button = new JButton("\u786E\u5B9A");
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						updateActionPerformed();
					}
				});
				panel.add(button);
			}
			{
				JButton button = new JButton("\u91CD\u7F6E");
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						init();
					}
				});
				panel.add(button);
			}
		}
		{
			JLabel label = new JLabel("\u4ECA\u65E5\u65F6\u95F4:");
			label.setBounds(14, 195, 68, 18);
			contentPane.add(label);
		}
		{
			todayTimeTxt = new JTextField();
			todayTimeTxt.setBounds(96, 192, 86, 24);
			contentPane.add(todayTimeTxt);
			todayTimeTxt.setColumns(10);
		}
		{
			todayTimeBox = new JComboBox();
			todayTimeBox.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					
					if(todayTimeBox.getSelectedIndex()==0){
						todayTimeTxt.setText(Integer.toString(task.todayTime/60));
					}else {
						todayTimeTxt.setText(Integer.toString(task.todayTime/3600));
					}
					
				}
			});
			todayTimeBox.setModel(new DefaultComboBoxModel(new String[] {"\u5206\u949F", "\u5C0F\u65F6"}));
			todayTimeBox.setSelectedIndex(0);
			todayTimeBox.setBounds(196, 192, 68, 24);
			contentPane.add(todayTimeBox);
		}
		{
			JSeparator separator = new JSeparator();
			separator.setBounds(0, 226, 360, 2);
			contentPane.add(separator);
		}
		{
			allTimeTxt = new JTextField();
			allTimeTxt.setColumns(10);
			allTimeTxt.setBounds(96, 238, 86, 24);
			contentPane.add(allTimeTxt);
		}
		{
			allTimeBox = new JComboBox();
			allTimeBox.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {

					if(allTimeBox.getSelectedIndex()==0){
						allTimeTxt.setText(Integer.toString(task.allTime/60));
					}else {
						allTimeTxt.setText(Integer.toString(task.allTime/3600));
					}
				}
			});
			allTimeBox.setModel(new DefaultComboBoxModel(new String[] {"\u5206\u949F", "\u5C0F\u65F6"}));
			allTimeBox.setSelectedIndex(0);
			allTimeBox.setBounds(196, 238, 68, 24);
			contentPane.add(allTimeBox);
		}
		{
			JSeparator separator = new JSeparator();
			separator.setBounds(0, 272, 360, 2);
			contentPane.add(separator);
		}
	}
	
	
	protected void updateActionPerformed() {
		
		int todayTime=0;
		int allTime=0;
		
		if(todayTimeBox.getSelectedIndex()==0){
			todayTime=ChangeUtil.changeString(todayTimeTxt.getText())*60;
		}else {
			todayTime=ChangeUtil.changeString(todayTimeTxt.getText())*3600;
		}
		
		if(allTimeBox.getSelectedIndex()==0){
			allTime=ChangeUtil.changeString(allTimeTxt.getText())*60;
		}else {
			allTime=ChangeUtil.changeString(allTimeTxt.getText())*3600;
		}
		
		Task task=mainFrm.taskDao.readTask(mainFrm.dbUtil, mainFrm.getSelectTask().typeId, mainFrm.getSelectTask().title);
		task.title=titleTxt.getText();
		task.detail=detailTxt.getText();
		task.todayTime=todayTime;
		task.allTime=allTime;
		
		mainFrm.taskDao.updateTask(mainFrm.dbUtil,mainFrm.getSelectTask().typeId , mainFrm.getSelectTitle(),task);//更新了数据库
		mainFrm.updateInformation();//更新修改后的数据
		
		setVisible(false);
		
	}

	public void init(){
		titleTxt.setText("");
		detailTxt.setText("");
		todayTimeTxt.setText("");
		allTimeTxt.setText("");
		
		todayTimeBox.setSelectedIndex(0);
		allTimeBox.setSelectedIndex(0);
	}
	
	public void updateData(Task task){
		
		this.task=task;//更新当前任务
		
		titleTxt.setText(task.title);
		detailTxt.setText(task.detail);
		
		
		if(todayTimeBox.getSelectedIndex()==0){
			todayTimeTxt.setText(Integer.toString(task.todayTime/60));
		}else {
			todayTimeTxt.setText(Integer.toString(task.todayTime/3600));
		}
		
		if(allTimeBox.getSelectedIndex()==0){
			allTimeTxt.setText(Integer.toString(task.allTime/60));
		}else {
			allTimeTxt.setText(Integer.toString(task.allTime/3600));
		}

		
	}
	
	
	
	

}
