package pi.naut.devlight;

import static pi.naut.devlight.HueConstants.*;

public class LightStateFactory {

	public static LightState blink(double[] xyColor) {
		LightState lightState = new LightState();
		lightState.setOn(true);
		lightState.setBri(BRI_MAX);
		lightState.setSat(SAT_MAX);
		lightState.setTransitiontime(250);
		lightState.setXy(xyColor);
		lightState.setAlert(ALERT_L_SELECT);
		return lightState;
	}

}
