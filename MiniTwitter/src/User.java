import java.util.ArrayList;
import java.util.List;


public class User extends Subject implements Composite, Observer {

	private String lastUpdatedUser;
	private long lastUpdateTime = 0;
	private long creationTime = 0;
	
	private String id;
	private List<User> followers;
	private List<String> following;
	private List<String> newsFeedList;
	private List<String> msgs;
	private int totalMsgs = 0;
	private int totalUsers = 0;
	private String tweet;
	private String[] positiveWords =  {"nice", "good", "cool", "dope"};
	private int positiveCounter = 0;

	
	public User(String id) {
		this.id = id;
		followers = new ArrayList();
		following = new ArrayList();
		newsFeedList = new ArrayList();
		msgs = new ArrayList();
		totalUsers = totalUsers++;
	}
	
	public void following(String user) { //add user to following list
		following.add(user);
	}
	
	public void tweet(String tweet) {
		totalMsgs++;
		msgs.add(tweet);
		this.tweet = tweet;
		notifyObservers(tweet);
		newsFeedList.add(0, id + ": " + tweet);
		lastUpdatedUser = id;
        for (String word : positiveWords) {
            if (tweet.toLowerCase().contains(word)) {
            	positiveCounter++;
            }
        }
	}

	@Override //composite
	public String getID() {
		return id;
	}

	@Override //observer
	public void update(Subject subject) {
		lastUpdatedUser = id;
        if (subject instanceof User) {
            newsFeedList.add("[" + ((User)subject).getID() + "] - " + tweet);
        }
		
	}

	public List<String> getMsgs() {
		return msgs;
	}

	public int getTotalMsgs() {
		return totalMsgs;
	}
	
	public int getTotalPositive() {
		return positiveCounter;
	}

	public int getTotalUsers() {
		return totalUsers;
	}

	public List<User> getFollowers() {
		return followers;
	}
	
	public List<String> getFollowing() {
		return following;
	}
	
	public List<String> getNewsFeed() {
		return newsFeedList;
	}
	
    public void  updateNewsFeed(String msg) {
		newsFeedList.add(msg);
	}
	@Override
	public String toString() {
		return id;
	}
	
	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}
	
	public long getCreationTime() {
		return creationTime;
	}
	
	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	public long getLastUpdateTime() {
		return lastUpdateTime;
	}
}
