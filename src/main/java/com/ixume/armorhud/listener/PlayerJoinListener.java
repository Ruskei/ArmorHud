package com.ixume.armorhud.listener;

import com.ixume.armorhud.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    public void init(ArmorHud plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        //read player equipment
        HudsHolder.getInstance().addHud(event.getPlayer().getUniqueId(), new Hud(new EquipmentSerializer(), event.getPlayer()));
    }
}
