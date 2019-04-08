package pi.naut.gpio.bonnet.layout;

import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;
import pi.naut.gpio.bonnet.Layout;
import pi.naut.gpio.bonnet.OLEDBonnet;
import pi.naut.gpio.bonnet.display.DisplayComponents;
import util.StateList;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;

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
		displayComponents.paginatedList(getStats());
	}

	@Override
	public Map<String, GpioPinListener> applyListenerConfiguration(OLEDBonnet oledBonnet) {
		return new HashMap<>();
	}

	@Override
	public Map<String, GpioTrigger> applyTriggerConfiguration(OLEDBonnet oledBonnet) {
		return new HashMap<>();
	}

	private StateList getStats() {
		return new StateList<>(asList(
				"Cores: " + Runtime.getRuntime().availableProcessors(),
				"Free mem: " + Runtime.getRuntime().freeMemory(),
				"Max mem: " + Runtime.getRuntime().maxMemory(),
				"Tot mem: " + Runtime.getRuntime().totalMemory()));
	}

}
