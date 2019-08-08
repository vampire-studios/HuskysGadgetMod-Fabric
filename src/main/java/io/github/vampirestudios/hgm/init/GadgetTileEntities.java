package io.github.vampirestudios.hgm.init;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.block.entity.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GadgetTileEntities {

    public static final BlockEntityType<EasterEggBlockEntity> EASTER_EGG;
    public static final BlockEntityType<PaperBlockEntity> PAPER;
    public static final BlockEntityType<RoofLightBlockEntity> ROOF_LIGHTS;
    public static final BlockEntityType<RouterBlockEntity> ROUTERS;
    public static final BlockEntityType<PrinterBlockEntity> PRINTERS;
    public static final BlockEntityType<LaptopBlockEntity> LAPTOPS;
    public static final BlockEntityType<ServerTerminalBlockEntity> SERVER_TERMINAL;

    static {
        EASTER_EGG = register("easter_egg", BlockEntityType.Builder.create(EasterEggBlockEntity::new, GadgetBlocks.EASTER_EGG));
        PAPER = register("paper", BlockEntityType.Builder.create(PaperBlockEntity::new, GadgetBlocks.PAPER));
        ROOF_LIGHTS = register("roof_light", BlockEntityType.Builder.create(RoofLightBlockEntity::new, GadgetBlocks.ROOF_LIGHTS));
        ROUTERS = register("router", BlockEntityType.Builder.create(RouterBlockEntity::new, GadgetBlocks.ROUTERS));
        PRINTERS = register("printer", BlockEntityType.Builder.create(PrinterBlockEntity::new, GadgetBlocks.PRINTERS));
        LAPTOPS = register("laptop", BlockEntityType.Builder.create(LaptopBlockEntity::new, GadgetBlocks.LAPTOPS));
        SERVER_TERMINAL = register("server_terminal", BlockEntityType.Builder.create(ServerTerminalBlockEntity::new, GadgetBlocks.SERVER_TERMINAL));
    }

    public static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType.Builder<T> builder) {
        BlockEntityType<T> blockEntityType = builder.build(null);
        if (!Registry.BLOCK_ENTITY.containsId(new Identifier(HuskysGadgetMod.MOD_ID, name)))
            Registry.register(Registry.BLOCK_ENTITY, new Identifier(HuskysGadgetMod.MOD_ID, name), blockEntityType);
        return blockEntityType;
    }

}