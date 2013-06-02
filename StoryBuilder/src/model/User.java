package model;

import interfaces.IModel;
import interfaces.IView;

import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;

/**
 * Both Adult class and Child class are subclasses of the User class. 
 * */

abstract public class User{
	private String userID;
	private String password;
	private String fullName;
	
	Map<String, IView> views=new TreeMap<String,IView>();
	public User()
	{
		
	}
	public User(String userID, String password, String fullName) {
		this.userID = userID;
		this.password = password;
		this.fullName = fullName;
	}
	public User(String password, String fullName) {
		this.userID = userID;
		this.password = password;
		this.fullName = fullName;
	}

	abstract public User login();
	
	abstract protected Boolean validate();
	
	public void printStory(Story s){
		
	}
	
	abstract public String getID();
	
	abstract public String getUsername();
	
	abstract public String getPassword();
	
	
}
