package pi.naut.gpio.display.ssd1306;

import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.i2c.I2CBus;
import net.fauxpark.oled.SSD1306;
import net.fauxpark.oled.transport.I2CTransport;
import pi.naut.gpio.InputController;
import pi.naut.gpio.display.Display;
import pi.naut.gpio.display.Layout;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Timer;
import java.util.TimerTask;

@Singleton
public class SSD1306Display implements Display {

	@Inject
	private InputController inputController;

	private final SSD1306 controller = new SSD1306(128, 64, new I2CTransport(RaspiPin.GPIO_15, I2CBus.BUS_1, 0x3c));

	@Override
	public void addLayout(Layout layout) {
		layout.bufferLayoutTo(controller);
		applyListenerConfiguration(layout);
	}

	@Override
	public void clearAll() {
	}

	@Override
	public void clearDisplay() {
		if (!controller.isInitialised()) {
			System.out.println("STARTUP!");
			controller.startup(false);
		}
		controller.clear();
		controller.display();
	}

	@Override
	public void display() {
		controller.display();
	}

	@Override
	public void displayLayout(Layout layout) {
		controller.clear();
		layout.bufferLayoutTo(controller);
		applyListenerConfiguration(layout);
		controller.display();
		controller.getGraphics().circle(4, 4, 32);
	}

	@Override
	public void displayTimedlayout(Layout layout, long layoutDurationInMs) {

		byte[] buffer = controller.getBuffer();
		displayLayout(layout);
		Timer timer = new Timer();
		timer.schedule(displayTimerTask(buffer), layoutDurationInMs);
		controller.display();
	}

	@Override
	public TimerTask displayTimerTask(byte[] buffer) {
		return new TimerTask() {
			public void run() {
				controller.setBuffer(buffer);
			}
		};
	}

	private void applyListenerConfiguration(Layout layout) {
		layout.getListenerConfig().forEach((s, gpioPinListener) -> inputController.getInputPins().get(s).addListener(gpioPinListener));
	}

	public SSD1306 getController() {
		return controller;
	}

}
