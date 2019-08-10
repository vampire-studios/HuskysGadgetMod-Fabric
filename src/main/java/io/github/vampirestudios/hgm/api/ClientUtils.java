//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.vampirestudios.hgm.api;

import io.github.vampirestudios.hgm.utils.ClientUtilsImpl;

import java.awt.*;

public interface ClientUtils {
    static ClientUtils getInstance() {
        return ClientUtilsImpl.getInstance();
    }

    static Point getMouseLocation() {
        return new Point((int)getInstance().getMouseX(), (int)getInstance().getMouseY());
    }

    double getMouseX();

    double getMouseY();
}
