package pi.naut.gpio.display.ssd1306.core.buffer;

import io.micronaut.core.util.CollectionUtils;
import io.micronaut.core.util.StringUtils;
import net.fauxpark.oled.SSD1306;
import net.fauxpark.oled.font.CodePage1252;
import pi.naut.gpio.display.ssd1306.core.component.Action;
import pi.naut.gpio.display.ssd1306.core.component.wrapper.Selectable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import static pi.naut.gpio.display.ssd1306.core.SSD1306Constants.*;

// TODO, separate this class into separate component buffers
public class ComponentBuffer {

	public void titleBar(SSD1306 controller, String title) {
		if (StringUtils.isNotEmpty(title)) {
			controller.getGraphics().text(25, MIN_XY, new CodePage1252(), title);
			controller.getGraphics().line(MIN_XY, TEXT_HEIGHT, MAX_X, TEXT_HEIGHT);
		}
	}

	public void scrollableList(SSD1306 controller, List<Selectable> selectables) {

		int bufferCount;

		if (selectables.size() < 3) {
			bufferCount = selectables.size();
		} else {
			bufferCount = 3;
		}

		if (bufferCount > 0) {
			// TODO, add logic to automatically hide/show arrows
//			bufferUpArrow(controller);
			for (int i = 0; i < selectables.size(); i++) {
				controller.getGraphics().text(
						(RADIUS_RADIO_SELECTED * 2) + (PADDING * 2),
						TEXT_HEIGHT + (TEXT_HEIGHT * (i + 1)) + 1,
						new CodePage1252(),     // TODO, use a monospaced font
						selectables.get(i).toString()
				);
				if (selectables.get(i).isSelected()) {
					controller.getGraphics().circle(RADIUS_RADIO_SELECTED, (TEXT_HEIGHT + (PADDING * 2)) + (TEXT_HEIGHT * (i + 1)), 1);
				}
			}
//			bufferDownArrow(controller);
		}

	}

	public void bufferDownArrow(SSD1306 controller) {
		controller.getGraphics().line(HALF_WIDTH - ARROW_SLOPE, BASE_HEIGHT_ARROW_DOWN, HALF_WIDTH, BASE_HEIGHT_ARROW_DOWN + ARROW_SLOPE);
		controller.getGraphics().line(HALF_WIDTH, BASE_HEIGHT_ARROW_DOWN + ARROW_SLOPE, HALF_WIDTH + ARROW_SLOPE, BASE_HEIGHT_ARROW_DOWN);
	}

	public void bufferUpArrow(SSD1306 controller) {
		controller.getGraphics().line(HALF_WIDTH - ARROW_SLOPE, BASE_HEIGHT_ARROW_UP + ARROW_SLOPE, HALF_WIDTH, BASE_HEIGHT_ARROW_UP);
		controller.getGraphics().line(HALF_WIDTH, BASE_HEIGHT_ARROW_UP, HALF_WIDTH + ARROW_SLOPE, BASE_HEIGHT_ARROW_UP + ARROW_SLOPE);
	}

	public void actionBar(SSD1306 controller, List<Action> actions) {
		if (CollectionUtils.isNotEmpty(actions)) {
			int i = 1;
			int size = actions.size();
			int buttonWidth = (MAX_WIDTH / size);

			for (Action action : actions) {
				// button box
				controller.getGraphics().rectangle(
						(buttonWidth * (i - 1)) + (i - 1),
						MAX_HEIGHT - HEIGHT_BUTTON,
						buttonWidth,
						HEIGHT_BUTTON,
						false
				);

				// button text
				controller.getGraphics().text(
						(buttonWidth * (i - 1)) + (i - 1) + PADDING,
						MAX_HEIGHT - FONT_HEIGHT - PADDING,
						new CodePage1252(),
						action.getLongDescription()  // TODO, make this responsive
				);

				// selected indicator
				if (action.isSelected()) {
					controller.getGraphics().circle(
							(buttonWidth * i) - (RADIUS_RADIO_SELECTED * 2) - PADDING,
							MAX_HEIGHT - (HEIGHT_BUTTON / 2) - 1,
							RADIUS_RADIO_SELECTED
					);
				}

				i++;
			}
		}
	}

	public void startupScreen(SSD1306 displayController, String user) {
		displayController.getGraphics().text(21, MIN_XY, new CodePage1252(), "WELCOME " + user.toUpperCase());
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream resourceAsStream = classLoader.getResourceAsStream("raspberry.png");
			BufferedImage bufferedImage = ImageIO.read(Objects.requireNonNull(resourceAsStream));
			displayController.getGraphics().image(bufferedImage, 48, 12 , 32, 40);
		} catch (IOException e) {
			e.printStackTrace();
		}
		displayController.getGraphics().text(25, MAX_Y - FONT_HEIGHT, new CodePage1252(), "PRESS JOYSTICK");
	}

}
