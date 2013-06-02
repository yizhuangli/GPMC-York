package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JList;
import javax.swing.JOptionPane;

import model.*;

import interfaces.IController;
import interfaces.IModel;
import interfaces.IView;
import view.*;


public class ChildController implements ActionListener,IController
{
	Map<String, IView> views=new TreeMap<String,IView>();
	Map<String, IModel> models=new TreeMap<String,IModel>();
	
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

	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
		 {
		 	 case "ShowChildLoginGUI":
		 		 act_ShowChildLoginGUI();
		 		 break;
			 case "Login":
				 act_Login();
				 break;
			 case "LeaveFeedback-Happy":
				 act_LeaveFeedback("Happy");
				 break;
			 case "LeaveFeedback-Mad":
				 act_LeaveFeedback("Mad");
				 break;
			 case "LeaveFeedback-Confused":
				 act_LeaveFeedback("Confused");
				 break;
			 case "LeaveFeedback-Sad":
				 act_LeaveFeedback("Sad");
				 break;
		 }
	}
	
	/**
	 * display child login gui
	 */
	private void act_ShowChildLoginGUI()
	{
		ChildLoginGUI childLoginGUI=(ChildLoginGUI)(views.get("childLoginGUI"));
		childLoginGUI.setVisible(true);
		childLoginGUI.init();
		
	}
	/**
	 * child login
	 */
	public void act_Login()
	{
		System.out.println("act_Login");
		ChildLoginGUI childLoginGUI=(ChildLoginGUI)views.get("childLoginGUI");
		
		Child child =(Child)((Child)models.get("childModel")).login(childLoginGUI.getChildID(),childLoginGUI.getPassword());
		if(child!=null)
		{
			ChildGUI childGUI=(ChildGUI)views.get("childGUI");
			childGUI.setVisible(true);
			childGUI.init();
		}else
		{
			childLoginGUI.showMessage("Sorry", "Login Failed.",JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * child write a feedback
	 * @param feeling
	 */
	public void act_LeaveFeedback(String feeling)
	{
		ChildGUI childGUI=(ChildGUI)views.get("childGUI");
		Story s=childGUI.getStory();
		if(((Child)models.get("childModel")).leaveFeedback(s,feeling))
		{
			childGUI.showMessage("Info", "Your feedback is recieved.",JOptionPane.INFORMATION_MESSAGE);
			childGUI.updateFeedbackPanel(false);
		}else
		{
			childGUI.showMessage("Info", "Your feedback is recieved.",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	/**
	 * read a story: readCount++
	 */
	public void act_Read()
	{
		ChildGUI childGUI=(ChildGUI)views.get("childGUI");
		Story s=childGUI.getStory();
		s.setReadCount((s.getReadCount()+1));
		((Child)models.get("childModel")).readStory(s);
	}
}
