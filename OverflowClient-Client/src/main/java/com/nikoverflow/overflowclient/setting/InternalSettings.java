package com.nikoverflow.overflowclient.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class InternalSettings implements Settings {

    private final Map<String, InternalSettingGroup> settingGroups;

    public InternalSettings() {
        settingGroups = new HashMap<>();
        settingGroups.put("default", new InternalSettingGroup());
    }

    @Override
    public SettingGroup defaultGroup() {
        return settingGroups.get("default");
    }

    @Override
    public SettingGroup group(String name) {
        return settingGroups.computeIfAbsent(name, key -> new InternalSettingGroup());
    }

    public void fromJson(JsonObject jsonObject) {
        for(Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String group = entry.getKey();
            JsonElement jsonElement = entry.getValue();
            if(!jsonElement.isJsonObject()) continue;
            InternalSettingGroup settingGroup = settingGroups.get(group);
            if(settingGroup != null) settingGroup.fromJson(jsonElement.getAsJsonObject());
        }
    }
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        for(Map.Entry<String, InternalSettingGroup> entry : settingGroups.entrySet()) {
            if(entry.getValue().toJson().isEmpty()) continue;
            jsonObject.add(entry.getKey(), entry.getValue().toJson());
        }
        return jsonObject;
    }
}