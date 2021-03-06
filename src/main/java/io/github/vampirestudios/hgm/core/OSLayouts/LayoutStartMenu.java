package io.github.vampirestudios.hgm.core.OSLayouts;

import io.github.vampirestudios.hgm.api.AppInfo;
import io.github.vampirestudios.hgm.api.ApplicationManager;
import io.github.vampirestudios.hgm.api.app.Layout;
import io.github.vampirestudios.hgm.api.app.component.Button;
import io.github.vampirestudios.hgm.api.app.emojies.Icons;
import io.github.vampirestudios.hgm.core.BaseDevice;
import io.github.vampirestudios.hgm.core.ScreenDrawing;
import net.minecraft.client.MinecraftClient;

import java.awt.*;

public class LayoutStartMenu extends Layout {

    public LayoutStartMenu() {
        super(0, 0, 83, 120);
    }

    @Override
    public void init() {
        this.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) -> {
            Color color = new Color(BaseDevice.getSystem().getSettings().getColourScheme().getItemBackgroundColour());
            ScreenDrawing.colorFill(10, 10, 10 + width - 2, 10 - height - 2, color.getRGB());
        });

        Button btnPowerOff = new Button(5, 5, new Rectangle(82, 20), "Shutdown", Icons.POWER_OFF);
        btnPowerOff.setToolTip("Power Off", "This will turn off the computer");
        btnPowerOff.setClickListener((mouseX, mouseY, mouseButton) -> {
            BaseDevice laptop = (BaseDevice) MinecraftClient.getInstance().currentScreen;
            laptop.closeContext();
            laptop.shutdown();
            System.out.println("Power Off");
        });
        this.addComponent(btnPowerOff);

        Button btnStore = new Button(5, 27, new Rectangle(82, 20), "App Market", Icons.SHOPPING_CART);
        btnStore.setToolTip("App Market", "Allows you to install apps");
        btnStore.setClickListener((mouseX, mouseY, mouseButton) -> {
            AppInfo info = ApplicationManager.getApplication("hgm:app_store");
            if (info != null) {
                BaseDevice.getSystem().openApplication(info);
            }
            System.out.println("Store");
        });
        this.addComponent(btnStore);

        Button btnSettings = new Button(5, 49, new Rectangle(82, 20), "Settings", Icons.HAMMER);
        btnSettings.setToolTip("Settings", "Allows you to change things on the computer");
        btnSettings.setClickListener((mouseX, mouseY, mouseButton) -> {
            if (mouseButton == 0) {
                AppInfo info = ApplicationManager.getApplication("hgm:settings");
                if (info != null) {
                    BaseDevice.getSystem().openApplication(info);
                } else {
                    System.out.println("This app don't exist");
                }
                System.out.println("Settings");
            }
        });
        this.addComponent(btnSettings);

        Button btnFileBrowser = new Button(5, 71, new Rectangle(82, 20), "File Browser", Icons.FOLDER);
        btnFileBrowser.setToolTip("File Browser", "Allows you to browse your files");
        btnFileBrowser.setClickListener((mouseX, mouseY, mouseButton) -> {
            if (mouseButton == 0) {
                AppInfo info = ApplicationManager.getApplication("hgm:file_browser");
                if (info != null) {
                    BaseDevice.getSystem().openApplication(info);
                }
                System.out.println("File Browser");
            }
        });
        this.addComponent(btnFileBrowser);
    }

}
