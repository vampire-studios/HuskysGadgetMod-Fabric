package io.github.vampirestudios.hgm.utils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.color.item.ItemColorProvider;

public interface IItemColorProvider {

    @Environment(EnvType.CLIENT)
    ItemColorProvider getItemColor();

}
