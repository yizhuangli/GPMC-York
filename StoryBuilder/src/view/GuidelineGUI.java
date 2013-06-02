package view;

import interfaces.IController;
import interfaces.IModel;
import interfaces.IView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;

public class GuidelineGUI extends JFrame implements IView {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					GuidelineGUI frame = new GuidelineGUI();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public GuidelineGUI() {
		setTitle("Guideline");
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 628, 411);
		
		center();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 592, 362);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		tabbedPane.addTab("Step1", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblGoToThe = new JLabel("Go to the create story interface");
		lblGoToThe.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblGoToThe.setBounds(56, 27, 521, 44);
		panel.add(lblGoToThe);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("image\\step1.png"));
		lblNewLabel.setBounds(163, 114, 230, 151);
		panel.add(lblNewLabel);
		
		JLabel lblYouCanFind = new JLabel("You can find it at the left corner in the operation box.");
		lblYouCanFind.setBounds(56, 82, 434, 29);
		panel.add(lblYouCanFind);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		tabbedPane.addTab("Step2", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblCompleteStoryInfomation = new JLabel("Complete story infomation");
		lblCompleteStoryInfomation.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCompleteStoryInfomation.setBounds(58, 27, 291, 41);
		panel_1.add(lblCompleteStoryInfomation);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("image\\step2.png"));
		lblNewLabel_1.setBounds(68, 60, 442, 263);
		panel_1.add(lblNewLabel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		tabbedPane.addTab("Step3", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel lblCompletePageInformation = new JLabel("Complete page information");
		lblCompletePageInformation.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCompletePageInformation.setBounds(51, 0, 219, 47);
		panel_2.add(lblCompletePageInformation);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("image\\step3.png"));
		label.setBounds(78, 37, 469, 297);
		panel_2.add(label);
	}

	/**
	 * make the frame in the middle of the screen
	 */
	  protected void center() {
		    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		    Dimension us = getSize();
		    int x = (screen.width - us.width) / 2;
		    int y = (screen.height - us.height) / 2;
		    setLocation(x, y);
		  }

	@Override
	public void showMessage(String title, String msg, int type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGUIVisible(boolean isVisible) {
		// TODO Auto-generated method stub
		setVisible(isVisible);
	}

	@Override
	public void addController(IController controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addModel(String key, IModel model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateView(String key, IModel model) {
		// TODO Auto-generated method stub
		
	}
}
