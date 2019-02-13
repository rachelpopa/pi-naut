package pi.naut.gpio.display;

import net.fauxpark.oled.SSD1306;

public interface Layout {
	void bufferComponentsTo(SSD1306 controller);
	void replaceListeners();
}
