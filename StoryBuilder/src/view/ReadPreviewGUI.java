package view;

import interfaces.IController;
import interfaces.IModel;
import interfaces.IView;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.*;


import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.border.TitledBorder;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLayeredPane;

import model.Story;

import utility.*;

public class ReadPreviewGUI extends JFrame implements IView {

	private TextSound textsound;
	
	private JPanel contentPane;
	private JPanel contentPane1;  //this panel will be printed
	private JLabel titleL;
	private JTextPane sentenceT;
	private JLabel picL1,picL2;
	private JButton button,button_1; //previous and next button
	private ImageIcon img,img1;
	private Story story;
	String sentence;
	private int currentpage=0;
	private int totalpage=0;
	// new a instance for sound
	PlaySounds ps = new PlaySounds();
	File wavFile; 
	boolean isAbsolutePath;
	private Map<String, IModel> models =new TreeMap<String, IModel>();
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ReadPreviewGUI frame = new ReadPreviewGUI();
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
	public ReadPreviewGUI() {
		
		textsound = new TextSound();
		setTitle("Read&Preview");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 685, 685);
		setLocationRelativeTo(null);
		
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
		
		contentPane1 = new JPanel();
		contentPane1.setBackground(Color.WHITE);
		contentPane1.setBounds(103, 11, 472, 589);
		contentPane.add(contentPane1);
		contentPane1.setLayout(null);
		
		sentenceT = new JTextPane();
		sentenceT.setEditable(false);
		sentenceT.setFont(new Font("Tahoma", Font.PLAIN, 15));
		sentenceT.setText("A sentence here");
		sentenceT.setBounds(59, 53, 358, 55);
		contentPane1.add(sentenceT);
		
		
//		img = new ImageIcon("story_image\\Apple.jpg");
//		img.setImage(img.getImage().getScaledInstance((int)(img.getIconWidth()*0.5),(int)(img.getIconHeight()*0.5),Image.SCALE_DEFAULT));
//		img1 = new ImageIcon("story_image\\Bicycle.jpg");
//		img1.setImage(img1.getImage().getScaledInstance((int)(img1.getIconWidth()*0.5),(int)(img1.getIconHeight()*0.5),Image.SCALE_DEFAULT));

		picL1 = new JLabel("");
		picL1.setBorder(null);
		picL1.setIcon(img);
		picL1.setBounds(120, 119, 255, 204);
		contentPane1.add(picL1);
		
		picL2 = new JLabel("");
		picL2.setIcon(img1);
		picL2.setBounds(120, 339, 255, 211);
		contentPane1.add(picL2);
		
