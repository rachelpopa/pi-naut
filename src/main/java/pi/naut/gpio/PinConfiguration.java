package pi.naut.gpio;

import com.pi4j.io.gpio.Pin;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PinConfiguration {
	public static final Map<String, Pin> DIGITAL_INPUTS;
	static {
		Map<String, Pin> aMap = new HashMap<>();

		// Put INPUT pin configuration here

		DIGITAL_INPUTS = Collections.unmodifiableMap(aMap);
	}

	public static final Map<String, Pin> DIGITAL_OUTPUTS;
	static {
		Map<String, Pin> aMap = new HashMap<>();

		// Put OUTPUT pin configuration here

		DIGITAL_OUTPUTS = Collections.unmodifiableMap(aMap);
	}
}
