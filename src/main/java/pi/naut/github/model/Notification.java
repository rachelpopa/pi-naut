package pi.naut.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Notification {
	private String id;
	private Subject subject;
	private Repository repository;
	private boolean unread;
}
