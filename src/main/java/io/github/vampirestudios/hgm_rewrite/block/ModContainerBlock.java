package io.github.vampirestudios.hgm_rewrite.block;

import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

import javax.annotation.Nullable;

public class ModContainerBlock extends BlockWithEntity implements Waterloggable {

    public ModContainerBlock(Settings block$Settings_1) {
        super(block$Settings_1);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView var1) {
        return null;
    }

}