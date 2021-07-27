package io.github.vampirestudios.hgm.item;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class USBCableItem extends BaseItem {
    public USBCableItem() {
        super();
    }

    @Override
    public void appendTooltip(ItemStack stack, World p_77624_2_, List<Text> tooltip, TooltipContext p_77624_4_) {
        if (stack.hasTag()) {
            NbtCompound tag = stack.getTag();
            if (tag != null) {
                tooltip.add(new LiteralText(Formatting.RED.toString() + Formatting.BOLD.toString() + "ID: " + Formatting.RESET.toString() + tag.getUuid("id")));
                tooltip.add(new LiteralText(Formatting.RED.toString() + Formatting.BOLD.toString() + "Device: " + Formatting.RESET.toString() + tag.getString("name")));

                BlockPos devicePos = BlockPos.fromLong(tag.getLong("pos"));
                StringBuilder builder = new StringBuilder();
                builder.append(Formatting.RED.toString() + Formatting.BOLD.toString() + "X: " + Formatting.RESET.toString() + devicePos.getX() + " ");
                builder.append(Formatting.RED.toString() + Formatting.BOLD.toString() + "Y: " + Formatting.RESET.toString() + devicePos.getY() + " ");
                builder.append(Formatting.RED.toString() + Formatting.BOLD.toString() + "Z: " + Formatting.RESET.toString() + devicePos.getZ());
                tooltip.add(new LiteralText(builder.toString()));
            }
        } else {
            if (!Screen.hasShiftDown()) {
                tooltip.add(new LiteralText(Formatting.GRAY.toString() + "Use this cable to connect"));
                tooltip.add(new LiteralText(Formatting.GRAY.toString() + "a device to either a drawing tablet or a server terminal."));
                tooltip.add(new LiteralText(Formatting.YELLOW.toString() + "Hold SHIFT for How-To"));
                return;
            }

            tooltip.add(new LiteralText(Formatting.GRAY.toString() + "Start by right clicking a"));
            tooltip.add(new LiteralText(Formatting.GRAY.toString() + "device with this cable"));
            tooltip.add(new LiteralText(Formatting.GRAY.toString() + "then right click the "));
            tooltip.add(new LiteralText(Formatting.GRAY.toString() + "either the drawing tablet or server terminal "));
            tooltip.add(new LiteralText(Formatting.GRAY.toString() + "you want to connect this device to."));
        }
        super.appendTooltip(stack, p_77624_2_, tooltip, p_77624_4_);
    }
}
