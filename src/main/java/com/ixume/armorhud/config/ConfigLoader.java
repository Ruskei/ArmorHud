package com.ixume.armorhud.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ixume.armorhud.ArmorHud;
import com.ixume.armorhud.hud.Alignment;
import com.ixume.armorhud.hud.HudLayoutBuilder;
import org.joml.Vector2i;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigLoader {
    private static ConfigLoader instance;

    private ConfigLoader() {}

    public static ConfigLoader getInstance() {
        if (instance == null) instance = new ConfigLoader();
        return instance;
    }

    public void init() {
        File dataFolder = ArmorHud.getInstance().getDataFolder();
        dataFolder.mkdirs();
        JsonObject obj;
        File file = new File(dataFolder.getAbsolutePath() + "/huds.json");
        try {
            if (!file.exists()) {
                InputStream stream = ArmorHud.getInstance().getResource("default_huds.json");
                Files.write(file.toPath(), stream.readAllBytes());
            }

            obj = JsonParser.parseReader(new FileReader(file)).getAsJsonObject();
            JsonArray huds = obj.getAsJsonArray("huds");
            for (JsonElement element : huds) {
                JsonObject object = element.getAsJsonObject();
                String name = object.getAsJsonPrimitive("name").getAsString();
                Vector2i helm = asVector(object.getAsJsonObject("head"));
                Vector2i chest = asVector(object.getAsJsonObject("chest"));
                Vector2i legs = asVector(object.getAsJsonObject("legs"));
                Vector2i feet = asVector(object.getAsJsonObject("feet"));
                Alignment horizontalAlignment = Alignment.valueOf(object.getAsJsonPrimitive("horizontal").getAsString());
                Alignment verticalAlignment = Alignment.valueOf(object.getAsJsonPrimitive("vertical").getAsString());
                HudLayoutBuilder.getInstance().registerLayout(name, new Vector2i[]{helm, chest, legs, feet}, horizontalAlignment, verticalAlignment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Vector2i asVector(JsonObject object) {
        int x = object.getAsJsonPrimitive("x").getAsInt();
        int y = object.getAsJsonPrimitive("y").getAsInt();
        return new Vector2i(x, y);
    }
}
