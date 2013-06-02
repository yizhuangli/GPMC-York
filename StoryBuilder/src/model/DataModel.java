package model;

import interfaces.IModel;
import interfaces.IView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;



public class DataModel implements IModel {
	
	// private Map<String, Adult> adults = new TreeMap<String,Adult>();
	 //private Map<String, Child> childrenmap = new TreeMap<String,Child>();
//	 private Map<String, Story> stories = new TreeMap<String,Story>();
	// private Map<String, Page> pages = new TreeMap<String,Page>();
	// private ArrayList<Story> stories = new ArrayList<Story>();
	 //private ArrayList<Page> pages = new ArrayList<Page>();
//	 private Map<String, Feedback> feedbacks = new TreeMap<String,Feedback>();
	 

	/**
	 * load XML into the Document
	 * */
	 private Document getDocument(String dataFile)
	 {
		Document dom=null;
		try 
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			db = dbf.newDocumentBuilder();
			dom = db.parse (new File("data\\"+dataFile));
		} catch (ParserConfigurationException | SAXException | IOException e)
		{
			e.printStackTrace();
			return null;
		}
		
		return dom;
	}

	/**
	 * Generate ID for the user.
	 * */
	 private Integer generateID(String db,String tagName,String idField)
	 {
		ArrayList<Integer> idList=new ArrayList<Integer>();
		Document dom=getDocument(db);
		int max=-1;
		
		if (dom!=null)
		{
			NodeList childlist = dom.getElementsByTagName(tagName);//Child

			for(int i=0;i<childlist.getLength();i++)
			{
				Element childElement = (Element)childlist.item(i);
				
				Element idEle = (Element)childElement.getElementsByTagName(idField).item(0);
	            String id = idEle.getChildNodes().item(0).getNodeValue();
	            System.out.println("read id:"+id);
	            idList.add(Integer.parseInt(id));
			}
			if (idList.size()==0)
			{
				return 1;
			}
			else
			{
				max=idList.get(0);
				for (Integer i : idList)
				{
					if (i>max)
					{
						max=i;
					}
				}
			}
			
		}
		return (max+1);
	 }
	 /**
	  * load all adults and store in Map
	  */
	public Map<String, Adult> loadAdult(){
		Document dom=getDocument("adult.xml");
		Map<String, Adult> adults=new TreeMap<String, Adult>();
		if (dom!=null)
		{
			NodeList adultlist = dom.getElementsByTagName("adult");
//			System.out.println("adult num: "+adultlist.getLength());
		
			for(int i=0;i<adultlist.getLength();i++){
				Node adultnode = adultlist.item(i);
				Element adultElement = (Element)adultnode;
				
				NodeList idlist = adultElement.getElementsByTagName("adultID");
				Element idEle = (Element)idlist.item(0);
				NodeList textid = idEle.getChildNodes();
			    String id = textid.item(0).getNodeValue();
	//                System.out.println("id: "+id);
			    
				NodeList usernamelist = adultElement.getElementsByTagName("username");
				Element usernameEle = (Element)usernamelist.item(0);
				NodeList textusername = usernameEle.getChildNodes();
			    String username = textusername.item(0).getNodeValue();
	//                System.out.println("username: "+username);
			    
			    NodeList pwdlist = adultElement.getElementsByTagName("password");
				Element pwdEle = (Element)pwdlist.item(0);
				NodeList textpwd = pwdEle.getChildNodes();
			    String password = textpwd.item(0).getNodeValue();
	//                System.out.println("password: "+password);
			    
			    Adult a = new Adult(id, password, username);
			    adults.put(username, a);   //username is a unique key
			}
			 return adults;
		}else {
			return null;
		}
	}	 
	
	
	
	/**
	 * load all children and store in the Map
	 */
