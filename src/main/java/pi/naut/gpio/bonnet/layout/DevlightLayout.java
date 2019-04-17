package pi.naut.gpio.bonnet.layout;

import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;
import io.micronaut.context.event.ApplicationEventPublisher;
import pi.naut.devlight.DevlightClient;
import pi.naut.devlight.DevlightState;
import pi.naut.devlight.LightStateFactory;
import pi.naut.gpio.bonnet.Layout;
import pi.naut.gpio.bonnet.OLEDBonnet;
import pi.naut.gpio.bonnet.display.DisplayComponents;
import pi.naut.gpio.input.listener.ChangeDevlightListener;
import pi.naut.gpio.input.listener.NextStateListener;
import pi.naut.gpio.input.listener.PreviousStateListener;
import util.StateList;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static pi.naut.devlight.HueConstants.*;
import static pi.naut.gpio.PinConfiguration.*;

@Singleton
public class DevlightLayout implements Layout {

	@Inject
	private DisplayComponents displayComponents;
	@Inject
	private DevlightClient devlightClient;
	@Inject
	private ApplicationEventPublisher applicationEventPublisher;

	private StateList<DevlightState> devlightStates = new StateList<>(asList(
			new DevlightState("BLINK RED", LightStateFactory.blink(COLOR_RED)),
			new DevlightState("BLINK YELLOW", LightStateFactory.blink(COLOR_YELLOW)),
			new DevlightState("BLINK GREEN", LightStateFactory.blink(COLOR_GREEN)),
			new DevlightState("BLINK BLUE", LightStateFactory.blink(COLOR_BLUE)),
			new DevlightState("BLINK PINK", LightStateFactory.blink(COLOR_PINK))
	));

	public static final String TITLE = "DEV LIGHTS";

	@Override
	public boolean isPrimary() { return true; }

	@Override
	public void bufferComponents() {
		displayComponents.titleBar(TITLE);
		displayComponents.scrollableList(devlightStates);
	}

	@Override
	public Map<String, GpioPinListener> applyListeners(OLEDBonnet oledBonnet) {
		Map<String, GpioPinListener> listeners = new HashMap<>();

		listeners.put(JOYSTICK_UP, new PreviousStateListener(applicationEventPublisher, devlightStates, DevlightLayout.class));
		listeners.put(JOYSTICK_DOWN, new NextStateListener(applicationEventPublisher, devlightStates, DevlightLayout.class));

		listeners.put(BUTTON_B, new ChangeDevlightListener(devlightClient, devlightStates.current()));

		return listeners;
	}

	@Override
	public Map<String, GpioTrigger> applyTriggers(OLEDBonnet oledBonnet) {
		return new HashMap<>();
	}

}

