package com.nikoverflow.overflowclient.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class EventManager implements IEventManager {

    private final Map<Class<? extends Event>, List<RegisteredHandler>> registeredHandlers;

    public EventManager() {
        registeredHandlers = new HashMap<>();
    }

    @Override
    public void registerEvents(EventListener listener) {
        for(Method method : listener.getClass().getDeclaredMethods()) {
            if(!method.isAnnotationPresent(EventHandler.class) || method.getParameterCount() != 1) continue;
            Class<?> parameter = method.getParameterTypes()[0];
            if(!Event.class.isAssignableFrom(parameter)) continue;
            Class<? extends Event> eventClass = parameter.asSubclass(Event.class);
            EventHandler eventHandler = method.getAnnotation(EventHandler.class);
            registeredHandlers.computeIfAbsent(eventClass, k -> new ArrayList<>()).add(new RegisteredHandler(listener, method, eventHandler.priority(), eventHandler.ignoreCancelled()));
            registeredHandlers.get(eventClass).sort(Comparator.comparing(registeredHandler -> registeredHandler.priority));
        }
    }
    @Override
    public void unregisterEvents(EventListener listener) {
        for(List<RegisteredHandler> handlerList : registeredHandlers.values()) {
            handlerList.removeIf(registeredHandler -> registeredHandler.listener.equals(listener));
        }
        registeredHandlers.entrySet().removeIf(registeredHandler -> registeredHandler.getValue().isEmpty());
    }

    @Override
    public void callEvent(Event event) {
        List<RegisteredHandler> handlerList = registeredHandlers.get(event.getClass());
        if(handlerList == null) return;
        handlerList.forEach(registeredHandler -> {
            try {
                if(event instanceof Cancellable cancellable && cancellable.isCancelled() && registeredHandler.ignoreCancelled) return;
                registeredHandler.method.invoke(registeredHandler.listener, event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private record RegisteredHandler(EventListener listener, Method method, EventPriority priority, boolean ignoreCancelled) {}
}