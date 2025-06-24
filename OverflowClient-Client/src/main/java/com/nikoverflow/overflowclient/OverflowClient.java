package com.nikoverflow.overflowclient;

import com.nikoverflow.overflowclient.module.ModuleManager;

public class OverflowClient implements IOverflowAPI {

    private static OverflowClient INSTANCE;

    private ModuleManager moduleManager;

    public void initialize() {
        INSTANCE = this;
        moduleManager = new ModuleManager();
    }

    @Override
    public ModuleManager getModuleManager() {
        return moduleManager;
    }
    
    public static OverflowClient getInstance() {
        return INSTANCE;
    }
}