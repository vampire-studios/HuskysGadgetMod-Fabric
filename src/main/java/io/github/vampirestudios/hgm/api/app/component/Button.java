package io.github.vampirestudios.hgm.api.app.component;

import com.mojang.blaze3d.platform.GlStateManager;
import io.github.vampirestudios.hgm.api.app.Component;
import io.github.vampirestudios.hgm.api.app.IIcon;
import io.github.vampirestudios.hgm.api.app.listener.ClickListener;
import io.github.vampirestudios.hgm.api.utils.RenderUtil;
import io.github.vampirestudios.hgm.core.BaseDevice;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ChatUtil;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Arrays;

public class Button extends Component {

    protected static final int TOOLTIP_DELAY = 20;

    protected String text;
    protected String toolTip, toolTipTitle;
    protected int toolTipTick;
    protected boolean hovered;
    protected int padding = 5;
    protected int width, height;
    protected boolean explicitSize = false;
    protected Identifier iconResource;
    protected int iconU, iconV;
    protected int iconWidth, iconHeight;
    protected int iconSourceWidth, iconSourceHeight;
    protected ClickListener clickListener = null;
//    private int textColorNormal = 0xFFFFFF, textColorDisabled = 0xFFFFFF, textColorHovered = 0xFFFFFF, backgroundColor, borderColor;
    private boolean background = true;
    public boolean focused;
    private Rectangle bounds;

    /**
     * Alternate button constructor
     *
     * @param left how many pixels from the left
     * @param top  how many pixels from the top
     * @param text text to be displayed in the button
     */
    public Button(int left, int top, String text) {
        super(left, top);
        this.width = getTextWidth(text) + padding * 2;
        this.height = 16;
        this.text = text;
    }

    /**
     * Alternate button constructor
     *
     * @param left how many pixels from the left
     * @param top  how many pixels from the top
     * @param text text to be displayed in the button
     */
    public Button(int left, int top, Rectangle bounds, String text) {
        super(left, top);
        this.explicitSize = true;
        this.width = bounds.width;
        this.height = bounds.height;
        this.text = text;
        this.bounds = bounds;
    }

    /**
     * Alternate button constructor
     *
     * @param left how many pixels from the left
     *             I	 * @param top how many pixels from the top
     * @param icon the icon for the button
     */
    public Button(int left, int top, IIcon icon) {
        super(left, top);
        this.padding = 3;
        this.width = icon.getIconSize() + padding * 2;
        this.height = icon.getIconSize() + padding * 2;
        this.setIcon(icon);
    }

    /**
     * Alternate button constructor
     *
     * @param left how many pixels from the left
     * @param top  how many pixels from the top
     * @param icon the icon for the button
     */
    public Button(int left, int top, Rectangle bounds, IIcon icon) {
        super(left, top);
        this.explicitSize = true;
        this.width = bounds.width;
        this.height = bounds.height;
        this.bounds = bounds;
        this.setIcon(icon);
    }

    /**
     * Alternate button constructor
     *
     * @param left how many pixels from the left
     * @param top  how many pixels from the top
     * @param icon the icon for the button
     */
    public Button(int left, int top, String text, IIcon icon) {
        this(left, top, text);
        this.setIcon(icon);
    }

    /**
     * Alternate button constructor
     *
     * @param left how many pixels from the left
     * @param top  how many pixels from the top
     * @param icon the icon for the button
     */
    public Button(int left, int top, Rectangle bounds, String text, IIcon icon) {
        super(left, top);
        this.text = text;
        this.explicitSize = true;
        this.width = bounds.width;
        this.height = bounds.height;
        this.bounds = bounds;
        this.setIcon(icon);
    }

    /**
     * Alternate button constructor
     *
     * @param left how many pixels from the left
     * @param top  how many pixels from the top
     */
    public Button(int left, int top, Identifier iconResource, int iconU, int iconV, int iconWidth, int iconHeight) {
        super(left, top);
        this.padding = 3;
        this.setIcon(iconResource, iconU, iconV, iconWidth, iconHeight);
    }

    /**
     * Alternate button constructor
     *
     * @param left how many pixels from the left
     * @param top  how many pixels from the top
     */
    public Button(int left, int top, Rectangle bounds, Identifier iconResource, int iconU, int iconV, int iconWidth, int iconHeight) {
        super(left, top);
        this.explicitSize = true;
        this.width = bounds.width;
        this.height = bounds.height;
        this.bounds = bounds;
        this.setIcon(iconResource, iconU, iconV, iconWidth, iconHeight);
    }

