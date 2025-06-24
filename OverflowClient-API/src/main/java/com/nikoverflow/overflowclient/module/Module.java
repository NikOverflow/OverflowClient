package com.nikoverflow.overflowclient.module;

import com.nikoverflow.overflowclient.setting.Settings;

public abstract class Module {

    private boolean enabled = false;
    private Settings settings;

    public void init() {}
    public void onEnable() {}
    public void onDisable() {}

    public final void enable() {
        enabled = true;
        onEnable();
    }
    public final void disable() {
        enabled = false;
        onDisable();
    }
    public final void toggle() {
        if(enabled) disable(); else enable();
    }

    public final boolean isEnabled() {
        return enabled;
    }
    public final Settings getSettings() {
        return settings;
    }

    public abstract String getName();
    public abstract String getDescription();
    public abstract boolean hasKeybind();
}