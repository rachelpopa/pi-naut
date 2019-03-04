package pi.naut.gpio.display;

import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;
import net.fauxpark.oled.SSD1306;

import java.util.Map;

public interface Layout {
	void init();
	void bufferTo(SSD1306 displayController);
	Map<String, GpioPinListener> getListenerConfiguration();
	Map<String, GpioTrigger> getTriggerConfiguration();
}
