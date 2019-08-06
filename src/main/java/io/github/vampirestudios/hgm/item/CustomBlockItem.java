package io.github.vampirestudios.hgm.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;

public class CustomBlockItem extends BlockItem {

    public CustomBlockItem(Block blockIn, ItemGroup itemGroup) {
        super(blockIn, new Settings().group(itemGroup));
    }

    public CustomBlockItem(Block blockIn, Settings properties, ItemGroup itemGroup) {
        super(blockIn, properties.group(itemGroup));
    }

}
