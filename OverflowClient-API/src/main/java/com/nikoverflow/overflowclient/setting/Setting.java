package com.nikoverflow.overflowclient.setting;

public class Setting<T> {

    protected final SettingType<T> type;
    protected T value;

    public Setting(SettingType<T> type, T defaultValue) {
        this.type = type;
        value = defaultValue;
    }

    public void setValue(T value) {
        this.value = value;
    }
    public T getValue() {
        return value;
    }
}