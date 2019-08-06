package io.github.vampirestudios.hgm.block.entity;

import io.github.vampirestudios.hgm.init.GadgetTileEntities;
import net.minecraft.block.entity.BlockEntity;

public class TileEntityRoofLights extends BlockEntity {

    public TileEntityRoofLights() {
        super(GadgetTileEntities.ROOF_LIGHTS.build(null));
    }

}
