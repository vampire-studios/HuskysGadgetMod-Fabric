//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.vampirestudios.hgm.utils;

import net.minecraft.util.StringIdentifiable;

public enum GuiAlignment implements StringIdentifiable {
    TOP_LEFT("top_left"),
    TOP_RIGHT("top_right"),
    BOTTOM_LEFT("bottom_left"),
    BOTTOM_RIGHT("bottom_right"),
    CENTER("center");

    private final String name;

    GuiAlignment(String name) {
        this.name = name;
    }

    public String asString() {
        return this.name;
    }

}
