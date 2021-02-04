package io.github.vampirestudios.hgm.api.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

public class WorldUtils
{
    public static int getDimensionId(World world)
    {
        return /*Registry..getRawId(world.getRegistryKey()).getRawId()*/1;
    }

    /**
     * Best name. Returns the integrated server world for the current dimension
     * in single player, otherwise just the client world.
     * @param mc
     * @return
     */
    public static World getBestWorld(MinecraftClient mc)
    {
        IntegratedServer server = mc.getServer();

        if (mc.world != null && server != null)
        {
            return server.getWorld(mc.world.getRegistryKey());
        }
        else
        {
            return mc.world;
        }
    }

    /**
     * Returns the requested chunk from the integrated server, if it's available.
     * Otherwise returns the client world chunk.
     * @param chunkX
     * @param chunkZ
     * @param mc
     * @return
     */
    public static WorldChunk getBestChunk(int chunkX, int chunkZ, MinecraftClient mc)
    {
        IntegratedServer server = mc.getServer();
        WorldChunk chunk = null;

        if (mc.world != null && server != null)
        {
            ServerWorld world = server.getWorld(mc.world.getRegistryKey());
            chunk = world.getChunk(chunkX, chunkZ);
        }

        if (chunk != null)
        {
            return chunk;
        }

        return mc.world.getChunk(chunkX, chunkZ);
    }
}