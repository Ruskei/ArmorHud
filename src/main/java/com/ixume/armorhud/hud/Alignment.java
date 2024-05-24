package com.ixume.armorhud.hud;

public enum Alignment {
    TOP(2),
    CENTER(1),
    BOTTOM(0);

    private final int value;

    Alignment(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
