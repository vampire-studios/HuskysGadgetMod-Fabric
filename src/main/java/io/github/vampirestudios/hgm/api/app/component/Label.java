package io.github.vampirestudios.hgm.api.app.component;

import com.mojang.blaze3d.platform.GlStateManager;
import io.github.vampirestudios.hgm.api.app.Component;
import io.github.vampirestudios.hgm.core.BaseDevice;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.awt.*;

public class Label extends Component {

    protected Text text;
    protected int width;
    protected boolean shadow = true;
    protected double scale = 1;
    protected int alignment = ComponentAlignment.LEFT.id;

    protected int textColor = Color.WHITE.getRGB();
    protected int color;
    protected int darkmodeColor;

    public static final int DEFAULT_TEXT_COLOR = 0x404040;
    public static final int DEFAULT_DARKMODE_TEXT_COLOR = 0xbcbcbc;

    public Label(String text, int color) {
        this(new LiteralText(text), color);
    }

    public Label(Text text, int color) {
        this.text = text;
        this.color = color;
        this.darkmodeColor = (color==DEFAULT_TEXT_COLOR) ? DEFAULT_DARKMODE_TEXT_COLOR : color;
    }

    public Label(String text) {
        this(text, DEFAULT_TEXT_COLOR);
    }

    @Override
    public void render(BaseDevice laptop, MinecraftClient mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
        if (this.visible) {
            GlStateManager.pushMatrix();
            {
                GlStateManager.translatef(this.x, this.y, 0);
                GlStateManager.scaled(scale, scale, scale);
                GlStateManager.translatef((int) -(mc.textRenderer.getStringWidth(text.asFormattedString()) * scale), 0, 0);
                /*if (alignment == ALIGN_RIGHT)
                    GlStateManager.translatef((int) -(mc.textRenderer.getStringWidth(text) * scale), 0, 0);
                if (alignment == ALIGN_CENTER)
                    GlStateManager.translatef((int) -(mc.textRenderer.getStringWidth(text) * scale) / (int) (2 * scale), 0, 0);*/
                BaseDevice.fontRenderer.draw(text.asFormattedString(), 0, 0, 0xFFFFFF);
            }
            GlStateManager.popMatrix();
        }
    }

    /**
     * Sets the text in the label
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = new LiteralText(text);
    }

    /**
     * Sets the text in the label
     *
     * @param text the text
     */
    public void setText(Text text) {
        this.text = text;
    }

    /**
     * Sets the whether shadow should show under the text
     *
     * @param shadow if should render shadow
     */
    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }

    /**
     * Scales the text, essentially setting the font size. Minecraft
     * does not support proper font resizing. The default scale is 1
     *
     * @param scale the text scale
     */
    public void setScale(double scale) {
        this.scale = scale;
    }

    /**
     * Sets the alignment of the text. Use {@link ComponentAlignment#LEFT} or
     * {@link ComponentAlignment#RIGHT} to set alignment.
     *
     * @param alignment the alignment type
     */
    public void setAlignment(ComponentAlignment alignment) {
        this.alignment = alignment.id;
    }
}