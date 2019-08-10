//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.vampirestudios.hgm.utils;

import io.github.vampirestudios.hgm.api.ClientUtils;
import net.minecraft.client.MinecraftClient;

public class ClientUtilsImpl implements ClientUtils {
    private static ClientUtilsImpl instance;
    private MinecraftClient client;

    public ClientUtilsImpl() {
        instance = this;
        this.client = MinecraftClient.getInstance();
    }

    /** @deprecated */
    @Deprecated
    public static ClientUtilsImpl getInstance() {
        return instance;
    }

    public double getMouseX() {
        return this.client.mouse.getX() * (double)this.client.window.getScaledWidth() / (double)this.client.window.getWidth();
    }

    public double getMouseY() {
        return this.client.mouse.getY() * (double)this.client.window.getScaledWidth() / (double)this.client.window.getWidth();
    }
}
