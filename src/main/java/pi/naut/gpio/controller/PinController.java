package pi.naut.gpio.controller;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import pi.naut.gpio.PinConfiguration;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class PinController {

	private final GpioController gpioController = GpioFactory.getInstance();
	private Map<String, GpioPinDigitalInput> inputPins = new HashMap<>();

	PinController() {
		PinConfiguration.DIGITAL_INPUT_PINS.forEach((key, value) ->
				inputPins.computeIfAbsent(key, e ->
						gpioController.provisionDigitalInputPin(value, key, PinPullResistance.PULL_UP)));
	}

	public Map<String, GpioPinDigitalInput> getInputPins() {
		return inputPins;
	}

}
