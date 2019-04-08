package pi.naut.gpio.bonnet.layout;

import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;
import pi.naut.ApplicationState;
import pi.naut.gpio.bonnet.Layout;
import pi.naut.gpio.bonnet.OLEDBonnet;
import pi.naut.gpio.bonnet.display.DisplayComponents;
import pi.naut.gpio.input.listener.DecrementStateListener;
import pi.naut.gpio.input.listener.IncrementStateListener;
import pi.naut.gpio.input.listener.NavigateToLayoutListener;

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

	public static final String NAME = "PULL REQUESTS";

	@PostConstruct
	void initialize() { applicationState.updatePullRequests(); }

	@Override
	public String name() { return NAME; }

	@Override
	public boolean primary() { return true; }

	@Override
	public void displayComponents() {
		displayComponents.titleBar(NAME);
		displayComponents.scrollableList(applicationState.getPullRequests());
	}

	@Override
	public Map<String, GpioPinListener> applyListeners(OLEDBonnet oledBonnet) {
		Map<String, GpioPinListener> listenerMap = new HashMap<>();

		listenerMap.put(JOYSTICK_UP, new DecrementStateListener(oledBonnet, applicationState));
		listenerMap.put(JOYSTICK_DOWN, new IncrementStateListener(oledBonnet, applicationState));

		listenerMap.put(BUTTON_B, new NavigateToLayoutListener(oledBonnet, PullRequestDetailsLayout.NAME));

		return listenerMap;
	}

	@Override
	public Map<String, GpioTrigger> applyTriggers(OLEDBonnet oledBonnet) { return new HashMap<>(); }

}
