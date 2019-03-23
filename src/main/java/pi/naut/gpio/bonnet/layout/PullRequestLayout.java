package pi.naut.gpio.display.layout;

import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;
import net.fauxpark.oled.SSD1306;
import pi.naut.github.GithubService;
import pi.naut.github.model.PullRequest;
import pi.naut.gpio.display.Layout;
import pi.naut.gpio.display.core.buffer.ComponentBuffer;
import pi.naut.gpio.display.core.component.wrapper.Selectable;
import pi.naut.gpio.listener.DecrementItemListener;
import pi.naut.gpio.listener.HueTestListener;
import pi.naut.gpio.listener.IncrementItemListener;
import pi.naut.gpio.listener.DisplayCurrentParentLayoutListener;
import util.CircularIterator;
import util.CircularList;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static pi.naut.gpio.config.PinConfiguration.*;

@Singleton
public class PullRequestLayout implements Layout {

	@Inject
	private GithubService githubService;
	@Inject
	private HueTestListener hueTestListener;
	@Inject
	private IncrementItemListener incrementItemListener;
	@Inject
	private DecrementItemListener decrementItemListener;

	private ComponentBuffer componentBuffer = new ComponentBuffer();

	private static final String TITLE = "PULL REQUESTS";

	private CircularList<PullRequest> pullRequestCircularList;
	private CircularIterator<PullRequest> pullRequestCircularIterator;

	@Override
	@PostConstruct
	public void init() { updatePullRequests(); }

	@Override
	public void bufferTo(SSD1306 displayController) {
		componentBuffer.titleBar(displayController, TITLE);
		componentBuffer.scrollableList(
				displayController,
				pullRequestCircularIterator.current(),
				pullRequestCircularList.getList()
				.stream()
				.map(Selectable::new)
				.collect(toList()));
	}

	@Override
	public Map<String, GpioPinListener> getListenerConfiguration() {
		Map<String, GpioPinListener> listenerMap = new HashMap<>();

//		listenerMap.put(JOYSTICK_LEFT, new DecrementActionListener());
//		listenerMap.put(JOYSTICK_RIGHT, new IncrementActionListener());
		listenerMap.put(JOYSTICK_UP, incrementItemListener);
		listenerMap.put(JOYSTICK_DOWN, decrementItemListener);

		listenerMap.put(BUTTON_A, hueTestListener);
		listenerMap.put(BUTTON_B, new DisplayCurrentParentLayoutListener());

		return listenerMap;
	}

	@Override
	public Map<String, GpioTrigger> getTriggerConfiguration() {
		Map<String, GpioTrigger> triggerMap = new HashMap<>();
//		triggerMap.put(BUTTON_B, new GpioCallbackTrigger(() -> {
//			return null;
//		}));
		return triggerMap;
	}

	public CircularList<PullRequest> getPullRequestCircularList() {
		return pullRequestCircularList;
	}

	public CircularIterator<PullRequest> getPullRequestCircularIterator() {
		return pullRequestCircularIterator;
	}

	public void updatePullRequests() {
		this.pullRequestCircularList = new CircularList<>(githubService.getOpenPullRequests());
		this.pullRequestCircularIterator = this.pullRequestCircularList.iterator();
	}

}
