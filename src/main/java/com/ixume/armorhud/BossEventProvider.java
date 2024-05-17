package com.ixume.armorhud;

import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent;

import java.util.UUID;

public class BossEventProvider {
    public static BossEvent createBossEvent(Component title, BossEvent.BossBarColor color) {
        return new BossEvent(UUID.randomUUID(), title, color, BossEvent.BossBarOverlay.PROGRESS) {
            @Override
            public UUID getId() {
                return super.getId();
            }
        };
    }
}
