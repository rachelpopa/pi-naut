package pi.naut.devlight;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Value;

import javax.inject.Singleton;

@Factory
class DevlightClientFactory {

	@Value("${github.user}")
	private String user;

	@Value("${github.token}")
	private String token;

	private static final String BASE_URI = "http://<SOME_IP>:<SOME_PORT>";

	@Bean
	@Primary
	@Singleton
	DevlightClient devlightClient() {
		return new DevlightClient(user, token, BASE_URI);
	}

}
