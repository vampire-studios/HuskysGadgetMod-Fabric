package io.github.vampirestudios.hgm.api.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

public class ScreenUtils {

    public static Screen getCurrentScreen() {
        return MinecraftClient.getInstance().currentScreen;
    }

}
