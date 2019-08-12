//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.vampirestudios.hgm.utils;

import javax.annotation.Nullable;
import java.io.File;

public interface IDirectoryCache {
    @Nullable
    File getCurrentDirectoryForContext(String var1);

    void setCurrentDirectoryForContext(String var1, File var2);
}
