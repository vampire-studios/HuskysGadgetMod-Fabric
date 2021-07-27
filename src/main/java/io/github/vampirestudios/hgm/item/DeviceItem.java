package io.github.vampirestudios.hgm.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;

public class DeviceItem extends CustomBlockItem {

    public DeviceItem(Block blockIn, ItemGroup itemGroup) {
        super(blockIn, new Settings().maxCount(1), itemGroup);
    }

    /*@Nullable
    @Override
    public NbtCompound getShareTag(ItemStack stack) {
        NbtCompound tag = new NbtCompound();
        if (stack.getTag() != null && stack.getTag().contains("display", Constants.NBT.TAG_COMPOUND)) {
            tag.put("display", Objects.requireNonNull(stack.getTag().get("display")));
        }
        return tag;
    }*/

}
