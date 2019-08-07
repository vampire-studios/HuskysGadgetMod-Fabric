package io.github.vampirestudios.hgm.init;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.enums.EnumPhoneColours;
import io.github.vampirestudios.hgm.item.*;
import io.github.vampirestudios.vampirelib.utils.registry.RegistryUtils;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class GadgetItems {

    public static final FoodComponent EASTER_EGG = new FoodComponent.Builder().saturationModifier(0.3F).hunger(4).snack().alwaysEdible()
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 300, 2), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 300, 1), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 300, 10), 1.0F)
            .build();

    public static final Item EASTER_EGG_ITEM;
    public static final Item MOTHERBOARD;
    public static final Item CPU;
    public static final Item RAM_STICKS;
    public static final Item WIFI_CARD;
    public static final Item GPU;

    public static final Item CAMERA;
    public static final Item CD;
    public static final Item DVD;
    public static final Item[] FLASH_DRIVES = new Item[16];

    public static final Item ID_CARD;
    public static final Item WHITE_WIIU_GAMEPAD;
    public static final Item BLACK_WIIU_GAMEPAD;

    public static final Item[] PIXEL_PHONES = new Item[3];
    public static final Item[] PIXEL_TABS = new Item[3];
    public static final Item[] PIXEL_WATCHES = new Item[3];

    public static final Item USB_CABLE;
    public static final Item ETHERNET_CABLE;

    static {
        EASTER_EGG_ITEM = RegistryUtils.registerItem(new Item(new Item.Settings().food(EASTER_EGG)), new Identifier(HuskysGadgetMod.MOD_ID, "easter_egg_item"));
        MOTHERBOARD = RegistryUtils.registerItem(new ItemMotherBoard(), new Identifier(HuskysGadgetMod.MOD_ID, "mother_board"));
        CPU = RegistryUtils.registerItem(new ItemMotherBoard.Component(), new Identifier(HuskysGadgetMod.MOD_ID, "cpu"));
        RAM_STICKS = RegistryUtils.registerItem(new ItemMotherBoard.Component(), new Identifier(HuskysGadgetMod.MOD_ID, "ram_stick"));
        WIFI_CARD = RegistryUtils.registerItem(new ItemMotherBoard.Component(), new Identifier(HuskysGadgetMod.MOD_ID, "wifi_card"));
        GPU = RegistryUtils.registerItem(new ItemMotherBoard.Component(), new Identifier(HuskysGadgetMod.MOD_ID, "gpu"));

        CAMERA = RegistryUtils.registerItem(new BaseItem(), new Identifier(HuskysGadgetMod.MOD_ID, "camera"));
        CD = RegistryUtils.registerItem(new BaseItem(), new Identifier(HuskysGadgetMod.MOD_ID, "cd"));
        DVD = RegistryUtils.registerItem(new BaseItem(), new Identifier(HuskysGadgetMod.MOD_ID, "dvd"));

        for (DyeColor color : DyeColor.values()) {
            FLASH_DRIVES[color.getId()] = RegistryUtils.registerItem(new BaseItem(), new Identifier(HuskysGadgetMod.MOD_ID, color.getName().toLowerCase() + "_flash_drive"));
        }

        ID_CARD = RegistryUtils.registerItem(new BaseItem(), new Identifier(HuskysGadgetMod.MOD_ID, "id_card"));
        WHITE_WIIU_GAMEPAD = RegistryUtils.registerItem(new BaseItem(), new Identifier(HuskysGadgetMod.MOD_ID, "white_wiiu_gamepad"));
        BLACK_WIIU_GAMEPAD = RegistryUtils.registerItem(new BaseItem(), new Identifier(HuskysGadgetMod.MOD_ID, "black_wiiu_gamepad"));

        for (EnumPhoneColours phoneColours : EnumPhoneColours.values()) {
            PIXEL_PHONES[phoneColours.getID()] = RegistryUtils.registerItem(new ItemPixelPhone(), new Identifier(HuskysGadgetMod.MOD_ID, phoneColours.asString().toLowerCase() + "_pixel_phone"));
            PIXEL_TABS[phoneColours.getID()] = RegistryUtils.registerItem(new ItemPixelTab(), new Identifier(HuskysGadgetMod.MOD_ID, phoneColours.asString().toLowerCase() + "_pixel_tablet"));
            PIXEL_WATCHES[phoneColours.getID()] = RegistryUtils.registerItem(new ItemPixelWatch(), new Identifier(HuskysGadgetMod.MOD_ID, phoneColours.asString().toLowerCase() + "_pixel_watch"));
        }

        ETHERNET_CABLE = RegistryUtils.registerItem(new ItemEthernetCable(), new Identifier("ethernet_cable"));
        USB_CABLE = RegistryUtils.registerItem(new ItemUSBCable(), new Identifier("usb_cable"));
    }

}
