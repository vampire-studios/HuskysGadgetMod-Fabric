package io.github.vampirestudios.hgm_rewrite.block;

import io.github.vampirestudios.hgm_rewrite.core.Laptop;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LaptopBlock extends FacingBlock {

    public LaptopBlock(DyeColor color) {
        super(FabricBlockSettings.copy(Blocks.STONE).strength(1.0F, 1.0F).materialColor(color).sounds(BlockSoundGroup.METAL).dynamicBounds().build());
    }

    @Override
    public boolean activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1) {

        if(world_1.isClient) {
            MinecraftClient.getInstance().openScreen(Laptop.asDevice());
        }

        return super.activate(blockState_1, world_1, blockPos_1, playerEntity_1, hand_1, blockHitResult_1);
    }

}