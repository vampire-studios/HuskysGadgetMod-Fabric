package io.github.vampirestudios.hgm.core.operation_systems;

import io.github.vampirestudios.hgm.api.os.OperatingSystem;
import io.github.vampirestudios.hgm.core.operation_systems.core_os.OSInfo;
import net.minecraft.util.Identifier;

public class PixelOSConsole implements OperatingSystem {

    @Override
    public String name() {
        return "PixelOS Console";
    }

    @Override
    public String version() {
        return "0.0.1";
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