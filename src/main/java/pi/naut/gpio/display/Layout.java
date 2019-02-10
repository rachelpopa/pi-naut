package pi.naut.gpio.display;

import net.fauxpark.oled.SSD1306;

public interface Layout {
	void addTo(SSD1306 controller);
}
