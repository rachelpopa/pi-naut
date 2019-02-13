package pi.naut.gpio.display.ssd1306.layout;

import net.fauxpark.oled.SSD1306;
import pi.naut.gpio.display.Layout;
import pi.naut.gpio.display.ssd1306.Action;
import pi.naut.gpio.display.ssd1306.Item;
import pi.naut.gpio.display.ssd1306.SSD1306Component;

import java.util.List;

public class DefaultLayout implements Layout {

	private SSD1306Component ssd1306Component = new SSD1306Component();

	private String title;

	private List<Action> actions;

	private List<Item> items;

	public DefaultLayout(String title, List<Item> items, List<Action> actions) {
		this.title = title;
		this.items = items;
		this.actions = actions;
	}

	@Override
	public void bufferComponentsTo(SSD1306 controller) {
		ssd1306Component.titleBar(controller, title);
		ssd1306Component.scrollableList(controller, items);
		ssd1306Component.actionBar(controller, actions);
	}

	@Override
	public void replaceListeners() {}

	public List<Action> getActions() {
		return actions;
	}

	public List<Item> getItems() {
		return items;
	}

	public String getTitle() {
		return title;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
