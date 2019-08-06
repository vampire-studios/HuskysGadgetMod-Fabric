package io.github.vampirestudios.hgm.system.object;

import javax.annotation.Nullable;

/**
 * Author: MrCrayfish
 */
public interface AppEntry
{
    String getId();
    String getName();
    String getAuthor();
    String getDescription();
    String getVersion();
    String getIcon();
    String[] getScreenshots();
}