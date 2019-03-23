package pi.naut.gpio.bonnet.layout;

import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;
import pi.naut.ApplicationState;
import pi.naut.gpio.bonnet.Layout;
import pi.naut.gpio.bonnet.OLEDBonnet;
import pi.naut.gpio.bonnet.display.DisplayComponents;
import pi.naut.gpio.bonnet.input.listener.NavigateToCurrentPrimaryLayoutListener;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

import static pi.naut.gpio.config.PinConfiguration.BUTTON_A;

@Singleton
public class PullRequestDetailsLayout implements Layout {

	@Inject
	private DisplayComponents displayComponents;
	@Inject
	private ApplicationState applicationState;

	public static final String NAME = "PR DETAILS";

	@Override
	public String name() {
		return NAME;
	}

	@Override
	public void bufferDisplayComponents() {
		displayComponents.titleBar(NAME);
		displayComponents.pullRequestDetails(applicationState.getPullRequests().current());
	}

	@Override
	public Map<String, GpioPinListener> applyListenerConfiguration(OLEDBonnet oledBonnet) {
		Map<String, GpioPinListener> listenerMap = new HashMap<>();
		listenerMap.put(BUTTON_A, new NavigateToCurrentPrimaryLayoutListener(oledBonnet));
		return listenerMap;
	}

	@Override
	public Map<String, GpioTrigger> applyTriggerConfiguration(OLEDBonnet oledBonnet) {
		return new HashMap<>();
	}

}
