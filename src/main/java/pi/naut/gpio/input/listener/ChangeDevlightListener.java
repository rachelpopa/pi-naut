package pi.naut.gpio.input.listener;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import pi.naut.devlight.DevlightClient;
import pi.naut.devlight.DevlightState;

public class ChangeDevlightListener implements GpioPinListenerDigital {

	private DevlightClient devlightClient;
	private DevlightState devlightState;

	public ChangeDevlightListener(DevlightClient devlightClient, DevlightState devlightState) {
		this.devlightClient = devlightClient;
		this.devlightState = devlightState;
	}

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		if (event.getState().isHigh()) {
			devlightClient.setLightState(devlightState.getLightState());
		}
	}

}
