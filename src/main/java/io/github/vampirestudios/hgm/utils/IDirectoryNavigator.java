//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.vampirestudios.hgm.utils;

import java.io.File;

public interface IDirectoryNavigator {
    File getCurrentDirectory();

    void switchToDirectory(File var1);

    void switchToParentDirectory();

    void switchToRootDirectory();
}
