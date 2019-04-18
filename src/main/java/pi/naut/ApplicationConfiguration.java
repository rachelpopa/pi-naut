package pi.naut;

import devlights.client.DevlightClient;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Value;

import javax.inject.Singleton;

@Factory
class ApplicationConfiguration {

	@Value("${github.user}")
	private String user;
	@Value("${github.token}")
	private String token;

	@Bean
	@Primary
	@Singleton
	DevlightClient devlightClient() {
		return new DevlightClient(user, token, "https://<IP>:<PORT>");
	}

}
