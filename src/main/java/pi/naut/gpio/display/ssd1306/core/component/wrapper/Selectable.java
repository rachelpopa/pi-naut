package pi.naut.gpio.display.ssd1306.core.component.wrapper;

public class Selectable {

	private Object object;
	private boolean selected = false;

	public Selectable() {}

	public Selectable(Object object) {
		this.object = object;
	}

	public Selectable(Boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Object getObject() {
		return object;
	}

	@Override
	public String toString() {
		return object.toString();
	}
}
