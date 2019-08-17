package io.github.vampirestudios.hgm;

import io.github.vampirestudios.hgm.api.ApplicationManager;
import io.github.vampirestudios.hgm.block.PrinterBlock;
import io.github.vampirestudios.hgm.init.*;
import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HuskysGadgetMod implements ModInitializer {

    public static final String MOD_ID = "hgm";
    public static final String MOD_NAME = "Husky's Gadget Mod";
    public static final Logger LOGGER = LogManager.getLogger(String.format("[%s]", MOD_NAME));
    public static final ModSetup SETUP = new ModSetup();

    public static final ItemGroup DEVICE_BLOCKS = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "device_blocks"))
        .icon(() -> new ItemStack(Blocks.STONE)).build();
    public static final ItemGroup DEVICE_ITEMS = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "device_items"))
            .icon(() -> new ItemStack(Blocks.STONE)).build();
    public static final ItemGroup DEVICE_DECORATION =FabricItemGroupBuilder.create(new Identifier(MOD_ID, "device_decoration"))
            .icon(() -> new ItemStack(Blocks.STONE)).build();

    public static ModConfig config;

    @Override
    public void onInitialize() {
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);

        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        new HGMBlockEntities();
        new HGMBlocks();
        new HGMItems();
        new HGMSounds();

        HGMApplications.init();
        HGMTasks.register();

        UseBlockCallback.EVENT.register((playerEntity, world, hand, blockHitResult) -> {
            if (!playerEntity.getStackInHand(hand).isEmpty() && playerEntity.getStackInHand(hand).getItem() == Items.PAPER) {
                if (world.getBlockState(blockHitResult.getBlockPos()).getBlock() instanceof PrinterBlock) {
                    return ActionResult.SUCCESS;
                }
            }
            return ActionResult.PASS;
        });

        ApplicationManager.getAvailableApplications().forEach(appInfo -> System.out.println(appInfo.getName()));
    }

}