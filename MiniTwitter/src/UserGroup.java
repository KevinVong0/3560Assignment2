import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

public class UserGroup implements Composite{
	
	private String id;
	private int totalGroups = 0;
    private List<User> users = new ArrayList();
	
	public UserGroup(String id) {
		this.id = id;
		totalGroups++;
	}
	
	@Override
	public String getID() {
		return id;
	}

	public int getTotalGroups() {
		return totalGroups;
	}
	@Override
	public String toString() {
		return id;
	}


}
