package pi.naut.gpio.listener;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import pi.naut.gpio.display.ssd1306.layout.PullRequestLayout;

public class IncrementItemListener implements GpioPinListenerDigital {

	private PullRequestLayout pullRequestLayout;
	private long itemSize;

	public IncrementItemListener(PullRequestLayout pullRequestLayout) {
		this.pullRequestLayout = pullRequestLayout;
		this.itemSize = pullRequestLayout.getActions().size();
	}

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		System.out.println("Increment Selectable index and re-display layout");
	}

}
