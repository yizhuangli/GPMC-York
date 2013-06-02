package interfaces;
public interface IView
{
	/**
	 * show message dialogue
	 * @param title
	 * @param msg
	 * @param type
	 */
	public void showMessage(String title,String msg,int type);
	/**
	 * show gui
	 * @param isVisible
	 */
	public void setGUIVisible(boolean isVisible);
	/**
	 * add controller to view
	 * @param controller
	 */
	public void addController(IController controller);
	/**
	 * add model to view
	 * @param key
	 * @param model
	 */
	public void addModel(String key,IModel model);
	/**
	 * update view
	 * @param key
	 * @param model
	 */
	public void updateView(String key,IModel model);
}
