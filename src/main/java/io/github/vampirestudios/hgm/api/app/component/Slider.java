package io.github.vampirestudios.hgm.api.app.component;

import io.github.vampirestudios.hgm.api.app.Component;
import io.github.vampirestudios.hgm.api.app.listener.ClickListener;
import io.github.vampirestudios.hgm.api.app.listener.ReleaseListener;
import io.github.vampirestudios.hgm.api.app.listener.SlideListener;
import io.github.vampirestudios.hgm.api.utils.RenderUtil;
import io.github.vampirestudios.hgm.core.BaseDevice;
import net.minecraft.client.MinecraftClient;

import java.awt.*;

public class Slider extends Component {
    protected boolean dragging = false;
    protected int clickX;

    protected int width;
    protected int prevSliderX;
    protected int newSliderX;

    protected int sliderColour = Color.WHITE.getRGB();
    protected int backgroundColour = Color.DARK_GRAY.getRGB();
    protected int borderColour = Color.BLACK.getRGB();

    protected ClickListener clickListener = null;
    protected ReleaseListener releaseListener = null;
    protected SlideListener slideListener = null;

    /**
     * Default slider listener
     *
     * @param left  how many pixels from the left
     * @param top   how many pixels from the top
     * @param width the width of the slider
     */
    public Slider(int left, int top, int width) {
        super(left, top);
        this.width = width;
    }

    @Override
    public void render(BaseDevice laptop, MinecraftClient mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
        if (this.visible) {
            fill(this.x, this.y + 4, this.x + width, this.y + 8, borderColour);
            fill(this.x + 1, this.y + 5, this.x + width - 1, this.y + 7, backgroundColour);
            fill(this.x + newSliderX, this.y, this.x + newSliderX + 8, this.y + 12, borderColour);
            fill(this.x + newSliderX + 1, this.y + 1, this.x + newSliderX + 7, this.y + 11, sliderColour);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (!this.visible || !this.enabled)
            return false;

        if (RenderUtil.isMouseInside(mouseX, mouseY, x + newSliderX, y, x + newSliderX + 8, y + 12)) {
            this.dragging = true;
            this.clickX = (int) mouseX;
            if (clickListener != null) {
                clickListener.onClick(mouseX, mouseY, mouseButton);
                return true;
            }
            return true;
        }
        return false;
    }

    @Override
    public void handleMouseDrag(int mouseX, int mouseY, int mouseButton) {
        if (!this.visible || !this.enabled)
            return;

        if (dragging) {
            this.newSliderX = prevSliderX + (mouseX - clickX);
            if (this.newSliderX < 0) {
                this.newSliderX = 0;
            }
            if (this.newSliderX >= width - 8) {
                this.newSliderX = width - 8;
            }
            if (slideListener != null) {
                slideListener.onSlide(getPercentage());
            }
        }
    }

    @Override
    public void handleMouseRelease(int mouseX, int mouseY, int mouseButton) {
        if (!this.visible || !this.enabled)
            return;

        this.dragging = false;
        this.prevSliderX = this.newSliderX;
        if (releaseListener != null) {
            releaseListener.onRelease(mouseX, mouseY, 0);
        }
    }

    @Override
    public void handleMouseScroll(int mouseX, int mouseY, boolean direction) {
        if (!this.visible || !this.enabled)
            return;

        if (RenderUtil.isMouseInside(mouseX, mouseY, x, y, x + width, y + 12)) {
            prevSliderX = newSliderX;
            if (direction) {
                newSliderX++;
                if (newSliderX >= width - 8) {
                    newSliderX = width - 8;
                }
            } else {
                newSliderX--;
                if (newSliderX < 0) {
                    newSliderX = 0;
                }
            }
            if (slideListener != null) {
                slideListener.onSlide(getPercentage());
            }
        }
    }

    /**
     * Sets the click listener. Calls the listener when the slider is clicked.
     *
     * @param clickListener the click listener
     */
    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /**
     * Sets the release listener. Calls the listener when the slider is released.
     *
     * @param releaseListener the release listener
     */
    public void setReleaseListener(ReleaseListener releaseListener) {
        this.releaseListener = releaseListener;
    }

    /**
     * Sets the slider listener. Calls the listener when the slider is moved.
     *
     * @param slideListener the slide listener
     */
    public void setSlideListener(SlideListener slideListener) {
        this.slideListener = slideListener;
    }

    /**
     * Gets the percentage of the slider
     *
     * @return the percentage
     */
    public float getPercentage() {
        return (float) this.newSliderX / (float) (this.width - 8);
    }

    /**
     * Sets the slider percentage
     *
     * @param percentage the percentage
     */
    public void setPercentage(float percentage) {
        if (percentage < 0.0F || percentage > 1.0F) return;
        this.newSliderX = (int) ((this.width - 8) * percentage);
    }

    /**
     * Sets the slider colour for this component
     *
     * @param color the slider colour
     */
    public void setSliderColour(Color color) {
        this.sliderColour = color.getRGB();
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
}
