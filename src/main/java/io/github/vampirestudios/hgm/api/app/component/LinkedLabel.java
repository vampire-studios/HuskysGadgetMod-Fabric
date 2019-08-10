package io.github.vampirestudios.hgm.api.app.component;

import com.mojang.blaze3d.platform.GlStateManager;
import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.api.app.Component;
import io.github.vampirestudios.hgm.api.app.listener.ClickListener;
import io.github.vampirestudios.hgm.api.utils.RenderUtil;
import io.github.vampirestudios.hgm.core.BaseDevice;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ChatUtil;
import net.minecraft.util.Formatting;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;

public class LinkedLabel extends Component {

    protected static final int TOOLTIP_DELAY = 20;
    protected String text, url;
    protected int width;
    protected double scale = 1;
    protected int alignment = ALIGN_LEFT;
    protected String toolTip, toolTipTitle;
    protected int toolTipTick;
    protected boolean hovered;
    protected ClickListener clickListener = null;
    protected int textColor = Color.WHITE.getRGB();
    private boolean shadow = true;
    private int textColorNormal = 0xFFFFFF, textColorDisabled = 0xFFFFFF, textColorHovered = 0xFFFFFF, backgroundColor, borderColor;

    /**
     * Default label constructor
     *
     * @param text the text to display
     * @param left how many pixels from the left
     * @param top  how many pixels from the top
     */
    public LinkedLabel(String text, int left, int top, String url) {
        super(left, top);
        this.text = text;
        this.url = url;
    }

    @Override
    public void render(BaseDevice laptop, MinecraftClient mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
        if (this.visible) {
            GlStateManager.pushMatrix();
            {
                GlStateManager.translatef(xPosition, yPosition, 0);
                GlStateManager.scaled(scale, scale, scale);
                if (alignment == ALIGN_RIGHT)
                    GlStateManager.translatef((int) -(mc.textRenderer.getStringWidth(text) * scale), 0, 0);
                if (alignment == ALIGN_CENTER)
                    GlStateManager.translatef((int) -(mc.textRenderer.getStringWidth(text) * scale) / (int) (2 * scale), 0, 0);
                if (shadow)
                    BaseDevice.fontRenderer.drawWithShadow(text, 0, 0, textColor);
                else
                    BaseDevice.fontRenderer.draw(text, 0, 0, textColor);

                if (!ChatUtil.isEmpty(text)) {
                    int textColor = !LinkedLabel.this.enabled ? textColorDisabled : (LinkedLabel.this.hovered ? textColorHovered : textColorNormal);
                    if (shadow)
                        BaseDevice.fontRenderer.drawWithShadow(text, 0, 0, textColor);
                    else
                        BaseDevice.fontRenderer.draw(text, 0, 0, textColor);
                }
                int offset = 0;
                if (this.alignment == ALIGN_CENTER) {
                    offset = (int) ((mc.textRenderer.getStringWidth(this.text) / 2) * this.scale);
                } else if (this.alignment == ALIGN_RIGHT) {
                    offset = (int) (mc.textRenderer.getStringWidth(this.text) * this.scale);
                }
                this.hovered = RenderUtil.isMouseInside(mouseX, mouseY, x - offset, y, (int) (mc.textRenderer.getStringWidth(this.text) * this.scale), ((int) scale) * 8) && windowActive;
                int i = this.getHoverState(this.hovered);
                GlStateManager.enableBlend();
                GlStateManager.blendFuncSeparate(770, 771, 1, 0);
                GlStateManager.blendFunc(770, 771);
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
        this.text = text;
    }

    /**
     * Sets the text color for this component
     *
     * @param color the text color
     */
    public void setTextColor(Color color) {
        this.textColor = color.getRGB();
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
     * Sets the alignment of the text. Use {@link Component#ALIGN_LEFT} or
     * {@link Component#ALIGN_RIGHT} to set alignment.
     *
     * @param alignment the alignment type
     */
    public void setAlignment(int alignment) {
        this.alignment = alignment;
    }

    @Override
    protected void handleTick() {
        toolTipTick = hovered ? ++toolTipTick : 0;
    }

    @Override
    public void renderOverlay(BaseDevice laptop, MinecraftClient mc, int mouseX, int mouseY, boolean windowActive) {
        if (this.hovered && this.toolTip != null && toolTipTick >= TOOLTIP_DELAY) {
            laptop.renderTooltip(Arrays.asList(Formatting.GOLD + this.toolTipTitle, this.toolTip), mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (!this.visible || !this.enabled)
            return false;

        if (this.hovered) {
            if (clickListener != null) {
                clickListener.onClick(mouseX, mouseY, mouseButton);

                if (mouseButton == 0) {
                    openWebLink(url);
                    return true;
                }
                return true;
            }
            playDownSound(MinecraftClient.getInstance().getSoundManager());
        }
        return false;
    }

    /**
     * Sets the click listener. Use this to handle custom actions
     * when you press the button.
     *
     * @param clickListener the click listener
     */
    public final void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    protected int getHoverState(boolean mouseOver) {
        int i = 1;

        if (!this.enabled) {
            i = 0;
        } else if (mouseOver) {
            i = 2;
        }

        return i;
    }

    private void playDownSound(SoundManager handler) {
        handler.play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    private void openWebLink(String url) {
        try {
            URI uri = new URL(url).toURI();
            Class<?> class_ = Class.forName("java.awt.Desktop");
            Object object = class_.getMethod("getDesktop").invoke(null);
            class_.getMethod("browse", URI.class).invoke(object, uri);
        } catch (Throwable throwable1) {
            Throwable throwable = throwable1.getCause();
            HuskysGadgetMod.LOGGER.error("Couldn't open link: {}", throwable == null ? "<UNKNOWN>" : throwable.getMessage());
        }
    }

}