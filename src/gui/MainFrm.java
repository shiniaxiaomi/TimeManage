package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Task;
import model.Time;
import mysql.DataDao;
import mysql.SearchDao;
import mysql.TableDao;
import mysql.TaskDao;
import util.ChangeUtil;
import util.DbUtil;
import util.StringUtil;

public class MainFrm extends JFrame {
	
	public static final int IS_TODAY=1;
	public static final int IS_NOT_TODAY=0;
	
	public static ArrayList<MyPanel> myPanels=new ArrayList<MyPanel>();
	public DbUtil dbUtil=new DbUtil();
	public TableDao tableDao=new TableDao();
	public TaskDao taskDao=new TaskDao();
	public SearchDao searchDao=new SearchDao();
	public DataDao dataDao=new DataDao(this);
	public DefaultListModel taskKindDlm;
	public EditFrm editFrm=new EditFrm(this);
	public EditTaskKindFrm editTaskKindFrm=new EditTaskKindFrm(this);
	public AddTimeFrm addTimeFrm=new AddTimeFrm(this);
	public Time timer=new Time(this);//计时器
	
	
	public int todayAllTime=0;
	public int allAllTime=0;

	public JPanel contentPane;
	public JTextField titleTxt;
	public JTextField timeTxt;
	public JTextField searchTxt;
	public JButton searchButton;
	public JList taskList;
	public JTextArea detailTxt;
	public JButton startButton;
	public JPanel editPanel;
	public JPanel displayPanel;
	public JPopupMenu taskListPopupMenu;
	public JPopupMenu displayPopupMenu;
	private JTextField addTaskTxt;
	private JButton addTaskButton;
	private JTextField addKindTxt;
	private JButton addKindButton;
	public JMenu moveToMenu;
	public JLabel todayAllTimeTxt;
	public JMenuItem addTodayItem;
	public JMenuItem cancelTodayItem;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrm frame = new MainFrm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrm() {
		
		try { // 使用Windows的界面风格
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		dataDao.readDate(dbUtil);//先从数据库中读取日期数据
		
		int left_kuan=170;
		int left_shang_gao=230;
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1033, 816);
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				JMenu menu = new JMenu("\u6DFB\u52A0");
				menuBar.add(menu);
				{
					JMenuItem menuItem = new JMenuItem("\u6DFB\u52A0\u5206\u7C7B");
					menu.add(menuItem);
				}
				{
					JMenuItem menuItem = new JMenuItem("\u6DFB\u52A0\u4EFB\u52A1");
					menu.add(menuItem);
				}
			}
			{
				JMenu menu = new JMenu("\u9009\u62E9");
				menuBar.add(menu);
				{
					JMenuItem menuItem = new JMenuItem("\u5168\u9009\u4EFB\u52A1");
					menu.add(menuItem);
				}
				{
					JMenuItem menuItem = new JMenuItem("\u53D6\u6D88\u5168\u9009");
					menu.add(menuItem);
				}
			}
			{
				JMenu menu = new JMenu("\u5907\u4EFD\u4E0E\u8FD8\u539F");
				menuBar.add(menu);
				{
					JMenuItem menuItem = new JMenuItem("\u5907\u4EFD");
					menu.add(menuItem);
				}
				{
					JMenuItem menuItem = new JMenuItem("\u8FD8\u539F");
					menu.add(menuItem);
				}
			}
			{
				JMenu menu = new JMenu("\u5173\u4E8E\u6211\u4EEC");
				menuBar.add(menu);
				{
					JMenuItem menuItem = new JMenuItem("\u5173\u4E8E\u6211\u4EEC");
					menu.add(menuItem);
				}
			}
		}
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		{
			JSplitPane splitPane = new JSplitPane();
			contentPane.add(splitPane, BorderLayout.CENTER);
			{
				JPanel panel = new JPanel();
				splitPane.setRightComponent(panel);
				panel.setLayout(new BorderLayout(0, 0));
				{
					JScrollPane scrollPane = new JScrollPane();
					scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
					scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
					scrollPane.getVerticalScrollBar().setUnitIncrement(10);//设置滚动条的滚动大小
					panel.add(scrollPane, BorderLayout.CENTER);
					{
						displayPanel = new JPanel();
						displayPanel.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {		
								initInformation();//清空面板
								
								MyPanel myPanel=null;
								
								for(int i=0;i<MainFrm.myPanels.size();i++){
									myPanel=MainFrm.myPanels.get(i);
									
									if(myPanel.isSelected()){
										myPanel.setSelected(false);
										myPanel.setBackground(Color.WHITE);
									}
								}
							}
						});
						scrollPane.setViewportView(displayPanel);
						{
							displayPopupMenu = new JPopupMenu();
							addPopup(displayPanel, displayPopupMenu);
							{
								JMenuItem menuItem = new JMenuItem("\u5220\u9664");
								menuItem.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										deleteTaskActionPerformed();//删除任务
									}
								});
								displayPopupMenu.add(menuItem);
							}
							{
								JMenuItem menuItem = new JMenuItem("\u7F16\u8F91");
								menuItem.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										editActionPerformed();
									}
								});
								displayPopupMenu.add(menuItem);
							}
							{
								moveToMenu = new JMenu("\u79FB\u52A8\u5230");
								displayPopupMenu.add(moveToMenu);
								{
									JMenuItem menuItem = new JMenuItem("\u4EFB\u52A1\u6240\u5728\u4F4D\u7F6E");
									menuItem.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											
											displayTaskLocation(getSelectTask());
											
										}
									});
									displayPopupMenu.add(menuItem);
								}
								{
									addTodayItem = new JMenuItem("\u52A0\u5165\u4ECA\u65E5\u4EFB\u52A1");
									addTodayItem.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											
											Task task=getSelectTask();
											
											Task task2=new Task(task.typeId, task.title, task.detail, task.todayTime, task.allTime, MainFrm.IS_TODAY);
											
											taskDao.updateTask(dbUtil,task2);//更新
											
										}
									});
									displayPopupMenu.add(addTodayItem);
								}
								{
									cancelTodayItem = new JMenuItem("\u53D6\u6D88\u4ECA\u65E5\u4EFB\u52A1");
									cancelTodayItem.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											
											
											Task task=getSelectTask();
											
											Task task2=new Task(task.typeId, task.title, task.detail, task.todayTime, task.allTime, MainFrm.IS_NOT_TODAY);
											
											taskDao.updateTask(dbUtil,task2);//更新
											
											displayTodayTask();//在显示一下今日任务
										}
									});
									displayPopupMenu.add(cancelTodayItem);
								}
								{
									JMenuItem mntmNewMenuItem = new JMenuItem("New menu item");

									updateMoveToMenu();//更新"移动到"的右键菜单按钮
	
								}
							}
						}
						displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.PAGE_AXIS));
						
					}
				}
				{
					JSplitPane splitPane_1 = new JSplitPane();
					splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
					panel.add(splitPane_1, BorderLayout.NORTH);
					{
						editPanel = new JPanel();
						splitPane_1.setLeftComponent(editPanel);
						editPanel.setBackground(Color.DARK_GRAY);
						editPanel.setPreferredSize(new Dimension(100, 165));
						editPanel.setLayout(null);
						{
							JScrollPane scrollPane = new JScrollPane();
							scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
							scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
							scrollPane.setBounds(59, 57, 452, 100);
							editPanel.add(scrollPane);
							{
								detailTxt = new JTextArea();
								detailTxt.setEditable(false);
								scrollPane.setViewportView(detailTxt);
								detailTxt.setFont(new Font("微软雅黑", Font.PLAIN, 17));
								detailTxt.setLineWrap(true);
							}
						}
						{
							JLabel label = new JLabel("\u6807\u9898:");
							label.setForeground(Color.WHITE);
							label.setBackground(Color.WHITE);
							label.setBounds(14, 20, 72, 18);
							editPanel.add(label);
						}
						{
							titleTxt = new JTextField();
							titleTxt.addKeyListener(new KeyAdapter() {
								@Override
								public void keyPressed(KeyEvent e) {
									if(e.getKeyCode()==KeyEvent.VK_ENTER){
										
										if(isSelectedTask()){
											taskDao.updateTask(dbUtil, getSelectItem(), getSelectTitle(), titleTxt.getText());//吧数据写入数据库
											titleTxt.getCaret().setVisible(false);
		
											updateDisplayPanel(getSelectItem());
										}	
										
									}
								}
							});
							titleTxt.setFont(new Font("微软雅黑", Font.PLAIN, 17));
							titleTxt.setBounds(59, 12, 452, 32);
							editPanel.add(titleTxt);
							titleTxt.setColumns(40);
						}
						{
							JLabel label = new JLabel("\u5185\u5BB9:");
							label.setForeground(Color.WHITE);
							label.setBackground(Color.WHITE);
							label.setBounds(14, 60, 72, 18);
							editPanel.add(label);
						}
						{
							startButton = new JButton("\u5F00\u59CB\u8BA1\u65F6");
							startButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									stratTimeActionPerformed();
								}
							});
							startButton.setFont(new Font("微软雅黑", Font.BOLD, 34));
							startButton.setBounds(602, 107, 174, 50);
							editPanel.add(startButton);
						}
						{
							timeTxt = new JTextField();
							timeTxt.setEditable(false);
							timeTxt.setFont(new Font("微软雅黑", Font.BOLD, 65));
							timeTxt.setText("00:00:00");
							timeTxt.setBounds(538, 13, 288, 80);
							editPanel.add(timeTxt);
							timeTxt.setColumns(10);
						}
						{
							JSeparator separator = new JSeparator();
							separator.setBackground(Color.WHITE);
							separator.setOrientation(SwingConstants.VERTICAL);
							separator.setBounds(525, 0, 2, 165);
							editPanel.add(separator);
						}
					}
					{
						JPanel panel_1 = new JPanel();
						panel_1.setBackground(Color.DARK_GRAY);
						splitPane_1.setRightComponent(panel_1);
						{
							addTaskTxt = new JTextField();
							addTaskTxt.addKeyListener(new KeyAdapter() {
								@Override
								public void keyPressed(KeyEvent e) {
									if(e.getKeyCode()==KeyEvent.VK_ENTER){
										addTaskActionPerformed();//添加任务
									}
								}
							});
							addTaskTxt.setFont(new Font("微软雅黑", Font.PLAIN, 22));
							panel_1.add(addTaskTxt);
							addTaskTxt.setColumns(33);
						}
						{
							addTaskButton = new JButton("\u6DFB\u52A0\u4EFB\u52A1");
							addTaskButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									
									addTaskActionPerformed();//添加任务
									
								}
							});
							addTaskButton.setFont(new Font("微软雅黑", Font.PLAIN, 20));
							panel_1.add(addTaskButton);
						}
					}
				}
			}
			{
				JPanel panel = new JPanel();
				splitPane.setLeftComponent(panel);
				panel.setLayout(new BorderLayout(0, 0));
				{
					JPanel panel_1 = new JPanel();
					panel.add(panel_1, BorderLayout.NORTH);
					panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.PAGE_AXIS));
					{
						searchTxt = new JTextField();
						searchTxt.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) {
								
								if(e.getKeyCode()==KeyEvent.VK_ENTER){
									
									searchActionPerformed();		//搜索	
								}
							}
						});
						panel_1.add(searchTxt);
						searchTxt.setColumns(10);
					}
					{
						JPanel panel_2 = new JPanel();
						panel_1.add(panel_2);
						{
							searchButton = new JButton("\u641C\u7D22");
							searchButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {	
									
									searchActionPerformed();		//搜索		
									
								}
							});
							panel_2.add(searchButton);
						}
						{
							JButton button = new JButton("\u91CD\u7F6E");
							button.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									searchTxt.setText("");
								}
							});
							panel_2.add(button);
						}
					}
					{
						addKindTxt = new JTextField();
						addKindTxt.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) {
								if(e.getKeyCode()==KeyEvent.VK_ENTER){
									if(StringUtil.isNotEmpty(addKindTxt.getText())){
										tableDao.addTable(dbUtil,addKindTxt.getText().trim());
										addKindTxt.setText("");
										updateListTaskKind();//更新任务分类列表
									}
								}
								
							}
						});
						panel_1.add(addKindTxt);
						addKindTxt.setColumns(10);
					}
					{
						JPanel panel_2 = new JPanel();
						panel_1.add(panel_2);
						{
							addKindButton = new JButton("\u4EFB\u52A1\u7C7B\u522B\u6DFB\u52A0");
							addKindButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									addTableActionPerformed();//添加任务列表
								}
							});
							panel_2.add(addKindButton);
						}
					}
				}
				{
					{
						JPanel panel_1 = new JPanel();
						panel.add(panel_1, BorderLayout.CENTER);
						panel_1.setPreferredSize(new Dimension(10, left_shang_gao));
						panel_1.setLayout(new BorderLayout(0, 0));
						JScrollPane scrollPane = new JScrollPane();
						scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
						panel_1.add(scrollPane);
						scrollPane.setPreferredSize(new Dimension(left_kuan, 100));
						taskList = new JList();
						taskList.addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								
									
									if(taskList.getSelectedValue()!=null){
										
										String s=taskList.getSelectedValue().toString();
										updateDisplayPanel(s);//更新罗列显示面板
										
										initInformation();//清空面板
										
										addTimeFrm.updateAddTime();		
									}
									
								
							}
						});
						
						updateListTaskKind();//更新任务分类列表
						taskList.setFont(new Font("微软雅黑", Font.BOLD, 17));
						taskList.setToolTipText("\u4EFB\u52A1\u5206\u7C7B");
						
						scrollPane.setViewportView(taskList);
						{
							taskListPopupMenu = new JPopupMenu();
							addPopup(taskList, taskListPopupMenu);
							{
								JMenuItem menuItem = new JMenuItem("\u7F16\u8F91");
								menuItem.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										
										editTaskKindActionPerformed();
										
									}
								});
								taskListPopupMenu.add(menuItem);
							}
							{
								JMenuItem menuItem = new JMenuItem("\u5220\u9664");
				
								menuItem.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										deleteTableActionPerformed();//删除任务分类
									}
								});
								taskListPopupMenu.add(menuItem);
							}
						}
						{
							JLabel lblNewLabel = new JLabel("\u4EFB\u52A1\u5206\u7C7B");
							lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
							panel_1.add(lblNewLabel, BorderLayout.NORTH);
						}
						{
							JPanel panel_2 = new JPanel();
							panel_1.add(panel_2, BorderLayout.SOUTH);
							panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.PAGE_AXIS));
							{
								JPanel panel_3 = new JPanel();
								panel_2.add(panel_3);
								{
									JButton button = new JButton("\u4ECA\u65E5\u4EFB\u52A1");
									button.addActionListener(new ActionListener() {
										public void actionPerformed(ActionEvent e) {
											displayTodayTask();
										}
									});
									panel_3.add(button);
								}
							}
							{
								JPanel panel_3 = new JPanel();
								panel_2.add(panel_3);
								{
									JLabel label = new JLabel("\u4ECA\u65E5\u603B\u65F6\u95F4");
									panel_3.add(label);
								}
							}
							{
								JPanel panel_3 = new JPanel();
								panel_2.add(panel_3);
								{
									todayAllTimeTxt = new JLabel("00:00:00");
									panel_3.add(todayAllTimeTxt);
								}
							}
						}
						
						taskList.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseReleased(MouseEvent e) {
								if(e.getButton()==MouseEvent.BUTTON3){

									taskListPopupMenu.show(taskList, e.getX(), e.getY());//显示右键菜单
									
									
								}
//								else if(e.getButton()==MouseEvent.BUTTON1){
//									
//									if(!addTimeFrm.isVisible()){
//										String s=taskList.getSelectedValue().toString();
//										updateDisplayPanel(s);//更新罗列显示面板
//										
//										initInformation();//清空面板
//										
//										addTimeFrm.updateAddTime();
//										
//									}
//									
//									
//								}
							}
						});
						
						
