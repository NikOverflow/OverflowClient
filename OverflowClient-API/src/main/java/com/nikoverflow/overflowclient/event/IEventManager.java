package com.nikoverflow.overflowclient.event;

public interface IEventManager {

    void registerEvents(EventListener listener);
    void unregisterEvents(EventListener listener);
    void callEvent(Event event);
}