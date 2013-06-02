package view;

import interfaces.IController;
import interfaces.IModel;
import interfaces.IView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.TitledBorder;
import javax.swing.text.html.ImageView;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import model.Page;
import model.PagesCollection;
import model.Story;

import utility.SpellCheckers;


import com.inet.jortho.FileUserDictionary;
import com.inet.jortho.SpellChecker;


import java.awt.Cursor;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class EditStoryGUI extends JFrame implements IView {

	SpellCheckers sc = new SpellCheckers();
	private JLabel lblCreateNewStory;
	private JLabel sampleL;
	private JPanel panel; //background sample panel
	private JPanel contentPane;
	private JCheckBox chckbxCollectFeedback;
	private JTextField textField;
	private Color color = new Color (0, 0, 0); //background color
	private Color color1 = new Color (0, 0, 0); //font color
	private String hex = "#FFFFFF"; //convert color to hex format
	private String hex1 = "#000000";
	private Font font = new Font ("Tahoma", Font.PLAIN, 10);
	private String fontstyle="Tahoma";
	private Boolean needFeedback = false;
	private int fontsize=20;
	private int pageNum=1;
	private int viewpress=0; //num of preview press
	
	private int readcount;
	private Story oldStory;
	private String msg = "ok";//indicate if a new page can be created.
	private JButton btnAdd1;
	private JButton btnPreview;
	private JButton btnNewButton_1; //ok button
	JButton btnCancel;
	private JTabbedPane tabbedPane;
	private ArrayList<JPanel> panelarray=new ArrayList<JPanel>();
	private ArrayList<JLabel> sentencearray=new ArrayList<JLabel>();
	private ArrayList<JLabel> soundlbl = new ArrayList<JLabel>();
	private ArrayList<JLabel> piclbl = new ArrayList<JLabel>();
	private ArrayList<JLabel> sound = new ArrayList<JLabel>();//display the selected file
	private ArrayList<JLabel> pic = new ArrayList<JLabel>(); //display the selected file
	private ArrayList<JLabel> pic1 = new ArrayList<JLabel>();//display the selected file
	private ArrayList<JButton> soundbtn = new ArrayList<JButton>();
	private ArrayList<JButton> picbtn = new ArrayList<JButton>();
	private ArrayList<JButton> picbtn1 = new ArrayList<JButton>();
	private ArrayList<JScrollPane> scrollarray = new ArrayList<JScrollPane>();
	private ArrayList<JTextArea> areaarray = new ArrayList<JTextArea>();
	JPanel panel_2;
	JButton btnSaveAsStory;
	
	private Map<String, IModel> models =new TreeMap<String, IModel>();
	private JCheckBox chckbxIsSampleStory;
	Map<String, File> files=new TreeMap<String,File>();
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					EditStoryGUI frame = new EditStoryGUI();
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
	public EditStoryGUI() {
		setTitle("Edit Story");
		//setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 521, 691);
		//center();
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setForeground(Color.RED);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		 lblCreateNewStory = new JLabel("Edit a Story");
		lblCreateNewStory.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCreateNewStory.setBounds(10, 11, 144, 37);
		contentPane.add(lblCreateNewStory);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "General info", TitledBorder.CENTER, TitledBorder.TOP, null, null));
			panel_1.setBounds(33, 59, 431, 249);
			contentPane.add(panel_1);
			panel_1.setLayout(null);
			
			JLabel lblStoryTitle = new JLabel("Story Title");
			lblStoryTitle.setBounds(10, 11, 95, 41);
			panel_1.add(lblStoryTitle);
			lblStoryTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
			
			textField = new JTextField();  //record title
			textField.setBounds(149, 21, 194, 20);
			panel_1.add(textField);
			textField.setColumns(10);
			
			//Do spell checker for story title
