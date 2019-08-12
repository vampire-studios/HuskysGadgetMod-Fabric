//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.vampirestudios.hgm.utils;

import javax.annotation.Nullable;
import java.io.File;

public interface IFileBrowserIconProvider {
    GuiIcon getIconRoot();

    GuiIcon getIconUp();

    GuiIcon getIconCreateDirectory();

    GuiIcon getIconSearch();

    GuiIcon getIconDirectory();

    @Nullable
    GuiIcon getIconForFile(File var1);
}
