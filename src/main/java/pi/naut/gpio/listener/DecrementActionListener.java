package pi.naut.gpio.listener;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import pi.naut.gpio.display.ssd1306.layout.DefaultLayout;

public class DecrementActionListener implements GpioPinListenerDigital {

	private DefaultLayout defaultLayout;
	private long actionSize;

	public DecrementActionListener(DefaultLayout defaultLayout) {
		this.defaultLayout = defaultLayout;
		this.actionSize = defaultLayout.getPullRequests().size();
	}

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		System.out.println("Decrement Action index and re-display layout");
	}

}
