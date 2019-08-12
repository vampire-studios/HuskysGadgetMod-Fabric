package io.github.vampirestudios.hgm.block.entity;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.api.print.IPrint;
import io.github.vampirestudios.hgm.block.PrinterBlock;
import io.github.vampirestudios.hgm.init.HGMSounds;
import io.github.vampirestudios.hgm.init.HGMBlockEntities;
import io.github.vampirestudios.hgm.utils.CollisionHelper;
import io.github.vampirestudios.hgm.utils.Constants;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.math.Direction;

import java.util.ArrayDeque;
import java.util.Deque;

public class PrinterBlockEntity extends NetworkDeviceBlockEntity.Colored {
    private State state = State.IDLE;
    /**
     * The ItemStacks that hold the items currently being used in the furnace
     */
    private DefaultedList<ItemStack> printerItemStacks = DefaultedList.ofSize(3, ItemStack.EMPTY);
    /**
     * The number of ticks that the furnace will keep burning
     */
    private int furnaceBurnTime;
    /**
     * The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for
     */
    private int printTime;
    private int totalPrintTime;
    private int inkLevels;
    private String furnaceCustomName;
    private Deque<IPrint> printQueue = new ArrayDeque<>();
    private IPrint currentPrint;
    private int remainingPrintTime;
    private int paperCount = 0;

