package pi.naut.gpio.input.listener;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import pi.naut.gpio.bonnet.Layout;
import pi.naut.gpio.bonnet.OLEDBonnet;

public class NavigateToLayoutListener implements GpioPinListenerDigital {

	private OLEDBonnet oledBonnet;
	private Layout layout;

	public NavigateToLayoutListener(OLEDBonnet oledBonnet, Layout layout) {
		this.oledBonnet = oledBonnet;
		this.layout = layout;
	}

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		if (event.getState().isHigh()) {
			oledBonnet.setIsPrimaryLayout(false);
			oledBonnet.displayLayout(layout);
//			ApplicationContext.run().publishEvent(new RefreshEvent()); // use refresh events to redisplay layout
		}
	}

}
