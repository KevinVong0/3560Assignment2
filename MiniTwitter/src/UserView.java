import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class UserView extends JFrame{
	
	private long lastUpdateTime = 0;
	
	private JTextArea tweetText;
	private JPanel contentPane;
    private User user;
    private ArrayList<User> users;
    private HashMap<String, UserView> userViews;
    private ArrayList<String> userIDs;
    private DefaultListModel<String> followingModel;
    private DefaultListModel<String> newsfeedModel;
    private JList followingList;
	private JList newsFeedList;

	public UserView(User user, ArrayList<String> userIDs,ArrayList<User> users, HashMap<String, UserView> userViews) {
		this.userIDs = userIDs;
		this.user = user;
		this.users = users;
		this.userViews = userViews;
		this.setTitle(user.getID() + "'s Profile");
		initialize();
	}
	
	public void initialize() {
		followingList = new JList(user.getFollowers().toArray());
		newsFeedList = new JList(user.getNewsFeed().toArray());
		
		followingModel = new DefaultListModel<String>();
        followingModel.addElement("Currently Following: ");
		newsfeedModel = new DefaultListModel<String>();
		newsfeedModel.addElement("Feed: ");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 439, 554);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		JTextArea userIDTextArea = new JTextArea();
		
		
		JButton followUserButton = new JButton("Follow");
		followUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	if (!userIDs.contains(userIDTextArea.getText())) {
                    JOptionPane.showMessageDialog(null, "Nonexisting User!", "Following Error", JOptionPane.INFORMATION_MESSAGE);
                    userIDTextArea.setText("");
                    return;
                } 
            	if (user.getFollowing().contains(userIDTextArea.getText())) {
                    JOptionPane.showMessageDialog(null, "You already follow this user.", "Following Error", JOptionPane.INFORMATION_MESSAGE);
                } 
            	if (user.getID().equals(userIDTextArea.getText())) {
                    JOptionPane.showMessageDialog(null, "You cannot follow yourself.", "Following Error", JOptionPane.INFORMATION_MESSAGE);
                } 
            	else {
                    followingModel.addElement(userIDTextArea.getText());
                    followingList.setModel(followingModel);
                    user.following(userIDTextArea.getText());
                    for (int i = 0; i < users.size(); i++) {
                        if (users.get(i).getID().equals(userIDTextArea.getText())) {
                            users.get(i).attach(user);
                        }
                    }
                }
            	userIDTextArea.setText("");
                revalidate();
                repaint();
            }
        });
		
		followUserButton.setBounds(298, 24, 97, 84);
		contentPane.add(followUserButton);
		
		userIDTextArea.setBounds(33, 24, 253, 83);
		contentPane.add(userIDTextArea);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 121, 362, 113);
		scrollPane.setViewportView(followingList);
		contentPane.add(scrollPane);
		
		tweetText = new JTextArea();
		tweetText.setBounds(33, 244, 253, 83);
		contentPane.add(tweetText);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
		Date d = new Date(user.getCreationTime());
		String creationTime = dateFormat.format(d);
		
		JLabel creationLabel = new JLabel("Creation Time: " + creationTime);
		creationLabel.setBounds(33, 462, 159, 32);
		contentPane.add(creationLabel);
		
		JLabel lastUpdateLabel = new JLabel("Last Update was at: " + user.getLastUpdateTime());
		lastUpdateLabel.setBounds(204, 462, 191, 32);
		contentPane.add(lastUpdateLabel);
		
		
		JButton button = new JButton("Tweet");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (tweetText.getText().equals("")) {
		            JOptionPane.showMessageDialog(null, "No blank tweets.", "Tweet Error", JOptionPane.INFORMATION_MESSAGE);
		        } 
				else {
		            user.tweet(tweetText.getText());
                    long currentTime = System.currentTimeMillis();
                    lastUpdateTime = currentTime;
                    user.setLastUpdateTime(currentTime);
		            newsfeedModel.insertElementAt(user.getNewsFeed().get(0), 1);
		            newsFeedList.setModel(newsfeedModel);
		            
		            //for observer pattern
		            List<User> observers = user.getObservers(); // list of observers/followers
		            String msg = user.getNewsFeed().get(0); //get most recent tweet
		            for (int i = 0; i < observers.size(); i++) {
		                User user = observers.get(i);
		                UserView userView = userViews.get(user.getID());
		                user.updateNewsFeed(msg);

		                userView.newsfeedModel.insertElementAt(msg, 1);
		                userView.newsFeedList.setModel(newsfeedModel);
		                userView.tweetText.setText("");
		                userView.revalidate();
		                userView.repaint();

		            }   
		            
		            tweetText.setText("");
		            revalidate();
		            repaint();
		          
		    		Date date = new Date(user.getLastUpdateTime());
		    		String updateTime = dateFormat.format(date);
		    		lastUpdateLabel.setText("Last update was at: " + updateTime);
		            
		        }
			}		
		});
		button.setBounds(298, 244, 97, 84);
		contentPane.add(button);
		setContentPane(contentPane);
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(33, 336, 362, 113);
		scrollPane2.setViewportView(newsFeedList);
		contentPane.add(scrollPane2);
		
	
	}
}
