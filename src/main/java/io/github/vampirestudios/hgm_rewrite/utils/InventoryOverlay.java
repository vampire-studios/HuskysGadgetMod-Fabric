//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.vampirestudios.hgm_rewrite.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import fi.dy.masa.malilib.gui.GuiBase;
import io.github.vampirestudios.hgm.utils.RenderingUtils;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext.Default;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;

public class InventoryOverlay {
    public static final Identifier TEXTURE_BREWING_STAND = new Identifier("textures/gui/container/brewing_stand.png");
    public static final Identifier TEXTURE_DISPENSER = new Identifier("textures/gui/container/dispenser.png");
    public static final Identifier TEXTURE_DOUBLE_CHEST = new Identifier("textures/gui/container/generic_54.png");
    public static final Identifier TEXTURE_FURNACE = new Identifier("textures/gui/container/furnace.png");
    public static final Identifier TEXTURE_HOPPER = new Identifier("textures/gui/container/hopper.png");
    public static final Identifier TEXTURE_PLAYER_INV = new Identifier("textures/gui/container/hopper.png");
    public static final Identifier TEXTURE_SINGLE_CHEST = new Identifier("textures/gui/container/shulker_box.png");
    public static final InventoryOverlay.InventoryProperties INV_PROPS_TEMP = new InventoryOverlay.InventoryProperties();
    private static final String[] EMPTY_SLOT_TEXTURES = new String[]{"item/empty_armor_slot_boots", "item/empty_armor_slot_leggings", "item/empty_armor_slot_chestplate", "item/empty_armor_slot_helmet"};
    private static final EquipmentSlot[] VALID_EQUIPMENT_SLOTS;

    public InventoryOverlay() {
    }