//	public void loadChild(){
//		Document dom;
//		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//		DocumentBuilder db;
//		try {
//			db = dbf.newDocumentBuilder();
//			dom = db.parse (new File("data\\child.xml"));
//			
//			NodeList childlist = dom.getElementsByTagName("child");
//			System.out.println("child num: "+childlist.getLength());
//			
//			for(int i=0;i<childlist.getLength();i++){
//				Node childnode = childlist.item(i);
//				Element childElement = (Element)childnode;
//				
//				NodeList idlist = childElement.getElementsByTagName("childID");
//				Element idEle = (Element)idlist.item(0);
//				NodeList textid = idEle.getChildNodes();
//                String id = textid.item(0).getNodeValue();
//                System.out.println("id: "+id);
//                
//				NodeList usernamelist = childElement.getElementsByTagName("name");
//				Element usernameEle = (Element)usernamelist.item(0);
//				NodeList textusername = usernameEle.getChildNodes();
//                String name = textusername.item(0).getNodeValue();
//                System.out.println("name: "+name);
//                
//                NodeList pwdlist = childElement.getElementsByTagName("password");
//				Element pwdEle = (Element)pwdlist.item(0);
//				NodeList textpwd = pwdEle.getChildNodes();
//                String password = textpwd.item(0).getNodeValue();
//                System.out.println("password: "+password);
//                
//                NodeList agelist = childElement.getElementsByTagName("age");
//				Element ageEle = (Element)agelist.item(0);
//				NodeList textage = ageEle.getChildNodes();
//                String age = textage.item(0).getNodeValue();
//                System.out.println("age: "+age);
//                
//                NodeList piclist = childElement.getElementsByTagName("picture");
//				Element picEle = (Element)piclist.item(0);
//				NodeList textpic = picEle.getChildNodes();
//                String picture = textpic.item(0).getNodeValue();
//                System.out.println("picture: "+picture);
//                
//                Child c = new Child(id, password, name, age, picture);
//                children.put(id, c);  
//                
//                
//			}
//			
//			
//		} catch (ParserConfigurationException | SAXException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}	 
	
	/**
	 * Get All Children Information From Database.
	 * @return Map<String, Child> or null
	 */
	public ArrayList<Child> getAllChildren()
	{
		ArrayList<Child> children = new ArrayList<Child>();

		Document dom=getDocument("child.xml");
		if (dom!=null)
		{
			NodeList childlist = dom.getElementsByTagName("child");

			for(int i=0;i<childlist.getLength();i++)
			{
				Element childElement = (Element)childlist.item(i);
				
				Element idEle = (Element)childElement.getElementsByTagName("childID").item(0);
	            String id = idEle.getChildNodes().item(0).getNodeValue();
	            
				Element usernameEle = (Element)childElement.getElementsByTagName("name").item(0);
	            String name = usernameEle.getChildNodes().item(0).getNodeValue(); 
	            
				Element pwdEle = (Element)childElement.getElementsByTagName("password").item(0);
	            String password = pwdEle.getChildNodes().item(0).getNodeValue();
	            
				Element ageEle = (Element)childElement.getElementsByTagName("age").item(0);
	            String age = ageEle.getChildNodes().item(0).getNodeValue();

				Element picEle = (Element)childElement.getElementsByTagName("picture").item(0);
	            String picture = picEle.getChildNodes().item(0).getNodeValue();
	            /*DEBUG BLOCK
	            System.out.println("id: "+id);
	            System.out.println("name: "+name);
	            System.out.println("password: "+password);
	            System.out.println("age: "+age);
	            System.out.println("picture: "+picture);
	            */
	            ChildStoriesCollection csc=new ChildStoriesCollection(getStoriesByChild(id));
	            Child c = new Child(id, password, name, age, picture);
	            c.setAssignedStory(csc);
	            children.add(c);
	            //childrenmap.put(id, c);
			}
			return children;
		}else
		{
			return null;
		}
	}
	
	/**
	 *	child login
	 *  @return boolean 
	 * */
	public boolean childLogin(String childID,String password)
	{
		for (Child c : getAllChildren())
		{
			if(c.getID().equals(childID) && c.getPassword().equals(password))
				return true;
		}
		return false;
	}
	
	/**
	 * adult login
	 * @return boolean
	 * */
	public boolean adultLogin(String userName,String password)
	{
		for (String key : loadAdult().keySet())
		{
			if(key.equals(userName) && loadAdult().get(key).getPassword().equals(password))
				return true;
		}
		return false;
	}
	
	/**
	 * get the child by childid
	 * @param id
	 * */
	public Child getChildByID(String id)
	{
		for (Child c : getAllChildren())
		{
			if(c.getID().equals(id))
			{
				return c;
			}
		}
		return null;
	}
	
	/**
	 * get the adult by adultID
	 * @param adultId
	 * */
	public Adult getAdultByID(String adultId)
	{
		for (String id : loadAdult().keySet())
		{
			if(id.equals(adultId))
			{
				return loadAdult().get(id);
			}
		}
		return null;
	}
	
	/**
	 * get the story by storyid
	 * @param sid
	 * */
	public Story getStoryByID(String sid)
	{
		ArrayList<Story> allStories=loadStory();
		Story rsStory=null;
		for (Story story : allStories)
		{
			if (story.getStoryID().equals(sid))
			{
				rsStory=story;
			}
		}
		return rsStory;
	}
	
	/**
	 * get all the assigned stories of a child
	 * */
	public ArrayList<Story> getStoriesByChild(String childID)
	{
		ArrayList<Story> childStories = new ArrayList<Story>();
		
		Document dom=getDocument("database.xml");
		if (dom!=null)
		{
			NodeList childlist = dom.getElementsByTagName("assign");

			for(int i=0;i<childlist.getLength();i++)
			{
				Element childElement = (Element)childlist.item(i);
				
				Element sidEle = (Element)childElement.getElementsByTagName("storyID").item(0);
	            String sid = sidEle.getChildNodes().item(0).getNodeValue();
	            
				Element cidEle = (Element)childElement.getElementsByTagName("childID").item(0);
	            String cid = cidEle.getChildNodes().item(0).getNodeValue(); 
	            
				
	            /*DEBUG BLOCK
	            System.out.println("id: "+id);
	            System.out.println("name: "+name);
	            System.out.println("password: "+password);
	            System.out.println("age: "+age);
	            System.out.println("picture: "+picture);
	            */
	            if (cid.equalsIgnoreCase(childID))
	            {
	            	Story story=getStoryByID(sid);
		            if (story!=null)
		            {
		            	Story s = story;
			            childStories.add(s);
					}
	            }
			}
			return childStories;
		}else
		{
			return null;
		}
	}
	
	/**
	 * load all stories in the arraylist
	 */
	public ArrayList<Story> loadStory(){
		Document dom=getDocument("database.xml");
		if (dom!=null) {
			ArrayList<Story> stories=new ArrayList<Story>();
			NodeList storylist = dom.getElementsByTagName("story");
			System.out.println("story num111: "+storylist.getLength());
			
			for(int i=0;i<storylist.getLength();i++){
				Node storynode = storylist.item(i);
				Element storyElement = (Element)storynode;
				
				NodeList idlist = storyElement.getElementsByTagName("storyID");
				Element idEle = (Element)idlist.item(0);
				NodeList textid = idEle.getChildNodes();
			    String id = textid.item(0).getNodeValue();
//	                System.out.println("id: "+id);
			    
			    NodeList aidlist = storyElement.getElementsByTagName("adultID");
				Element aidEle = (Element)aidlist.item(0);
				NodeList textaid = aidEle.getChildNodes();
			    String adultID = textaid.item(0).getNodeValue();
//	                System.out.println("adultID: "+adultID);
			    
				NodeList titlelist = storyElement.getElementsByTagName("title");
				Element titleEle = (Element)titlelist.item(0);
				NodeList texttitle = titleEle.getChildNodes();
			    String title = texttitle.item(0).getNodeValue();
//	                System.out.println("title: "+title);
			    
			    NodeList backlist = storyElement.getElementsByTagName("background");
				Element backEle = (Element)backlist.item(0);
				NodeList textback = backEle.getChildNodes();
			    String background = textback.item(0).getNodeValue();
//	                System.out.println("background: "+background);
			    
			    NodeList stylelist = storyElement.getElementsByTagName("fontstyle");
				Element styleEle = (Element)stylelist.item(0);
				NodeList textage = styleEle.getChildNodes();
			    String fontstyle = textage.item(0).getNodeValue();
//	                System.out.println("fontstyle: "+fontstyle);
			    
			    NodeList sizelist = storyElement.getElementsByTagName("fontsize");
				Element sizeEle = (Element)sizelist.item(0);
				NodeList textsize = sizeEle.getChildNodes();
			    String fontsize = textsize.item(0).getNodeValue();
//	                System.out.println("fontsize: "+fontsize);
			    
			    NodeList colorlist = storyElement.getElementsByTagName("fontcolor");
				Element colorEle = (Element)colorlist.item(0);
				NodeList textcolor = colorEle.getChildNodes();
			    String fontcolor = textcolor.item(0).getNodeValue();
//	                System.out.println("fontcolor: "+fontcolor);
			    
			    NodeList feedlist = storyElement.getElementsByTagName("needFeedback");
				Element feedEle = (Element)feedlist.item(0);
				NodeList textfeed = feedEle.getChildNodes();
			    String needFeedback = textfeed.item(0).getNodeValue();
//	                System.out.println("needFeedback: "+needFeedback);
			    
			    NodeList samplelist = storyElement.getElementsByTagName("isSample");
				Element samEle = (Element)samplelist.item(0);
				NodeList textSam = samEle.getChildNodes();
			    String isSample = textSam.item(0).getNodeValue();
			    
			    NodeList countlist = storyElement.getElementsByTagName("readCount");
				Element countEle = (Element)countlist.item(0);
				NodeList textcount = countEle.getChildNodes();
			    String readCount = textcount.item(0).getNodeValue();
//	                System.out.println("readCount: "+readCount);
			    
//	                loadPage(Long.parseLong(id));
			    PagesCollection pc = new PagesCollection(loadPage(Long.parseLong(id)));
			    Story s = new Story(id,adultID,title,background,fontstyle,Integer.parseInt(fontsize),fontcolor,Boolean.valueOf(needFeedback),Boolean.valueOf(isSample),Integer.parseInt(readCount),pc);
//	                stories.put(id, s);
			    stories.add(s);
//	                System.out.println(stories.get(i).getContent().getPages());
			}
			return stories;
		}
		else {
			return null;
		}
		
	}	 
	
	
	/**
	 * load all pages of a story
	 * @param storyid
	 */
	public ArrayList<Page> loadPage(long storyid){
		Document dom=getDocument("database.xml");
		ArrayList<Page> pages = new ArrayList<Page>();
		if (dom!=null)
		{
			NodeList storylist = dom.getElementsByTagName("page");
//			System.out.println("page num: "+storylist.getLength());
		
		for(int i=0;i<storylist.getLength();i++){
			Node pagenode = storylist.item(i);
			Element pageElement = (Element)pagenode;
			
			NodeList idlist = pageElement.getElementsByTagName("storyID");
			Element idEle = (Element)idlist.item(0);
			NodeList textid = idEle.getChildNodes();
		    String storyID = textid.item(0).getNodeValue();
//                System.out.println("storyID: "+storyID);
		    
		    NodeList pidlist = pageElement.getElementsByTagName("pageID");
			Element pidEle = (Element)pidlist.item(0);
			NodeList textpid = pidEle.getChildNodes();
		    String pageID = textpid.item(0).getNodeValue();
//                System.out.println("pageID: "+pageID);
		    
		    NodeList numlist = pageElement.getElementsByTagName("pageNum");
			Element numEle = (Element)numlist.item(0);
			NodeList textnum = numEle.getChildNodes();
		    String pageNum = textnum.item(0).getNodeValue();
//                System.out.println("pageNum: "+pageNum);
		    
			NodeList senlist = pageElement.getElementsByTagName("sentence");
			Element senEle = (Element)senlist.item(0);
			NodeList textsen = senEle.getChildNodes();
		    String sentence = textsen.item(0).getNodeValue();
//                System.out.println("sentence: "+sentence);
		    
		    NodeList soundlist = pageElement.getElementsByTagName("sound");
			Element soundEle = (Element)soundlist.item(0);
			NodeList textsound = soundEle.getChildNodes();
		    String sound = textsound.item(0).getNodeValue();
//                System.out.println("sound: "+sound);
		    
		    NodeList pic1list = pageElement.getElementsByTagName("pic1");
			Element pic1Ele = (Element)pic1list.item(0);
			NodeList textpic1 = pic1Ele.getChildNodes();
		    String pic1 = textpic1.item(0).getNodeValue();
//                System.out.println("pic1: "+pic1);
		    
		    NodeList pic2list = pageElement.getElementsByTagName("pic2");
			Element pic2Ele = (Element)pic2list.item(0);
			NodeList textpic2 = pic2Ele.getChildNodes();
		    String pic2 = textpic2.item(0).getNodeValue();
//                System.out.println("pic2: "+pic2);
		    
		    //find a matched page
		    if(Long.toString(storyid).equals(storyID)){
		        Page p = new Page(pageID,storyID,sentence,sound,pic1,pic2,pageNum);
		        pages.add(p);
		    }
		    
		}
		return pages;
		}
		else {
			return null;
		}
		
	}
	
	
	/**
	 * load  feedbacks by STORY ID
	 * @param storyid
	 */
	public ArrayList<Feedback> loadFeedbackByStoryID(String storyid){
		Document dom=getDocument("database.xml");
		ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();
		if (dom!=null)
		{
			NodeList feedlist = dom.getElementsByTagName("feedback");
			System.out.println("feedback num: "+feedlist.getLength());
			
			for(int i=0;i<feedlist.getLength();i++){
				Node feednode = feedlist.item(i);
				Element feedElement = (Element)feednode;
				
				NodeList sidlist = feedElement.getElementsByTagName("storyID");
				Element sidEle = (Element)sidlist.item(0);
				NodeList textsid = sidEle.getChildNodes();
                String storyID = textsid.item(0).getNodeValue();
//                System.out.println("storyID: "+storyID);
                
                NodeList cidlist = feedElement.getElementsByTagName("childID");
				Element cidEle = (Element)cidlist.item(0);
				NodeList textcid = cidEle.getChildNodes();
                String childID = textcid.item(0).getNodeValue();
//                System.out.println("childID: "+childID);
                
                NodeList fidlist = feedElement.getElementsByTagName("feedbackID");
				Element fidEle = (Element)fidlist.item(0);
				NodeList textfid = fidEle.getChildNodes();
                String feedbackID = textfid.item(0).getNodeValue();
//                System.out.println("feedbackID: "+feedbackID);
                
				NodeList senlist = feedElement.getElementsByTagName("feeling");
				Element senEle = (Element)senlist.item(0);
				NodeList textsen = senEle.getChildNodes();
                String feeling = textsen.item(0).getNodeValue();
//                System.out.println("feeling: "+feeling);
                
                if(storyid.equals(storyID)){
                	 Feedback f = new Feedback(feedbackID,childID,storyID,feeling);
                	 feedbacks.add(f);
                }
			}
			 if(feedbacks.size()>0){
     			return feedbacks;
     		}else {
					return null;
				}
		}else {
			return null;
		}	
	}
	
	/**
	 * load feedback by child id
	 * @param childid
	 * @return
	 */
	public ArrayList<Feedback> loadFeedbackByChildID(String childid){
		ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();
		
		Document dom=getDocument("database.xml");
		if (dom!=null)
		{
			NodeList feedlist = dom.getElementsByTagName("feedback");
			System.out.println("feedback num: "+feedlist.getLength());
			
			for(int i=0;i<feedlist.getLength();i++){
				Node feednode = feedlist.item(i);
				Element feedElement = (Element)feednode;
				
				NodeList sidlist = feedElement.getElementsByTagName("storyID");
				Element sidEle = (Element)sidlist.item(0);
				NodeList textsid = sidEle.getChildNodes();
			    String storyID = textsid.item(0).getNodeValue();
//	                System.out.println("storyID: "+storyID);
			    
			    NodeList cidlist = feedElement.getElementsByTagName("childID");
				Element cidEle = (Element)cidlist.item(0);
				NodeList textcid = cidEle.getChildNodes();
			    String childID = textcid.item(0).getNodeValue();
//	                System.out.println("childID: "+childID);
			    
			    NodeList fidlist = feedElement.getElementsByTagName("feedbackID");
				Element fidEle = (Element)fidlist.item(0);
				NodeList textfid = fidEle.getChildNodes();
			    String feedbackID = textfid.item(0).getNodeValue();
//	                System.out.println("feedbackID: "+feedbackID);
			    
				NodeList senlist = feedElement.getElementsByTagName("feeling");
				Element senEle = (Element)senlist.item(0);
				NodeList textsen = senEle.getChildNodes();
			    String feeling = textsen.item(0).getNodeValue();
//	                System.out.println("feeling: "+feeling);
			    
			    if(childid.equals(childID)){
			    	 Feedback f = new Feedback(feedbackID,childID,storyID,feeling);
			    	 feedbacks.add(f);
			    }
			   
			    
			}
			
			if(!feedbacks.isEmpty()){
				return feedbacks;
			}
			else{
				return null;
			}
		}else {
			return null;
		}
	}
	
	
	/**
	 * get unassigned child , story size should <  10
	 * @param storyid
	 */
	public ArrayList<Child> getUnassignChild(String storyid){
		ArrayList<Child> allchild = new ArrayList<Child>(); //store unassigned children
		ArrayList<Child> unassigns1 = new ArrayList<Child>();
		ArrayList<Child> unassigns2 = new ArrayList<Child>();
//		unassigns1= getAllChildren();
		allchild = getAllChildren();
		System.out.println("all child num-- "+allchild.size());
		Map<String, Child> mychild = new TreeMap<String, Child>(); //make a copy of children list
		for (Child child : getAllChildren())
		{
			mychild.put(child.getID(), child);
		}
		
		Document dom=getDocument("database.xml");
		if(dom!=null)
		{
			NodeList assignlist = dom.getElementsByTagName("assign");
			System.out.println("assign num: "+assignlist.getLength());
			
			for(int i=0;i<assignlist.getLength();i++){
				Node feednode = assignlist.item(i);
				Element feedElement = (Element)feednode;
				
				NodeList sidlist = feedElement.getElementsByTagName("storyID");
				Element sidEle = (Element)sidlist.item(0);
				NodeList textsid = sidEle.getChildNodes();
			    String storyID = textsid.item(0).getNodeValue();
			    System.out.println("storyID: "+storyID);
			    
			    NodeList cidlist = feedElement.getElementsByTagName("childID");
				Element cidEle = (Element)cidlist.item(0);
				NodeList textcid = cidEle.getChildNodes();
			    String childID = textcid.item(0).getNodeValue();
			    System.out.println("childID: "+childID);
			    
			    if(storyid.equals(storyID)){
			      	System.out.println("FIND A ASSIGNED CHILD"+childID);
			     
			      	mychild.remove(childID);
//	                  	for(int j=0;j<allchild.size();j++){
//	                  	  if(allchild.get(j).getID().equals(childID)){
//	                  		  unassigns1.remove(allchild.get(j));
//	                  	  }
////	                  	  
//	                   }
//	                  	System.out.println("un1----------------"+unassigns1.size());
			    }
//	                else{
//	                	System.out.println("mychild size"+mychild.size());
//	                	if(mychild.size()>0){
//	                		unassigns1.add(mychild.get(childID));
//	        			}
//	                }
			    
			    
			    
			}
			
			if(!mychild.isEmpty()){
				Iterator it = mychild.keySet().iterator();
				while(it.hasNext()){  //add unassigned child to the arraylist
					unassigns1.add(mychild.get(it.next()));
				}
			}
			
			if(unassigns1.size()>0){
				for(int i=0;i<unassigns1.size();i++){
					ArrayList<Story> assignstory = new ArrayList<Story>(); 
					assignstory = getStoriesByChild(unassigns1.get(i).getID());
					if(assignstory.size()<10){  //filter the unassign1 to get the children has <10 story
						unassigns2.add(unassigns1.get(i));
					}
				}
			}
			
			if(unassigns2.size()>0){
				return unassigns2;
			}
			else{
				return null;
			}
		}else {
			return null;
		}
		
	}
	

	/**
	 * adult login
	 * @param username
	 * @param password
	 * @return
	 */
