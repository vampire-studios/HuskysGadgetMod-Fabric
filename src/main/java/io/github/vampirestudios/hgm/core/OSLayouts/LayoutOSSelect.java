package io.github.vampirestudios.hgm.core.OSLayouts;

import io.github.vampirestudios.hgm.api.app.Layout;
import io.github.vampirestudios.hgm.api.app.component.Button;
import io.github.vampirestudios.hgm.core.BaseDevice;
import io.github.vampirestudios.hgm.core.Laptop;
import io.github.vampirestudios.hgm.core.operation_systems.NeonOS;
import io.github.vampirestudios.hgm.core.operation_systems.PixelOS;
import io.github.vampirestudios.hgm.core.operation_systems.TuxedOS;

public class LayoutOSSelect extends Layout {

    public LayoutOSSelect() {
        super(BaseDevice.SCREEN_WIDTH, BaseDevice.SCREEN_HEIGHT);
        this.setLocation(0, 18);
    }

    @Override
    public void init() {

        Button btnSelectNeonOS = new Button(30, 40, "NeonOS");
        btnSelectNeonOS.setClickListener((mouseX, mouseY, mouseButton) -> BaseDevice.getSystem().getSettings().setOS(new NeonOS(Laptop.taskBar)));
        this.addComponent(btnSelectNeonOS);

        Button btnSelectCraftOS = new Button(60, 40, "TuxedOS");
        btnSelectCraftOS.setClickListener((mouseX, mouseY, mouseButton) -> BaseDevice.getSystem().getSettings().setOS(new TuxedOS(Laptop.taskBar)));
        this.addComponent(btnSelectCraftOS);

        Button btnSelectPixelOS = new Button(90, 40, "PixelOS");
        btnSelectPixelOS.setClickListener((mouseX, mouseY, mouseButton) -> BaseDevice.getSystem().getSettings().setOS(new PixelOS(Laptop.taskBar)));
        this.addComponent(btnSelectPixelOS);

    }

}
