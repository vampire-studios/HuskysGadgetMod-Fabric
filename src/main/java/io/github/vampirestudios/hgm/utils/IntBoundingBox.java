//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.vampirestudios.hgm.utils;

import net.minecraft.nbt.IntArrayTag;
import net.minecraft.util.math.MutableIntBoundingBox;
import net.minecraft.util.math.Vec3i;

public class IntBoundingBox {
    public final int minX;
    public final int minY;
    public final int minZ;
    public final int maxX;
    public final int maxY;
    public final int maxZ;

    public IntBoundingBox(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public boolean containsPos(Vec3i pos) {
        return pos.getX() >= this.minX && pos.getX() <= this.maxX && pos.getZ() >= this.minZ && pos.getZ() <= this.maxZ && pos.getY() >= this.minY && pos.getY() <= this.maxY;
    }

    public boolean intersects(IntBoundingBox box) {
        return this.maxX >= box.minX && this.minX <= box.maxX && this.maxZ >= box.minZ && this.minZ <= box.maxZ && this.maxY >= box.minY && this.minY <= box.maxY;
    }

    public MutableIntBoundingBox toVanillaBox() {
        return new MutableIntBoundingBox(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
    }

    public IntArrayTag toNBTIntArray() {
        return new IntArrayTag(new int[]{this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ});
    }

    public static IntBoundingBox fromVanillaBox(MutableIntBoundingBox box) {
        return createProper(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
    }

    public static IntBoundingBox createProper(int x1, int y1, int z1, int x2, int y2, int z2) {
        return new IntBoundingBox(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2), Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));
    }

    public static IntBoundingBox fromArray(int[] coords) {
        return coords.length == 6 ? new IntBoundingBox(coords[0], coords[1], coords[2], coords[3], coords[4], coords[5]) : new IntBoundingBox(0, 0, 0, 0, 0, 0);
    }

    public int hashCode() {
        int result = 1;
        int result1 = 31 * result + this.maxX;
        result1 = 31 * result1 + this.maxY;
        result1 = 31 * result1 + this.maxZ;
        result1 = 31 * result1 + this.minX;
        result1 = 31 * result1 + this.minY;
        result1 = 31 * result1 + this.minZ;
        return result1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && this.getClass() == obj.getClass()) {
            IntBoundingBox other = (IntBoundingBox)obj;
            return this.maxX == other.maxX && this.maxY == other.maxY && this.maxZ == other.maxZ && this.minX == other.minX && this.minY == other.minY && this.minZ == other.minZ;
        } else {
            return false;
        }
    }
}