    /**
     * Alternate button constructor
     *
     * @param left how many pixels from the left
     * @param top  how many pixels from the top
     */
    public Button(int left, int top, String text, Identifier iconResource, int iconU, int iconV, int iconWidth, int iconHeight) {
        super(left, top);
        this.text = text;
        this.setIcon(iconResource, iconU, iconV, iconWidth, iconHeight);
    }

    /**
     * Alternate button constructor
     *
     * @param left how many pixels from the left
     * @param top  how many pixels from the top
     */
    public Button(int left, int top, int buttonWidth, int buttonHeight, String text, Identifier iconResource, int iconU, int iconV, int iconWidth, int iconHeight) {
        super(left, top);
        this.text = text;
        this.explicitSize = true;
        this.width = buttonWidth;
        this.height = buttonHeight;
        this.setIcon(iconResource, iconU, iconV, iconWidth, iconHeight);
    }

    private static int getTextWidth(String text) {
        TextRenderer fontRenderer = MinecraftClient.getInstance().textRenderer;
        boolean flag = fontRenderer.isRightToLeft();
        fontRenderer.setRightToLeft(false);
        int width = fontRenderer.getStringWidth(text);
        fontRenderer.setRightToLeft(flag);
        return width;
    }

    @Override
    protected void handleTick() {
        toolTipTick = hovered ? ++toolTipTick : 0;
    }

    @Override
    public void render(BaseDevice laptop, MinecraftClient mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
        if (this.visible) {
            mc.getTextureManager().bindTexture(Component.COMPONENTS_GUI);
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

            Color bgColor = new Color(BaseDevice.getSystem().getSettings().getColourScheme().getButtonNormalColour());
            float[] hsb = Color.RGBtoHSB(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), null);
            bgColor = new Color(Color.HSBtoRGB(hsb[0], hsb[1], 1.0F));
            GL11.glColor4f(bgColor.getRed() / 255F, bgColor.getGreen() / 255F, bgColor.getBlue() / 255F, 1.0F);

            this.hovered = RenderUtil.isMouseInside(mouseX, mouseY, x, y, width, height) && windowActive;
            int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.blendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);

            if (background) {
                /* Corners */
                RenderUtil.drawRectWithTexture(x, y, 96 + i * 5, 12, 2, 2, 2, 2);
                RenderUtil.drawRectWithTexture(x + width - 2, y, 99 + i * 5, 12, 2, 2, 2, 2);
                RenderUtil.drawRectWithTexture(x + width - 2, y + height - 2, 99 + i * 5, 15, 2, 2, 2, 2);
                RenderUtil.drawRectWithTexture(x, y + height - 2, 96 + i * 5, 15, 2, 2, 2, 2);

                /* Middles */
                RenderUtil.drawRectWithTexture(x + 2, y, 98 + i * 5, 12, width - 4, 2, 1, 2);
                RenderUtil.drawRectWithTexture(x + width - 2, y + 2, 99 + i * 5, 14, 2, height - 4, 2, 1);
                RenderUtil.drawRectWithTexture(x + 2, y + height - 2, 98 + i * 5, 15, width - 4, 2, 1, 2);
                RenderUtil.drawRectWithTexture(x, y + 2, 96 + i * 5, 14, 2, height - 4, 2, 1);

                /* Center */
                RenderUtil.drawRectWithTexture(x + 2, y + 2, 98 + i * 5, 14, width - 4, height - 4, 1, 1);
            }

            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            int contentWidth = (iconResource != null ? iconWidth : 0) + getTextWidth(text);
            if (iconResource != null && !ChatUtil.isEmpty(text)) contentWidth += 3;
            int contentX = (int) Math.ceil((width - contentWidth) / 2.0);

            if (iconResource != null) {
                int iconY = (height - iconHeight) / 2;
                mc.getTextureManager().bindTexture(iconResource);
                RenderUtil.drawRectWithTexture(x + contentX, y + iconY, iconU, iconV, iconWidth, iconHeight, iconWidth, iconHeight, iconSourceWidth, iconSourceHeight);
            }

