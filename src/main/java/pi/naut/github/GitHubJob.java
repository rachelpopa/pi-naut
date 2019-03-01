package pi.naut.github;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.annotation.Requires;
import io.micronaut.scheduling.annotation.Scheduled;
import pi.naut.github.model.ActionType;
import pi.naut.github.model.EventType;
import pi.naut.github.model.PullRequest;
import pi.naut.github.model.PullRequestEvent;
import pi.naut.github.model.StateType;
import pi.naut.gpio.display.ssd1306.SSD1306Display;
import pi.naut.gpio.display.ssd1306.core.component.Action;
import pi.naut.gpio.display.ssd1306.layout.DefaultLayout;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

@Singleton
@Requires(beans = GitHubClient.class)
public class GitHubJob {

	@Inject
	private GitHubClient gitHubClient;
	@Inject
	private SSD1306Display display;

	@Scheduled(fixedRate = "1m")
	public void playground() throws IOException {

		List<PullRequest> prs = getOpenPullRequests();

		// just for dev
		int size = prs.size() > 2 ? 3 : prs.size();

		List<PullRequest> items = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			items.add(prs.get(i));
		}

		Action detailsAction = new Action("DETAILS", "DET", "De", f -> null);

		Action dismissAction = new Action("DISMISS", "DIS", "Di", f -> null);

		DefaultLayout pullRequests = new DefaultLayout("PULL REQUESTS", asList(detailsAction, dismissAction), items);

		display.clearDisplay();
		display.addLayout(pullRequests);
		display.display();

		System.out.println("Refreshed!");
	}

	private List<PullRequest> getOpenPullRequests() {
		return gitHubClient.getCurrentUserEvents()
				.stream()
				.filter(event -> event.getType().equals(EventType.PullRequestEvent.name()))
				.map(event -> new ObjectMapper().convertValue(event.getPayload(), PullRequestEvent.class))
				.filter(pr -> pr.getAction() == ActionType.opened)
				.map(pre -> pre.getPullRequest())
				.filter(pr -> pr.getState() == StateType.open)
				.collect(toList());
	}

}
