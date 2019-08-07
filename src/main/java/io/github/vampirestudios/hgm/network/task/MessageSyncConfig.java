package io.github.vampirestudios.hgm.network.task;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.PacketByteBuf;

import java.util.Objects;

public class MessageSyncConfig {

    public static MessageSyncConfig decode(PacketByteBuf buf) {
        CompoundTag syncTag = buf.readCompoundTag();
        HuskysGadgetMod.config.readSyncTag(Objects.requireNonNull(syncTag));
        return new MessageSyncConfig();
    }

    public void encode(PacketByteBuf buf) {
        buf.writeCompoundTag(HuskysGadgetMod.config.writeSyncTag());
    }

    /*public void received(Supplier<NetworkEvent.Context> contextSupplier) {
    }*/

}
