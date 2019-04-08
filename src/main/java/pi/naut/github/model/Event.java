package pi.naut.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;
import pi.naut.github.model.type.EventType;

import java.util.LinkedHashMap;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
	private String id;
	private EventType type;
	private LinkedHashMap payload;
	private Repository repository;
}
