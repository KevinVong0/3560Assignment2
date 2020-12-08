
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;

public class AdminControlPanel extends JFrame {
	
	private long creationTime;
	
	private JPanel contentPane;
	private static AdminControlPanel pointer; //for singleton
	
	private ArrayList<UserGroup> groups; ///list of groups
	private ArrayList<String> groupIDs; //list of groupIDs
	private ArrayList<User> users; //list of users
	private ArrayList<String> userIDs; //list of userIDs

    private DefaultTreeModel rootModel;
    private String selectedUser;
    private HashMap<String, UserView> userViews = new HashMap<String, UserView>();
	private DefaultMutableTreeNode root;
	private JTree tree; //to view tree
	
	//visitors
	BottomButtons b = new BottomButtons();
	
	//singleton
	public static AdminControlPanel getInstance() {
		if (pointer == null) {
            synchronized (AdminControlPanel.class) {
                if (pointer == null) {
                    pointer = new AdminControlPanel();
                }
            }
        }
        return pointer;
	}
	private AdminControlPanel() {
		users = new ArrayList();
		userIDs = new ArrayList();
		groups = new ArrayList();
		groupIDs = new ArrayList();
		groups.add(new UserGroup("Root"));
		initiate();
	}
	
	public void initiate() {
		root = new DefaultMutableTreeNode("Root");
		rootModel = new DefaultTreeModel(root);
		tree = new JTree(rootModel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 761, 546);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane treeView = new JScrollPane(tree);
		treeView.setBounds(12, 13, 324, 476);
		contentPane.add(treeView);
		
		JTextArea userTextArea = new JTextArea();
		userTextArea.setRows(5);
		userTextArea.setLineWrap(true);
		userTextArea.setColumns(20);
		userTextArea.setBounds(348, 13, 239, 94);
		contentPane.add(userTextArea);
		
		JTextArea groupTextArea = new JTextArea();
		groupTextArea.setRows(5);
		groupTextArea.setLineWrap(true);
		groupTextArea.setColumns(20);
		groupTextArea.setBounds(348, 128, 239, 94);
		contentPane.add(groupTextArea);
		
		JButton addUserButton = new JButton();
		addUserButton.setText("Add User");
		addUserButton.setBounds(599, 13, 132, 94);
		addUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	 if (userTextArea.getText().equals("")) {
                     JOptionPane.showMessageDialog(null, "Please type a username.", "Add User Error", JOptionPane.INFORMATION_MESSAGE);
                 }
            	 //space check
            	 if (userTextArea.getText().contains(" ")){
            		 JOptionPane.showMessageDialog(null, "No spaces are allowed for usernames.", "Add User Error", JOptionPane.INFORMATION_MESSAGE);
            	 }
            	 else {
                     if (!userIDs.contains(userTextArea.getText())) {
                         if (tree.getSelectionPath() == null) {
                             User user = new User(userTextArea.getText());
                             creationTime = System.currentTimeMillis();
                             user.setCreationTime(creationTime);
                             
                             DefaultMutableTreeNode userNode = new DefaultMutableTreeNode(user);
                             users.add(user);
                             userIDs.add(userTextArea.getText());
                             userViews.put(user.getID(), new UserView(user, userIDs, users, userViews)); //hash
                             root.add(userNode);
                         } 
                         else {
                             DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
                             if (selectedElement == root) {
                                 User user = new User(userTextArea.getText());
                                 creationTime = System.currentTimeMillis();
                                 user.setCreationTime(creationTime);
                                 
                                 DefaultMutableTreeNode userNode = new DefaultMutableTreeNode(user);
                                 users.add(user);
                                 userIDs.add(userTextArea.getText());
                                 userViews.put(user.getID(), new UserView(user, userIDs, users, userViews)); //hash
                                 root.add(userNode);
                             }
                             if (selectedElement.getUserObject() instanceof UserGroup) {
                                 User user = new User(userTextArea.getText());
                                 creationTime = System.currentTimeMillis();
                                 user.setCreationTime(creationTime);
                                 
                                 DefaultMutableTreeNode userNode = new DefaultMutableTreeNode(user);
                                 users.add(user);
                                 userIDs.add(userTextArea.getText());
                                 userViews.put(user.getID(), new UserView(user, userIDs, users, userViews)); //hash
                                 selectedElement.add(userNode);
                             }
                             if (selectedElement.getUserObject() instanceof User) {
                                 User user = new User(userTextArea.getText());
                                 creationTime = System.currentTimeMillis();
                                 user.setCreationTime(creationTime);
                                 
                                 DefaultMutableTreeNode userNode = new DefaultMutableTreeNode(user);
                                 DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedElement.getParent();
                                 users.add(user);
                                 userIDs.add(userTextArea.getText());
                                 userViews.put(user.getID(), new UserView(user, userIDs, users, userViews)); //hash
                                 parentNode.add(userNode);
                             }
                         }
                     } 
                     else {
                         JOptionPane.showMessageDialog(null, "This user already exists.", "Add User Error", JOptionPane.INFORMATION_MESSAGE);
                     }
                 }
            	 
                 rootModel.reload(root);
                 expandAllNodes(tree, 0, tree.getRowCount());
                 userTextArea.setText("");
            }
            
        });
		contentPane.add(addUserButton);
		
		
		JButton addGroupButton = new JButton();
		addGroupButton.setText("Add Group");
		addGroupButton.setBounds(599, 127, 132, 94);
		addGroupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (groupTextArea.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please type a group to add.", "Add Group Error", JOptionPane.INFORMATION_MESSAGE);
                }
                //space check
           	 	if (groupTextArea.getText().contains(" ")){
           		 JOptionPane.showMessageDialog(null, "No spaces are allowed for groups.", "Add Group Error", JOptionPane.INFORMATION_MESSAGE);
           	 	}
                else {
                    if (!groupIDs.contains(groupTextArea.getText())) {
                        if (tree.getSelectionPath() == null) {
                            UserGroup group = new UserGroup(groupTextArea.getText());
                            creationTime = System.currentTimeMillis();
                            
                            DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(group);
                            groups.add(group);
                            groupIDs.add(groupTextArea.getText());
                            root.add(groupNode);
                        } 
                        else {
                            DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
                            if (selectedElement == root) {
                                UserGroup group = new UserGroup(groupTextArea.getText());
                                creationTime = System.currentTimeMillis();
                                
                                DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(group);
                                groups.add(group);
                                groupIDs.add(groupTextArea.getText());
                                root.add(groupNode);
                            } else if (groupIDs.contains(selectedElement.getUserObject().toString())) {
                                UserGroup group = new UserGroup(groupTextArea.getText());
                                creationTime = System.currentTimeMillis();
                                
                                DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(group);
                                groups.add(group);
                                groupIDs.add(groupTextArea.getText());
                                selectedElement.add(groupNode);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "This group already exists.", "Add Group Error", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                rootModel.reload(root);
                expandAllNodes(tree, 0, tree.getRowCount());
                groupTextArea.setText("");
            }
        });
		contentPane.add(addGroupButton);
		
		JButton userViewButton = new JButton();
		userViewButton.setText("Open User View");
		userViewButton.setBounds(348, 235, 383, 46);
		userViewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (tree.getSelectionPath() == null) {
                    JOptionPane.showMessageDialog(null, "Please select a user to view.", "User View Error", JOptionPane.INFORMATION_MESSAGE);
                } 
                else {
                    DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent();
                    if (selectedElement.getUserObject() instanceof UserGroup || selectedElement.getUserObject().toString().equals("Root")) {
                        JOptionPane.showMessageDialog(null, "Please select a user to view.", "User View Error", JOptionPane.INFORMATION_MESSAGE);
                    } 
                    if (selectedElement.getUserObject() instanceof User) {
                        selectedUser = selectedElement.getUserObject().toString();
                        User user = new User(selectedUser);
                        UserView userView = userViews.get(selectedUser);
                        userView.setVisible(true);
                        userView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    }
                }
            }
        });
		contentPane.add(userViewButton);
		
		
		//total users
		JButton userTotalButton = new JButton();
		userTotalButton.setText("Show User Total");
		userTotalButton.setBounds(348, 294, 184, 55);
        userTotalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	//visitor display
            	TotalUsersDisplayVisitor u = new TotalUsersDisplayVisitor(users);
            	b.accept(u);
            }
        });
		contentPane.add(userTotalButton);
		
		
		//total groups
		JButton groupTotalButton = new JButton();
		groupTotalButton.setText("Show Group Total");
		groupTotalButton.setBounds(547, 294, 184, 55);
		groupTotalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	//visitor display
            	TotalGroupsDisplayVisitor u = new TotalGroupsDisplayVisitor(groups);
            	b.accept(u);
            }
        });
		contentPane.add(groupTotalButton);
		
		
		//total msgs
		JButton msgTotalButton = new JButton();
		msgTotalButton.setText("Show Messages Total");
		msgTotalButton.setBounds(348, 362, 184, 57);
		msgTotalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	//visitor display
            	TotalMsgsDisplayVisitor t = new TotalMsgsDisplayVisitor(users);
            	b.accept(t);
            }
        });
		contentPane.add(msgTotalButton);
		
		JButton posPercentageButton = new JButton();
		posPercentageButton.setText("Show Positive Percentage");
		posPercentageButton.setBounds(547, 364, 184, 55);
		posPercentageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	//visitor display
            	TotalPositivePercentDisplayVisitor p = new TotalPositivePercentDisplayVisitor(users);
            	b.accept(p);
            }
        });
		contentPane.add(posPercentageButton);
		
		JButton IDverification = new JButton();
		IDverification.setText("Verify IDs");
		IDverification.setBounds(348, 432, 184, 57);
		IDverification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// check if there are IDs first
                if(users.size() == 0 && groups.size()-1 == 0) {
    				JOptionPane.showMessageDialog(null, "There are no IDs!", "ID Verification Error", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                	boolean unique = false;
                	//check if groupIDs have any userIDs
                	for (String u : userIDs) {
                		if(groupIDs.contains(u)) {
                			unique = false;
                		}
        				else {
        					unique = true;
        				}
                	}
                	//check if userIDs have any groupIDs
                   	for (String g : groupIDs) {
                		if(userIDs.contains(g)) {
                			unique = false;
                		}
        				else {
        					unique = true;
        				}
                	}
                   if (unique) {
                	   JOptionPane.showMessageDialog(null, "All IDs are unique!", "ID Verification", JOptionPane.INFORMATION_MESSAGE);
                   }
                   else {
                	   JOptionPane.showMessageDialog(null, "There is an invalid ID!", "ID Verification Error", JOptionPane.INFORMATION_MESSAGE);
                   }
                	
                }
				
			}
		});
		contentPane.add(IDverification);
		
		JButton lastUpdatedUser = new JButton();
		lastUpdatedUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//check for any users
				if (users.size() == 0) {
					JOptionPane.showMessageDialog(null, "No users have updated.", "Last Updated User Error", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					//check for any messages
					int msgCounter = 0;
					for (User u : users) {
						msgCounter = msgCounter + u.getMsgs().size();
					}
					if (msgCounter == 0) {
						JOptionPane.showMessageDialog(null, "No users have updated.", "Last Updated User Error", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						long latestUpdate = users.get(0).getLastUpdateTime();
						String lastUpdatedUser = users.get(0).getID();
						for (User v : users) {
							if(v.getLastUpdateTime() > latestUpdate) {
								latestUpdate = v.getLastUpdateTime();
								lastUpdatedUser = v.getID();
							}
						}
						JOptionPane.showMessageDialog(null, "Last update was from: " + lastUpdatedUser, "Last Updated User", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		lastUpdatedUser.setText("Show Last Updated User");
		lastUpdatedUser.setBounds(547, 432, 184, 55);
		contentPane.add(lastUpdatedUser);
	}
	
    private void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
        for (int i = startingIndex; i < rowCount; ++i) {
            tree.expandRow(i);
        }

        if (tree.getRowCount() != rowCount) {
            expandAllNodes(tree, rowCount, tree.getRowCount());

        }
    }
}
