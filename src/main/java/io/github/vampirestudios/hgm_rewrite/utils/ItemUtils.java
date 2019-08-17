package io.github.vampirestudios.hgm_rewrite.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import org.apache.commons.lang3.text.WordUtils;

import java.lang.reflect.Field;
import java.util.List;

public class ItemUtils {

    public static void addInformation(ItemStack stack, List<String> tooltip) {
        DyeColor color = DyeColor.byId(stack.getTag().getInt("color"));
        Formatting tf = Formatting.WHITE;
        try {
            Field f = DyeColor.class.getDeclaredField("chatColor");
            f.setAccessible(true);
            tf = (Formatting) f.get(color == DyeColor.MAGENTA ? DyeColor.PINK : color);
        } catch (Exception ignored) {
        }
        String colorName = color.getName().replace("_", " ");
        colorName = WordUtils.capitalize(colorName);
        tooltip.add("Color: " + Formatting.BOLD + tf.toString() + colorName);
    }

}
