package com.ixume.armorhud;

import com.ixume.armorhud.hud.HudSlot;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

import java.awt.*;
import java.util.ArrayList;

public class EquipmentSerializer {
    private final static int DURABILITY_MARKER = 59;

    private final HudSlot slot;

    public EquipmentSerializer(HudSlot slot) {
        this.slot = slot;
    }

    public java.util.List<TranslatableComponent> serializeIcon() {
        TranslatableComponent itemComponent = translationOf(slot.getMaterial(), slot.getSlot());
        //set position
        itemComponent.setColor(ChatColor.of(new Color(255, slot.getPosition().y, 255)));
        return offset(itemComponent, slot.getPosition().x);
    }

    public java.util.List<TranslatableComponent> serializeDurability() {
        if (slot.getDurability() == -1 || slot.getDurability() == 255) return new ArrayList<>();
        TranslatableComponent durabilityComponent = new TranslatableComponent(ArmorHud.PREFIX + ".DURABILITY");
        durabilityComponent.setFont(ArmorHud.PREFIX + ":general");
        durabilityComponent.setColor(ChatColor.of(new Color(DURABILITY_MARKER, slot.getPosition().y, slot.getDurability())));
        return offset(durabilityComponent, slot.getPosition().x);
    }

    private java.util.List<TranslatableComponent> offset(TranslatableComponent component, int offset) {
        java.util.List<TranslatableComponent> toReturn = new ArrayList<>();
        toReturn.add(new TranslatableComponent("space.-19"));
        toReturn.add(new TranslatableComponent("offset." + offset, component));
        return toReturn;
    }

    private TranslatableComponent translationOf(Material material, EquipmentSlot slot) {
        TranslatableComponent component = new TranslatableComponent(ArmorHud.PREFIX + (!ValidItemsChecker.getInstance().contains(material) ? (switch(slot) {
            case HEAD -> ".HELM.AIR";
            case CHEST -> ".CHEST.AIR";
            case LEGS -> ".LEGS.AIR";
            case FEET -> ".FEET.AIR";
            case HAND -> ".MAIN.AIR";
            case OFF_HAND -> ".OFF.AIR";
        }) : ("." + material)));

        component.setFont(ArmorHud.PREFIX + ":general");
        return component;
    }
}
