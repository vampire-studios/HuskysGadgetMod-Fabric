package io.github.vampirestudios.hgm.api.app.component;

import io.github.vampirestudios.hgm.api.app.Component;
import io.github.vampirestudios.hgm.api.app.IIcon;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class ToggleButton extends Button implements RadioGroup.Item {

    protected boolean toggle = false;
    protected RadioGroup group = null;

    /**
     * Alternate button constructor
     *
     * @param left how many pixels from the left
     * @param top  how many pixels from the top
     * @param text text to be displayed in the button
     */
    public ToggleButton(int left, int top, String text) {
        super(left, top, text);
    }

    /**
     * Alternate button constructor
     *
     * @param left how many pixels from the left
     * @param top  how many pixels from the top
     * @param icon
     */
    public ToggleButton(int left, int top, IIcon icon) {
        super(left, top, icon);
    }

    /**
     * Alternate button constructor
     *
     * @param left how many pixels from the left
     * @param top  how many pixels from the top
     * @param icon
     */
    public ToggleButton(int left, int top, String text, IIcon icon) {
        super(left, top, text, icon);
    }

    /**
     * Alternate button constructor
     *
     * @param left how many pixels from the left
     * @param top  how many pixels from the top
     */
    public ToggleButton(int left, int top, Identifier iconResource, int iconU, int iconV, int iconWidth, int iconHeight) {
        super(left, top, iconResource, iconU, iconV, iconWidth, iconHeight);
    }

    /**
     * Alternate button constructor
     *
     * @param left how many pixels from the left
     * @param top  how many pixels from the top
     */
    public ToggleButton(int left, int top, String text, Identifier iconResource, int iconU, int iconV, int iconWidth, int iconHeight) {
        super(left, top, text, iconResource, iconU, iconV, iconWidth, iconHeight);
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

    @Override
    public Component mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!this.visible || !this.enabled)
            return null;

        if (super.isInside(mouseX, mouseY)) {
            if (clickListener != null) {
                clickListener.onClick(mouseX, mouseY, mouseButton);
            }
            MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            if (group != null) {
                group.deselect();
                this.toggle = true;
            } else {
                this.toggle = !toggle;
            }
            return this;
        }
        return null;
    }

    @Override
    public boolean isSelected() {
        return toggle;
    }

    @Override
    public void setSelected(boolean selected) {
        this.toggle = selected;
    }
}