//			SpellCheckers sc = new SpellCheckers();
			 sc.check(textField);
			
			JLabel lblBackgroundColor = new JLabel("Background color");
			lblBackgroundColor.setBounds(10, 67, 144, 41);
			panel_1.add(lblBackgroundColor);
			lblBackgroundColor.setFont(new Font("Tahoma", Font.BOLD, 12));
			
			panel = new JPanel();
			panel.setBounds(252, 77, 20, 20);
			panel_1.add(panel);
			
			JButton btnChoose = new JButton("Choose");
			btnChoose.setBounds(147, 76, 89, 23);
			panel_1.add(btnChoose);
			
			
			
			JLabel lblFont = new JLabel("Font");
			lblFont.setBounds(10, 126, 77, 31);
			panel_1.add(lblFont);
			lblFont.setBackground(Color.BLACK);
			lblFont.setFont(new Font("Tahoma", Font.BOLD, 12));
			
			sampleL = new JLabel("");
			sampleL.setBounds(282, 71, 139, 37);
			panel_1.add(sampleL);
			
			final JComboBox comboBox = new JComboBox();
			comboBox.setBounds(149, 125, 89, 41);
			panel_1.add(comboBox);
			comboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					fontstyle=comboBox.getSelectedItem().toString();
					
					font=new Font(fontstyle,Font.PLAIN,fontsize);
					sampleL.setText("Sample");
					sampleL.setFont(font);
							
				}
			});
			comboBox.setBorder(new TitledBorder(null, "Family", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			comboBox.setModel(new DefaultComboBoxModel(new String[] {"Tahoma", "Arial", "Calibri", "Raavi", "SimHei", "Forte"}));
			
			final JComboBox comboBox_1 = new JComboBox();
			comboBox_1.setBounds(239, 125, 77, 41);
			panel_1.add(comboBox_1);
			comboBox_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					fontsize=Integer.parseInt(comboBox_1.getSelectedItem().toString());
					font=new Font(fontstyle,font.PLAIN,fontsize);
					sampleL.setText("Sample");
					sampleL.setFont(font);
				}
			});
			comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"20", "22", "24", "26", "28", "30", "32", "34"}));
			comboBox_1.setBorder(new TitledBorder(null, "Size", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			
			JButton btnColor = new JButton("Color");
			btnColor.setBounds(320, 141, 89, 23);
			panel_1.add(btnColor);
			
			chckbxCollectFeedback = new JCheckBox("Collect feedback?");
			chckbxCollectFeedback.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(chckbxCollectFeedback.isSelected()){
						needFeedback = true;
					}
					else{
						needFeedback = false;
					}
					System.out.println(needFeedback);
				}
			});
			chckbxCollectFeedback.setBounds(149, 195, 131, 20);
			panel_1.add(chckbxCollectFeedback);
			
			chckbxIsSampleStory = new JCheckBox("Is sample story?");
			chckbxIsSampleStory.setBounds(282, 194, 139, 23);
			panel_1.add(chckbxIsSampleStory);
			
			panel_2 = new JPanel();
			panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Page", TitledBorder.CENTER, TitledBorder.TOP, null, null));
			panel_2.setBounds(33, 339, 431, 255);
			contentPane.add(panel_2);
			panel_2.setLayout(null);
			
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setBounds(20, 45, 385, 173);
			panel_2.add(tabbedPane);
			
