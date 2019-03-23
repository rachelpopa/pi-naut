package pi.naut.gpio.bonnet.input.listener;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import pi.naut.gpio.bonnet.OLEDBonnet;
import pi.naut.gpio.bonnet.layout.PullRequestDetailsLayout;

public class NavigateToPullRequestDetailListener implements GpioPinListenerDigital {

	private OLEDBonnet oledBonnet;
	private PullRequestDetailsLayout pullRequestDetailsLayout;

	public NavigateToPullRequestDetailListener(OLEDBonnet oledBonnet, PullRequestDetailsLayout pullRequestDetailsLayout) {
		this.oledBonnet = oledBonnet;
		this.pullRequestDetailsLayout = pullRequestDetailsLayout;
	}

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		if (event.getState().isHigh()) {
			oledBonnet.displayLayout(pullRequestDetailsLayout);
			oledBonnet.setIsPrimaryLayout(false);
		}
	}

}
