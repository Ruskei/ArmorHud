package com.ixume.armorhud.hud;

import org.joml.Vector2i;

//0 = head
//1 = chest
//2 = legs
//3 = boots
public record HudLayout(Vector2i[] positions, Alignment horizontalAlignment, Alignment verticalAlignment) {
}