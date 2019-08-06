package io.github.vampirestudios.hgm;

import io.github.vampirestudios.hgm.api.AppInfo;
import io.github.vampirestudios.hgm.api.app.Application;
import io.github.vampirestudios.hgm.api.print.IPrint;
import io.github.vampirestudios.hgm.api.theme.Theme;
import io.github.vampirestudios.hgm.block.BlockPrinter;
import io.github.vampirestudios.hgm.init.GadgetItems;
import io.github.vampirestudios.hgm.init.GadgetTileEntities;
import io.github.vampirestudios.hgm.object.ThemeInfo;
import io.github.vampirestudios.hgm.system.SystemApplication;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HuskysGadgetMod implements ModInitializer {

    public static final String MOD_ID = "hgm";
    public static final String MOD_NAME = "Husky's Gadget Mod";
    public static final Logger LOGGER = LogManager.getLogger(String.format("[%s]", MOD_NAME));

    public static final ItemGroup TESTING = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "device_blocks"))
            .icon(() -> null).build();
    public static final ItemGroup DEVICE_BLOCKS = new DeviceTab("device_blocks");
    public static final ItemGroup DEVICE_ITEMS = new DeviceTab("device_items");
    public static final ItemGroup DEVICE_DECORATION = new DeviceTab("device_decoration");

    @Override
    public void onInitialize() {
        new GadgetItems();
        GadgetTileEntities.init();

        UseBlockCallback.EVENT.register((playerEntity, world, hand, blockHitResult) -> {
            if (!playerEntity.getStackInHand(hand).isEmpty() && playerEntity.getStackInHand(hand).getItem() == Items.PAPER) {
                if (world.getBlockState(blockHitResult.getBlockPos()).getBlock() instanceof BlockPrinter) {
                    return ActionResult.SUCCESS;
                }
            }
            return ActionResult.FAIL;
        });
    }

    List<AppInfo> allowedApps;
    private List<ThemeInfo> allowedThemes;

    @Nullable
    public Application registerApplication(Identifier identifier, Class<? extends Application> clazz) {
        if (allowedApps == null) {
            allowedApps = new ArrayList<>();
        }
        if (SystemApplication.class.isAssignableFrom(clazz)) {
            allowedApps.add(new AppInfo(identifier, true));
        } else {
            allowedApps.add(new AppInfo(identifier, false));
        }
        return null;
    }

    public boolean registerPrint(Identifier identifier, Class<? extends IPrint> classPrint) {
        return true;
    }

    public boolean hasAllowedApplications() {
        return allowedApps != null;
    }

    public List<AppInfo> getAllowedApplications() {
        if (allowedApps == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(allowedApps);
    }

    /*@SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (allowedApps != null) {
            PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new MessageSyncApplications(allowedApps));
        }
        PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new MessageSyncConfig());
    }*/

    public void showNotification(CompoundTag tag) {
    }

    @Nullable
    public Theme registerTheme(Identifier identifier) {
        if (allowedThemes == null) {
            allowedThemes = new ArrayList<>();
        }
        allowedThemes.add(new ThemeInfo(identifier));
        return null;
    }

    public boolean hasAllowedThemes() {
        return allowedThemes != null;
    }

    public List<ThemeInfo> getAllowedThemes() {
        if (allowedThemes == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(allowedThemes);
    }

}