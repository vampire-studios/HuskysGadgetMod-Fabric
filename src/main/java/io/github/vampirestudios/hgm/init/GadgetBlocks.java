package io.github.vampirestudios.hgm.init;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.block.*;
import io.github.vampirestudios.hgm.item.CustomBlockItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GadgetBlocks {

    public static final Block SERVER_TERMINAL = new BlockServerTerminal();
    public static final Block ELECTRIC_SECURITY_FENCE = new BlockElectricSecurityFence();
    public static final Block LASER_GATE = new BlockElectricSecurityGate();
    public static final Block EASTER_EGG = new BlockEasterEgg();

    public static final Block[] ROOF_LIGHTS = new Block[16];
    public static final Block[] ROUTERS = new Block[16];
    public static final Block[] PRINTERS = new Block[16];
    public static final Block[] LAPTOPS = new Block[16];

    public static final Block PAPER = new BlockPaper();

    static {
        for (DyeColor color : DyeColor.values()) {
            ROOF_LIGHTS[color.getId()] = new BlockRoofLights(), new Identifier(HuskysGadgetMod.MOD_ID, String.format("%s_roof_light", color.getName())));
            ROUTERS[color.getId()] = new BlockRouter(color));
            PRINTERS[color.getId()] = new BlockPrinter(color));
            LAPTOPS[color.getId()] = new BlockLaptop(color));
        }
        event.getRegistry().registerAll(
                SERVER_TERMINAL,
                EASTER_EGG,
                ELECTRIC_SECURITY_FENCE,
                LASER_GATE,
                PAPER
        );
    }

}
