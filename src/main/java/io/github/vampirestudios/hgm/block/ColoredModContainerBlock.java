package io.github.vampirestudios.hgm.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public abstract class ColoredModContainerBlock extends BlockWithEntity implements IColoredBlock, Waterloggable {

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

    @Environment(EnvType.CLIENT)
    public float getAmbientOcclusionLightLevel(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        return 1.0F;
    }

    public boolean canSuffocate(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        return false;
    }

    public boolean allowsSpawning(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, EntityType<?> entityType_1) {
        return false;
    }

}