package pi.naut.gpio.bonnet.layout;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.trigger.GpioCallbackTrigger;
import com.pi4j.io.gpio.trigger.GpioTrigger;

import pi.naut.gpio.PinConfiguration;
import pi.naut.gpio.bonnet.Layout;
import pi.naut.gpio.bonnet.OLEDBonnet;
import pi.naut.gpio.bonnet.display.DisplayComponents;

@Singleton
public class HelloWorldLayout implements Layout { 

	@Inject
	private DisplayComponents displayComponents;

	@Override
	public boolean isPrimary() { return true; }

	@Override
	public void bufferComponents() {
		displayComponents.titleBar("Hello World");
	}

	@Override
	public Map<String, GpioPinListener> applyListeners(OLEDBonnet oledBonnet) {
		Map<String, GpioPinListener> listeners = new HashMap<>();

		listeners.put(PinConfiguration.BUTTON_A, (GpioPinListenerDigital) event -> {
			System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
		});

		return listeners;
	}

	@Override
	public Map<String, GpioTrigger> applyTriggers(OLEDBonnet oledBonnet) {
		Map<String, GpioTrigger> triggers = new HashMap<>();

		triggers.put(PinConfiguration.BUTTON_B, new GpioCallbackTrigger(() -> {
			System.out.println(" --> GPIO TRIGGER CALLBACK RECEIVED");
			return null;
		}));

		return triggers;
	}

}