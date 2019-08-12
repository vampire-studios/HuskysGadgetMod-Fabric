//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.vampirestudios.hgm.utils;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.math.Direction.AxisDirection;

public class PositionUtils {
    public PositionUtils() {
    }

    public static Vec3d modifyValue(PositionUtils.CoordinateType type, Vec3d valueIn, double amount) {
        switch(type) {
        case X:
            return new Vec3d(valueIn.x + amount, valueIn.y, valueIn.z);
        case Y:
            return new Vec3d(valueIn.x, valueIn.y + amount, valueIn.z);
        case Z:
            return new Vec3d(valueIn.x, valueIn.y, valueIn.z + amount);
        default:
            return valueIn;
        }
    }

    public static BlockPos modifyValue(PositionUtils.CoordinateType type, BlockPos valueIn, int amount) {
        switch(type) {
        case X:
            return new BlockPos(valueIn.getX() + amount, valueIn.getY(), valueIn.getZ());
        case Y:
            return new BlockPos(valueIn.getX(), valueIn.getY() + amount, valueIn.getZ());
        case Z:
            return new BlockPos(valueIn.getX(), valueIn.getZ(), valueIn.getZ() + amount);
        default:
            return valueIn;
        }
    }

    public static Vec3d setValue(PositionUtils.CoordinateType type, Vec3d valueIn, double newValue) {
        switch(type) {
        case X:
            return new Vec3d(newValue, valueIn.y, valueIn.z);
        case Y:
            return new Vec3d(valueIn.x, newValue, valueIn.z);
        case Z:
            return new Vec3d(valueIn.x, valueIn.y, newValue);
        default:
            return valueIn;
        }
    }

    public static BlockPos setValue(PositionUtils.CoordinateType type, BlockPos valueIn, int newValue) {
        switch(type) {
        case X:
            return new BlockPos(newValue, valueIn.getY(), valueIn.getZ());
        case Y:
            return new BlockPos(valueIn.getX(), newValue, valueIn.getZ());
        case Z:
            return new BlockPos(valueIn.getX(), valueIn.getZ(), newValue);
        default:
            return valueIn;
        }
    }

    public static Direction getClosestLookingDirection(Entity entity) {
        return getClosestLookingDirection(entity, 60.0F);
    }

    public static Direction getClosestLookingDirection(Entity entity, float verticalThreshold) {
        if (entity.pitch >= verticalThreshold) {
            return Direction.DOWN;
        } else {
            return entity.yaw <= -verticalThreshold ? Direction.UP : entity.getHorizontalFacing();
        }
    }

    public static BlockPos getPositionInfrontOfEntity(Entity entity) {
        return getPositionInfrontOfEntity(entity, 60.0F);
    }

    public static BlockPos getPositionInfrontOfEntity(Entity entity, float verticalThreshold) {
        BlockPos pos = new BlockPos(entity.x, entity.y, entity.z);
        if (entity.pitch >= verticalThreshold) {
            return pos.down();
        } else if (entity.pitch <= -verticalThreshold) {
            return new BlockPos(entity.x, Math.ceil(entity.getBoundingBox().maxY), entity.z);
        } else {
            double y = Math.floor(entity.y + (double)entity.getStandingEyeHeight());
            switch(entity.getHorizontalFacing()) {
            case EAST:
                return new BlockPos((int)Math.ceil(entity.x + (double)(entity.getWidth() / 2.0F)), (int)y, (int)Math.floor(entity.z));
            case WEST:
                return new BlockPos((int)Math.floor(entity.x - (double)(entity.getWidth() / 2.0F)) - 1, (int)y, (int)Math.floor(entity.z));
            case SOUTH:
                return new BlockPos((int)Math.floor(entity.x), (int)y, (int)Math.ceil(entity.z + (double)(entity.getWidth() / 2.0F)));
            case NORTH:
                return new BlockPos((int)Math.floor(entity.x), (int)y, (int)Math.floor(entity.z - (double)(entity.getWidth() / 2.0F)) - 1);
            default:
                return pos;
            }
        }
    }

