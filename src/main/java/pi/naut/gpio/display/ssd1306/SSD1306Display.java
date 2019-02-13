package pi.naut.gpio.display.ssd1306;

import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.i2c.I2CBus;
import net.fauxpark.oled.SSD1306;
import net.fauxpark.oled.transport.I2CTransport;
import pi.naut.gpio.display.Display;
import pi.naut.gpio.display.Layout;

import javax.inject.Singleton;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Singleton
public class SSD1306Display implements Display {

	private List<Action> actions;

	private List<Item> items;

	private int actionIndex = 0;

	private int itemIndex = 0;

	private SSD1306 controller = new SSD1306(128, 64, new I2CTransport(RaspiPin.GPIO_15, I2CBus.BUS_1, 0x3c));

	@Override
	public void addLayout(Layout layout) {
		layout.bufferComponentsTo(controller);
		layout.replaceListeners();
	}

	@Override
	public void clear() {
		controller.clear();
	}

	@Override
	public void clearDisplay() {
		controller.clear();
		controller.display();
	}

	@Override
	public void display() {
		controller.display();
	}

	@Override
	public void displayLayout(Layout layout) {
		controller.clear();
		layout.bufferComponentsTo(controller);
		layout.replaceListeners();
		controller.display();
	}

	@Override
	public void displayTimedlayout(Layout layout, long layoutDurationInMs) {
		byte[] buffer = controller.getBuffer();
		displayLayout(layout);
		Timer timer = new Timer();
		timer.schedule(displayTimerTask(buffer), layoutDurationInMs);
		controller.display();
	}

	@Override
	public TimerTask displayTimerTask(byte[] buffer) {
		return new TimerTask() {
			public void run() {
				controller.setBuffer(buffer);
			}
		};
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public int getActionIndex() {
		return actionIndex;
	}

	public void setActionIndex(int actionIndex) {
		this.actionIndex = actionIndex;
	}

	public int getItemIndex() {
		return itemIndex;
	}

	public void setItemIndex(int itemIndex) {
		this.itemIndex = itemIndex;
	}

	public SSD1306 getController() {
		return controller;
	}

}
