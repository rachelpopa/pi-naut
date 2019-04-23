package pi.naut.gpio.controller;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import io.micronaut.discovery.event.ServiceShutdownEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import pi.naut.gpio.PinConfiguration;

import javax.inject.Singleton;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class PinController {

	private final GpioController gpioController = GpioFactory.getInstance();
	private Map<String, GpioPinDigitalInput> inputPins = new ConcurrentHashMap<>();

	PinController() {
		System.out.println("--> GPIO STARTUP");
		PinConfiguration.DIGITAL_INPUT_PINS.forEach((key, value) ->
				inputPins.computeIfAbsent(key, e -> {
					GpioPinDigitalInput gpioPinDigitalInput = gpioController.provisionDigitalInputPin(value, key, PinPullResistance.PULL_UP);
					gpioPinDigitalInput.setShutdownOptions(false);
					return gpioPinDigitalInput;
				}));
	}

	@EventListener
	void onShutdown(ServiceShutdownEvent event) {
		System.out.println("--> GPIO SHUTDOWN");
		gpioController.shutdown();
	}

	public Map<String, GpioPinDigitalInput> getInputPins() {
		return inputPins;
	}

}
