package pi.naut.github.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Notification {
	private String id;
	private Subject subject;
	private Repository repository;
	private boolean unread;
}
