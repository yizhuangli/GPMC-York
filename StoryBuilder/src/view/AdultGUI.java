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
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JList;
import javax.swing.JToolBar;
import javax.swing.JDesktopPane;
import java.awt.ScrollPane;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.border.TitledBorder;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.AbstractListModel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JFileChooser;


import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.ListSelectionModel;
import javax.swing.JRadioButton;

import model.Adult;
import model.Child;
import model.DataModel;
import model.Feedback;
import model.Story;

public class AdultGUI extends JFrame implements IView {

	private JPanel contentPane;
	private JTextField txtName;
	private JPasswordField txtPassword;
	private DefaultListModel md1; //story list
	private DefaultListModel md2; //feedback story list
	private DefaultListModel md3; //unassigned children list
	private DefaultListModel md4; //feedback child list
	private JButton btnCreate,btnEdit,btnDelete,btnRead,btnAssign,btnOk;
	private JList storylist;
	private JTextPane txtpnTitleXxxxxx; //store the sotry info
	private JTextArea summaryTP; //store the feedback summary
	final JList lstUnassignedChildren;
	JRadioButton rdbtnAll;
	JRadioButton rdbtnMyStories;
	JRadioButton rdbtnSample;
	JMenuItem mntmGuideline;
	
	private ArrayList<Story> stories = new ArrayList<Story>();
	private ArrayList<Story> allstories = new ArrayList<Story>();
	//ArrayList<Child> children = new ArrayList<Child>();
	//ArrayList<Child> unassigns = new ArrayList<Child>();

	private int selectStory=-1; //story list
	private int selectChild=-1; //child list in assign
	private int selectStoryFeed = -1;//feedback story list
	private int selectChildFeed = -1;//feedback child list

	//dataController dc = new dataController();
	
