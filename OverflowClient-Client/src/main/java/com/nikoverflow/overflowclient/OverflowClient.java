package com.nikoverflow.overflowclient;

import com.nikoverflow.overflowclient.addon.AddonManager;
import com.nikoverflow.overflowclient.module.ModuleManager;

public class OverflowClient implements IOverflowAPI {

    private static OverflowClient INSTANCE;

    private ModuleManager moduleManager;
    private AddonManager addonManager;

    public void initialize() {
        INSTANCE = this;
        moduleManager = new ModuleManager();
        addonManager = new AddonManager();

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
    public static OverflowClient getInstance() {
        return INSTANCE;
    }
}