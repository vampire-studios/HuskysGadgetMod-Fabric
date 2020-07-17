package io.github.vampirestudios.hgm.core;

import io.github.vampirestudios.hgm.block.entity.LaptopBlockEntity;
import io.github.vampirestudios.hgm.core.operation_systems.NeonOS;

public class Desktop extends BaseDevice {

    private static final Desktop instance = new Desktop();

    public static final TaskBar taskBar = new TaskBar(instance);

    public Desktop() {
        super(new LaptopBlockEntity(), new NeonOS(taskBar));
    }

}
