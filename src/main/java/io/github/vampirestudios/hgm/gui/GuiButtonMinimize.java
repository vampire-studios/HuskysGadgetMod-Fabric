package io.github.vampirestudios.hgm.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.hgm.core.Window;

public class GuiButtonMinimize extends GuiButtonWindow {
    private boolean minimized;

    public GuiButtonMinimize(int x, int y) {
        super(x, y);
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            minecraft.getTextureManager().bindTexture(Window.WINDOW_GUI);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(770, 771, 1, 0);
            RenderSystem.blendFunc(770, 771);

            int state = this.getHoverState(this.isHovered);
            this.blit(this.x, this.y, state * this.width + 26, (2 - (this.minimized ? 1 : 0)) * this.height, this.width, this.height);
        }
    }

    public void setMinimized(boolean minimized) {
        this.minimized = minimized;
    }
}