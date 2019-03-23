package pi.naut.gpio.bonnet.input.listener;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import pi.naut.gpio.bonnet.Layouts;
import pi.naut.gpio.bonnet.OLEDBonnet;

public class NavigateToCurrentPrimaryLayoutListener implements GpioPinListenerDigital {

	private OLEDBonnet oledBonnet;
	private Layouts layouts;

	public NavigateToCurrentPrimaryLayoutListener(OLEDBonnet oledBonnet, Layouts layouts) {
		this.oledBonnet = oledBonnet;
		this.layouts = layouts;
	}

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		if (event.getState().isHigh()) {
			if (layouts.getPrimaryLayouts().hasNext()) {
				oledBonnet.displayLayout(layouts.getPrimaryLayouts().next());
				oledBonnet.setIsPrimaryLayout(true);
			}
		}
	}

}
