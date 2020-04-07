package io.github.vampirestudios.hgm.block;

import io.github.vampirestudios.hgm.block.entity.DeviceBlockEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public abstract class ColoredDeviceBlock extends ColoredFacingBlock {

    public ColoredDeviceBlock(DyeColor color) {
        super(color);
        this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext blockItemUseContext) {
        BlockState state = super.getPlacementState(blockItemUseContext);
        return state.with(FACING, blockItemUseContext.getPlayer().getHorizontalFacing());
    }

    @Override
    public void onPlaced(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.onPlaced(worldIn, pos, state, placer, stack);
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        if (tileEntity instanceof DeviceBlockEntity) {
            DeviceBlockEntity tileEntityDevice = (DeviceBlockEntity) tileEntity;
            if (stack.hasCustomName()) {
                tileEntityDevice.setCustomName(stack.getName().getString());
            }
        }
    }
    @Override
    public void onBreak(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!worldIn.isClient && player.abilities.creativeMode) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof DeviceBlockEntity) {
                DeviceBlockEntity device = (DeviceBlockEntity) tileEntity;

                CompoundTag tileEntityTag = new CompoundTag();
                tileEntity.toTag(tileEntityTag);
                tileEntityTag.remove("x");
                tileEntityTag.remove("y");
                tileEntityTag.remove("z");
                tileEntityTag.remove("id");

                CompoundTag compound = new CompoundTag();
                compound.put("BlockEntityTag", tileEntityTag);

                ItemStack drop;
                drop = new ItemStack(Item.fromBlock(this));
                drop.setTag(compound);

                if (device.hasCustomName()) {
                    drop.setCustomName(new LiteralText(device.getCustomName()));
                }

                worldIn.spawnEntity(new ItemEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop));
            }
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

}
