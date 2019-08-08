package io.github.vampirestudios.hgm.item;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.utils.Constants;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class MotherBoardItem extends Item {

    public MotherBoardItem() {
        super(new Settings().group(HuskysGadgetMod.DEVICE_ITEMS));
    }

    @Override
    public void appendTooltip(ItemStack itemStack_1, @Nullable World world_1, List<net.minecraft.text.Text> list_1, TooltipContext tooltipContext_1) {
        CompoundTag tag = itemStack_1.getTag();
        if (!Screen.hasShiftDown()) {
            list_1.add(new LiteralText("CPU: " + getComponentStatus(tag, "cpu")));
            list_1.add(new LiteralText("RAM: " + getComponentStatus(tag, "ram")));
            list_1.add(new LiteralText("GPU: " + getComponentStatus(tag, "gpu")));
            list_1.add(new LiteralText("WIFI: " + getComponentStatus(tag, "wifi")));
            list_1.add(new LiteralText(Formatting.YELLOW + "Hold shift for help"));
        } else {
            list_1.add(new LiteralText("To add the required components"));
            list_1.add(new LiteralText("place the motherboard and the"));
            list_1.add(new LiteralText("corresponding component into a"));
            list_1.add(new LiteralText("crafting table to combine them."));
        }
    }

    private String getComponentStatus(CompoundTag tag, String component) {
        if (tag != null && tag.containsKey("components", Constants.NBT.TAG_COMPOUND)) {
            CompoundTag components = tag.getCompound("components");
            if (components.containsKey(component, Constants.NBT.TAG_BYTE)) {
                return Formatting.GREEN + "Added";
            }
        }
        return Formatting.RED + "Missing";
    }

    public static class Component extends ComponentItem {
        public Component() {
            super();
        }
    }

    public static class ColoredComponent extends ColoredComponentItem {
        public ColoredComponent(DyeColor color) {
            super(color);
        }
    }

}
