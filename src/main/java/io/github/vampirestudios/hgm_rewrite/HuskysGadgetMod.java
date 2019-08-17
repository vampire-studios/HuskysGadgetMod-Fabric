package io.github.vampirestudios.hgm_rewrite;

import io.github.vampirestudios.hgm_rewrite.init.HGMBlocks;
import net.fabricmc.api.ModInitializer;

public class HuskysGadgetMod implements ModInitializer {

    public static final String MOD_ID = "hgm";

    @Override
    public void onInitialize() {
        new HGMBlocks();
    }

}