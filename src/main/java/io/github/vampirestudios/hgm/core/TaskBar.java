package io.github.vampirestudios.hgm.core;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.hgm.api.AppInfo;
import io.github.vampirestudios.hgm.api.app.Layout;
import io.github.vampirestudios.hgm.api.app.component.*;
import io.github.vampirestudios.hgm.api.app.component.Button;
import io.github.vampirestudios.hgm.api.app.component.Label;
import io.github.vampirestudios.hgm.api.app.component.render.BackgroundPainter;
import io.github.vampirestudios.hgm.api.app.emojies.Icons;
import io.github.vampirestudios.hgm.api.utils.RenderUtil;
import io.github.vampirestudios.hgm.core.OSLayouts.LayoutStartMenu;
import io.github.vampirestudios.hgm.core.network.TrayItemWifi;
import io.github.vampirestudios.hgm.core.trayItems.TrayItemClipboard;
import io.github.vampirestudios.hgm.core.trayItems.TrayItemConnectedDevices;
import io.github.vampirestudios.hgm.core.trayItems.TrayItemFlameChat;
import io.github.vampirestudios.hgm.core.trayItems.TrayItemSound;
import io.github.vampirestudios.hgm.object.AnalogClock;
import io.github.vampirestudios.hgm.object.TrayItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskBar extends Screen {

    public static final int BAR_HEIGHT = 18;
    private static final Identifier APP_BAR_GUI = new Identifier("hgm:textures/gui/application_bar.png");
    private static final int APPS_DISPLAYED = 11;
    private Button btnLeft;
    private Button btnRight;
    private Button btnStartButton;

    private int offset = 0;

    private List<TrayItem> trayItems = new ArrayList<>();

    private BaseDevice device;

    private int posX, posY;

    public TaskBar(BaseDevice device) {
        super(new LiteralText("TaskBar"));
        this.device = device;
        trayItems.add(new TrayItemWifi());
        trayItems.add(new TrayItemSound());
        trayItems.add(new TrayItemConnectedDevices());
        trayItems.add(new TrayItemClipboard());
        trayItems.add(new TrayItemFlameChat());
//        trayItems.add(new ApplicationAppStore.StoreTrayItem());
    }

    public void init() {
        trayItems.forEach(TrayItem::init);
    }

    public void init(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;

        WPanel root = new WPanel();
        root.setBackgroundPainter(BackgroundPainter.VANILLA);
        root.setSize(400, 300);

        btnLeft = new Button(0, 0, Icons.CHEVRON_LEFT);
        btnLeft.setPadding(1);
        btnLeft.x = posX + 30;
        btnLeft.y = posY + 7;
        btnLeft.setClickListener((mouseX, mouseY, mouseButton) -> {
            if (offset > 0) {
                offset--;
            }
        });

        btnRight = new Button(0, 0, Icons.CHEVRON_RIGHT);
        btnRight.setPadding(1);
        btnRight.x = posX + 40 + 14 * APPS_DISPLAYED + 38;
        btnRight.y = posY + 7;
        btnRight.setClickListener((mouseX, mouseY, mouseButton) -> {
            if (offset + APPS_DISPLAYED < device.installedApps.size()) {
                offset++;
            }
        });

        WSprite sprite = new WSprite(new Identifier("textures/item/redstone.png"), posX + 9, posY + 4);

        btnStartButton = new Button(0, 0, new Identifier("hgm:textures/gui/tuxed/start.png"), 0, 0, 15, 15);
        btnStartButton.setPadding(1);
        btnStartButton.setBackground(true);
        btnStartButton.x = posX + 9;
        btnStartButton.y = posY + 4;
        btnStartButton.setClickListener((mouseX, mouseY, mouseButton) -> {
            if (mouseButton == 0) {
                Layout layout = new LayoutStartMenu();
                layout.init();
                if (!BaseDevice.getSystem().hasContext() || !(BaseDevice.getContext() instanceof LayoutStartMenu)) {
                    switch (BaseDevice.getSystem().getSettings().getTaskBarPlacement()) {
                        case "Bottom":
                            BaseDevice.getSystem().openContext(layout, this.posX, this.posY - layout.height + 2);
                            break;
                        case "Top":
                            BaseDevice.getSystem().openContext(layout, this.posX, this.posY);
                            break;
                        case "Left":
                            BaseDevice.getSystem().openContext(layout, this.posX - layout.width, this.posY - layout.height);
                            break;
                        case "Right":
                            BaseDevice.getSystem().openContext(layout, this.posX + layout.width, this.posY + layout.height);
                            break;
                    }
                } else {
                    BaseDevice.getSystem().closeContext();
                }
            }
        });

        trayItems.forEach(TrayItem::init);
    }

    public void onTick() {
        trayItems.forEach(TrayItem::tick);
    }

    public void render(BaseDevice gui, MinecraftClient mc, int x, int y, int mouseX, int mouseY, float partialTicks) {

        RenderSystem.enableBlend();
        mc.getTextureManager().bindTexture(APP_BAR_GUI);

        Color bgColor = new Color(BaseDevice.getSystem().getSettings().getColourScheme().getTaskBarColour());
        float[] hsb = Color.RGBtoHSB(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), null);
        bgColor = new Color(Color.HSBtoRGB(hsb[0], hsb[1], 0.3F));
        GL11.glColor4f(bgColor.getRed() / 255F, bgColor.getGreen() / 255F, bgColor.getBlue() / 255F, 0.7F);

        int trayItemsWidth = trayItems.size() * 14;
        RenderUtil.drawRectWithTexture(x, y, 0, 0, 1, 18, 1, 18);
        RenderUtil.drawRectWithTexture(x + 1, y, 1, 0, BaseDevice.SCREEN_WIDTH - 36 - trayItemsWidth, 18, 1, 18);
        RenderUtil.drawRectWithTexture(x + BaseDevice.SCREEN_WIDTH - 35 - trayItemsWidth, y, 2, 0, 35 + trayItemsWidth, 18, 1, 18);

        RenderSystem.disableBlend();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        if (gui.installedApps.size() > APPS_DISPLAYED) {
            btnLeft.render(gui, mc, btnLeft.x, btnLeft.y, mouseX, mouseY, true, partialTicks);
            btnRight.render(gui, mc, btnRight.x, btnLeft.y, mouseX, mouseY, true, partialTicks);
        }
        btnStartButton.render(gui, mc, btnStartButton.x, btnStartButton.y, mouseX, mouseY, true, partialTicks);

        if (gui.installedApps.size() < APPS_DISPLAYED) {
            for (int i = 0; i < APPS_DISPLAYED && i < gui.installedApps.size(); i++) {
                AppInfo info = gui.installedApps.get(i + offset);
                RenderUtil.drawApplicationIcon(info, x + 19 + i * 16, y + 2);
                if (gui.isApplicationRunning(info)) {
                    mc.getTextureManager().bindTexture(APP_BAR_GUI);
                    gui.blit(x + 18 + i * 16, y + 1, 35, 0, 16, 16);
                }
            }
        } else {
            for (int i = 0; i < APPS_DISPLAYED && i < gui.installedApps.size(); i++) {
                AppInfo info = gui.installedApps.get(i + offset);
                RenderUtil.drawApplicationIcon(info, x + 34 + i * 16, y + 2);
                if (gui.isApplicationRunning(info)) {
                    mc.getTextureManager().bindTexture(APP_BAR_GUI);
                    gui.blit(x + 33 + i * 16, y + 1, 35, 0, 16, 16);
                }
            }
        }

        mc.textRenderer.drawWithShadow(timeToString(mc.player.world.getTimeOfDay()), x + 414, y + 5, Color.WHITE.getRGB());
        if (isMouseInside(mouseX, mouseY, x + 412, y + 2, x + 412 + 30, y + 16)) {
            fill(x + 412, y + 2, x + 412 + 30, y + 16, new Color(1.0F, 1.0F, 1.0F, 0.1F).getRGB());
        }

        /* Settings App */
        int startX = x + 397;
        for (int i = 0; i < trayItems.size(); i++) {
            int posX = startX - (trayItems.size() - 1 - i) * 14;
            if (isMouseInside(mouseX, mouseY, posX, y + 2, posX + 13, y + 15)) {
                fill(posX, y + 2, posX + 14, y + 16, new Color(1.0F, 1.0F, 1.0F, 0.1F).getRGB());
            }
            trayItems.get(i).getIcon().draw(mc, posX + 2, y + 4);
        }

        mc.getTextureManager().bindTexture(APP_BAR_GUI);

        /* Other Apps */
        if (gui.installedApps.size() < APPS_DISPLAYED) {
            if (isMouseInside(mouseX, mouseY, x + 18, y + 1, x + 236, y + 16)) {
                int appIndex = (mouseX - x - 18) / 16;
                if (appIndex >= 0 && appIndex < offset + APPS_DISPLAYED && appIndex < gui.installedApps.size()) {
                    gui.blit(x + appIndex * 16 + 18, y + 1, 35, 0, 16, 16);
                    gui.renderTooltip(Collections.singletonList(gui.installedApps.get(appIndex).getName()), mouseX + 20, mouseY + 35);
                }
            }
        } else {
            if (isMouseInside(mouseX, mouseY, x + 33, y + 1, x + 306, y + 16)) {
                int appIndex = (mouseX - x - 33) / 16;
                if (appIndex >= 0 && appIndex < offset + APPS_DISPLAYED && appIndex < gui.installedApps.size()) {
                    gui.blit(x + appIndex * 16 + 33, y + 1, 35, 0, 16, 16);
                    gui.renderTooltip(Collections.singletonList(gui.installedApps.get(appIndex).getName()), mouseX + 20, mouseY + 35);
                }
            }
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        DiffuseLighting.disable();

    }

    public void renderOnSide(BaseDevice gui, MinecraftClient mc, int x, int y, int mouseX, int mouseY, float partialTicks) {

        RenderSystem.enableBlend();
        mc.getTextureManager().bindTexture(APP_BAR_GUI);

        Color bgColor = new Color(gui.getSettings().getColourScheme().getBackgroundColour());
        float[] hsb = Color.RGBtoHSB(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), null);
        bgColor = new Color(Color.HSBtoRGB(hsb[0], hsb[1], 0.3F));
        GL11.glColor4f(bgColor.getRed() / 255F, bgColor.getGreen() / 255F, bgColor.getBlue() / 255F, 0.7F);

        int trayItemsWidth = trayItems.size() * 14;
        RenderUtil.drawRectWithTexture(x, y, 0, 0, 18, 1, 19, 1);
        RenderUtil.drawRectWithTexture(x + 1, y, 1, 0, 18, Laptop.SCREEN_HEIGHT - 36, 1, 18);
        RenderUtil.drawRectWithTexture(x, y, 2, 0, 18, 35, 1, 18);

        RenderSystem.disableBlend();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        if (gui.installedApps.size() > APPS_DISPLAYED) {
            btnLeft.render(gui, mc, btnLeft.x, btnLeft.y, mouseX, mouseY, true, partialTicks);
            btnRight.render(gui, mc, btnRight.x, btnLeft.y, mouseX, mouseY, true, partialTicks);
        }
        btnStartButton.render(gui, mc, btnStartButton.x, btnStartButton.y, mouseX, mouseY, true, partialTicks);

        if (gui.installedApps.size() < APPS_DISPLAYED) {
            for (int i = 0; i < APPS_DISPLAYED && i < gui.installedApps.size(); i++) {
                AppInfo info = gui.installedApps.get(i + offset);
                RenderUtil.drawApplicationIcon(info, x + 19 + i * 16, y + 2);
                if (gui.isApplicationRunning(info)) {
                    mc.getTextureManager().bindTexture(APP_BAR_GUI);
                    gui.blit(x + 1, y + 18 + i * 16, 35, 0, 16, 16);
                }
            }
        } else {
            for (int i = 0; i < APPS_DISPLAYED && i < gui.installedApps.size(); i++) {
                AppInfo info = gui.installedApps.get(i + offset);
                RenderUtil.drawApplicationIcon(info, x + 34 + i * 16, y + 2);
                if (gui.isApplicationRunning(info)) {
                    mc.getTextureManager().bindTexture(APP_BAR_GUI);
                    gui.blit(x + 33 + i * 16, y + 1, 35, 0, 16, 16);
                }
            }
        }

        mc.textRenderer.drawWithShadow(timeToString(mc.player.world.getTime()), x + 414, y + 5, Color.WHITE.getRGB());
        if (isMouseInside(mouseX, mouseY, x + 412, y + 2, x + 412 + 30, y + 16)) {
            fill(x + 412, y + 2, x + 412 + 30, y + 16, new Color(1.0F, 1.0F, 1.0F, 0.1F).getRGB());
        }

        /* Settings App */
        int startX = x + 397;
        for (int i = 0; i < trayItems.size(); i++) {
            int posX = startX - (trayItems.size() - 1 - i) * 14;
            if (isMouseInside(mouseX, mouseY, posX, y + 2, posX + 13, y + 15)) {
                fill(posX, y + 2, posX + 14, y + 16, new Color(1.0F, 1.0F, 1.0F, 0.1F).getRGB());
            }
            trayItems.get(i).getIcon().draw(mc, posX + 2, y + 4);
        }

        mc.getTextureManager().bindTexture(APP_BAR_GUI);

        /* Other Apps */
        if (gui.installedApps.size() < APPS_DISPLAYED) {
            if (isMouseInside(mouseX, mouseY, x + 18, y + 1, x + 236, y + 16)) {
                int appIndex = (mouseX - x - 18) / 16;
                if (appIndex >= 0 && appIndex < offset + APPS_DISPLAYED && appIndex < gui.installedApps.size()) {
                    gui.blit(x + appIndex * 16 + 18, y + 1, 35, 0, 16, 16);
                    gui.renderTooltip(Collections.singletonList(gui.installedApps.get(appIndex).getName()), mouseX + 20, mouseY + 35);
                }
            }
        } else {
            if (isMouseInside(mouseX, mouseY, x + 33, y + 1, x + 306, y + 16)) {
                int appIndex = (mouseX - x - 33) / 16;
                if (appIndex >= 0 && appIndex < offset + APPS_DISPLAYED && appIndex < gui.installedApps.size()) {
                    gui.blit(x + appIndex * 16 + 33, y + 1, 35, 0, 16, 16);
                    gui.renderTooltip(Collections.singletonList(gui.installedApps.get(appIndex).getName()), mouseX + 20, mouseY + 35);
                }
            }
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        DiffuseLighting.disable();

    }

    public void handleClick(BaseDevice laptop, int x, int y, int mouseX, int mouseY, int mouseButton) {
        if (laptop.installedApps.size() > APPS_DISPLAYED) {
            btnLeft.mouseClicked(mouseX, mouseY, mouseButton);
            btnRight.mouseClicked(mouseX, mouseY, mouseButton);
        }
        btnStartButton.mouseClicked(mouseX, mouseY, mouseButton);

        if (laptop.installedApps.size() < APPS_DISPLAYED) {
            if (isMouseInside(mouseX, mouseY, x + 18, y + 1, x + 306, y + 16)) {
                int appIndex = (mouseX - x - 18) / 16;
                if (appIndex >= 0 && appIndex <= offset + APPS_DISPLAYED && appIndex < laptop.installedApps.size()) {
                    laptop.openApplication(laptop.installedApps.get(appIndex));
                    return;
                }
            }
        } else {
            if (isMouseInside(mouseX, mouseY, x + 34, y + 1, x + 306, y + 16)) {
                int appIndex = (mouseX - x - 34) / 16;
                if (appIndex >= 0 && appIndex <= offset + APPS_DISPLAYED && appIndex < laptop.installedApps.size()) {
                    laptop.openApplication(laptop.installedApps.get(appIndex));
                    return;
                }
            }
        }

        int startX = x + 397;
        for (int i = 0; i < trayItems.size(); i++) {
            int posX = startX - (trayItems.size() - 1 - i) * 14;
            if (isMouseInside(mouseX, mouseY, posX, y + 2, posX + 13, y + 15)) {
                trayItems.get(i).handleClick(mouseX, mouseY, mouseButton);
                break;
            }
        }

        if (isMouseInside(mouseX, mouseY, x + 412, y + 2, x + 412 + 30, y + 16)) {
            Layout layout = createClockLayout();
            Laptop.getSystem().openContext(layout, layout.width + 233, layout.height - 83);
        }

    }

    public boolean isMouseInside(double mouseX, double mouseY, int x1, int y1, int x2, int y2) {
        return mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2;
    }

    public String timeToString(long time) {
        int hours = (int) ((Math.floor(time / 1000.0) + 7) % 24);
        int minutes = (int) Math.floor((time % 1000) / 1000.0 * 60);
        return String.format("%02d:%02d", hours, minutes);
    }

    private Layout createClockLayout() {
        Layout layout = new Layout.Context(115, 115);
        layout.setBackground((x, y, panel) -> fill(x, y, x + width, y + height, new Color(0.65F, 0.65F, 0.65F, 0.9F).getRGB()));
        layout.addComponent(new AnalogClock(layout.width / 2 - 100 / 2, 12 + (layout.height - 12) / 2 - 100 / 2, 100, 100));

        Label label = new Label("Day -1", 0xFFFFFF) {
            @Override
            public void render(BaseDevice laptop, MinecraftClient mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
                this.setText("Day " + MinecraftClient.getInstance().player.world.getTimeOfDay() / 24000);
                super.render(laptop, mc, x, y, mouseX, mouseY, windowActive, partialTicks);
            }
        };
        label.setLocation(layout.width / 2, 5);
        label.setAlignment(ComponentAlignment.CENTER);
        layout.addComponent(label);
        return layout;
    }

}