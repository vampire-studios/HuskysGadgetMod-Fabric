package io.github.vampirestudios.hgm.init;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.block.*;
import io.github.vampirestudios.vampirelib.utils.registry.RegistryUtils;
import net.minecraft.block.Block;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class GadgetBlocks {

    public static final Block SERVER_TERMINAL;
    public static final Block ELECTRIC_SECURITY_FENCE;
    public static final Block LASER_GATE;
    public static final Block EASTER_EGG;

    public static final Block[] ROOF_LIGHTS = new Block[16];
    public static final Block[] ROUTERS = new Block[16];
    public static final Block[] PRINTERS = new Block[16];
    public static final Block[] LAPTOPS = new Block[16];

    public static final Block PAPER = new BlockPaper();

    static {
        for (DyeColor color : DyeColor.values()) {
            ROOF_LIGHTS[color.getId()] = RegistryUtils.register(new BlockRoofLights(), new Identifier(HuskysGadgetMod.MOD_ID, String.format("%s_roof_light", color.getName())));
            ROUTERS[color.getId()] = RegistryUtils.register(new BlockRouter(color), new Identifier(HuskysGadgetMod.MOD_ID, String.format("%s_router", color.getName())));
            PRINTERS[color.getId()] = RegistryUtils.register(new BlockPrinter(color), new Identifier(HuskysGadgetMod.MOD_ID, String.format("%s_printer", color.getName())));
            LAPTOPS[color.getId()] = RegistryUtils.register(new BlockLaptop(color), new Identifier(HuskysGadgetMod.MOD_ID, String.format("%s_laptop", color.getName())));
        }
        SERVER_TERMINAL = RegistryUtils.register(new BlockServerTerminal(), new Identifier(HuskysGadgetMod.MOD_ID, "server_terminal"));
        EASTER_EGG = RegistryUtils.registerBlockWithoutItem(new BlockEasterEgg(), new Identifier(HuskysGadgetMod.MOD_ID, "easter_egg"));
        ELECTRIC_SECURITY_FENCE = RegistryUtils.register(new BlockElectricSecurityFence(), new Identifier(HuskysGadgetMod.MOD_ID, "electric_security_fence"));
        LASER_GATE = RegistryUtils.register(new BlockElectricSecurityGate(), new Identifier(HuskysGadgetMod.MOD_ID, "laser_gate"));
    }

}
