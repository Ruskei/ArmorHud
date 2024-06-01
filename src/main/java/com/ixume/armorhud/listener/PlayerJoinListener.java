package com.ixume.armorhud.listener;

import com.ixume.armorhud.*;
import com.ixume.armorhud.hud.Hud;
import com.ixume.armorhud.hud.HudLayoutFactory;
import com.ixume.armorhud.hud.HudsHolder;
import org.bukkit.Bukkit;
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
        HudsHolder.getInstance().addHud(event.getPlayer().getUniqueId(), new Hud(event.getPlayer(), HudLayoutFactory.getInstance().getLayout("SCREEN_LEFT")));
    }
}
