

public class TotalUsersButton implements Buttons {

	@Override
	public void accept(ButtonVisitor buttonVisitor) {
		buttonVisitor.visit(this);
	}

}
