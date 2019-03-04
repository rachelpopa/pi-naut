package pi.naut;

import io.micronaut.context.annotation.Requires;
import io.micronaut.scheduling.annotation.Scheduled;
import pi.naut.github.GithubClient;
import pi.naut.github.GithubService;
import pi.naut.gpio.display.ssd1306.SSD1306Display;
import pi.naut.gpio.display.ssd1306.layout.GithubUserLayout;
import pi.naut.gpio.display.ssd1306.layout.PullRequestLayout;
import pi.naut.gpio.display.ssd1306.layout.StatsLayout;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Requires(beans = GithubClient.class)
public class ApplicationJob {

	@Inject
	private SSD1306Display display;
	@Inject
	private GithubService githubService;
	@Inject
	private GithubUserLayout githubUserLayout;
	@Inject
	private PullRequestLayout pullRequestLayout;
	@Inject
	private StatsLayout statsLayout;

	@Scheduled(fixedRate = "1m")
	public void updatePullRequests() {
		pullRequestLayout.setPullRequests(githubService.getOpenPullRequests());
		System.out.println("--> Pull Requests Refreshed");
	}

	@Scheduled(fixedDelay = "10m")
	public void updateUserDetails() {
		githubUserLayout.setUser(githubService.getCurrentUser());
		System.out.println("--> User Details Refreshed");
	}

}
