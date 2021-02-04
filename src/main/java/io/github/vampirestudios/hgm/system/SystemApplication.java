package io.github.vampirestudios.hgm.system;

import io.github.vampirestudios.hgm.api.app.Application;
import io.github.vampirestudios.hgm.core.BaseDevice;

/**
 * Created by Casey on 03-Aug-17.
 */
public abstract class SystemApplication extends Application {

    private BaseDevice laptop;

    protected SystemApplication() {

    }

    public BaseDevice getLaptop() {
        return laptop;
    }

    public void setLaptop(BaseDevice laptop) {
        this.laptop = laptop;
    }

}
