package com.nikoverflow.overflowclient.setting;

import com.google.gson.JsonElement;

public interface SettingType<T> {

    JsonElement serialize(T value);
    T deserialize(JsonElement jsonElement);
}