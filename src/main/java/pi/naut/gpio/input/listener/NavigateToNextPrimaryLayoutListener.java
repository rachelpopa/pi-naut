package pi.naut.gpio.input.listener;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import pi.naut.gpio.bonnet.Layouts;
import pi.naut.gpio.bonnet.OLEDBonnet;

public class NavigateToNextPrimaryLayoutListener implements GpioPinListenerDigital {

	private OLEDBonnet oledBonnet;
	private Layouts layouts;

	public NavigateToNextPrimaryLayoutListener(OLEDBonnet oledBonnet, Layouts layouts) {
		this.oledBonnet = oledBonnet;
		this.layouts = layouts;
	}

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		if (event.getState().isHigh()) {
			if (layouts.getPrimaryLayouts().hasNext()) {
				oledBonnet.setIsPrimaryLayout(true);
				oledBonnet.displayLayout(layouts.getPrimaryLayouts().next());
			}
		}
	}

}
