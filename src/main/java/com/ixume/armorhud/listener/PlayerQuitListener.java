package com.ixume.armorhud.listener;

import com.ixume.armorhud.ArmorHud;
import com.ixume.armorhud.hud.HudsHolder;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    public void init(ArmorHud plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        HudsHolder.getInstance().removeHud(event.getPlayer().getUniqueId());
    }
}
