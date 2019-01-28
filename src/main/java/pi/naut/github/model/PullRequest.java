package pi.naut.github.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PullRequest {
	private long id;
	private int number;
	private String title;
	private StateType state;
	private boolean merged;
	private boolean mergable;
	private String mergableState;
}
