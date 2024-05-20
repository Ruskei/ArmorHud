package com.ixume.armorhud.hud;

public enum Alignment {
    MIN(0),
    CENTER(1),
    MAX(2);

    private final int value;

    Alignment(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
