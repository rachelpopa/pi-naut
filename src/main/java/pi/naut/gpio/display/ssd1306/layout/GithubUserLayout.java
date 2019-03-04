package pi.naut.gpio.display.ssd1306.layout;

import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;
import net.fauxpark.oled.SSD1306;
import pi.naut.github.GithubService;
import pi.naut.github.model.User;
import pi.naut.gpio.display.Layout;
import pi.naut.gpio.display.ssd1306.core.buffer.ComponentBuffer;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Singleton
public class GithubUserLayout implements Layout {

	@Inject
	private GithubService githubService;

	private ComponentBuffer componentBuffer = new ComponentBuffer();

	private static final String title = "USER DETAILS";

	private User user;
	private BufferedImage userAvatar;

	@Override
	@PostConstruct
	public void init() {
		this.user = githubService.getCurrentUser();
	}

	@Override
	public void bufferTo(SSD1306 displayController) {
		componentBuffer.titleBar(displayController, title);
		componentBuffer.gitHubAvatar(displayController, user, getOrSetUserAvatar());
	}

	@Override
	public Map<String, GpioPinListener> getListenerConfiguration() {
		return new HashMap<>();
	}

	@Override
	public Map<String, GpioTrigger> getTriggerConfiguration() {
		return new HashMap<>();
	}

	public void setUser(User user) {
		this.user = user;
	}

	private BufferedImage getOrSetUserAvatar() {
		if (this.userAvatar == null) {
			try {
				this.userAvatar = ImageIO.read(Objects.requireNonNull(new URL(user.getAvatar_url()).openStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return this.userAvatar;
	}

}
