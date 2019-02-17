package pi.naut.gpio.display;

import java.util.TimerTask;

public interface Display {

	void addLayout(Layout layout);
	void clearAll();
	void clearDisplay();
	void display();
	void displayLayout(Layout layout);
	void displayTimedlayout(Layout layout, long layoutDurationInMs);
	TimerTask displayTimerTask(byte[] buffer);

}
