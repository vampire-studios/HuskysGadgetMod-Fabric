package io.github.vampirestudios.hgm.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.hgm.core.Window;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class GuiButtonWindow extends ButtonWidget {

    protected MinecraftClient minecraft = MinecraftClient.getInstance();

    public GuiButtonWindow(int x, int y) {
        super(x, y, 11, 11, new LiteralText(""), p_onPress_1_ -> {

        });
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.visible) {
            this.minecraft.getTextureManager().bindTexture(Window.WINDOW_GUI);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(770, 771, 1, 0);
            RenderSystem.blendFunc(770, 771);

            int state = this.getHoverState(this.hovered);
            this.drawTexture(matrices, this.x, this.y, state * this.width + 26, 2 * this.height, this.width, this.height);
        }
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    protected int getHoverState(boolean mouseOver) {
        int i = 1;

        if (!this.active) {
            i = 0;
        } else if (mouseOver) {
            i = 2;
        }

        return i;
    }
}
