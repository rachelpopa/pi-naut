package pi.naut;

import io.micronaut.context.annotation.Factory;
import lombok.Getter;
import lombok.Setter;
import pi.naut.github.model.PullRequest;

import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;

@Factory
public class PiStore {

	@Getter
	@Setter
	@Singleton
	private Set<PullRequest> pullRequests = new HashSet<>();

}
