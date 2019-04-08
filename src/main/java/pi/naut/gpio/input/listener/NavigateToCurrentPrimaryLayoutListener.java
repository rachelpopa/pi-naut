package pi.naut.gpio.input.listener;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import pi.naut.gpio.bonnet.OLEDBonnet;

public class NavigateToCurrentPrimaryLayoutListener implements GpioPinListenerDigital {

	private OLEDBonnet oledBonnet;

	public NavigateToCurrentPrimaryLayoutListener(OLEDBonnet oledBonnet) {
		this.oledBonnet = oledBonnet;
	}

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		if (event.getState().isHigh()) {
			oledBonnet.displayPrimaryLayout();
		}
	}

}
