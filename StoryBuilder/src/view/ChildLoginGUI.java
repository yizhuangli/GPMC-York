package view;

import interfaces.IController;
import interfaces.IModel;
import interfaces.IView;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Cursor;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.Adult;
import model.Child;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import controller.ChildController;

public class ChildLoginGUI extends JFrame implements IView {

	private int page=1,page_size=4,page_count=1;
	private String childID;
	private String password;
	
	private ChildController childController;
	private Map<String, IModel> models =new TreeMap<String, IModel>();

	private JPanel contentPane;
	private JTextField txtName;
	private JButton btnPhotos[]=new JButton[page_size];
	private JButton btnPrev;
	private JButton btnNext;
	private JPanel photoPanel;
	private JPanel promptPanel;
	private JLabel pwd = new JLabel("Password");
	private JPasswordField pwdField;
	
	/**
	 * Create the frame.
	 */
	@SuppressWarnings("serial")
	public ChildLoginGUI()
	{
		setResizable(false);
		setTitle("Child login");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 460, 354);
		this.setLocationRelativeTo(null);
		
		//Add background image 
		contentPane = new JPanel(){
		     public void paintComponent(Graphics g) {
		    	 ImageIcon img = new ImageIcon("image\\login.png");
	               //image move along the size of the frame
	                g.drawImage(img.getImage(), 0, 0, this.getSize().width,this.getSize().height,this);
	            }
		};
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblYourName = new JLabel("Your name:");
		lblYourName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblYourName.setBounds(73, 44, 94, 23);
		contentPane.add(lblYourName);
		
		txtName = new JTextField();
		txtName.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				page=1;
				updatePhotoPage();
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				page=1;
				updatePhotoPage();
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				page=1;
				updatePhotoPage();
			}
		});
		
		
		txtName.setBounds(193, 46, 112, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblYourImage = new JLabel("Your photo:");
		lblYourImage.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblYourImage.setBounds(73, 114, 98, 23);
		contentPane.add(lblYourImage);
		
		photoPanel = new JPanel();
		photoPanel.setBounds(180, 116, 146, 132);
		photoPanel.setOpaque(false);
		contentPane.add(photoPanel);
		photoPanel.setLayout(new GridLayout(3, 2, 0, 0));
		
		//Create a button array
		for(int i=0;i<btnPhotos.length;i++)
		{
			btnPhotos[i] = new JButton();
			btnPhotos[i].setName(String.valueOf(i+1));
			btnPhotos[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnPhotos[i].setBorderPainted(false);
			btnPhotos[i].setContentAreaFilled(false);
			photoPanel.add(btnPhotos[i]);
			
			btnPhotos[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JButton eventButton = (JButton) arg0.getSource();
					System.out.println(eventButton.getName());
					childID=eventButton.getName();
					prompt(); //prompt for password
					
					int a=JOptionPane.showConfirmDialog(null,promptPanel,"Enter your password",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);  
					if(a==JOptionPane.OK_OPTION){
						System.out.println("pwd:" +pwdField.getText());
						password=pwdField.getText();
						childController.act_Login();
					}
				}
			});
		}
		
		//Draw page button
		btnPrev=new JButton();
		btnPrev.setIcon(new ImageIcon("image/back2.png"));
		btnPrev.setContentAreaFilled(false);
		btnPrev.setBorderPainted(false);
		btnPrev.setBounds(150, 140, 40, 40);
		btnPrev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				page--;
				updatePhotoPage();
			}
		});
		contentPane.add(btnPrev);
		
		btnNext=new JButton();
		btnNext.setIcon(new ImageIcon("image/next2.png"));
		btnNext.setContentAreaFilled(false);
		btnNext.setBorderPainted(false);
		btnNext.setBounds(315, 140, 40, 40);
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				page++;
				updatePhotoPage();
			}
		});
		contentPane.add(btnNext);
	}
	
	/**
	 * Show message from itself or controllers.
	 * @param title
	 * @param msg
	 */
