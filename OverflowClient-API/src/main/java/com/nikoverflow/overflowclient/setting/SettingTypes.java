package com.nikoverflow.overflowclient.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class SettingTypes {

    public static final SettingType<String> STRING = new SettingType<>() {
        @Override
        public JsonElement serialize(String value) {
            return new JsonPrimitive(value);
        }
        @Override
        public String deserialize(JsonElement jsonElement) {
            return jsonElement.getAsString();
        }
    };
    public static final SettingType<Integer> INTEGER = new SettingType<>() {
        @Override
        public JsonElement serialize(Integer value) {
            return new JsonPrimitive(value);
        }
        @Override
        public Integer deserialize(JsonElement jsonElement) {
            return jsonElement.getAsInt();
        }
    };
    public static final SettingType<Boolean> BOOLEAN = new SettingType<>() {
        @Override
        public JsonElement serialize(Boolean value) {
            return new JsonPrimitive(value);
        }
        @Override
        public Boolean deserialize(JsonElement jsonElement) {
            return jsonElement.getAsBoolean();
        }
    };
    public static <T extends Enum<T>> SettingType<T> ENUM(Class<T> enumClass) {
        return new SettingType<>() {
            @Override
            public JsonElement serialize(T value) {
                return new JsonPrimitive(value.name());
            }
            @Override
            public T deserialize(JsonElement jsonElement) {
                return Enum.valueOf(enumClass, jsonElement.getAsString());
            }
        };
    };
}