package io.github.vampirestudios.hgm_rewrite.utils;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.container.Container;
import net.minecraft.container.PlayerContainer;
import net.minecraft.container.Slot;
import net.minecraft.container.SlotActionType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class InventoryUtils {
    private static final DefaultedList<ItemStack> EMPTY_LIST = DefaultedList.of();

    public InventoryUtils() {
    }

    public static boolean areStacksEqual(ItemStack stack1, ItemStack stack2) {
        return ItemStack.areItemsEqual(stack1, stack2) && ItemStack.areTagsEqual(stack1, stack2);
    }

    public static boolean areStacksEqualIgnoreDurability(ItemStack stack1, ItemStack stack2) {
        return ItemStack.areItemsEqualIgnoreDamage(stack1, stack2) && ItemStack.areTagsEqual(stack1, stack2);
    }

    public static void swapSlots(Container container, int slotNum, int hotbarSlot) {
        MinecraftClient mc = MinecraftClient.getInstance();
        mc.interactionManager.method_2906(container.syncId, slotNum, hotbarSlot, SlotActionType.SWAP, mc.player);
    }

    public static boolean isRegularInventorySlot(int slotNumber, boolean allowOffhand) {
        return slotNumber > 8 && (allowOffhand || slotNumber < 45);
    }

    public static int findEmptySlotInPlayerInventory(Container containerPlayer, boolean allowOffhand, boolean reverse) {
        int startSlot = reverse ? containerPlayer.slotList.size() - 1 : 0;
        int endSlot = reverse ? -1 : containerPlayer.slotList.size();
        int increment = reverse ? -1 : 1;

        for(int slotNum = startSlot; slotNum != endSlot; slotNum += increment) {
            Slot slot = containerPlayer.slotList.get(slotNum);
            ItemStack stackSlot = slot.getStack();
            if (stackSlot.isEmpty() && isRegularInventorySlot(slot.id, allowOffhand)) {
                return slot.id;
            }
        }

        return -1;
    }

    public static int findSlotWithItem(Container container, ItemStack stackReference, boolean reverse) {
        int startSlot = reverse ? container.slotList.size() - 1 : 0;
        int endSlot = reverse ? -1 : container.slotList.size();
        int increment = reverse ? -1 : 1;
        boolean isPlayerInv = container instanceof PlayerContainer;

        for(int slotNum = startSlot; slotNum != endSlot; slotNum += increment) {
            Slot slot = container.slotList.get(slotNum);
            if ((!isPlayerInv || isRegularInventorySlot(slot.id, false)) && areStacksEqualIgnoreDurability(slot.getStack(), stackReference)) {
                return slot.id;
            }
        }

        return -1;
    }

    public static boolean swapItemToMainHand(ItemStack stackReference, MinecraftClient mc) {
        PlayerEntity player = mc.player;
        boolean isCreative = player.abilities.creativeMode;
        if (areStacksEqual(stackReference, player.getMainHandStack())) {
            return false;
        } else if (isCreative) {
            player.inventory.addPickBlock(stackReference);
            mc.interactionManager.clickCreativeStack(player.getMainHandStack(), 36 + player.inventory.selectedSlot);
            return true;
        } else {
            int slot = findSlotWithItem(player.playerContainer, stackReference, true);
            if (slot != -1) {
                int currentHotbarSlot = player.inventory.selectedSlot;
                mc.interactionManager.method_2906(player.playerContainer.syncId, slot, currentHotbarSlot, SlotActionType.SWAP, mc.player);
                return true;
            } else {
                return false;
            }
        }
    }

    @Nullable
    public static Inventory getInventory(World world, BlockPos pos) {
        if (!world.method_22340(pos)) {
            return null;
        } else {
            BlockEntity te = world.getWorldChunk(pos).getBlockEntity(pos);
            if (te instanceof Inventory) {
                Inventory inv = (Inventory)te;
                BlockState state = world.getBlockState(pos);
                if (state.getBlock() instanceof ChestBlock && te instanceof ChestBlockEntity) {
                    ChestType type = state.get(ChestBlock.CHEST_TYPE);
                    if (type != ChestType.SINGLE) {
                        BlockPos posAdj = pos.offset(ChestBlock.getFacing(state));
                        if (world.method_22340(posAdj)) {
                            BlockState stateAdj = world.getBlockState(posAdj);
                            BlockEntity te2 = world.getWorldChunk(posAdj).getBlockEntity(posAdj);
                            if (stateAdj.getBlock() == state.getBlock() && te2 instanceof ChestBlockEntity && stateAdj.get(ChestBlock.CHEST_TYPE) != ChestType.SINGLE && stateAdj.get(ChestBlock.FACING) == state.get(ChestBlock.FACING)) {
                                Inventory invRight = type == ChestType.RIGHT ? inv : (Inventory)te2;
                                Inventory invLeft = type == ChestType.RIGHT ? (Inventory)te2 : inv;
                                inv = new DoubleInventory(invRight, invLeft);
                            }
                        }
                    }
                }

                return inv;
            } else {
                return null;
            }
        }
    }

    public static boolean shulkerBoxHasItems(ItemStack stackShulkerBox) {
        CompoundTag nbt = stackShulkerBox.getTag();
        if (nbt != null && nbt.containsKey("BlockEntityTag", 10)) {
            CompoundTag tag = nbt.getCompound("BlockEntityTag");
            if (tag.containsKey("Items", 9)) {
                ListTag tagList = tag.getList("Items", 10);
                return tagList.size() > 0;
            }
        }

        return false;
    }

    public static DefaultedList<ItemStack> getStoredItems(ItemStack stackIn) {
        CompoundTag nbt = stackIn.getTag();
        if (nbt != null && nbt.containsKey("BlockEntityTag", 10)) {
            CompoundTag tagBlockEntity = nbt.getCompound("BlockEntityTag");
            if (tagBlockEntity.containsKey("Items", 9)) {
                DefaultedList<ItemStack> items = DefaultedList.of();
                ListTag tagList = tagBlockEntity.getList("Items", 10);
                int count = tagList.size();

                for(int i = 0; i < count; ++i) {
                    ItemStack stack = ItemStack.fromTag(tagList.getCompoundTag(i));
                    if (!stack.isEmpty()) {
                        items.add(stack);
                    }
                }

                return items;
            }
        }

        return DefaultedList.of();
    }

    public static DefaultedList<ItemStack> getStoredItems(ItemStack stackIn, int slotCount) {
        CompoundTag nbt = stackIn.getTag();
        if (nbt != null && nbt.containsKey("BlockEntityTag", 10)) {
            CompoundTag tagBlockEntity = nbt.getCompound("BlockEntityTag");
            if (tagBlockEntity.containsKey("Items", 9)) {
                ListTag tagList = tagBlockEntity.getList("Items", 10);
                int count = tagList.size();
                int maxSlot = -1;
                if (slotCount <= 0) {
                    for(int i = 0; i < count; ++i) {
                        CompoundTag tag = tagList.getCompoundTag(i);
                        int slot = tag.getByte("Slot");
                        if (slot > maxSlot) {
                            maxSlot = slot;
                        }
                    }

                    slotCount = maxSlot + 1;
                }

                DefaultedList<ItemStack> items = DefaultedList.ofSize(slotCount, ItemStack.EMPTY);

                for(int i = 0; i < count; ++i) {
                    CompoundTag tag = tagList.getCompoundTag(i);
                    ItemStack stack = ItemStack.fromTag(tag);
                    int slot = tag.getByte("Slot");
                    if (slot >= 0 && slot < items.size() && !stack.isEmpty()) {
                        items.set(slot, stack);
                    }
                }

                return items;
            }
        }

        return EMPTY_LIST;
    }

    public static Object2IntOpenHashMap<ItemType> getStoredItemCounts(ItemStack stackShulkerBox) {
        Object2IntOpenHashMap<ItemType> map = new Object2IntOpenHashMap();
        DefaultedList<ItemStack> items = getStoredItems(stackShulkerBox);

        for(int slot = 0; slot < items.size(); ++slot) {
            ItemStack stack = items.get(slot);
            if (!stack.isEmpty()) {
                map.addTo(new ItemType(stack), stack.getCount());
            }
        }

        return map;
    }

    public static Object2IntOpenHashMap<ItemType> getInventoryItemCounts(Inventory inv) {
        Object2IntOpenHashMap<ItemType> map = new Object2IntOpenHashMap();
        int slots = inv.getInvSize();

        for(int slot = 0; slot < slots; ++slot) {
            ItemStack stack = inv.getInvStack(slot);
            if (!stack.isEmpty()) {
                map.addTo(new ItemType(stack, false, true), stack.getCount());
                if (stack.getItem() instanceof BlockItem && ((BlockItem)stack.getItem()).getBlock() instanceof ShulkerBoxBlock && shulkerBoxHasItems(stack)) {
                    Object2IntOpenHashMap<ItemType> boxCounts = getStoredItemCounts(stack);
                    ObjectIterator var6 = boxCounts.keySet().iterator();

                    while(var6.hasNext()) {
                        ItemType type = (ItemType)var6.next();
                        map.addTo(type, boxCounts.getInt(type));
                    }
                }
            }
        }

        return map;
    }

    public static Inventory getAsInventory(DefaultedList<ItemStack> items) {
        BasicInventory inv = new BasicInventory(items.size());

        for(int slot = 0; slot < items.size(); ++slot) {
            inv.setInvStack(slot, items.get(slot));
        }

        return inv;
    }
}
