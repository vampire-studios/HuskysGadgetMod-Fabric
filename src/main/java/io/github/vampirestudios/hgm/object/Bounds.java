package io.github.vampirestudios.hgm.object;

import io.github.vampirestudios.hgm.utils.CollisionHelper;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;

public class Bounds {
    public double x1, y1, z1;
    public double x2, y2, z2;

    /**
     * Will divide every parameter by 16
     *
     * @param x1 the X point where it starts
     * @param y1 the Y point where it starts
     * @param z1 the Z point where it starts
     * @param x2 the X point where it ends
     * @param y2 the Y point where it ends
     * @param z2 the Z point where it ends
     */
    public Bounds(int x1, int y1, int z1, int x2, int y2, int z2) {
        this(x1 / 16.0, y1 / 16.0, z1 / 16.0, x2 / 16.0, y2 / 16.0, z2 / 16.0);
    }

    public Bounds(double x1, double y1, double z1, double x2, double y2, double z2) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
    }

    public Box toBox() {
        return new Box(x1, y1, z1, x2, y2, z2);
    }

    public Box getRotation(Direction facing) {
        return CollisionHelper.getBlockBounds(facing, this);
    }

    public Box[] getRotatedBounds() {
        Box boundsNorth = CollisionHelper.getBlockBounds(Direction.NORTH, this);
        Box boundsEast = CollisionHelper.getBlockBounds(Direction.EAST, this);
        Box boundsSouth = CollisionHelper.getBlockBounds(Direction.SOUTH, this);
        Box boundsWest = CollisionHelper.getBlockBounds(Direction.WEST, this);
        return new Box[]{boundsSouth, boundsWest, boundsNorth, boundsEast};
    }


}
