package pi.naut.github.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Subject {
	private String title;
	private SubjectType type;
}
