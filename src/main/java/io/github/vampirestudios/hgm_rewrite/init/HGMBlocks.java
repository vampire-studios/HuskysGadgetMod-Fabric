package io.github.vampirestudios.hgm_rewrite.init;

import io.github.vampirestudios.hgm_rewrite.HuskysGadgetMod;
import io.github.vampirestudios.hgm_rewrite.block.LaptopBlock;
import io.github.vampirestudios.vampirelib.utils.registry.RegistryUtils;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class HGMBlocks {

    public static final Block LAPTOP;

    static {
        LAPTOP = RegistryUtils.register(new LaptopBlock(DyeColor.WHITE), new Identifier(HuskysGadgetMod.MOD_ID, "laptop"), ItemGroup.DECORATIONS);
    }

}
