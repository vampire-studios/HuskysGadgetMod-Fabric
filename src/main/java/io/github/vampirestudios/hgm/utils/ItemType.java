//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.vampirestudios.hgm.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemType {
    private ItemStack stack;
    private boolean checkNBT;

    public ItemType(ItemStack stack) {
        this(stack, true, true);
    }

    public ItemType(ItemStack stack, boolean copy, boolean checkNBT) {
        this.stack = stack.isEmpty() ? ItemStack.EMPTY : (copy ? stack.copy() : stack);
        this.checkNBT = checkNBT;
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public boolean checkNBT() {
        return this.checkNBT;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    public int hashCode() {
        int result = 1;
        int result2 = 31 * result + this.stack.getItem().hashCode();
        if (this.checkNBT()) {
            result2 = 31 * result2 + (this.stack.getTag() != null ? this.stack.getTag().hashCode() : 0);
        }

        return result2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            ItemType other = (ItemType)obj;
            if (!this.stack.isEmpty() && !other.stack.isEmpty()) {
                if (this.stack.getItem() != other.stack.getItem()) {
                    return false;
                } else {
                    return !this.checkNBT() || ItemStack.areTagsEqual(this.stack, other.stack);
                }
            } else {
                return this.stack.isEmpty() == other.stack.isEmpty();
            }
        }
    }

    public String toString() {
        Identifier rl;
        if (this.checkNBT()) {
            rl = Registry.ITEM.getId(this.stack.getItem());
            return rl.toString() + " " + this.stack.getTag();
        } else {
            rl = Registry.ITEM.getId(this.stack.getItem());
            return rl.toString();
        }
    }
}
