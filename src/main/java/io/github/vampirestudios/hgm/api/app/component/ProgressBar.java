package io.github.vampirestudios.hgm.api.app.component;

import io.github.vampirestudios.hgm.api.app.Component;
import io.github.vampirestudios.hgm.core.BaseDevice;
import io.github.vampirestudios.hgm.core.ScreenDrawing;
import net.minecraft.client.MinecraftClient;

import java.awt.*;

public class ProgressBar extends Component {
    protected int width, height;
    protected int progress = 0;
    protected int max = 100;

    protected int progressColour = new Color(189, 198, 255).getRGB();
    protected int backgroundColour = Color.DARK_GRAY.getRGB();
    protected int borderColour = Color.BLACK.getRGB();

    /**
     * Default progress bar constructor
     *
     * @param left   how many pixels from the left
     * @param top    how many pixels from the top
     * @param width  width of the progress bar
     * @param height height of the progress bar
     */
    public ProgressBar(int left, int top, int width, int height) {
        super(left, top);
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(BaseDevice laptop, MinecraftClient mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
        if (this.visible) {
            ScreenDrawing.rect(x, y, this.x + width, this.y + height, borderColour);
            ScreenDrawing.rect(this.x + 1, this.y + 1, this.x + width - 1, this.y + height - 1, backgroundColour);
            ScreenDrawing.rect(this.x + 2, this.y + 2, this.x + 2 + getProgressScaled(), this.y + height - 2, progressColour);
        }
    }

    private int getProgressScaled() {
        return (int) Math.ceil(((width - 4) * ((double) progress / (double) max)));
    }

    /**
     * Gets the current progress.
     *
     * @return the progress
     */
    public int getProgress() {
        return progress;
    }

    /**
     * Sets the current progress.
     *
     * @param progress the progress to set
     */
    public void setProgress(int progress) {
        if (progress > max) {
            progress = max;
        } else if (progress < 0) {
            progress = 0;
        }
        this.progress = progress;
    }

    /**
     * Sets the max progress
     *
     * @param max the max progress
     */
    public void setMax(int max) {
        if (max > 0) {
            this.max = max;
        }
    }
}
