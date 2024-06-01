package com.ixume.armorhud.hud;

import org.joml.Vector2i;

import java.util.HashMap;
import java.util.Map;

public class HudLayoutBuilder {
    private static HudLayoutBuilder instance;
    private final Map<String, HudLayout> layouts;

    private HudLayoutBuilder() {
        layouts = new HashMap<>();
//        layouts.put("HOTBAR_LEFT", new HudLayout(new Vector2i[]{
//                new Vector2i(-160, 20),
//                new Vector2i(-160 + 20, 20),
//                new Vector2i(-160 + 20 * 2, 20),
//                new Vector2i(-160 + 20 * 3, 20)}, Alignment.CENTER, Alignment.BOTTOM));
//        layouts.put("HOTBAR_RIGHT", new HudLayout(new Vector2i[]{
//                new Vector2i(160 - 19 * 2, 20),
//                new Vector2i(160 + 20 - 19 * 2, 20),
//                new Vector2i(160 + 20 * 2 - 19 * 2, 20),
//                new Vector2i(160 + 20 * 3 - 19 * 2, 20)}, Alignment.CENTER, Alignment.BOTTOM));
//        layouts.put("SCREEN_LEFT", new HudLayout(new Vector2i[]{
//                new Vector2i(122, -20),
//                new Vector2i(142, 20),
//                new Vector2i(162, 20),
//                new Vector2i(182, 20)}, Alignment.BOTTOM, Alignment.CENTER));
    }

    public static HudLayoutBuilder getInstance() {
        if (instance == null) {
            instance = new HudLayoutBuilder();
        }

        return instance;
    }

    public void registerLayout(String name, Vector2i[] positions, Alignment horizontal, Alignment vertical) {
        layouts.put(name, new HudLayout(positions, horizontal, vertical));
    }

    public HudLayout getLayout(String name) {
        return layouts.get(name);
    }
}
