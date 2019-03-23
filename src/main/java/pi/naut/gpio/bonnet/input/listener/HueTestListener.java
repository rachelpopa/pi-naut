package pi.naut.gpio.bonnet.input.listener;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import io.micronaut.context.annotation.Value;
import pi.naut.devlight.DevlightClient;
import pi.naut.devlight.HueConstants;
import pi.naut.devlight.LightState;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HueTestListener implements GpioPinListenerDigital {

	@Value("${github.user}")
	private String user;
	@Value("${github.token}")
	private String token;

	@Inject
	private DevlightClient devlightClient;

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		if (event.getState().isHigh()) {
			System.out.println(" --> Calling Devlights");

			LightState state = new LightState();
			state.setBri(HueConstants.BRI_MAX);
			state.setOn(true);
			state.setSat(HueConstants.SAT_MAX);
			state.setAlert(HueConstants.ALERT_L_SELECT);
			state.setEffect(HueConstants.EFFECT_NONE);
			state.setXy(HueConstants.COLOR_GREEN);

			System.out.println(user);
			System.out.println(token);

			devlightClient.setLightState(user, token, state);
		}
	}
}
