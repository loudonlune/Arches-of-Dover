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
	
	static EventHandlerWrapper wrapClass(EventBus parent, Class<?> wrappedClass) {
		Optional<Constructor<?>> defaultCtor = Arrays.asList(wrappedClass.getConstructors())
				.stream()
				.filter(ctor -> ctor.getParameterCount() == 0)
				.findFirst();
		
		if (defaultCtor.isEmpty())
			return null;
		
		Constructor<?> unwrappedCtor = defaultCtor.get();
		
		return new EventHandlerWrapper(parent, wrappedClass, unwrappedCtor);
	}
	
	private EventHandlerWrapper(EventBus parent, Class<?> wrappedClass, Constructor<?> defaultConstructor) {
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
		
		try {
			eventHandlerInstance = defaultConstructor.newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			logger.severe("Failed to create handler instance. Reason: " + e.getMessage());
			logger.severe(e.toString());
		}
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