//						updateDisplayPanel(taskList.getSelectedValue().toString());//更新罗列显示面板
						
						todayAllTimeTxt.setText(ChangeUtil.changeTime(todayAllTime));//初始化
						
					}
				}
			}
		}
		
		
		
	}




//-----------------------------------------------------------------------------------------------------------------------------------
	
	protected void displayTodayTask() {//显示今日做过的任务
		

		
		
		MyPanel myPanel=null;
		ArrayList<Task> values=taskDao.listTodayTask(dbUtil);
		
		displayPanel.removeAll();
		myPanels.clear();
		
		for(int i=0;i<values.size();i++){
			myPanel=new MyPanel(values.get(i),this);
			displayPanel.add(myPanel);
			myPanels.add(myPanel);
			
			{
				JSeparator separator = new JSeparator();
				separator.setMaximumSize(new Dimension(32767, 2));
				displayPanel.add(separator);
			}

		}
		
		
		displayPanel.updateUI();
		
		
	}
	
	
	protected void titleFocusGained() {//标题获取到焦点后

		titleTxt.setEditable(true);
	}
	
	
	protected void stratTimeActionPerformed() {//开始计时和停止计时
		
		if(timer.isStarted()){
			timer.stop();
			
			addTimeFrm.showAddTimeFrm();//显示窗体
			
		}else {
			timer.star();
		}
	}

	
	protected void editTaskKindActionPerformed() {//显示任务类别编辑面板
		editTaskKindFrm.setVisible(true);
		
	}
	
	
	protected void editActionPerformed() {//编辑任务
		
		Task task=getSelectTask();	
		editFrm.updateData(task);
	
		editFrm.setVisible(true);//显示编辑窗口
		
	}
	
	public void updateInformation(){//更新信息面板
		
		MyPanel myPanel=isSelectedTaskReturnMyPanel();
		
		if(myPanel!=null){
			if( myPanel.selected){

				Task task=taskDao.readTask(dbUtil, myPanel.typeId, myPanel.title);
				titleTxt.setText(task.title);
				detailTxt.setText(task.detail);
				
				myPanel.todayTimeLabel.setText(ChangeUtil.changeTime(task.todayTime));
				myPanel.allTimeLabel.setText(ChangeUtil.changeTime(task.allTime));
				
			}else {
				initInformation();//初始化信息面板
			}
		}
		
	}
	
	public void initInformation(){//初始化信息面板
		titleTxt.setText("");
		detailTxt.setText("");
	}

	public void updateMoveToMenu(){//更新"移动到"的右键菜单按钮
		
		moveToMenu.removeAll();
		
		JMenuItem mntmDf=null;
		String values[]=tableDao.listMenu(dbUtil);
		
		for(int i=0;i<values.length;i++){
			mntmDf = new JMenuItem(values[i]);
			moveToMenu.add(mntmDf);
			
			mntmDf.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					moveto(e);//移动任务

				}
			});
			
		}
	}
	
	public void moveto(ActionEvent e){//移动任务
		
		Task task=getSelectTask();
		if(!taskDao.isExited(dbUtil, e.getActionCommand(), task.title)){
			taskDao.moveTo(dbUtil, getSelectItem(), e.getActionCommand(), task.title);
			
//			updateDisplayPanel(e.getActionCommand());
			
			//设置选中
			
			setSelectItem(e.getActionCommand());
			
			updateDisplayPanel(e.getActionCommand());
//			setSelectTitle(task.title);
		}	
	
	}
	
	public void displayTaskLocation(Task task){
		
		String tableName=TableDao.getTableName(dbUtil, task.typeId);
		setSelectItem(tableName);
		//---------------显示更新
		String s=taskList.getSelectedValue().toString();
		updateDisplayPanel(s);//更新罗列显示面板
		initInformation();//清空面板	
		addTimeFrm.updateAddTime();
		//---------------	
		
		setSelectTask(task.title);
		
	}
	
	protected void deleteTableActionPerformed() {//删除任务类别
		tableDao.deleteTable(dbUtil,taskList.getSelectedValue().toString());
		updateDeleteListTaskKind();
	}

	protected void addTableActionPerformed() {//添加任务类别
		if(StringUtil.isNotEmpty(addKindTxt.getText())){
			
			if(!tableDao.isExited(dbUtil, addKindTxt.getText().trim())){
				tableDao.addTable(dbUtil,addKindTxt.getText().trim());
				updateListTaskKind();
				addKindTxt.setText("");
				
				
				

			}
			
		}
	}
	

	protected void deleteTaskActionPerformed() {//删除任务
		int i=0;
		for(i=0;i<myPanels.size();i++){
			if(myPanels.get(i).selected){
				break;
			}
		}
		if(i<myPanels.size()){//没有选中的时候不会进行删除操作
			taskDao.deleteTask(dbUtil, taskList.getSelectedValue().toString(), myPanels.get(i).getTitleTxt());
			myPanels.remove(i);
			
			updateDisplayPanel(taskList.getSelectedValue().toString());
		}
		
	}
	
	protected void addTaskActionPerformed() {//添加任务
		
		if(taskList.getSelectedValue()!=null && StringUtil.isNotEmpty(addTaskTxt.getText())){//如果有选中的任务分类并且不是空串
			
			int typeId=TableDao.getTypeId(dbUtil, getSelectItem());
			
			Task task=new Task(typeId,addTaskTxt.getText(), "", 0,0,MainFrm.IS_NOT_TODAY);
			taskDao.addTask(dbUtil, taskList.getSelectedValue().toString(), task);//添加到数据库	
				
			final MyPanel myPanel=new MyPanel(task,this);
			displayPanel.add(myPanel);
			{
				JSeparator separator = new JSeparator();
				separator.setMaximumSize(new Dimension(32767, 2));
				displayPanel.add(separator);
			}
			
			myPanels.add(myPanel);
			addTaskTxt.setText("");
			displayPanel.updateUI();
			
		}
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
	}
	
	public void updateListTaskKind(){//更新任务分类列表
		
		taskKindDlm=tableDao.listTable(dbUtil);
		
		taskList.setModel(taskKindDlm);						
//		taskList.setSelectedIndex(0);	
		setSelectItem(addKindTxt.getText());
		
		updateMoveToMenu();//在更新列表的时候直接更新移动到菜单按钮

	}
	
	public void updateDeleteListTaskKind(){//更新任务分类列表
		
		taskKindDlm=tableDao.listTable(dbUtil);
		
		taskList.setModel(taskKindDlm);						
		taskList.setSelectedIndex(0);	
		
		updateMoveToMenu();//在更新列表的时候直接更新移动到菜单按钮

	}
	
	
	protected void updateDisplayPanel(String s) {//更新罗列显示面板
		
		Task task=getSelectTask();
		
		if(s!=null){
			
			MyPanel myPanel=null;
			ArrayList<Task> values=taskDao.listTask(dbUtil, s);
			
			displayPanel.removeAll();
			myPanels.clear();
			
			for(int i=0;i<values.size();i++){
				myPanel=new MyPanel(values.get(i),this);
				displayPanel.add(myPanel);
				myPanels.add(myPanel);
				
				{
					JSeparator separator = new JSeparator();
					separator.setMaximumSize(new Dimension(32767, 2));
					displayPanel.add(separator);
				}
				
				
				if(task!=null){
					if(task.title.equals(myPanel.title)){				
						myPanel.setSelectTask();
					}
				}
			}		
			displayPanel.updateUI();
		}	
	}
	
	public void listDisplayPanel(ArrayList<Task> values){
		MyPanel myPanel=null;
		
		displayPanel.removeAll();
		myPanels.clear();
		
		for(int i=0;i<values.size();i++){
			myPanel=new MyPanel(values.get(i),this);
			displayPanel.add(myPanel);
			myPanels.add(myPanel);
			
			{
				JSeparator separator = new JSeparator();
				separator.setMaximumSize(new Dimension(32767, 2));
				displayPanel.add(separator);
			}
		}	
		
		displayPanel.updateUI();
		
		
	}

	
	public String getSelectItem(){//返回选中的任务分类的名称
		
		return taskList.getSelectedValue().toString();
	}
	
	public void setSelectItem(String tableName){//返回选中的任务分类的名称
		int i=0;
		String s=null;
		for(i=0;i<taskKindDlm.size();i++){
			s=(String) taskKindDlm.get(i);
			if(s.equals(tableName)) break;
		}

		taskList.setSelectedIndex(i);
	}
	
	public String getSelectTitle(){//返回选中的任务标题
		int i=0;
		for(i=0;i<myPanels.size();i++){
			if(myPanels.get(i).selected){
				break;
			}
		}
		return myPanels.get(i).title;
	}
	
	public void setSelectTask(String title){//返回选中的任务分类的名称
		int i=0;
		String s=null;
		MyPanel myPanel=null;
		for(i=0;i<myPanels.size();i++){
			myPanel=myPanels.get(i);
			s=myPanel.title;
			if(s.equals(title)) break;
		}

		myPanel.setSelectTask();
	}
	
