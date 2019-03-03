package pi.naut.gpio.display.ssd1306.layout;

import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;
import net.fauxpark.oled.SSD1306;
import pi.naut.gpio.display.Layout;
import pi.naut.gpio.display.ssd1306.core.buffer.ComponentBuffer;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class WelcomeLayout implements Layout {

	private ComponentBuffer componentBuffer = new ComponentBuffer();

	private static final String title = "WELCOME";

	@Override
	public void bufferComponentsTo(SSD1306 displayController) {
		componentBuffer.titleBar(displayController, title);
	}

	@Override
	public Map<String, GpioPinListener> getListenerConfiguration() {
		return new HashMap<>();
	}

	@Override
	public Map<String, GpioTrigger> getTriggerConfiguration() {
		Map<String, GpioTrigger> triggerMap = new HashMap<>();
		return triggerMap;
	}

}
