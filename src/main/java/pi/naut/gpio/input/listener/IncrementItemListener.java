package pi.naut.gpio.input.listener;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import pi.naut.ApplicationState;
import pi.naut.gpio.bonnet.OLEDBonnet;

public class IncrementItemListener implements GpioPinListenerDigital {

	private OLEDBonnet oledBonnet;
	private ApplicationState applicationState;

	public IncrementItemListener(OLEDBonnet oledBonnet, ApplicationState applicationState) {
		this.applicationState = applicationState;
		this.oledBonnet = oledBonnet;
	}

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		if (event.getState().isHigh()) {
			applicationState.getPullRequests().next();
			oledBonnet.redisplayLayout();
		}
	}

}
