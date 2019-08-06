package io.github.vampirestudios.hgm.block;

import io.github.vampirestudios.hgm.block.entity.TileEntityRoofLights;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.state.StateContainer;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockRoofLights extends BlockDecoration {

    public BlockRoofLights() {
        super(Material.ANVIL);
        this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityRoofLights();
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileEntityRoofLights) {
            TileEntityRoofLights roofLights = (TileEntityRoofLights) tileEntity;

            CompoundTag tileEntityTag = new CompoundTag();
            roofLights.write(tileEntityTag);
            tileEntityTag.remove("pos");
            tileEntityTag.remove("color");
            tileEntityTag.remove("powered");
            tileEntityTag.remove("lightColour");

            CompoundTag compound = new CompoundTag();
            compound.put("BlockEntityTag", tileEntityTag);

            ItemStack drop = new ItemStack(Item.getItemFromBlock(this));
            drop.setTag(compound);

            worldIn.addEntity(new ItemEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop));
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = super.getStateForPlacement(context);
        return state.with(FACING, context.getPlayer().getHorizontalFacing());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(FACING);
    }

}