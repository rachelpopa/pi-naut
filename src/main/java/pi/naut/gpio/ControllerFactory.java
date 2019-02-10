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
	public GpioController gpioController() {
		GpioController gpioController = GpioFactory.getInstance();

		// TODO, throw exceptions here for invalid pin/board configurations

		PinConfiguration.DIGITAL_INPUTS.forEach((key, value) ->
				inputPins.computeIfAbsent(key, e -> gpioController.provisionDigitalInputPin(value, key, PinPullResistance.PULL_UP)));

		return gpioController;
	}

}
