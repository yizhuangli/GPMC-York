package view;

import interfaces.IController;
import interfaces.IModel;
import interfaces.IView;
import utility.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import controller.ChildController;


import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.border.TitledBorder;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLayeredPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;

import model.*;

public class ChildGUI extends JFrame implements IView {

	private JPanel contentPane;
	private JPanel contentPane1;  //this panel will be printed
	JLabel lblPhoto;
	JLabel lblHello;
	JPanel fbPanel;//Feedback panel
	JButton happybtn;
	JButton madbtn;
	JButton confusebtn;
	JButton sadbtn;
	JButton playbtn;
	JButton stopbtn;
	private JLabel titleL;
	private JTextPane sentenceT;
	private JLabel picL1,picL2;
	JLayeredPane layeredPane_1;
	JLayeredPane layeredPane;
	JButton btnPrev;
	private JButton btnNext;
	JButton btnRead;
	ImageIcon img1;
	ImageIcon img2;
	JList lstStories;
	
	private Map<String, IModel> models =new TreeMap<String, IModel>();
	private ChildController childController;
	File wavFile;
	String storyID;
	int curPage=0;
	
	private String sentence;
	private TextSound textsound = new TextSound();
	PlaySounds ps = new PlaySounds();
	private Story story;//story for print
	/**
	 * Create the frame.
	 */
	public ChildGUI() {
		setTitle("Welcome");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 779, 693);
		this.setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("Print");
		menuBar.add(mnFile);
		
		JMenuItem mntmPrintAsPdf = new JMenuItem("Print as PDF");
		mntmPrintAsPdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new javax.swing.JFileChooser();  
				FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf(*.pdf)","pdf");
			    jfc.addChoosableFileFilter((javax.swing.filechooser.FileFilter) filter);
		        jfc.setFileFilter(filter);

				File saveFile=new File("");
				
				int i =-1;  //record option
				
