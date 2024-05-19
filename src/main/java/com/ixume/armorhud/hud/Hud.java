package com.ixume.armorhud.hud;

import com.ixume.armorhud.BossEventFactory;
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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Hud {
    private final Player player;
    private final List<HudSlot> slots = new ArrayList<>();
    private HudLayout positions;
    private int cachedEquipment;
    private BossEvent event;

    public Hud(Player player, HudLayout positions) {
        this.player = player;
        this.positions = positions;
    }

    public void setPositions(HudLayout positions) {
        this.positions = positions;
    }

    public void init() {
        //generate icons
        slots.add(new HudSlot(player.getEquipment(), EquipmentSlot.HEAD, positions.positions()[0]));
        slots.add(new HudSlot(player.getEquipment(), EquipmentSlot.CHEST, positions.positions()[1]));
        slots.add(new HudSlot(player.getEquipment(), EquipmentSlot.LEGS, positions.positions()[2]));
        slots.add(new HudSlot(player.getEquipment(), EquipmentSlot.FEET, positions.positions()[3]));
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
                stackHash(player.getEquipment().getBoots());

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