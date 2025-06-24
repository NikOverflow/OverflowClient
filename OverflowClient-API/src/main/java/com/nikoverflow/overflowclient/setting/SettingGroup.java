package com.nikoverflow.overflowclient.setting;

public interface SettingGroup {

    <T> Setting<T> addSetting(String name, SettingType<T> type, T defaultValue);
    Setting<?> getSetting(String name);
}