package pi.naut.gpio.bonnet.layout;

import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;
import pi.naut.ApplicationState;
import pi.naut.gpio.bonnet.Layout;
import pi.naut.gpio.bonnet.OLEDBonnet;
import pi.naut.gpio.bonnet.display.DisplayComponents;
import pi.naut.gpio.bonnet.input.listener.DecrementItemListener;
import pi.naut.gpio.bonnet.input.listener.IncrementItemListener;
import pi.naut.gpio.bonnet.input.listener.NavigateToLayoutListener;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

import static pi.naut.gpio.config.PinConfiguration.*;

@Singleton
public class PullRequestLayout implements Layout {

	@Inject
	private DisplayComponents displayComponents;
	@Inject
	private ApplicationState applicationState;

	@Inject
	private PullRequestDetailsLayout pullRequestDetailsLayout;

	public static final String NAME = "PULL REQUESTS";

	@PostConstruct
	private void init() {
		applicationState.updatePullRequests();
	}

	@Override
	public String name() {
		return NAME;
	}

	@Override
	public void bufferDisplayComponents() {
		displayComponents.titleBar(NAME);
		displayComponents.scrollableList(
				applicationState.getPullRequests().getList(),
				applicationState.getPullRequests().currentIndex());
	}

	@Override
	public Map<String, GpioPinListener> applyListenerConfiguration(OLEDBonnet oledBonnet) {
		Map<String, GpioPinListener> listenerMap = new HashMap<>();

		listenerMap.put(JOYSTICK_UP, new DecrementItemListener(oledBonnet, applicationState));
		listenerMap.put(JOYSTICK_DOWN, new IncrementItemListener(oledBonnet, applicationState));

		listenerMap.put(BUTTON_B, new NavigateToLayoutListener(oledBonnet, pullRequestDetailsLayout));

		return listenerMap;
	}

	@Override
	public Map<String, GpioTrigger> applyTriggerConfiguration(OLEDBonnet oledBonnet) { return new HashMap<>(); }

}
