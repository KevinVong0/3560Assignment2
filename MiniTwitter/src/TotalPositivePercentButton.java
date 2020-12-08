


public class TotalPositivePercentButton implements Buttons{

	@Override
	public void accept(ButtonVisitor buttonVisitor) {
		buttonVisitor.visit(this);
	}


}
