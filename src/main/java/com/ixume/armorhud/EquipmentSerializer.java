package com.ixume.armorhud;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.inventory.EntityEquipment;

import javax.annotation.Nullable;
import java.awt.*;

public class EquipmentSerializer {
    public EquipmentSerializer() {
    }

    public String serialize(@Nullable
                            EntityEquipment equipment) {

        BaseComponent helmet;
        //set to right icon
        if (equipment == null || equipment.getHelmet() == null) {
            helmet = new TranslatableComponent(ArmorHud.PREFIX + ".HELM.AIR");
        } else {
            helmet = new TranslatableComponent(ArmorHud.PREFIX + "." + equipment.getHelmet().getType());
        }

        //set right font
        helmet.setFont(ArmorHud.PREFIX + ":general");
        //set position
        helmet.setColor(ChatColor.of(new Color(255, 16, 255)));
        TranslatableComponent xPos = new TranslatableComponent("space.-20");
        return ComponentSerializer.toString(xPos, helmet);
    }
}