				if(JFileChooser.APPROVE_OPTION==jfc.showSaveDialog(null) ) {
					saveFile = jfc.getSelectedFile();
		           if(!saveFile.exists()){           //file not exists
		               try {
						saveFile.createNewFile();
						i=0;
					} catch (IOException e) {
						e.printStackTrace();
					}
		           }
		           
		           else{
		        	  i =  JOptionPane.showConfirmDialog( null, saveFile.getName() + " already exists \r\nReplace it?", "File exists!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		           }
				}

				//replace the exist file
				if(i==JOptionPane.YES_OPTION){
					//PRINT THE story
					PrintPDF p = new PrintPDF();
					p.print(story,saveFile);
					
				}
				else{
					//do nothing
				}
			}
		});
		
		mnFile.add(mntmPrintAsPdf);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Page Buttons
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(129, 563, 63, 60);
		contentPane.add(layeredPane);
		
		btnPrev = new JButton("");
		btnPrev.setBounds(0, 0, 59, 55);
		layeredPane.add(btnPrev);
		btnPrev.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPrev.setIcon(new ImageIcon("image\\Back.png"));
		btnPrev.setContentAreaFilled(false);
		btnPrev.setBorderPainted(false);
		btnPrev.setToolTipText("Previous page");
		btnPrev.setName("");
		btnPrev.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				curPage--;
				updatePageContent();
				ps.play(wavFile);
			}
		});
		
		layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBounds(684, 563, 54, 60);
		contentPane.add(layeredPane_1);
		
		btnNext = new JButton("");
		btnNext.setBounds(0, 0, 53, 55);
		layeredPane_1.add(btnNext);
		btnNext.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNext.setToolTipText("Next page");
		btnNext.setContentAreaFilled(false);
		btnNext.setBorderPainted(false);
		btnNext.setIcon(new ImageIcon("image\\Next.png"));
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				curPage++;
				updatePageContent();
				ps.play(wavFile);
			}
		});
		
		contentPane1 = new JPanel();
		contentPane1.setBackground(Color.WHITE);
		contentPane1.setBounds(202, 34, 472, 589);
		contentPane.add(contentPane1);
		contentPane1.setLayout(null);
		
		sentenceT = new JTextPane();
		sentenceT.setEditable(false);
		sentenceT.setFont(new Font("Tahoma", Font.PLAIN, 15));
		sentenceT.setBounds(59, 53, 358, 55);
		contentPane1.add(sentenceT);

		picL1 = new JLabel("");
		picL1.setBorder(null);
		picL1.setIcon(img1);
		picL1.setBounds(120, 119, 255, 204);
		contentPane1.add(picL1);
		
		picL2 = new JLabel("");
		picL2.setIcon(img2);
		picL2.setBounds(120, 339, 255, 211);
		contentPane1.add(picL2);
		
		titleL = new JLabel("Please select a story.");
		titleL.setHorizontalAlignment(SwingConstants.CENTER);
		titleL.setFont(new Font("Tahoma", Font.BOLD, 18));
		titleL.setBounds(21, 22, 427, 28);
		contentPane1.add(titleL);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 76, 182, 161);
		contentPane.add(scrollPane);
		
		lstStories = new JList();
		lstStories.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstStories.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				updateStoryID(lstStories.getSelectedIndex());
				updatePageContent();
				ps.play(wavFile);
				System.out.println("waa"+wavFile);
				story = getStory();
				childController.act_Read();
			}
		});
		lstStories.setBorder(new TitledBorder(null, "My story", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
//		lstStories.addListSelectionListener(new ListSelectionListener() {
//			@Override
//			public void valueChanged(ListSelectionEvent arg0) {
//				updateStoryID(lstStories.getSelectedIndex());
//				updatePageContent();
//				ps.play(wavFile);
//				System.out.println("waa"+wavFile);
////				story = getStory();
//			}
//		});
		scrollPane.setViewportView(lstStories);
		
		fbPanel = new JPanel();
		fbPanel.setBorder(new TitledBorder(null, "Your feedback", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		fbPanel.setBounds(10, 339, 182, 175);
		contentPane.add(fbPanel);
		fbPanel.setLayout(null);
		
		happybtn = new JButton("");
		happybtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		happybtn.setContentAreaFilled(false);
		happybtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		happybtn.setIcon(new ImageIcon("image\\happy.png"));
		happybtn.setBounds(20, 31, 60, 57);
		happybtn.setActionCommand("LeaveFeedback-Happy");
		
		fbPanel.add(happybtn);
		
		madbtn = new JButton("");
		madbtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		madbtn.setIcon(new ImageIcon("image\\mad.png"));
		madbtn.setContentAreaFilled(false);
		madbtn.setBounds(90, 99, 60, 57);
		madbtn.setActionCommand("LeaveFeedback-Mad");
		fbPanel.add(madbtn);
		
		confusebtn = new JButton("");
		confusebtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		confusebtn.setContentAreaFilled(false);
		confusebtn.setIcon(new ImageIcon("image\\confuse.png"));
		confusebtn.setBounds(20, 99, 60, 57);
		confusebtn.setActionCommand("LeaveFeedback-Confused");
		fbPanel.add(confusebtn);
		
		sadbtn = new JButton("");
		sadbtn.setBounds(90, 31, 60, 57);
		fbPanel.add(sadbtn);
		sadbtn.setVerticalTextPosition(SwingConstants.BOTTOM);
		sadbtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		sadbtn.setContentAreaFilled(false);
		sadbtn.setIcon(new ImageIcon("image\\sad.png"));
		sadbtn.setActionCommand("LeaveFeedback-Sad");
		
		lblHello = new JLabel("Hello,");
		lblHello.setBounds(63, 11, 129, 60);
		contentPane.add(lblHello);
		lblHello.setFont(new Font("Tahoma", Font.BOLD, 12));
		//lblHello.setIcon(new ImageIcon("image\\face-1.png"));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Sound", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		panel_2.setBounds(10, 248, 182, 80);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		playbtn = new JButton("");
		//play the sound
		playbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ps.play(wavFile);
			}
		});
		playbtn.setBorderPainted(false);
		playbtn.setToolTipText("play");
		playbtn.setIcon(new ImageIcon("image\\play.png"));
		playbtn.setBounds(95, 24, 64, 41);
		panel_2.add(playbtn);
		
		stopbtn = new JButton("");
		//stop the sound
		stopbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ps.stop(wavFile);
			}
		});
		stopbtn.setBorderPainted(false);
		stopbtn.setToolTipText("stop");
		stopbtn.setIcon(new ImageIcon("image\\stop.png"));
		stopbtn.setBounds(21, 24, 64, 41);
		panel_2.add(stopbtn);
		
		lblPhoto = new JLabel("");
		lblPhoto.setBounds(10, 21, 44, 45);
		contentPane.add(lblPhoto);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Reader", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
		panel_1.setBounds(684, 76, 74, 81);
		contentPane.add(panel_1);
		
		btnRead = new JButton("");
		btnRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textsound.read(sentence);
			}
		});
		btnRead.setIcon(new ImageIcon("image\\sound.png"));
		btnRead.setBorderPainted(false);
		btnRead.setBounds(10, 27, 54, 31);
		panel_1.add(btnRead);
	}
	/**
	 * Update current Story's ID
	 * @param idx
	 */
	public void updateStoryID(int idx)
	{
		ChildStoriesCollection stories=((Child)models.get("childModel")).getAssignedStory();
		storyID = stories.getStories().get(idx).getStoryID();
		curPage=1;
		System.out.println("selected story id:"+storyID);
	}
	/**
	 * Initialization of the GUI.
	 */
	public void init()
	{
		Child child=(Child)(models.get("childModel"));
		lblHello.setText("Hello,"+child.getUsername());
		setIcon("child_image\\"+child.getPic(),lblPhoto);
		fbPanel.setEnabled(false);
		clearPageContent(false);
		updateStoryList();
		updatePageButtons();
		updateFeedbackPanel(false);
	}
	/**
	 * Adjust icon size.
	 * @param file
	 * @param iconButton
	 * @author http://blog.csdn.net/niuox/article/details/6915824
	 */
	public void setIcon(String file, JLabel iconButton) {  
        ImageIcon icon = new ImageIcon(file);  
        Image temp = icon.getImage().getScaledInstance(iconButton.getWidth(),  
                iconButton.getHeight(), icon.getImage().SCALE_DEFAULT);  
        icon = new ImageIcon(temp);  
        iconButton.setIcon(icon);  
    }  
	/**
	 * Update story list on the left side.
	 */
	public void updateStoryList()
	{
		ChildStoriesCollection stories=((Child)models.get("childModel")).getAssignedStory();
		DefaultListModel<String> lstModel=new DefaultListModel<String>();
		for (Story s : stories.getStories())
		{
			lstModel.addElement(s.getTitle());
		}
		lstStories.setModel(lstModel);
	}
	/**
	 * Update page content on the right side.
	 */
	public void updatePageContent()
	{
		clearPageContent(true);
		Page page;
		Story story=getStory();
		System.out.println("stories:"+story.getStoryID());
		if(story.getContent().getPages().size()>0)
		{
			Color background = Color.decode(story.getBackground());
			Color fontcolor = Color.decode(story.getColor());
			
			page=story.getContent().getPages().get(curPage-1);
			titleL.setText(getStory().getTitle());
			sentence=	page.getSentece();
			sentenceT.setText(page.getSentece());
			sentenceT.setFont(new Font(getStory().getFontStyle(),Font.PLAIN, getStory().getFontSize()));
			sentenceT.setForeground(fontcolor);
			contentPane1.setBackground(background);
			layeredPane.setPosition(btnPrev, 0);
			layeredPane_1.setPosition(btnNext, 0);
			wavFile=new File("sounds\\"+page.getSound());
			picL1.setIcon(null);
			picL2.setIcon(null);
			
			if(!page.getPic1().equalsIgnoreCase("null"))
			{
				System.out.println(page.getPic1());
				setIcon("story_image\\"+page.getPic1(), picL1);
			}
			if (!page.getPic2().equalsIgnoreCase("null"))
			{
				setIcon("story_image\\"+page.getPic2(), picL2);
			}
	
			updatePageButtons();
		}else {
			curPage=0;
			clearPageContent(false);
			updatePageButtons();
		}
	}
	/**
	 * Clear page content and format.
	 * @param isVisible
	 */
	public void clearPageContent(boolean isVisible)
	{
		titleL.setText("No more pages.");
		sentenceT.setText("");
		playbtn.setEnabled(isVisible);
		stopbtn.setEnabled(isVisible);
		picL1.setIcon(null);
		picL2.setIcon(null);
		picL1.setVisible(isVisible);
		picL1.setVisible(isVisible);
		contentPane1.setBackground(new Color(255,255,255));
		btnPrev.setVisible(isVisible);
		btnNext.setVisible(isVisible);
		btnRead.setEnabled(isVisible);
	}
	/**
	 * Update page buttons' status.
	 */
	public void updatePageButtons()
	{
		if(curPage!=0)
		{
			int page_count=0;
			page_count=getStory().getContent().getPages().size();
			
			if (curPage<=1)
			{
				btnPrev.setVisible(false);
			}else {
				btnPrev.setVisible(true);
			}
			if (curPage>=page_count)
			{
				btnNext.setVisible(false);
				updateFeedbackPanel(true);
			}else {
				btnNext.setVisible(true);
				updateFeedbackPanel(false);
			}
		}else {//Initial State
			btnPrev.setVisible(false);
			btnNext.setVisible(false);
			updateFeedbackPanel(false);
		}
	}
	/**
	 * Update feedback panel's status.
	 * @param isEnabled
	 */
	public void updateFeedbackPanel(boolean isEnabled)
	{
		if (getStory()==null || !getStory().isNeedFeedback())
		{
			isEnabled=false;
		}
		fbPanel.setEnabled(isEnabled);
		happybtn.setEnabled(isEnabled);
		madbtn.setEnabled(isEnabled);
		confusebtn.setEnabled(isEnabled);
		sadbtn.setEnabled(isEnabled);
	}
	/**
	 * Get selected story.
	 * @return
	 */
	public Story getStory()
	{
		Child child=(Child)models.get("childModel");
		for(Story s : child.getAssignedStory().getStories())
		{
			if(s.getStoryID().equals(storyID))
			{
				return s;
			}
		}
		return null;
	}
	/**
	 * Show message from itself or controllers.
	 * @param title
	 * @param msg
	 */
//	public void showMessage(String title,String msg)
//	{
//		JOptionPane.showMessageDialog(this,msg,title,JOptionPane.OK_OPTION|JOptionPane.INFORMATION_MESSAGE);
//	}
	
	@Override
	public void addController(IController controller) {
		childController=(ChildController)controller;
		happybtn.addActionListener((ActionListener)controller);
		madbtn.addActionListener((ActionListener)controller);
		confusebtn.addActionListener((ActionListener)controller);
		sadbtn.addActionListener((ActionListener)controller);
	}

	@Override
	public void addModel(String key, IModel model)
	{
		models.put(key, model);	
	}

	@Override
	public void updateView(String key, IModel model)
	{
		models.put(key,model);
		updatePageContent();
		updateStoryList();
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
