package com.nikoverflow.overflowclient;

import com.nikoverflow.overflowclient.addon.AddonManager;
import com.nikoverflow.overflowclient.event.EventManager;
import com.nikoverflow.overflowclient.module.ModuleManager;

public class OverflowClient implements IOverflowAPI {

    private static OverflowClient INSTANCE;

    private AddonManager addonManager;
    private ModuleManager moduleManager;
    private EventManager eventManager;

    public void initialize() {
        INSTANCE = this;
        addonManager = new AddonManager();
        moduleManager = new ModuleManager();
        eventManager = new EventManager();

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));

        addonManager.loadAddons();
    }
    public void lateInitialize() {
        addonManager.enableAddons();
    }
    public void shutdown() {
        addonManager.disableAddons();
    }

    @Override
    public ModuleManager getModuleManager() {
        return moduleManager;
    }
    @Override
    public EventManager getEventManager() {
        return eventManager;
    }
    public static OverflowClient getInstance() {
        return INSTANCE;
    }
}