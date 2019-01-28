package pi.naut.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PullRequestEvent {
	private String action;
	private Integer number;
	@JsonProperty("pull_request")
	private PullRequest pullRequest;
}
