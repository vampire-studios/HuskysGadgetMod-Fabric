package io.github.vampirestudios.hgm.item;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ComponentItem extends Item {

    public ComponentItem() {
        super(new Settings().group(HuskysGadgetMod.DEVICE_ITEMS));
    }

    public static Item getComponentFromName(String name) {
        return Registry.ITEM.get(new Identifier(HuskysGadgetMod.MOD_ID, name));
    }

}
