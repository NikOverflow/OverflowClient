package com.nikoverflow.overflowclient.setting;

import com.google.gson.JsonElement;

public class InternalSetting<T> extends Setting<T> {

    public InternalSetting(SettingType<T> type, T value) {
        super(type, value);
    }

    public void fromJson(JsonElement jsonElement) {
        value = type.deserialize(jsonElement);
    }
    public JsonElement toJson() {
        return type.serialize(value);
    }
}