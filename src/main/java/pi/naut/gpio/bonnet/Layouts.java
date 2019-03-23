package pi.naut.gpio.bonnet;

import pi.naut.gpio.bonnet.layout.PullRequestDetailsLayout;
import pi.naut.gpio.bonnet.layout.PullRequestLayout;
import pi.naut.gpio.bonnet.layout.RuntimeStatsLayout;
import util.DimesionalIterator;
import util.ImmutableList;

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

	private DimesionalIterator<Layout> primaryLayouts;
	private DimesionalIterator<PullRequestDetailsLayout> secondaryLayouts;

	@PostConstruct
	private void init() {
		primaryLayouts = new ImmutableList<>(asList(
				runtimeStatsLayout,
				pullRequestLayout
		), true).iterator();

		secondaryLayouts = new ImmutableList<>(asList(
				pullRequestDetailsLayout
		), true).iterator();
	}

	public DimesionalIterator<Layout> getPrimaryLayouts() {
		return primaryLayouts;
	}

	public DimesionalIterator<PullRequestDetailsLayout> getSecondaryLayouts() {
		return secondaryLayouts;
	}

}
