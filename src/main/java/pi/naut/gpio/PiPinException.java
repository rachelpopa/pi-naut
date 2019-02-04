package pi.naut.gpio;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinDirection;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.system.SystemInfo;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

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

	private StringBuffer pinName(Map.Entry<String, Pin> pinConfigEntry) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(pinConfigEntry.getValue().getName());
		return stringBuffer;
	}

	private static String supportedPinModes(Pin pin) {
		return pin.getSupportedPinModes().stream().map(PinMode::getName).collect(Collectors.joining(", "));
	}

	private static String pinDirection(Pin pin) {
		return pin.getSupportedPinModes().stream().map(PinMode::getDirection).map(PinDirection::getName).collect(Collectors.joining(", "));
	}

}
