package io.github.vampirestudios.hgm.block;

import io.github.vampirestudios.hgm.block.entity.RouterBlockEntity;
import io.github.vampirestudios.hgm.object.Bounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class RouterBlock extends ColoredDeviceBlock {

    public static final BooleanProperty VERTICAL = BooleanProperty.of("vertical");

    private static final Box[] BODY_BOUNDING_BOX = new Bounds(4, 0, 2, 12, 2, 14).getRotatedBounds();
    private static final Box[] BODY_VERTICAL_BOUNDING_BOX = new Bounds(14, 1, 2, 16, 9, 14).getRotatedBounds();
    private static final Box[] SELECTION_BOUNDING_BOX = new Bounds(3, 0, 1, 13, 3, 15).getRotatedBounds();
    private static final Box[] SELECTION_VERTICAL_BOUNDING_BOX = new Bounds(13, 0, 1, 16, 10, 15).getRotatedBounds();

    public RouterBlock(DyeColor color) {
        super(color);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(VERTICAL, false));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView worldIn, BlockPos pos, EntityContext context) {
        if (state.get(VERTICAL)) {
            return VoxelShapes.cuboid(SELECTION_VERTICAL_BOUNDING_BOX[state.get(FACING).getHorizontal()]);
        }
        return VoxelShapes.cuboid(SELECTION_BOUNDING_BOX[state.get(FACING).getHorizontal()]);
    }

    @Override
    public VoxelShape getRayTraceShape(BlockState state, BlockView worldIn, BlockPos pos) {
        if (state.get(VERTICAL)) {
            return VoxelShapes.cuboid(SELECTION_VERTICAL_BOUNDING_BOX[state.get(FACING).getHorizontal()]);
        }
        return VoxelShapes.cuboid(SELECTION_BOUNDING_BOX[state.get(FACING).getHorizontal()]);
    }

    @Override
    public ActionResult onUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockHitResult hit) {
        if (worldIn.isClient && player.abilities.creativeMode) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof RouterBlockEntity) {
                RouterBlockEntity tileEntityRouter = (RouterBlockEntity) tileEntity;
                tileEntityRouter.setDebug();
                if (tileEntityRouter.isDebug()) {
//                    PacketHandler.INSTANCE.sendToServer(new MessageSyncBlock(pos));
                }
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onBreak(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!worldIn.isClient && !player.abilities.creativeMode) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof RouterBlockEntity) {
                RouterBlockEntity router = (RouterBlockEntity) tileEntity;

                CompoundTag tileEntityTag = new CompoundTag();
                router.toTag(tileEntityTag);
                tileEntityTag.remove("x");
                tileEntityTag.remove("y");
                tileEntityTag.remove("z");
                tileEntityTag.remove("id");

                CompoundTag compound = new CompoundTag();
                compound.put("BlockEntityTag", tileEntityTag);

                ItemStack drop = new ItemStack(Item.fromBlock(this));
                drop.setTag(compound);

                worldIn.spawnEntity(new ItemEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop));
            }
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext blockItemUseContext) {
        BlockState state = super.getPlacementState(blockItemUseContext);
        return state.with(FACING, blockItemUseContext.getSide().getAxis() == Direction.Axis.Y ? blockItemUseContext.getPlayer().getHorizontalFacing() :
                blockItemUseContext.getSide().getOpposite()).with(VERTICAL, blockItemUseContext.getSide().getAxis() != Direction.Axis.Y);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new RouterBlockEntity();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateFactory$Builder_1) {
        stateFactory$Builder_1.add(FACING, VERTICAL);
    }

}
