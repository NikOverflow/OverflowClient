package com.nikoverflow.overflowclient;

import com.nikoverflow.overflowclient.event.IEventManager;
import com.nikoverflow.overflowclient.module.IModuleManager;

public interface IOverflowAPI {

    IModuleManager getModuleManager();
    IEventManager getEventManager();
}