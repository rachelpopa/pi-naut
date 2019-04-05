package pi.naut;

import io.micronaut.scheduling.annotation.Scheduled;
import pi.naut.github.GithubService;
import pi.naut.github.model.PullRequest;
import util.StateList;

import javax.inject.Inject;
import javax.inject.Singleton;

import static java.util.Collections.emptyList;

@Singleton
public class ApplicationState {

	@Inject
	private GithubService githubService;

	private StateList<PullRequest> pullRequests = new StateList<>(emptyList());

	@Scheduled(initialDelay = "30s", fixedRate = "1m")
	public void updatePullRequests() {
		this.pullRequests = new StateList<>(githubService.getOpenPullRequests());
		System.out.println("--> Pull Requests Updated");
	}

	public StateList<PullRequest> getPullRequests() { return pullRequests; }

}
