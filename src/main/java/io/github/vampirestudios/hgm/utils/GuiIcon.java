//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.vampirestudios.hgm.utils;

import net.minecraft.util.Identifier;

public interface GuiIcon {
    int getWidth();

    int getHeight();

    int getU();

    int getV();

    void renderAt(int var1, int var2, float var3, boolean var4, boolean var5);

    Identifier getTexture();
}
