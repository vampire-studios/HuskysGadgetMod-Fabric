package io.github.vampirestudios.hgm.block;

import io.github.vampirestudios.hgm.block.entity.TileEntityDevice;
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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class BlockDevice extends BlockFacing {

    protected BlockDevice(Material materialIn) {
        super(materialIn);
    }

    @Override
    public boolean isSimpleFullBlock(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        return false;
    }

    @Override
    public boolean isOpaque(BlockState blockState_1) {
        return false;
    }

    @Nullable
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
        if (tileEntity instanceof TileEntityDevice) {
            TileEntityDevice tileEntityDevice = (TileEntityDevice) tileEntity;
            if (stack.hasCustomName()) {
                tileEntityDevice.setCustomName(stack.getName().getString());
            }
        }
    }

    @Override
    public void onBreak(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!worldIn.isClient && player.abilities.creativeMode) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof TileEntityDevice) {
                TileEntityDevice device = (TileEntityDevice) tileEntity;

                CompoundTag tileEntityTag = new CompoundTag();
                tileEntity.toTag(tileEntityTag);
                tileEntityTag.remove("x");
                tileEntityTag.remove("y");
                tileEntityTag.remove("z");
                tileEntityTag.remove("id");

                removeTagsForDrop(tileEntityTag);

                CompoundTag compound = new CompoundTag();
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

    void removeTagsForDrop(CompoundTag tileEntityTag) {

    }

    @Override
    public boolean hasBlockEntity() {
        return true;
    }

}
