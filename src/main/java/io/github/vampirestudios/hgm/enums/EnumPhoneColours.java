package io.github.vampirestudios.hgm.enums;

import net.minecraft.util.StringIdentifiable;

public enum EnumPhoneColours implements StringIdentifiable {
    WHITE("white", 0),
    SILVER("silver", 1),
    BLACK("black", 2);

    int ID;
    String name;

    EnumPhoneColours(String name, int ID) {
        this.ID = ID;
        this.name = name;
    }

    @Override
    public String asString() {
        return name;
    }

    public int getID() {
        return ID;
    }
}
