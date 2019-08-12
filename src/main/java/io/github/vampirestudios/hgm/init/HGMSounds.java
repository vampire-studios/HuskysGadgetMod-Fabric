package io.github.vampirestudios.hgm.init;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class HGMSounds {
    public static final SoundEvent PRINTER_PRINTING;
    public static final SoundEvent PRINTER_LOADING_PAPER;
    public static final SoundEvent FANS_BLOWING;
    public static final SoundEvent ZAP;
    public static final SoundEvent LASER;


    static {
        PRINTER_PRINTING = registerSound("printing_ink");
        PRINTER_LOADING_PAPER = registerSound("printing_paper");
        FANS_BLOWING = registerSound("fans_blowing");
        ZAP = registerSound("zap");
        LASER = registerSound("lasers");
    }

    private static SoundEvent registerSound(String soundNameIn) {
        Identifier resource = new Identifier(HuskysGadgetMod.MOD_ID, soundNameIn);
        return Registry.register(Registry.SOUND_EVENT, resource, new SoundEvent(resource));
    }

}
