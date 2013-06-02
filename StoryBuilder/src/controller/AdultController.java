package controller;

import java.io.File;
import interfaces.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import model.Adult;
import model.Child;
import model.Page;
import model.PagesCollection;
import model.Story;
import model.User;

import view.AdultGUI;
import view.AdultLoginGUI;
import view.CreateStoryGUI;
import view.EditStoryGUI;
import view.ReadPreviewGUI;

public class AdultController implements ActionListener,IController
{
	Map<String, IView> views=new TreeMap<String,IView>();
	Map<String, IModel> models=new TreeMap<String,IModel>();
	

	public void mouseClicked(MouseEvent e) {
		System.out.println(e.getSource());
	}
	
	public void actionPerformed(ActionEvent e)
	{
		 switch(e.getActionCommand())
		 {
		 	 case "ShowAdultLoginGUI":
		 		 act_ShowAdultLoginGUI();
		 		 break;
			 case "ShowAdultGUI":
				 act_ShowAdultGUI();
				 break;
			 case "Login":
				 act_Login();
				 break;
			 case "Register":
				 act_Register();
				 break;
			 case "ShowReadGUI":
				 act_ShowReadGUI();
				 break;
			 case "ShowEditGUI":
				 act_ShowEditGUI();
				 break;
			 case "EditStoryGUICancel":
				 act_editStoryGUICancel();
				 break;
			 case "ShowCreateGUI":
				 act_ShowCreateGUI();
				 break;
			 case "CreateStoryGUICancel":
				 act_createStoryGUICancel();
				 break;
			 case "Delete":
				 act_Delete();
				 break;
			 case "Assign":
				 act_Assign();
				 break;
			 case "CreateChild":
				 act_CreateChild();
				 break;
			 case "CreateStory":
				 act_CreateStory();
				 break;
			 case "Preview":
				 act_Preview();
				 break;
			 case "PreviewInEdit":
				 act_PreviewInEdit();
				 break;
			 case "EditStory":
				 act_EditStory();
				 break;
			 case "SaveAsStory":
				 act_saveAsStory();
				 break;
			 case "ReadStory":
				 act_ReadStory();
				 break;
			 case "ViewGuideline":
				 act_viewGuideline();
				 break;
		 }
	}
	
	

	/**
	 * display the guideline gui
	 */
	private void act_viewGuideline() {
		System.out.println("guideline open!");
		views.get("guidelineGUI").setGUIVisible(true);
	}

