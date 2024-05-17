package com.ixume.armorhud;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class HudsHolder {
    private static ConcurrentHashMap<UUID, Hud> huds = new ConcurrentHashMap<>();

    private static BukkitTask task;

    public static void addHud(UUID uuid, Hud hud) {
        hud.init();
        huds.put(uuid, hud);
    }

    public static void removeHud(Player player) {
        huds.remove(player);
    }

    public static void start(ArmorHud plugin) {
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                huds.forEach((uuid, hud) -> hud.tick());
            }
        }, 1, 2);
    }
}
