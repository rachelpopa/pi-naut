package pi.naut.gpio.bonnet;

import com.pi4j.io.gpio.GpioPinDigitalInput;
import io.micronaut.scheduling.annotation.Scheduled;
import pi.naut.gpio.bonnet.display.DisplayController;
import pi.naut.gpio.bonnet.input.DigitalInputController;
import pi.naut.gpio.bonnet.input.listener.NavigateToNextPrimaryLayoutListener;
import pi.naut.gpio.bonnet.layout.WelcomeLayout;
import pi.naut.gpio.config.PinConfiguration;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Singleton
public class OLEDBonnet {

	// Controllers
	@Inject
	private DigitalInputController digitalInputController;
	@Inject
	private DisplayController displayController;

	// Layouts
	@Inject
	private Layouts layouts;
	@Inject
	private WelcomeLayout welcomeLayout;

	private Boolean isPrimaryLayout = true;

	@PostConstruct
	private void init() {
		System.out.println("Welcome");
		displayLayout(welcomeLayout);
		applyGlobalEvents();
	}

	@Scheduled(fixedRate = "1s")
	public void redisplayLayout() {
		if (isPrimaryLayout) {
			if (layouts.getPrimaryLayouts() != null && layouts.getPrimaryLayouts().current() != null) {
				displayController.bufferLayout(layouts.getPrimaryLayouts().current());
			}
		} else {
			if (layouts.getSecondaryLayouts() != null && layouts.getSecondaryLayouts().current() != null) {
				displayController.bufferLayout(layouts.getSecondaryLayouts().current());
			}
		}
	}

	public void displayLayout(Layout layout) {
		if (layout == null) {
			return;
		}
		displayController.bufferLayout(layout);
		applyLayoutEvents(layout);
	}

	public void displayPrimaryLayout() {
		isPrimaryLayout = true;
		displayLayout(layouts.getPrimaryLayouts().current());
	}

	private void applyLayoutEvents(Layout layout) {
		Map<String, GpioPinDigitalInput> pins = digitalInputController.getInputPins().entrySet()
				.stream()
				.filter(pin -> !PinConfiguration.JOYSTICK_CENTER.equals(pin.getKey())) // exclude global pin
				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

		pins.values().forEach(pin -> {
			pin.removeAllListeners();
			pin.removeAllTriggers();
		});
		layout.applyListenerConfiguration(this).forEach((pin, listener) -> pins.get(pin).addListener(listener));
		layout.applyTriggerConfiguration(this).forEach((pin, trigger) -> pins.get(pin).addTrigger(trigger));
	}

	private void applyGlobalEvents() {
		digitalInputController.getInputPins().get(PinConfiguration.JOYSTICK_CENTER)
				.addListener(new NavigateToNextPrimaryLayoutListener(this, layouts));
	}

	public void setIsPrimaryLayout(Boolean isPrimaryLayout) {
		this.isPrimaryLayout = isPrimaryLayout;
	}

}
