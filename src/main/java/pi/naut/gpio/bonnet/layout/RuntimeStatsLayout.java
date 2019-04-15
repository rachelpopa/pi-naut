package pi.naut.gpio.bonnet.layout;

import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;
import pi.naut.ApplicationState;
import pi.naut.gpio.bonnet.Layout;
import pi.naut.gpio.bonnet.OLEDBonnet;
import pi.naut.gpio.bonnet.display.DisplayComponents;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class RuntimeStatsLayout implements Layout {

	@Inject
	private DisplayComponents displayComponents;
	@Inject
	private ApplicationState applicationState;

	public static final String NAME = "RUNTIME STATS";

	@Override
	public String name() {
		return NAME;
	}

	@Override
	public boolean isPrimary() { return true; }

	@Override
	public void bufferComponents() {
		displayComponents.titleBar(NAME);
		displayComponents.paginatedList(applicationState.getRuntimeStats());
	}

	@Override
	public Map<String, GpioPinListener> applyListeners(OLEDBonnet oledBonnet) {
		return new HashMap<>();
	}

	@Override
	public Map<String, GpioTrigger> applyTriggers(OLEDBonnet oledBonnet) {
		return new HashMap<>();
	}

}
