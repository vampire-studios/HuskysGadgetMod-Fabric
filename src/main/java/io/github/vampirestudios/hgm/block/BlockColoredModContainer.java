package io.github.vampirestudios.hgm.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.world.BlockView;

import javax.annotation.Nullable;
import java.util.List;

public abstract class BlockColoredModContainer extends BlockWithEntity implements ColoredBlock {

    private DyeColor color;

    public BlockColoredModContainer(DyeColor color) {
        super(Block.Settings.of(Material.STONE));
        this.color = color;
    }

    @Override
    public DyeColor getDyeColor() {
        return color;
    }

    @Override
    public void buildTooltip(ItemStack itemStack_1, @Nullable BlockView blockView_1, List<Text> list_1, TooltipContext tooltipContext_1) {
        addInformation(list_1);
    }
}