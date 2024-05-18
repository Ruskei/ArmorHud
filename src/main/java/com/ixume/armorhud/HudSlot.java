package com.ixume.armorhud;

import org.bukkit.Material;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.joml.Vector2i;

import javax.annotation.Nullable;

public class HudSlot {
    private EquipmentSerializer serializer;
    private Material material;
    private final EquipmentSlot slot;
    private Vector2i position; //centered around center of screen for x, 0-255 for y (0 at the top, 255 at bottom)
    private int durability; //0-255

    public HudSlot(@Nullable EntityEquipment equipment, EquipmentSlot slot, Vector2i position) {
        serializer = new EquipmentSerializer(this);
        this.slot = slot;
        this.position = position;
        setEquipment(equipment);
    }

    public void setEquipment(EntityEquipment equipment) {
        if (equipment == null) {
            material = Material.AIR;
            durability = -1;
        } else {
            ItemStack relevantItem = relevantItem(equipment, slot);
            if (relevantItem == null) {
                material = Material.AIR;
                durability = -1;
            } else {
                material = relevantItem.getType();

                if (!relevantItem.hasItemMeta()) {
                    durability = -1;
                } else {
                    if (relevantItem.getItemMeta() instanceof Damageable) {
                        durability = (int) (255d - (255d * (((double) ((Damageable) relevantItem.getItemMeta()).getDamage()) / ((double) relevantItem.getType().getMaxDurability()))));
                    } else {
                        durability = -1;
                    }
                }
            }
        }
    }

    public EquipmentSerializer getSerializer() {
        return serializer;
    }

    public Material getMaterial() {
        return material;
    }

    public EquipmentSlot getSlot() {
        return slot;
    }

    public Vector2i getPosition() {
        return position;
    }

    public int getDurability() {
        return durability;
    }

    @Nullable
    private ItemStack relevantItem(EntityEquipment equipment, EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> equipment.getHelmet();
            case CHEST -> equipment.getChestplate();
            case LEGS -> equipment.getLeggings();
            case FEET -> equipment.getBoots();
            case HAND -> equipment.getItemInMainHand();
            case OFF_HAND -> equipment.getItemInOffHand();
        };
    }
}
