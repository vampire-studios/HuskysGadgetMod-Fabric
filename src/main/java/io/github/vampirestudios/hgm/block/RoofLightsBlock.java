package io.github.vampirestudios.hgm.block;

import io.github.vampirestudios.hgm.block.entity.RoofLightBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.state.StateFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class RoofLightsBlock extends DecorationBlock {

    public RoofLightsBlock() {
        super(Material.ANVIL);
        this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new RoofLightBlockEntity();
    }

    @Override
    public void onBreak(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        if (tileEntity instanceof RoofLightBlockEntity) {
            RoofLightBlockEntity roofLights = (RoofLightBlockEntity) tileEntity;

            CompoundTag tileEntityTag = new CompoundTag();
            roofLights.toTag(tileEntityTag);
            tileEntityTag.remove("pos");
            tileEntityTag.remove("color");
            tileEntityTag.remove("powered");
            tileEntityTag.remove("lightColour");

            CompoundTag compound = new CompoundTag();
            compound.put("BlockEntityTag", tileEntityTag);

            ItemStack drop = new ItemStack(Item.fromBlock(this));
            drop.setTag(compound);

            worldIn.spawnEntity(new ItemEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop));
        }
        super.onBreak(worldIn, pos, state, player);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockState state = super.getPlacementState(context);
        return state.with(FACING, context.getPlayer().getHorizontalFacing());
    }

    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory$Builder_1) {
        stateFactory$Builder_1.add(FACING);
    }

}