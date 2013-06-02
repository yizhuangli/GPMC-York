package interfaces;
public interface IModel
{
	/**
	 * add view to model
	 * @param key
	 * @param view
	 */
	public void addView(String key,IView view);
	/**
	 * notify registered views
	 */
	public void notifyViews();
}
