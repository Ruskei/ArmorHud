package com.ixume.armorhud;

import com.ixume.armorhud.config.ConfigLoader;
import com.ixume.armorhud.hud.HudsHolder;
import com.ixume.armorhud.listener.PlayerJoinListener;
import com.ixume.armorhud.listener.PlayerQuitListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ArmorHud extends JavaPlugin {
    private static ArmorHud instance;

    public static ArmorHud getInstance() {
        return instance;
    }

    public final static String PREFIX = "armor_hud";

    @Override
    public void onEnable() {
        instance = this;
        ConfigLoader.getInstance().init();
        HudsHolder.getInstance().start(this);
        new PlayerJoinListener().init(this);
        new PlayerQuitListener().init(this);
    }

    @Override
    public void onDisable() {
        HudsHolder.getInstance().end();
    }
}
