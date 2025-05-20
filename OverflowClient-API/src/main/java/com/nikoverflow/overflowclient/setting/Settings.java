package com.nikoverflow.overflowclient.setting;

public interface Settings {

    SettingGroup defaultGroup();
    SettingGroup group(String name);
}