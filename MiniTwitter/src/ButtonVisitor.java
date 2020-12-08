
public interface ButtonVisitor {
    
//    public void visit(PositivePercentButton ppButton);
    
	public void visit(TotalMsgsButton totalMsgsButton);

	public void visit(TotalUsersButton totalUsersButton);
	
	public void visit(TotalGroupsButton totalGroupsButton);

	public void visit(TotalPositivePercentButton positivePercentButton);


}