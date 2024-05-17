package com.ixume.armorhud;

import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.world.BossEvent;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Hud {
    private EquipmentSerializer equipmentSerializer;
    private final Player player;
    private int cachedEquipment;
    private BossEvent event;

    public Hud(EquipmentSerializer equipmentSerializer, Player player) {
        this.equipmentSerializer = equipmentSerializer;
        this.player = player;
    }

    public void init() {
        //generate text for hud
        String text = equipmentSerializer.serialize(player.getEquipment());
        cachedEquipment = hashEquipment();
        //create boss event
        event = BossEventProvider.createBossEvent(Component.Serializer.fromJson(text), BossEvent.BossBarColor.YELLOW);
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
        //get text for hud
        String text = equipmentSerializer.serialize(player.getEquipment());

        event.setName(Component.Serializer.fromJson(text));
        //send update packet
        ClientboundBossEventPacket packet = ClientboundBossEventPacket.createUpdateNamePacket(event);
        ((CraftPlayer) player).getHandle().connection.send(packet);
    }

    private int hashEquipment() {
        return player.getEquipment() == null ? 0 : player.getEquipment().hashCode();
    }
}
