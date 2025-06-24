package com.nikoverflow.overflowclient.addon;

import com.nikoverflow.overflowclient.OverflowAddon;
import com.nikoverflow.overflowclient.OverflowClient;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class AddonManager {

    private final Map<String, OverflowAddon> addons;

    public AddonManager() {
        addons = new HashMap<>();
        try {
            Field apiField = OverflowAddon.class.getDeclaredField("api");
            apiField.setAccessible(true);
            apiField.set(null, OverflowClient.getInstance());
            apiField.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            throw new RuntimeException(exception);
        }
        for(EntrypointContainer<OverflowAddon> container : FabricLoader.getInstance().getEntrypointContainers("OverflowAddon", OverflowAddon.class)) addons.put(container.getProvider().getMetadata().getName(), container.getEntrypoint());
    }

    public void loadAddons() {
        addons.forEach((name, addon) -> addon.onLoad());
    }
    public void enableAddons() {
        addons.forEach((name, addon) -> addon.onEnable());
    }
    public void disableAddons() {
        addons.forEach((name, addon) -> addon.onDisable());
    }
}