package io.github.vampirestudios.hgm.api.app.component;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.hgm.api.app.Component;
import io.github.vampirestudios.hgm.api.app.listener.ClickListener;
import io.github.vampirestudios.hgm.api.utils.RenderUtil;
import io.github.vampirestudios.hgm.core.BaseDevice;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;


public class CheckBox extends Component implements RadioGroup.Item {
    protected String name;
    protected boolean checked = false;
    protected RadioGroup group = null;

    protected ClickListener listener = null;

    protected int textColour = Color.WHITE.getRGB();
    protected int backgroundColour = Color.GRAY.getRGB();
    protected int borderColour = Color.BLACK.getRGB();
    protected int checkedColour = Color.DARK_GRAY.getRGB();

    /**
     * Default check box constructor
     *
     * @param name the name of the check box
     * @param left how many pixels from the left
     * @param top  how many pixels from the top
     */
    public CheckBox(String name, int left, int top) {
        super(left, top);
        this.name = name;
    }

    /**
     * Sets the radio group for this button.
     *
     * @param group the radio group.
     */
    public void setRadioGroup(RadioGroup group) {
        this.group = group;
        this.group.add(this);
    }

    /**
     * Sets the click listener. Use this to handle custom actions
     * when you press the check box.
     *
     * @param listener the click listener
     */
    public void setClickListener(ClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void render(MatrixStack matrixStack, BaseDevice laptop, MinecraftClient mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
        if (this.visible) {
            if (group == null) {
                fill(matrixStack, this.x, this.y, this.x + 10, this.y + 10, borderColour);
                fill(matrixStack, this.x + 1, this.y + 1, this.x + 9, this.y + 9, backgroundColour);
                if (checked) {
                    fill(matrixStack, this.x + 2, this.y + 2, this.x + 8, this.y + 8, checkedColour);
                }
            } else {
                RenderSystem.color3f(1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(COMPONENTS_GUI);
                drawTexture(matrixStack, this.x, this.y, checked ? 10 : 0, 60, 10, 10);
            }
            drawStringWithShadow(matrixStack, mc.textRenderer, name, this.x + 12, this.y + 1, textColour);
        }
    }

    @Override
    public Component mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!this.visible || !this.enabled)
            return null;

        if (RenderUtil.isMouseInside(mouseX, mouseY, x, y, x + 10, y + 10)) {
            if (group != null) {
                group.deselect();
                return this;
            }
            this.checked = !checked;
            if (listener != null) {
                listener.onClick(mouseX, mouseY, mouseButton);
                return this;
            }
        }
        return null;
    }

    @Override
    public boolean isSelected() {
        return checked;
    }

    @Override
    public void setSelected(boolean enabled) {
        this.checked = enabled;
    }

    /**
     * Sets the text colour for this component
     *
     * @param color the text colour
     */
    public void setTextColour(Color color) {
        this.textColour = color.getRGB();
    }

    /**
     * Sets the background colour for this component
     *
     * @param color the background colour
     */
    public void setBackgroundColour(Color color) {
        this.backgroundColour = color.getRGB();
    }

    /**
     * Sets the border colour for this component
     *
     * @param color the border colour
     */
    public void setBorderColour(Color color) {
        this.borderColour = color.getRGB();
    }

    /**
     * Sets the checked colour for this component
     *
     * @param color the checked colour
     */
    public void setCheckedColour(Color color) {
        this.checkedColour = color.getRGB();
    }
}
