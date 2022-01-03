package org.loudonlune.arches.event;

import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class EventBus {
	// TODO: make these two a single member, access-control managed by a mutex
	private Set<Event> eventQueueSet;
	private PriorityQueue<Event> eventQueue;
	
	private Set<EventHandlerWrapper> handlers;
	private Set<Class<? extends Event>> events;
	private Logger eventBusLogger;
	
	public Logger getLogger() {
		return eventBusLogger;
	}
	
	public boolean hasEvent(Class<?> candidateClass) {
		return events.contains(candidateClass);
	}
	
	/**
	 * Queues an event on the event bus.
	 * TODO: make this thread-safe
	 * @param e
	 * @return
	 */
	public boolean queueEvent(Event e) {
		if (events.contains(e.getClass())) {
			if (!eventQueueSet.add(e))
				return false;
			
			eventQueue.add(e);
			return true;
		} else throw new IllegalStateException("An unknown event type was queued.");
	}
	
	/**
	 * Searches classpath for event classes.
	 * Use the reflections ConfigurationBuilder to specify where in the classpath searching should happen.
	 * @param searchPackage Package to search.
	 * @return Number of events and event handlers found and registered.
	 */
	public int searchForEventClasses(ConfigurationBuilder searchPackage) {
		Reflections reflectionUtils = new Reflections(searchPackage);
		Set<Class<?>> listenerClasses = 
				reflectionUtils.getTypesAnnotatedWith(EventHandler.class);
		
		Set<Class<? extends Event>> eventClasses = reflectionUtils.getSubTypesOf(Event.class);
		
		events.addAll(
				eventClasses
				.stream()
				.filter(
						c -> !Modifier.isAbstract(c.getModifiers())
				).toList());
		
		for (Class<?> c : listenerClasses)
			handlers.add(new EventHandlerWrapper(this, c));
		
		return listenerClasses.size() + eventClasses.size();
	}
	
	/**
	 * @return
	 */
	public Set<EventHandlerWrapper> handlers() {
		return handlers;
	}
	
	public List<String> getKnownEvents() {
		return events
		.stream()
		.map(c -> c.getName())
		.collect(Collectors.toList());
	}
	
	public List<String> getKnownEventHandlers() {
		return handlers
		.stream()
		.map(c -> c.getClass().getName())
		.collect(Collectors.toList());
	}
	
	public EventBus() {
		handlers = new HashSet<>();
		events = new HashSet<>();
		eventQueueSet = new HashSet<>();
		eventQueue = new PriorityQueue<>();
		eventBusLogger = Logger.getLogger("EventBus");
		
		eventBusLogger.info("Event bus initializing...");
		
		ConfigurationBuilder searchConfig = new ConfigurationBuilder()
				.forPackage("org.loudonlune.arches")
				.filterInputsBy(new FilterBuilder()
						.includePackage("org.loudonlune.arches")
						.excludePackage("org.loudonlune.arches.math")
					);
		
		eventBusLogger.info("Loaded " + searchForEventClasses(searchConfig) + " components.");
	}
	
	public void fireEvents() {
		while (!eventQueue.isEmpty()) {
			Event e = eventQueue.remove();
			
			if (e.isCancelled())
				continue;
			
			for (EventHandlerWrapper eh : handlers)
				eh.handle(e.getClass(), e);
		}
	}
	
}
