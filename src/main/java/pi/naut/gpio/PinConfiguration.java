package pi.naut.gpio;

import com.pi4j.io.gpio.Pin;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class PinConfiguration {

	public static final Map<String, Pin> DIGITAL_INPUT_PINS;
	static {
		Map<String, Pin> aMap = new HashMap<>();
		DIGITAL_INPUT_PINS = Collections.unmodifiableMap(aMap);
	}

}