    public PrinterBlockEntity() {
        super(HGMBlockEntities.PRINTERS);
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory() {
        return this.printerItemStacks.size();
    }

    public boolean isEmpty() {
        for (ItemStack itemstack : this.printerItemStacks) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the stack in the given slot.
     */
    public ItemStack getStackInSlot(int index) {
        return this.printerItemStacks.get(index);
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     */
    public ItemStack decrStackSize(int index, int count) {
        return Inventories.splitStack(this.printerItemStacks, index, count);
    }

    /**
     * Removes a stack from the given slot and returns it.
     */
    public ItemStack removeStackFromSlot(int index) {
        return Inventories.removeStack(this.printerItemStacks, index);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = this.printerItemStacks.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areEqualIgnoreDamage(stack, itemstack);
        this.printerItemStacks.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index == 0 && !flag) {
            this.totalPrintTime = this.getTotalPrintTime();
            this.printTime = 0;
            this.markDirty();
        }
    }

    /**
     * Get the name of this object. For players this returns their username
     */
    public String getName() {
        return this.hasCustomName() ? this.furnaceCustomName : "container.printer";
    }

    /**
     * Returns true if this thing is named
     */
    public boolean hasCustomName() {
        return this.furnaceCustomName != null && !this.furnaceCustomName.isEmpty();
    }

    public void setCustomInventoryName(String p_145951_1_) {
        this.furnaceCustomName = p_145951_1_;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
     */
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void tick() {
        if (!world.isClient) {
            if (remainingPrintTime > 0) {
                if (remainingPrintTime % 20 == 0 || state == State.LOADING_PAPER) {
                    pipeline.putInt("remainingPrintTime", remainingPrintTime);
                    sync();
                    if (remainingPrintTime != 0 && state == State.PRINTING) {
                        world.playSound(null, pos, HGMSounds.PRINTER_PRINTING, SoundCategory.BLOCKS, 0.5F, 1.0F);
                    }
                }
                remainingPrintTime--;
            } else {
                setState(state.next());
            }
        }

        if (state == State.IDLE && remainingPrintTime == 0 && currentPrint != null) {
            if (!world.isClient) {
                BlockState state = world.getBlockState(pos);
                double[] fixedPosition = CollisionHelper.adjustValues(state.get(PrinterBlock.FACING), 0.15, 0.5, 0.15, 0.5);
                ItemEntity entity = new ItemEntity(world, pos.getX() + fixedPosition[0], pos.getY() + 0.0625, pos.getZ() + fixedPosition[1], IPrint.generateItem(currentPrint));
                entity.setPosition(0, 0, 0);
                world.spawnEntity(entity);
            }
            currentPrint = null;
        }

        if (state == State.IDLE && currentPrint == null && !printQueue.isEmpty() && paperCount > 0) {
            print(printQueue.poll());
        }
    }

    @Override
    public String getDeviceName() {
        return "Printer";
    }

    @Override
    public void fromTag(CompoundTag compound) {
        super.fromTag(compound);
        if (compound.containsKey("currentPrint", Constants.NBT.TAG_COMPOUND)) {
            currentPrint = IPrint.loadFromTag(compound.getCompound("currentPrint"));
        }
        if (compound.containsKey("totalPrintTime", Constants.NBT.TAG_INT)) {
            totalPrintTime = compound.getInt("totalPrintTime");
        }
        if (compound.containsKey("remainingPrintTime", Constants.NBT.TAG_INT)) {
            remainingPrintTime = compound.getInt("remainingPrintTime");
        }
        if (compound.containsKey("state", Constants.NBT.TAG_INT)) {
            state = State.values()[compound.getInt("state")];
        }
        if (compound.containsKey("paperCount", Constants.NBT.TAG_INT)) {
            paperCount = compound.getInt("paperCount");
        }
        if (compound.containsKey("queue", Constants.NBT.TAG_LIST)) {
            printQueue.clear();
            ListTag queue = compound.getList("queue", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < queue.size(); i++) {
                IPrint print = IPrint.loadFromTag(queue.getCompoundTag(i));
                printQueue.offer(print);
            }
        }
        this.printerItemStacks = DefaultedList.ofSize(this.getSizeInventory(), ItemStack.EMPTY);
        Inventories.fromTag(compound, this.printerItemStacks);
        this.printTime = compound.getInt("printTime");
        this.totalPrintTime = compound.getInt("printTimeTotal");
        this.inkLevels = compound.getInt("inkLevels");

        if (compound.containsKey("CustomName", 8)) {
            this.furnaceCustomName = compound.getString("CustomName");
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag compound) {
        super.toTag(compound);
        compound.putInt("totalPrintTime", totalPrintTime);
        compound.putInt("remainingPrintTime", remainingPrintTime);
        compound.putInt("state", state.ordinal());
        compound.putInt("paperCount", paperCount);
        if (currentPrint != null) {
            compound.put("currentPrint", IPrint.writeToTag(currentPrint));
        }
        if (!printQueue.isEmpty()) {
            ListTag queue = new ListTag();
            printQueue.forEach(print -> queue.add(IPrint.writeToTag(print)));
            compound.put("queue", queue);
        }
        compound.putInt("printTime", (short) this.printTime);
        compound.putInt("inkLevels", (short) this.inkLevels);
        Inventories.toTag(compound, this.printerItemStacks);

        if (this.hasCustomName()) {
            compound.putString("CustomName", this.furnaceCustomName);
        }
        return compound;
    }

    @Override
    public CompoundTag writeSyncTag() {
        CompoundTag tag = super.writeSyncTag();
        tag.putInt("paperCount", paperCount);
        return tag;
    }

    public void setState(State newState) {
        if (newState == null)
            return;

        state = newState;
        if (state == State.PRINTING) {
            if (HuskysGadgetMod.config.printerSettings.overridePrintSpeed) {
                remainingPrintTime = HuskysGadgetMod.config.printerSettings.customPrintSpeed * 20;
            } else {
                remainingPrintTime = currentPrint.speed() * 20;
            }
        } else {
            remainingPrintTime = state.animationTime;
        }
        totalPrintTime = remainingPrintTime;

        pipeline.putInt("state", state.ordinal());
        pipeline.putInt("totalPrintTime", totalPrintTime);
        pipeline.putInt("remainingPrintTime", remainingPrintTime);
        sync();
    }

    public void addToQueue(IPrint print) {
        printQueue.offer(print);
    }

    private void print(IPrint print) {
        world.playSound(null, pos, HGMSounds.PRINTER_LOADING_PAPER, SoundCategory.BLOCKS, 0.5F, 1.0F);

        setState(State.LOADING_PAPER);
        currentPrint = print;
        paperCount--;

        pipeline.putInt("paperCount", paperCount);
        pipeline.put("currentPrint", IPrint.writeToTag(currentPrint));
        sync();
    }

    public boolean isLoading() {
        return state == State.LOADING_PAPER;
    }

    public boolean isPrinting() {
        return state == State.PRINTING;
    }

    public int getTotalPrintTime() {
        return totalPrintTime;
    }

    public int getRemainingPrintTime() {
        return remainingPrintTime;
    }

    public boolean addPaper(ItemStack stack, boolean addAll) {
        if (!stack.isEmpty() && stack.getItem() == Items.PAPER && paperCount < HuskysGadgetMod.config.printerSettings.maxPaperCount) {
            if (!addAll) {
                paperCount++;
                stack.decrement(1);
            } else {
                paperCount += stack.getCount();
                stack.setCount(Math.max(0, paperCount - 64));
                paperCount = Math.min(64, paperCount);
            }
            pipeline.putInt("paperCount", paperCount);
            sync();
            world.playSound(null, pos, SoundEvents.ENTITY_ITEM_FRAME_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return true;
        }
        return false;
    }

    public boolean hasPaper() {
        return paperCount > 0;
    }

    public int getPaperCount() {
        return paperCount;
    }

    public IPrint getPrint() {
        return currentPrint;
    }

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     */
    public boolean isUsableByPlayer(PlayerEntity player) {
        if (this.world.getBlockEntity(this.pos) != this) {
            return false;
        } else {
            return player.squaredDistanceTo((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    public void openInventory(PlayerEntity player) {
    }

    public void closeInventory(PlayerEntity player) {
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side.
     */
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        if (direction == Direction.DOWN && index == 1) {
            Item item = stack.getItem();

            return item == Items.WATER_BUCKET || item == Items.BUCKET;
        }

        return true;
    }

    public String getGuiID() {
        return "hgm:printer";
    }

    public int getField(int id) {
        switch (id) {
            case 0:
                return this.printTime;
            case 1:
                return this.totalPrintTime;
            case 2:
                return this.inkLevels;
            default:
                return 0;
        }
    }

    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.printTime = value;
                break;
            case 1:
                this.totalPrintTime = value;
                break;
            case 2:
                this.inkLevels = value;
        }
    }

    public int getFieldCount() {
        return 4;
    }

    public void clear() {
        this.printerItemStacks.clear();
    }

    public enum State {
        LOADING_PAPER(30), PRINTING(0), IDLE(0);

        final int animationTime;

        State(int time) {
            this.animationTime = time;
        }

        public State next() {
            if (ordinal() + 1 >= values().length)
                return null;
            return values()[ordinal() + 1];
        }
    }
}