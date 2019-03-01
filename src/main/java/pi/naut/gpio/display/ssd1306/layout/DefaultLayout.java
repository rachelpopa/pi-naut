package pi.naut.gpio.display.ssd1306.layout;

import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;
import net.fauxpark.oled.SSD1306;
import pi.naut.github.model.PullRequest;
import pi.naut.gpio.display.Layout;
import pi.naut.gpio.display.ssd1306.core.buffer.ComponentBuffer;
import pi.naut.gpio.display.ssd1306.core.component.Action;
import pi.naut.gpio.listener.ActionListener;
import pi.naut.gpio.listener.ButtonListener;
import pi.naut.gpio.listener.ChangeLayoutListener;
import pi.naut.gpio.listener.DecrementActionListener;
import pi.naut.gpio.listener.DecrementItemListener;
import pi.naut.gpio.listener.IncrementActionListener;
import pi.naut.gpio.listener.IncrementItemListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pi.naut.gpio.config.PinConfiguration.*;

public class DefaultLayout implements Layout {

	private ComponentBuffer componentBuffer = new ComponentBuffer();

	private String title;

	private List<PullRequest> pullRequests;
	private int actionIndex = 0;

	private List<Action> actions;
	private int itemIndex = 0;

	public DefaultLayout(String title, List<Action> actions, List<PullRequest> pullRequests) {
		this.title = title;
		this.actions = actions;
		this.pullRequests = pullRequests;
	}

	@Override
	public void bufferLayoutTo(SSD1306 displayController) {
		componentBuffer.titleBar(displayController, title);
		componentBuffer.scrollableList(displayController, pullRequests);
		componentBuffer.actionBar(displayController, actions);
	}

	@Override
	public Map<String, GpioPinListener> getListenerConfig() {
		Map<String, GpioPinListener> listenerMap = new HashMap<>();

		listenerMap.put(JOYSTICK_LEFT, new DecrementActionListener(this));
		listenerMap.put(JOYSTICK_RIGHT, new IncrementActionListener(this));
		listenerMap.put(JOYSTICK_CENTER, new ChangeLayoutListener(this));
		listenerMap.put(JOYSTICK_UP, new IncrementItemListener(this));
		listenerMap.put(JOYSTICK_DOWN, new DecrementItemListener(this));

		listenerMap.put(BUTTON_A, new ButtonListener());
		listenerMap.put(BUTTON_B, new ActionListener(this));

		return listenerMap;
	}

	@Override
	public Map<String, GpioTrigger> getTriggerConfig() {
		return new HashMap<>();
	}

	public List<PullRequest> getPullRequests() {
		return pullRequests;
	}

	public List<Action> getActions() {
		return actions;
	}

	public String getTitle() {
		return title;
	}

	public void setPullRequests(List<PullRequest> pullRequests) {
		this.pullRequests = pullRequests;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public void setTitle(String title) {
		this.title = title;
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
}
