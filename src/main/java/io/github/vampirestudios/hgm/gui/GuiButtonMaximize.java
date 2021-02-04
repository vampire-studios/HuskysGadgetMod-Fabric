package io.github.vampirestudios.hgm.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.hgm.core.Window;
import net.minecraft.client.util.math.MatrixStack;

public class GuiButtonMaximize extends GuiButtonWindow {
    private boolean maximized;

    public GuiButtonMaximize(int x, int y) {
        super(x, y);
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            this.minecraft.getTextureManager().bindTexture(Window.WINDOW_GUI);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(770, 771, 1, 0);
            RenderSystem.blendFunc(770, 771);

            int state = this.getHoverState(this.hovered);
            this.drawTexture(matrixStack, this.x, this.y, state * this.width + 26, (2 - (this.maximized ? 1 : 0)) * this.height, this.width, this.height);
        }
    }

    public void setMaximized(boolean maximized) {
        this.maximized = maximized;
    }
}