	/**
	 * preview in edit mode
	 */
	private void act_PreviewInEdit() {
		EditStoryGUI editStoryGUI=(EditStoryGUI)views.get("editStoryGUI");
		Story story=editStoryGUI.getEditingStory();
		for(Page p:story.getContent().getPages())
		{
			if(p.getPic1().length()==0 || p.getSound().length()==0 ||p.getSentece().length()==0)
			{
				((EditStoryGUI)views.get("editStoryGUI")).showMessage("Warning", "Please finish your page detail with Sentence,Sound and at least 1 picture", JOptionPane.WARNING_MESSAGE);
				return;
			}
			else
			{
				
			}
		}
		views.get("readPreviewGUI").setGUIVisible(true);
		((ReadPreviewGUI)views.get("readPreviewGUI")).initialGUI(story,true);
	}

	
	/**
	 * preview in create mode
	 */
	private void act_Preview() {
		CreateStoryGUI createStoryGUI=(CreateStoryGUI)views.get("createStoryGUI");
		Story story=createStoryGUI.getCreatingStory();
		if (story!=null)
		{
			for (Page p : story.getContent().getPages())
			{
				if (p.getPic1().length()==0||p.getSound().length()==0 ||p.getSentece().length()==0)
				{
					((CreateStoryGUI)views.get("createStoryGUI")).showMessage("Warning", "Please finish your page detail with Sentence,Sound and at least 1 picture", JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
			ReadPreviewGUI readPreviewGUI=(ReadPreviewGUI)views.get("readPreviewGUI");
			readPreviewGUI.setVisible(true);
			readPreviewGUI.initialGUI(story,true);
		}else {
			createStoryGUI.showMessage("Warning", "You haven't created anything.", JOptionPane.WARNING_MESSAGE);
		}  
	}

	/**
	 * initialize adult's login GUI
	 */
	private void act_ShowAdultLoginGUI()
	{
		hideOtherGUI("adultLoginGUI");
	}

	/**
	 * initialize the adult GUI
	 */
	private void act_ShowAdultGUI()
	{
		hideOtherGUI("adultGUI");
		((AdultGUI)views.get("adultGUI")).initialGUI();
	}
	
	/**
	 * validate username and password during login, then go to adultGUI
	 */
	private void act_Login()
	{
		System.out.println("act_Login.");
		Adult adult=(Adult)models.get("adultModel");
		String username = ((AdultLoginGUI)views.get("adultLoginGUI")).getUsername_login();
		String password = ((AdultLoginGUI)views.get("adultLoginGUI")).getPwd_login();
		
		adult.setUserName(username);
		adult.setPassword(password);
		
		if (adult.login()!=null)
		{
			hideOtherGUI("adultGUI");
		}else if (adult.isExist()) {
			((AdultLoginGUI)views.get("adultLoginGUI")).showMessage("Error", "Wrong password, try again!", JOptionPane.ERROR_MESSAGE);
		}else {
			((AdultLoginGUI)views.get("adultLoginGUI")).showMessage("Error", "Sorry, you didn't register yet.", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * validate username and password during register
	 */
	private void act_Register()
	{
		System.out.println("act_Register.");
		
		
		String username1 = ((AdultLoginGUI)views.get("adultLoginGUI")).getUsername_Reg();
		String password1 = ((AdultLoginGUI)views.get("adultLoginGUI")).getPwd_Reg();
		
		Adult a=new Adult(username1, password1);
		
		if(a.isExist()){ //validate username
			System.out.println("duplicate username: "+username1);
			((AdultLoginGUI)views.get("adultLoginGUI")).showMessage("Error", "Your username exists already, please change one", JOptionPane.ERROR_MESSAGE);
		}
		else{
//			return "username ok";
			a.setUserName(username1);
			a.setPassword(password1);
			if(username1.length()>0 && password1.length()>0){  //write the record to database
				if (a.register())
				{
					((AdultLoginGUI)views.get("adultLoginGUI")).showMessage("Info", "Congratulations, you can login now", JOptionPane.INFORMATION_MESSAGE);
				} else {
					((AdultLoginGUI)views.get("adultLoginGUI")).showMessage("Error", "Register Failed.", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			else{
				((AdultLoginGUI)views.get("adultLoginGUI")).showMessage("Warning", "Please finish the form", JOptionPane.WARNING_MESSAGE);
			}
		}
		
	}
	/**
	 * display read gui
	 */
	private void act_ShowReadGUI()
	{
		views.get("readPreviewGUI").setGUIVisible(true);
	}
	/**
	 * display edit gui
	 */
	private void act_ShowEditGUI()
	{
		System.out.println("ShowEditGUI.");
		Adult adult=(Adult)models.get("adultModel");
		
		//validate if a story is selected
		AdultGUI adultGUI=(AdultGUI)views.get("adultGUI");
		Story story=adultGUI.getSelectedStory();
		
		if(story==null){
			((AdultGUI)views.get("adultGUI")).showMessage("Warning", "Please select a story.",JOptionPane.WARNING_MESSAGE);
		}
		else{
			if (story.getAdultID().equals(adult.getID()) || story.isSample())
			{
				views.get("editStoryGUI").setGUIVisible(true);
				((EditStoryGUI)views.get("editStoryGUI")).initialGUI(story);
			}else {
					adultGUI.showMessage("Warning", "You can only edit \"your stories\".", JOptionPane.ERROR_MESSAGE);
				}
		}
	}
	
	/**
	 * initialize create story GUI
	 */
	private void act_ShowCreateGUI()
	{
		System.out.println("ShowCreateGUI.");
		views.get("createStoryGUI").setGUIVisible(true);
		((CreateStoryGUI)views.get("createStoryGUI")).init();
		
	}
	/**
	 * delete story
	 */
	private void act_Delete()
	{
		AdultGUI adultGUI=(AdultGUI)views.get("adultGUI");
		Adult adult=(Adult)models.get("adultModel");
		Story s = adultGUI.getSelectedStory();
		
		System.out.println("act_Delete.");

		//validate if a story is selected
		if(s==null){
			adultGUI.showMessage("Warning", "Please select a story you want to delete.", JOptionPane.WARNING_MESSAGE);
		}
		else{
			if (JOptionPane.showConfirmDialog(null, "Delete this story?","Delete",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
			{
				if (adult.getID().equals(s.getAdultID()) ||s.isSample())
				{
					if(adult.deleteStory(s.getStoryID()))
					{
						//act_ShowAdultGUI();
					}else {
						adultGUI.showMessage("Error", "Deleting failed.", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					adultGUI.showMessage("Warning", "You can only delete \"your stories.\"", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
			
	}
	/**
	 * assign story
	 */
	private void act_Assign()
	{
		Adult adult=(Adult)models.get("adultModel");
		AdultGUI adultGUI=(AdultGUI)views.get("adultGUI");
		System.out.println("act_Assign.");
		ArrayList<String> childid = ((AdultGUI)views.get("adultGUI")).getSelectChildID_Assign();
		Story s =adultGUI.getSelectedStory();
		
		if (childid!=null && s!=null)
		{
			if (!s.isSample())
			{
				if (s.getAdultID().equals(adult.getID()))
				{
					adult.assignStory(s.getStoryID(), childid);
					adultGUI.showMessage("Info", "Assigned successfully!", JOptionPane.OK_OPTION|JOptionPane.INFORMATION_MESSAGE);
					//act_ShowAdultGUI();
				}else
				{
					adultGUI.showMessage("Warning", "You can only assign \"your stories\" to children.", JOptionPane.WARNING_MESSAGE);
				}
			}else {
				adultGUI.showMessage("Warning", "You cannot assign a sample story to children.", JOptionPane.WARNING_MESSAGE);
			}
		}else
		{
			adultGUI.showMessage("Warning", "Please select at least one story and one child!", JOptionPane.WARNING_MESSAGE);
		}
	}
	/**
	 * register a child
	 */
	private void act_CreateChild()
	{
		System.out.println("act_CreateChild.");
		Adult adult=(Adult)models.get("adultModel");
		AdultGUI adultGUI=(AdultGUI)views.get("adultGUI");
		Child c=adultGUI.getChild();
		File photo =adultGUI.getPhoto();
		if (c!=null && photo!=null)
		{
			if(adult.createAccount(c,photo))
			{
				adultGUI.showMessage("Info", "Create account successful.",JOptionPane.INFORMATION_MESSAGE);
			}else
			{
				adultGUI.showMessage("Error", "Sorry. Create account failed.", JOptionPane.ERROR_MESSAGE);
			}
		}else {
			adultGUI.showMessage("Warning","Please fill in all fields.",JOptionPane.WARNING_MESSAGE);
		}
	}
	/**
	 * create a story
	 */
	private void act_CreateStory()
	{
		System.out.println("act_CreateStory.");
		CreateStoryGUI createStoryGUI=(CreateStoryGUI)views.get("createStoryGUI");
		Adult adult=(Adult)models.get("adultModel");
		Story s = createStoryGUI.getCreatingStory();
		if (s!=null) {
			if(s.getTitle().isEmpty())
			{
				createStoryGUI.showMessage("Warning", "Please set a title for this story.", JOptionPane.WARNING_MESSAGE);
				return;
			}
			for(Page p:s.getContent().getPages())
			{
				if(p.getPic1().isEmpty() || p.getSentece().isEmpty() || p.getSound().isEmpty())
				{
					createStoryGUI.showMessage("Warning", "Please finish your page detail with Sentence,Sound and at least 1 picture", JOptionPane.WARNING_MESSAGE);
					return;
				}else if (p.getPic2().isEmpty()) {
					p.setPic2("null");
				}
			}
			Map<String, File> files=createStoryGUI.getFiles();
			if (adult.CreateStory(s,files))
			{
				createStoryGUI.showMessage("Info", "Your story is created!", JOptionPane.INFORMATION_MESSAGE);
			}else {
				createStoryGUI.showMessage("Error", "Creating failed!", JOptionPane.ERROR_MESSAGE);
			}
		}else {
			createStoryGUI.showMessage("Warning", "You haven't created anything.", JOptionPane.WARNING_MESSAGE);
		}	
	}
	/**
	 * edit the story
	 */

	private void act_EditStory()
	{
		System.out.println("act_EditStory.");
		
		EditStoryGUI editStoryGUI=(EditStoryGUI)views.get("editStoryGUI");
		Adult adult=(Adult)models.get("adultModel");
		Story s = editStoryGUI.getEditingStory();
		if (s.getAdultID().equals(adult.getID()) ||s.isSample())
		{
			if (s.getTitle().isEmpty())
			{
				editStoryGUI.showMessage("Warning", "Please input a title.", JOptionPane.WARNING_MESSAGE);
				return;
			}
			for (Page p : s.getContent().getPages())
			{
				if (p.getPic1().isEmpty() || p.getSound().isEmpty() ||p.getSentece().isEmpty())
				{
					editStoryGUI.showMessage("Warning", "Please finish your page detail with Sentence,Sound and at least 1 picture", JOptionPane.WARNING_MESSAGE);
					return;
				}else if (p.getPic2()==null)
				{
					p.setPic2("null");
				}
			}
			if(((Adult)models.get("adultModel")).editStory(s,editStoryGUI.getFiles()))
			{
				editStoryGUI.showMessage("Info", "Edit successfully!", JOptionPane.INFORMATION_MESSAGE);
				//act_ShowAdultGUI();
			}else
			{
				editStoryGUI.showMessage("Error", "Edit failed!", JOptionPane.ERROR_MESSAGE);
			}
		} else
		{
			editStoryGUI.showMessage("Warning", "You can only edit \"your stories\".", JOptionPane.ERROR_MESSAGE);
		}
		
		
	}

	/**
	 * save sample as a story
	 */
	private void act_saveAsStory()
	{
		EditStoryGUI editStoryGUI=(EditStoryGUI)views.get("editStoryGUI");
		Story s = editStoryGUI.getEditingStory();
		s.setIsSample(false);

		if(((Adult)models.get("adultModel")).CreateStory(s,editStoryGUI.getFiles()))
		{
			editStoryGUI.showMessage("Info", "Save successfully!", JOptionPane.INFORMATION_MESSAGE);
			//act_ShowAdultGUI();
		}else
		{
			editStoryGUI.showMessage("Error", "Save failed!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * when read story button is pressed, if a story is selected then go to the readGUI.
	 */
	private void act_ReadStory()
	{
		System.out.println("act_ReadStory.");
		Adult adult=(Adult)models.get("adultModel");
		AdultGUI adultGUI=(AdultGUI)views.get("adultGUI");
		
		//int selected = ((AdultGUI)views.get("adultGUI")).getSelectStoryIndex();
		//validate if a story is selected
		Story s = adultGUI.getSelectedStory();
		if(s==null){
			adultGUI.showMessage("Warning", "Please select a story.", JOptionPane.WARNING_MESSAGE);
		}
		else{
			act_ShowReadGUI();
			//System.out.println(s.getContent().getPages().get(1).getPic1());
			((ReadPreviewGUI)views.get("readPreviewGUI")).initialGUI(s,false);
		}
		
	}

	/**
	 * close create story gui
	 */
	public void act_createStoryGUICancel()
	{
		hideOtherGUI("adultGUI");
		//((CreateStoryGUI)views.get("createStoryGUI")).setVisible(false);
	}
	/**
	 * close edit story gui
	 */
	public void act_editStoryGUICancel()
	{
		hideOtherGUI("adultGUI");
		//((EditStoryGUI)views.get("editStoryGUI")).setVisible(false);
	}
	/**
	 * hide other gui except the parameter
	 * @param visibleGUI
	 */
	public void hideOtherGUI(String visibleGUI)
	{
		for (String key : views.keySet())
		{
			if (key.equals(visibleGUI))
			{
				views.get(key).setGUIVisible(true);
			}else
			{
				views.get(key).setGUIVisible(false);
			}
		}
	}
	@Override
	public void addView(String key, IView view)
	{
		views.put(key, view);
	}

	@Override
	public void addModel(String key, IModel model)
	{
		models.put(key, model);
	}

}
