package model;

import java.util.Map;
import java.util.TreeMap;


import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import interfaces.IModel;
import interfaces.IView;


/**
 * Child class is the concrete class used to package the data of the child.
 * */
public class Child extends User implements IModel{

	private String userID;
	private String fullName;
	private String age;
	private String pic;
	private String password;
	private ChildStoriesCollection assignedStory;
	Map<String, IView> views=new TreeMap<String,IView>();
	private DataModel dataModel=new DataModel();
	
	public Child()
	{}
	public Child(String userID, String password, String fullName, String age,
			String pic) {
		super(userID, password, fullName);
		this.age = age;
		this.pic = pic;
		this.assignedStory= new ChildStoriesCollection(null);
		this.password=password;
		this.userID=userID;
		this.fullName=fullName;
	}
	
	@Override
	protected Boolean validate() {
		return false;
	}
	protected Boolean validate(String userID,String password) {
		return dataModel.childLogin(userID, password);
	}

	@Override
	public User login() {
		return null;
	}
	public User login(String userID,String password) {
		if (validate(userID,password))
		{
			Child entireChild=dataModel.getChildByID(userID);
			this.age = entireChild.age;
			this.pic = entireChild.pic;
			this.assignedStory= entireChild.assignedStory;
			this.password=entireChild.password;
			this.userID=entireChild.userID;
			this.fullName=entireChild.fullName;
			notifyViews();
			return this;
		}
		return null;
	}
	
	public boolean leaveFeedback(Story s, String feeling){
		Feedback feedback=new Feedback(this.userID, s.getStoryID(), feeling);
		return dataModel.addFeedback(feedback);
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getID(){
		return userID;
	}
	public String getPic() {
		return pic;
	}
	public String getPassword(){
		return password;
	}
	public void setUsername(String fullName) {
		this.fullName=fullName;
	}

	public void setPassword(String password) {
		this.password=password;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	//get available stories
	public ChildStoriesCollection getAssignedStory() {
		return assignedStory;
	}

	public void setAssignedStory(ChildStoriesCollection assignedStory) {
		this.assignedStory = assignedStory;
	}
	public boolean readStory(Story s)
	{
		return dataModel.editStory(s,null);
	}
	@Override
	public String getUsername() {
		return fullName;
	}

	@Override
	public void addView(String key, IView view) {
		views.put(key, view);
		
	}

	@Override
	public void notifyViews() {
		for (String key : views.keySet()) {
			views.get(key).updateView(key, this);
		}
		
	}
	
}
