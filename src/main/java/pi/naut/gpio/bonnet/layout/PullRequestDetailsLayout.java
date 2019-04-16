package pi.naut.gpio.bonnet.layout;

import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;
import pi.naut.ApplicationState;
import pi.naut.github.model.PullRequest;
import pi.naut.gpio.bonnet.Layout;
import pi.naut.gpio.bonnet.OLEDBonnet;
import pi.naut.gpio.bonnet.display.DisplayComponents;
import pi.naut.gpio.input.listener.NavigateToLayoutListener;
import util.StateList;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static pi.naut.gpio.PinConfiguration.BUTTON_A;

@Singleton
public class PullRequestDetailsLayout implements Layout {

	@Inject
	private DisplayComponents displayComponents;
	@Inject
	private ApplicationState applicationState;

	public static final String TITLE = "PR DETAILS";

	@Override
	public boolean isPrimary() { return false; }

	@Override
	public void bufferComponents() {
		displayComponents.titleBar(TITLE);
		displayComponents.paginatedList(getPullRequestDetails());
	}

	private StateList getPullRequestDetails() {
		PullRequest current = applicationState.getPullRequests().current();
		return new StateList<>(asList(
				"TITLE: " + current.getTitle(),
				"NO: " + current.getNumber(),
				"MERGEABLE: " + current.isMergable(),
				"MERGED: " + current.isMerged()
		));
	}

	@Override
	public Map<String, GpioPinListener> applyListeners(OLEDBonnet oledBonnet) {
		Map<String, GpioPinListener> listenerMap = new HashMap<>();
		listenerMap.put(BUTTON_A, new NavigateToLayoutListener(oledBonnet, PullRequestLayout.class));
		return listenerMap;
	}

	@Override
	public Map<String, GpioTrigger> applyTriggers(OLEDBonnet oledBonnet) {
		return new HashMap<>();
	}

}
