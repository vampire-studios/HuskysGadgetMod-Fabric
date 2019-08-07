package io.github.vampirestudios.hgm.item;

public class ItemPaper extends BaseItem {
    public ItemPaper() {
        super(new Settings().maxCount(1));
    }

    /*@Nullable
    @Override
    public CompoundTag getShareTag(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null) {
            CompoundTag copy = tag.copy();
            copy.remove("BlockEntityTag");
            return copy;
        }
        return null;
    }*/
}
