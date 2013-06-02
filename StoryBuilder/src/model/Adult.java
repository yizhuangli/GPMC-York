package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;


import interfaces.IModel;
import interfaces.IView;

/**
 * Adult class is used to package the data of the adult.
 * */
public class Adult extends User implements IModel{

	Map<String, IView> views=new TreeMap<String,IView>();
	
	private String password;
	private String userID;
	private String fullName;
	
	private DataModel dm=new DataModel();
	
	public Adult()
	{
		
	}
	
	public Adult(String userID, String password, String fullName) {
		super(userID, password, fullName);
		// TODO Auto-generated constructor stub
		this.userID = userID;
		this.password = password;
		this.fullName = fullName;
	}
	public Adult(String fullName,String password) {
		super(password, fullName);
		// TODO Auto-generated constructor stub
		this.password = password;
		this.fullName = fullName;
	}
	public void setUserName(String username)
	{
		this.fullName=username;
	}
	public void setPassword(String password)
	{
		this.password=password;
	}

	@Override
	protected Boolean validate() {
		// TODO Auto-generated method stub
		return null;
	}
	protected Boolean validate(String userID,String password) {
		return dm.adultLogin(userID, password);
	}
	@Override
	public User login() {
		if (validate(this.fullName,this.password))
		{
			Adult entireAdult=dm.getAdultByID(fullName);
			this.fullName=entireAdult.getUsername();
			this.password=entireAdult.getPassword();
			this.userID=entireAdult.getID();
			notifyViews();
			return this;
		}else
		{
			return null;
		}
	}

	public boolean isExist()
	{
		if(dm.getAdultByID(this.fullName)!=null)
		{
			return true;
		}else {
			return false;
		}
	}
	public boolean register()
	{
		boolean rs =dm.addAdult(this);
		notifyViews();
		return rs;
	}
	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return userID;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return fullName;
	}
	@Override
	public String getPassword(){
		return password;
	}
	
	/**
	 * Get All Children.
	 * @return Map<String,Child>.
	 */
	public ArrayList<Child> getAllChildren()
	{
		return dm.getAllChildren();
	}
	public ArrayList<Feedback> getFeedbacksByChild(String childID)
	{
		return dm.loadFeedbackByChildID(childID);
	}
	public ArrayList<Feedback> getFeedbacksByStory(String storyID)
	{
		return dm.loadFeedbackByStoryID(storyID);
	}
	public ArrayList<Child> getUnassignedChildren(String storyID)
	{
		return dm.getUnassignChild(storyID);
	}
	public ArrayList<Story> getAllStories()
	{
		return dm.loadStory();
	}
	public ArrayList<Story> getMyStories()//exclude sample stories
	{
		ArrayList<Story> all=getAllStories();
		ArrayList<Story> mystories=new ArrayList<Story>();
		for (Story s :all)
		{
			if (s.getAdultID().equals(this.userID) && !s.isSample())
			{
				mystories.add(s);
			}
		}
		return mystories;
	}
	public ArrayList<Story> getSampleStories()
	{
		ArrayList<Story> all=getAllStories();
		ArrayList<Story> samstories=new ArrayList<Story>();
		for (Story s :all)
		{
			if (s.isSample())
			{
				samstories.add(s);
			}
		}
		return samstories;
	}
	public Story getStoryByID(String sid)
	{
		return dm.getStoryByID(sid);
	}
	public boolean CreateStory(Story s,Map<String, File> files){
		boolean rs=dm.addStory(s,files);
		notifyViews();
		return rs;
	}
	
	public boolean editStory(Story newStory,Map<String, File> files){
		boolean rs=dm.editStory(newStory,files);
		notifyViews();
		return rs;
	}
	
	public Boolean deleteStory(String storyID){
		boolean rs=dm.deleteStory(storyID);
		notifyViews();
		return rs;
	}
	
	public boolean assignStory(String storyID,ArrayList<String> childID){
		boolean rs=dm.addAssign(storyID, childID);
		notifyViews();
		return rs;
	}
	//create an account for child
	public boolean createAccount(Child c,File photo){
		boolean rs=dm.addChild(c,photo);
		notifyViews();
		return rs;
	}
	
	public void readGuideline(){
		
	}
	//check feedback report by child
	public FeedbacksCollection checkFeedbackReport(Child c){
		return null;
	}
	//check feedback report by story
	public FeedbacksCollection checkFeedbackReport(Story s){
		return null;
	}

	@Override
	public void addView(String key, IView view)
	{
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
