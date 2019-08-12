package io.github.vampirestudios.hgm.block;

import io.github.vampirestudios.hgm.block.entity.EasterEggBlockEntity;
import io.github.vampirestudios.hgm.init.HGMItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EasterEggBlock extends Block implements BlockEntityProvider {

    public EasterEggBlock() {
        super(Settings.of(Material.CARPET).strength(1.0F, 1.0F));
    }

    @Override
    public void onBreak(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!worldIn.isClient) {
            BlockEntity te = worldIn.getBlockEntity(pos);
            if (te instanceof EasterEggBlockEntity) {
                EasterEggBlockEntity eggte = (EasterEggBlockEntity) te;
                ItemStack egg = new ItemStack(HGMItems.EASTER_EGG_ITEM);
                CompoundTag nbt = eggte.writeColorsToNBT(new CompoundTag());
                egg.setTag(nbt);
                worldIn.spawnEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), egg));
                System.out.println("Breaking block");
            }
            worldIn.breakBlock(pos, false);
        }

    }

    @Override
    public boolean isOpaque(BlockState state) {
        return false;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean hasBlockEntity() {
        return true;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView var1) {
        return new EasterEggBlockEntity();
    }

}