package pi.naut.gpio.bonnet.input;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import io.micronaut.discovery.event.ServiceShutdownEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import pi.naut.gpio.config.PinConfiguration;

import javax.inject.Singleton;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class DigitalInputController {

	private final GpioController gpioController = GpioFactory.getInstance();
	private Map<String, GpioPinDigitalInput> inputPins = new ConcurrentHashMap<>();

	DigitalInputController() {
		PinConfiguration.DIGITAL_INPUT_PINS.forEach((key, value) ->
				inputPins.computeIfAbsent(key, e -> {
					GpioPinDigitalInput gpioPinDigitalInput = gpioController.provisionDigitalInputPin(value, key, PinPullResistance.PULL_UP);
					gpioPinDigitalInput.setShutdownOptions(false); // FIXME, should this be false (exported)
					return gpioPinDigitalInput;
				}));
	}

	@EventListener
	void onShutdown(ServiceShutdownEvent event) {
		gpioController.shutdown();
	}

	public Map<String, GpioPinDigitalInput> getInputPins() {
		return inputPins;
	}

}
