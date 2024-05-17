package com.ixume.armorhud;

import com.ixume.armorhud.listener.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ArmorHud extends JavaPlugin {
    public static String PREFIX = "armor_hud";

    @Override
    public void onEnable() {
        HudsHolder.start(this);
        new PlayerJoinListener().init(this);
    }
}