//	public String loginAdult(String username, String password){
//		if(adults.containsKey(username)){
//			System.out.println("find a record: "+username);
//			if(adults.get(username).getPassword().equals(password)){
//				return "login ok";
//			}
//			else{
//				return "wrong password";
//			}
//		}
//		return "not found";
//	}
	

	/**
	 * validate adult's username during register, username cannot be the same
	 * @param username
	 * @return
	 */
//	public String validateAdultRegister(String username){
//		if(adults.containsKey(username)){
//			return "duplicate username";
//		}
//		else{
//			return "username ok";
//		}
//		
//	}
	
	/**
	 * child login, children with same names are allowed
	 * @param id
	 * @param password
	 * @return
//	 */
//	public String loginChild(String id, String password){
//		if(children.containsKey(id)){
//			System.out.println("find a record: "+id);
//			if(children.get(id).getPassword().equals(password)){
//				return "loginOK";
//			}
//			else{
//				return "wrong password";
//			}
//		}
//		
//		return "not found";
//	}

	/**
	 * register for adults
	 * @param a
	 */
	public boolean addAdult(Adult a) {
		Document dom=getDocument("adult.xml");
		try {

			Element rootEle = dom.getDocumentElement();
			Element adultEle = dom.createElement("adult");
			rootEle.appendChild(adultEle);
			
			//create a adultID node
			Element idEle = dom.createElement("adultID");
			org.w3c.dom.Text idText =  dom.createTextNode(generateID("adult.xml", "adult", "adultID").toString());
			idEle.appendChild(idText);
			adultEle.appendChild(idEle);
			
			//create a username node
			Element usernameEle = dom.createElement("username");
			org.w3c.dom.Text usernameText =  dom.createTextNode(a.getUsername());
			usernameEle.appendChild(usernameText);
			adultEle.appendChild(usernameEle);
			
			//create a password node
			Element passwordEle = dom.createElement("password");
			org.w3c.dom.Text passwordText =  dom.createTextNode(a.getPassword());
			passwordEle.appendChild(passwordText);
			adultEle.appendChild(passwordEle);
			
			//write the data to xml file
			TransformerFactory.newInstance().newTransformer()
            .transform(new DOMSource(dom), 
                       new StreamResult(new File("data\\adult.xml")));
			
			
			
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	/**
	 * register for children
	 * @param c
	 */
	public boolean addChild(Child c,File photo) {
		Document dom=getDocument("child.xml");

		if(dom!=null)
		{
			Element rootEle = dom.getDocumentElement();
			Element childEle = dom.createElement("child");
			rootEle.appendChild(childEle);
			
			//create a childID node
			Element idEle = dom.createElement("childID");
			org.w3c.dom.Text idText =  dom.createTextNode(generateID("child.xml", "child", "childID").toString());
			idEle.appendChild(idText);
			childEle.appendChild(idEle);
			
			//create a name node
			Element usernameEle = dom.createElement("name");
			org.w3c.dom.Text usernameText =  dom.createTextNode(c.getUsername());
			usernameEle.appendChild(usernameText);
			childEle.appendChild(usernameEle);
			
			//create a password node
			Element passwordEle = dom.createElement("password");
			org.w3c.dom.Text passwordText =  dom.createTextNode(c.getPassword());
			passwordEle.appendChild(passwordText);
			childEle.appendChild(passwordEle);
			
			//create a age node
			Element ageEle = dom.createElement("age");
			org.w3c.dom.Text ageText = dom.createTextNode(c.getAge());
			ageEle.appendChild(ageText);
			childEle.appendChild(ageEle);
			
			//upload picture
			String fileName=System.currentTimeMillis()+photo.getName().substring(photo.getName().lastIndexOf("."));
			File target = new File("child_image\\"+fileName);
			uploadFile(photo, target);
			c.setPic(fileName);
			//create a picture node
			Element picEle = dom.createElement("picture");
			org.w3c.dom.Text picText = dom.createTextNode(c.getPic());
			picEle.appendChild(picText);
			childEle.appendChild(picEle);
			
			//write the data to xml file
			try {
				TransformerFactory.newInstance().newTransformer()
				.transform(new DOMSource(dom), 
				           new StreamResult(new File("data\\child.xml")));
				
				//Copy photo file
			} catch (TransformerException | TransformerFactoryConfigurationError e)
			{
				e.printStackTrace();
				return false;
			}
			return true;
		}else {
			return false;
		}
	}
	/**
	 * Upload File
	 * @author http://www.blogjava.net/toby/archive/2011/12/05/365585.html
	 */
	  private void uploadFile(File sourceFile, File targetFile)
	  {
	        BufferedInputStream inBuff = null;
	        BufferedOutputStream outBuff = null;
	        try {
	            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
	            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
	            byte[] b = new byte[1024 * 5];
	            int len;
	            while ((len = inBuff.read(b)) != -1) {
	                outBuff.write(b, 0, len);
	            }
	            outBuff.flush();
	        } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
	        	
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally
			{
				try {
					inBuff.close();
					outBuff.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
			}
	    }
		/**
		 * delete all the irrelevant files
		 * */
	  public void clearUnlinkedFiles()
	  {
		  Document dom=getDocument("database.xml");
		  ArrayList<String> usedsounds=new ArrayList<String>();
		  ArrayList<String> usedpic1s=new ArrayList<String>();
		  ArrayList<String> usedpic2s=new ArrayList<String>();
			if(dom!=null)
			{
				NodeList assignlist = dom.getElementsByTagName("page");
				
				for(int i=0;i<assignlist.getLength();i++){
					Node feednode = assignlist.item(i);
					Element feedElement = (Element)feednode;
					
					NodeList sidlist = feedElement.getElementsByTagName("sound");
					Element sidEle = (Element)sidlist.item(0);
					NodeList textsid = sidEle.getChildNodes();
				    String sound = textsid.item(0).getNodeValue();
				    usedsounds.add(sound);
				    
				    NodeList cidlist = feedElement.getElementsByTagName("pic1");
					Element cidEle = (Element)cidlist.item(0);
					NodeList textcid = cidEle.getChildNodes();
				    String pic1 = textcid.item(0).getNodeValue();
				    usedpic1s.add(pic1);
				    
				    NodeList pic2list = feedElement.getElementsByTagName("pic2");
					Element pic2Ele = (Element)pic2list.item(0);
					NodeList textpic2 = pic2Ele.getChildNodes();
				    String pic2 = textpic2.item(0).getNodeValue();
				    usedpic2s.add(pic2);
				}
				//DELETE 
				File dir=new File("sounds\\");
				
				for (File f : dir.listFiles()) {
					System.out.println(f.getName());
					if (!usedsounds.contains(f.getName()))
					{
						//System.out.println(f.getName()+","+usedsounds.get(0));
						boolean rs=f.delete();
						System.out.println("delete file:"+rs);
					}
				}
				dir=new File("story_image\\");
				
				for (File f : dir.listFiles()) {
					System.out.println(f.getName());
					if (!usedpic1s.contains(f.getName()) && !usedpic2s.contains(f.getName()))
					{
						boolean rs=f.delete();
						System.out.println("delete file:"+rs);
					}
				}
			}
	  }
	/**
	 * create a new story
	 * @param s
	 */
	public boolean addStory(Story s,Map<String, File> files){
		Document dom=getDocument("database.xml");
		try {
			Element rootEle = (Element) dom.getDocumentElement().getElementsByTagName("stories").item(0);
			Element storyEle = dom.createElement("story");
			rootEle.appendChild(storyEle);
			
			//create a story id node
			Element idEle = dom.createElement("storyID");
			String storyID=generateID("database.xml", "story", "storyID").toString();
			org.w3c.dom.Text idText =  dom.createTextNode(storyID);
			idEle.appendChild(idText);
			storyEle.appendChild(idEle);
			
			//create a adult id node
			Element adultidEle = dom.createElement("adultID");
			org.w3c.dom.Text adultidText =  dom.createTextNode(s.getAdultID());
			adultidEle.appendChild(adultidText);
			storyEle.appendChild(adultidEle);
			
			//create a title node
			Element titleEle = dom.createElement("title");
			org.w3c.dom.Text titleText =  dom.createTextNode(s.getTitle());
			titleEle.appendChild(titleText);
			storyEle.appendChild(titleEle);
			
			//create a background color node
			Element backEle = dom.createElement("background");
			org.w3c.dom.Text backText =  dom.createTextNode(s.getBackground());
			backEle.appendChild(backText);
			storyEle.appendChild(backEle);
			
			//create a font style node
			Element fontstyleEle = dom.createElement("fontstyle");
			org.w3c.dom.Text fontstyleText = dom.createTextNode(s.getFontStyle());
			fontstyleEle.appendChild(fontstyleText);
			storyEle.appendChild(fontstyleEle);
			
			//create a font size node
			Element fontsizeEle = dom.createElement("fontsize");
			org.w3c.dom.Text fontsizeText = dom.createTextNode(Integer.toString(s.getFontSize()));
			fontsizeEle.appendChild(fontsizeText);
			storyEle.appendChild(fontsizeEle);
			
			//create a font color node
			Element fontcolorEle = dom.createElement("fontcolor");
			org.w3c.dom.Text fontcolorText = dom.createTextNode(s.getColor());
			fontcolorEle.appendChild(fontcolorText);
			storyEle.appendChild(fontcolorEle);
			
			//create a feedback node
			Element feedbackEle = dom.createElement("needFeedback");
			org.w3c.dom.Text feedbackText = dom.createTextNode(Boolean.toString(s.isNeedFeedback()));
			feedbackEle.appendChild(feedbackText);
			storyEle.appendChild(feedbackEle);
			
			Element sampleEle = dom.createElement("isSample");
			org.w3c.dom.Text samText = dom.createTextNode(Boolean.toString(s.isSample()));
			sampleEle.appendChild(samText);
			storyEle.appendChild(sampleEle);
			
			//create a read count node
			Element countEle = dom.createElement("readCount");
			org.w3c.dom.Text countText = dom.createTextNode("0");
			countEle.appendChild(countText);
			storyEle.appendChild(countEle);
			
			//write the data to xml file
			TransformerFactory.newInstance().newTransformer()
            .transform(new DOMSource(dom), 
                       new StreamResult(new File("data\\database.xml")));
			
			//add pages
			for (int i=0;i<s.getContent().getPages().size();i++)
			{
				Page p=s.getContent().getPages().get(i);
				
				
				//save sound file
				String fileName=System.currentTimeMillis()+files.get("soundFile"+i).getName().substring(files.get("soundFile"+i).getName().lastIndexOf("."));
				File target = new File("sounds\\"+fileName);
				uploadFile(files.get("soundFile"+i), target);
				p.setSound(fileName);
				//save pic1
				fileName=System.currentTimeMillis()+files.get("pic1File"+i).getName().substring(files.get("pic1File"+i).getName().lastIndexOf("."));
				target = new File("story_image\\"+fileName);
				uploadFile(files.get("pic1File"+i), target);
				p.setPic1(fileName);
				//save pic2
				if (files.get("pic2File"+i)!=null)
				{
					fileName=System.currentTimeMillis()+files.get("pic2File"+i).getName().substring(files.get("pic2File"+i).getName().lastIndexOf("."));
					target = new File("story_image\\"+fileName);
					uploadFile(files.get("pic2File"+i), target);
					p.setPic2(fileName);
				}
				addPage(p,storyID);
			}

		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * create a new page
	 * @param p
	 */
	public void addPage(Page p,String storyID){
		Document dom=getDocument("database.xml");
		try {
			Element rootEle = (Element) dom.getDocumentElement().getElementsByTagName("pages").item(0);
			Element pageEle = dom.createElement("page");
			rootEle.appendChild(pageEle);
			
			//create a page id node
			Element pageidEle = dom.createElement("pageID");
			org.w3c.dom.Text pageidText =  dom.createTextNode(generateID("database.xml", "page", "pageID").toString());
			pageidEle.appendChild(pageidText);
			pageEle.appendChild(pageidEle);
			
			//create a story id node
			Element idEle = dom.createElement("storyID");
			org.w3c.dom.Text idText =  dom.createTextNode(storyID);
			idEle.appendChild(idText);
			pageEle.appendChild(idEle);
			
			//create a page num node
			Element numEle = dom.createElement("pageNum");
			org.w3c.dom.Text numText =  dom.createTextNode(p.getPageNum());
			numEle.appendChild(numText);
			pageEle.appendChild(numEle);
			
			//create a sentence node
			Element senEle = dom.createElement("sentence");
			org.w3c.dom.Text senText =  dom.createTextNode(p.getSentece());
			senEle.appendChild(senText);
			pageEle.appendChild(senEle);
			
			//create a sound node
			Element soundEle = dom.createElement("sound");
			org.w3c.dom.Text soundText =  dom.createTextNode(p.getSound());
			soundEle.appendChild(soundText);
			pageEle.appendChild(soundEle);
			
			//create a pic 1 node
			Element pic1Ele = dom.createElement("pic1");
			org.w3c.dom.Text pic1Text;
			if (p.getPic1()=="") {
				pic1Text= dom.createTextNode("null");
			} else {
				pic1Text= dom.createTextNode(p.getPic1());
			}
			pic1Ele.appendChild(pic1Text);
			pageEle.appendChild(pic1Ele);
			
			//create a pic 2 node
			Element pic2Ele = dom.createElement("pic2");
			org.w3c.dom.Text pic2Text;
			if (p.getPic2()=="") {
				pic2Text= dom.createTextNode("null");
			} else {
				pic2Text= dom.createTextNode(p.getPic2());
			}
			pic2Ele.appendChild(pic2Text);
			pageEle.appendChild(pic2Ele);
			
			
			//write the data to xml file
			TransformerFactory.newInstance().newTransformer()
            .transform(new DOMSource(dom), 
                       new StreamResult(new File("data\\database.xml")));
			
			
			
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * create a new feedback
	 * @param f
	 */
	public boolean addFeedback(Feedback f){
		Document dom=getDocument("database.xml");
		try {
			
			Element rootEle = (Element) dom.getDocumentElement().getElementsByTagName("feedbacks").item(0);
			Element feedbackEle = dom.createElement("feedback");
			rootEle.appendChild(feedbackEle);
			
			
			//create a feedback id node
			Element idEle = dom.createElement("feedbackID");
			org.w3c.dom.Text idText =  dom.createTextNode(generateID("database.xml","feedback","feedbackID").toString());
			idEle.appendChild(idText);
			feedbackEle.appendChild(idEle);
			
			//create a story id node
			Element storyidEle = dom.createElement("storyID");
			org.w3c.dom.Text storyidText =  dom.createTextNode(f.getStoryID());
			storyidEle.appendChild(storyidText);
			feedbackEle.appendChild(storyidEle);
			
			//create a child id node
			Element childidEle = dom.createElement("childID");
			org.w3c.dom.Text childidText =  dom.createTextNode(f.getChildID());
			childidEle.appendChild(childidText);
			feedbackEle.appendChild(childidEle);
			
			//create a feeling node
			Element feelEle = dom.createElement("feeling");
			org.w3c.dom.Text feelText =  dom.createTextNode(f.getFeeling());
			feelEle.appendChild(feelText);
			feedbackEle.appendChild(feelEle);
			
			//write the data to xml file
			TransformerFactory.newInstance().newTransformer()
            .transform(new DOMSource(dom), 
                       new StreamResult(new File("data\\database.xml")));
			
			
			
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * create a new story assignment
	 */
	public boolean addAssign(String storyID, ArrayList<String> childID) {
		Document dom=getDocument("database.xml");
		try {
			for (String cid : childID)
			{
				Element rootEle = (Element) dom.getDocumentElement().getElementsByTagName("assigns").item(0);
				Element assignEle = dom.createElement("assign");
				rootEle.appendChild(assignEle);
				
				//create a story id node
				Element storyidEle = dom.createElement("storyID");
				org.w3c.dom.Text storyidText =  dom.createTextNode(storyID);
				storyidEle.appendChild(storyidText);
				assignEle.appendChild(storyidEle);
				
				//create a child id node
				Element childidEle = dom.createElement("childID");
				org.w3c.dom.Text childidText =  dom.createTextNode(cid);
				childidEle.appendChild(childidText);
				assignEle.appendChild(childidEle);
				
				
				//write the data to xml file
				TransformerFactory.newInstance().newTransformer()
	            .transform(new DOMSource(dom), 
	                       new StreamResult(new File("data\\database.xml")));
			}
			
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * create a new story
	 * @param s
	 */
	public boolean editStory(Story s,Map<String, File> files){
		
		deleteStoryInfo(s.getStoryID());//only delete story info
		Document dom=getDocument("database.xml");
		try {
			Element rootEle = (Element) dom.getDocumentElement().getElementsByTagName("stories").item(0);
			Element storyEle = dom.createElement("story");
			rootEle.appendChild(storyEle);
			
			//create a story id node
			Element idEle = dom.createElement("storyID");
			org.w3c.dom.Text idText =  dom.createTextNode(s.getStoryID());
			idEle.appendChild(idText);
			storyEle.appendChild(idEle);
			
			//create a adult id node
			Element adultidEle = dom.createElement("adultID");
			org.w3c.dom.Text adultidText =  dom.createTextNode(s.getAdultID());
			adultidEle.appendChild(adultidText);
			storyEle.appendChild(adultidEle);
			
			//create a title node
			Element titleEle = dom.createElement("title");
			org.w3c.dom.Text titleText =  dom.createTextNode(s.getTitle());
			titleEle.appendChild(titleText);
			storyEle.appendChild(titleEle);
			
			//create a background color node
			Element backEle = dom.createElement("background");
			org.w3c.dom.Text backText =  dom.createTextNode(s.getBackground());
			backEle.appendChild(backText);
			storyEle.appendChild(backEle);
			
			//create a font style node
			Element fontstyleEle = dom.createElement("fontstyle");
			org.w3c.dom.Text fontstyleText = dom.createTextNode(s.getFontStyle());
			fontstyleEle.appendChild(fontstyleText);
			storyEle.appendChild(fontstyleEle);
			
			//create a font size node
			Element fontsizeEle = dom.createElement("fontsize");
			org.w3c.dom.Text fontsizeText = dom.createTextNode(Integer.toString(s.getFontSize()));
			fontsizeEle.appendChild(fontsizeText);
			storyEle.appendChild(fontsizeEle);
			
			//create a font color node
			Element fontcolorEle = dom.createElement("fontcolor");
			org.w3c.dom.Text fontcolorText = dom.createTextNode(s.getColor());
			fontcolorEle.appendChild(fontcolorText);
			storyEle.appendChild(fontcolorEle);
			
			//create a feedback node
			Element feedbackEle = dom.createElement("needFeedback");
			org.w3c.dom.Text feedbackText = dom.createTextNode(Boolean.toString(s.isNeedFeedback()));
			feedbackEle.appendChild(feedbackText);
			storyEle.appendChild(feedbackEle);
			
			Element samEle = dom.createElement("isSample");
			org.w3c.dom.Text samText = dom.createTextNode(Boolean.toString(s.isSample()));
			samEle.appendChild(samText);
			storyEle.appendChild(samEle);
			
			//create a read count node
			Element countEle = dom.createElement("readCount");
			org.w3c.dom.Text countText = dom.createTextNode(Integer.toString(s.getReadCount()));
			countEle.appendChild(countText);
			storyEle.appendChild(countEle);
			
			//write the data to xml file
			TransformerFactory.newInstance().newTransformer()
            .transform(new DOMSource(dom), 
                       new StreamResult(new File("data\\database.xml")));
			
			//edit page
			if (files!=null) {
				editPages(s.getContent().getPages(),files);
			}
		} catch (ParserConfigurationException | SAXException | IOException   e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 	edit the pages of a story
	 * */
	public void editPages(ArrayList<Page> pages,Map<String, File> files) throws TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError, ParserConfigurationException, SAXException, IOException{
		deletePage(pages.get(0).getStoryID());
		Document dom=getDocument("database.xml");
		for (int i=0;i<pages.size();i++)
		{
			Page page=pages.get(i);
			
			//save sound file
			String fileName=System.currentTimeMillis()+files.get("soundFile"+i).getName().substring(files.get("soundFile"+i).getName().lastIndexOf("."));
			File target = new File("sounds\\"+fileName);
			uploadFile(files.get("soundFile"+i), target);
			page.setSound(fileName);
			//save pic1
			fileName=System.currentTimeMillis()+files.get("pic1File"+i).getName().substring(files.get("pic1File"+i).getName().lastIndexOf("."));
			target = new File("story_image\\"+fileName);
			uploadFile(files.get("pic1File"+i), target);
			page.setPic1(fileName);
			//save pic2
			if (files.get("pic2File"+i)!=null && !files.get("pic2File"+i).getName().equalsIgnoreCase("null"))
			{
				System.out.println("pic2File"+i+"="+files.get("pic2File"+i).getName());
				fileName=System.currentTimeMillis()+files.get("pic2File"+i).getName().substring(files.get("pic2File"+i).getName().lastIndexOf("."));
				target = new File("story_image\\"+fileName);
				uploadFile(files.get("pic2File"+i), target);
				page.setPic2(fileName);
			}
			
			Element rootEle = (Element) dom.getDocumentElement().getElementsByTagName("pages").item(0);
			Element pageEle = dom.createElement("page");
			rootEle.appendChild(pageEle);
			
			//create a page id node
			Element pageidEle = dom.createElement("pageID");
			org.w3c.dom.Text pageidText;
			if (page.getPageID()==null)
			{
				pageidText =  dom.createTextNode(generateID("database.xml", "page", "pageID").toString());
			}else {
				pageidText =  dom.createTextNode(page.getPageID());
			}
			
			pageidEle.appendChild(pageidText);
			pageEle.appendChild(pageidEle);
			
			//create a story id node
			Element idEle = dom.createElement("storyID");
			org.w3c.dom.Text idText =  dom.createTextNode(page.getStoryID());
			idEle.appendChild(idText);
			pageEle.appendChild(idEle);
			
			//create a page num node
			Element numEle = dom.createElement("pageNum");
			org.w3c.dom.Text numText =  dom.createTextNode(page.getPageNum());
			numEle.appendChild(numText);
			pageEle.appendChild(numEle);
			
			//create a sentence node
			Element senEle = dom.createElement("sentence");
			org.w3c.dom.Text senText =  dom.createTextNode(page.getSentece());
			senEle.appendChild(senText);
			pageEle.appendChild(senEle);
			
			//create a sound node
			Element soundEle = dom.createElement("sound");
			org.w3c.dom.Text soundText =  dom.createTextNode(page.getSound());
			soundEle.appendChild(soundText);
			pageEle.appendChild(soundEle);
			
			//create a pic 1 node
			Element pic1Ele = dom.createElement("pic1");
			org.w3c.dom.Text pic1Text;
			if (page.getPic1()=="") {
				pic1Text= dom.createTextNode("null");
			} else {
				pic1Text= dom.createTextNode(page.getPic1());
			}
			pic1Ele.appendChild(pic1Text);
			pageEle.appendChild(pic1Ele);
			
			//create a pic 2 node
			Element pic2Ele = dom.createElement("pic2");
			org.w3c.dom.Text pic2Text;
			if (page.getPic2()=="") {
				pic2Text= dom.createTextNode("null");
			} else {
				pic2Text= dom.createTextNode(page.getPic2());
			}
			pic2Ele.appendChild(pic2Text);
			pageEle.appendChild(pic2Ele);
			

			//write the data to xml file
			TransformerFactory.newInstance().newTransformer()
            .transform(new DOMSource(dom), 
                       new StreamResult(new File("data\\database.xml")));
		}
		clearUnlinkedFiles();
	}
	/**
	 * delete a story
	 * @param storyid
	 */
	public boolean deleteStoryInfo(String storyid) {//use for readcount
		Document dom=getDocument("database.xml");
		try {
			
			NodeList stories = dom.getElementsByTagName("stories");
			NodeList storylist = dom.getElementsByTagName("story");
//			System.out.println("story num: "+storylist.getLength());
			
			for(int i=0;i<storylist.getLength();i++){
				Node storynode = storylist.item(i);
				Element storyElement = (Element)storynode;
				
				NodeList idlist = storyElement.getElementsByTagName("storyID");
				Element idEle = (Element)idlist.item(0);
				NodeList textid = idEle.getChildNodes();
                String id = textid.item(0).getNodeValue();
                
                if(storyid.equals(id)){
                	stories.item(0).removeChild(storynode); //remove the record
                }
                
			}
			
			//write the data to xml file
			TransformerFactory.newInstance().newTransformer()
	        .transform(new DOMSource(dom), 
	                   new StreamResult(new File("data\\database.xml")));
			System.out.println("deleted!!!");

			//deletePage(storyid,fa);

		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Delete story by storyID.
	 * @param storyid
	 * */
	public boolean deleteStory(String storyid) {
		Document dom=getDocument("database.xml");
		try {
			
			NodeList stories = dom.getElementsByTagName("stories");
			NodeList storylist = dom.getElementsByTagName("story");
//			System.out.println("story num: "+storylist.getLength());
			
			for(int i=0;i<storylist.getLength();i++){
				Node storynode = storylist.item(i);
				Element storyElement = (Element)storynode;
				
				NodeList idlist = storyElement.getElementsByTagName("storyID");
				Element idEle = (Element)idlist.item(0);
				NodeList textid = idEle.getChildNodes();
                String id = textid.item(0).getNodeValue();
                
                if(storyid.equals(id)){
                	stories.item(0).removeChild(storynode); //remove the record
                }
                
			}
			
			//write the data to xml file
			TransformerFactory.newInstance().newTransformer()
	        .transform(new DOMSource(dom), 
	                   new StreamResult(new File("data\\database.xml")));
			System.out.println("deleted!!!");

			deletePage(storyid);
			deleteFeedbackByStory(storyid);
			deleteAssignsByStory(storyid);
			clearUnlinkedFiles();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * delete all pages related with the given story
	 * @param storyid
	 */
	public void deletePage(String storyid) {
		
		ArrayList<Node> deletes = new ArrayList<Node>();//record the page node, these pages will be deleted
		Document dom=getDocument("database.xml");
		try {
			
			NodeList pages = dom.getElementsByTagName("pages");
			NodeList pagelist = dom.getElementsByTagName("page");
			System.out.println("pages len: "+pages.getLength());
			System.out.println("pagelist len="+pagelist.getLength());
			for(int i=0;i<pagelist.getLength();i++){
				
				Node pagenode = pagelist.item(i);
				Element storyElement = (Element)pagenode;
				
				NodeList idlist = storyElement.getElementsByTagName("storyID");
				Element idEle = (Element)idlist.item(0);
				NodeList textid = idEle.getChildNodes();
                String id = textid.item(0).getNodeValue();
                
                NodeList soundlist = storyElement.getElementsByTagName("sound");
				Element soundEle = (Element)soundlist.item(0);
				NodeList textsound = soundEle.getChildNodes();
                String sound = textsound.item(0).getNodeValue();
                
                NodeList pic1list = storyElement.getElementsByTagName("pic1");
				Element picEle = (Element)pic1list.item(0);
				NodeList textpic = picEle.getChildNodes();
                String pic1 = textpic.item(0).getNodeValue();
                
                NodeList pic2list = storyElement.getElementsByTagName("pic2");
				Element pic2Ele = (Element)pic2list.item(0);
				NodeList textpic2 = pic2Ele.getChildNodes();
                String pic2 = textpic2.item(0).getNodeValue();
                
                if(storyid.equals(id)){
                	deletes.add(pagenode);
//                	if (delFile)
//                	{
//                		deleteFile(new File("sounds\\"+sound));
//                    	deleteFile(new File("story_image\\"+pic1));
//                    	if (pic2!="null")
//                    	{
//                    		deleteFile(new File("story_image\\"+pic2));
//    					}
//					}
                }      
			}
			
			//delete the node
			if(deletes.size()>0){
				for(int s=0;s<deletes.size();s++)
				{
					
					pages.item(0).removeChild(deletes.get(s));
				}
			}
			
			//write the data to xml file
			TransformerFactory.newInstance().newTransformer()
	        .transform(new DOMSource(dom), 
	                   new StreamResult(new File("data\\database.xml")));
			System.out.println("page deleted!!!");

		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * delete all the feedback by storyid
	 * @param storyid
	 * */
public void deleteFeedbackByStory(String storyid) {
		
		ArrayList<Node> deletes = new ArrayList<Node>();//record the page node, these pages will be deleted
		Document dom=getDocument("database.xml");
		try {
			
			NodeList pages = dom.getElementsByTagName("feedbacks");
			NodeList pagelist = dom.getElementsByTagName("feedback");
//			System.out.println("page num: "+pagelist.getLength());
			
			for(int i=0;i<pagelist.getLength();i++){
				Node pagenode = pagelist.item(i);
				Element storyElement = (Element)pagenode;
				
				NodeList idlist = storyElement.getElementsByTagName("storyID");
				Element idEle = (Element)idlist.item(0);
				NodeList textid = idEle.getChildNodes();
                String id = textid.item(0).getNodeValue();
                
                if(storyid.equals(id)){
                	deletes.add(pagenode);
                }
                
			}
			
			//delete the node
			if(deletes.size()>0){
				for(int s=0;s<deletes.size();s++){
					pages.item(0).removeChild(deletes.get(s));
				}
			}
			
			//write the data to xml file
			TransformerFactory.newInstance().newTransformer()
	        .transform(new DOMSource(dom), 
	                   new StreamResult(new File("data\\database.xml")));
			System.out.println("page deleted!!!");

		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/**
 *	delete the assigns of child by the story id
 *@param storyid 
 * */
public void deleteAssignsByStory(String storyid) {
	
	ArrayList<Node> deletes = new ArrayList<Node>();//record the page node, these pages will be deleted
	Document dom=getDocument("database.xml");
	try {
		
		NodeList pages = dom.getElementsByTagName("assigns");
		NodeList pagelist = dom.getElementsByTagName("assign");
//		System.out.println("page num: "+pagelist.getLength());
		
		for(int i=0;i<pagelist.getLength();i++){
			Node pagenode = pagelist.item(i);
			Element storyElement = (Element)pagenode;
			
			NodeList idlist = storyElement.getElementsByTagName("storyID");
			Element idEle = (Element)idlist.item(0);
			NodeList textid = idEle.getChildNodes();
            String id = textid.item(0).getNodeValue();
            
            if(storyid.equals(id)){
            	deletes.add(pagenode);
            }
            
		}
		
		//delete the node
		if(deletes.size()>0){
			for(int s=0;s<deletes.size();s++){
				pages.item(0).removeChild(deletes.get(s));
			}
		}
		
		//write the data to xml file
		TransformerFactory.newInstance().newTransformer()
        .transform(new DOMSource(dom), 
                   new StreamResult(new File("data\\database.xml")));
		System.out.println("page deleted!!!");

	} catch (TransformerConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (TransformerException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (TransformerFactoryConfigurationError e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataModel dc = new DataModel();
//		dc.adultRegister(null);
//		dc.childRegister(null);
//		dc.storyCreate(null);
//		dc.pageCreate(null);
//		dc.feedbackCreate(null);
//		dc.assignCreate(0, 0);
//		dc.loadAdult();
//		dc.loadChild();
//		dc.loadStory();
		
//		dc.loadAdult();
//		System.out.println(	dc.loginAdult("hello", "123"));
		
//		dc.loadChild();
//		System.out.println(	dc.loginChild("lee.png", "123"));
//	
//		dc.loadFeedback();
		
//		dc.loadChild();
//		System.out.println(	dc.getUnassignChild(2));
		
//		dc.addStory(null);
//		dc.deleteStory(1);
		
//		dc.addPage(null);
//		dc.deletePage(1);
//		ArrayList<Story> s = new ArrayList<Story>();
//		ArrayList<Page> p = new ArrayList<Page>();
//		s = dc.loadStory();
//		for(int i =0;i<s.size();i++){
//		System.out.println(s.get(0).getTitle());
//		System.out.println(s.get(0).getContent());
//		System.out.println(s.get(0).getContent().getPages());
//		}
		
//		p = dc.loadPage(1);
//		PagesCollection pc = new PagesCollection(p);
//		System.out.println(p);
//		System.out.println(pc.getPages());
//		dc.deletePage("1365623199657");
//		dc.getUnassignChild("1");
		System.out.println(dc.loadFeedbackByStoryID("1"));
//		dc.getFeedbackFeeling();
//		System.out.println(dc.loadFeedbackByStoryID("0"));
	}

	@Override
	public void addView(String key, IView view) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyViews() {
		// TODO Auto-generated method stub
		
	}

}
