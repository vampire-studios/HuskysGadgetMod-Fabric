package io.github.vampirestudios.hgm.api.print;

import io.github.vampirestudios.hgm.init.HGMBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.LiteralText;

public interface IPrint {
    static NbtCompound writeToTag(IPrint print) {
        NbtCompound tag = new NbtCompound();
        tag.putString("type", PrintingManager.getPrintIdentifier(print));
        tag.put("data", print.toTag());
        return tag;
    }

    static IPrint loadFromTag(NbtCompound tag) {
        IPrint print = PrintingManager.getPrint(tag.getString("type"));
        if (print != null) {
            print.fromTag(tag.getCompound("data"));
            return print;
        }
        return null;
    }

    static ItemStack generateItem(IPrint print) {
        NbtCompound blockEntityTag = new NbtCompound();
        blockEntityTag.put("print", writeToTag(print));

        NbtCompound itemTag = new NbtCompound();
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
    NbtCompound toTag();

    void fromTag(NbtCompound tag);

    @Environment(EnvType.CLIENT)
    Class<? extends Renderer> getRenderer();

    interface Renderer {
        boolean render(NbtCompound data);
    }
}