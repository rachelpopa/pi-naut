package pi.naut.gpio;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;

import javax.inject.Singleton;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class InputController {

	private GpioController gpioController = GpioFactory.getInstance();
	private Map<String, GpioPinDigitalInput> inputPins = new ConcurrentHashMap<>();

	InputController() {
		PinConfiguration.DIGITAL_INPUTS.forEach((key, value) ->
				inputPins.computeIfAbsent(key, e -> {
					GpioPinDigitalInput gpioPinDigitalInput = gpioController.provisionDigitalInputPin(value, key, PinPullResistance.PULL_UP);
					// TODO, set this when we get a teardown function working
//					gpioPinDigitalInput.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
					return gpioPinDigitalInput;
				}));
	}

	public GpioController getGpioController() {
		return gpioController;
	}

	public Map<String, GpioPinDigitalInput> getInputPins() {
		return inputPins;
	}

}
