## How To Use

If you are using a pre-configured project read the **Quick Start Guide** to start making and testing layouts pronto! 
For documentation on how to customize and configure your own project read the **Comprehensive Instructions**.

### Quick Start Guide

#### Layouts

A **Layout** is an interface that defines the display components and I/O events for a single... layout. It implements:

* A `name()`, which is best defined as a `public static final String`. Used for filtering.

* An `isPrimary()` flag. If true, primary layouts can be cycled through by pressing the **center joystick**.

* A `bufferComponents()` method that buffers all the required display components in a builder like fashion.

* The `applyListeners()` and `applyTriggers()` methods which use map configurations to apply events to specific pins.

When implementing the `OLEDBonnet` to display layouts, two things will happen when it is displayed:

1. The current state of all display components are **buffered** and **sent** to the display.
2. All prior layout events are removed and the new layout event are added to the pins defined in the layout.

This is an example of a **Hello World layout** with a title component and a mock events added to **BUTTON_X** and **BUTTON_Y**.

```java
@Singleton
public class HelloWorldLayout implements Layout { 
	
	@Inject
	private DisplayComponents displayComponents;
	@Inject
	private ApplicationState applicationState;

	public static final String NAME = "Hello World";

	@Override
	public String name() { return NAME; }
	
	@Override
	public boolean isPrimary() { return true; }
	
	@Override
	public void bufferComponents() { 
		displayComponents.title(NAME);
	}

	@Override
	public Map<String, GpioPinListener> applyListeners(OLEDBonnet oledBonnet) { 
		Map<String, GpioPinListener> listeners = new HashMap<>();
		listeners.put(PinConfiguration.BUTTON_X, new MockListener());
		return listeners;
	}

	@Override
	public Map<String, GpioTrigger> applyTriggers(OLEDBonnet oledBonnet) { 
				Map<String, GpioTrigger> triggers = new HashMap<>();
        		triggers.put(PinConfiguration.BUTTON_Y, new MockTrigger());
        		return triggers;
	 }

}
```

__Note:__ Once you create a `Layout`, add it to the `LayoutFactory` to have it available for use by the `OLEDBonnet`. 


#### Application State, Services, and Refresh Display Events

It is likely that you will want to share stateful services across multiple layouts. 
The `ApplicationState` can then be injected into a `Layout` to provide the latest state of that service when it is displayed or refreshed.
Services can be **updated** via [@Scheduled](https://docs.micronaut.io/latest/guide/index.html#scheduling), and when the state of a service is updated you can use [ApplicationEventPublisher](https://docs.micronaut.io/latest/guide/index.html#contextEvents) to refresh the display.
Simply publish a `RefreshDisplayEvent` and pass the **NAME** of the layout(s) that call this service.

An example implementation of a mock stateful service:

```java
@Singleton
public class ApplicationState {

	@Inject
	private ApplicationEventPublisher applicationEventPublisher;
	@Inject
	private Mockservice mockService;

	private StateList<String> mockStateList;

	@Scheduled(fixedRate = "1m")
	void updateMockStateList() {
		this.mockStateList = new StateList<>(mockService.getMockList());
		applicationEventPublisher.publishEvent(new RefreshDisplayEvent(MyLayoutOne.NAME, MyLayoutTwo.NAME));
	}
	public StateList<PullRequest> getMockStateList() { return mockStateList; }
}
```

__Note:__ The `StateList` is a utility provided to make it easier to persist the state of a list and is not required to use.
When dealing with Iterators, you often need to return the origin list. 
This is a custom implementation with a pre-instantiated `StateIterator` and allows you to iterate through your state (previous, current, and next) and still returns the origin list.

__Warning:__ If a layout uses multiple stateful services you must pass the name of that layout to all the refresh events or your layout may not be refreshed when you expect!

### Comprehensive Instructions

#### Pin Configuration

You can define which pins are connected to each I/O in the [PinConfiguration](src/main/java/pi/naut/gpio/config/PinConfiguration.java). 
Each pin is instantiated in a static map and referred to by a logical name that describes the pin. 
For example, a digital button connected to pin #2 on a GPIO may be configured like this:

```java
public class PinConfiguration {
	public static final String DIGITAL_BUTTON = "DIGITAL_BUTTON";

	public static final Map<String, Pin> DIGITAL_INPUT_PINS;
	static {
		Map<String, Pin> aMap = new HashMap<>();
		aMap.put(DIGITAL_BUTTON, RaspiPin.GPIO_02);
		DIGITAL_INPUT_PINS = Collections.unmodifiableMap(aMap);
	}
}
```

#### Pin Controller

Creates a singleton `GpioController` for provisioning and accessing the GPIO. 
Injecting it gives you access to the pins defined in the `PinConfiguration`. 
All configured pins should be provisioned here.

#### Display Controller

Creates a singleton `SSD1306` controller for an OLED display component.
`SSD1306` has distinct methods for **buffering** and **displaying** arrays of pixel states, so the `DisplayController` implements convenience methods for displaying a `Layout` in it's entirety.

#### OLED Bonnet

The `OLEDBonnet` injects both `PinController` and `DisplayController`. It is the default implementation for displaying a `Layout` on the screen while applying I/O events to buttons and switches. 

By default it also determines **how** to apply events. **Global events** are defined directly in the `OLEDBonnet` class and can be excluded when all **layout events** are removed and re-added.

Lastly, the `OLEDBonnet` controls when to refresh the display via a [context event](https://docs.micronaut.io/latest/guide/index.html#contextEvents). 
In this case, it is listening for a `RefreshDisplayEvent` and only refreshing the display if the event contains the name of the currently displayed layout.
