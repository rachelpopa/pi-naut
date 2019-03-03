package pi.naut.gpio.listener;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import pi.naut.gpio.display.ssd1306.layout.PullRequestLayout;

public class ActionListener implements GpioPinListenerDigital {

	private PullRequestLayout pullRequestLayout;
	private long actionSize;

	public ActionListener(PullRequestLayout pullRequestLayout) {
		this.pullRequestLayout = pullRequestLayout;
		this.actionSize = pullRequestLayout.getPullRequests().size();
	}

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		System.out.println("Do some action!");
	}

}
