package view;

import interfaces.IController;
import interfaces.IModel;

import javax.swing.JFrame;

import model.Adult;
import model.Child;
import model.DataModel;

import controller.AdultController;
import controller.ChildController;

/**
 *  entrance of the system
 */
public class GlueGUI
{
	public static void main(String[] args)
	{
		//Declare MVC parts
		IModel adultModel=new Adult();
		IModel childModel=new Child();

		MainChooseTypeGUI mainChoose=new MainChooseTypeGUI();
		AdultLoginGUI adultLoginGUI=new AdultLoginGUI();
		AdultGUI adultGUI=new AdultGUI();
		ChildLoginGUI childLoginGUI=new ChildLoginGUI();
		ChildGUI childGUI=new ChildGUI();
		ReadPreviewGUI readPreviewGUI=new ReadPreviewGUI();
		CreateStoryGUI createStoryGUI = new CreateStoryGUI();
		EditStoryGUI editStoryGUI = new EditStoryGUI();
		GuidelineGUI guidelineGUI = new GuidelineGUI();
		
		IController adultController=new AdultController();
		IController childController=new ChildController();
		
		//Add views to models
		adultModel.addView("adultGUI", adultGUI);
		
		
		//Add views to controllers
		adultController.addView("mainChoose", mainChoose);
		adultController.addView("adultLoginGUI", adultLoginGUI);
		adultController.addView("adultGUI", adultGUI);
		adultController.addView("readPreviewGUI", readPreviewGUI);
		adultController.addView("createStoryGUI", createStoryGUI);
		adultController.addView("editStoryGUI", editStoryGUI);
		adultController.addModel("adultModel", adultModel);
		adultController.addView("guidelineGUI", guidelineGUI);

		childController.addView("mainChoose", mainChoose);
		childController.addView("childLoginGUI", childLoginGUI);
		childController.addView("childGUI", childGUI);
		childController.addModel("adultModel", adultModel);
		childController.addModel("childModel", childModel);
		
		
		//Add controllers and models to views
		mainChoose.addController(adultController);
		adultLoginGUI.addController(adultController);
		adultLoginGUI.addModel("adultModel", adultModel);
		adultGUI.addController(adultController);
		adultGUI.addModel("adultModel", adultModel);
		createStoryGUI.addController(adultController);
		createStoryGUI.addModel("adultModel", adultModel);
		editStoryGUI.addController(adultController);
		mainChoose.addController(childController);
		readPreviewGUI.addController(adultController);
		childLoginGUI.addController(childController);
		childLoginGUI.addModel("adultModel", adultModel);
		childGUI.addController(childController);
		childGUI.addModel("childModel", childModel);
		
		/**
		 * Start the application
		 */
		//DataModel dm=new DataModel();
		//dm.clearUnlikedFiles();
		mainChoose.setVisible(true);
		mainChoose.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


	}
}
