package io.github.vampirestudios.hgm.api.app.interfaces;

import net.minecraft.util.Formatting;

public interface IHighlight {
    Formatting[] getKeywordFormatting(String text);
}
