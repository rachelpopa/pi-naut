package pi.naut.github;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;

import static pi.naut.github.GitHubClientConfiguration.PREFIX;

@ConfigurationProperties(PREFIX)
public class GitHubClientConfiguration {

	static final String PREFIX = "github";
	static final String GITHUB_API_URL   = "https://api.github.com/";

	@Getter
	@Setter
	private String user;

	@Getter
	@Setter
	private String token;

}
