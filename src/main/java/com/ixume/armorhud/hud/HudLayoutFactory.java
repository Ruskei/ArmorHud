package com.ixume.armorhud.hud;

import org.joml.Vector2i;

import java.util.HashMap;
import java.util.Map;

public class HudLayoutFactory {
    private static HudLayoutFactory instance;
    private final Map<String, HudLayout> layouts;

    private HudLayoutFactory() {
        layouts = new HashMap<>();
        layouts.put("HOTBAR", new HudLayout(new Vector2i[]{
                new Vector2i(-156, 225),
                new Vector2i(-156 + 20, 225),
                new Vector2i(-156 + 20 * 2, 225),
                new Vector2i(-156 + 20 * 3, 225)}, Alignment.MAX, Alignment.MAX));
    }

    public static HudLayoutFactory getInstance() {
        if (instance == null) {
            instance = new HudLayoutFactory();
        }

        return instance;
    }

    public HudLayout getLayout(String name) {
        return layouts.get(name);
    }
}
