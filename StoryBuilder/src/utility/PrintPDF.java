package utility;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Story;

import view.ChildGUI;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

/**
 *print the story to PDF format
 */
public class PrintPDF extends JFrame{
	private JPanel contentPane;
	private JPanel contentPane1;  //this panel will be printed
	private JLabel titleL;
	private JTextPane sentenceT;
	private JLabel picL1,picL2;
	private ImageIcon img,img1;
	

	
	public PrintPDF() {
		setTitle("Welcome");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 747, 693);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane1 = new JPanel();
		contentPane1.setBackground(Color.WHITE);
		contentPane1.setBounds(241, 34, 472, 589);
		contentPane.add(contentPane1);
		contentPane1.setLayout(null);
		
		sentenceT = new JTextPane();
		sentenceT.setEditable(false);
		sentenceT.setFont(new Font("Tahoma", Font.PLAIN, 15));
		sentenceT.setText("A sentence here");
		sentenceT.setBounds(59, 53, 358, 55);
		contentPane1.add(sentenceT);
		
		titleL = new JLabel("Title here");
		titleL.setHorizontalAlignment(SwingConstants.CENTER);
		titleL.setFont(new Font("Tahoma", Font.BOLD, 18));
		titleL.setBounds(21, 22, 427, 28);
		contentPane1.add(titleL);
		
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
		
		
		
	}
	

	/**
	 * print the story in PDF
	 * @param story
	 * @param saveFile
	 */
	  public void  print(Story s,File saveFile){

		  
		Document d = new Document();
		PdfWriter writer = null;
		
			try {
				writer = PdfWriter.getInstance( d, new FileOutputStream(saveFile) );
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			
			d.open();
			//i is page numbers
			for(int i=0;i<s.getContent().getPages().size();i++){
				
				String title = s.getTitle();
				String fontstyle = s.getFontStyle();
				String pic1 = s.getContent().getPages().get(i).getPic1();
				String pic2 = s.getContent().getPages().get(i).getPic2();
				int fontsize = s.getFontSize();
				Color background = Color.decode(s.getBackground());
				Color fontcolor = Color.decode(s.getColor());
				titleL.setText(title);
				contentPane1.setBackground(background);
				sentenceT.setText(s.getContent().getPages().get(i).getSentece());
				sentenceT.setBackground(background);
				sentenceT.setForeground(fontcolor);
				sentenceT.setFont(new Font(fontstyle,Font.PLAIN,fontsize));
				changePic(pic1,pic2);
					
				PdfContentByte cb = writer.getDirectContent( );
				PdfTemplate tp = cb.createTemplate(PageSize.A4.getHeight(), PageSize.A4.getWidth());
	    //		PdfTemplate tp = cb.createTemplate(c.getWidth(), c.getWidth());
				Graphics2D g2d = tp.createGraphics( contentPane1.getWidth(), contentPane1.getHeight(), new DefaultFontMapper() );
		//		g2d.translate( pf.getImageableX( ), pf.getImageableY( ) );
		//	    g2d.translate(500, 500);
		//		g2d.scale( 0.4d, 0.4d );
			
				contentPane1.addNotify();
				contentPane1.validate();
				contentPane1.paint(g2d);
				g2d.dispose( );
			
				cb.addTemplate(tp,0,200);
				d.newPage(); // add a new page
			}
			
			d.close();
		
	}
	  
	  /**
		 * change the pic on each page
		 * @param pic1
		 * @param pic2
		 */
		public void changePic(String pic1,String pic2){
			if(!pic1.equals("null")){
				String url = "story_image\\"+pic1;
//				img = new ImageIcon(url);
//				img.setImage(img.getImage().getScaledInstance((int)(img.getIconWidth()*0.5),(int)(img.getIconHeight()*0.5),Image.SCALE_DEFAULT));
//				picL1.setIcon(img);
				setIcon(url, picL1);
			}
			else{
				picL1.setIcon(null);
			}
			
			if(!pic2.equals("null")){
				String url1 = "story_image\\"+pic2;
//				img1 = new ImageIcon(url1);
//				img1.setImage(img1.getImage().getScaledInstance((int)(img1.getIconWidth()*0.5),(int)(img1.getIconHeight()*0.5),Image.SCALE_DEFAULT));
//				picL2.setIcon(img1);
				setIcon(url1, picL2);
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
}
