package io.github.vampirestudios.hgm.block;

import io.github.vampirestudios.hgm.utils.IBlockColorProvider;
import io.github.vampirestudios.hgm.utils.IItemColorProvider;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import org.apache.commons.lang3.text.WordUtils;

import java.util.List;

public interface IColoredBlock extends IBlockColorProvider, IItemColorProvider {

    static Formatting getFromColor(DyeColor color) {
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

    DyeColor getDyeColor();

    default void addInformation(List<Text> tooltip) {
        if (!Screen.hasShiftDown()) {
            tooltip.add(new LiteralText("Hold " + Formatting.BOLD + getFromColor(getDyeColor()) + "SHIFT " + Formatting.GRAY + "for more information"));
        } else {
            String colorName = getDyeColor().getName().replace("_", " ");
            colorName = WordUtils.capitalize(colorName);
            tooltip.add(new LiteralText("Color: " + Formatting.BOLD.toString() + getFromColor(getDyeColor()).toString() + colorName));
        }
    }

    @Override
    default BlockColorProvider getBlockColor() {
        return (state, worldIn, pos, tintIndex) -> DyeColor.values()[tintIndex].getId();
    }

    @Override
    default ItemColorProvider getItemColor() {
        return (stack, tintIndex) -> DyeColor.values()[tintIndex].getId();
    }

}
