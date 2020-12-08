
import java.util.List;

import javax.swing.JOptionPane;

public class TotalGroupsDisplayVisitor implements ButtonVisitor{
	private int total = 0;
	List<UserGroup> groups;
	
	public TotalGroupsDisplayVisitor(List<UserGroup> groups) {
		this.groups = groups;
	}
	
	@Override
	public void visit(TotalUsersButton totalUsersButton) {
		
	}
	
	@Override
	public void visit(TotalMsgsButton totalMsgsButton) {
		
	}

	@Override
	public void visit(TotalGroupsButton totalGroupsButton) {
		JOptionPane.showMessageDialog(null, "There are a total of " + (groups.size()-1) + " group(s).", "Show Group Total", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void visit(TotalPositivePercentButton positivePercentButton) {
		
	}

}