    public static void renderInventoryBackground(InventoryOverlay.InventoryRenderType type, int x, int y, int slotsPerRow, int totalSlots, MinecraftClient mc) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBufferBuilder();
        buffer.begin(7, VertexFormats.POSITION_UV);
        if (type == InventoryOverlay.InventoryRenderType.FURNACE) {
            RenderingUtils.bindTexture(TEXTURE_FURNACE);
            RenderingUtils.drawTexturedRectBatched(x, y, 0, 0, 4, 64, buffer);
            RenderingUtils.drawTexturedRectBatched(x + 4, y, 84, 0, 92, 4, buffer);
            RenderingUtils.drawTexturedRectBatched(x, y + 64, 0, 162, 92, 4, buffer);
            RenderingUtils.drawTexturedRectBatched(x + 92, y + 4, 172, 102, 4, 64, buffer);
            RenderingUtils.drawTexturedRectBatched(x + 4, y + 4, 52, 13, 88, 60, buffer);
        } else if (type == InventoryOverlay.InventoryRenderType.BREWING_STAND) {
            RenderingUtils.bindTexture(TEXTURE_BREWING_STAND);
            RenderingUtils.drawTexturedRectBatched(x, y, 0, 0, 4, 68, buffer);
            RenderingUtils.drawTexturedRectBatched(x + 4, y, 63, 0, 113, 4, buffer);
            RenderingUtils.drawTexturedRectBatched(x, y + 68, 0, 162, 113, 4, buffer);
            RenderingUtils.drawTexturedRectBatched(x + 113, y + 4, 172, 98, 4, 68, buffer);
            RenderingUtils.drawTexturedRectBatched(x + 4, y + 4, 13, 13, 109, 64, buffer);
        } else if (type == InventoryOverlay.InventoryRenderType.DISPENSER) {
            RenderingUtils.bindTexture(TEXTURE_DISPENSER);
            RenderingUtils.drawTexturedRectBatched(x, y, 0, 0, 7, 61, buffer);
            RenderingUtils.drawTexturedRectBatched(x + 7, y, 115, 0, 61, 7, buffer);
            RenderingUtils.drawTexturedRectBatched(x, y + 61, 0, 159, 61, 7, buffer);
            RenderingUtils.drawTexturedRectBatched(x + 61, y + 7, 169, 105, 7, 61, buffer);
            RenderingUtils.drawTexturedRectBatched(x + 7, y + 7, 61, 16, 54, 54, buffer);
        } else if (type == InventoryOverlay.InventoryRenderType.HOPPER) {
            RenderingUtils.bindTexture(TEXTURE_HOPPER);
            RenderingUtils.drawTexturedRectBatched(x, y, 0, 0, 7, 25, buffer);
            RenderingUtils.drawTexturedRectBatched(x + 7, y, 79, 0, 97, 7, buffer);
            RenderingUtils.drawTexturedRectBatched(x, y + 25, 0, 126, 97, 7, buffer);
            RenderingUtils.drawTexturedRectBatched(x + 97, y + 7, 169, 108, 7, 25, buffer);
            RenderingUtils.drawTexturedRectBatched(x + 7, y + 7, 43, 19, 90, 18, buffer);
        } else if (type == InventoryOverlay.InventoryRenderType.VILLAGER) {
            RenderingUtils.bindTexture(TEXTURE_DOUBLE_CHEST);
            RenderingUtils.drawTexturedRectBatched(x, y, 0, 0, 7, 79, buffer);
            RenderingUtils.drawTexturedRectBatched(x + 7, y, 133, 0, 43, 7, buffer);
            RenderingUtils.drawTexturedRectBatched(x, y + 79, 0, 215, 43, 7, buffer);
            RenderingUtils.drawTexturedRectBatched(x + 43, y + 7, 169, 143, 7, 79, buffer);
            RenderingUtils.drawTexturedRectBatched(x + 7, y + 7, 7, 17, 36, 72, buffer);
        } else if (type == InventoryOverlay.InventoryRenderType.FIXED_27) {
            renderInventoryBackground27(x, y, buffer, mc);
        } else if (type == InventoryOverlay.InventoryRenderType.FIXED_54) {
            renderInventoryBackground54(x, y, buffer, mc);
        } else {
            RenderingUtils.bindTexture(TEXTURE_DOUBLE_CHEST);
            int rows = (int)Math.ceil((double)totalSlots / (double)slotsPerRow);
            int bgw = Math.min(totalSlots, slotsPerRow) * 18 + 7;
            int bgh = rows * 18 + 7;
            RenderingUtils.drawTexturedRectBatched(x, y, 0, 0, 7, bgh, buffer);
            RenderingUtils.drawTexturedRectBatched(x + 7, y, 176 - bgw, 0, bgw, 7, buffer);
            RenderingUtils.drawTexturedRectBatched(x, y + bgh, 0, 215, bgw, 7, buffer);
            RenderingUtils.drawTexturedRectBatched(x + bgw, y + 7, 169, 222 - bgh, 7, bgh, buffer);

            for(int row = 0; row < rows; ++row) {
                int rowLen = MathHelper.clamp(totalSlots - row * slotsPerRow, 1, slotsPerRow);
                RenderingUtils.drawTexturedRectBatched(x + 7, y + row * 18 + 7, 7, 17, rowLen * 18, 18, buffer);
                if (rows > 1 && rowLen < slotsPerRow) {
                    RenderingUtils.drawTexturedRectBatched(x + rowLen * 18 + 7, y + row * 18 + 7, 7, 3, (slotsPerRow - rowLen) * 18, 9, buffer);
                    RenderingUtils.drawTexturedRectBatched(x + rowLen * 18 + 7, y + row * 18 + 16, 7, 3, (slotsPerRow - rowLen) * 18, 9, buffer);
                }
            }
        }

