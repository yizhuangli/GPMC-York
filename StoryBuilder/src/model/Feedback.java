package model;

import interfaces.IController;
import interfaces.IModel;
import interfaces.IView;

import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;

/**
 * Feedback class is the concrete class used to package the data of feedback. 
 * */
public class Feedback implements IModel{

	private String feedbackID;
	private String childID;
	private String storyID;
	private String feeling;
	Map<String, IView> views=new TreeMap<String,IView>();
	
	public Feedback(String feedbackID, String childID, String storyID,
			String feeling) {
		this.feedbackID = feedbackID;
		this.childID = childID;
		this.storyID = storyID;
		this.feeling = feeling;
	}
	public Feedback(String childID, String storyID,String feeling) {
		this.childID = childID;
		this.storyID = storyID;
		this.feeling = feeling;
	}
	
	public String getFeedbackID() {
		return feedbackID;
	}
	public void setFeedbackID(String feedbackID) {
		this.feedbackID = feedbackID;
	}
	public String getChildID() {
		return childID;
	}
	public void setChildID(String childID) {
		this.childID = childID;
	}
	public String getStoryID() {
		return storyID;
	}
	public void setStoryID(String storyID) {
		this.storyID = storyID;
	}
	public String getFeeling() {
		return feeling;
	}
	public void setFeeling(String feeling) {
		this.feeling = feeling;
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
