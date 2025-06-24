package com.nikoverflow.overflowclient.module;

import com.google.gson.JsonObject;
import com.nikoverflow.overflowclient.setting.InternalSettings;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ModuleManager implements IModuleManager {

    private final Map<String, Module> modules;

    public ModuleManager() {
        modules = new HashMap<>();
    }

    @Override
    public void addModule(Module module) {
        try {
            Field settingsField = Module.class.getDeclaredField("settings");
            settingsField.setAccessible(true);
            settingsField.set(module, new InternalSettings());
            settingsField.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        module.init();
        modules.put(module.getName(), module);
    }
    @Override
    public Module getModule(String name) {
        return modules.get(name);
    }

    public void fromJson(JsonObject jsonObject) {
        for(Map.Entry<String, Module> entry : modules.entrySet()) {
            if(!jsonObject.has(entry.getKey())) continue;
            if(jsonObject.getAsJsonObject(entry.getKey()).get("enabled").getAsBoolean()) entry.getValue().enable();
            // TODO: parse keybind if exists
            ((InternalSettings) entry.getValue().getSettings()).fromJson(jsonObject.getAsJsonObject(entry.getKey()).getAsJsonObject("settings"));
        }
    }
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        for(Map.Entry<String, Module> entry : modules.entrySet()) {
            JsonObject module = new JsonObject();
            module.addProperty("enabled", entry.getValue().isEnabled());
            // TODO: add keybind if exists
            module.add("settings", ((InternalSettings) entry.getValue().getSettings()).toJson());
            jsonObject.add(entry.getKey(), module);
        }
        return jsonObject;
    }
}