package view;

import interfaces.*;//CHANGE

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import model.*;

import controller.*;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.util.TreeMap;

public class MainChooseTypeGUI extends JFrame implements IView {//CHANGE

	private JPanel contentPane;
	static MainChooseTypeGUI mainChoose = new MainChooseTypeGUI();
	Map<String, IModel> models =new TreeMap<String, IModel>();//CHANGE
	
	JButton btnAdult = new JButton("Adult");
	JButton btnChild = new JButton("Child");
	/**
	 * Launch the application. CHANGE
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					mainChoose.setVisible(true);
					mainChoose.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					
					//CHANGE-----
					IController adultController=new AdultController();
					//IModel adultModel=new Adult();
					mainChoose.addController("adultController", adultController);
					//mainChoose.addModel("adultModel", adultModel);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	/**
	 * Create the frame.
	 */
	public MainChooseTypeGUI() {
		 
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				int option = JOptionPane.showConfirmDialog(null, "Are you sure?","Exit",JOptionPane.YES_NO_OPTION);
				if(option==JOptionPane.YES_OPTION){
					System.exit(0);
				}
				else{
					  return;
				}
				
			}
		});
		setTitle("Welcome");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		center();
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(42, 57, 346, 148);
		contentPane.add(panel);
		panel.setLayout(null);
		
		
		//CHANGE
//		btnAdult.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
////				ALG.setVisible(true);
////				mainChoose.setVisible(false);
//				//adultController ac = new adultController();
//				//ac.initAdultlogin(mainChoose);
//				
//			}
//		});
		btnAdult.setActionCommand("ShowAdultLoginGUI");
		btnAdult.setFocusPainted(false);
		btnAdult.setContentAreaFilled(false);
		btnAdult.setIcon(new ImageIcon("image\\teacher.jpg"));
		btnAdult.setBounds(0, 30, 150, 91);
		panel.add(btnAdult);
		
		btnAdult.setActionCommand("ShowAdultLoginGUI");
		
		btnChild.setActionCommand("ShowChildLoginGUI");
		btnChild.setFocusPainted(false);
		btnChild.setContentAreaFilled(false);
		btnChild.setBorderPainted(true);
		btnChild.setIcon(new ImageIcon("image\\student.jpg"));
		btnChild.setBounds(196, 30, 150, 91);
		panel.add(btnChild);
		
		JLabel lblOr = new JLabel("or");
		lblOr.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblOr.setBounds(168, 58, 28, 30);
		panel.add(lblOr);
		
		JLabel lblAreYou = new JLabel("Are you...");
		lblAreYou.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAreYou.setBounds(42, 24, 182, 35);
		contentPane.add(lblAreYou);
		
		
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
	public void addController(IController controller)
	{
		if (AdultController.class==controller.getClass())
		{
			btnAdult.addActionListener((ActionListener)controller);
		}else
		{
			btnChild.addActionListener((ActionListener)controller);
		}
		
	}

	@Override
	public void addModel(String key, IModel model)//CHANGE
	{
		models.put(key, model);
	}

	@Override
	public void updateView(String key,IModel model)//CHANGE
	{
		models.put(key, model);
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
