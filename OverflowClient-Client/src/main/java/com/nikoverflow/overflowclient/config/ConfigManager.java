package com.nikoverflow.overflowclient.config;

import com.google.gson.*;

import java.io.*;

public class ConfigManager {

    private final File file;
    private final Gson gson;
    private JsonObject config;

    public ConfigManager(File file) {
        this.file = file;
        gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            JsonElement jsonElement = JsonParser.parseReader(new FileReader(file));
            config = jsonElement.getAsJsonObject();
        } catch (FileNotFoundException | JsonSyntaxException e) {
            config = new JsonObject();
        }
    }

    public void set(String key, JsonElement value) {
        if(key.startsWith(".") || key.endsWith(".")) throw new IllegalArgumentException("Key cannot start or end with '.'!");
        String[] parts = key.split("\\.");
        JsonObject current = config;
        for(int i = 0; i < parts.length - 1; i++) {
            if(!current.has(parts[i]) || !current.get(parts[i]).isJsonObject()) current.add(parts[i], new JsonObject());
            current = current.getAsJsonObject(parts[i]);
        }
        current.add(parts[parts.length - 1], value);
    }
    public JsonElement get(String key) {
        if(key.startsWith(".") || key.endsWith(".")) throw new IllegalArgumentException("Key cannot start or end with '.'!");
        String[] parts = key.split("\\.");
        JsonObject current = config;
        for(int i = 0; i < parts.length - 1; i++) {
            if(!current.has(parts[i]) || !current.get(parts[i]).isJsonObject()) return new JsonObject();
            current = current.getAsJsonObject(parts[i]);
        }
        return current.get(parts[parts.length - 1]);
    }

    public void save() throws IOException {
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(String.valueOf(gson.toJson(config)));
        fileWriter.close();
    }
}