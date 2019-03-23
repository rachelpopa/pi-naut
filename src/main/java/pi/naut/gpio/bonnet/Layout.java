package pi.naut.gpio.bonnet;

import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;

import java.util.Map;

public interface Layout {
	String name();
	void bufferDisplayComponents();
	Map<String, GpioPinListener> applyListenerConfiguration(OLEDBonnet oledBonnet);
	Map<String, GpioTrigger> applyTriggerConfiguration(OLEDBonnet oledBonnet);
}
