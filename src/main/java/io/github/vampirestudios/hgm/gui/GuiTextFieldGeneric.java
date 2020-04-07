/*
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.vampirestudios.hgm.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;

public class GuiTextFieldGeneric extends TextFieldWidget {
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public GuiTextFieldGeneric(int x, int y, int width, int height, TextRenderer textRenderer) {
        super(textRenderer, x, y, width, height, "");
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.setMaxLength(256);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        boolean ret = super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 1 && this.isMouseOver((int)mouseX, (int)mouseY)) {
            this.setText("");
            this.setFocused(true);
            return true;
        } else {
            return ret;
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height;
    }

    public void setFocused(boolean isFocusedIn) {
        boolean wasFocused = this.isFocused();
        super.setFocused(isFocusedIn);
        if (this.isFocused() != wasFocused) {
            MinecraftClient.getInstance().keyboard.enableRepeatEvents(this.isFocused());
        }

    }

    public int getCursorPosition() {
        return this.getCursor();
    }

    public void setCursorPosition(int pos) {
        this.se(pos);
    }

    public void setCursorPositionZero() {
        this.method_1870();
    }

    public void setCursorPositionEnd() {
        this.method_1872();
    }

    public GuiTextFieldGeneric setZLevel(int zLevel) {
        this.blitOffset = zLevel;
        return this;
    }

    public void render(int mouseX, int mouseY, float partialTicks) {
        if (this.blitOffset != 0) {
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0F, 0.0F, (float)this.blitOffset);
            super.render(mouseX, mouseY, partialTicks);
            RenderSystem.popMatrix();
        } else {
            super.render(mouseX, mouseY, partialTicks);
        }

    }
}
*/
