package pi.naut.github.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.LinkedHashMap;

@Data
@Accessors(chain = true)
public class Event {
	private String id;
	private String type;
	private LinkedHashMap payload;
}
