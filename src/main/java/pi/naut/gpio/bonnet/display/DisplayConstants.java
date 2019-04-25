package pi.naut.gpio.bonnet.display;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DisplayConstants {

	public static final int FONT_HEIGHT = 8;
	public static final int PADDING = 2;
	public static final int TEXT_HEIGHT = FONT_HEIGHT + PADDING;

	public static final int MAX_ROWS = 4;

	public static final int MIN_XY = 0;
	public static final int MAX_X = 127;
	public static final int MAX_Y = 63;

	public static final int MAX_WIDTH = MAX_X + 1;
	public static final int MAX_HEIGHT = MAX_Y + 1;

	public static final int HALF_WIDTH = MAX_WIDTH / 2;

	public static final int ARROW_SLOPE = 2;
	public static final int BASE_HEIGHT_ARROW_UP = TEXT_HEIGHT + (PADDING * 2);
	public static final int BASE_HEIGHT_ARROW_DOWN = MAX_Y - ARROW_SLOPE - (PADDING / 2);

	public static final int RADIUS_SELECTED = 3;

}