		titleL = new JLabel("Title here");
		titleL.setHorizontalAlignment(SwingConstants.CENTER);
		titleL.setFont(new Font("Tahoma", Font.BOLD, 18));
		titleL.setBounds(21, 22, 427, 28);
		contentPane1.add(titleL);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Sound", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		panel_2.setBounds(10, 11, 83, 121);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JButton playbtn = new JButton("");
		
		
		playbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ps.play(wavFile);
			}
		});
		playbtn.setBorderPainted(false);
		playbtn.setToolTipText("play");
		playbtn.setIcon(new ImageIcon("image\\play.png"));
		playbtn.setBounds(10, 71, 64, 41);
		panel_2.add(playbtn);
		
		JButton stopbtn = new JButton("");
		//stop the sound
		stopbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ps.stop(wavFile);
			}
		});
		stopbtn.setBorderPainted(false);
		stopbtn.setToolTipText("stop");
		stopbtn.setIcon(new ImageIcon("image\\stop.png"));
		stopbtn.setBounds(10, 24, 64, 41);
		panel_2.add(stopbtn);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBounds(585, 540, 63, 60);
		contentPane.add(layeredPane_1);
		
		button = new JButton(""); //previous page button
		button_1 = new JButton(""); //next page button
		
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentpage++;
				updatePageButtons();
				scrollPage(currentpage-1);
			}
		});
		button_1.setBounds(10, 0, 53, 55);
		layeredPane_1.add(button_1);
		button_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button_1.setToolTipText("Next page");
		button_1.setContentAreaFilled(false);
		button_1.setBorderPainted(false);
		button_1.setIcon(new ImageIcon("image\\Next.png"));
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(30, 540, 63, 60);
		contentPane.add(layeredPane);
		
		
		button.setVisible(false);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentpage--;
				updatePageButtons();
				scrollPage(currentpage-1);
			}
		});
		button.setBounds(0, 0, 59, 55);
		layeredPane.add(button);
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button.setIcon(new ImageIcon("image\\Back.png"));
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setToolTipText("Previous page");
		button.setName("");
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Reader", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
		panel.setBounds(585, 11, 74, 81);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textsound.read(sentence);
			}
		});
		btnNewButton.setBorderPainted(false);
		btnNewButton.setIcon(new ImageIcon("image\\sound.png"));
		btnNewButton.setBounds(10, 27, 54, 31);
		panel.add(btnNewButton);
		
		
		
	}

	public void updatePageButtons()
	{
		if (currentpage==1)
		{
			button.setVisible(false);
		}else
		{
			button.setVisible(true);
		}
		if (currentpage>=totalpage) {
			button_1.setVisible(false);
		}else {
			button_1.setVisible(true);
		}
	}
	/**
	 * initialize the GUI, display story with its pages info
	 * @param s
	 */
	public void initialGUI(Story s,boolean isAbsolutePath){
		this.isAbsolutePath=isAbsolutePath;
		currentpage=1;
		story = s;
		String title = s.getTitle();
		sentence = s.getContent().getPages().get(0).getSentece(); //load first page
		String url = s.getContent().getPages().get(0).getSound();
		if (!isAbsolutePath) {
			url="sounds\\"+url;
		}
		wavFile = new File(url);
		String fontstyle = s.getFontStyle();
		String pic1 = s.getContent().getPages().get(0).getPic1();
		String pic2 = s.getContent().getPages().get(0).getPic2();
		
		int fontsize = s.getFontSize();
		Color background = Color.decode(s.getBackground());
		Color fontcolor = Color.decode(s.getColor());
		totalpage = s.getContent().getPages().size();
		if(story.getContent().getPages().size() == 1){
			button_1.setVisible(false);
		}
		titleL.setText(title);
		contentPane1.setBackground(background);
		sentenceT.setBackground(background);
		sentenceT.setForeground(fontcolor);
		sentenceT.setText(sentence);
		sentenceT.setFont(new Font(fontstyle,Font.PLAIN,fontsize));
		ps.play(wavFile);
		changePic(pic1,pic2);
		updatePageButtons();
		System.out.println("curpage="+currentpage+",totalpage="+totalpage);
	}
	
	/**
	 * change the pic on each page
	 * @param pic1
	 * @param pic2
	 */
	public void changePic(String pic1,String pic2){
		if(!pic1.equals("null")&&!pic1.isEmpty()){
			if (!isAbsolutePath)
			{
				pic1="story_image\\"+pic1;
			}
			setIcon(pic1, picL1);
		}
		else{
			picL1.setIcon(null);
		}
		
		if(!pic2.equals("null")&&!pic2.isEmpty()){
			if (!isAbsolutePath)
			{
				pic2="story_image\\"+pic2;
			}
			setIcon(pic2, picL2);
		}
		else{
			picL2.setIcon(null);
		}
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
	 * scroll the page with the pagenum
	 * @param pagenum
	 */
	public void scrollPage(int pagenum){
		sentence = story.getContent().getPages().get(pagenum).getSentece();
		sentenceT.setText(sentence);
		String url = story.getContent().getPages().get(pagenum).getSound();
		if (!isAbsolutePath) {
			url="sounds\\"+url;
		}
		String pic1 = story.getContent().getPages().get(pagenum).getPic1();
		String pic2 = story.getContent().getPages().get(pagenum).getPic2();
		changePic(pic1,pic2);
		wavFile = new File(url);
	    ps.play(wavFile);
		
	}

	@Override
	public void addModel(String key, IModel model) {
		models.put(key, model);
	}

	@Override
	public void updateView(String key, IModel model) {
		//No parts need update.
	}

	@Override
	public void showMessage(String title, String msg, int type) {
		JOptionPane.showMessageDialog(this,msg,title,JOptionPane.OK_OPTION|type);
	}

	@Override
	public void addController(IController controller) {
		// No body.
	}
	@Override
	public void setGUIVisible(boolean isVisible) {
		setVisible(isVisible);
	}
}
