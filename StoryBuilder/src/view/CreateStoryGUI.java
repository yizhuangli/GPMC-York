package view;

import interfaces.IController;
import interfaces.IModel;
import interfaces.IView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.naming.InitialContext;
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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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

import model.Adult;
import model.Feedback;
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
public class CreateStoryGUI extends JFrame implements IView {

	SpellCheckers sc = new SpellCheckers();
	private JPanel contentPane;
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
	//private int viewpress=0; //num of preview press
	
	private String msg = "ok";//indicate if a new page can be created.
	private JButton btnAdd;
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
	JCheckBox chckbxIsSampleStory;
	JCheckBox chckbxCollectFeedback;
	JLabel sampleL;
	JPanel panel;

	private Map<String, IModel> models =new TreeMap<String, IModel>();
	private Story creatingStory;
	Map<String, File> files=new TreeMap<String,File>();
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					CreateStoryGUI frame = new CreateStoryGUI();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public void init()
	{

		pageNum=0;
		color = new Color (0, 0, 0); //background color
		color1 = new Color (0, 0, 0); //font color
		hex = "#FFFFFF"; //convert color to hex format
		hex1 = "#000000";
		font = new Font ("Tahoma", Font.PLAIN, 10);
		fontstyle="Tahoma";
		fontsize=20;
		
		sentencearray.get(0).setText("");
		pic1.get(0).setText("");
		pic.get(0).setText("");
		sound.get(0).setText("");
		
		textField.setText("");
		tabbedPane.removeAll();
		panel.setBackground(Color.decode(hex));
		sampleL.setText("");
		sampleL.setFont(font);
		sampleL.setForeground(color1);
		chckbxCollectFeedback.setSelected(false);
		chckbxIsSampleStory.setSelected(false);
		areaarray.clear();
		pic1.clear();
		pic.clear();
		sound.clear();
		

		soundbtn.clear();
		picbtn.clear();
		picbtn1.clear();

		createTab();
	}
	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CreateStoryGUI() {
		setTitle("Create Story");
		//setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 521, 691);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setForeground(Color.RED);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCreateNewStory = new JLabel("Create new Story");
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
			chckbxCollectFeedback.setBounds(149, 195, 139, 20);
			panel_1.add(chckbxCollectFeedback);
			
			chckbxIsSampleStory = new JCheckBox("Is sample story?");
			chckbxIsSampleStory.setBounds(291, 194, 130, 23);
			panel_1.add(chckbxIsSampleStory);
			
			JPanel panel_2 = new JPanel();
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
			
			panelarray.add(new JPanel());
			panelarray.get(0).setLayout(null);
			
			sentencearray.add(new JLabel("Sentence"));
			sentencearray.get(0).setFont(new Font("Tahoma", Font.BOLD, 12));
			sentencearray.get(0).setBounds(48, 11, 88, 14);
			panelarray.get(0).add(sentencearray.get(0));
			
			soundlbl.add(new JLabel("Sound"));
			soundlbl.get(0).setFont(new Font("Tahoma", Font.BOLD, 12));
			soundlbl.get(0).setBounds(48, 67, 55, 14);
			panelarray.get(0).add(soundlbl.get(0));
			
			piclbl.add(new JLabel("Picture"));
			piclbl.get(0).setFont(new Font("Tahoma", Font.BOLD, 12));
			piclbl.get(0).setBounds(48, 97, 68, 14);
			panelarray.get(0).add(piclbl.get(0));
			
			scrollarray.add(new JScrollPane());
			scrollarray.get(0).setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollarray.get(0).setBounds(134, 11, 171, 45);
			panelarray.get(0).add(scrollarray.get(0));

			areaarray.add(new JTextArea());
			areaarray.get(0).setLineWrap(true);
			scrollarray.get(0).setViewportView(areaarray.get(0));
			sc.check(areaarray.get(0)); //do spell check
			
			sound.add(new JLabel(""));
			sound.get(0).setBounds(249, 64, 121, 23);
			panelarray.get(0).add(sound.get(0));
			
			pic.add(new JLabel(""));
			pic.get(0).setBounds(249, 94, 121, 23);
			panelarray.get(0).add(pic.get(0));
			
			pic1.add(new JLabel(""));
			pic1.get(0).setBounds(249, 120, 121, 23);
			panelarray.get(0).add(pic1.get(0));
			
			
			String pagename="Page"+pageNum;
			tabbedPane.addTab(pagename, null,panelarray.get(0),null);
			tabbedPane.setSelectedIndex(0);
			
			btnAdd = new JButton("Add");
			btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnAdd.setBorderPainted(false);
			btnAdd.setContentAreaFilled(false);
			btnAdd.addActionListener(new ActionListener() {
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
			
			btnAdd.setIcon(new ImageIcon("image\\edit1.png"));
			btnAdd.setVerticalTextPosition(SwingConstants.BOTTOM);
			btnAdd.setBounds(10, 11, 77, 23);
			panel_2.add(btnAdd);
			//btnAdd.setActionCommand("CreatePage");
			
			btnPreview = new JButton("Preview");
//			btnPreview.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent arg0) {
//					viewpress++;
//				}
//			});
			btnPreview.setBorderPainted(false);
			btnPreview.setContentAreaFilled(false);
			btnPreview.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnPreview.setIcon(new ImageIcon("image\\preview.png"));
			btnPreview.setBounds(321, 11, 100, 23);
			panel_2.add(btnPreview);
			btnPreview.setActionCommand("Preview");
			
			btnNewButton_1 = new JButton("OK");
			btnNewButton_1.setIcon(new ImageIcon("image\\ok.png"));
			btnNewButton_1.setBounds(139, 612, 89, 23);
			contentPane.add(btnNewButton_1);
			btnNewButton_1.setActionCommand("CreateStory");
			
			btnCancel = new JButton("Cancel");
			btnCancel.setIcon(new ImageIcon("image\\remove.png"));
			btnCancel.setBounds(251, 612, 103, 23);
			btnCancel.setActionCommand("CreateStoryGUICancel");
			contentPane.add(btnCancel);
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
		System.out.println("pagenum="+pageNum);
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
		picbtn1.get(pageNum-1).setVisible(false);
		System.out.println("picbtn1.get("+pageNum+"-1).setVisible(false)");
		
		panelarray.get(pageNum-1).add(picbtn1.get(pageNum-1));
		
		
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
				//System.out.println("soundbtn filechooser");
				JFileChooser jfc = new javax.swing.JFileChooser(); 
				jfc.setCurrentDirectory(new File("sounds"));
				//File file = null;
				if(JFileChooser.APPROVE_OPTION==jfc.showOpenDialog(null) ) {
//					file = jfc.getSelectedFile();
//					sound.get(pageNum-1).setText(file.getName());
//					System.out.println(file.getName());
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
				System.out.println("picbtn:filechooser");
				JFileChooser jfc = new javax.swing.JFileChooser(); 
				jfc.setCurrentDirectory(new File("story_image"));
				//File file = null;
				if(JFileChooser.APPROVE_OPTION==jfc.showOpenDialog(null) ) {
//					file = jfc.getSelectedFile();
//					pic.get(pageNum-1).setText(file.getName());
//					System.out.println(file.getName());
//					picbtn1.get(pageNum-1).setVisible(true);
					File pic1File;
					pic1File = jfc.getSelectedFile();
					String fix=pic1File.getName();
					fix=fix.substring(fix.lastIndexOf("."));
					if(!fix.equalsIgnoreCase(".jpg") &&!fix.equalsIgnoreCase(".png")&&!fix.equalsIgnoreCase(".gif"))
					{
						pic1File=null;
						picbtn1.get(pageNum-1).setVisible(false);
						System.out.println("picbtn1.get("+pageNum+"-1).setVisible(false)");
						showMessage("Error", "File Format is wrong. You can choose .jpg, .png or .gif.",JOptionPane.ERROR_MESSAGE);
					}else {
						pic.get(pageNum-1).setText(pic1File.getName());
						picbtn1.get(pageNum-1).setVisible(true);
						System.out.println("picbtn1.get("+pageNum+"-1).setVisible(true)");
						files.put("pic1File"+(pageNum-1), pic1File);
						//showMessage("Info", "File selected.",JOptionPane.INFORMATION_MESSAGE);
					}
				}
				
			}
		});
		
		picbtn1.get(pageNum-1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("pic1btn:filechooser:"+(pageNum-1));
				JFileChooser jfc = new javax.swing.JFileChooser(); 
				jfc.setCurrentDirectory(new File("story_image"));
				//File file = null;
				if(JFileChooser.APPROVE_OPTION==jfc.showOpenDialog(null) ) {
//					file = jfc.getSelectedFile();
//					pic1.get(pageNum-1).setText(file.getName());
//					System.out.println(file.getName());
					
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
	}
	
	

	/**
	 * get story's title
	 */
	
	public String getTitle(){
		return textField.getText();
	}
	
	public int getPageNum(){
		return pageNum;
	}
	
	public String getBackgroundColor(){
		return hex;
	}
	
	public String getFontColor(){
		return hex1;
	}
	
	public int getFontsize(){
		return fontsize;
	}
	public String getFontstyle(){
		return fontstyle;
	}
	
	public Boolean getNeedFeedback(){
		return needFeedback;
		
	}
	
	public String getSentence(int num){
		return areaarray.get(num).getText();
	}
	
	public String getSound(int num){
		return sound.get(num).getText();
	}
	
	public String getPic1(int num){
		return pic.get(num).getText();
	}
	public String getPic2(int num){
		return pic1.get(num).getText();
	}
	
//	public int getViewPressed(){
//		return viewpress;
//	}
	public Story getCreatingStory()
	{
		Adult adult=(Adult)models.get("adultModel");
		creatingStory=new Story(adult.getID(), textField.getText(), hex, fontstyle, fontsize, hex1, needFeedback,chckbxIsSampleStory.isSelected(),0);
		//get page
		PagesCollection pcCollection=new PagesCollection();
		for (int i = 0; i < pageNum; i++)
		{
			//Page page=new Page(null, areaarray.get(i).getText(), sound.get(i).getText(), pic.get(i).getText(), pic1.get(i).getText(), pageNum+"");
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
			System.out.println("soundpath="+soundPath+",pic1path="+pic1Path+",pic2path="+pic2Path);
			Page page=new Page(null, areaarray.get(i).getText(), soundPath, pic1Path, pic2Path, pageNum+"");
			pcCollection.addPage(page);
		}
		creatingStory.setContent(pcCollection);
		
		return creatingStory;
	}
	/**
	 * show the dialogue 
	 * @param message
	 */
//	public void Message(String message){
//		msg = message;
//		System.out.println(message+"in create GUI");
//		JOptionPane.showMessageDialog(null, message);
//	}
public Map<String, File> getFiles()
{
	return files;
}
	@Override
	public void addController(IController controller) {
		btnNewButton_1.addActionListener((ActionListener)controller);  //ok button
		//btnAdd.addActionListener((ActionListener)controller);
		btnPreview.addActionListener((ActionListener)controller);
		btnCancel.addActionListener((ActionListener)controller);
		
	}

	@Override
	public void addModel(String key, IModel model)
	{
		models.put(key, model);
	}

	@Override
	public void updateView(String key, IModel model) {
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
		init();
	}
}
