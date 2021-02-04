package io.github.vampirestudios.hgm.api.app.component;

import io.github.vampirestudios.hgm.api.app.Component;
import io.github.vampirestudios.hgm.api.app.Layout;
import io.github.vampirestudios.hgm.api.app.emojies.Icons;
import io.github.vampirestudios.hgm.api.utils.RenderUtil;
import io.github.vampirestudios.hgm.core.BaseDevice;
import io.github.vampirestudios.hgm.object.ImageEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class SlideShow extends Component {
    private static final java.awt.Color OVERLAY = new java.awt.Color(0.0F, 0.0F, 0.0F, 0.15F);
    private static final java.awt.Color OVERLAY_HOVER = new java.awt.Color(0.35F, 0.35F, 0.35F, 0.15F);

    private int width;
    private int height;

    private DefaultedList<ImageEntry> IMAGES = DefaultedList.of();
    private int currentImage = -1;
    private Image image;

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
    public SlideShow(int left, int top, int width, int height) {
        super(left, top);
        this.width = width;
        this.height = height;
        this.image = new Image(left, top, width, height);
        this.image.setBorderVisible(true);
    }

    @Override
    protected void init(Layout layout) {
        image.setDrawFull(true);
        image.init(layout);
    }

    @Override
    protected void handleLoad() {
        image.handleLoad();
    }

    @Override
    public void render(MatrixStack matrixStack, BaseDevice laptop, MinecraftClient mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
        if (!this.visible)
            return;

        image.render(matrixStack, laptop, mc, x, y, mouseX, mouseY, windowActive, partialTicks);

        if (RenderUtil.isMouseInside(mouseX, mouseY, x, y, 15, height)) {
            fill(matrixStack, x, y, x + 15, y + height, OVERLAY_HOVER.getRGB());
        } else {
            fill(matrixStack, x, y, x + 15, y + height, OVERLAY.getRGB());
        }

        if (RenderUtil.isMouseInside(mouseX, mouseY, x + width - 15, y, 15, height)) {
            fill(matrixStack, x + width - 15, y, x + width, y + height, OVERLAY_HOVER.getRGB());
        } else {
            fill(matrixStack, x + width - 15, y, x + width, y + height, OVERLAY.getRGB());
        }

        Icons.CHEVRON_LEFT.draw(mc, x + 2, y + (height - 10) / 2);
        Icons.CHEVRON_RIGHT.draw(mc, x + 3 + width - 15, y + (height - 10) / 2);
    }

    @Override
    public Component mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!this.visible || !this.enabled || mouseButton != 0)
            return null;

        if (RenderUtil.isMouseInside(mouseX, mouseY, x, y, 15, height)) {
            if (currentImage > 0) {
                this.setImage(currentImage - 1);
                return this;
            }
        }

        if (RenderUtil.isMouseInside(mouseX, mouseY, x + width - 15, y, 15, height)) {
            if (currentImage < IMAGES.size() - 1) {
                this.setImage(currentImage + 1);
                return this;
            }
        }
        return null;
    }

    public void addImage(Identifier resource) {
        IMAGES.add(new ImageEntry(resource));
        if (currentImage == -1) {
            this.setImage(0);
        }
    }

    public void addImage(String url) {
        IMAGES.add(new ImageEntry(url));
        if (currentImage == -1) {
            this.setImage(0);
        }
    }

    public void setImage(int index) {
        currentImage = index;
        ImageEntry entry = IMAGES.get(index);
        switch (entry.getType()) {
            case LOCAL:
                image.setImage(entry.getResource());
                break;
            case REMOTE:
                image.setImage(entry.getUrl());
                break;
        }
    }
}