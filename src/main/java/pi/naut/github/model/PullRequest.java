package pi.naut.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import pi.naut.github.model.type.StateType;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PullRequest {
	private long id;
	private int number;
	private String title;
	@JsonProperty("repo")
	private Repository repository;
	private StateType state;
	private boolean merged;
	private boolean mergable;
	private String mergableState;

	@Override
	public String toString() {
		return title;
	}
}
