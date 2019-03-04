package pi.naut.github;

import com.fasterxml.jackson.databind.ObjectMapper;
import pi.naut.github.model.ActionType;
import pi.naut.github.model.EventType;
import pi.naut.github.model.PullRequest;
import pi.naut.github.model.PullRequestEvent;
import pi.naut.github.model.StateType;
import pi.naut.github.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Singleton
public class GithubService {

	@Inject
	private GithubClient gitHubClient;

	public List<PullRequest> getOpenPullRequests() {
		return gitHubClient.getCurrentUserEvents()
				.stream()
				.filter(event -> event.getType().equals(EventType.PullRequestEvent.name()))
				.map(event -> new ObjectMapper().convertValue(event.getPayload(), PullRequestEvent.class))
				.filter(pr -> pr.getAction() == ActionType.opened)
				.map(pre -> pre.getPullRequest())
				.filter(pr -> pr.getState() == StateType.open)
				.collect(toList());
	}

	public User getCurrentUser() {
		return gitHubClient.getCurrentUser();
	}

}
