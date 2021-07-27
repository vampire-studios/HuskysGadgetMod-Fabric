package io.github.vampirestudios.hgm.network.task;

import io.github.vampirestudios.hgm.api.task.Task;
import io.github.vampirestudios.hgm.api.task.TaskManager;
import net.fabricmc.fabric.api.network.PacketConsumer;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;

public class MessageRequest implements PacketConsumer {

    private static int id;
    private static Task task;
    private static NbtCompound nbt;

    public MessageRequest(int idIn, Task taskIn) {
        id = idIn;
        task = taskIn;
    }

    public MessageRequest(int idIn, Task taskIn, NbtCompound nbtIn) {
        id = idIn;
        task = taskIn;
        nbt = nbtIn;
    }

    public static MessageRequest deserialize(PacketByteBuf buf) {
        String name = buf.readString();
        return new MessageRequest(buf.readInt(), TaskManager.getTask(name), buf.readNbtCompound());
    }

    public int getId() {
        return id;
    }

    public void serialize(PacketByteBuf packetBuffer) {
        packetBuffer.writeInt(id);
        packetBuffer.writeString(task.getName());
        NbtCompound nbt = new NbtCompound();
        task.prepareRequest(nbt);
        packetBuffer.writeNbtCompound(nbt);
    }

    @Override
    public void accept(PacketContext packetContext, PacketByteBuf packetByteBuf) {
        task.processRequest(nbt, packetContext.getPlayer().world, packetContext.getPlayer());
    }

}
