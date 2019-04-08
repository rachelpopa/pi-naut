package pi.naut.gpio.bonnet.layout;

import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.trigger.GpioTrigger;
import io.micronaut.context.annotation.Value;
import pi.naut.gpio.bonnet.Layout;
import pi.naut.gpio.bonnet.OLEDBonnet;
import pi.naut.gpio.bonnet.display.DisplayComponents;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

import static pi.naut.gpio.config.PinConfiguration.BUTTON_A;
import static pi.naut.gpio.config.PinConfiguration.BUTTON_B;

@Singleton
public class WelcomeLayout implements Layout {

	@Inject
	private DisplayComponents displayComponents;

	@Value("${github.user}")
	private String userName;

	public static final String NAME = "WELCOME";

	@Override
	public String name() {
		return NAME;
	}

	@Override
	public boolean primary() {
		return false;
	}

	@Override
	public void displayComponents() {
		displayComponents.startupScreen(userName);
	}

	@Override
	public Map<String, GpioPinListener> applyListeners(OLEDBonnet oledBonnet) {
		Map<String, GpioPinListener> listeners = new HashMap<>();

		listeners.put(BUTTON_A, getWelcomeListener(oledBonnet));
		listeners.put(BUTTON_B, getWelcomeListener(oledBonnet));

		return listeners;
	}

	@Override
	public Map<String, GpioTrigger> applyTriggers(OLEDBonnet oledBonnet) { return new HashMap<>(); }

	private GpioPinListenerDigital getWelcomeListener(OLEDBonnet oledBonnet) {
		return event -> {
			if (event.getState().isHigh()) {
				oledBonnet.nextPrimaryLayout();
			}
		};
	}

}
