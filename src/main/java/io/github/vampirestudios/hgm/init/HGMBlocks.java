package io.github.vampirestudios.hgm.init;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.block.*;
import io.github.vampirestudios.hgm.item.ColoredBlockItem;
import io.github.vampirestudios.vampirelib.utils.registry.RegistryUtils;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HGMBlocks {

    public static final Block SERVER_TERMINAL;
    public static final Block ELECTRIC_SECURITY_FENCE;
    public static final Block LASER_GATE;
    public static final Block EASTER_EGG;

    public static final Block[] ROOF_LIGHTS = new Block[16];
    public static final Block[] ROUTERS = new Block[16];
    public static final Block[] PRINTERS = new Block[16];
    public static final Block[] LAPTOPS = new Block[16];
    public static final Block[] GAMING_DESKS = new Block[16];
    public static final Block[] THREE_DEE_PRINTER = new Block[16];
    public static final Block[] DESKTOP_CASE = new Block[16];
    public static final Block[] DESKTOP = new Block[16];
    public static final Block[] MONITOR = new Block[16];
    public static final Block[] ETHERNET_OUTLET = new Block[16];

    public static final Block PAPER = new PaperBlock();

    static {
        for (DyeColor color : DyeColor.values()) {
            ROOF_LIGHTS[color.getId()] = register(new RoofLightsBlock(), new Identifier(HuskysGadgetMod.MOD_ID, String.format("%s_roof_light", color.getName())));
            ROUTERS[color.getId()] = registerColoredDevice(new RouterBlock(color), color, new Identifier(HuskysGadgetMod.MOD_ID, String.format("%s_router", color.getName())));
            PRINTERS[color.getId()] = registerColoredDevice(new PrinterBlock(color), color, new Identifier(HuskysGadgetMod.MOD_ID, String.format("%s_printer", color.getName())));
            LAPTOPS[color.getId()] = registerColoredDevice(new LaptopBlock(color), color, new Identifier(HuskysGadgetMod.MOD_ID, String.format("%s_laptop", color.getName())));
            GAMING_DESKS[color.getId()] = registerColoredDevice(new GamingDeskBlock(color), color,
                    new Identifier(HuskysGadgetMod.MOD_ID, String.format("%s_gaming_desk", color.getName())));
            THREE_DEE_PRINTER[color.getId()] = registerColoredDevice(new ThreeDeePrinterBlock(color), color,
                    new Identifier(HuskysGadgetMod.MOD_ID, String.format("%s_3d_printer", color.getName())));
            DESKTOP_CASE[color.getId()] = registerColoredDevice(new Dyab(color), color,
                    new Identifier(HuskysGadgetMod.MOD_ID, String.format("%s_3d_printer", color.getName())));
            THREE_DEE_PRINTER[color.getId()] = registerColoredDevice(new ThreeDeePrinterBlock(color), color,
                    new Identifier(HuskysGadgetMod.MOD_ID, String.format("%s_3d_printer", color.getName())));
            THREE_DEE_PRINTER[color.getId()] = registerColoredDevice(new ThreeDeePrinterBlock(color), color,
                    new Identifier(HuskysGadgetMod.MOD_ID, String.format("%s_3d_printer", color.getName())));
        }
        SERVER_TERMINAL = registerDevice(new ServerTerminalBlock(), new Identifier(HuskysGadgetMod.MOD_ID, "server_terminal"));
        EASTER_EGG = RegistryUtils.registerBlockWithoutItem(new EasterEggBlock(), new Identifier(HuskysGadgetMod.MOD_ID, "easter_egg"));
        ELECTRIC_SECURITY_FENCE = register(new ElectricSecurityFenceBlock(), new Identifier(HuskysGadgetMod.MOD_ID, "electric_security_fence"));
        LASER_GATE = register(new ElectricSecurityGateBlock(), new Identifier(HuskysGadgetMod.MOD_ID, "laser_gate"));
    }

    private static Block registerDevice(Block block, Identifier name) {
        return RegistryUtils.register(block, name, HuskysGadgetMod.DEVICE_BLOCKS);
    }

    private static Block registerColoredDevice(Block block, DyeColor color, Identifier name) {
        Registry.register(Registry.BLOCK, name, block);
        BlockItem item = new ColoredBlockItem(block, color);
        item.appendBlocks(Item.BLOCK_ITEMS, item);
        Registry.register(Registry.ITEM, name, item);
        return block;
    }

    private static Block register(Block block, Identifier name) {
        return RegistryUtils.register(block, name, HuskysGadgetMod.DEVICE_DECORATION);
    }

}
