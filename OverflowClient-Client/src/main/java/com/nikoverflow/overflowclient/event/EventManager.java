package com.nikoverflow.overflowclient.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class EventManager implements IEventManager {

    private final Map<Class<? extends Event>, Map<EventPriority, List<RegisteredHandler>>> registeredHandlers;

    public EventManager() {
        registeredHandlers = new HashMap<>();
    }

    @Override
    public void registerEvents(EventListener listener) {
        for(Method method : listener.getClass().getDeclaredMethods()) {
            if(!method.isAnnotationPresent(EventHandler.class) || method.getParameterCount() != 1) continue;
            Class<?> parameter = method.getParameterTypes()[0];
            if(!Event.class.isAssignableFrom(parameter)) continue;
            EventHandler eventHandler = method.getAnnotation(EventHandler.class);
            registeredHandlers.computeIfAbsent(parameter.asSubclass(Event.class), clazz -> new EnumMap<>(EventPriority.class)).computeIfAbsent(eventHandler.priority(), priority -> new ArrayList<>()).add(new RegisteredHandler(listener, method, eventHandler.priority(), eventHandler.ignoreCancelled()));
        }
    }
    @Override
    public void unregisterEvents(EventListener listener) {
        for(Map<EventPriority, List<RegisteredHandler>> handlerList : registeredHandlers.values()) handlerList.values().forEach(registeredHandlers -> registeredHandlers.removeIf(registeredHandler -> registeredHandler.listener.equals(listener)));
        registeredHandlers.entrySet().removeIf(entry -> entry.getValue().values().stream().allMatch(List::isEmpty));
    }

    @Override
    public void callEvent(Event event) {
        Map<EventPriority, List<RegisteredHandler>> handlerList = registeredHandlers.get(event.getClass());
        if(handlerList == null) return;
        handlerList.forEach((eventPriority, registeredHandlers) -> registeredHandlers.forEach(registeredHandler -> {
            if(event instanceof Cancellable cancellable && cancellable.isCancelled() && registeredHandler.ignoreCancelled) return;
            try {
                registeredHandler.method.invoke(registeredHandler.listener, event);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    private record RegisteredHandler(EventListener listener, Method method, EventPriority priority, boolean ignoreCancelled) {}
}