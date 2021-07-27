package io.github.vampirestudios.hgm.network.task;

import io.github.vampirestudios.hgm.api.task.Task;
import io.github.vampirestudios.hgm.api.task.TaskManager;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;

public class MessageResponse {
    private static int id;
    private static Task request;
    private static NbtCompound nbt;

    public MessageResponse() {
    }

    public MessageResponse(int idIn, Task requestIn) {
        id = idIn;
        request = requestIn;
    }

    public MessageResponse(int idIn, Task requestIn, NbtCompound NbtCompound) {
        id = idIn;
        request = requestIn;
        nbt = NbtCompound;
    }

    public static MessageResponse decode(PacketByteBuf buf) {
        boolean successful = buf.readBoolean();
        if (successful) request.setSuccessful();
        return new MessageResponse(buf.readInt(), TaskManager.getTaskAndRemove(id), buf.readNbtCompound());
    }

    public void encode(PacketByteBuf buf) {
        buf.writeInt(id);
        buf.writeBoolean(request.isSucessful());
        NbtCompound nbt = new NbtCompound();
        request.prepareResponse(nbt);
        buf.writeNbtCompound(nbt);
        request.complete();
    }

    /*public void received(Supplier<NetworkEvent.Context> contextSupplier) {
        request.processResponse(nbt);
        request.callback(nbt);
    }*/

}
