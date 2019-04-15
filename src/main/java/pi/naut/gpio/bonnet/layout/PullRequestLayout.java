package pi.naut.gpio.bonnet.layout;

import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;
import io.micronaut.context.event.ApplicationEventPublisher;
import pi.naut.ApplicationState;
import pi.naut.gpio.bonnet.Layout;
import pi.naut.gpio.bonnet.OLEDBonnet;
import pi.naut.gpio.bonnet.display.DisplayComponents;
import pi.naut.gpio.input.listener.NavigateToLayoutListener;
import pi.naut.gpio.input.listener.NextStateListener;
import pi.naut.gpio.input.listener.PreviousStateListener;

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
	private ApplicationEventPublisher applicationEventPublisher;

	public static final String NAME = "PULL REQUESTS";

	@Override
	public String name() {
		return NAME;
	}

	@Override
	public boolean isPrimary() { return true; }

	@Override
	public void bufferComponents() {
		displayComponents.titleBar(NAME);
		displayComponents.scrollableList(applicationState.getPullRequests());
	}

	@Override
	public Map<String, GpioPinListener> applyListeners(OLEDBonnet oledBonnet) {
		Map<String, GpioPinListener> listenerMap = new HashMap<>();

		listenerMap.put(JOYSTICK_UP, new PreviousStateListener(
				applicationEventPublisher, applicationState.getPullRequests(), PullRequestLayout.NAME));
		listenerMap.put(JOYSTICK_DOWN, new NextStateListener(
				applicationEventPublisher, applicationState.getPullRequests(), PullRequestLayout.NAME));

		listenerMap.put(BUTTON_B, new NavigateToLayoutListener(oledBonnet, PullRequestDetailsLayout.NAME));

		return listenerMap;
	}

	@Override
	public Map<String, GpioTrigger> applyTriggers(OLEDBonnet oledBonnet) {
		return new HashMap<>();
	}

}
