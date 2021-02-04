package io.github.vampirestudios.hgm.system.layout;

import io.github.vampirestudios.hgm.api.app.Application;
import io.github.vampirestudios.hgm.api.app.IIcon;
import io.github.vampirestudios.hgm.api.app.Layout;
import io.github.vampirestudios.hgm.api.app.component.Button;
import io.github.vampirestudios.hgm.api.app.emojies.Icons;
import io.github.vampirestudios.hgm.core.BaseDevice;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public class StandardLayout extends Layout {
    protected Application app;
    private String title;
    private Layout previous;
    private IIcon icon;

    public StandardLayout(String title, int width, int height, Application app, Layout previous) {
        super(width, height);
        this.title = title;
        this.app = app;
        this.previous = previous;
    }

    @Override
    public void init() {
        if (previous != null) {
            Button btnBack = new Button(2, 2, Icons.ARROW_LEFT);
            btnBack.setClickListener((mouseX, mouseY, mouseButton) ->
            {
                if (mouseButton == 0) {
                    app.setCurrentLayout(previous);
                }
            });
            this.addComponent(btnBack);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, BaseDevice laptop, MinecraftClient mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
        Color color = new Color(BaseDevice.getSystem().getSettings().getColourScheme().getSecondApplicationBarColour());
        fill(matrixStack, x - 1, y, x + width + 1, y + 20, color.getRGB());
        fill(matrixStack, x - 1, y + 20, x + width + 1, y + 21, color.darker().getRGB());

        if (previous == null && icon != null) {
            icon.draw(mc, x + 5, y + 5);
        }
        mc.textRenderer.drawWithShadow(matrixStack, title, x + 5 + (previous != null || icon != null ? 16 : 0), y + 7, Color.WHITE.getRGB());

        super.render(matrixStack, laptop, mc, x, y, mouseX, mouseY, windowActive, partialTicks);
    }

    public void setIcon(IIcon icon) {
        this.icon = icon;
    }
}