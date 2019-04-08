package pi.naut.gpio.bonnet;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import pi.naut.gpio.bonnet.layout.PullRequestDetailsLayout;
import pi.naut.gpio.bonnet.layout.PullRequestLayout;
import pi.naut.gpio.bonnet.layout.RuntimeStatsLayout;
import pi.naut.gpio.bonnet.layout.WelcomeLayout;
import util.StateList;

import javax.inject.Inject;
import javax.inject.Singleton;

import static java.util.Arrays.asList;

@Factory
class LayoutFactory {

	// Layouts
	@Inject
	private WelcomeLayout welcomeLayout;
	@Inject
	private PullRequestLayout pullRequestLayout;
	@Inject
	private RuntimeStatsLayout runtimeStatsLayout;
	@Inject
	private PullRequestDetailsLayout pullRequestDetailsLayout;

	@Bean
	@Singleton
	StateList<Layout> layouts() {
		return new StateList<>(asList(
				welcomeLayout,
				runtimeStatsLayout,
				pullRequestLayout,
				pullRequestDetailsLayout
		), true);
	}

}
