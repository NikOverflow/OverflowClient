package com.nikoverflow.overflowclient.setting;

public interface SettingGroup {

    <T> Setting<T> add(String name, SettingType<T> type, T defaultValue);
    Setting<?> get(String name);
}