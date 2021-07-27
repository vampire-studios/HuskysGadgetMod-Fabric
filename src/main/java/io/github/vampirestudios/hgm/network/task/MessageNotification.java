package io.github.vampirestudios.hgm.network.task;

import io.github.vampirestudios.hgm.api.app.Notification;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;

public class MessageNotification {
    private NbtCompound notificationTag;

    public MessageNotification() {
    }

    public MessageNotification(Notification notification) {
        this.notificationTag = notification.toTag();
    }

    public MessageNotification(NbtCompound notification) {
        this.notificationTag = notification;
    }

    public static MessageNotification deserialize(PacketByteBuf buf) {
        return new MessageNotification(buf.readNbtCompound());
    }

    public void serialize(PacketByteBuf buf) {
        buf.writeNbtCompound(notificationTag);
    }

    /*public void received(Supplier<NetworkEvent.Context> contextSupplier) {
        HuskysGadgetMod.setup.showNotification(notificationTag);
    }*/
}