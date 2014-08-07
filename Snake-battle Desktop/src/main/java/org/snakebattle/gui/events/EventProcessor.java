package org.snakebattle.gui.events;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ClassUtils;
import org.snakebattle.utils.reflection.ReflectionUtils;

public abstract class EventProcessor {
	protected Map<Class<?>, Set<EventListener>> eventListeners = new HashMap<>();

	@SuppressWarnings("unchecked")
	protected <T extends EventListener> void onEvent(Event<T> event) {
		Set<EventListener> listeners = eventListeners.get(ReflectionUtils
				.getGenericParameterClass(event.getClass(), Event.class, 0));
		if (listeners == null){
			return;
		}
		EventListener[] listenersArray = listeners.toArray(new EventListener[listeners.size()]);
		if (listeners != null) {
			for (EventListener eventListener : listenersArray) {
				event.onEvent((T) eventListener);
			}
		}
	}

	protected abstract class Event<T extends EventListener> {
		public abstract void onEvent(T eventListener);
	}

	public void subscribe(EventListener eventListener) {
		for (Class<?> listenerInterface : ClassUtils
				.getAllInterfaces(eventListener.getClass())) {
			if (EventListener.class.isAssignableFrom(listenerInterface)) {
				Set<EventListener> listeners = eventListeners
						.get(listenerInterface);
				if (listeners == null) {
					listeners = new HashSet<>();
					eventListeners.put(listenerInterface, listeners);
				}

				listeners.add(eventListener);
			}
		}
	}

	public void unSubscribe(EventListener eventListener) {
		for (Class<?> listenerInterface : ClassUtils
				.getAllInterfaces(eventListener.getClass())) {
			if (EventListener.class.isAssignableFrom(listenerInterface)) {
				Set<EventListener> listeners = eventListeners
						.get(listenerInterface);
				if (listeners != null) {
					listeners.remove(eventListener);
				}
			}
		}
	}
}
