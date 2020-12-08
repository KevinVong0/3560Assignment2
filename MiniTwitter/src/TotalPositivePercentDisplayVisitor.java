
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class TotalPositivePercentDisplayVisitor implements ButtonVisitor{

	private double totalMsgs = 0;
	private double totalPos = 0;
	private double percentage = 0;
	private ArrayList<User> users;
	DecimalFormat df = new DecimalFormat("#.0");
	public TotalPositivePercentDisplayVisitor(ArrayList<User> users) {
		this.users = users;
		
    	for(User u : users) {
    		totalMsgs = totalMsgs + u.getTotalMsgs();
    		totalPos = totalPos + u.getTotalPositive();
    		percentage = (totalPos/totalMsgs) * 100;
    	}
	}
	
	
	@Override
	public void visit(TotalMsgsButton totalMsgsButton) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TotalUsersButton totalUsersButton) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TotalGroupsButton totalGroupsButton) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TotalPositivePercentButton totalPositivePercentButton) {
		JOptionPane.showMessageDialog(null, "The percent of positive messages is: " +df.format(percentage) + "%.", "Show Positive Percentage", JOptionPane.INFORMATION_MESSAGE);
		
	}
	

}
