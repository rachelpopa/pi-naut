package pi.naut.github.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Organization {
	private String id;
	private String login;
}
