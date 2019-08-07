package io.github.vampirestudios.hgm.init;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.block.entity.*;
import io.github.vampirestudios.vampirelib.utils.registry.RegistryUtils;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;

public class GadgetTileEntities {

    public static final BlockEntityType.Builder<?> EASTER_EGG = BlockEntityType.Builder.create(EasterEggBlockEntity::new, GadgetBlocks.EASTER_EGG);
    public static final BlockEntityType.Builder<?> PAPER = BlockEntityType.Builder.create(PaperBlockEntity::new, GadgetBlocks.PAPER);
    public static final BlockEntityType.Builder<?> ROOF_LIGHTS = BlockEntityType.Builder.create(RoofLightBlockEntity::new, GadgetBlocks.ROOF_LIGHTS);
    public static final BlockEntityType.Builder<?> ROUTERS = BlockEntityType.Builder.create(RouterBlockEntity::new, GadgetBlocks.ROUTERS);
    public static final BlockEntityType.Builder<?> PRINTERS = BlockEntityType.Builder.create(PrinterBlockEntity::new, GadgetBlocks.PRINTERS);
    public static final BlockEntityType.Builder<?> LAPTOPS = BlockEntityType.Builder.create(LaptopBlockEntity::new, GadgetBlocks.LAPTOPS);

    public static void init() {
        RegistryUtils.registerBlockEntity(EASTER_EGG, new Identifier(HuskysGadgetMod.MOD_ID, "easter_egg_be"));
        RegistryUtils.registerBlockEntity(PAPER, new Identifier(HuskysGadgetMod.MOD_ID, "paper_be"));
        RegistryUtils.registerBlockEntity(ROOF_LIGHTS, new Identifier(HuskysGadgetMod.MOD_ID, "roof_light_be"));
        RegistryUtils.registerBlockEntity(ROUTERS, new Identifier(HuskysGadgetMod.MOD_ID, "router_be"));
        RegistryUtils.registerBlockEntity(PRINTERS, new Identifier(HuskysGadgetMod.MOD_ID, "printer_be"));
        RegistryUtils.registerBlockEntity(LAPTOPS, new Identifier(HuskysGadgetMod.MOD_ID, "laptop_be"));
    }

}