package io.github.vampirestudios.hgm.block;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.block.entity.LaptopBlockEntity;
import io.github.vampirestudios.hgm.core.Laptop;
import io.github.vampirestudios.hgm.object.Bounds;
import io.github.vampirestudios.hgm.utils.TileEntityUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.LiteralText;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class LaptopBlock extends ColoredDeviceBlock {

    //public static final EnumProperty<Type> TYPE = EnumProperty.of("type", Type.class);

    private static final Box[] SCREEN_BOXES = new Bounds(13 * 0.0625, 0.0625, 1 * 0.0625, 1.0, 12 * 0.0625, 0.9375).getRotatedBounds();
    private static final Box BODY_OPEN_BOX = new Box(1 * 0.0625, 0.0, 1 * 0.0625, 13 * 0.0625, 1 * 0.0625, 15 * 0.0625);
    private static final Box BODY_CLOSED_BOX = new Box(1 * 0.0625, 0.0, 1 * 0.0625, 13 * 0.0625, 2 * 0.0625, 15 * 0.0625);
    private static final Box SELECTION_BOX_OPEN = new Box(0, 0, 0, 1, 12 * 0.0625, 1);
    private static final Box SELECTION_BOX_CLOSED = new Box(0, 0, 0, 1, 3 * 0.0625, 1);

    public LaptopBlock(DyeColor color) {
        super(color);
        this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH));//.with(TYPE, Type.BASE));
    }

    protected VoxelShape getActualShape(BlockState state, BlockView world, BlockPos pos) {
        BlockEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof LaptopBlockEntity) {
            LaptopBlockEntity laptop = (LaptopBlockEntity) tileEntity;
            if (laptop.isOpen()) {
                VoxelShape SHAPE_1 = VoxelShapes.cuboid(BODY_OPEN_BOX);
                VoxelShape SHAPE_2 = VoxelShapes.cuboid(SCREEN_BOXES[state.get(FACING).getHorizontal()]);
                return VoxelShapes.combine(SHAPE_1, SHAPE_2, BooleanBiFunction.OR);
            } else {
                return VoxelShapes.cuboid(BODY_CLOSED_BOX);
            }
        }
        return VoxelShapes.cuboid(BODY_CLOSED_BOX);
    }
    
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView worldIn, BlockPos pos, EntityContext context) {
        return getActualShape(state, worldIn, pos);
        /*
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        if (tileEntity instanceof LaptopBlockEntity) {
            LaptopBlockEntity laptop = (LaptopBlockEntity) tileEntity;
            if (laptop.isOpen()) {
                VoxelShape SHAPE_1 = VoxelShapes.cuboid(BODY_OPEN_BOX);
                VoxelShape SHAPE_2 = VoxelShapes.cuboid(SCREEN_BOXES[state.get(FACING).getHorizontal()]);
                return VoxelShapes.combine(SHAPE_1, SHAPE_2, BooleanBiFunction.OR);
            } else {
                return VoxelShapes.cuboid(BODY_CLOSED_BOX);
            }
        }
        return VoxelShapes.fullCube();*/
    }

    @Override
    public VoxelShape getOutlineShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, EntityContext entityContext_1) {
        return getActualShape(blockState_1, blockView_1, blockPos_1);
        /*
        BlockEntity tileEntity = blockView_1.getBlockEntity(blockPos_1);
        if (tileEntity instanceof LaptopBlockEntity) {
            LaptopBlockEntity laptop = (LaptopBlockEntity) tileEntity;
            if (laptop.isOpen()) {
                return VoxelShapes.cuboid(SELECTION_BOX_OPEN);
            } else {
                return VoxelShapes.cuboid(SELECTION_BOX_CLOSED);
            }
        }
        return VoxelShapes.fullCube();*/
    }

    @Override
    public boolean activate(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockHitResult hit) {
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        if (tileEntity instanceof LaptopBlockEntity) {
            LaptopBlockEntity laptop = (LaptopBlockEntity) tileEntity;

            if (player.isSneaking()) {
                if (!worldIn.isClient) {
                    laptop.openClose();
                }
            }
            if (!laptop.isOpen() && player.isSneaking() && Screen.hasControlDown()) {
                if (!worldIn.isClient) {
                    laptop.powerUnpower();
                }
            } else {
                /*if (side == state.get(FACING).rotateYCCW()) {
                    ItemStack heldItem = player.getHeldItem(handIn);
                    if (!heldItem.isEmpty() && (heldItem.getItem() == ForgeRegistries.ITEMS.getValue(new Identifier(HuskysGadgetMod.MOD_ID, "flash_drive_" + getDyeColor().getName())))) {
                        if (!worldIn.isRemote) {
                            if (laptop.getFileSystem().setAttachedDrive(heldItem.copy())) {
                                heldItem.shrink(1);
                                TileEntityUtil.markBlockForUpdate(worldIn, pos);
                            } else {
                                player.sendMessage(new StringTextComponent("No more available USB slots!"));
                            }
                        }
                    }

                    if (!worldIn.isRemote) {
                        ItemStack stack = laptop.getFileSystem().removeAttachedDrive();
                        if (stack != null) {
                            BlockPos summonPos = pos.offset(state.get(FACING).rotateYCCW());
                            worldIn.addEntity(new ItemEntity(worldIn, summonPos.getX() + 0.5, summonPos.getY(), summonPos.getZ() + 0.5, stack));
                            TileEntityUtil.markBlockForUpdate(worldIn, pos);
                        }
                    }
                    return true;
                }*/
                ItemStack heldItem = player.getStackInHand(handIn);
                if (!heldItem.isEmpty() && (heldItem.getItem() == Registry.ITEM.get(new Identifier(HuskysGadgetMod.MOD_ID, "flash_drive_" + getDyeColor().getName())))) {
                    if (!worldIn.isClient) {
                        if (laptop.getFileSystem().setAttachedDrive(heldItem.copy())) {
                            heldItem.decrement(1);
                            TileEntityUtil.markBlockForUpdate(worldIn, pos);
                        } else {
                            player.sendMessage(new LiteralText("No more available USB slots!"));
                        }
                    }
                }

                if (!worldIn.isClient) {
                    ItemStack stack = laptop.getFileSystem().removeAttachedDrive();
                    if (stack != null) {
                        BlockPos summonPos = pos.offset(state.get(FACING).rotateYCounterclockwise());
                        worldIn.spawnEntity(new ItemEntity(worldIn, summonPos.getX() + 0.5, summonPos.getY(), summonPos.getZ() + 0.5, stack));
                        TileEntityUtil.markBlockForUpdate(worldIn, pos);
                    }
                }

                /*if(laptop.isPowered()) {
                    if (laptop.isOpen() && worldIn.isClient) {
                        MinecraftClient.getInstance().openScreen(new Laptop());
                    }
                }*/

                if (worldIn.isClient) {
                    MinecraftClient.getInstance().openScreen(new Laptop());
                }
                /*if (!laptop.isPowered()) {
                    if (laptop.isOpen() && worldIn.isClient) {
                        player.addChatMessage(new LiteralText("The laptop is not powered. To power it do: CTRL + Shift + Right Click it"), true);
                    }
                }*/
            }

        }
        return true;
    }

    @Override
    public void onBreak(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(worldIn, pos, state, player);
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        if (tileEntity instanceof LaptopBlockEntity) {
            LaptopBlockEntity laptop = (LaptopBlockEntity) tileEntity;

            CompoundTag tileEntityTag = new CompoundTag();
            laptop.toTag(tileEntityTag);
            tileEntityTag.remove("open");
            tileEntityTag.remove("powered");

            CompoundTag compound = new CompoundTag();
            compound.put("BlockEntityTag", tileEntityTag);

            ItemStack drop = new ItemStack(Item.fromBlock(this));
            drop.setTag(compound);
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext blockItemUseContext) {
        BlockState state = super.getPlacementState(blockItemUseContext);
        return state.with(FACING, blockItemUseContext.getPlayer().getHorizontalFacing());
    }

    //@Override
    //protected void appendProperties(StateFactory.Builder<Block, BlockState> p_206840_1_) {
    //    p_206840_1_.add(FACING);//, TYPE);
    //}

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView worldIn) {
        return new LaptopBlockEntity();
    }
    /*
    public enum Type implements StringIdentifiable {
        BASE, SCREEN;

        @Override
        public String asString() {
            return name().toLowerCase();
        }

    }*/

}