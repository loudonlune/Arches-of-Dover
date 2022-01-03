package org.loudonlune.arches.event;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

public class EventHandlerWrapper {
	private Class<?> wrappedClass;
	private HashMap<Class<?>, Method> eventMethodMap;
	private Logger logger;
	private Object eventHandlerInstance;
	
	public EventHandlerWrapper(EventBus parent, Class<?> wrappedClass) {
		this.wrappedClass = wrappedClass;
		eventMethodMap = new HashMap<>();
		logger = parent.getLogger();
		
		for (Method m : wrappedClass.getMethods()) {
			if (m.getParameterCount() == 1) {
				Class<?> parameterClass = m.getParameters()[0].getType();
				
				if (parent.hasEvent(parameterClass)) {
					eventMethodMap.put(parameterClass, m);
				}
			}
		}
		
		Optional<Constructor<?>> defaultCtor = Arrays.asList(wrappedClass.getConstructors())
				.stream()
				.filter(ctor -> ctor.getParameterCount() == 0)
				.findFirst();
		
		if (defaultCtor.isEmpty())
			throw new IllegalStateException("An event handler with no default constructor was loaded. This is not allowed.");
		
		Constructor<?> unwrappedCtor = defaultCtor.get();
		try {
			eventHandlerInstance = unwrappedCtor.newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			logger.severe("Failed to create handler instance. Reason: " + e.getMessage());
			logger.severe(e.toString());
		}
		
		logger.info("Initialized event handler with " + eventMethodMap.size() + " handling methods.");
	}
		
	public Class<?> getWrappedClass() {
		return wrappedClass;
	}
	
	public Object getInstance() {
		return eventHandlerInstance;
	}
	
	public boolean handlesEvent(Class<?> eventClass) {
		return eventMethodMap.containsKey(eventClass);
	}
	
	public Set<Class<?>> getEventsHandled() {
		return eventMethodMap.keySet();
	}
	
	public boolean handle(Class<?> eventClass, Object eventInstance) {
		if (eventMethodMap.containsKey(eventClass)) {
			try {
				eventMethodMap.get(eventClass).invoke(eventHandlerInstance, eventInstance);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				logger.severe("Failed to invoke Event Handler method. An exception was thrown: ");
				logger.severe(e.toString());
				return false;
			}
			
			return true;
		}
		
		return false;
	}
}
