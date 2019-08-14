package io.github.vampirestudios.hgm.api.app;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.api.app.component.WPanel;
import io.github.vampirestudios.hgm.core.BaseDevice;
import io.github.vampirestudios.hgm.gui.GuiDescription;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.util.Identifier;

public abstract class Component extends DrawableHelper {
    /**
     * The default components textures
     */
    public static final Identifier COMPONENTS_GUI = new Identifier(HuskysGadgetMod.MOD_ID, "textures/gui/components.png");
    public final MinecraftClient mc = MinecraftClient.getInstance();
    public final TextRenderer textRenderer = mc.textRenderer;
    public WPanel parent;
    /**
     * The raw x position of the component. This is not relative to the application.
     */
    public int x = 0;
    /**
     * The raw y position of the component.  This is not relative to the application.
     */
    public int y = 0;
    public int width = 18;
    public int height = 18;
    /**
     * The z level of the component.
     */
    protected int zLevel;
    /**
     * Is the component enabled
     */
    protected boolean enabled = true;
    /**
     * Is the component visible
     */
    protected boolean visible = true;

    /**
     * The default constructor for a component.
     * <p>
     * Laying out components is simply relative positioning. So for left (x position),
     * specific how many pixels from the left of the application window you want
     * it to be positioned at. The top is the same, but instead from the top (y position).
     *
     * @param x how many pixels from the left
     * @param y  how many pixels from the top
     */
    public Component(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * The default constructor for a component.
     * <p>
     * Laying out components is simply relative positioning. So for left (x position),
     * specific how many pixels from the left of the application window you want
     * it to be positioned at. The top is the same, but instead from the top (y position).
     *
     * @param x how many pixels from the left
     * @param y  how many pixels from the top
     */
    public Component(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * The default constructor for a component.
     */
    public Component() { }

    /**
     * Called when this component is added to a Layout. You can add
     * sub-components through this method. Use {@link Layout#addComponent(Component)}
     *
     * @param layout the layout this component is added to
     */
    protected void init(Layout layout) {
    }

    /**
     * Called when the Layout this component is bound to is set as the current layout in an
     * application.
     */
    protected void handleLoad() {
    }

    /**
     * TODO: finish docs
     */
    protected void handleUnload() {
    }

    /**
     * Called when the game ticks
     */
    protected void handleTick() {
    }

    public int getAbsoluteX() {
        if (parent==null) {
            return getX();
        } else {
            return getX() + parent.getAbsoluteX();
        }
    }

    public int getAbsoluteY() {
        if (parent==null) {
            return getY();
        } else {
            return getY() + parent.getAbsoluteY();
        }
    }

    public boolean canResize() {
        return false;
    }

    public void setParent(WPanel parent) {
        this.parent = parent;
    }

    @Environment(EnvType.CLIENT)
    public void paintBackground(int x, int y) {
    }

    @Environment(EnvType.CLIENT)
    public void paintForeground(int x, int y, int mouseX, int mouseY) {
        if (mouseX >= x && mouseX < x+getWidth() && mouseY >= y && mouseY < y+getHeight()) {
//            renderTooltip(mouseX, mouseY);
        }
    }

    /**
     * Creates component peers, lays out children, and initializes animation data for this Widget and all its children.
     * The host container must clear any heavyweight peers from its records before this method is called.
     */
    public void validate(GuiDescription host) {
        //valid = true;
    }

    /**
     * The main render loop. This is where you draw your component.
     *
     * @param device       the device instance
     * @param mc           a Minecraft instance
     * @param mouseX       the current x position of the mouse
     * @param mouseY       the current y position of the mouse
     * @param windowActive if the window is active (at front)
     * @param partialTicks percentage passed in-between two ticks
     */
    public void render(BaseDevice device, MinecraftClient mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
//        BackgroundPainter.VANILLA.paintBackground(x, y, this);
    }

    /**
     * The overlay render loop. Renders over the top of the main render
     * loop.
     *
     * @param device       the device instance
     * @param mc           a MinecraftClient instance
     * @param mouseX       the current x position of the mouse
     * @param mouseY       the current y position of the mouse
     * @param windowActive if the window is active (at front)
     */
    protected void renderOverlay(BaseDevice device, MinecraftClient mc, int mouseX, int mouseY, boolean windowActive) {
    }

    /**
     * Called when a key is typed from your keyboard.
     *
     * @param character the typed character
     * @param code      the typed character code
     */
    protected void handleKeyTyped(char character, int code) {
    }

    /**
     * Called when a key is released from your keyboard.
     *
     * @param character the released character
     * @param code      the released character code
     */
    protected void handleKeyReleased(char character, int code) {
    }

    /**
     * Notifies this component that the mouse has been pressed while inside its bounds
     * @param x The X coordinate of the event, in widget-space (0 is the left edge of this widget)
     * @param y The Y coordinate of the event, in widget-space (0 is the top edge of this widget)
     * @param button The mouse button that was used. Button numbering is consistent with LWJGL Mouse (0=left, 1=right, 2=mousewheel click)
     */
    public Component mouseClicked(int x, int y, int button) {
        return this;
    }

    /**
     * Notifies this component that the mouse has been moved while pressed and inside its bounds
     * @param x The X coordinate of the event, in widget-space (0 is the left edge of this widget)
     * @param y The Y coordinate of the event, in widget-space (0 is the top edge of this widget)
     * @param button The mouse button that was used. Button numbering is consistent with LWJGL Mouse (0=left, 1=right, 2=mousewheel click)
     */
    public void mouseDragged(int x, int y, int button) {
    }

    /**
     * Notifies this component that the mouse has been released while inside its bounds
     * @param x The X coordinate of the event, in widget-space (0 is the left edge of this widget)
     * @param y The Y coordinate of the event, in widget-space (0 is the top edge of this widget)
     * @param button The mouse button that was used. Button numbering is consistent with LWJGL Mouse (0=left, 1=right, 2=mousewheel click)
     */
    public Component mouseReleased(int x, int y, int button) {
        return this;
    }

    /**
     * Notifies this component that the mouse has been pressed and released, both while inside its bounds.
     * @param x The X coordinate of the event, in widget-space (0 is the left edge of this widget)
     * @param y The Y coordinate of the event, in widget-space (0 is the top edge of this widget)
     * @param button The mouse button that was used. Button numbering is consistent with LWJGL Mouse (0=left, 1=right, 2=mousewheel click)
     */
    public void onClick(int x, int y, int button) {
    }

    /**
     * Notifies this component that the mouse has been scrolled, both while inside its bounds.
     * @param x The X coordinate of the event, in widget-space (0 is the left edge of this widget)
     * @param y The Y coordinate of the event, in widget-space (0 is the top edge of this widget)
     * @param direction the direction the mousewheel is scrolled, if true down else up
     */
    public void mouseScrolled(int x, int y, boolean direction) {
    }

    /**
     * This method should be ignored. Used for the core.
     * Will probably be removed in the future.
     */
    protected void updateComponents(int x, int y) {
        this.x = x + this.x;
        this.y = y + this.y;
    }

    /**
     * Sets whether this component is enabled. You should respect
     * this value if you create your own custom components.
     *
     * @param enabled if this component should be enabled or not
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Sets whether this component is visible. You should respect
     * this value if you create your own custom components.
     *
     * @param visible if this component should be visible or not
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
