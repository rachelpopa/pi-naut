package pi.naut;

import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.scheduling.annotation.Scheduled;
import pi.naut.gpio.bonnet.display.RefreshDisplayEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ApplicationState {

	@Inject
	private ApplicationEventPublisher applicationEventPublisher;

	@Scheduled(fixedRate = "1s")
	void dummyJob() {
		applicationEventPublisher.publishEvent(new RefreshDisplayEvent());
	}

}
