package pi.naut.gpio.bonnet;

import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;

import java.util.Map;

public interface Layout {
	String name();
	boolean primary();
	void displayComponents();
	Map<String, GpioPinListener> applyListeners(OLEDBonnet oledBonnet);
	Map<String, GpioTrigger> applyTriggers(OLEDBonnet oledBonnet);
}
