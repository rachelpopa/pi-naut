package pi.naut.gpio.bonnet;

import io.micronaut.context.event.ApplicationEvent;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class RefreshDisplayEvent extends ApplicationEvent {
	/**
	 * Constructs a prototypical Event.
	 *
	 * @param layouts associated to the refresh event.
	 * @throws IllegalArgumentException if layouts is null.
	 */
	public RefreshDisplayEvent(Class... layouts) {
		super(Arrays.stream(layouts).map(Class::getSimpleName).collect(toList()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getSource() {
		return (List<String>) super.getSource();
	}

}
