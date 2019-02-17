package pi.naut.gpio.listener;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import pi.naut.gpio.display.ssd1306.layout.DefaultLayout;

public class ChangeLayoutListener implements GpioPinListenerDigital {

	public ChangeLayoutListener(DefaultLayout defaultLayout) {
	}

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
			System.out.println("Change Layout!");
	}

}
