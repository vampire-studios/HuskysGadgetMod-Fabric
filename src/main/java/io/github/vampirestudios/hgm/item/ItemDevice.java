package io.github.vampirestudios.hgm.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;

public class ItemDevice extends CustomBlockItem {

    public ItemDevice(Block blockIn, ItemGroup itemGroup) {
        super(blockIn, new Settings().maxCount(1), itemGroup);
    }

    /*@Nullable
    @Override
    public CompoundTag getShareTag(ItemStack stack) {
        CompoundTag tag = new CompoundTag();
        if (stack.getTag() != null && stack.getTag().contains("display", Constants.NBT.TAG_COMPOUND)) {
            tag.put("display", Objects.requireNonNull(stack.getTag().get("display")));
        }
        return tag;
    }*/

}
