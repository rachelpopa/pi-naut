package pi.naut.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import pi.naut.github.model.type.ActionType;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PullRequestEvent {
	private ActionType action;
	@JsonProperty("pull_request")
	private PullRequest pullRequest;
}
