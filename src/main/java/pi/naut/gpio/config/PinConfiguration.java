package pi.naut.gpio.config;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class PinConfiguration {

	public static final String JOYSTICK_LEFT = "JOYSTICK_LEFT";
	public static final String JOYSTICK_RIGHT = "JOYSTICK_RIGHT";
	public static final String JOYSTICK_CENTER = "JOYSTICK_CENTER";
	public static final String JOYSTICK_UP = "JOYSTICK_UP";
	public static final String JOYSTICK_DOWN = "JOYSTICK_DOWN";

	public static final String BUTTON_A = "BUTTON_A";
	public static final String BUTTON_B = "BUTTON_B";

	public static final Map<String, Pin> DIGITAL_INPUT_PINS;
	static {
		Map<String, Pin> aMap = new HashMap<>();

		aMap.put(JOYSTICK_LEFT, RaspiPin.GPIO_02);
		aMap.put(JOYSTICK_RIGHT, RaspiPin.GPIO_04);
		aMap.put(JOYSTICK_CENTER, RaspiPin.GPIO_07);
		aMap.put(JOYSTICK_UP, RaspiPin.GPIO_00);
		aMap.put(JOYSTICK_DOWN, RaspiPin.GPIO_03);

		aMap.put(BUTTON_A, RaspiPin.GPIO_21);
		aMap.put(BUTTON_B, RaspiPin.GPIO_22);

		DIGITAL_INPUT_PINS = Collections.unmodifiableMap(aMap);
	}

}
