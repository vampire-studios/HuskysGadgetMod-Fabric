package io.github.vampirestudios.hgm.api.app.component;

public enum ComponentAlignment {

    LEFT(0),
    RIGHT(1),
    CENTER(2);

    public int id;

    ComponentAlignment(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
