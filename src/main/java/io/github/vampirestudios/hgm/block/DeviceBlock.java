package io.github.vampirestudios.hgm.block;

import io.github.vampirestudios.hgm.block.entity.DeviceBlockEntity;
import io.github.vampirestudios.hgm.utils.IColored;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public abstract class DeviceBlock extends FacingBlock {

    protected DeviceBlock(Material materialIn) {
        super(materialIn);
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockState thisState = super.getPlacementState(context);
        return thisState.with(FACING, context.getPlayer().getHorizontalFacing());
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
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

                NbtCompound tileEntityTag = new NbtCompound();
                tileEntity.toTag(tileEntityTag);
                tileEntityTag.remove("x");
                tileEntityTag.remove("y");
                tileEntityTag.remove("z");
                tileEntityTag.remove("id");

                NbtCompound compound = new NbtCompound();
                compound.put("BlockEntityTag", tileEntityTag);

                ItemStack drop;
                if (tileEntity instanceof IColored) {
                    drop = new ItemStack(Item.fromBlock(this), 1);
                } else {
                    drop = new ItemStack(Item.fromBlock(this));
                }
                drop.setTag(compound);

                if (device.hasCustomName()) {
                    drop.setCustomName(new LiteralText(device.getCustomName()));
                }

                worldIn.spawnEntity(new ItemEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop));
            }
        }
    }

}
