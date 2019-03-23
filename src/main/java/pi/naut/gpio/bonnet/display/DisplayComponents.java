package pi.naut.gpio.bonnet.display;

import io.micronaut.core.util.StringUtils;
import net.fauxpark.oled.SSD1306;
import net.fauxpark.oled.font.CodePage1252;
import pi.naut.github.model.PullRequest;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import static pi.naut.gpio.bonnet.display.DisplayConstants.*;

@Singleton
public class DisplayComponents {

	@Inject
	private DisplayController displayController;
	private SSD1306 controller;

	@PostConstruct
	private void init() {
		this.controller = displayController.getSsd1306();
	}

	public void titleBar(String title) {
		if (StringUtils.isNotEmpty(title)) {
			controller.getGraphics().text(25, MIN_XY, new CodePage1252(), title);
			controller.getGraphics().line(MIN_XY, TEXT_HEIGHT, MAX_X, TEXT_HEIGHT);
		}
	}

	public void scrollableList(List dimesionalIterator, int currentIndex) {

		int bufferCount;
		if (dimesionalIterator.size() < 5) {
			bufferCount = dimesionalIterator.size();
		} else {
			bufferCount = 5;    // max rows without action bar
		}

		if (bufferCount > 0) {
//			bufferUpArrow(controller);
			for (int i = 0; i < dimesionalIterator.size(); i++) {
				controller.getGraphics().text(
						(RADIUS_RADIO_SELECTED * 2) + (PADDING * 2),
						TEXT_HEIGHT + (TEXT_HEIGHT * (i + 1)) + 1,
						new CodePage1252(),     // TODO, use a monospaced font
						dimesionalIterator.get(i).toString()
				);
				if (currentIndex == i) {
					controller.getGraphics().circle(RADIUS_RADIO_SELECTED, (TEXT_HEIGHT + (PADDING * 2)) + (TEXT_HEIGHT * (i + 1)), 1);
				}
			}
//			bufferDownArrow(controller);
		}

	}

	public void bufferDownArrow() {
		controller.getGraphics().line(HALF_WIDTH - ARROW_SLOPE, BASE_HEIGHT_ARROW_DOWN, HALF_WIDTH, BASE_HEIGHT_ARROW_DOWN + ARROW_SLOPE);
		controller.getGraphics().line(HALF_WIDTH, BASE_HEIGHT_ARROW_DOWN + ARROW_SLOPE, HALF_WIDTH + ARROW_SLOPE, BASE_HEIGHT_ARROW_DOWN);
	}

	public void bufferUpArrow() {
		controller.getGraphics().line(HALF_WIDTH - ARROW_SLOPE, BASE_HEIGHT_ARROW_UP + ARROW_SLOPE, HALF_WIDTH, BASE_HEIGHT_ARROW_UP);
		controller.getGraphics().line(HALF_WIDTH, BASE_HEIGHT_ARROW_UP, HALF_WIDTH + ARROW_SLOPE, BASE_HEIGHT_ARROW_UP + ARROW_SLOPE);
	}

	public void startupScreen(String user) {
		controller.getGraphics().text(42, MIN_XY, new CodePage1252(), "PI-NAUT");
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream resourceAsStream = classLoader.getResourceAsStream("raspberry.png");
			BufferedImage bufferedImage = ImageIO.read(Objects.requireNonNull(resourceAsStream));
			controller.getGraphics().image(bufferedImage, 48, 12, 32, 40);
		} catch (IOException e) {
			e.printStackTrace();
		}
		controller.getGraphics().text(21, MAX_Y - FONT_HEIGHT, new CodePage1252(), "WELCOME " + user.toUpperCase());
	}

	public void pullRequestDetails(PullRequest pullRequest) {
		controller.getGraphics().text(
				PADDING,
				TEXT_HEIGHT + (TEXT_HEIGHT) + 1,
				new CodePage1252(),
				"NAME: " + pullRequest.getTitle()
		);
		controller.getGraphics().text(
				PADDING,
				TEXT_HEIGHT + (TEXT_HEIGHT * 4) + 1,
				new CodePage1252(),
				"NUMBER: " + pullRequest.getNumber()
		);
		controller.getGraphics().text(
				PADDING,
				TEXT_HEIGHT + (TEXT_HEIGHT * 3) + 1,
				new CodePage1252(),
//				"REPO: " + pullRequest.getRepository().getFullName()
				"REPO: WORKING ON IT..."
		);
		controller.getGraphics().text(
				PADDING,
				TEXT_HEIGHT + (TEXT_HEIGHT * 2) + 1,
				new CodePage1252(),
				"STATE: " + pullRequest.getState().name()
		);
		controller.getGraphics().text(
				PADDING,
				TEXT_HEIGHT + (TEXT_HEIGHT * 5) + 1,
				new CodePage1252(),
				"MSTATE: " + pullRequest.getMergableState()
		);
	}

	// TODO, make pagination component
	public void runtimeStats(List<String> stats) {
		for (int i = 0; i < stats.size(); i++) {
			controller.getGraphics().text(
					PADDING,
					TEXT_HEIGHT + (TEXT_HEIGHT * (i + 1)) + 1,
					new CodePage1252(),
					stats.get(i)
			);
		}
	}

	// TODO, take another swing at this
//	public void actionBar(List<Action> actions) {
//		if (CollectionUtils.isNotEmpty(actions)) {
//			int i = 1;
//			int size = actions.size();
//			int buttonWidth = (MAX_WIDTH / size);
//
//			for (Action action : actions) {
//				// button box
//				controller.getGraphics().rectangle(
//						(buttonWidth * (i - 1)) + (i - 1),
//						MAX_HEIGHT - HEIGHT_BUTTON,
//						buttonWidth,
//						HEIGHT_BUTTON,
//						false
//				);
//
//				// button text
//				controller.getGraphics().text(
//						(buttonWidth * (i - 1)) + (i - 1) + PADDING,
//						MAX_HEIGHT - FONT_HEIGHT - PADDING,
//						new CodePage1252(),
//						action.getLongDescription()  // TODO, make this responsive
//				);
//
//				// selected indicator
//				if (action.isSelected()) {
//					controller.getGraphics().circle(
//							(buttonWidth * i) - (RADIUS_RADIO_SELECTED * 2) - PADDING,
//							MAX_HEIGHT - (HEIGHT_BUTTON / 2) - 1,
//							RADIUS_RADIO_SELECTED
//					);
//				}
//
//				i++;
//			}
//		}
//	}

}
