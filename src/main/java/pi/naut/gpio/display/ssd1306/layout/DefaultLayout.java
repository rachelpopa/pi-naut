package pi.naut.gpio.display.ssd1306.layout;

import lombok.Getter;
import lombok.Setter;
import net.fauxpark.oled.SSD1306;
import pi.naut.gpio.display.Layout;
import pi.naut.gpio.display.ssd1306.Action;
import pi.naut.gpio.display.ssd1306.Item;
import pi.naut.gpio.display.ssd1306.SSD1306Component;

import java.util.List;

public class DefaultLayout implements Layout {

	private SSD1306Component ssd1306Component = new SSD1306Component();

	private String title;

	@Getter
	@Setter
	private List<Action> actions;

	@Getter
	@Setter
	private List<Item> items;

	public DefaultLayout(String title, List<Item> items, List<Action> actions) {
		this.title = title;
		this.items = items;
		this.actions = actions;
	}

	@Override
	public void addTo(SSD1306 controller) {
		ssd1306Component.bufferTitle(controller, title);
		ssd1306Component.bufferVisibleItems(controller, items);
		ssd1306Component.bufferActionButtons(controller, actions);
	}

}
