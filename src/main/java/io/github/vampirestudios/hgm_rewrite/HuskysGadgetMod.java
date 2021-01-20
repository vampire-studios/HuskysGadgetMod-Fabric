package io.github.vampirestudios.hgm_rewrite;

import io.github.vampirestudios.hgm_rewrite.init.HGMBlocks;
import io.github.vampirestudios.vampirelib.utils.registry.RegistryHelper;
import net.fabricmc.api.ModInitializer;

public class HuskysGadgetMod implements ModInitializer {

    public static final String MOD_ID = "hgm";

    public static final RegistryHelper REGISTRY_HELPER = RegistryHelper.createRegistryHelper(MOD_ID);

    @Override
    public void onInitialize() {
        new HGMBlocks();
    }

}