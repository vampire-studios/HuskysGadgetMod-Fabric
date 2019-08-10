package io.github.vampirestudios.hgm.api.app;

import io.github.vampirestudios.hgm.core.BaseDevice;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

public abstract class Component<T extends BaseDevice> extends Screen implements Drawable {
    /**
     * The default components textures
     */
    public static final Identifier COMPONENTS_GUI = new Identifier("hgm:textures/gui/components.png");
    public static final int ALIGN_LEFT = 0;
    public static final int ALIGN_RIGHT = 1;
    public static final int ALIGN_CENTER = 2;
    /**
     * The raw x position of the component. This is not relative to the application.
     */
    public int xPosition;
    /**
     * The raw y position of the component.  This is not relative to the application.
     */
    public int yPosition;
    /**
     * The relative x position from the left.
     */
    public int left;
    /**
     * The relative y position from the top.
     */
    public int top;
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
     * @param left how many pixels from the left
     * @param top  how many pixels from the top
     */
    public Component(int left, int top) {
        super(new LiteralText(""));
        this.left = left;
        this.top = top;
    }

    /**
     * Called when this component is added to a Layout. You can add
     * sub-components through this method. Use {@link Layout#addComponent(Component)}
     *
     * @param layout
     */
    protected void init(Layout<T> layout) {
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
    public void render(T device, MinecraftClient mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
    }

    /**
     * The overlay render loop. Renders over the top of the main render
     * loop.
     *
     * @param device       the device instance
     * @param mc           a Minecraft instance
     * @param mouseX       the current x position of the mouse
     * @param mouseY       the current y position of the mouse
     * @param windowActive if the window is active (at front)
     */
    protected void renderOverlay(T device, MinecraftClient mc, int mouseX, int mouseY, boolean windowActive) {
    }

    /**
     * Called when you mouse button has been pressed. You have to do
     * your own checking to test if it was within the component's
     * bounds.
     *
     * @param mouseX      the current x position of the mouse
     * @param mouseY      the current y position of the mouse
     * @param mouseButton the clicked mouse button
     */
    protected void handleMouseClick(double mouseX, double mouseY, int mouseButton) {
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
     * Called when you drag the mouse with a button pressed down.
     *
     * @param mouseX      the current x position of the mouse
     * @param mouseY      the current y position of the mouse
     * @param mouseButton the pressed mouse button
     */
    protected void handleMouseDrag(int mouseX, int mouseY, int mouseButton) {
    }

    /**
     * Called when you release the currently pressed mouse button. You have to do
     * your own checking to test if it was within the component's
     * bounds.
     *
     * @param mouseX      the x position of the release
     * @param mouseY      the y position of the release
     * @param mouseButton the button that was released
     */
    protected void handleMouseRelease(int mouseX, int mouseY, int mouseButton) {
    }

    //TODO document this plz
    protected void handleMouseScroll(int mouseX, int mouseY, boolean direction) {
    }

    /**
     * This method should be ignored. Used for the core.
     * Will probably be removed in the future.
     */
    protected void updateComponents(int x, int y) {
        this.xPosition = x + left;
        this.yPosition = y + top;
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

}
