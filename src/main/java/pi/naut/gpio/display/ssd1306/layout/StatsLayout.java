package pi.naut.gpio.display.ssd1306.layout;

import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;
import net.fauxpark.oled.SSD1306;
import pi.naut.gpio.display.Layout;
import pi.naut.gpio.display.ssd1306.core.buffer.ComponentBuffer;
import pi.naut.gpio.display.ssd1306.core.component.wrapper.Selectable;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class StatsLayout implements Layout {

	private ComponentBuffer componentBuffer = new ComponentBuffer();

	private static final String title = "RUNTIME STATS";

	@Override
	public void init() {
	}

	@Override
	public void bufferTo(SSD1306 displayController) {
		componentBuffer.titleBar(displayController, title);
		componentBuffer.scrollableList(displayController, getStats());
	}

	@Override
	public Map<String, GpioPinListener> getListenerConfiguration() {
		return new HashMap<>();
	}

	@Override
	public Map<String, GpioTrigger> getTriggerConfiguration() {
		return new HashMap<>();
	}

	private List<Selectable> getStats() {
		List<Selectable> stats = new ArrayList<>();

		stats.add(new Selectable("Cores: " + Runtime.getRuntime().availableProcessors()));
		stats.add(new Selectable("Free mem: " + Runtime.getRuntime().freeMemory()));
		stats.add(new Selectable("Max mem: " + Runtime.getRuntime().maxMemory()));
		stats.add(new Selectable("Tot mem: " + Runtime.getRuntime().totalMemory()));

		return stats;
	}

}
