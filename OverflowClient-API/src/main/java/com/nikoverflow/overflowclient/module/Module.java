package com.nikoverflow.overflowclient.module;

import com.nikoverflow.overflowclient.OverflowAddon;
import com.nikoverflow.overflowclient.event.EventListener;
import com.nikoverflow.overflowclient.setting.Settings;

public abstract class Module implements EventListener {

    private boolean enabled = false;
    private Settings settings;

    public void onEnable() {}
    public void onDisable() {}

    public final void enable() {
        OverflowAddon.getOverflowAPI().getEventManager().registerEvents(this);
        enabled = true;
        onEnable();
    }
    public final void disable() {
        OverflowAddon.getOverflowAPI().getEventManager().unregisterEvents(this);
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