//	public void setSelectTitle(String title){//返回选中的任务标题
//		int i=0;
//		
//		MyPanel myPanel=null;
//		
//		for(i=0;i<myPanels.size();i++){
//			myPanel=myPanels.get(i);
//			if(myPanel.title.equals(title)){
//				myPanel.setSelected(true);
//				myPanel.setBackground(SystemColor.textHighlight);
//				break;
//			}
//		}
//		
//		myPanel.displayChange();
//
//
//		
//		
//		
//		
//		
//		
//		
//	}
	
	public boolean isSelectedTask(){
		
		int i=0;
		for(i=0;i<myPanels.size();i++){
			if(myPanels.get(i).selected){
				break;
			}
		}
		
		if(i<myPanels.size()){
			return true;
		}else {
			return false;
		}
		
	}
	
	public MyPanel isSelectedTaskReturnMyPanel(){
		
		int i=0;
		for(i=0;i<myPanels.size();i++){
			if(myPanels.get(i).selected){
				break;
			}
		}
		
		if(i<myPanels.size()){
			return myPanels.get(i);
		}else {
			return null;
		}

	}
	
	public int isSelectedTaskReturnNum(){
		
		int i=0;
		for(i=0;i<myPanels.size();i++){
			if(myPanels.get(i).selected){
				break;
			}
		}
		
		if(i<myPanels.size()){
			return i;
		}else {
			return -1;
		}

	}
	
	public Task getSelectTask(){//返回选中的任务对象
		int i=0;
		for(i=0;i<myPanels.size();i++){
			if(myPanels.get(i).selected){
				break;
			}
		}

		if(i<myPanels.size()){
			return new Task(myPanels.get(i).id,myPanels.get(i).typeId,myPanels.get(i).title, myPanels.get(i).detail, myPanels.get(i).todayTime, myPanels.get(i).allTime, myPanels.get(i).isToday);
		}else {
			return null;
		}
		

	}

	
	protected void searchActionPerformed() {//搜索
		
		ArrayList<Task> values=searchDao.listAllTask(dbUtil, searchTxt.getText());
		
		
		listDisplayPanel(values);
		
	}

}
