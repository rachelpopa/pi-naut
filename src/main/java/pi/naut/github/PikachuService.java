package pi.naut.github;

import javax.inject.Singleton;

import pi.naut.github.model.PikachuMood;

@Singleton
public class PikachuService {

	public PikachuMood getPikachuMood(int numOfPullRequests) {
		switch (numOfPullRequests) {
			case 0:
				return PikachuMood.MAX_HAPPY;
			case 1:
				return PikachuMood.VERY_HAPPY;
			case 2:
				return PikachuMood.HAPPY;
			case 3:
				return PikachuMood.NEUTRAL;
			case 4:
				return PikachuMood.SAD;
			case 5:
				return PikachuMood.DEPRESSED;
			default:
				return PikachuMood.SICK;
		}
	}

}
