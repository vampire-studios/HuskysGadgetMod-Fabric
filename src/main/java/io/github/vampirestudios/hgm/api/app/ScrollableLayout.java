package io.github.vampirestudios.hgm.api.app;

import io.github.vampirestudios.hgm.api.app.component.Text;
import io.github.vampirestudios.hgm.api.utils.RenderUtil;
import io.github.vampirestudios.hgm.core.BaseDevice;
import io.github.vampirestudios.hgm.utils.GLHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class ScrollableLayout extends Layout {
    protected int placeholderColor = new Color(1.0F, 1.0F, 1.0F, 0.35F).getRGB();

    protected int scroll;
    private int visibleHeight;
    private int scrollSpeed = 5;

    public ScrollableLayout(int width, int height, int visibleHeight) {
        super(width, height);
        this.visibleHeight = visibleHeight;
    }

    /**
     * The default constructor for a component.
     * <p>
     * Laying out components is simply relative positioning. So for left (x position),
     * specific how many pixels from the left of the application window you want
     * it to be positioned at. The top is the same, but instead from the top (y position).
     *
     * @param left how many pixels from the left
     * @param top  how many pixels from the top
     */
    public ScrollableLayout(int left, int top, int width, int height, int visibleHeight) {
        super(width, height);
        this.setLocation(left, top);
        this.visibleHeight = visibleHeight;
    }

    public static ScrollableLayout create(int left, int top, int width, int visibleHeight, String text) {
        Text t = new Text(text, 0, 0, width);
        ScrollableLayout layout = new ScrollableLayout(left, top, t.getWidth(), t.getHeight(), visibleHeight);
        layout.addComponent(t);
        return layout;
    }

    @Override
    public void render(BaseDevice laptop, MinecraftClient mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
        if (!visible)
            return;

        GLHelper.pushScissor(x, y, width, visibleHeight);
        super.render(laptop, mc, x, y - scroll, mouseX, mouseY, windowActive, partialTicks);
        GLHelper.popScissor();
    }

    @Override
    public void renderOverlay(BaseDevice laptop, MinecraftClient mc, int mouseX, int mouseY, boolean windowActive) {
        if (!visible)
            return;

        super.renderOverlay(laptop, mc, mouseX, mouseY, windowActive);

        if (this.height > this.visibleHeight) {
            int visibleScrollBarHeight = visibleHeight;
            int scrollBarHeight = Math.max(20, (int) (visibleHeight / (float) height * (float) visibleScrollBarHeight));
            float scrollPercentage = MathHelper.clamp(scroll / (float) (height - visibleHeight), 0.0F, 1.0F);
            int scrollBarY = (int) ((visibleScrollBarHeight - scrollBarHeight) * scrollPercentage);
            int scrollY = y + scrollBarY;
            fill(x + width - 5, scrollY, x + width - 2, scrollY + scrollBarHeight, placeholderColor);
        }
    }

    @Override
    public void updateComponents(int x, int y) {
        this.x = x + this.x;
        this.y = y + this.y;
        for (Component c : components) {
            c.updateComponents(x + this.x, y + this.x - scroll);
        }
    }

    @Override
    public void mouseScrolled(int mouseX, int mouseY, boolean direction) {
        if (!visible || !enabled)
            return;

        if (RenderUtil.isMouseInside(mouseX, mouseY, x, y, width, visibleHeight) && height > visibleHeight) {
            scroll += direction ? -scrollSpeed : scrollSpeed;
            if (scroll + visibleHeight > height) {
                scroll = height - visibleHeight;
            } else if (scroll < 0) {
                scroll = 0;
            }
            this.updateComponents(x - this.x, y - this.y);
        }
    }

    @Override
    public Component mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (RenderUtil.isMouseInside(mouseX, mouseY, x, y, width, visibleHeight)) {
            return super.mouseClicked(mouseX, mouseY, mouseButton);
        }
        return null;
    }

    @Override
    public Component mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (RenderUtil.isMouseInside(mouseX, mouseY, x, y, width, visibleHeight)) {
            return super.mouseReleased(mouseX, mouseY, mouseButton);
        }
        return null;
    }

    @Override
    public void mouseDragged(int mouseX, int mouseY, int mouseButton) {
        if (RenderUtil.isMouseInside(mouseX, mouseY, x, y, width, visibleHeight)) {
            super.mouseDragged(mouseX, mouseY, mouseButton);
        }
    }

    public void setScrollSpeed(int scrollSpeed) {
        this.scrollSpeed = scrollSpeed;
    }

    public void resetScroll() {
        this.scroll = 0;
        this.updateComponents(x - this.x, y - this.y);
    }
}