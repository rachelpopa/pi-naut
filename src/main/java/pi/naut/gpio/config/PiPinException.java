package pi.naut.gpio.config;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinDirection;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.system.SystemInfo;

import java.io.IOException;
import java.util.Map;

import static java.util.stream.Collectors.joining;

// TODO, finish testing this and use it
public class PiPinException extends RuntimeException {

	private PiPinException(Map.Entry<String, Pin> pinConfigEntry) {
		String s = pinName(pinConfigEntry) + " (" + pinConfigEntry.getKey() + ") does not exist on " + boardName();
	}

	private PiPinException(Pin pin) {
		super(supportedPinModes(pin) );
	}

	private PiPinException(String s) { super(s); }

	private static String boardName() {
		try {
			return SystemInfo.getBoardType().name();
		} catch (IOException | InterruptedException | NullPointerException e) {
			throw new PiPinException("SystemInfoFactory cannot provide board type");
		}
	}

	private String pinName(Map.Entry<String, Pin> pinConfigEntry) {
		return pinConfigEntry.getValue().getName();
	}

	private static String supportedPinModes(Pin pin) {
		return pin.getSupportedPinModes().stream().map(PinMode::getName).collect(joining(", "));
	}

	private static String pinDirection(Pin pin) {
		return pin.getSupportedPinModes().stream().map(PinMode::getDirection).map(PinDirection::getName).collect(joining(", "));
	}

}