//			final JPanel panel_3 = new JPanel();
//			tabbedPane.addTab("Page1", null, panel_3, null);
//			panel_3.setLayout(null);
//			
//			final JLabel soundl = new JLabel("");
//			soundl.setBounds(249, 64, 121, 23);
//			panel_3.add(soundl);
//			
//			final JLabel picl = new JLabel("");
//			picl.setBounds(249, 108, 121, 23);
//			panel_3.add(picl);
//			
//			final JLabel lblSentence = new JLabel("Sentence");
//			lblSentence.setFont(new Font("Tahoma", Font.BOLD, 12));
//			lblSentence.setBounds(48, 11, 88, 14);
//			panel_3.add(lblSentence);
//			
//			JLabel lblSound = new JLabel("Sound");
//			lblSound.setFont(new Font("Tahoma", Font.BOLD, 12));
//			lblSound.setBounds(48, 67, 55, 14);
//			panel_3.add(lblSound);
//			
//			JLabel lblPicture = new JLabel("Picture");
//			lblPicture.setFont(new Font("Tahoma", Font.BOLD, 12));
//			lblPicture.setBounds(48, 107, 68, 14);
//			panel_3.add(lblPicture);
//			
//			JScrollPane scrollPane = new JScrollPane();
//			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//			scrollPane.setBounds(134, 11, 171, 45);
//			panel_3.add(scrollPane);
//			
//			JTextArea textArea = new JTextArea();
//			textArea.setLineWrap(true);
//			scrollPane.setViewportView(textArea);
//			
//			//Do spell checker for sentence
////			SpellCheckers sc1 = new SpellCheckers();
//			 sc.check(textArea);
//			
//			JButton btnUpload = new JButton("Upload");
//			btnUpload.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent arg0) {
//					JFileChooser jfc = new javax.swing.JFileChooser(); 
//					jfc.setCurrentDirectory(new File("sounds"));
//					jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
//					File file = null;
//					if(JFileChooser.APPROVE_OPTION==jfc.showOpenDialog(null) ) {
//						file = jfc.getSelectedFile();
//						soundl.setText(file.getName());
//						System.out.println(file.getName());
//					}
//				}
//			});
//			btnUpload.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//			btnUpload.setBorderPainted(false);
//			btnUpload.setContentAreaFilled(false);
//			btnUpload.setIcon(new ImageIcon("image\\music.png"));
//			btnUpload.setBounds(134, 64, 105, 23);
//			panel_3.add(btnUpload);
//			
//			JButton btnUpload_1 = new JButton("Upload");
//			btnUpload_1.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					JFileChooser jfc = new javax.swing.JFileChooser(); 
//					jfc.setCurrentDirectory(new File("story_image"));
//					File file = null;
//					if(JFileChooser.APPROVE_OPTION==jfc.showOpenDialog(null) ) {
//						file = jfc.getSelectedFile();
//						picl.setText(file.getName());
//						System.out.println(file.getName());
//					}
//				}
//			});
//			btnUpload_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//			btnUpload_1.setBorderPainted(false);
//			btnUpload_1.setContentAreaFilled(false);
//			btnUpload_1.setIcon(new ImageIcon("image\\picture.png"));
//			btnUpload_1.setBounds(134, 104, 105, 23);
//			panel_3.add(btnUpload_1);
			
		
			
			btnAdd1 = new JButton("Add");
			btnAdd1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnAdd1.setBorderPainted(false);
			btnAdd1.setContentAreaFilled(false);
			btnAdd1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(!areaarray.get(pageNum-1).getText().isEmpty() && !sound.get(pageNum-1).getText().isEmpty() && !pic.get(pageNum-1).getText().isEmpty())
					{
						createTab();
					}
					else{
						showMessage("Warning", "Please input sentence,choose at least one sound and one picture.", JOptionPane.WARNING_MESSAGE);
					}
				}
			});
			
			btnAdd1.setIcon(new ImageIcon("image\\edit1.png"));
			btnAdd1.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnAdd1.setBounds(10, 11, 77, 23);
			panel_2.add(btnAdd1);
			//btnAdd1.setActionCommand("CreatePage111");
			
			btnPreview = new JButton("Preview");
			btnPreview.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					viewpress++;
				}
			});
			btnPreview.setBorderPainted(false);
			btnPreview.setContentAreaFilled(false);
			btnPreview.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnPreview.setIcon(new ImageIcon("image\\preview.png"));
			btnPreview.setBounds(321, 11, 100, 23);
			panel_2.add(btnPreview);
			btnPreview.setActionCommand("PreviewInEdit");
			
			btnNewButton_1 = new JButton("OK");
			btnNewButton_1.setIcon(new ImageIcon("image\\ok.png"));
			btnNewButton_1.setBounds(134, 612, 89, 23);
			contentPane.add(btnNewButton_1);
			btnNewButton_1.setActionCommand("EditStory");
			
			btnCancel = new JButton("Cancel");
			btnCancel.setIcon(new ImageIcon("image\\remove.png"));
			btnCancel.setBounds(233, 612, 103, 23);
			btnCancel.setActionCommand("EditStoryGUICancel");
			contentPane.add(btnCancel);
			
			btnSaveAsStory = new JButton("Save as story");
			btnSaveAsStory.setActionCommand("SaveAsStory");
			btnSaveAsStory.setBounds(339, 612, 125, 23);
			contentPane.add(btnSaveAsStory);
			btnColor.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					color1 = JColorChooser.showDialog(null, "Choose Color", color1);
					hex1 = String.format("#%06X", (0xFFFFFF&color1.getRGB()));
					sampleL.setText("Sample");
					sampleL.setFont(font);
					sampleL.setForeground(color1);
					System.out.println(color1);
				}
			});
			btnChoose.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					color = JColorChooser.showDialog(null, "Choose Color", color);
					hex = String.format("#%06X", (0xFFFFFF&color.getRGB()));
					
					
					panel.setBackground(Color.decode(hex));
					System.out.println(hex);
					System.out.println(color);
				}
			});
	}
	
	/**
	 * create new tab to add the page. When click the add page button, it will create a new panel with its relevant elements
	 */
	public void createTab(){
		pageNum++;
		panelarray.add(new JPanel());
		panelarray.get(pageNum-1).setLayout(null);
		
		sentencearray.add(new JLabel("Sentence"));
		sentencearray.get(pageNum-1).setFont(new Font("Tahoma", Font.BOLD, 12));
		sentencearray.get(pageNum-1).setBounds(48, 11, 88, 14);
		panelarray.get(pageNum-1).add(sentencearray.get(pageNum-1));
		
		soundlbl.add(new JLabel("Sound"));
		soundlbl.get(pageNum-1).setFont(new Font("Tahoma", Font.BOLD, 12));
		soundlbl.get(pageNum-1).setBounds(48, 67, 55, 14);
		panelarray.get(pageNum-1).add(soundlbl.get(pageNum-1));
		
		piclbl.add(new JLabel("Picture"));
		piclbl.get(pageNum-1).setFont(new Font("Tahoma", Font.BOLD, 12));
		piclbl.get(pageNum-1).setBounds(48, 97, 68, 14);
		panelarray.get(pageNum-1).add(piclbl.get(pageNum-1));
		
		scrollarray.add(new JScrollPane());
		scrollarray.get(pageNum-1).setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollarray.get(pageNum-1).setBounds(134, 11, 171, 45);
		panelarray.get(pageNum-1).add(scrollarray.get(pageNum-1));

		areaarray.add(new JTextArea());
		areaarray.get(pageNum-1).setLineWrap(true);
		scrollarray.get(pageNum-1).setViewportView(areaarray.get(pageNum-1));
		sc.check(areaarray.get(pageNum-1)); //do spell check
		
		soundbtn.add(new JButton("Upload"));
		soundbtn.get(pageNum-1).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		soundbtn.get(pageNum-1).setBorderPainted(false);
		soundbtn.get(pageNum-1).setContentAreaFilled(false);
		soundbtn.get(pageNum-1).setIcon(new ImageIcon("image\\music.png"));
		soundbtn.get(pageNum-1).setBounds(134, 64, 105, 23);
		panelarray.get(pageNum-1).add(soundbtn.get(pageNum-1));
		
		picbtn.add(new JButton("Upload"));
		picbtn.get(pageNum-1).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		picbtn.get(pageNum-1).setBorderPainted(false);
		picbtn.get(pageNum-1).setContentAreaFilled(false);
		picbtn.get(pageNum-1).setIcon(new ImageIcon("image\\picture.png"));
		picbtn.get(pageNum-1).setBounds(134, 94, 105, 23);
		panelarray.get(pageNum-1).add(picbtn.get(pageNum-1));
		
		picbtn1.add(new JButton("Upload"));
		picbtn1.get(pageNum-1).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		picbtn1.get(pageNum-1).setBorderPainted(false);
		picbtn1.get(pageNum-1).setContentAreaFilled(false);
		picbtn1.get(pageNum-1).setIcon(new ImageIcon("image\\picture.png"));
		picbtn1.get(pageNum-1).setBounds(134, 120, 105, 23);
		panelarray.get(pageNum-1).add(picbtn1.get(pageNum-1));
		picbtn1.get(pageNum-1).setVisible(false);
		
		sound.add(new JLabel(""));
		sound.get(pageNum-1).setBounds(249, 64, 121, 23);
		panelarray.get(pageNum-1).add(sound.get(pageNum-1));
		
		pic.add(new JLabel(""));
		pic.get(pageNum-1).setBounds(249, 94, 121, 23);
		panelarray.get(pageNum-1).add(pic.get(pageNum-1));
		
		pic1.add(new JLabel(""));
		pic1.get(pageNum-1).setBounds(249, 120, 121, 23);
		panelarray.get(pageNum-1).add(pic1.get(pageNum-1));
		
		soundbtn.get(pageNum-1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new javax.swing.JFileChooser(); 
				jfc.setCurrentDirectory(new File("sounds"));
				//File file = null;
				if(JFileChooser.APPROVE_OPTION==jfc.showOpenDialog(null) ) {
					File soundFile = jfc.getSelectedFile();
					String fix=soundFile.getName();
					fix=fix.substring(fix.lastIndexOf("."));
					if(!fix.equalsIgnoreCase(".wav"))
					{
						soundFile=null;
						showMessage("Error", "File Format is wrong. You can choose .wav.",JOptionPane.ERROR_MESSAGE);
					}else {
						sound.get(pageNum-1).setText(soundFile.getName());
						files.put("soundFile"+(pageNum-1), soundFile);
						//showMessage("Info", "File selected.",JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
			
		});
		
		picbtn.get(pageNum-1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new javax.swing.JFileChooser(); 
				jfc.setCurrentDirectory(new File("story_image"));
				//File file = null;
				if(JFileChooser.APPROVE_OPTION==jfc.showOpenDialog(null) ) {
					File pic1File;
					pic1File = jfc.getSelectedFile();
					String fix=pic1File.getName();
					fix=fix.substring(fix.lastIndexOf("."));
					if(!fix.equalsIgnoreCase(".jpg") &&!fix.equalsIgnoreCase(".png")&&!fix.equalsIgnoreCase(".gif"))
					{
						pic1File=null;
						showMessage("Error", "File Format is wrong. You can choose .jpg, .png or .gif.",JOptionPane.ERROR_MESSAGE);
					}else {
						pic.get(pageNum-1).setText(pic1File.getName());
						picbtn1.get(pageNum-1).setVisible(true);
						files.put("pic1File"+(pageNum-1), pic1File);
						//showMessage("Info", "File selected.",JOptionPane.INFORMATION_MESSAGE);
					}
				}
				
			}
		});
		
		picbtn1.get(pageNum-1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new javax.swing.JFileChooser(); 
				jfc.setCurrentDirectory(new File("story_image"));
				File file = null;
				if(JFileChooser.APPROVE_OPTION==jfc.showOpenDialog(null) ) {
					File pic2File = jfc.getSelectedFile();
					String fix=pic2File.getName();
					fix=fix.substring(fix.lastIndexOf("."));
					if(!fix.equalsIgnoreCase(".jpg") &&!fix.equalsIgnoreCase(".png")&&!fix.equalsIgnoreCase(".gif"))
					{
						pic2File=null;
						showMessage("Error", "File Format is wrong. You can choose .jpg, .png or .gif.",JOptionPane.ERROR_MESSAGE);
					}else {
						pic1.get(pageNum-1).setText(pic2File.getName());
						files.put("pic2File"+(pageNum-1), pic2File);
						//showMessage("Info", "File selected.",JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		
		String pagename="Page"+pageNum;
		tabbedPane.addTab(pagename, null,panelarray.get(pageNum-1),null);
		tabbedPane.setSelectedIndex(pageNum-1);
		/*
		panelarray[pageNum-1] = new JPanel();
		panelarray[pageNum-1].setLayout(null);
		
		sentencearray[pageNum-1]= new JLabel("Sentence");
		sentencearray[pageNum-1].setFont(new Font("Tahoma", Font.BOLD, 12));
		sentencearray[pageNum-1].setBounds(48, 11, 88, 14);
		panelarray[pageNum-1].add(sentencearray[pageNum-1]);
		
		soundlbl[pageNum-1]= new JLabel("Sound");
		soundlbl[pageNum-1].setFont(new Font("Tahoma", Font.BOLD, 12));
		soundlbl[pageNum-1].setBounds(48, 67, 55, 14);
		panelarray[pageNum-1].add(soundlbl[pageNum-1]);
		
		piclbl[pageNum-1]= new JLabel("Picture");
		piclbl[pageNum-1].setFont(new Font("Tahoma", Font.BOLD, 12));
		piclbl[pageNum-1].setBounds(48, 97, 68, 14);
		panelarray[pageNum-1].add(piclbl[pageNum-1]);
		
		scrollarray.get(pageNum-1) = new JScrollPane();
		scrollarray.get(pageNum-1).setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollarray.get(pageNum-1).setBounds(134, 11, 171, 45);
		panelarray[pageNum-1].add(scrollarray.get(pageNum-1));

		areaarray.get(pageNum-1) = new JTextArea();
		areaarray.get(pageNum-1).setLineWrap(true);
		scrollarray.get(pageNum-1).setViewportView(areaarray.get(pageNum-1));
		sc.check(areaarray.get(pageNum-1)); //do spell check
		
		soundbtn[pageNum-1] = new JButton("Upload");
		soundbtn[pageNum-1].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		soundbtn[pageNum-1].setBorderPainted(false);
		soundbtn[pageNum-1].setContentAreaFilled(false);
		soundbtn[pageNum-1].setIcon(new ImageIcon("image\\music.png"));
		soundbtn[pageNum-1].setBounds(134, 64, 105, 23);
		panelarray[pageNum-1].add(soundbtn[pageNum-1]);
		
		picbtn.get(pageNum-1) = new JButton("Upload");
		picbtn.get(pageNum-1).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		picbtn.get(pageNum-1).setBorderPainted(false);
		picbtn.get(pageNum-1).setContentAreaFilled(false);
		picbtn.get(pageNum-1).setIcon(new ImageIcon("image\\picture.png"));
		picbtn.get(pageNum-1).setBounds(134, 94, 105, 23);
		panelarray[pageNum-1].add(picbtn.get(pageNum-1));
		
		picbtn1.get(pageNum-1) = new JButton("Upload");
		picbtn1.get(pageNum-1).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		picbtn1.get(pageNum-1).setBorderPainted(false);
		picbtn1.get(pageNum-1).setContentAreaFilled(false);
		picbtn1.get(pageNum-1).setIcon(new ImageIcon("image\\picture.png"));
		picbtn1.get(pageNum-1).setBounds(134, 120, 105, 23);
		panelarray[pageNum-1].add(picbtn1.get(pageNum-1));
		picbtn1.get(pageNum-1).setVisible(false);
		
		sound[pageNum-1] = new JLabel("");
		sound[pageNum-1].setBounds(249, 64, 121, 23);
		panelarray[pageNum-1].add(sound[pageNum-1]);
		
		pic[pageNum-1] = new JLabel("");
		pic[pageNum-1].setBounds(249, 94, 121, 23);
		panelarray[pageNum-1].add(pic[pageNum-1]);
		
		pic1[pageNum-1] = new JLabel("");
		pic1[pageNum-1].setBounds(249, 120, 121, 23);
		panelarray[pageNum-1].add(pic1[pageNum-1]);
		
		soundbtn[pageNum-1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new javax.swing.JFileChooser(); 
				jfc.setCurrentDirectory(new File("sounds"));
				File file = null;
				if(JFileChooser.APPROVE_OPTION==jfc.showOpenDialog(null) ) {
					file = jfc.getSelectedFile();
					sound[pageNum-1].setText(file.getName());
					System.out.println(file.getName());
				}
			}
			
		});
		
		picbtn.get(pageNum-1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new javax.swing.JFileChooser(); 
				jfc.setCurrentDirectory(new File("story_image"));
				File file = null;
				if(JFileChooser.APPROVE_OPTION==jfc.showOpenDialog(null) ) {
					file = jfc.getSelectedFile();
					pic[pageNum-1].setText(file.getName());
					System.out.println(file.getName());
					picbtn1.get(pageNum-1).setVisible(true);
				}
				
			}
		});
		
		picbtn1.get(pageNum-1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new javax.swing.JFileChooser(); 
				jfc.setCurrentDirectory(new File("story_image"));
				File file = null;
				if(JFileChooser.APPROVE_OPTION==jfc.showOpenDialog(null) ) {
					file = jfc.getSelectedFile();
					pic1[pageNum-1].setText(file.getName());
					System.out.println(file.getName());
				}
			}
		});
		
		String pagename="Page"+pageNum;
		tabbedPane.addTab(pagename, null,panelarray[pageNum-1],null);
		tabbedPane.setSelectedIndex(pageNum-1);
		*/
	}
	
	public Story getEditingStory()
	{
		PagesCollection pcCollection=new PagesCollection();
		System.out.println("areaarray.length="+areaarray.size());
		for (int i = 0; i < pageNum; i++)
		{
			Page p;
			String soundPath="",pic1Path="",pic2Path="";
			if (files.get("soundFile"+i)!=null) {
				soundPath=files.get("soundFile"+i).getAbsolutePath();
			}
			if (files.get("pic1File"+i)!=null) {
				pic1Path=files.get("pic1File"+i).getAbsolutePath();
			}	
			if (files.get("pic2File"+i)!=null && files.get("pic2File"+i).getName()!="null") {
				pic2Path=files.get("pic2File"+i).getAbsolutePath();
			}
			
			if (i<oldStory.getContent().getPages().size())
			{
				//edit page
				p=new Page(oldStory.getContent().getPages().get(i).getPageID(),oldStory.getStoryID(),areaarray.get(i).getText(), soundPath, pic1Path, pic2Path, pageNum+"");
			}else {
				//new page
				p=new Page(oldStory.getStoryID(),areaarray.get(i).getText(), soundPath, pic1Path, pic2Path, pageNum+"");
			}
			pcCollection.addPage(p);
		}
		Story s=new Story(oldStory.getStoryID(), oldStory.getAdultID(), textField.getText(), hex, fontstyle, fontsize, hex1, needFeedback,chckbxIsSampleStory.isSelected(), readcount, pcCollection);
		return s;
	}
	/**
	 * make the frame in the middle of screen
	 */
//	public void center() {
//	    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
//	    Dimension us = getSize();
//	    int x = (screen.width - us.width) / 2;
//	    int y = (screen.height - us.height) / 2;
//	    setLocation(x, y);
//	  }
//	
//	public String getStoryID(){
//		return storyid;
//	}
//	public String getAdultID(){
//		return adultid;
//	}
//	public int getCount(){
//		return readcount;
//	}
	
//	/**
//	 * get story's title
//	 */
//	public String getTitle(){
//		return textField.getText();
//	}
//	
//	public int getPageNum(){
//		return pageNum;
//	}
//	
//	public String getBackgroundColor(){
//		return hex;
//	}
//	
//	public String getFontColor(){
//		return hex1;
//	}
//	
//	public int getFontsize(){
//		return fontsize;
//	}
//	public String getFontstyle(){
//		return fontstyle;
//	}
//	
//	public Boolean getNeedFeedback(){
//		return needFeedback;
//		
//	}
//	
//	public String getSentence(int num){
//		return areaarray[num].getText();
//	}
//	
//	public String getSound(int num){
//		return sound[num].getText();
//	}
//	
//	public String getPic1(int num){
//		return pic[num].getText();
//	}
//	public String getPic2(int num){
//		return pic1[num].getText();
//	}
	public int getViewPressed(){
		return viewpress;
	}
	
	/**
	 * initialize the gui
	 * @param s
	 */
	public void initialGUI(Story s){
		pageNum=s.getContent().getPages().size();
		oldStory=s;
		readcount = s.getReadCount();
		this.setTitle(s.getTitle());
		textField.setText(s.getTitle());
		hex = s.getBackground();
		hex1 = s.getColor();
		fontsize = s.getFontSize();
		pageNum = s.getContent().getPages().size();
		needFeedback = s.isNeedFeedback();
		
		if(needFeedback){
			chckbxCollectFeedback.setSelected(true);
		}else {
			chckbxCollectFeedback.setSelected(false);
		}
		if (s.isSample()) {
			chckbxIsSampleStory.setSelected(true);
			chckbxIsSampleStory.setEnabled(true);
			btnSaveAsStory.setVisible(true);
		}else {
			chckbxIsSampleStory.setSelected(false);
			chckbxIsSampleStory.setEnabled(false);
			btnSaveAsStory.setVisible(false);
		}
		
		font=new Font(fontstyle,Font.PLAIN,fontsize);
		sampleL.setText("Sample");
		sampleL.setFont(font);
		sampleL.setForeground(Color.decode(hex1));
		panel.setBackground(Color.decode(hex));
		tabbedPane.removeAll();
		soundbtn.clear();
		picbtn.clear();
		picbtn1.clear();
		for(int i = 0;i<pageNum;i++){
			final int j =i;
			panelarray.add(new JPanel());
			panelarray.get(i).setLayout(null);
			
			sentencearray.add(new JLabel("Sentence"));
			sentencearray.get(i).setFont(new Font("Tahoma", Font.BOLD, 12));
			sentencearray.get(i).setBounds(48, 11, 88, 14);
			panelarray.get(i).add(sentencearray.get(i));
			
			soundlbl.add(new JLabel("Sound"));
			soundlbl.get(i).setFont(new Font("Tahoma", Font.BOLD, 12));
			soundlbl.get(i).setBounds(48, 67, 55, 14);
			panelarray.get(i).add(soundlbl.get(i));
			
			piclbl.add(new JLabel("Picture"));
			piclbl.get(i).setFont(new Font("Tahoma", Font.BOLD, 12));
			piclbl.get(i).setBounds(48, 97, 68, 14);
			panelarray.get(i).add(piclbl.get(i));
			
			scrollarray.add(new JScrollPane());
			scrollarray.get(i).setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollarray.get(i).setBounds(134, 11, 171, 45);
			panelarray.get(i).add(scrollarray.get(i));

			areaarray.add(new JTextArea());
			areaarray.get(i).setLineWrap(true);
			scrollarray.get(i).setViewportView(areaarray.get(i));
			areaarray.get(i).setText( s.getContent().getPages().get(i).getSentece()) ;
			sc.check(areaarray.get(i)); //do spell check
			
			soundbtn.add(new JButton("Upload"));
			soundbtn.get(i).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			soundbtn.get(i).setBorderPainted(false);
			soundbtn.get(i).setContentAreaFilled(false);
			soundbtn.get(i).setIcon(new ImageIcon("image\\music.png"));
			soundbtn.get(i).setBounds(134, 64, 105, 23);
			panelarray.get(i).add(soundbtn.get(i));
			
			picbtn.add(new JButton("Upload"));
			picbtn.get(i).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			picbtn.get(i).setBorderPainted(false);
			picbtn.get(i).setContentAreaFilled(false);
			picbtn.get(i).setIcon(new ImageIcon("image\\picture.png"));
			picbtn.get(i).setBounds(134, 94, 105, 23);
			panelarray.get(i).add(picbtn.get(i));
			
			picbtn1.add(new JButton("Upload"));
			picbtn1.get(i).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			picbtn1.get(i).setBorderPainted(false);
			picbtn1.get(i).setContentAreaFilled(false);
			picbtn1.get(i).setIcon(new ImageIcon("image\\picture.png"));
			picbtn1.get(i).setBounds(134, 120, 105, 23);
			panelarray.get(i).add(picbtn1.get(i));
			
			sound.add(new JLabel(""));
			sound.get(i).setBounds(249, 64, 121, 23);
			panelarray.get(i).add(sound.get(i));
			sound.get(i).setText(s.getContent().getPages().get(i).getSound());
			files.put("soundFile"+i, new File("sounds\\"+s.getContent().getPages().get(i).getSound()));
			
			pic.add(new JLabel(""));
			pic.get(i).setBounds(249, 94, 121, 23);
			panelarray.get(i).add(pic.get(i));
			pic.get(i).setText(s.getContent().getPages().get(i).getPic1());
			files.put("pic1File"+i, new File("story_image\\"+s.getContent().getPages().get(i).getPic1()));
			
			pic1.add(new JLabel(""));
			pic1.get(i).setBounds(249, 120, 121, 23);
			panelarray.get(i).add(pic1.get(i));
			pic1.get(i).setText(s.getContent().getPages().get(i).getPic2());
			if (s.getContent().getPages().get(i).getPic2().equals("null")) {
				files.put("pic2File"+i, null);
			}else {
				files.put("pic2File"+i, new File("story_image\\"+s.getContent().getPages().get(i).getPic2()));
			}
			
			
			soundbtn.get(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFileChooser jfc = new javax.swing.JFileChooser(); 
					jfc.setCurrentDirectory(new File("sounds"));
					//File file = null;
					if(JFileChooser.APPROVE_OPTION==jfc.showOpenDialog(null) ) {
						File soundFile = jfc.getSelectedFile();
						String fix=soundFile.getName();
						fix=fix.substring(fix.lastIndexOf("."));
						if(!fix.equalsIgnoreCase(".wav"))
						{
							soundFile=null;
							showMessage("Error", "File Format is wrong. You can choose .wav.",JOptionPane.ERROR_MESSAGE);
						}else {
							sound.get(j).setText(soundFile.getName());
							files.put("soundFile"+j, soundFile);
							//showMessage("Info", "File selected.",JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			});
//			
			picbtn.get(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser jfc = new javax.swing.JFileChooser(); 
					jfc.setCurrentDirectory(new File("story_image"));
					//File file = null;
					if(JFileChooser.APPROVE_OPTION==jfc.showOpenDialog(null) ) {
						File pic1File;
						pic1File = jfc.getSelectedFile();
						String fix=pic1File.getName();
						fix=fix.substring(fix.lastIndexOf("."));
						if(!fix.equalsIgnoreCase(".jpg") &&!fix.equalsIgnoreCase(".png")&&!fix.equalsIgnoreCase(".gif"))
						{
							pic1File=null;
							showMessage("Error", "File Format is wrong. You can choose .jpg, .png or .gif.",JOptionPane.ERROR_MESSAGE);
						}else {
							pic.get(j).setText(pic1File.getName());
							picbtn1.get(j).setVisible(true);
							files.put("pic1File"+j, pic1File);
							//showMessage("Info", "File selected.",JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			});
			
			picbtn1.get(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser jfc = new javax.swing.JFileChooser(); 
					jfc.setCurrentDirectory(new File("story_image"));
					//File file = null;
					if(JFileChooser.APPROVE_OPTION==jfc.showOpenDialog(null) ) {
						File pic2File = jfc.getSelectedFile();
						String fix=pic2File.getName();
						fix=fix.substring(fix.lastIndexOf("."));
						if(!fix.equalsIgnoreCase(".jpg") &&!fix.equalsIgnoreCase(".png")&&!fix.equalsIgnoreCase(".gif"))
						{
							pic2File=null;
							showMessage("Error", "File Format is wrong. You can choose .jpg, .png or .gif.",JOptionPane.ERROR_MESSAGE);
						}else {
							pic1.get(j).setText(pic2File.getName());
							files.put("pic2File"+j, pic2File);
							//showMessage("Info", "File selected.",JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			});
			
			
			
			String pagename="Page"+(1+i);
			tabbedPane.addTab(pagename, null,panelarray.get(i),null);
			tabbedPane.setSelectedIndex(0);
		}
		
		
	}
	
	/**
	 * show the dialogue 
	 * @param message
	 */
//	public void Message(String message){
//		msg = message;
//		System.out.println(message+"in GUI");
//		JOptionPane.showMessageDialog(null, message);
//	}
	public Map<String, File> getFiles()
	{
		return files;
	}
	@Override
	public void addController(IController controller) {
		btnNewButton_1.addActionListener((ActionListener)controller);  //ok button
		
		//btnAdd1.addActionListener((ActionListener)controller);
		btnPreview.addActionListener((ActionListener)controller);
		btnCancel.addActionListener((ActionListener)controller);
		btnSaveAsStory.addActionListener((ActionListener)controller);
	}

	@Override
	public void addModel(String key, IModel model) {
		models.put(key, model);
	}

	@Override
	public void updateView(String key, IModel model) {
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
