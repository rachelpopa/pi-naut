package pi.naut;

import io.micronaut.scheduling.annotation.Scheduled;
import pi.naut.github.GithubService;
import pi.naut.github.model.PullRequest;
import util.DimesionalIterator;
import util.ImmutableList;

import javax.inject.Inject;
import javax.inject.Singleton;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Singleton
public class ApplicationState {

	@Inject
	private GithubService githubService;

	private DimesionalIterator<PullRequest> pullRequests = new ImmutableList<PullRequest>(emptyList()).iterator();

	@Scheduled(initialDelay = "1m", fixedRate = "1m")
	public void updatePullRequests() {
		this.pullRequests = new ImmutableList<>(ofNullable(githubService.getOpenPullRequests())
				.orElse(emptyList()))
				.iterator();
		this.pullRequests.current(); // initialize iterator. TODO, maybe clone pos when setting this
		System.out.println("--> Pull Requests Updated");
	}

	public DimesionalIterator<PullRequest> getPullRequests() { return pullRequests; }

}
