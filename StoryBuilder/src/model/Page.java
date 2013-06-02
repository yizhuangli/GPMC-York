package model;

import interfaces.IModel;
import interfaces.IView;

import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

/**
 *	Page class is the concrete class used to package the data of the page.	 
 * */

public class Page implements IModel{
	
	private String pageID;
	private String storyID;
	private String sentece;
	private String sound;
	private String pic1;
	private String pic2;
	private String pageNum;
	Map<String, IView> views=new TreeMap<String,IView>();
	
	public Page(String pageID, String storyID, String sentece, String sound,
			String pic1, String pic2, String pageNum) {
		this.pageID = pageID;
		this.storyID = storyID;
		this.sentece = sentece;
		this.sound = sound;
		this.pic1 = pic1;
		this.pic2 = pic2;
		this.pageNum = pageNum;
	}
	public Page(String storyID, String sentece, String sound,
			String pic1, String pic2, String pageNum) {
		this.storyID = storyID;
		this.sentece = sentece;
		this.sound = sound;
		this.pic1 = pic1;
		this.pic2 = pic2;
		this.pageNum = pageNum;
	}

	public String getPageID() {
		return pageID;
	}
	public void setPageID(String pageID) {
		this.pageID = pageID;
	}
	public String getStoryID() {
		return storyID;
	}
	public void setStoryID(String storyID) {
		this.storyID = storyID;
	}
	public String getSentece() {
		return sentece;
	}
	public void setSentece(String sentece) {
		this.sentece = sentece;
	}
	public String getSound() {
		return sound;
	}
	public void setSound(String sound) {
		this.sound = sound;
	}
	public String getPic1() {
		return pic1;
	}
	public void setPic1(String pic1) {
		this.pic1 = pic1;
	}
	public String getPic2() {
		return pic2;
	}
	public void setPic2(String pic2) {
		this.pic2 = pic2;
	}
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	@Override
	public void addView(String key, IView view) {
		views.put(key, view);
	}
	@Override
	public void notifyViews()
	{
		for (String key : views.keySet()) {
			views.get(key).updateView(key, this);
		}
	}
}
