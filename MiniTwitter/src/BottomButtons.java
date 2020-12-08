
public class BottomButtons implements Buttons {

	Buttons[] buttons;

	public BottomButtons() {
		buttons = new Buttons[] { new TotalMsgsButton(), new TotalUsersButton(), new TotalGroupsButton(), new TotalPositivePercentButton()};
	}
	public void accept(ButtonVisitor buttonVisitor) {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].accept(buttonVisitor);
		}
	}

}
