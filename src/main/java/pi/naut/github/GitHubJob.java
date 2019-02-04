package pi.naut.github;

import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.i2c.I2CBus;
import io.micronaut.context.annotation.Requires;
import io.micronaut.scheduling.annotation.Scheduled;
import net.fauxpark.oled.SSD1306;
import net.fauxpark.oled.font.CodePage1252;
import net.fauxpark.oled.transport.I2CTransport;
import net.fauxpark.oled.transport.Transport;
import pi.naut.github.model.PullRequest;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.List;

@Singleton
@Requires(beans = GitHubClient.class)
public class GitHubJob {

	public static final int FONT_HEIGHT = 7;

	public static final int MIN_XY = 0;
	public static final int MAX_X = 127;
	public static final int MAX_Y = 63;

	public static final int MAX_WIDTH = MAX_X + 1;
	public static final int MAX_HEIGHT = MAX_Y + 1;

	public static final int HEIGHT_BUTTON_BOX = 11;
	public static final int HEIGHT_ACTION_FOOTER = 15;

	public static final int PADDING_BUTTON = 2;
	public static final int PADDING_TEXT = 2;

	public static final int TITLE_HEIGHT = FONT_HEIGHT + PADDING_TEXT;

	@Scheduled(fixedRate = "10m")
	public void updatePullRequests() throws IOException {

		Transport transport = new I2CTransport(RaspiPin.GPIO_15, I2CBus.BUS_1, 0x3c);
		SSD1306 display = new SSD1306(128, 64, transport);

		// TODO, might not need this
		display.setDisplayOn(true);

		display.startup(false);

		// TODO, remove debug pixels
//		display.setPixel(MIN_XY, MIN_XY, true);
//		display.setPixel(MAX_X, MIN_XY, true);
//		display.setPixel(MIN_XY, MAX_Y, true);
//		display.setPixel(MAX_X, MAX_Y, true);

		// Context Title
		display.getGraphics().text(25, MIN_XY, new CodePage1252(),"PULL REQUESTS");
		display.getGraphics().line(MIN_XY, FONT_HEIGHT + PADDING_TEXT, MAX_X, FONT_HEIGHT + PADDING_TEXT);

		// Show arrow if more items are up
		display.getGraphics().line((MAX_WIDTH / 2) - 2, TITLE_HEIGHT + PADDING_TEXT + 2, (MAX_WIDTH / 2), TITLE_HEIGHT + PADDING_TEXT);
		display.getGraphics().line((MAX_WIDTH / 2), TITLE_HEIGHT + PADDING_TEXT, (MAX_WIDTH / 2) + 2, TITLE_HEIGHT + PADDING_TEXT + 2);

		// Dummy current page
		display.getGraphics().text(PADDING_TEXT, TITLE_HEIGHT + ((FONT_HEIGHT + PADDING_TEXT) * 1), new CodePage1252(),"[*] Dalin's secrets");
		display.getGraphics().text(PADDING_TEXT, TITLE_HEIGHT + ((FONT_HEIGHT + PADDING_TEXT) * 2), new CodePage1252(),"[+] Fuzzy Navel Time");
		display.getGraphics().text(PADDING_TEXT, TITLE_HEIGHT + ((FONT_HEIGHT + PADDING_TEXT) * 3), new CodePage1252(),"[-] Quackers Mc Wackers");

		// Show arrow if more items are down
		// TODO

		// footer button bar
		display.getGraphics().rectangle(MIN_XY, MAX_HEIGHT - HEIGHT_ACTION_FOOTER, MAX_WIDTH, HEIGHT_ACTION_FOOTER, false);

		// "Details" button

		// footer button box
		display.getGraphics().rectangle(
				(PADDING_BUTTON),
				(MAX_HEIGHT - HEIGHT_BUTTON_BOX - PADDING_BUTTON),
				((MAX_WIDTH / 2) - PADDING_BUTTON),
				HEIGHT_BUTTON_BOX,
				false
		);

		// footer button text
		display.getGraphics().text(
				(PADDING_BUTTON + PADDING_TEXT),
				(MAX_HEIGHT - HEIGHT_BUTTON_BOX),
				new CodePage1252(),
				"DETAILS"
		);

		// "Dismiss" button

		// footer button box
		display.getGraphics().rectangle(
				((MAX_WIDTH / 2) + 1),
				(MAX_HEIGHT - HEIGHT_BUTTON_BOX - PADDING_BUTTON),
				((MAX_WIDTH / 2) - 3),
				HEIGHT_BUTTON_BOX,
				false
		);

		// footer button text
		display.getGraphics().text(
				((MAX_WIDTH / 2) + PADDING_BUTTON + PADDING_TEXT),
				(MAX_HEIGHT - HEIGHT_BUTTON_BOX),
				new CodePage1252(),
				"DISMISS"
		);

		display.display();

	}

	private List<PullRequest> getOpenPullRequests() {
//		return gitHubClient.getCurrentUserEvents()
//				.stream()
//				.filter(event -> event.getType().equals(EventType.PullRequestEvent.name()))
//				.map(event -> new ObjectMapper().convertValue(event.getPayload(), PullRequestEvent.class))
//				.filter(pr -> pr.getAction() == ActionType.opened)
//				.map(pre -> pre.getPullRequest())
//				.filter(pr -> pr.getState() == StateType.open)
//				.collect(Collectors.toList());
		return null;
	}

}
