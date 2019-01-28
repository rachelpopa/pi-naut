package pi.naut.github;

import io.micronaut.context.annotation.Requires;
import io.micronaut.scheduling.annotation.Scheduled;
import pi.naut.github.model.Event;
import pi.naut.github.model.EventType;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@Requires(beans = GitHubClient.class)
public class GitHubJob {

	@Inject
	private GitHubClient gitHubClient;

	@Scheduled(fixedRate = "1m")
	public void updatePullRequests() throws IOException {

		List<Event> getPullRequestEvents = gitHubClient.getCurrentUserEvents()
				.stream()
				.filter(event -> event.getType().equals(EventType.PullRequestEvent.name()))
				.collect(Collectors.toList());

	}

}
