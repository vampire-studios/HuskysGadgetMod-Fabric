package io.github.vampirestudios.hgm.init;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.block.entity.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HGMBlockEntities {

    public static final BlockEntityType<EasterEggBlockEntity> EASTER_EGG;
    public static final BlockEntityType<PaperBlockEntity> PAPER;
    public static final BlockEntityType<RoofLightBlockEntity> ROOF_LIGHTS;
    public static final BlockEntityType<RouterBlockEntity> ROUTERS;
    public static final BlockEntityType<PrinterBlockEntity> PRINTERS;
    public static final BlockEntityType<LaptopBlockEntity> LAPTOPS;
    public static final BlockEntityType<ServerTerminalBlockEntity> SERVER_TERMINAL;
    public static final BlockEntityType<GamingDeskBlockEntity> GAMING_DESK;
    public static final BlockEntityType<ThreeDeePrinterBlockEntity> THREE_DEE_PRINTER;

    static {
        EASTER_EGG = register("easter_egg", BlockEntityType.Builder.create(EasterEggBlockEntity::new, HGMBlocks.EASTER_EGG));
        PAPER = register("paper", BlockEntityType.Builder.create(PaperBlockEntity::new, HGMBlocks.PAPER));
        ROOF_LIGHTS = register("roof_light", BlockEntityType.Builder.create(RoofLightBlockEntity::new, HGMBlocks.ROOF_LIGHTS));
        ROUTERS = register("router", BlockEntityType.Builder.create(RouterBlockEntity::new, HGMBlocks.ROUTERS));
        PRINTERS = register("printer", BlockEntityType.Builder.create(PrinterBlockEntity::new, HGMBlocks.PRINTERS));
        LAPTOPS = register("laptop", BlockEntityType.Builder.create(LaptopBlockEntity::new, HGMBlocks.LAPTOPS));
        SERVER_TERMINAL = register("server_terminal", BlockEntityType.Builder.create(ServerTerminalBlockEntity::new, HGMBlocks.SERVER_TERMINAL));
        GAMING_DESK = register("gaming_desk", BlockEntityType.Builder.create(GamingDeskBlockEntity::new, HGMBlocks.SERVER_TERMINAL));
        THREE_DEE_PRINTER = register("3d_printer", BlockEntityType.Builder.create(ThreeDeePrinterBlockEntity::new, HGMBlocks.THREE_DEE_PRINTER));
    }

    public static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType.Builder<T> builder) {
        BlockEntityType<T> blockEntityType = builder.build(null);
        if (!Registry.BLOCK_ENTITY_TYPE.containsId(new Identifier(HuskysGadgetMod.MOD_ID, name)))
            Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(HuskysGadgetMod.MOD_ID, name), blockEntityType);
        return blockEntityType;
    }

}