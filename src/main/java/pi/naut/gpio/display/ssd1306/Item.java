package pi.naut.gpio.display.ssd1306;

public class Item {

	private String content;

	private boolean selected;

	public Item(String content, boolean selected) {
		this.content = content;
		this.selected = selected;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
