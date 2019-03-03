package pi.naut.gpio.listener;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import pi.naut.gpio.display.ssd1306.layout.PullRequestLayout;

public class DecrementItemListener implements GpioPinListenerDigital {

	private PullRequestLayout pullRequestLayout;
	private long itemSize;

	public DecrementItemListener(PullRequestLayout pullRequestLayout) {
		this.pullRequestLayout = pullRequestLayout;
		this.itemSize = pullRequestLayout.getActions().size();
	}

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		System.out.println("Decrement Selectable index and re-display layout");
	}

}
