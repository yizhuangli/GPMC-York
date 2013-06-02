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
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.util.Map;
import java.util.TreeMap;

public class AdultLoginGUI extends JFrame implements IView {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JTextField textField_1;
	private JPasswordField passwordField_1;
	private JButton btnLogin;
	private JButton btnRegister;
	private Map<String, IModel> models =new TreeMap<String, IModel>();

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AdultLoginGUI frame = new AdultLoginGUI();
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
	public AdultLoginGUI() {
		//setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
			}
		});
		
		setTitle("Welcome to Story Builder");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 434, 262);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		tabbedPane.addTab("Login", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUsername.setBounds(83, 62, 85, 27);
		panel.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPassword.setBounds(83, 107, 85, 27);
		panel.add(lblPassword);
		
		textField = new JTextField();
		textField.setBounds(201, 66, 126, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(201, 111, 126, 23);
		panel.add(passwordField);
		
		btnLogin = new JButton("Login");
		btnLogin.setBounds(156, 169, 89, 23);
		panel.add(btnLogin);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		tabbedPane.addTab("Register", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblUsername_1 = new JLabel("Username");
		lblUsername_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUsername_1.setBounds(87, 67, 85, 27);
		panel_1.add(lblUsername_1);
		
		JLabel lblPassword_1 = new JLabel("Password");
		lblPassword_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPassword_1.setBounds(87, 118, 85, 21);
		panel_1.add(lblPassword_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(203, 71, 121, 20);
		panel_1.add(textField_1);
		textField_1.setColumns(10);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(203, 119, 121, 21);
		panel_1.add(passwordField_1);
		
		btnRegister = new JButton("Register");
		btnRegister.setBounds(152, 172, 89, 23);
		panel_1.add(btnRegister);
		
		btnLogin.setActionCommand("Login");
		btnRegister.setActionCommand("Register");
		
	}
	
	/**
	 * get username in login panel
	 * @return
	 */
	public String getUsername_login(){
		return textField.getText();
	}
	
	public String getPwd_login(){
		return passwordField.getText();
	}
	
	public String getUsername_Reg(){
		return textField_1.getText();
	}
	public String getPwd_Reg(){
		return passwordField_1.getText();
	}
	
	/**
	 * show the dialogue if username or password is bad
	 * @param message
	 */
//	public void Message(String message){
//		System.out.println(message);
//		JOptionPane.showMessageDialog(null, message);
//	}
//	
	@Override
	public void addController(IController controller) {
		btnLogin.addActionListener((ActionListener)controller);
		btnRegister.addActionListener((ActionListener)controller);
	}

	@Override
	public void addModel(String key, IModel model) {
		models.put(key, model);
	}

	@Override
	public void updateView(String key, IModel model) {
		//No parts need to be updated.
	}

	@Override
	public void showMessage(String title, String msg, int type) {
		JOptionPane.showMessageDialog(this,msg,title,JOptionPane.OK_OPTION|type);
	}
	@Override
	public void setGUIVisible(boolean isVisible) {
		setVisible(isVisible);
	}
}
