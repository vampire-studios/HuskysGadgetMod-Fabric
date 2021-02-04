package io.github.vampirestudios.hgm.init;

import io.github.vampirestudios.hgm.enums.EnumPhoneColours;
import io.github.vampirestudios.hgm.item.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.DyeColor;

public class HGMItems {

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
    public static final Item GAME_BOY;
    public static final Item DS_LITE;
    public static final Item WHITE_SWITCH;
    public static final Item BLACK_SWITCH;

    public static final Item[] PIXEL_PHONES = new Item[3];
    public static final Item[] PIXEL_TABS = new Item[3];
    public static final Item[] PIXEL_WATCHES = new Item[3];

    public static final Item USB_CABLE;
    public static final Item ETHERNET_CABLE;

    static {
        EASTER_EGG_ITEM = io.github.vampirestudios.hgm_rewrite.HuskysGadgetMod.REGISTRY_HELPER.registerItem(new Item(new Item.Settings().food(EASTER_EGG)), "easter_egg_item");
        MOTHERBOARD = io.github.vampirestudios.hgm_rewrite.HuskysGadgetMod.REGISTRY_HELPER.registerItem(new MotherBoardItem(), "mother_board");
        CPU = io.github.vampirestudios.hgm_rewrite.HuskysGadgetMod.REGISTRY_HELPER.registerItem(new MotherBoardItem.Component(), "cpu");
        RAM_STICKS = io.github.vampirestudios.hgm_rewrite.HuskysGadgetMod.REGISTRY_HELPER.registerItem(new MotherBoardItem.Component(), "ram_stick");
        WIFI_CARD = io.github.vampirestudios.hgm_rewrite.HuskysGadgetMod.REGISTRY_HELPER.registerItem(new MotherBoardItem.Component(), "wifi_card");
        GPU = io.github.vampirestudios.hgm_rewrite.HuskysGadgetMod.REGISTRY_HELPER.registerItem(new MotherBoardItem.Component(), "gpu");

        CAMERA = io.github.vampirestudios.hgm_rewrite.HuskysGadgetMod.REGISTRY_HELPER.registerItem(new BaseItem(), "camera");
        CD = io.github.vampirestudios.hgm_rewrite.HuskysGadgetMod.REGISTRY_HELPER.registerItem(new BaseItem(), "cd");
        DVD = io.github.vampirestudios.hgm_rewrite.HuskysGadgetMod.REGISTRY_HELPER.registerItem(new BaseItem(), "dvd");

        for (DyeColor color : DyeColor.values()) {
            FLASH_DRIVES[color.getId()] = io.github.vampirestudios.hgm_rewrite.HuskysGadgetMod.REGISTRY_HELPER.registerItem(new ColoredItem(color), color.getName().toLowerCase() + "_flash_drive");
        }

        ID_CARD = io.github.vampirestudios.hgm_rewrite.HuskysGadgetMod.REGISTRY_HELPER.registerItem(new BaseItem(), "id_card");
        WHITE_WIIU_GAMEPAD = io.github.vampirestudios.hgm_rewrite.HuskysGadgetMod.REGISTRY_HELPER.registerItem(new BaseItem(), "white_wiiu_gamepad");
        BLACK_WIIU_GAMEPAD = io.github.vampirestudios.hgm_rewrite.HuskysGadgetMod.REGISTRY_HELPER.registerItem(new BaseItem(), "black_wiiu_gamepad");
        GAME_BOY = io.github.vampirestudios.hgm_rewrite.HuskysGadgetMod.REGISTRY_HELPER.registerItem(new BaseItem(), "game_boy");
        DS_LITE = io.github.vampirestudios.hgm_rewrite.HuskysGadgetMod.REGISTRY_HELPER.registerItem(new BaseItem(), "ds_lite");
        WHITE_SWITCH = io.github.vampirestudios.hgm_rewrite.HuskysGadgetMod.REGISTRY_HELPER.registerItem(new BaseItem(), "white_switch");
        BLACK_SWITCH = io.github.vampirestudios.hgm_rewrite.HuskysGadgetMod.REGISTRY_HELPER.registerItem(new BaseItem(), "black_switch");

        for (EnumPhoneColours phoneColours : EnumPhoneColours.values()) {
            PIXEL_PHONES[phoneColours.getID()] = io.github.vampirestudios.hgm_rewrite.HuskysGadgetMod.REGISTRY_HELPER.registerItem(new PixelPhoneItem(), phoneColours.asString().toLowerCase() + "_pixel_phone");
            PIXEL_TABS[phoneColours.getID()] = io.github.vampirestudios.hgm_rewrite.HuskysGadgetMod.REGISTRY_HELPER.registerItem(new PixelTabItem(), phoneColours.asString().toLowerCase() + "_pixel_tablet");
            PIXEL_WATCHES[phoneColours.getID()] = io.github.vampirestudios.hgm_rewrite.HuskysGadgetMod.REGISTRY_HELPER.registerItem(new PixelWatchItem(), phoneColours.asString().toLowerCase() + "_pixel_watch");
        }

        ETHERNET_CABLE = io.github.vampirestudios.hgm_rewrite.HuskysGadgetMod.REGISTRY_HELPER.registerItem(new EthernetCableItem(), "ethernet_cable");
        USB_CABLE = io.github.vampirestudios.hgm_rewrite.HuskysGadgetMod.REGISTRY_HELPER.registerItem(new USBCableItem(), "usb_cable");
    }

}
