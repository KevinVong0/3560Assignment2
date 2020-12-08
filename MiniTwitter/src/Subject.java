import java.util.ArrayList;
import java.util.List;

public abstract class Subject {

	private List<User> observers = new ArrayList<User>();
	
	public void attach(User user) {
		observers.add(user);
	}
	
	public void detach(Observer observer) {
		observers.remove(observer);
	}
	
	public List<User> getObservers() {
		return observers;
	}
	
	public void notifyObservers(String tweet) {
		for(Observer observer : observers) {
			observer.update(this);
		}
	}
}