            if (!ChatUtil.isEmpty(text)) {
                int textY = (height - mc.textRenderer.fontHeight) / 2 + 1;
                int textOffsetX = iconResource != null ? iconWidth + 3 : 0;
//                int textColor = !Button.this.enabled ? textColorDisabled : (Button.this.hovered ? textColorHovered : textColorNormal);
                drawString(mc.textRenderer, text, x + contentX + textOffsetX, y + textY, 0xFFFFFF);
            }
        }
    }

    @Override
    public void renderOverlay(BaseDevice laptop, MinecraftClient mc, int mouseX, int mouseY, boolean windowActive) {
        if (this.hovered && this.toolTip != null && toolTipTick >= TOOLTIP_DELAY) {
            laptop.renderTooltip(Arrays.asList(Formatting.GOLD + this.toolTipTitle, this.toolTip), mouseX, mouseY);
        }
    }

    protected boolean isValidClickButton(int int_1) {
        return int_1 == 0;
    }

    public void onClick(double double_1, double double_2) {

    }

    public void onRelease(double double_1, double double_2) {
    }

    protected void onDrag(double double_1, double double_2, double double_3, double double_4) {
    }

    @Override
    public boolean  mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if(this.enabled && this.visible) {
            if (this.isValidClickButton(mouseButton)) {
                boolean boolean_1 = this.clicked(mouseX, mouseY);
                if (boolean_1) {
                    this.playDownSound(MinecraftClient.getInstance().getSoundManager());
                    this.onClick(mouseX, mouseY);
                    this.clickListener.onClick(mouseX, mouseY, mouseButton);
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    protected boolean clicked(double double_1, double double_2) {
        return this.enabled && this.visible && double_1 >= (double)this.x && double_2 >= (double)this.y && double_1 < (double)(this.x + this.width) && double_2 < (double)(this.y + this.height);
    }

    public boolean isMouseOver(double double_1, double double_2) {
        return this.enabled && this.visible && double_1 >= (double)this.x && double_2 >= (double)this.y && double_1 < (double)(this.x + this.width) && double_2 < (double)(this.y + this.height);
    }

    @Override
    public boolean changeFocus(boolean boolean_1) {
        if (!enabled)
            return false;
        this.focused = !this.focused;
        return true;
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

    protected void playDownSound(SoundManager handler) {
        handler.play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    protected boolean isInside(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
    }

    public void setSize(int width, int height) {
        this.explicitSize = true;
        this.width = width;
        this.height = height;
    }

    public void setPadding(int padding) {
        this.padding = padding;
        updateSize();
    }

    /**
     * Gets the text currently displayed in the button
     *
     * @return the button text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text to display in the button
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
        updateSize();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setIcon(Identifier iconResource, int iconU, int iconV, int iconWidth, int iconHeight) {
        this.iconU = iconU;
        this.iconV = iconV;
        this.iconResource = iconResource;
        this.iconWidth = iconWidth;
        this.iconHeight = iconHeight;
        this.iconSourceWidth = 16;
        this.iconSourceHeight = 16;
        updateSize();
    }

    public void setIcon(IIcon icon) {
        this.iconU = icon.getU();
        this.iconV = icon.getV();
        this.iconResource = icon.getIconAsset();
        this.iconWidth = icon.getIconSize();
        this.iconHeight = icon.getIconSize();
        this.iconSourceWidth = icon.getGridWidth() * icon.getIconSize();
        this.iconSourceHeight = icon.getGridHeight() * icon.getIconSize();
        updateSize();
    }

    public void removeIcon() {
        this.iconResource = null;
        updateSize();
    }

    private void updateSize() {
        if (explicitSize) return;
        int height = padding * 2;
        int width = padding * 2;

        if (iconResource != null) {
            width += iconWidth;
            height += iconHeight;
        }

        if (text != null) {
            width += getTextWidth(text);
            height = 16;
        }

        if (iconResource != null && text != null) {
            width += 3;
            height = iconHeight + padding * 2;
        }

        this.width = width;
        this.height = height;
    }

    /**
     * Displays a message when hovering the button.
     *
     * @param toolTipTitle title of the tool tip
     * @param toolTip      description of the tool tip
     */
    public void setToolTip(String toolTipTitle, String toolTip) {
        this.toolTipTitle = toolTipTitle;
        this.toolTip = toolTip;
    }

    public boolean hasBackground() {
        return background;
    }

    public void setBackground(boolean background) {
        this.background = background;
    }

    /*public int getTextColorNormal() {
        return textColorNormal;
    }

    public void setTextColorNormal(int textColorNormal) {
        this.textColorNormal = textColorNormal;
    }

    public int getTextColorDisabled() {
        return textColorDisabled;
    }

    public void setTextColorDisabled(int textColorDisabled) {
        this.textColorDisabled = textColorDisabled;
    }

    public int getTextColorHovered() {
        return textColorHovered;
    }

    public void setTextColorHovered(int textColorHovered) {
        this.textColorHovered = textColorHovered;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }*/

}
