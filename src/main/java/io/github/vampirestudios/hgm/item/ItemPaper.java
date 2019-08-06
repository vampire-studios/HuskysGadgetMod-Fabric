package io.github.vampirestudios.hgm.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nullable;

public class ItemPaper extends BaseItem {
    public ItemPaper() {
        super("paper", new Properties().maxStackSize(1));
    }

    @Nullable
    @Override
    public CompoundTag getShareTag(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null) {
            CompoundTag copy = tag.copy();
            copy.remove("BlockEntityTag");
            return copy;
        }
        return null;
    }
}
