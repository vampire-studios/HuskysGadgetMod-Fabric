package io.github.vampirestudios.hgm.network.task;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import java.util.Objects;

public class MessageSyncConfig {

    public static MessageSyncConfig decode(PacketByteBuf buf) {
        NbtCompound syncTag = buf.readNbtCompound();
        HuskysGadgetMod.config.readSyncTag(Objects.requireNonNull(syncTag));
        return new MessageSyncConfig();
    }

    public void encode(PacketByteBuf buf) {
        buf.writeNbtCompound(HuskysGadgetMod.config.writeSyncTag());
    }

    /*public void received(Supplier<NetworkEvent.Context> contextSupplier) {
    }*/

}
