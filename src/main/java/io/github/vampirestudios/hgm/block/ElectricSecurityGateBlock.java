package io.github.vampirestudios.hgm.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Random;

public class ElectricSecurityGateBlock extends DoorBlock {
    public ElectricSecurityGateBlock() {
        super(FabricBlockSettings.of(Material.METAL).lightLevel(2).hardness(1.0F).build());
    }

    public static boolean canWallConnect(BlockState blockState_1, Direction direction_1) {
        return (blockState_1.get(FACING)).getAxis() == direction_1.rotateYClockwise().getAxis();
    }

    @Override
    public void onEntityCollision(BlockState p_196262_1_, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof ItemEntity) && !entity.getName().equals("unknown")) {
            if (entity instanceof CreeperEntity) {
                CreeperEntity creeper = (CreeperEntity) entity;
                LightningEntity lightning = new LightningEntity(world, pos.getX(), pos.getY(), pos.getZ(), false);
                if (!creeper.getIgnited()) {
                    creeper.setFuseSpeed(1);
                    creeper.onStruckByLightning(lightning);
                }
            } else if (entity instanceof PlayerEntity) {
                if (!((PlayerEntity) entity).isCreative()) {
                    entity.damage(ElectricSecurityFenceBlock.electric, (int) 2.0F);
                    world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_ANVIL_HIT, SoundCategory.BLOCKS, 0.2F, 1.0F);

                    this.sparkle(world, pos);
                }
            } else {
                entity.damage(ElectricSecurityFenceBlock.electric, (int) 2.0F);
                world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_ANVIL_HIT, SoundCategory.BLOCKS, 0.2F, 1.0F);
                this.sparkle(world, pos);
            }
        }
    }

    private void sparkle(World worldIn, BlockPos pos) {
        BlockState state = worldIn.getBlockState(pos);
        Random random = new Random();
        double d0 = 0.0625D;

        for (int l = 0; l < 6; ++l) {
            double d1 = (pos.getX() + random.nextFloat());
            double d2 = (pos.getY() + random.nextFloat());
            double d3 = (pos.getZ() + random.nextFloat());

            if (l == 0) {
                d2 = (pos.getY() + 1) + d0;
            }

            if (l == 1) {
                d2 = (pos.getY()) - d0;
            }

            if (l == 2) {
                d3 = (pos.getZ() + 1) + d0;
            }

            if (l == 3) {
                d3 = (pos.getZ()) - d0;
            }

            if (l == 4) {
                d1 = (pos.getX() + 1) + d0;
            }

            if (l == 5) {
                d1 = (pos.getX()) - d0;
            }

            if (d1 < pos.getX() || d1 > (pos.getX() + 1) || d2 < 0.0D || d2 > (pos.getY() + 1) || d3 < pos.getZ() || d3 > (pos.getZ() + 1)) {
                worldIn.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, d1, d2, d3, 0.0D, 0.0D, 0.0D);
            }
        }
    }

}
