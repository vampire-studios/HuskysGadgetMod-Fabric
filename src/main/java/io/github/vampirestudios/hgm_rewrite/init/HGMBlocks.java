package io.github.vampirestudios.hgm_rewrite.init;

import io.github.vampirestudios.hgm_rewrite.HuskysGadgetMod;
import io.github.vampirestudios.hgm_rewrite.block.LaptopBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.DyeColor;

public class HGMBlocks {

    public static final Block LAPTOP;

    static {
        LAPTOP = HuskysGadgetMod.REGISTRY_HELPER.registerBlock(new LaptopBlock(DyeColor.WHITE), "laptop", ItemGroup.DECORATIONS);
    }

}
