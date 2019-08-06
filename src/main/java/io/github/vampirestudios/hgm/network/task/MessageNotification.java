package io.github.vampirestudios.hgm.network.task;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.api.app.Notification;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.PacketByteBuf;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageNotification {
    private CompoundTag notificationTag;

    public MessageNotification() {
    }

    public MessageNotification(Notification notification) {
        this.notificationTag = notification.toTag();
    }

    public MessageNotification(CompoundTag notification) {
        this.notificationTag = notification;
    }

    public static MessageNotification deserialize(PacketByteBuf buf) {
        return new MessageNotification(buf.readCompoundTag());
    }

    public void serialize(PacketByteBuf buf) {
        buf.writeCompoundTag(notificationTag);
    }

    public void received(Supplier<NetworkEvent.Context> contextSupplier) {
        HuskysGadgetMod.setup.showNotification(notificationTag);
    }
}