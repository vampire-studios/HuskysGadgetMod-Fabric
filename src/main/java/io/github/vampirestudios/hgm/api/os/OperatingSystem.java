package io.github.vampirestudios.hgm.api.os;

import io.github.vampirestudios.hgm.core.TaskBar;
import net.minecraft.util.Identifier;

public interface OperatingSystem {

    String name();

    String version();

    default TaskBar taskBar() {
        return null;
    }

    int ram();

    int storage();

    Identifier bootLogo();

}
