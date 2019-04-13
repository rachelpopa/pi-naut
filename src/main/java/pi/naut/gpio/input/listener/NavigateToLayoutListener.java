package pi.naut.gpio.input.listener;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import pi.naut.gpio.bonnet.OLEDBonnet;

public class NavigateToLayoutListener implements GpioPinListenerDigital {

	private OLEDBonnet oledBonnet;
	private String layoutName;

	public NavigateToLayoutListener(OLEDBonnet oledBonnet, String layoutName) {
		this.oledBonnet = oledBonnet;
		this.layoutName = layoutName;
	}

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		if (event.getState().isHigh()) {
			oledBonnet.displayLayout(layoutName);
		}
	}

}
