package pi.naut.gpio.bonnet.layout;

import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;
import io.micronaut.context.annotation.Value;
import pi.naut.gpio.bonnet.Layout;
import pi.naut.gpio.bonnet.OLEDBonnet;
import pi.naut.gpio.bonnet.display.DisplayComponents;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class WelcomeLayout implements Layout {

	@Inject
	private DisplayComponents displayComponents = new DisplayComponents();

	@Value("${github.user}")
	private String userName;

	public static final String NAME = "WELCOME";

	@Override
	public String name() {
		return NAME;
	}

	@Override
	public void bufferDisplayComponents() {
		displayComponents.startupScreen(userName);
	}

	@Override
	public Map<String, GpioPinListener> applyListenerConfiguration(OLEDBonnet oledBonnet) {
		return new HashMap<>();
	}

	@Override
	public Map<String, GpioTrigger> applyTriggerConfiguration(OLEDBonnet oledBonnet) { return new HashMap<>(); }

}