//	public void showMessage(String title,String msg)
//	{
//		JOptionPane.showMessageDialog(this,msg,title,JOptionPane.OK_OPTION);
//	}
	
	/**
	 * Update photo panel by page.
	 */
	private void updatePhotoPage()
	{
		ArrayList<Child> children;
		ArrayList<Child> pageChildren=new ArrayList<Child>();
		
		if (txtName.getText().isEmpty())
		{
			children=((Adult)models.get("adultModel")).getAllChildren();
			
		}else
		{
			children=getChildrenByName(txtName.getText());
		}
		/*
		 */
		
		//Filter by page
		updatePageCount(children.size());
		int begin=(page-1)*page_size;
		int end=begin+page_size-1;
		System.out.println("children from "+begin+" to "+end);
		for (int i = begin; i <= end; i++)
		{
			if(i<children.size())
			{
				Child c=children.get(i);
				pageChildren.add(c);
			}
		}
		//Update photoPanel
		for(int i=0;i<btnPhotos.length;i++)
		{
			if (i<pageChildren.size())
			{
				btnPhotos[i].setVisible(true);
				Child c=pageChildren.get(i);
				String url="child_image\\"+c.getPic();
				ImageIcon photoIcon=new ImageIcon(url);
				setIcon(url, btnPhotos[i]);
				btnPhotos[i].setName(c.getID());
			}else
			{
				btnPhotos[i].setVisible(false);
			}
			
		}
		updatePageButtons();
	}
	/**
	 * Adjust icon size.
	 * @param file
	 * @param iconButton
	 * @author http://blog.csdn.net/niuox/article/details/6915824
	 */
	public void setIcon(String file, JButton iconButton) {  
        ImageIcon icon = new ImageIcon(file);  
        Image temp = icon.getImage().getScaledInstance(iconButton.getWidth()-15,  
                iconButton.getHeight()-5, icon.getImage().SCALE_DEFAULT);  
        icon = new ImageIcon(temp);  
        iconButton.setIcon(icon);  
    }  
	/**
	 * Update page buttons' status
	 */
	private void updatePageButtons()
	{
		if (page>=page_count)
		{
			btnNext.setVisible(false);
		}else {
			btnNext.setVisible(true);
		}
		if (page==1)
		{
			btnPrev.setVisible(false);
		}else {
			btnPrev.setVisible(true);
		}
	}
	/**
	 * Init components and variables.
	 */
	public void init()
	{
		int total=((Adult)models.get("adultModel")).getAllChildren().size();
		updatePageCount(total);
		
		updatePhotoPage();
	}
	
	public void updatePageCount(int total)
	{
		page_count=(int)Math.ceil(total*1.0/page_size);
	}
	
	/**
	 * Get Children Information By Name.
	 * @param name
	 * @return Map<String, Child>
	 */
	private ArrayList<Child> getChildrenByName(String name)
	{
		ArrayList<Child> children = new ArrayList<Child>();
		ArrayList<Child> allChildren=((Adult)models.get("adultModel")).getAllChildren();
		for(Child c : allChildren)
		{
			if (c.getUsername().equalsIgnoreCase(name))
			{
				children.add(c);
			}
		}
		return children;
	}
    /**
     * prompt for typing password
     */
	protected void prompt()
	{
	  promptPanel = new JPanel();
	  pwdField=new JPasswordField(12); 
	  promptPanel.setLayout(new GridLayout(2,1));  
	  promptPanel.add(pwd);
	  promptPanel.add(pwdField);
	 }

	/**
	 * Getters
	 */
	public String getPassword()
	{
		return password;
	}
	public String getChildID()
	{
		return childID;
	}
	
	
	@Override
	public void addController(IController controller)
	{
		childController=(ChildController)controller;
	}

	@Override
	public void addModel(String key, IModel model)
	{
		models.put(key, model);
	}

	@Override
	public void updateView(String key, IModel model)
	{
		models.put(key, model);
		init();
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