    public static Vec3d getHitVecCenter(BlockPos basePos, Direction facing) {
        int x = basePos.getX();
        int y = basePos.getY();
        int z = basePos.getZ();
        switch(facing) {
        case EAST:
            return new Vec3d((double)(x + 1), (double)y + 0.5D, (double)(z + 1));
        case WEST:
            return new Vec3d((double)x, (double)y + 0.5D, (double)z);
        case SOUTH:
            return new Vec3d((double)x + 0.5D, (double)y + 0.5D, (double)(z + 1));
        case NORTH:
            return new Vec3d((double)x + 0.5D, (double)y + 0.5D, (double)z);
        case UP:
            return new Vec3d((double)x + 0.5D, (double)(y + 1), (double)z + 0.5D);
        case DOWN:
            return new Vec3d((double)x + 0.5D, (double)y, (double)z + 0.5D);
        default:
            return new Vec3d((double)x, (double)y, (double)z);
        }
    }

    public static PositionUtils.HitPart getHitPart(Direction originalSide, Direction playerFacingH, BlockPos pos, Vec3d hitVec) {
        Vec3d positions = getHitPartPositions(originalSide, playerFacingH, pos, hitVec);
        double posH = positions.x;
        double posV = positions.y;
        double offH = Math.abs(posH - 0.5D);
        double offV = Math.abs(posV - 0.5D);
        if (offH <= 0.25D && offV <= 0.25D) {
            return PositionUtils.HitPart.CENTER;
        } else if (offH > offV) {
            return posH < 0.5D ? PositionUtils.HitPart.LEFT : PositionUtils.HitPart.RIGHT;
        } else {
            return posV < 0.5D ? PositionUtils.HitPart.BOTTOM : PositionUtils.HitPart.TOP;
        }
    }

    private static Vec3d getHitPartPositions(Direction originalSide, Direction playerFacingH, BlockPos pos, Vec3d hitVec) {
        double x = hitVec.x - (double)pos.getX();
        double y = hitVec.y - (double)pos.getY();
        double z = hitVec.z - (double)pos.getZ();
        double posH = 0.0D;
        double posV = 0.0D;
        switch(originalSide) {
        case EAST:
        case WEST:
            posH = originalSide.getDirection() == AxisDirection.NEGATIVE ? z : 1.0D - z;
            posV = y;
            break;
        case SOUTH:
        case NORTH:
            posH = originalSide.getDirection() == AxisDirection.POSITIVE ? x : 1.0D - x;
            posV = y;
            break;
        case UP:
        case DOWN:
            switch(playerFacingH) {
            case EAST:
                posH = z;
                posV = x;
                break;
            case WEST:
                posH = 1.0D - z;
                posV = 1.0D - x;
                break;
            case SOUTH:
                posH = 1.0D - x;
                posV = z;
                break;
            case NORTH:
                posH = x;
                posV = 1.0D - z;
            }

            if (originalSide == Direction.DOWN) {
                posV = 1.0D - posV;
            }
        }

        return new Vec3d(posH, posV, 0.0D);
    }

    public static Direction getTargetedDirection(Direction side, Direction playerFacingH, BlockPos pos, Vec3d hitVec) {
        Vec3d positions = getHitPartPositions(side, playerFacingH, pos, hitVec);
        double posH = positions.x;
        double posV = positions.y;
        double offH = Math.abs(posH - 0.5D);
        double offV = Math.abs(posV - 0.5D);
        if (offH <= 0.25D && offV <= 0.25D) {
            return side;
        } else if (side.getAxis() == Axis.Y) {
            if (offH > offV) {
                return posH < 0.5D ? playerFacingH.rotateYCounterclockwise() : playerFacingH.rotateYClockwise();
            } else if (side == Direction.DOWN) {
                return posV > 0.5D ? playerFacingH.getOpposite() : playerFacingH;
            } else {
                return posV < 0.5D ? playerFacingH.getOpposite() : playerFacingH;
            }
        } else if (offH > offV) {
            return posH < 0.5D ? side.rotateYClockwise() : side.rotateYCounterclockwise();
        } else {
            return posV < 0.5D ? Direction.DOWN : Direction.UP;
        }
    }

    public static enum CoordinateType {
        X,
        Y,
        Z;

        private CoordinateType() {
        }
    }

    public static enum HitPart {
        CENTER,
        LEFT,
        RIGHT,
        BOTTOM,
        TOP;

        private HitPart() {
        }
    }
}
