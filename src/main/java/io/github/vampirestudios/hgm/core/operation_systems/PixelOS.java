package io.github.vampirestudios.hgm.core.operation_systems;

import io.github.vampirestudios.hgm.api.os.OperatingSystem;
import io.github.vampirestudios.hgm.core.TaskBar;
import io.github.vampirestudios.hgm.core.operation_systems.core_os.OSInfo;
import net.minecraft.util.Identifier;

public class PixelOS implements OperatingSystem {

    private TaskBar taskBar;

    public PixelOS(TaskBar taskBar) {
        this.taskBar = taskBar;
    }

    @Override
    public String name() {
        return "PixelOS";
    }

    @Override
    public String version() {
        return "0.0.1";
    }

    @Override
    public TaskBar taskBar() {
        return taskBar;
    }

    @Override
    public int ram() {
        return OSInfo.BASIC_RAM;
    }

    @Override
    public int storage() {
        return OSInfo.BASIC_STORAGE;
    }

    @Override
    public Identifier bootLogo() {
        return new Identifier("hgm:textures/gui/boot/pixel_os.png");
    }

}