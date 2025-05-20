package com.nikoverflow.overflowclient.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class InternalSettings implements Settings {

    private final Map<String, SettingGroup> settingGroups;

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
        SettingGroup group;
        if(settingGroups.containsKey(name)) {
            group = settingGroups.get(name);
        } else {
            group = new InternalSettingGroup();
            settingGroups.put(name, group);
        }
        return group;
    }

    public void fromJson(JsonObject jsonObject) {
        for(Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String group = entry.getKey();
            JsonElement jsonElement = entry.getValue();
            if(!jsonElement.isJsonObject()) continue;
            InternalSettingGroup settingGroup = (InternalSettingGroup) settingGroups.get(group);
            if(settingGroup != null) settingGroup.fromJson(jsonElement.getAsJsonObject());
        }
    }
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        for(Map.Entry<String, SettingGroup> entry : settingGroups.entrySet()) {
            if(((InternalSettingGroup) entry.getValue()).toJson().isEmpty()) continue;
            jsonObject.add(entry.getKey(), ((InternalSettingGroup) entry.getValue()).toJson());
        }
        return jsonObject;
    }
}