	File photo;
	private Map<String, IModel> models =new TreeMap<String, IModel>();
	private JTextField txtAge;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AdultGUI frame = new AdultGUI();
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
	public AdultGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 658, 514);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnGuideline = new JMenu("View Guideline");
		menuBar.add(mnGuideline);
		
		mntmGuideline = new JMenuItem("Guideline");
		mntmGuideline.setActionCommand("ViewGuideline");
		mnGuideline.add(mntmGuideline);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(43, 46, 553, 394);
		contentPane.add(tabbedPane);
		
		JPanel storyP = new JPanel();
		tabbedPane.addTab("Story", new ImageIcon("image\\main1.png"), storyP, null);
		storyP.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(new TitledBorder(null, "My story list", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane_1.setBounds(23, 26, 207, 139);
		storyP.add(scrollPane_1);
		
		storylist = new JList();
		storylist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		storylist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectStory = storylist.getSelectedIndex();
				if(selectStory>-1){
					updateGUI();
				}
				
			}
		});
		md1 = new DefaultListModel();
		storylist.setModel(md1);
		scrollPane_1.setViewportView(storylist);
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Operations", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(23, 198, 207, 130);
		storyP.add(panel_1);
		panel_1.setLayout(null);
		
		btnCreate = new JButton("Create");
		btnCreate.setBounds(9, 78, 89, 23);
		panel_1.add(btnCreate);
		btnCreate.setActionCommand("ShowCreateGUI");
		
		btnEdit = new JButton("Edit");
		btnEdit.setBounds(108, 30, 89, 23);
		panel_1.add(btnEdit);
		btnEdit.setActionCommand("ShowEditGUI");
		
		btnDelete = new JButton("Delete");
		btnDelete.setBounds(108, 78, 89, 23);
		btnDelete.setActionCommand("Delete");
		panel_1.add(btnDelete);
		
		btnRead = new JButton("Read");
		btnRead.setBounds(10, 30, 89, 23);
		panel_1.add(btnRead);
		btnRead.setActionCommand("ReadStory");
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBorder(new TitledBorder(null, "Story info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane_2.setBounds(289, 26, 207, 139);
		storyP.add(scrollPane_2);
		
		txtpnTitleXxxxxx = new JTextPane();
		txtpnTitleXxxxxx.setEditable(false);
		txtpnTitleXxxxxx.setText("");
		scrollPane_2.setViewportView(txtpnTitleXxxxxx);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Unassigned children", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(288, 198, 208, 130);
		storyP.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(21, 21, 163, 61);
		panel.add(scrollPane_3);
		
		lstUnassignedChildren = new JList();
		lstUnassignedChildren.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				selectChild = lstUnassignedChildren.getSelectedIndex();
			}
		});
		md3= new DefaultListModel();
		lstUnassignedChildren.setModel(md3);
		scrollPane_3.setViewportView(lstUnassignedChildren);
		
		btnAssign = new JButton("Assign");
		btnAssign.setBounds(61, 93, 89, 23);
		panel.add(btnAssign);
		btnAssign.setActionCommand("Assign");
		
		rdbtnAll = new JRadioButton("All");
		rdbtnAll.setSelected(true);
		rdbtnAll.setBounds(23, 160, 43, 23);
		rdbtnAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateStoryList();
			}
		});
		storyP.add(rdbtnAll);
		
		rdbtnMyStories = new JRadioButton("My Stories");
		rdbtnMyStories.setBounds(68, 160, 85, 23);
		rdbtnMyStories.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateStoryList();
			}
		});
		storyP.add(rdbtnMyStories);
		
		rdbtnSample = new JRadioButton("Sample");
		rdbtnSample.setBounds(155, 160, 75, 23);
		rdbtnSample.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateStoryList();
			}
		});
		storyP.add(rdbtnSample);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnAll);
		buttonGroup.add(rdbtnMyStories);
		buttonGroup.add(rdbtnSample);
		
		JPanel regP = new JPanel();
		tabbedPane.addTab("Register", new ImageIcon("image\\face-1.png"), regP, null);
		regP.setLayout(null);
		
		JLabel lblCreateAccountsFor = new JLabel("Create accounts for children");
		lblCreateAccountsFor.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCreateAccountsFor.setBounds(26, 11, 236, 36);
		regP.add(lblCreateAccountsFor);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblName.setBounds(136, 92, 69, 22);
		regP.add(lblName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPassword.setBounds(121, 172, 84, 22);
		regP.add(lblPassword);
		
		txtName = new JTextField();
		txtName.setBounds(238, 93, 134, 20);
		regP.add(txtName);
		txtName.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(238, 173, 134, 20);
		regP.add(txtPassword);
		
		JLabel lblPicture = new JLabel("Picture");
		lblPicture.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPicture.setBounds(136, 230, 69, 14);
		regP.add(lblPicture);
		
		JButton btnUpload = new JButton("Upload");
		btnUpload.setIcon(new ImageIcon("image\\picture.png"));
		btnUpload.setBounds(238, 226, 99, 23);
		btnUpload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser=new JFileChooser();
				chooser.setAcceptAllFileFilterUsed(false);
				if(JFileChooser.OPEN_DIALOG==chooser.showOpenDialog(null))
				{
					photo = chooser.getSelectedFile();
					String fix=photo.getName();
					fix=fix.substring(fix.lastIndexOf("."));
					if(!fix.equalsIgnoreCase(".jpg") &&!fix.equalsIgnoreCase(".png")&&!fix.equalsIgnoreCase(".gif"))
					{
						photo=null;
						showMessage("Error", "File Format is wrong. You can choose .jpg, .png or .gif.",JOptionPane.ERROR_MESSAGE);
					}else {
						showMessage("Info", "File selected.",JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		regP.add(btnUpload);
		
		btnOk = new JButton("OK");
		btnOk.setIcon(new ImageIcon("image\\ok.png"));
		btnOk.setBounds(238, 303, 89, 23);
		btnOk.setActionCommand("CreateChild");
		regP.add(btnOk);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAge.setBounds(151, 138, 54, 15);
		regP.add(lblAge);
		
		txtAge = new JTextField();
		txtAge.setBounds(238, 135, 66, 21);
		regP.add(txtAge);
		txtAge.setColumns(10);
		
		JPanel feedbackP = new JPanel();
		tabbedPane.addTab("Feedback", new ImageIcon("image\\book-1.png"), feedbackP, null);
		feedbackP.setLayout(null);
		
		JLabel lblMyStoryList = new JLabel("View feedbacks");
		lblMyStoryList.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblMyStoryList.setBounds(10, 11, 160, 50);
		feedbackP.add(lblMyStoryList);
		
		JScrollPane storySP = new JScrollPane();
		storySP.setViewportBorder(new TitledBorder(null, "Choose from story", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		storySP.setBounds(50, 72, 187, 120);
		feedbackP.add(storySP);
		
		md2 = new DefaultListModel();
		final JList list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				selectStoryFeed = list.getSelectedIndex();
				if(selectStoryFeed>-1){
					updateFeedback();
				}
			}
		});
		list.setModel(md2);
		storySP.setViewportView(list);
		
		JScrollPane childSP = new JScrollPane();
		childSP.setViewportBorder(new TitledBorder(null, "Choose from assigned child", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		childSP.setBounds(306, 72, 187, 120);
		feedbackP.add(childSP);
		
		final JList list_1 = new JList();
		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				selectChildFeed = list_1.getSelectedIndex();
				if(selectChildFeed>-1){
					updateFeedbackByChild();
				}
			}
		});
		md4 = new DefaultListModel();
		list_1.setModel(md4);
		childSP.setViewportView(list_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new TitledBorder(null, "Feedback summary", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		scrollPane.setBounds(50, 222, 444, 113);
		feedbackP.add(scrollPane);
		
		summaryTP = new JTextArea();
		summaryTP.setEditable(false);
		summaryTP.setText("Please select a story or child");
		scrollPane.setViewportView(summaryTP);
		
	
	}

	  
	  /**
	   * update feedback  summary when a child is selected.
	   */
	  public void updateFeedbackByChild(){
		  Adult adult=(Adult)models.get("adultModel");
		  ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();
		  feedbacks = adult.getFeedbacksByChild(adult.getAllChildren().get(selectChildFeed).getID());
		  String childname = adult.getAllChildren().get(selectChildFeed).getUsername();
//		  String feeling = feedbacks.get(selectChildFeed).getFeeling();
		  summaryTP.setText("");
		  if(feedbacks!=null){
			  for(int i=0;i<feedbacks.size();i++){
				  summaryTP.append("\r\n>>"+childname+"<< feels ("+feedbacks.get(i).getFeeling()+") on ("+adult.getStoryByID(feedbacks.get(i).getStoryID()).getTitle()+").");
			  }
		  }
		  else{
			  summaryTP.setText(">>"+childname+"<< did not make any feedbacks");
		  }
		  
	  }
	
	 /**
	  * update feedback summary when a story is selected
	  */
	 public void updateFeedback(){
		 Adult adult=(Adult)models.get("adultModel");
		 ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();
		 feedbacks = adult.getFeedbacksByStory(allstories.get(selectStoryFeed).getStoryID());
		 String title = allstories.get(selectStoryFeed).getTitle();
		 int readcount = allstories.get(selectStoryFeed).getReadCount();
		 int happy = 0;
		 int sad = 0;
		 int mad = 0;
		 int confused = 0;
		 
		 if(feedbacks!=null){
			 for(int i=0;i<feedbacks.size();i++){
				 if(feedbacks.get(i).getFeeling().equalsIgnoreCase("happy")){
					 happy++;
				 }
				 else if(feedbacks.get(i).getFeeling().equalsIgnoreCase("sad")){
					 sad++;
				 }
				 else if(feedbacks.get(i).getFeeling().equalsIgnoreCase("mad")){
					 sad++;
				 }
				 else if(feedbacks.get(i).getFeeling().equalsIgnoreCase("confused")){
					 confused++;
				 }
			 }
		 }
		 
		 summaryTP.setText("\t Story title ==>"+title+"\r\n \t Read count ==>"+readcount+"\r\n \t Happy: "+happy+"\t Sad: "+sad+"\r\n \t Mad: "+mad+"\t Confused: "+confused);
		 
	 }
	  
	/**
	 * update the GUI when select a story
	 */
	public void updateGUI() {
		Adult adult=(Adult)models.get("adultModel");
		//update the story info
		String title =stories.get(selectStory).getTitle();
		int pagenums =stories.get(selectStory).getContent().getPages().size();
		txtpnTitleXxxxxx.setText("Story title: "+title+"\r\nTotal of pages: "+pagenums);
		
		//update unassigned children
		
		String storyid = stories.get(selectStory).getStoryID();
		System.out.println("story id----------------"+storyid);
		
		
		ArrayList<Child> unassigns = adult.getUnassignedChildren(storyid);
		md3.clear();
		if(unassigns!=null && !unassigns.isEmpty()){
			
			for(int i=0;i<unassigns.size();i++){
				md3.addElement(unassigns.get(i).getUsername());
			}
		}
	}

	  
	/**
	 * initialize the adult GUI
	 */
	public void initialGUI(){
		//this.stories = stories;
		Adult adult=(Adult)models.get("adultModel");
		this.setTitle("Welcome,"+adult.getUsername());
		stories=adult.getAllStories();
		rdbtnAll.setSelected(true);
		ArrayList<Child> children=adult.getAllChildren();
		System.out.println("story size  "+stories.size());
		this.setTitle("Welcome, "+adult.getUsername());
		md1.clear();
		md2.clear();
		md3.clear();
		md4.clear();
		//unassigns.clear();
		if(!stories.isEmpty()){  
			
			for(int i=0;i<stories.size();i++){
				md1.addElement(stories.get(i).getTitle());
				md2.addElement(stories.get(i).getTitle());
			}
		}
		if(!children.isEmpty()){
			for(int i=0;i<children.size();i++){
				md4.addElement(children.get(i).getUsername());
			}
		}
		
	}
	
	/**
	 * get the story index from list
	 * @return
	 */
	public int getSelectStoryIndex(){
		return selectStory;
	}
	
	/**
	 * get select story id for assign
	 */
	public String getSelectStoryID(){
		Adult adult=(Adult)models.get("adultModel");
		if (selectStory!=-1)
		{
			return stories.get(selectStory).getStoryID();
		} else {
			return "-1";
		}
		
	}
	public ArrayList<String> getSelectChildID_Assign(){
		Adult adult=(Adult)models.get("adultModel");
		if (selectChild!=-1)
		{
			ArrayList<String> selChildID=new ArrayList<String>();
			for (int seleIdx : lstUnassignedChildren.getSelectedIndices())
			{
				selChildID.add(adult.getUnassignedChildren(getSelectStoryID()).get(seleIdx).getID());
			}
			return selChildID;
		} else {
			return null;
		}
		
	}
	
	/**
	 * Get child who requires registration.
	 */
	public Child getChild()
	{
		
		if(!txtName.getText().isEmpty() && !txtPassword.getText().isEmpty() && photo!=null && !txtAge.getText().isEmpty())
		{
			Child child=new Child();
			child.setUsername(txtName.getText());
			child.setPassword(txtPassword.getText());
			child.setAge(txtAge.getText());
			
			return child;
		}else {
			return null;
		}
	}

	/**
	 * Get photo file
	 */
	public File getPhoto()
	{
		return photo;
	}

	@Override
	public void addController(IController controller) {
		btnCreate.addActionListener((ActionListener)controller);
		btnEdit.addActionListener((ActionListener)controller);
		btnDelete.addActionListener((ActionListener)controller);
		btnRead.addActionListener((ActionListener)controller);
		btnAssign.addActionListener((ActionListener)controller);
		btnOk.addActionListener((ActionListener)controller);
//		storylist.addMouseListener((MouseListener)controller);
		mntmGuideline.addActionListener((ActionListener)controller);
	}
	public void updateStoryList()
	{
		allstories=((Adult)models.get("adultModel")).getAllStories();
		md2.clear();
		for(int i=0;i<allstories.size();i++){
			md2.addElement(allstories.get(i).getTitle());
		}
		
		if (rdbtnAll.isSelected())
		{
			stories=((Adult)models.get("adultModel")).getAllStories();
		}else if (rdbtnMyStories.isSelected()) {
			stories=((Adult)models.get("adultModel")).getMyStories();
		}else {
			stories=((Adult)models.get("adultModel")).getSampleStories();
		}
		
		md1.clear();
		for(int i=0;i<stories.size();i++){
			md1.addElement(stories.get(i).getTitle());
		}
		
	}
	public void updateChildrenList()
	{
		md3.clear();
		md4.clear();
		ArrayList<Child> unassigned=new ArrayList<Child>();
		if (!getSelectStoryID().equals("-1"))
		{
			unassigned=((Adult)models.get("adultModel")).getUnassignedChildren(getSelectStoryID());
		}
		
		ArrayList<Child> allchildren=((Adult)models.get("adultModel")).getAllChildren();
		for(int i=0;i<unassigned.size();i++){
			md3.addElement(unassigned.get(i).getUsername());
		}
		for(int i=0;i<allchildren.size();i++){
			md4.addElement(allchildren.get(i).getUsername());
		}
		
	}
	/**
	 * get selected story
	 * */
	public Story getSelectedStory()
	{
		if (storylist.getSelectedIndex()!=-1)
		{
			return stories.get(storylist.getSelectedIndex());
		}else {
			return null;
		}
	}
	@Override
	public void addModel(String key, IModel model) {
		models.put(key, model);
	}

	@Override
	public void updateView(String key, IModel model) {
		models.put(key, model);
		selectStory=-1;
		selectChild=-1;
		selectChildFeed=-1;
		selectStoryFeed=-1;
		updateStoryList();
		updateChildrenList();
	}

	@Override
	public void showMessage(String title, String msg, int type) {
		JOptionPane.showMessageDialog(this,msg,title,JOptionPane.OK_OPTION|type);
	}


	@Override
	public void setGUIVisible(boolean isVisible) {
		setVisible(isVisible);
		initialGUI();
	}
}
