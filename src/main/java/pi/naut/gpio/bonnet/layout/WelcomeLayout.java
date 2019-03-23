package pi.naut.gpio.display.layout;

import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;
import io.micronaut.context.annotation.Value;
import net.fauxpark.oled.SSD1306;
import pi.naut.gpio.display.Layout;
import pi.naut.gpio.display.core.buffer.ComponentBuffer;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class WelcomeLayout implements Layout {

	private ComponentBuffer componentBuffer = new ComponentBuffer();

	@Value("${github.user}")
	private String userName;

	@Override
	public void init() {}

	@Override
	public void bufferTo(SSD1306 displayController) {
		componentBuffer.startupScreen(displayController, userName);
	}

	@Override
	public Map<String, GpioPinListener> getListenerConfiguration() {
		return new HashMap<>();
	}

	@Override
	public Map<String, GpioTrigger> getTriggerConfiguration() { return new HashMap<>(); }

}
