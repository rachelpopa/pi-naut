package pi.naut.gpio.controller;

import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.i2c.I2CBus;
import io.micronaut.discovery.event.ServiceShutdownEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import net.fauxpark.oled.SSD1306;
import net.fauxpark.oled.transport.I2CTransport;
import pi.naut.gpio.bonnet.Layout;
import pi.naut.gpio.bonnet.display.DisplayConstants;

import javax.inject.Singleton;
import java.util.Timer;
import java.util.TimerTask;

@Singleton
public class DisplayController {

	private final SSD1306 ssd1306 = new SSD1306(
			DisplayConstants.MAX_WIDTH, DisplayConstants.MAX_HEIGHT,
			new I2CTransport(RaspiPin.GPIO_15, I2CBus.BUS_1, 0x3c));

	DisplayController() {
		if (!ssd1306.isInitialised()) {
			System.out.println("--> STARTUP");
			ssd1306.startup(false);
		}
	}

	@EventListener
	void onShutdown(ServiceShutdownEvent event) {
		clearDisplay();
		ssd1306.shutdown();
	}

	public SSD1306 getSsd1306() {
		return ssd1306;
	}

	public void clearDisplay() {
		ssd1306.clear();
		ssd1306.display();
	}

	public void bufferLayout(Layout layout) {
		ssd1306.clear();
		layout.bufferDisplayComponents();
		ssd1306.display();
	}

	public void displayTimedlayout(Layout layout, long layoutDurationInMs) {
		byte[] buffer = ssd1306.getBuffer();
		bufferLayout(layout);
		Timer timer = new Timer();
		timer.schedule(displayTimerTask(buffer), layoutDurationInMs);
		ssd1306.display();
	}

	private TimerTask displayTimerTask(byte[] buffer) {
		return new TimerTask() {
			public void run() {
				ssd1306.setBuffer(buffer);
			}
		};
	}
}
