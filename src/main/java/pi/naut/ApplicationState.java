package pi.naut;

import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.scheduling.annotation.Scheduled;
import pi.naut.github.GithubService;
import pi.naut.github.model.PullRequest;
import pi.naut.gpio.bonnet.RefreshDisplayEvent;
import pi.naut.gpio.bonnet.layout.PullRequestLayout;
import pi.naut.gpio.bonnet.layout.RuntimeStatsLayout;
import util.StateList;

import javax.inject.Inject;
import javax.inject.Singleton;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

@Singleton
public class ApplicationState {

	@Inject
	private ApplicationEventPublisher applicationEventPublisher;
	@Inject
	private GithubService githubService;

	private StateList<PullRequest> pullRequests = new StateList<>(emptyList());
	private StateList<String> runtimeStats = new StateList<>(emptyList());

	@Scheduled(fixedRate = "1m")
	public void updatePullRequests() {
		pullRequests = new StateList<>(githubService.getOpenPullRequests());
		System.out.println(pullRequests.getList().size());
		applicationEventPublisher.publishEvent(new RefreshDisplayEvent(PullRequestLayout.NAME));
	}
	public StateList<PullRequest> getPullRequests() { return pullRequests; }

	@Scheduled(fixedRate = "1s")
	public void updateRuntimeStats() {
		runtimeStats = new StateList<>(asList(
				"Cores: " + Runtime.getRuntime().availableProcessors(),
				"Free mem: " + Runtime.getRuntime().freeMemory(),
				"Max mem: " + Runtime.getRuntime().maxMemory(),
				"Tot mem: " + Runtime.getRuntime().totalMemory()));
		applicationEventPublisher.publishEvent(new RefreshDisplayEvent(RuntimeStatsLayout.NAME));
	}
	public StateList<String> getRuntimeStats() { return runtimeStats; }

}
