package io.github.vampirestudios.hgm.network.task;

import io.github.vampirestudios.hgm.api.task.Task;
import io.github.vampirestudios.hgm.api.task.TaskManager;
import net.fabricmc.fabric.api.network.PacketConsumer;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.PacketByteBuf;

public class MessageRequest implements PacketConsumer {

    private static int id;
    private static Task task;
    private static CompoundTag nbt;

    public MessageRequest(int idIn, Task taskIn) {
        id = idIn;
        task = taskIn;
    }

    public MessageRequest(int idIn, Task taskIn, CompoundTag nbtIn) {
        id = idIn;
        task = taskIn;
        nbt = nbtIn;
    }

    public int getId() {
        return id;
    }

    public void serialize(PacketByteBuf packetBuffer) {
        packetBuffer.writeInt(id);
        packetBuffer.writeString(task.getName());
        CompoundTag nbt = new CompoundTag();
        task.prepareRequest(nbt);
        packetBuffer.writeCompoundTag(nbt);
    }

    public static MessageRequest deserialize(PacketByteBuf buf) {
        String name = buf.readString();
        return new MessageRequest(buf.readInt(), TaskManager.getTask(name), buf.readCompoundTag());
    }

    @Override
    public void accept(PacketContext packetContext, PacketByteBuf packetByteBuf) {
        task.processRequest(nbt, packetContext.getPlayer().world, packetContext.getPlayer());
    }

}
