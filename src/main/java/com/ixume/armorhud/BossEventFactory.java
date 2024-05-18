package com.ixume.armorhud;

import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent;

import java.util.UUID;

public class BossEventFactory {
    private static BossEventFactory instance;

    private BossEventFactory() {}

    public static BossEventFactory getInstance() {
        if (instance == null) {
            instance = new BossEventFactory();
        }

        return instance;
    }

    public BossEvent createBossEvent(Component title, BossEvent.BossBarColor color) {
        return new BossEvent(UUID.randomUUID(), title, color, BossEvent.BossBarOverlay.PROGRESS) {};
    }
}
