package io.github.vampirestudios.hgm.api.app;

import io.github.vampirestudios.hgm.api.app.component.WPlainPanel;
import io.github.vampirestudios.hgm.api.app.component.render.BackgroundPainter;
import io.github.vampirestudios.hgm.api.app.listener.InitListener;
import io.github.vampirestudios.hgm.core.BaseDevice;
import io.github.vampirestudios.hgm.core.ScreenDrawing;
import io.github.vampirestudios.hgm.core.Wrappable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * The Layout class is the main implementation for displaying
 * components in your application. You can have multiple layouts
 * in your application to switch interfaces during runtime.
 * <p>
 * Use {@link Application#setCurrentLayout(Layout)}
 * inside of {@link Wrappable#init(net.minecraft.nbt.CompoundTag)}
 * to set the current layout for your application.
 * <p>
 * Check out the example applications to get a better understand of
 * how this works.
 *
 * @author MrCrayfish
 */
public class Layout extends WPlainPanel {
    /**
     * The list of components in the layout
     */
    public List<Component> components;

    /**
     * The width of the layout
     */
    public int width;

    /**
     * The height of the layout
     */
    public int height;

    private String title;
    private boolean initialized = false;

    private InitListener initListener;
    private BackgroundPainter background;

    /**
     * Default constructor. Initializes a layout with a width of
     * 200 and a height of 100. Use the alternate constructor to
     * set a custom width and height.
     */
    public Layout() {
        this(200, 100);
    }

    public Layout(int width, int height) {
        if (width < 13)
            throw new IllegalArgumentException("Width can not be less than 13 wide");

        if (height < 1)
            throw new IllegalArgumentException("Height can not be less than 1 tall");

        this.components = new ArrayList<>();
        this.width = width;
        this.height = height;
    }

    /**
     * Called on the initialization of the layout. Caused by
     * {@link Application#setCurrentLayout(Layout)}. Will
     * trigger on initialization listener if set.
     * See {@link #setInitListener(InitListener)}
     * TODO: Fix docs
     */
    public void init() {
    }

    /**
     * Adds a component to this layout and initializes it.
     *
     * @param c the component
     */
    public void addComponent(Component c) {
        if (c != null) {
            this.components.add(c);
            c.init(this);
        }
    }

    @Override
    public void init(Layout layout) {
    }

    @Override
    public void handleLoad() {
        if (!initialized) {
            this.init();
            initialized = true;
        }

        if (initListener != null) {
            initListener.onInit();
        }

        for (Component c : components) {
            c.handleLoad();
        }
    }

    @Override
    protected void handleUnload() {
        for (Component c : components) {
            c.handleUnload();
        }
    }

    @Override
    public void handleTick() {
        for (Component c : components) {
            c.handleTick();
        }
    }

    @Override
    public void handleKeyTyped(char character, int code) {
        if (!visible || !enabled)
            return;

        for (Component c : components) {
            c.handleKeyTyped(character, code);
        }
    }

    @Override
    public void handleKeyReleased(char character, int code) {
        if (!visible || !enabled)
            return;

        for (Component c : components) {
            c.handleKeyReleased(character, code);
        }
    }

    @Override
    public Component mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!visible || !enabled)
            return null;

        for (Component c : components) {
            c.mouseClicked(mouseX, mouseY, mouseButton);
            return this;
        }
        return null;
    }

    @Override
    public void mouseDragged(int mouseX, int mouseY, int mouseButton) {
        if (!visible || !enabled)
            return;

        for (Component c : components) {
            c.mouseDragged(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public Component mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (!visible || !enabled)
            return null;

        for (Component c : components) {
            c.mouseReleased(mouseX, mouseY, mouseButton);
            return this;
        }
        return null;
    }

    @Override
    public void mouseScrolled(int mouseX, int mouseY, boolean direction) {
        if (!visible || !enabled)
            return;

        for (Component c : components) {
            c.mouseScrolled(mouseX, mouseY, direction);
        }
    }

    @Override
    public void updateComponents(int x, int y) {
        super.updateComponents(x, y);
        for (Component c : components) {
            c.updateComponents(x + this.x, y + this.y);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (Component c : components) {
            c.setEnabled(enabled);
        }
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        for (Component c : components) {
            c.setVisible(visible);
        }
    }

    /**
     * Sets the initialization listener for this layout.
     * See {@link InitListener}.
     *
     * @param initListener
     */
    public void setInitListener(InitListener initListener) {
        this.initListener = initListener;
    }

    /**
     * Sets the background for this layout.
     * See {@link BackgroundPainter}.
     *
     * @param background the background
     */
    public void setBackground(BackgroundPainter background) {
        this.background = background;
    }

    /**
     * Clears all components in this layout
     */
    public void clear() {
        this.components.clear();
    }

    public boolean hasTitle() {
        return title != null;
    }

    public Text getTitle() {
        return new LiteralText(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized() {
        this.initialized = true;
    }

    public static class Context extends Layout {

        public Context(int width, int height) {
            super(width, height);
        }

        @Override
        public void render(MatrixStack matrixStack, BaseDevice laptop, MinecraftClient mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
            super.render(matrixStack, laptop, mc, x, y, mouseX, mouseY, windowActive, partialTicks);
            ScreenDrawing.drawGuiPanel(x, y, width, height, false);
        }

    }
}