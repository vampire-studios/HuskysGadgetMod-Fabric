package io.github.vampirestudios.hgm.block;

import io.github.vampirestudios.hgm.block.entity.ServerTerminalBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class ServerTerminalBlock extends DeviceBlock {

    public ServerTerminalBlock() {
        super(Material.METAL);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new ServerTerminalBlockEntity();
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockState state = super.getPlacementState(context);
        return state.with(FACING, context.getPlayer().getHorizontalFacing());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(FACING);
    }

}