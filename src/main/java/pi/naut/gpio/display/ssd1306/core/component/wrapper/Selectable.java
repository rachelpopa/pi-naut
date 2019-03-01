package pi.naut.gpio.display.ssd1306.core.component.wrapper;

public class SelectableComponent {

	private boolean selected = false;

	public SelectableComponent() {}

	public SelectableComponent(Boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
