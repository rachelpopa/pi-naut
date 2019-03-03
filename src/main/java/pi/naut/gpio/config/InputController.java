package pi.naut.gpio.config;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import io.micronaut.discovery.event.ServiceShutdownEvent;
import io.micronaut.runtime.event.annotation.EventListener;

import javax.inject.Singleton;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class InputController {

	private GpioController gpioController = GpioFactory.getInstance();
	private Map<String, GpioPinDigitalInput> inputPins = new ConcurrentHashMap<>();

	InputController() {
		PinConfiguration.DIGITAL_INPUT_PINS.forEach((key, value) ->
				inputPins.computeIfAbsent(key, e -> {
					GpioPinDigitalInput gpioPinDigitalInput = gpioController.provisionDigitalInputPin(value, key, PinPullResistance.PULL_UP);
					gpioPinDigitalInput.setShutdownOptions(false);
					return gpioPinDigitalInput;
				}));
	}

	@EventListener
	void onShutdown(ServiceShutdownEvent event) {
		gpioController.shutdown();
	}

	public GpioController getGpioController() {
		return gpioController;
	}

	public Map<String, GpioPinDigitalInput> getInputPins() {
		return inputPins;
	}

}
