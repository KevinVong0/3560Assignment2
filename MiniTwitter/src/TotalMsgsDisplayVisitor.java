
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class TotalMsgsDisplayVisitor implements ButtonVisitor{

	private int total = 0;
	ArrayList<User> users;
	
	public TotalMsgsDisplayVisitor(ArrayList<User> users) {
		this.users = users;
		
    	for(User u : users) {
    		total = total + u.getTotalMsgs();
    	}
	}

	@Override
	public void visit(TotalMsgsButton totalMsgsButton) {
		JOptionPane.showMessageDialog(null, "There are a total of " + total + " message(s).", "Show Message Total", JOptionPane.INFORMATION_MESSAGE);

	}

	@Override
	public void visit(TotalUsersButton totalUsersButton) {
	}

	@Override
	public void visit(TotalGroupsButton totalGroupsButton) {

	}

	@Override
	public void visit(TotalPositivePercentButton positivePercentButton) {
		
	}
}
