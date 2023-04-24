package io.github.vampirestudios.hgm.core.client;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.block.ColoredFacingBlock;
import io.github.vampirestudios.hgm.block.entity.EasterEggBlockEntity;
import io.github.vampirestudios.hgm.block.entity.LaptopBlockEntity;
import io.github.vampirestudios.hgm.core.BaseDevice;
import io.github.vampirestudios.hgm.init.HGMBlockEntities;
import io.github.vampirestudios.hgm.init.HGMBlocks;
import io.github.vampirestudios.hgm.init.HGMItems;
import io.github.vampirestudios.hgm.item.ColoredBlockItem;
import io.github.vampirestudios.hgm.item.ColoredItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

import java.util.Objects;

public class ClientInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BaseDevice.addWallpaper(new Identifier(HuskysGadgetMod.MOD_ID, "textures/gui/wallpapers/default.png"));
        for(int i = 1; i > 17; i++) {
            BaseDevice.addWallpaper(new Identifier(HuskysGadgetMod.MOD_ID, String.format("textures/gui/wallpapers/wallpaper_%d.png", i)));
        }

        ColorProviderRegistry.ITEM.register((itemStack, layer) -> {
            int baseColor = itemStack.hasTag() ? Objects.requireNonNull(itemStack.getTag()).getInt("color" + layer) : 0xFFFFFF;
            if(layer == 1) {
                return baseColor;
            } else {
                return baseColor;
            }
        }, HGMItems.EASTER_EGG_ITEM);

        BlockColorProvider easterEggBlock = (state, worldIn, pos, layer) -> {
            BlockEntity te = Objects.requireNonNull(worldIn).getBlockEntity(Objects.requireNonNull(pos));
            if (te instanceof EasterEggBlockEntity) {
                return ((EasterEggBlockEntity) te).getColor(layer);
            }
            return 0xFFFFFF;
        };
        ColorProviderRegistry.BLOCK.register(easterEggBlock, HGMBlocks.EASTER_EGG);
        
        ItemColorProvider handlerItems = (ItemStack stack, int layer) -> {
            if (layer!=0) return 0xFFFFFF;
            if (!(stack.getItem() instanceof ColoredItem)) return 0xFFFFFF;
            ColoredItem item = (ColoredItem)stack.getItem();
            return item.color.getFireworkColor();
        };
        ColorProviderRegistry.ITEM.register(handlerItems, HGMItems.FLASH_DRIVES);

        ItemColorProvider handlerItems2 = (ItemStack stack, int layer) -> {
            if (layer!=0) return 0xFFFFFF;
            if (!(stack.getItem() instanceof ColoredBlockItem)) return 0xFFFFFF;
            ColoredBlockItem item = (ColoredBlockItem)stack.getItem();
            return item.color.getFireworkColor();
        };
        ColorProviderRegistry.ITEM.register(handlerItems2, HGMBlocks.ROOF_LIGHTS);
        ColorProviderRegistry.ITEM.register(handlerItems2, HGMBlocks.ROUTERS);
        ColorProviderRegistry.ITEM.register(handlerItems2, HGMBlocks.PRINTERS);
        ColorProviderRegistry.ITEM.register(handlerItems2, HGMBlocks.LAPTOPS);
        ColorProviderRegistry.ITEM.register(handlerItems2, HGMBlocks.THREE_DEE_PRINTER);
        ColorProviderRegistry.ITEM.register(handlerItems2, HGMBlocks.GAMING_DESKS);

        BlockColorProvider handlerBlocks = (BlockState state, BlockRenderView world, BlockPos pos, int layer) -> {
            if (layer!=0) return 0xFFFFFF;
            if (!(state.getBlock() instanceof ColoredFacingBlock)) return 0xFFFFFF;
            DyeColor dye = ((ColoredFacingBlock)state.getBlock()).getDyeColor();
            return dye.getFireworkColor();
        };
        
        ColorProviderRegistry.BLOCK.register(handlerBlocks, HGMBlocks.ROOF_LIGHTS);
        ColorProviderRegistry.BLOCK.register(handlerBlocks, HGMBlocks.ROUTERS);
        ColorProviderRegistry.BLOCK.register(handlerBlocks, HGMBlocks.PRINTERS);
        ColorProviderRegistry.BLOCK.register(handlerBlocks, HGMBlocks.LAPTOPS);
        ColorProviderRegistry.BLOCK.register(handlerBlocks, HGMBlocks.THREE_DEE_PRINTER);
        ColorProviderRegistry.BLOCK.register(handlerBlocks, HGMBlocks.GAMING_DESKS);

        BlockEntityRendererRegistry.INSTANCE.register(HGMBlockEntities.PAPER, PaperRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(HGMBlockEntities.PRINTERS, PrinterRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(HGMBlockEntities.LAPTOPS, ctx -> new LaptopRenderer());
//        BlockEntityRendererRegistry.INSTANCE.register(RouterBlockEntity.class, new RouterRenderer());
        
        ModelLoadingRegistry.INSTANCE.registerAppender((resourceManager, consumer)->
                consumer.accept(new ModelIdentifier(new Identifier(HuskysGadgetMod.MOD_ID, "laptop_screen"), "")));
    }

}