package pi.naut.gpio.display;

public interface Display {

	void clearDisplay();
	void displayLayout(Layout layout);
	void displayTimedlayout(Layout layout, long layoutDurationInMs);

}
