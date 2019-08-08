package io.github.vampirestudios.hgm.core.client;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.block.ColoredBlock;
import io.github.vampirestudios.hgm.block.entity.*;
import io.github.vampirestudios.hgm.core.BaseDevice;
import io.github.vampirestudios.hgm.init.GadgetBlocks;
import io.github.vampirestudios.hgm.init.GadgetItems;
import io.github.vampirestudios.hgm.item.ColoredItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class ClientInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BaseDevice.addWallpaper(new Identifier(HuskysGadgetMod.MOD_ID, "textures/gui/wallpapers/default.png"));
        for(int i = 1; i > 17; i++) {
            BaseDevice.addWallpaper(new Identifier(HuskysGadgetMod.MOD_ID, String.format("textures/gui/wallpapers/wallpaper_%d.png", i)));
        }

        ItemColorProvider easterEgg = (stack, tintIndex) -> tintIndex < 2 && stack.hasTag() ? Objects.requireNonNull(stack.getTag()).getInt("color" + tintIndex) : 0xFFFFFF;
        ColorProviderRegistry.ITEM.register(easterEgg, GadgetItems.EASTER_EGG_ITEM);

        BlockColorProvider easterEggBlock = (state, worldIn, pos, tintIndex) -> {
            BlockEntity te = Objects.requireNonNull(worldIn).getBlockEntity(Objects.requireNonNull(pos));
            if (te instanceof EasterEggBlockEntity) {
                return ((EasterEggBlockEntity) te).getColor(tintIndex);
            }
            return 0xFFFFFF;
        };
        ColorProviderRegistry.BLOCK.register(easterEggBlock, GadgetBlocks.EASTER_EGG);

        ItemColorProvider handlerItems = (s, t) -> t == 0 ? ((ColoredItem) s.getItem()).color.getId() : 0xFFFFFF;
        ColorProviderRegistry.ITEM.register(handlerItems, GadgetItems.FLASH_DRIVES);
        ColorProviderRegistry.ITEM.register(handlerItems, GadgetBlocks.ROOF_LIGHTS);
        ColorProviderRegistry.ITEM.register(handlerItems, GadgetBlocks.ROUTERS);
        ColorProviderRegistry.ITEM.register(handlerItems, GadgetBlocks.PRINTERS);
        ColorProviderRegistry.ITEM.register(handlerItems, GadgetBlocks.LAPTOPS);

        BlockColorProvider handlerBlocks = (s, w, p, t) -> t == 0 ? ((ColoredBlock) s.getBlock()).color.getId() : 0xFFFFFF;
        ColorProviderRegistry.BLOCK.register(handlerBlocks, GadgetBlocks.ROOF_LIGHTS);
        ColorProviderRegistry.BLOCK.register(handlerBlocks, GadgetBlocks.ROUTERS);
        ColorProviderRegistry.BLOCK.register(handlerBlocks, GadgetBlocks.PRINTERS);
        ColorProviderRegistry.BLOCK.register(handlerBlocks, GadgetBlocks.LAPTOPS);

        BlockEntityRendererRegistry.INSTANCE.register(PaperBlockEntity.class, new PaperRenderer());
        BlockEntityRendererRegistry.INSTANCE.register(PrinterBlockEntity.class, new PrinterRenderer());
        BlockEntityRendererRegistry.INSTANCE.register(LaptopBlockEntity.class, new LaptopRenderer());
        BlockEntityRendererRegistry.INSTANCE.register(RouterBlockEntity.class, new RouterRenderer());
    }

}