        tessellator.draw();
    }

    public static void renderInventoryBackground27(int x, int y, BufferBuilder buffer, MinecraftClient mc) {
        RenderingUtils.bindTexture(TEXTURE_SINGLE_CHEST);
        RenderingUtils.drawTexturedRectBatched(x, y, 0, 0, 7, 61, buffer);
        RenderingUtils.drawTexturedRectBatched(x + 7, y, 7, 0, 169, 7, buffer);
        RenderingUtils.drawTexturedRectBatched(x, y + 61, 0, 159, 169, 7, buffer);
        RenderingUtils.drawTexturedRectBatched(x + 169, y + 7, 169, 105, 7, 61, buffer);
        RenderingUtils.drawTexturedRectBatched(x + 7, y + 7, 7, 17, 162, 54, buffer);
    }

    public static void renderInventoryBackground54(int x, int y, BufferBuilder buffer, MinecraftClient mc) {
        RenderingUtils.bindTexture(TEXTURE_DOUBLE_CHEST);
        RenderingUtils.drawTexturedRectBatched(x, y, 0, 0, 7, 115, buffer);
        RenderingUtils.drawTexturedRectBatched(x + 7, y, 7, 0, 169, 7, buffer);
        RenderingUtils.drawTexturedRectBatched(x, y + 115, 0, 215, 169, 7, buffer);
        RenderingUtils.drawTexturedRectBatched(x + 169, y + 7, 169, 107, 7, 115, buffer);
        RenderingUtils.drawTexturedRectBatched(x + 7, y + 7, 7, 17, 162, 108, buffer);
    }

    public static void renderEquipmentOverlayBackground(int x, int y, LivingEntity entity) {
        RenderingUtils.color(1.0F, 1.0F, 1.0F, 1.0F);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBufferBuilder();
        buffer.begin(7, VertexFormats.POSITION_UV);
        RenderingUtils.bindTexture(TEXTURE_DISPENSER);
        RenderingUtils.drawTexturedRectBatched(x, y, 0, 0, 50, 83, buffer);
        RenderingUtils.drawTexturedRectBatched(x + 50, y, 173, 0, 3, 83, buffer);
        RenderingUtils.drawTexturedRectBatched(x, y + 83, 0, 163, 50, 3, buffer);
        RenderingUtils.drawTexturedRectBatched(x + 50, y + 83, 173, 163, 3, 3, buffer);
        int i = 0;
        int xOff = 7;

        int yOff;
        for(yOff = 7; i < 4; yOff += 18) {
            RenderingUtils.drawTexturedRectBatched(x + xOff, y + yOff, 61, 16, 18, 18, buffer);
            ++i;
        }

        RenderingUtils.drawTexturedRectBatched(x + 28, y + 36 + 7, 61, 16, 18, 18, buffer);
        RenderingUtils.drawTexturedRectBatched(x + 28, y + 54 + 7, 61, 16, 18, 18, buffer);
        tessellator.draw();
        RenderingUtils.bindTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEX);
        if (entity.getEquippedStack(EquipmentSlot.OFFHAND).isEmpty()) {
            String texture = "minecraft:item/empty_armor_slot_shield";
            RenderingUtils.renderSprite(x + 28 + 1, y + 54 + 7 + 1, 16, 16, texture);
        }

        i = 0;
        xOff = 7;

        for(yOff = 7; i < 4; yOff += 18) {
            EquipmentSlot eqSlot = VALID_EQUIPMENT_SLOTS[i];
            if (entity.getEquippedStack(eqSlot).isEmpty()) {
                String texture = EMPTY_SLOT_TEXTURES[eqSlot.getEntitySlotId()];
                RenderingUtils.renderSprite(x + xOff + 1, y + yOff + 1, 16, 16, texture);
            }

            ++i;
        }

    }

    public static InventoryOverlay.InventoryRenderType getInventoryType(Inventory inv) {
        if (inv instanceof ShulkerBoxBlockEntity) {
            return InventoryOverlay.InventoryRenderType.FIXED_27;
        } else if (inv instanceof DoubleInventory) {
            return InventoryOverlay.InventoryRenderType.FIXED_54;
        } else if (inv instanceof FurnaceBlockEntity) {
            return InventoryOverlay.InventoryRenderType.FURNACE;
        } else if (inv instanceof BrewingStandBlockEntity) {
            return InventoryOverlay.InventoryRenderType.BREWING_STAND;
        } else if (inv instanceof DispenserBlockEntity) {
            return InventoryOverlay.InventoryRenderType.DISPENSER;
        } else if (inv instanceof HopperBlockEntity) {
            return InventoryOverlay.InventoryRenderType.HOPPER;
        } else {
            return inv.getClass() == BasicInventory.class ? InventoryOverlay.InventoryRenderType.HORSE : InventoryOverlay.InventoryRenderType.GENERIC;
        }
    }

    public static InventoryOverlay.InventoryRenderType getInventoryType(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof BlockItem) {
            Block block = ((BlockItem)item).getBlock();
            if (block instanceof ShulkerBoxBlock || block instanceof ChestBlock) {
                return InventoryOverlay.InventoryRenderType.FIXED_27;
            }

            if (block instanceof FurnaceBlock) {
                return InventoryOverlay.InventoryRenderType.FURNACE;
            }

            if (block instanceof DispenserBlock) {
                return InventoryOverlay.InventoryRenderType.DISPENSER;
            }

            if (block instanceof HopperBlock) {
                return InventoryOverlay.InventoryRenderType.HOPPER;
            }

            if (block instanceof BrewingStandBlock) {
                return InventoryOverlay.InventoryRenderType.BREWING_STAND;
            }
        }

        return InventoryOverlay.InventoryRenderType.GENERIC;
    }

    public static InventoryOverlay.InventoryProperties getInventoryPropsTemp(InventoryOverlay.InventoryRenderType type, int totalSlots) {
        INV_PROPS_TEMP.totalSlots = totalSlots;
        if (type == InventoryOverlay.InventoryRenderType.FURNACE) {
            INV_PROPS_TEMP.slotsPerRow = 1;
            INV_PROPS_TEMP.slotOffsetX = 0;
            INV_PROPS_TEMP.slotOffsetY = 0;
            INV_PROPS_TEMP.width = 96;
            INV_PROPS_TEMP.height = 68;
        } else if (type == InventoryOverlay.InventoryRenderType.BREWING_STAND) {
            INV_PROPS_TEMP.slotsPerRow = 9;
            INV_PROPS_TEMP.slotOffsetX = 0;
            INV_PROPS_TEMP.slotOffsetY = 0;
            INV_PROPS_TEMP.width = 127;
            INV_PROPS_TEMP.height = 72;
        } else if (type == InventoryOverlay.InventoryRenderType.DISPENSER) {
            INV_PROPS_TEMP.slotsPerRow = 3;
            INV_PROPS_TEMP.slotOffsetX = 8;
            INV_PROPS_TEMP.slotOffsetY = 8;
            INV_PROPS_TEMP.width = 68;
            INV_PROPS_TEMP.height = 68;
        } else if (type == InventoryOverlay.InventoryRenderType.HORSE) {
            INV_PROPS_TEMP.slotsPerRow = Math.max(1, totalSlots / 3);
            INV_PROPS_TEMP.slotOffsetX = 8;
            INV_PROPS_TEMP.slotOffsetY = 8;
            INV_PROPS_TEMP.width = totalSlots * 18 / 3 + 14;
            INV_PROPS_TEMP.height = 68;
        } else if (type == InventoryOverlay.InventoryRenderType.HOPPER) {
            INV_PROPS_TEMP.slotsPerRow = 5;
            INV_PROPS_TEMP.slotOffsetX = 8;
            INV_PROPS_TEMP.slotOffsetY = 8;
            INV_PROPS_TEMP.width = 105;
            INV_PROPS_TEMP.height = 32;
        } else if (type == InventoryOverlay.InventoryRenderType.VILLAGER) {
            INV_PROPS_TEMP.slotsPerRow = 2;
            INV_PROPS_TEMP.slotOffsetX = 8;
            INV_PROPS_TEMP.slotOffsetY = 8;
            INV_PROPS_TEMP.width = 50;
            INV_PROPS_TEMP.height = 86;
        } else {
            if (type == InventoryOverlay.InventoryRenderType.FIXED_27) {
                totalSlots = 27;
            } else if (type == InventoryOverlay.InventoryRenderType.FIXED_54) {
                totalSlots = 54;
            }

            INV_PROPS_TEMP.slotsPerRow = 9;
            INV_PROPS_TEMP.slotOffsetX = 8;
            INV_PROPS_TEMP.slotOffsetY = 8;
            int rows = (int)Math.ceil((double)totalSlots / (double)INV_PROPS_TEMP.slotsPerRow);
            INV_PROPS_TEMP.width = Math.min(INV_PROPS_TEMP.slotsPerRow, totalSlots) * 18 + 14;
            INV_PROPS_TEMP.height = rows * 18 + 14;
        }

        return INV_PROPS_TEMP;
    }

    public static void renderInventoryStacks(InventoryOverlay.InventoryRenderType type, Inventory inv, int startX, int startY, int slotsPerRow, int startSlot, int maxSlots, MinecraftClient mc) {
        if (type == InventoryOverlay.InventoryRenderType.FURNACE) {
            renderStackAt(inv.getInvStack(0), (float)(startX + 8), (float)(startY + 8), 1.0F, mc);
            renderStackAt(inv.getInvStack(1), (float)(startX + 8), (float)(startY + 44), 1.0F, mc);
            renderStackAt(inv.getInvStack(2), (float)(startX + 68), (float)(startY + 26), 1.0F, mc);
        } else if (type == InventoryOverlay.InventoryRenderType.BREWING_STAND) {
            renderStackAt(inv.getInvStack(0), (float)(startX + 47), (float)(startY + 42), 1.0F, mc);
            renderStackAt(inv.getInvStack(1), (float)(startX + 70), (float)(startY + 49), 1.0F, mc);
            renderStackAt(inv.getInvStack(2), (float)(startX + 93), (float)(startY + 42), 1.0F, mc);
            renderStackAt(inv.getInvStack(3), (float)(startX + 70), (float)(startY + 8), 1.0F, mc);
            renderStackAt(inv.getInvStack(4), (float)(startX + 8), (float)(startY + 8), 1.0F, mc);
        } else {
            int slots = inv.getInvSize();
            int x = startX;
            int y = startY;
            if (maxSlots < 0) {
                maxSlots = slots;
            }

            int slot = startSlot;

            for(int i = 0; slot < slots && i < maxSlots; y += 18) {
                for(int column = 0; column < slotsPerRow && slot < slots && i < maxSlots; ++i) {
                    ItemStack stack = inv.getInvStack(slot);
                    if (!stack.isEmpty()) {
                        renderStackAt(stack, (float)x, (float)y, 1.0F, mc);
                    }

                    x += 18;
                    ++column;
                    ++slot;
                }

                x = startX;
            }
        }

    }

    public static void renderEquipmentStacks(LivingEntity entity, int x, int y, MinecraftClient mc) {
        int i = 0;
        int xOff = 7;

        for(int yOff = 7; i < 4; yOff += 18) {
            EquipmentSlot eqSlot = VALID_EQUIPMENT_SLOTS[i];
            ItemStack stack = entity.getEquippedStack(eqSlot);
            if (!stack.isEmpty()) {
                renderStackAt(stack, (float)(x + xOff + 1), (float)(y + yOff + 1), 1.0F, mc);
            }

            ++i;
        }

        ItemStack stack = entity.getEquippedStack(EquipmentSlot.MAINHAND);
        if (!stack.isEmpty()) {
            renderStackAt(stack, (float)(x + 28), (float)(y + 36 + 7 + 1), 1.0F, mc);
        }

        stack = entity.getEquippedStack(EquipmentSlot.OFFHAND);
        if (!stack.isEmpty()) {
            renderStackAt(stack, (float)(x + 28), (float)(y + 54 + 7 + 1), 1.0F, mc);
        }

    }

    public static void renderItemStacks(DefaultedList<ItemStack> items, int startX, int startY, int slotsPerRow, int startSlot, int maxSlots, MinecraftClient mc) {
        int slots = items.size();
        int x = startX;
        int y = startY;
        if (maxSlots < 0) {
            maxSlots = slots;
        }

        int slot = startSlot;

        for(int i = 0; slot < slots && i < maxSlots; y += 18) {
            for(int column = 0; column < slotsPerRow && slot < slots && i < maxSlots; ++i) {
                ItemStack stack = (ItemStack)items.get(slot);
                if (!stack.isEmpty()) {
                    renderStackAt(stack, (float)x, (float)y, 1.0F, mc);
                }

                x += 18;
                ++column;
                ++slot;
            }

            x = startX;
        }

    }

    public static void renderStackAt(ItemStack stack, float x, float y, float scale, MinecraftClient mc) {
        RenderSystem.pushMatrix();
        RenderSystem.translatef(x, y, 0.0F);
        RenderSystem.scalef(scale, scale, 1.0F);
        RenderSystem.disableLighting();
        RenderingUtils.enableGuiItemLighting();
        ItemRenderer var10000 = mc.getItemRenderer();
        var10000.zOffset += 100.0F;
        mc.getItemRenderer().renderGuiItem(mc.player, stack, 0, 0);
        mc.getItemRenderer().renderGuiItemOverlay(mc.textRenderer, stack, 0, 0, (String)null);
        var10000 = mc.getItemRenderer();
        var10000.zOffset -= 100.0F;
        RenderingUtils.disableItemLighting();
        RenderSystem.popMatrix();
    }

    public static void renderStackToolTip(int x, int y, ItemStack stack, MinecraftClient mc) {
        List<Text> list = stack.getTooltip(mc.player, mc.options.advancedItemTooltips ? Default.ADVANCED : Default.NORMAL);
        List<String> lines = new ArrayList();

        for(int i = 0; i < list.size(); ++i) {
            if (i == 0) {
                lines.add(stack.getRarity().formatting + ((Text)list.get(i)).getString());
            } else {
                lines.add(GuiBase.TXT_GRAY + ((Text)list.get(i)).getString());
            }
        }

        RenderingUtils.drawHoverText(x, y, lines);
    }

    static {
        VALID_EQUIPMENT_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    }

    public static enum InventoryRenderType {
        BREWING_STAND,
        DISPENSER,
        FURNACE,
        HOPPER,
        HORSE,
        FIXED_27,
        FIXED_54,
        VILLAGER,
        GENERIC;

        private InventoryRenderType() {
        }
    }

    public static class InventoryProperties {
        public int totalSlots = 1;
        public int width = 176;
        public int height = 83;
        public int slotsPerRow = 9;
        public int slotOffsetX = 8;
        public int slotOffsetY = 8;

        public InventoryProperties() {
        }
    }
}
