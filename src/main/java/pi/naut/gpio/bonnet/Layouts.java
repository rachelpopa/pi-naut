package pi.naut.gpio.bonnet;

import pi.naut.gpio.bonnet.layout.PullRequestDetailsLayout;
import pi.naut.gpio.bonnet.layout.PullRequestLayout;
import pi.naut.gpio.bonnet.layout.RuntimeStatsLayout;
import util.StateList;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import static java.util.Arrays.asList;

@Singleton
public class Layouts {

	// Primary Layouts
	@Inject
	private PullRequestLayout pullRequestLayout;
	@Inject
	private RuntimeStatsLayout runtimeStatsLayout;

	// Secondary Layouts
	@Inject
	private PullRequestDetailsLayout pullRequestDetailsLayout;

	/***/

	private StateList<Layout> primaryLayouts;
	private StateList<PullRequestDetailsLayout> secondaryLayouts;

	@PostConstruct
	private void init() {
		primaryLayouts = new StateList<>(asList(
				runtimeStatsLayout,
				pullRequestLayout
		), true);

		secondaryLayouts = new StateList<>(asList(
				pullRequestDetailsLayout
		), true);
	}

	public StateList<Layout> getPrimaryLayouts() {
		return primaryLayouts;
	}

	public StateList<PullRequestDetailsLayout> getSecondaryLayouts() {
		return secondaryLayouts;
	}

}
