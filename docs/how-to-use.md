### How To Use

If you are updated to an `oled-bonnet` branch or have a pre-configured project read the **Quick Start Guide** to start making layouts. 
For documentation on how to customize and configure your own project read the **Comprehensive Instructions**.

### Quick Start Guide

#### Layouts

A `Layout` is an interface that defines the display components and I/O events for a single... layout. It implements:

* An `isPrimary()` flag. If true, **primary** layouts can be cycled through by pressing the **center joystick**.

* A `bufferComponents()` method that buffers all the required display components in a builder like fashion.

* The `applyListeners()` and `applyTriggers()` methods which apply layout events to specific pins.

An example **Hello World** layout with a display component and events added to **button A** and **button B**:

```java
@Singleton
public class HelloWorldLayout implements Layout { 
	
	@Inject
	private DisplayComponents displayComponents;

	@Override
	public boolean isPrimary() { return true; }

	@Override
	public void bufferComponents() {
		displayComponents.titleBar("Hello World");
	}

	@Override
	public Map<String, GpioPinListener> applyListeners(OLEDBonnet oledBonnet) {
		Map<String, GpioPinListener> listeners = new HashMap<>();

		listeners.put(PinConfiguration.BUTTON_A, (GpioPinListenerDigital) event -> {
			System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
		});

		return listeners;
	}

	@Override
	public Map<String, GpioTrigger> applyTriggers(OLEDBonnet oledBonnet) {
		Map<String, GpioTrigger> triggers = new HashMap<>();

		triggers.put(PinConfiguration.BUTTON_B, new GpioCallbackTrigger(() -> {
			System.out.println(" --> GPIO TRIGGER CALLBACK RECEIVED");
			return null;
		}));

		return triggers;
	}

}
```

Once you create a `Layout` include it in the `layouts` bean defined in the `LayoutFactory`.

__Note:__ Primary layouts will be cycled through with the **center joystick** in the order they are defined.

__Info:__ Refer to Pi4J docs for creating [listeners](https://pi4j.com/1.2/example/listener.html) and [triggers](https://pi4j.com/1.2/example/trigger.html).

__Tip:__ If you are creating re-usable event classes and need access to the `OLEDBonnet`, data, or other beans in your events, inject them into your layouts and pass it to the event via the events constructor. 
Although you may be tempted to use dependency injection in your event classes, you will likely tie yourself into a circular dependency knot. 

#### Display Components

`DisplayComponents` can be injected into layouts and used to buffer components to the display. 
When implementing a component you can pass it data and use the `SSD1306`'s methods and `DisplayConstants` to:

* Display text
* Draw lines and basic shapes
* Set individual pixels or buffer entire pixel arrays
* Scroll horizontally or vertically
* Flip the display horizontally or vertically
* Invert the display

__Note:__ Out of the box there is a **title bar** and **list components** at your disposal.

#### Application State, Services, and Refresh Display Events

It is likely that you will want to share stateful services across multiple layouts. 
The `ApplicationState` can be used to store and provide the latest state of a service for when it is displayed or refreshed in a layout.
Services can be updated via [@Scheduled](https://docs.micronaut.io/latest/guide/index.html#scheduling) jobs, and when the state of a service is updated you can publish an event with the [ApplicationEventPublisher](https://docs.micronaut.io/latest/guide/index.html#contextEvents) to refresh the display.
Simply publish a `RefreshDisplayEvent` and pass the **class** of the layout(s) that you want to refresh.

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
		mockStateList.next(mockService.getMockList());
		applicationEventPublisher.publishEvent(new RefreshDisplayEvent(MyLayoutOne.NAME, MyLayoutTwo.NAME));
	}
	public StateList<PullRequest> getMockStateList() { return mockStateList; }
}
```

__Note:__ The `StateList` is a utility provided in this project to make it easier to persist the state of a list and is optional to use. 
It is a list with a pre-instantiated `StateIterator` that allows you to iterate through your state (previous, current, and next) and provides utilities for getting data from the original list as well setting the `next()` state.
Dive into the `StateList` class for more documentation.

__Note:__ If you run into a scenario where you need to populate a state prior to the scheduled job updating it for the first time, you can either lazy load it in the getter or use a `@PostConstruct` method to instantiate your data once the bean is created.

__Warning:__ If a layout uses multiple stateful services you must pass the layout class to all the refresh events or your display may not be refreshed when you expect!

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

It also determines **how** to apply events. **Global events** are defined directly in the `OLEDBonnet` class and can be excluded when all **layout events** are removed and re-added.

Lastly, the `OLEDBonnet` controls when to refresh by listening to a [context event](https://docs.micronaut.io/latest/guide/index.html#contextEvents). 
In this case, it is listening for a `RefreshDisplayEvent` and only refreshing the display if the event contains the name of the currently displayed layout.
