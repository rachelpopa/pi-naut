package pi.naut.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
	@JsonProperty("login")
	private String user;
	private String displayName;
}
