package com.ixume.armorhud;

import net.md_5.bungee.api.chat.TranslatableComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.world.BossEvent;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.joml.Vector2i;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Hud {
    private final Player player;
    private final List<HudSlot> slots = new ArrayList<>();
    private int cachedEquipment;
    private BossEvent event;

    public Hud(Player player) {
        this.player = player;
    }

    public void init() {
        //generate icons
        slots.add(new HudSlot(player.getEquipment(), EquipmentSlot.HEAD, new Vector2i(-156, 235)));
        slots.add(new HudSlot(player.getEquipment(), EquipmentSlot.CHEST, new Vector2i(-156 + 20, 235)));
        slots.add(new HudSlot(player.getEquipment(), EquipmentSlot.LEGS, new Vector2i(-156 + 20 * 2, 235)));
        slots.add(new HudSlot(player.getEquipment(), EquipmentSlot.FEET, new Vector2i(-156 + 20 * 3, 235)));
        slots.add(new HudSlot(player.getEquipment(), EquipmentSlot.HAND, new Vector2i(130 - 20, 235)));
        slots.add(new HudSlot(player.getEquipment(), EquipmentSlot.OFF_HAND, new Vector2i(130, 235)));
        cachedEquipment = hashEquipment();
        //create boss event
        event = BossEventFactory.getInstance().createBossEvent(Component.Serializer.fromJson(serialize()), BossEvent.BossBarColor.YELLOW);
        //send to player
        ClientboundBossEventPacket packet = ClientboundBossEventPacket.createAddPacket(event);
        ((CraftPlayer) player).getHandle().connection.send(packet);
    }

    public void tick() {
        //check if already cached
        if (hashEquipment() == cachedEquipment) {
            return;
        }

        cachedEquipment = hashEquipment();
        //update hud slots
        slots.forEach(slot -> slot.setEquipment(player.getEquipment()));

        event.setName(Component.Serializer.fromJson(serialize()));
        //send update packet
        ClientboundBossEventPacket packet = ClientboundBossEventPacket.createUpdateNamePacket(event);
        ((CraftPlayer) player).getHandle().connection.send(packet);
    }

    private String serialize() {
        List<TranslatableComponent> components = new ArrayList<>();
        slots.forEach(slot -> components.addAll(slot.getSerializer().serializeIcon()));
        components.add(new TranslatableComponent("newlayer"));
        slots.forEach(slot -> {
            List<TranslatableComponent> result = slot.getSerializer().serializeDurability();
            if (!result.isEmpty()) components.addAll(result);
        });

        System.out.println(ComponentSerializer.toString(components));
        return ComponentSerializer.toString(components);
    }

    private int hashEquipment() {
        if (player.getEquipment() == null) {
            return 0;
        }

        String toHash =
                stackHash(player.getEquipment().getHelmet()) +
                stackHash(player.getEquipment().getChestplate()) +
                stackHash(player.getEquipment().getLeggings()) +
                stackHash(player.getEquipment().getBoots()) +
                stackHash(player.getEquipment().getItemInMainHand()) +
                stackHash(player.getEquipment().getItemInOffHand());

        return toHash.hashCode();
    }

    private String stackHash(@Nullable ItemStack stack) {
        if (stack == null) {
            return "0";
        } else {
            return stack.getType().toString() + (stack.hasItemMeta() ? ((Damageable) stack.getItemMeta()).getDamage() : "");
        }
    }
}

//[{"translate":"space.-18"},{"translate":"offset.-156","with":[{"color":"#ffebff","font":"armor_hud:general","translate":"armor_hud.NETHERITE_HELMET"}]},{"translate":"space.-18"},{"translate":"offset.-136","with":[{"color":"#ffebff","font":"armor_hud:general","translate":"armor_hud.GOLDEN_CHESTPLATE"}]},{"translate":"space.-18"},{"translate":"offset.-116","with":[{"color":"#ffebff","font":"armor_hud:general","translate":"armor_hud.LEGS.AIR"}]},{"translate":"space.-18"},{"translate":"offset.-96","with":[{"color":"#ffebff","font":"armor_hud:general","translate":"armor_hud.FEET.AIR"}]},{"translate":"space.-18"},{"translate":"offset.110","with":[{"color":"#ffebff","font":"armor_hud:general","translate":"armor_hud.GOLDEN_CHESTPLATE"}]},{"translate":"space.-18"},{"translate":"offset.130","with":[{"color":"#ffebff","font":"armor_hud:general","translate":"armor_hud.NETHERITE_SWORD"}]},{"translate":"newlayer"},{"translate":"space.-18"},{"translate":"offset.-156","with":[{"color":"#3beb43","font":"armor_hud:general","translate":"armor_hud.DURABILITY"}]},{"translate":"space.-18"},{"translate":"offset.-136","with":[{"color":"#3beb1b","font":"armor_hud:general","translate":"armor_hud.DURABILITY"}]},{"translate":"space.-18"},{"translate":"offset.110","with":[{"color":"#3beb1b","font":"armor_hud:general","translate":"armor_hud.DURABILITY"}]},{"translate":"space.-18"},{"translate":"offset.130","with":[{"color":"#3bebd9","font":"armor_hud:general","translate":"armor_hud.DURABILITY"}]}]