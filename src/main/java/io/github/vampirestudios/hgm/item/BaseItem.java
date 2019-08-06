package io.github.vampirestudios.hgm.item;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import net.minecraft.item.Item;

public class BaseItem extends Item {
    public BaseItem() {
        super(new Settings().group(HuskysGadgetMod.DEVICE_ITEMS));
    }

    public BaseItem(Settings properties) {
        super(properties.group(HuskysGadgetMod.DEVICE_ITEMS));
    }
}
