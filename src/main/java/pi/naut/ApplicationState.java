package pi.naut;

import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.scheduling.annotation.Scheduled;
import pi.naut.github.GithubService;
import pi.naut.github.model.PullRequest;
import pi.naut.gpio.bonnet.display.RefreshDisplayEvent;
import pi.naut.gpio.bonnet.layout.PullRequestDetailsLayout;
import pi.naut.gpio.bonnet.layout.PullRequestLayout;
import pi.naut.gpio.bonnet.layout.RuntimeStatsLayout;
import util.StateList;

import javax.inject.Inject;
import javax.inject.Singleton;

import static java.util.Arrays.asList;

@Singleton
public class ApplicationState {

	@Inject
	private ApplicationEventPublisher applicationEventPublisher;
	@Inject
	private GithubService githubService;

	private StateList<PullRequest> openPullRequests;
	private StateList<String> runtimeStats;

	@Scheduled(initialDelay = "20s", fixedRate = "1m")
	void updatePullRequests() {
		this.openPullRequests = new StateList<>(githubService.getOpenPullRequests());
		applicationEventPublisher.publishEvent(new RefreshDisplayEvent(PullRequestLayout.class, PullRequestDetailsLayout.class));
	}

	@Scheduled(initialDelay = "20s", fixedRate = "1s")
	void updateRuntimeStats() {
		this.runtimeStats = new StateList<>(asList(
				"Cores: " + Runtime.getRuntime().availableProcessors(),
				"Free mem: " + Runtime.getRuntime().freeMemory(),
				"Max mem: " + Runtime.getRuntime().maxMemory(),
				"Tot mem: " + Runtime.getRuntime().totalMemory()));
		applicationEventPublisher.publishEvent(new RefreshDisplayEvent(RuntimeStatsLayout.class));
	}

	public StateList<PullRequest> getOpenPullRequests() {
		if (openPullRequests == null) {
			this.openPullRequests = new StateList<>(githubService.getOpenPullRequests());
		}
		return openPullRequests;
	}

	public StateList<String> getRuntimeStats() {
		if (runtimeStats == null) {
			updateRuntimeStats();
		}
		return this.runtimeStats;
	}

}
