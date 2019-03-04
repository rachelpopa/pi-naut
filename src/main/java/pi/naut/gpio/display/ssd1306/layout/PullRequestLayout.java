package pi.naut.gpio.display.ssd1306.layout;

import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;
import net.fauxpark.oled.SSD1306;
import pi.naut.github.GithubService;
import pi.naut.github.model.PullRequest;
import pi.naut.gpio.display.Layout;
import pi.naut.gpio.display.ssd1306.core.buffer.ComponentBuffer;
import pi.naut.gpio.display.ssd1306.core.component.Action;
import pi.naut.gpio.display.ssd1306.core.component.wrapper.Selectable;
import pi.naut.gpio.listener.ActionListener;
import pi.naut.gpio.listener.ButtonListener;
import pi.naut.gpio.listener.DecrementActionListener;
import pi.naut.gpio.listener.DecrementItemListener;
import pi.naut.gpio.listener.IncrementActionListener;
import pi.naut.gpio.listener.IncrementItemListener;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static pi.naut.gpio.config.PinConfiguration.*;

@Singleton
public class PullRequestLayout implements Layout {

	@Inject
	private GithubService githubService;

	private ComponentBuffer componentBuffer = new ComponentBuffer();

	private static final String TITLE = "PULL REQUESTS";

	private List<PullRequest> pullRequests = new ArrayList<>();
	private int prIndex = 0;

	private List<Action> actions = new ArrayList<>();
	private int actionIndex = 0;

	@Override
	@PostConstruct
	public void init() {
		Action details = new Action("DETAILS", "DET", "De", f -> null);
		details.setSelected(true);
		this.actions.add(details);
		this.pullRequests = githubService.getOpenPullRequests();
	}

	@Override
	public void bufferTo(SSD1306 displayController) {
		componentBuffer.titleBar(displayController, TITLE);
		componentBuffer.scrollableList(displayController, pullRequests
				.stream()
				.map(Selectable::new)
				.collect(toList()));
		componentBuffer.actionBar(displayController, actions);
	}

	@Override
	public Map<String, GpioPinListener> getListenerConfiguration() {
		Map<String, GpioPinListener> listenerMap = new HashMap<>();

		listenerMap.put(JOYSTICK_LEFT, new DecrementActionListener(this));
		listenerMap.put(JOYSTICK_RIGHT, new IncrementActionListener(this));
		listenerMap.put(JOYSTICK_UP, new IncrementItemListener(this));
		listenerMap.put(JOYSTICK_DOWN, new DecrementItemListener(this));

		listenerMap.put(BUTTON_A, new ButtonListener());
		listenerMap.put(BUTTON_B, new ActionListener(this));

		return listenerMap;
	}

	@Override
	public Map<String, GpioTrigger> getTriggerConfiguration() {
		return new HashMap<>();
	}

	public List<PullRequest> getPullRequests() {
		return pullRequests;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setPullRequests(List<PullRequest> pullRequests) {
		this.pullRequests = pullRequests;
	}

}
