package model;

import java.util.ArrayList;

public class ChildStoriesCollection {
	
	private ArrayList<Story> stories= new ArrayList<Story>();
	
	public ChildStoriesCollection(ArrayList<Story> stories) {
		this.stories = stories;
	}
	
	public void addStory(Story s){
		stories.add(s);
	}
	
	public Boolean removeStory(Story s){
		return stories.remove(s);
	}
	
	public ArrayList<Story> getStories() {
		return stories;
	}

	public void setStories(ArrayList<Story> stories) {
		this.stories = stories;
	}
	
}
