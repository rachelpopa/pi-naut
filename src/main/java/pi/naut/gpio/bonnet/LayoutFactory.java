package pi.naut.gpio.bonnet;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import pi.naut.gpio.bonnet.layout.WelcomeLayout;
import util.StateList;

import javax.inject.Inject;
import javax.inject.Singleton;

import static java.util.Arrays.asList;

@Factory
class LayoutFactory {

	@Inject
	private WelcomeLayout welcomeLayout;

	@Bean
	@Primary
	@Singleton
	StateList<Layout> layouts() {
		return new StateList<>(asList(
				welcomeLayout
		), true);
	}

}
