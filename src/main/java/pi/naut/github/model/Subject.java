package pi.naut.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;
import pi.naut.github.model.type.SubjectType;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Subject {
	private String title;
	private SubjectType type;
}
