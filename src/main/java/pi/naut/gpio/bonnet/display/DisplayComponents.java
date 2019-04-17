package pi.naut.gpio.bonnet.display;

import io.micronaut.core.util.StringUtils;
import net.fauxpark.oled.SSD1306;
import net.fauxpark.oled.font.CodePage1252;
import pi.naut.gpio.controller.DisplayController;
import util.StateList;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static pi.naut.gpio.bonnet.display.DisplayConstants.*;

@Singleton
public class DisplayComponents {
	// TODO, refactor this into a component builder

	@Inject
	private DisplayController displayController;
	private SSD1306 controller;

	@PostConstruct
	private void init() {
		this.controller = displayController.getSsd1306();
	}

	public void startupScreen() {
		controller.getGraphics().text(42, MIN_XY, new CodePage1252(), "PI-NAUT");
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream resourceAsStream = classLoader.getResourceAsStream("raspberry.png");
			BufferedImage bufferedImage = ImageIO.read(Objects.requireNonNull(resourceAsStream));
			controller.getGraphics().image(bufferedImage, 48, 12, 32, 40);
		} catch (IOException e) {
			e.printStackTrace();
		}
		controller.getGraphics().text(21, MAX_Y - FONT_HEIGHT, new CodePage1252(), "PRESS JOYSTICK");
	}

	public void titleBar(String title) {
		if (StringUtils.isNotEmpty(title)) {
			controller.getGraphics().text(25, MIN_XY, new CodePage1252(), title);
			controller.getGraphics().line(MIN_XY, TEXT_HEIGHT, MAX_X, TEXT_HEIGHT);
		}
	}

	public void paginatedList(StateList list) {
		bufferList(list, MIN_XY, true);
	}

	public void scrollableList(StateList stateList) {
		bufferList(stateList, (RADIUS_SELECTED * 2) + (PADDING * 2), false);
		if (stateList.hasCurrent()) {
			controller.getGraphics().circle(RADIUS_SELECTED, 23 + (TEXT_HEIGHT * (stateList.currentIndex() % MAX_ROWS)), 1);
		}
	}

	private void bufferList(StateList stateList, int xOffset, boolean paginated) {
		if (!stateList.hasCurrent()) {
			return;
		}

		int maxPages = (int) Math.ceil((double) stateList.getList().size() / MAX_ROWS);

		int currentPage = 1;
		int currentPos = stateList.currentIndex() + 1;
		if (currentPos > MAX_ROWS) {
			if (!paginated) {
				currentPage = currentPos / MAX_ROWS;
				if (currentPos % MAX_ROWS > 0) {
					currentPage++;
				}
			}
		}

		int indexOffset = (currentPage - 1) * MAX_ROWS;
		int itemsRemaining = stateList.getList().size() - ((currentPage - 1) * MAX_ROWS);
		int maxIndex = itemsRemaining < MAX_ROWS ? itemsRemaining : MAX_ROWS;

		if (stateList.isCircular() || currentPage > 1) {
			bufferUpArrow();
		}
		for (int i = 0; i < maxIndex; i++) {
			controller.getGraphics().text(
					xOffset,
					TEXT_HEIGHT + (TEXT_HEIGHT * (i + 1)),
					new CodePage1252(),     // TODO, use a monospaced font
					stateList.getList().get(i + indexOffset).toString()
			);
		}
		if (stateList.isCircular() || currentPage < maxPages) {
			bufferDownArrow();
		}
	}

	private void bufferDownArrow() {
		controller.getGraphics().line(HALF_WIDTH - ARROW_SLOPE, BASE_HEIGHT_ARROW_DOWN, HALF_WIDTH, BASE_HEIGHT_ARROW_DOWN + ARROW_SLOPE);
		controller.getGraphics().line(HALF_WIDTH, BASE_HEIGHT_ARROW_DOWN + ARROW_SLOPE, HALF_WIDTH + ARROW_SLOPE, BASE_HEIGHT_ARROW_DOWN);
	}

	private void bufferUpArrow() {
		controller.getGraphics().line(HALF_WIDTH - ARROW_SLOPE, BASE_HEIGHT_ARROW_UP + ARROW_SLOPE, HALF_WIDTH, BASE_HEIGHT_ARROW_UP);
		controller.getGraphics().line(HALF_WIDTH, BASE_HEIGHT_ARROW_UP, HALF_WIDTH + ARROW_SLOPE, BASE_HEIGHT_ARROW_UP + ARROW_SLOPE);
	}

}
