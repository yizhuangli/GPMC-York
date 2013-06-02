package model;

import interfaces.IModel;
import interfaces.IView;

import java.awt.Color;
import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;

/**
 * 	Story Class is the concrete class used to package the data of the story.
 * */
public class Story implements IModel{
	
	private String storyID;
	private String adultID;
	private String title;
	private String background;
	private String fontStyle;
	private int fontSize;
	private String color;
	private boolean needFeedback;
	private boolean isSample;
	private int readCount;
	private PagesCollection content=new PagesCollection();
	
	Map<String, IView> views=new TreeMap<String,IView>();
	
	public Story(String storyID, String adultID, String title,
			String background, String fontStyle, int fontSize, String color,
			boolean needFeedback,boolean isSample, int readCount, PagesCollection content) {
		this.storyID = storyID;
		this.adultID = adultID;
		this.title = title;
		this.background = background;
		this.fontStyle = fontStyle;
		this.fontSize = fontSize;
		this.color = color;
		this.needFeedback = needFeedback;
		this.isSample=isSample;
		this.readCount = readCount;
		this.content = content;
	}
	public Story(String adultID, String title,
			String background, String fontStyle, int fontSize, String color,
			boolean needFeedback,boolean isSample, int readCount, PagesCollection content) {
		this.adultID = adultID;
		this.title = title;
		this.background = background;
		this.fontStyle = fontStyle;
		this.fontSize = fontSize;
		this.color = color;
		this.needFeedback = needFeedback;
		this.isSample=isSample;
		this.readCount = readCount;
		this.content = content;
	}
	public Story(String adultID, String title,
			String background, String fontStyle, int fontSize, String color,
			boolean needFeedback,boolean isSample, int readCount) {
		this.adultID = adultID;
		this.title = title;
		this.background = background;
		this.fontStyle = fontStyle;
		this.fontSize = fontSize;
		this.color = color;
		this.needFeedback = needFeedback;
		this.isSample=isSample;
		this.readCount = readCount;
	}
	
	public Page preview(){
		return content.getPages().get(0);
	}

	public void addPage(Page p){
		content.addPage(p);
	}	
	
	public void deletePage(String pageNum){
		content.removePage(pageNum);
	}
	
	public String getStoryID() {
		return storyID;
	}

	public void setStoryID(String storyID) {
		this.storyID = storyID;
	}

	public String getAdultID() {
		return adultID;
	}

	public void setAdultID(String adultID) {
		this.adultID = adultID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getFontStyle() {
		return fontStyle;
	}

	public void setFontStyle(String fontStyle) {
		this.fontStyle = fontStyle;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isNeedFeedback() {
		return needFeedback;
	}

	public void setNeedFeedback(boolean needFeedback) {
		this.needFeedback = needFeedback;
	}
	public boolean isSample() {
		return isSample;
	}

	public void setIsSample(boolean isSample) {
		this.isSample = isSample;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public PagesCollection getContent() {
		return content;
	}

	public void setContent(PagesCollection content) {
		this.content = content;
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
