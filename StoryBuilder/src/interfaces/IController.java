package interfaces;
public interface IController
{
	/**
	 * add view to controller
	 * @param key
	 * @param view
	 */
	public void addView(String key,IView view);
	/**
	 * add model to controller
	 * @param key
	 * @param model
	 */
	public void addModel(String key,IModel model);
}
