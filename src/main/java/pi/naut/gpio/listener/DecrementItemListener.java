package pi.naut.gpio.listener;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import pi.naut.gpio.display.ssd1306.layout.DefaultLayout;

public class DecrementItemListener implements GpioPinListenerDigital {

	private DefaultLayout defaultLayout;
	private long itemSize;

	public DecrementItemListener(DefaultLayout defaultLayout) {
		this.defaultLayout = defaultLayout;
		this.itemSize = defaultLayout.getItems().size();
	}

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		System.out.println("Decrement Item index and re-display layout");
	}

}
