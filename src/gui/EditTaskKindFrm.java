package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import util.StringUtil;

public class EditTaskKindFrm extends JFrame {

	public MainFrm mainFrm;
	
	private JPanel contentPane;
	private JTextField taskKindName;


	/**
	 * Create the frame.
	 */
	public EditTaskKindFrm(MainFrm mainFrm) {
		
		try { // 使用Windows的界面风格
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.mainFrm=mainFrm;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				
				taskKindName.setText("");
				setVisible(false);
			}
		});
		setResizable(false);
		setBounds(100, 100, 256, 133);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		{
			JPanel panel = new JPanel();
			contentPane.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			{
				JLabel label = new JLabel("\u7C7B\u522B\u540D\u79F0:");
				label.setBounds(14, 13, 106, 18);
				panel.add(label);
			}
			{
				taskKindName = new JTextField();
				taskKindName.setBounds(84, 10, 143, 24);
				panel.add(taskKindName);
				taskKindName.setColumns(10);
			}
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBounds(14, 44, 213, 43);
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
							taskKindName.setText("");
						}
					});
					panel_1.add(button);
				}
			}
		}
	}


	protected void confirmActionPerformed() {
		
		if(StringUtil.isNotEmpty(taskKindName.getText())){
			mainFrm.tableDao.updateTableName(mainFrm.dbUtil, mainFrm.getSelectItem(), taskKindName.getText());
			taskKindName.setText("");
			
			mainFrm.updateListTaskKind();
			
			
			setVisible(false);
		}

	}
}
