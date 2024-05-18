package com.ixume.armorhud;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.awt.*;

public class EquipmentSerializer {
    public EquipmentSerializer() {
    }

    public String serialize(@Nullable
                            EntityEquipment equipment) {

        TranslatableComponent helm = translationOf(equipment == null ? null : equipment.getHelmet(), SlotEnum.HELMET);
        TranslatableComponent chest = translationOf(equipment == null ? null : equipment.getChestplate(), SlotEnum.CHESTPLATE);
        TranslatableComponent legs = translationOf(equipment == null ? null : equipment.getLeggings(), SlotEnum.LEGGINGS);
        TranslatableComponent feet = translationOf(equipment == null ? null : equipment.getBoots(), SlotEnum.BOOTS);
        TranslatableComponent main = translationOf(equipment == null ? null : equipment.getItemInMainHand(), SlotEnum.MAIN);
        TranslatableComponent off = translationOf(equipment == null ? null : equipment.getItemInOffHand(), SlotEnum.OFF);
        //set position
        helm.setColor(ChatColor.of(new Color(255, 235, 255)));
        chest.setColor(ChatColor.of(new Color(255, 235, 255)));
        legs.setColor(ChatColor.of(new Color(255, 235, 255)));
        feet.setColor(ChatColor.of(new Color(255, 235, 255)));
        main.setColor(ChatColor.of(new Color(255, 235, 255)));
        off.setColor(ChatColor.of(new Color(255, 235, 255)));
        helm = new TranslatableComponent("offset.-116", helm);
        chest = new TranslatableComponent("offset.-114", chest);
        legs = new TranslatableComponent("offset.-112", legs);
        feet = new TranslatableComponent("offset.-110", feet);
        main = new TranslatableComponent("offset.78", main);
        off = new TranslatableComponent("offset.74", off);
        return ComponentSerializer.toString(helm, chest, legs, feet, main, off);
    }

    private TranslatableComponent translationOf(@Nullable ItemStack item, SlotEnum slot) {
        TranslatableComponent component = new TranslatableComponent(ArmorHud.PREFIX + ((item == null || SlotEnum.getEnum(item.getType()) == null) ? (switch(slot) {
            case HELMET -> ".HELM.AIR";
            case CHESTPLATE -> ".CHEST.AIR";
            case LEGGINGS -> ".LEGS.AIR";
            case BOOTS -> ".FEET.AIR";
            case MAIN -> ".MAIN.AIR";
            case OFF -> ".OFF.AIR";
        }) : ("." + item.getType())));

        component.setFont(ArmorHud.PREFIX + ":general");
        return component;
    }
}
