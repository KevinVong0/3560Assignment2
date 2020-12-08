
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class TotalUsersDisplayVisitor implements ButtonVisitor {

	private int total = 0;
	ArrayList<User> users;
	
	public TotalUsersDisplayVisitor(ArrayList<User> users) {
		this.users = users;
	}
	
	@Override
	public void visit(TotalUsersButton totalUsersButton) {
		JOptionPane.showMessageDialog(null, "There are a total of " + users.size() + " user(s).", "Show User Total", JOptionPane.INFORMATION_MESSAGE);
	}

	
	@Override
	public void visit(TotalMsgsButton totalMsgsButton) {
		
	}

	@Override
	public void visit(TotalGroupsButton totalGroupsButton) {

	}

	@Override
	public void visit(TotalPositivePercentButton positivePercentButton) {
		
	}

}
