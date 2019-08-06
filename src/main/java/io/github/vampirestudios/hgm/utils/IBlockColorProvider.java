package io.github.vampirestudios.hgm.utils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.color.block.BlockColorProvider;

public interface IBlockColorProvider extends IItemColorProvider {

    @Environment(EnvType.CLIENT)
    BlockColorProvider getBlockColor();

}
