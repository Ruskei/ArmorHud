package com.ixume.armorhud;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HudsHolder {
    private static HudsHolder instance = null;

    private ConcurrentHashMap<UUID, Hud> huds = new ConcurrentHashMap<>();
    private BukkitTask task;

    private HudsHolder() {}

    public static HudsHolder getInstance() {
        if (instance == null) {
            instance = new HudsHolder();
        }

        return instance;
    }

    public void addHud(UUID uuid, Hud hud) {
        hud.init();
        huds.put(uuid, hud);
    }

    public void removeHud(UUID id) {
        huds.remove(id);
    }

    public void start(ArmorHud plugin) {
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> huds.forEach((uuid, hud) -> hud.tick()), 1, 2);
    }

    public void end() {
        task.cancel();
    }
}
