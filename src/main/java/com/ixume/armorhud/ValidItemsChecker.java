package com.ixume.armorhud;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public final class ValidItemsChecker {
    private static ValidItemsChecker instance;

    private final List<Material> validMaterials;

    private ValidItemsChecker() {
        validMaterials = new ArrayList<>();
        //NEED TO ADD STONE TOOLS
        validMaterials.add(Material.DIAMOND_HELMET);
        validMaterials.add(Material.DIAMOND_CHESTPLATE);
        validMaterials.add(Material.DIAMOND_LEGGINGS);
        validMaterials.add(Material.DIAMOND_BOOTS);
        validMaterials.add(Material.DIAMOND_SWORD);
        validMaterials.add(Material.DIAMOND_PICKAXE);
        validMaterials.add(Material.DIAMOND_AXE);
        validMaterials.add(Material.DIAMOND_HOE);
        validMaterials.add(Material.DIAMOND_SHOVEL);

        validMaterials.add(Material.LEATHER_HELMET);
        validMaterials.add(Material.LEATHER_CHESTPLATE);
        validMaterials.add(Material.LEATHER_LEGGINGS);
        validMaterials.add(Material.LEATHER_BOOTS);
        validMaterials.add(Material.WOODEN_SWORD);
        validMaterials.add(Material.WOODEN_PICKAXE);
        validMaterials.add(Material.WOODEN_AXE);
        validMaterials.add(Material.WOODEN_HOE);
        validMaterials.add(Material.WOODEN_SHOVEL);

        validMaterials.add(Material.IRON_HELMET);
        validMaterials.add(Material.IRON_CHESTPLATE);
        validMaterials.add(Material.IRON_LEGGINGS);
        validMaterials.add(Material.IRON_BOOTS);
        validMaterials.add(Material.IRON_SWORD);
        validMaterials.add(Material.IRON_PICKAXE);
        validMaterials.add(Material.IRON_AXE);
        validMaterials.add(Material.IRON_HOE);
        validMaterials.add(Material.IRON_SHOVEL);

        validMaterials.add(Material.GOLDEN_HELMET);
        validMaterials.add(Material.GOLDEN_CHESTPLATE);
        validMaterials.add(Material.GOLDEN_LEGGINGS);
        validMaterials.add(Material.GOLDEN_BOOTS);
        validMaterials.add(Material.GOLDEN_SWORD);
        validMaterials.add(Material.GOLDEN_PICKAXE);
        validMaterials.add(Material.GOLDEN_AXE);
        validMaterials.add(Material.GOLDEN_HOE);
        validMaterials.add(Material.GOLDEN_SHOVEL);

        validMaterials.add(Material.NETHERITE_HELMET);
        validMaterials.add(Material.NETHERITE_CHESTPLATE);
        validMaterials.add(Material.NETHERITE_LEGGINGS);
        validMaterials.add(Material.NETHERITE_BOOTS);
        validMaterials.add(Material.NETHERITE_SWORD);
        validMaterials.add(Material.NETHERITE_PICKAXE);
        validMaterials.add(Material.NETHERITE_AXE);
        validMaterials.add(Material.NETHERITE_HOE);
        validMaterials.add(Material.NETHERITE_SHOVEL);
    }

    public static ValidItemsChecker getInstance() {
        if (instance == null) {
            instance = new ValidItemsChecker();
        }

        return instance;
    }

    public boolean contains(Material material) {
        return validMaterials.contains(material);
    }
}
