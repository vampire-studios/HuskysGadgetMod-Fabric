package io.github.vampirestudios.hgm.block.entity;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.core.network.Router;
import io.github.vampirestudios.hgm.init.GadgetTileEntities;
import io.github.vampirestudios.hgm.utils.IColored;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;

public class TileEntityRouter extends TileEntitySync implements ITickableTileEntity, IColored {

    private DyeColor color = DyeColor.WHITE;

    private Router router;

    @Environment(EnvType.CLIENT)
    private int debugTimer;

    public TileEntityRouter() {
        super(GadgetTileEntities.ROUTERS);
    }

    public Router getRouter() {
        if (router == null) {
            router = new Router(pos);
            markDirty();
        }
        return router;
    }

    public void tick() {
        if (!world.isRemote) {
            getRouter().update(world);
        } else if (debugTimer > 0) {
            debugTimer--;
        }
    }

    @Environment(EnvType.CLIENT)
    public boolean isDebug() {
        return debugTimer > 0;
    }

    @Environment(EnvType.CLIENT)
    public void setDebug() {
        HuskysGadgetMod.LOGGER.info("Debug mode activated");
        if (debugTimer <= 0) {
            debugTimer = 1200;
        } else {
            debugTimer = 0;
        }
    }

    @Override
    public CompoundTag write(CompoundTag compound) {
        super.write(compound);
        compound.put("router", getRouter().toTag(false));
        compound.putByte("color", (byte) color.getId());
        return compound;
    }

    @Override
    public void read(CompoundTag compound) {
        super.read(compound);
        if (compound.contains("router", Constants.NBT.TAG_COMPOUND)) {
            router = Router.fromTag(pos, compound.getCompound("router"));
        }
        if (compound.contains("color", Constants.NBT.TAG_BYTE)) {
            this.color = DyeColor.byId(compound.getByte("color"));
        }
    }

    @Override
    public CompoundTag writeSyncTag() {
        CompoundTag tag = new CompoundTag();
        tag.putByte("color", (byte) color.getId());
        return tag;
    }

    public void syncDevicesToClient() {
        pipeline.put("router", getRouter().toTag(true));
        sync();
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        return 16384;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    @Override
    public DyeColor getColor() {
        return color;
    }

    @Override
    public void setColor(DyeColor color) {
        this.color = color;
    }

}