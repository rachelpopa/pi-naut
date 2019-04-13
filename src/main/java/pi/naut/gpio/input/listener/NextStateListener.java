package pi.naut.gpio.input.listener;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import io.micronaut.context.event.ApplicationEventPublisher;
import pi.naut.gpio.bonnet.RefreshDisplayEvent;
import util.StateList;

public class NextStateListener implements GpioPinListenerDigital {

	ApplicationEventPublisher applicationEventPublisher;
	private StateList stateList;
	private String[] layoutNames;

	public NextStateListener(ApplicationEventPublisher applicationEventPublisher, StateList stateList, String... layoutNames) {
		this.stateList = stateList;
		this.applicationEventPublisher = applicationEventPublisher;
		this.layoutNames = layoutNames;
	}

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		if (event.getState().isHigh()) {
			stateList.next();
			applicationEventPublisher.publishEvent(new RefreshDisplayEvent(layoutNames));
		}
	}

}
