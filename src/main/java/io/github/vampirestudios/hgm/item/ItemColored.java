package io.github.vampirestudios.hgm.item;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.utils.IItemColorProvider;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.apache.commons.lang3.text.WordUtils;

import javax.annotation.Nullable;
import java.util.List;

public class ItemColored extends Item implements IItemColorProvider {

    public DyeColor color;

    public ItemColored(DyeColor color) {
        super(new Settings().group(HuskysGadgetMod.DEVICE_ITEMS));
        this.color = color;
    }

    private static Formatting getFromColor(DyeColor color) {
        switch (color) {
            case ORANGE:
            case BROWN:
                return Formatting.GOLD;
            case MAGENTA:
            case PINK:
                return Formatting.LIGHT_PURPLE;
            case LIGHT_BLUE:
                return Formatting.BLUE;
            case YELLOW:
                return Formatting.YELLOW;
            case LIME:
                return Formatting.GREEN;
            case GRAY:
                return Formatting.DARK_GRAY;
            case LIGHT_GRAY:
                return Formatting.GRAY;
            case CYAN:
                return Formatting.DARK_AQUA;
            case PURPLE:
                return Formatting.DARK_PURPLE;
            case BLUE:
                return Formatting.DARK_BLUE;
            case GREEN:
                return Formatting.DARK_GREEN;
            case RED:
                return Formatting.DARK_RED;
            case BLACK:
                return Formatting.BLACK;
            default:
                return Formatting.WHITE;
        }
    }

    @Override
    public void appendTooltip(ItemStack itemStack_1, @Nullable World world_1, List<net.minecraft.text.Text> list_1, TooltipContext tooltipContext_1) {
        if (!Screen.hasShiftDown()) {
            list_1.add(new LiteralText("Hold " + Formatting.BOLD + getFromColor(color) + "SHIFT " + Formatting.GRAY + "for more information"));
        } else {
            String colorName = color.getName().replace("_", " ");
            colorName = WordUtils.capitalize(colorName);
            list_1.add(new LiteralText("Color: " + Formatting.BOLD.toString() + getFromColor(color).toString() + colorName));
        }
    }

    @Override
    public ItemColorProvider getItemColor() {
        return (stack, tintIndex) -> DyeColor.values()[tintIndex].getId();
    }

}