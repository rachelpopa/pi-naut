package pi.naut.gpio.display.layout;

import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;
import net.fauxpark.oled.SSD1306;
import pi.naut.github.model.PullRequest;
import pi.naut.gpio.display.Layout;
import pi.naut.gpio.display.core.buffer.ComponentBuffer;
import pi.naut.gpio.listener.DisplayCurrentParentLayoutListener;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

import static pi.naut.gpio.config.PinConfiguration.BUTTON_A;

@Singleton
public class PullRequestDetailsLayout implements Layout {

	@Inject
	private PullRequestLayout pullRequestLayout;
	@Inject
	private DisplayCurrentParentLayoutListener displayCurrentParentLayoutListener;

	private ComponentBuffer componentBuffer = new ComponentBuffer();

	private static final String TITLE = "PR DETAILS";

	private PullRequest pullRequest;

	@Override
	@PostConstruct
	public void init() {}

	@Override
	public void bufferTo(SSD1306 displayController) {
		setPullRequest(pullRequestLayout.getPullRequestCircularIterator().current());
		componentBuffer.titleBar(displayController, TITLE);
		componentBuffer.pullRequestDetails(displayController, pullRequest);
	}

	@Override
	public Map<String, GpioPinListener> getListenerConfiguration() {
		Map<String, GpioPinListener> listenerMap = new HashMap<>();
		listenerMap.put(BUTTON_A, displayCurrentParentLayoutListener);
		return listenerMap;
	}

	@Override
	public Map<String, GpioTrigger> getTriggerConfiguration() {
		return new HashMap<>();
	}

	private void setPullRequest(PullRequest pullRequest) {
		this.pullRequest = pullRequest;
	}

}
