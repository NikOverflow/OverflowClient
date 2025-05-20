package com.nikoverflow.overflowclient.setting;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class InternalSettingGroup implements SettingGroup {

    private final Map<String, InternalSetting<?>> settings;

    public InternalSettingGroup() {
        settings = new HashMap<>();
    }

    @Override
    public <T> Setting<T> add(String name, SettingType<T> type, T defaultValue) {
        InternalSetting<T> setting = new InternalSetting<>(type, defaultValue);
        settings.put(name, setting);
        return setting;
    }

    @Override
    public Setting<?> get(String name) {
        return settings.get(name);
    }

    public void fromJson(JsonObject jsonObject) {
        for(Map.Entry<String, InternalSetting<?>> entry : settings.entrySet()) {
            if(jsonObject.has(entry.getKey())) entry.getValue().fromJson(jsonObject.get(entry.getKey()));
        }
    }
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        for(Map.Entry<String, InternalSetting<?>> entry : settings.entrySet()) {
            jsonObject.add(entry.getKey(), entry.getValue().toJson());
        }
        return jsonObject;
    }
}