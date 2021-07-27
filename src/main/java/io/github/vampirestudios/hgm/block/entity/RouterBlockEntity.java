package io.github.vampirestudios.hgm.block.entity;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.core.network.Router;
import io.github.vampirestudios.hgm.init.HGMBlockEntities;
import io.github.vampirestudios.hgm.utils.Constants;
import io.github.vampirestudios.hgm.utils.IColored;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.DyeColor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class RouterBlockEntity extends SyncBlockEntity implements IColored {

    private DyeColor color = DyeColor.WHITE;

    private Router router;

    @Environment(EnvType.CLIENT)
    private int debugTimer;

    public RouterBlockEntity() {
        super(HGMBlockEntities.ROUTERS);
    }

    public Router getRouter() {
        if (router == null) {
            router = new Router(pos);
            markDirty();
        }
        return router;
    }

    public void tick() {
        if (!world.isClient) {
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
    public NbtCompound toTag(NbtCompound compound) {
        super.toTag(compound);
        compound.put("router", getRouter().toTag(false));
        compound.putByte("color", (byte) color.getId());
        return compound;
    }

    @Override
    public void fromTag(BlockState blockState, NbtCompound compound) {
        super.fromTag(blockState, compound);
        if (compound.contains("router", Constants.NBT.TAG_COMPOUND)) {
            router = Router.fromTag(pos, compound.getCompound("router"));
        }
        if (compound.contains("color", Constants.NBT.TAG_BYTE)) {
            this.color = DyeColor.byId(compound.getByte("color"));
        }
    }

    @Override
    public NbtCompound writeSyncTag() {
        NbtCompound tag = new NbtCompound();
        tag.putByte("color", (byte) color.getId());
        return tag;
    }

    public void syncDevicesToClient() {
        pipeline.put("router", getRouter().toTag(true));
        sync();
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