package model;

import java.util.ArrayList;

public class FeedbacksCollection {
	
	private ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();
	
	public FeedbacksCollection(ArrayList<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}
	
	public void addFeedback(Feedback fb){
		this.feedbacks.add(fb);
	}
	
	public Boolean removeFeedback(Feedback fb){
		return this.feedbacks.remove(fb);
	}
	
	public ArrayList<Feedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(ArrayList<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}
	
}
