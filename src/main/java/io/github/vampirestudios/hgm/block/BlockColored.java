package io.github.vampirestudios.hgm.block;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.world.BlockView;

import java.util.List;

public class BlockColored extends Block implements ColoredBlock {

    public final DyeColor color;

    public BlockColored(DyeColor color) {
        super(Settings.of(Material.STONE));
        this.color = color;
    }

    @Override
    public DyeColor getDyeColor() {
        return color;
    }

    @Override
    public void buildTooltip(ItemStack stack, BlockView blockView, List<Text> textList, TooltipContext tooltipContext) {
        this.addInformation(textList);
    }

}