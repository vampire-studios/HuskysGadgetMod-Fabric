package io.github.vampirestudios.hgm.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.util.DyeColor;

public abstract class ColoredModContainerBlock extends BlockWithEntity implements IColoredBlock {

    private DyeColor color;

    public ColoredModContainerBlock(DyeColor color) {
        super(Block.Settings.of(Material.STONE));
        this.color = color;
    }

    @Override
    public DyeColor getDyeColor() {
        return color;
    }

    /*@Override
    public void buildTooltip(ItemStack itemStack_1, @Nullable BlockView blockView_1, List<Text> list_1, TooltipContext tooltipContext_1) {
        addInformation(list_1);
    }*/

}