package pi.naut.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.LinkedHashMap;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
	private String id;
	private String type;
	private LinkedHashMap payload;
	private Repository repository;
}
