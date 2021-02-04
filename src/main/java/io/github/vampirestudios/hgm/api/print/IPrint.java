package io.github.vampirestudios.hgm.api.print;

import io.github.vampirestudios.hgm.init.HGMBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;

public interface IPrint {
    static CompoundTag writeToTag(IPrint print) {
        CompoundTag tag = new CompoundTag();
        tag.putString("type", PrintingManager.getPrintIdentifier(print));
        tag.put("data", print.toTag());
        return tag;
    }

    static IPrint loadFromTag(CompoundTag tag) {
        IPrint print = PrintingManager.getPrint(tag.getString("type"));
        if (print != null) {
            print.fromTag(tag.getCompound("data"));
            return print;
        }
        return null;
    }

    static ItemStack generateItem(IPrint print) {
        CompoundTag blockEntityTag = new CompoundTag();
        blockEntityTag.put("print", writeToTag(print));

        CompoundTag itemTag = new CompoundTag();
        itemTag.put("BlockEntityTag", blockEntityTag);

        ItemStack stack = new ItemStack(HGMBlocks.PAPER);
        stack.setTag(itemTag);

        if (print.getName() != null && !print.getName().isEmpty()) {
            stack.setCustomName(new LiteralText(print.getName()));
        }
        return stack;
    }

    String getName();

    /**
     * Gets the speed of the print. The higher the value, the longer it will take to print.
     *
     * @return the speed of this print
     */
    int speed();

    /**
     * Gets whether or not this print requires coloured ink.
     *
     * @return if print requires ink
     */
    boolean requiresColor();

    /**
     * Converts print into an NBT tag compound. Used for the renderer.
     *
     * @return nbt form of print
     */
    CompoundTag toTag();

    void fromTag(CompoundTag tag);

    @Environment(EnvType.CLIENT)
    Class<? extends Renderer> getRenderer();

    interface Renderer {
        boolean render(CompoundTag data);
    }
}