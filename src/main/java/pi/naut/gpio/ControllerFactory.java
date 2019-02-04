package pi.naut.gpio;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;

import javax.inject.Singleton;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Factory
public class ControllerFactory {

	@Singleton
	Map<String, GpioPinDigitalInput> inputPins = new ConcurrentHashMap<>();

	@Bean
	@Singleton
	GpioController gpioController() {
		GpioController gpioController = GpioFactory.getInstance();

		// TODO, throw exceptions here for invalid pin/board configurations

		PinConfiguration.DIGITAL_INPUTS.forEach((key, value) ->
				inputPins.computeIfAbsent(key, e -> gpioController.provisionDigitalInputPin(value, key, PinPullResistance.PULL_UP)));


		return gpioController;
	}

//	@Bean
//	@Singleton
//	SSD1306 display() {
//		Transport transport = new I2CTransport(RaspiPin.GPIO_15, I2CBus.BUS_1, 0x3D);
//		SSD1306 display = new SSD1306(128, 64, transport);
//		display.startup(false);
//
//		System.out.println("display factory starting...");
//
//		display.getGraphics().text(20, 20, new CodePage1252(), "Hello World!");
//
//		return display;
//	}
//
//	private TimerTask setListeners(SSD1306 display) {
//		return new TimerTask() {
//			public void run() {
//				display.clear();
//			}
//		};
//	}

}
