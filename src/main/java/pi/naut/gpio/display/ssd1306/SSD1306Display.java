package pi.naut.gpio.display.ssd1306;

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.i2c.I2CBus;
import io.micronaut.discovery.event.ServiceShutdownEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.scheduling.annotation.Scheduled;
import net.fauxpark.oled.SSD1306;
import net.fauxpark.oled.transport.I2CTransport;
import pi.naut.gpio.config.InputController;
import pi.naut.gpio.config.PinConfiguration;
import pi.naut.gpio.display.Display;
import pi.naut.gpio.display.Layout;
import pi.naut.gpio.display.ssd1306.layout.GithubUserLayout;
import pi.naut.gpio.display.ssd1306.layout.PullRequestLayout;
import pi.naut.gpio.display.ssd1306.layout.StatsLayout;
import pi.naut.gpio.display.ssd1306.layout.WelcomeLayout;
import util.CircularIterator;
import util.CircularList;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toMap;

@Singleton
public class SSD1306Display implements Display {

	@Inject
	private InputController inputController;

	// Inject Layouts
	@Inject
	private GithubUserLayout githubUserLayout;
	@Inject
	private PullRequestLayout pullRequestLayout;
	@Inject
	private StatsLayout statsLayout;
	@Inject
	private WelcomeLayout welcomeLayout;

	private static final SSD1306 controller = new SSD1306(128, 64, new I2CTransport(RaspiPin.GPIO_15, I2CBus.BUS_1, 0x3c));
	private CircularIterator<Layout> parentLayouts;

	@PostConstruct
	private void init() {
		parentLayouts = new CircularList<>(asList(
				githubUserLayout,
				statsLayout,
				pullRequestLayout
				)).iterator();
		clearDisplay();
		displayLayout(welcomeLayout);
		applyDisplayEvents();
	}

	@EventListener
	void teardown(ServiceShutdownEvent event) {
		clearDisplay();
		controller.shutdown();
	}

	@Scheduled(initialDelay = "25s", fixedRate = "2s")
	void refresh() {
		redisplayCurrentLayout();
	}

	@Override
	public void clearDisplay() {
		if (!controller.isInitialised()) {
			System.out.println("--> STARTUP");
			controller.startup(false);
		}
		controller.clear();
		controller.display();
	}

	@Override
	public void displayLayout(Layout layout) {
		controller.clear();
		layout.bufferTo(controller);
		reapplyLayoutEvents(layout);
		controller.display();
	}

	@Override
	public void displayTimedlayout(Layout layout, long layoutDurationInMs) {
		byte[] buffer = controller.getBuffer();
		displayLayout(layout);
		Timer timer = new Timer();
		timer.schedule(displayTimerTask(buffer), layoutDurationInMs);
		controller.display();
	}

	private TimerTask displayTimerTask(byte[] buffer) {
		return new TimerTask() {
			public void run() {
				controller.setBuffer(buffer);
			}
		};
	}

	private void reapplyLayoutEvents(Layout layout) {
		Map<String, GpioPinDigitalInput> pins = inputController.getInputPins().entrySet()
				.stream()
				.filter(pin -> !PinConfiguration.JOYSTICK_CENTER.equals(pin.getKey()))
				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

		pins.values().forEach(pin -> {
			pin.removeAllListeners();
			pin.removeAllTriggers();
		});
		layout.getListenerConfiguration().forEach((pin, listener) -> pins.get(pin).addListener(listener));
		layout.getTriggerConfiguration().forEach((pin, trigger) -> pins.get(pin).addTrigger(trigger));
	}

	private void applyDisplayEvents() {
		inputController.getInputPins().get(PinConfiguration.JOYSTICK_CENTER).addListener((GpioPinListenerDigital) event -> {
			if (event.getState().isHigh()) {
				displayLayout(parentLayouts.next());
			}
		});
	}

	private void redisplayCurrentLayout() {
		controller.clear();
		parentLayouts.current().bufferTo(controller);
		controller.display();
	}

}
