package io.github.vampirestudios.hgm.api.app.component;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.hgm.api.app.Component;
import io.github.vampirestudios.hgm.api.app.IIcon;
import io.github.vampirestudios.hgm.core.BaseDevice;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class TextField extends TextArea {

    private IIcon icon;

    /**
     * Default text field constructor
     *
     * @param left  how many pixels from the left
     * @param top   how many pixels from the top
     * @param width the width of the text field
     */
    public TextField(int left, int top, int width) {
        super(left, top, width, 16);
        this.setScrollBarVisible(false);
        this.setMaxLines(1);
    }

    @Override
    public void render(MatrixStack matrixStack, BaseDevice laptop, MinecraftClient mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
        if (icon != null) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            fill(matrixStack, x, y, x + 15, y + 16, borderColor);
            fill(matrixStack, x + 1, y + 1, x + 15, y + 15, secondaryBackgroundColor);
            icon.draw(mc, x + 3, y + 3);
        }
        super.render(matrixStack, laptop, mc, x + (icon != null ? 15 : 0), y, mouseX, mouseY, windowActive, partialTicks);
    }

    @Override
    public Component mouseClicked(int mouseX, int mouseY, int mouseButton) {
        return super.mouseClicked(mouseX - (icon != null ? 15 : 0), mouseY, mouseButton);
    }

    @Override
    public void mouseDragged(int mouseX, int mouseY, int mouseButton) {
        super.mouseDragged(mouseX - (icon != null ? 15 : 0), mouseY, mouseButton);
    }

    @Override
    public Component mouseReleased(int mouseX, int mouseY, int mouseButton) {
        return super.mouseReleased(mouseX - (icon != null ? 15 : 0), mouseY, mouseButton);
    }

    public void setIcon(IIcon icon) {
        if (this.icon == null) {
            width -= 15;
        } else if (icon == null) {
            width += 15;
        }
        this.icon = icon;
    }
}
