package pi.naut.gpio.display.ssd1306.core.component;

import pi.naut.gpio.display.ssd1306.core.component.wrapper.Selectable;

import java.util.function.Function;

public class Action extends Selectable {

	private String longDescription;
	private String shortDescription;
	private String abbreviation;
	private Function function;

	public Action(String longDescription, String shortDescription, String abbreviation, Function function) {
		super();
		this.longDescription = longDescription;
		this.shortDescription = shortDescription;
		this.abbreviation = abbreviation;
		this.function = function;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public Function getFunction() {
		return function;
	}

}
