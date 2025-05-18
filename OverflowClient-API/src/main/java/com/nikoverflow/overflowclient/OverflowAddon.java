package com.nikoverflow.overflowclient;

public abstract class OverflowAddon {

    private static IOverflowAPI api;

    public void onLoad() {}
    public void onEnable() {}
    public void onDisable() {}

    public static IOverflowAPI getOverflowAPI() {
        return api;
    }
}
