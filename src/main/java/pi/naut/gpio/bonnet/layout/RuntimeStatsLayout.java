package pi.naut.gpio.bonnet.layout;

import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;
import pi.naut.gpio.bonnet.Layout;
import pi.naut.gpio.bonnet.OLEDBonnet;
import pi.naut.gpio.bonnet.display.DisplayComponents;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class RuntimeStatsLayout implements Layout {

	@Inject
	private DisplayComponents displayComponents = new DisplayComponents();

	public static final String NAME = "RUNTIME STATS";

	@Override
	public String name() {
		return NAME;
	}

	@Override
	public void bufferDisplayComponents() {
		displayComponents.titleBar(NAME);
		displayComponents.runtimeStats(getStats());
	}

	@Override
	public Map<String, GpioPinListener> applyListenerConfiguration(OLEDBonnet oledBonnet) {
		return new HashMap<>();
	}

	@Override
	public Map<String, GpioTrigger> applyTriggerConfiguration(OLEDBonnet oledBonnet) {
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
