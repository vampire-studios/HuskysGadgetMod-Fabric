package io.github.vampirestudios.hgm.network.task;

import net.minecraft.network.PacketByteBuf;

public class MessageUnlockAdvancement {

    public static MessageUnlockAdvancement decode(PacketByteBuf buf) {
        return new MessageUnlockAdvancement();
    }

    public void encode(PacketByteBuf buf) {
    }

    /*public void onMessage(Supplier<NetworkEvent.Context> contextSupplier) {
        PlayerEntity pl = contextSupplier.get().getSender();
        World w = pl.world;
        int rad = 10;
        int x = (int) pl.posX + w.rand.nextInt(rad * 2) - rad;
        int z = (int) pl.posZ + w.rand.nextInt(rad * 2) - rad;
        int y = w.getHeight(Heightmap.Type.WORLD_SURFACE, x, z);
        BlockPos pos = new BlockPos(x, y, z);
        w.setBlockState(pos, GadgetBlocks.EASTER_EGG.getDefaultState());
    }*/

}
