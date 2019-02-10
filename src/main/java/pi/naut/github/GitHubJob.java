package pi.naut.github;

import io.micronaut.context.annotation.Requires;
import io.micronaut.scheduling.annotation.Scheduled;
import pi.naut.github.model.PullRequest;
import pi.naut.gpio.display.ssd1306.Action;
import pi.naut.gpio.display.ssd1306.Item;
import pi.naut.gpio.display.ssd1306.SSD1306Display;
import pi.naut.gpio.display.ssd1306.layout.DefaultLayout;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;

@Singleton
@Requires(beans = GitHubClient.class)
public class GitHubJob {

	@Inject
	private GitHubClient gitHubClient;

	@Inject
	private SSD1306Display display;

	@Scheduled(fixedRate = "30m")
	public void updatePullRequests() throws IOException {

//		List<PullRequest> prs = getOpenPullRequests();

		DefaultLayout pullRequests = new DefaultLayout(
				"PULL REQUESTS",
				asList(
						new Item("Thing 1 is VERY good", true),
						new Item("Thing 2 is VERY bad", false),
						new Item("Thing 3 is VERY meh", false)
				),
				asList(
						new Action("DET", false),
						new Action("DIS", true),
						new Action("DEL", false)
				));

		display.displayLayout(pullRequests);

	}

	private List<PullRequest> getOpenPullRequests() {
//		return gitHubClient.getCurrentUserEvents()
//				.stream()
//				.filter(event -> event.getType().equals(EventType.PullRequestEvent.name()))
//				.map(event -> new ObjectMapper().convertValue(event.getPayload(), PullRequestEvent.class))
//				.filter(pr -> pr.getAction() == ActionType.opened)
//				.map(PullRequestEvent::getPullRequest)
//				.filter(pr -> pr.getState() == StateType.open)
//				.collect(Collectors.toList());
		return null;
	}

}
