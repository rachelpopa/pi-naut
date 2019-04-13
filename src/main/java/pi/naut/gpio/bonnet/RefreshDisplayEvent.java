package pi.naut.gpio.bonnet;

import io.micronaut.context.event.ApplicationEvent;

import java.util.List;

import static java.util.Arrays.asList;

public class RefreshDisplayEvent extends ApplicationEvent {
	/**
	 * Constructs a prototypical Event.
	 *
	 * @param layouts associated to the refresh event.
	 * @throws IllegalArgumentException if layouts is null.
	 */
	public RefreshDisplayEvent(String... layouts) {
		super(asList(layouts));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getSource() {
		return (List<String>) super.getSource();
	}

}
