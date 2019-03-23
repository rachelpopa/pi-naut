package pi.naut.gpio.display.layout;

import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;
import net.fauxpark.oled.SSD1306;
import pi.naut.gpio.display.Layout;
import pi.naut.gpio.display.core.buffer.ComponentBuffer;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class RuntimeStatsLayout implements Layout {

	private ComponentBuffer componentBuffer = new ComponentBuffer();
	private static final String title = "RUNTIME STATS";

	@Override
	public void init() {}

	@Override
	public void bufferTo(SSD1306 displayController) {
		componentBuffer.titleBar(displayController, title);
		componentBuffer.runtimeStats(displayController, getStats());
	}

	@Override
	public Map<String, GpioPinListener> getListenerConfiguration() {
		return new HashMap<>();
	}

	@Override
	public Map<String, GpioTrigger> getTriggerConfiguration() {
		return new HashMap<>();
	}

	private List<String> getStats() {
		List<String> stats = new ArrayList<>();
		stats.add("Cores: "      + Runtime.getRuntime().availableProcessors());
		stats.add("Free mem: "   + Runtime.getRuntime().freeMemory());
		stats.add("Max mem: "    + Runtime.getRuntime().maxMemory());
		stats.add("Tot mem: "    + Runtime.getRuntime().totalMemory());
		return stats;
	}

}
