package io.github.vampirestudios.hgm.api.app.component;

import io.github.vampirestudios.hgm.api.app.Component;
import io.github.vampirestudios.hgm.api.app.Layout;
import io.github.vampirestudios.hgm.api.app.emojies.Icons;
import io.github.vampirestudios.hgm.api.app.listener.ItemClickListener;
import io.github.vampirestudios.hgm.api.app.renderer.ListItemRenderer;
import io.github.vampirestudios.hgm.api.utils.RenderUtil;
import io.github.vampirestudios.hgm.core.BaseDevice;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.DefaultedList;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ItemList<E> extends Component implements Iterable<E> {

    private static final int LOADING_BACKGROUND = new Color(BaseDevice.getSystem().getSettings().getColourScheme().getSecondApplicationBarColour()).darker().getRGB();
    protected int width;
    protected int visibleItems;
    protected int offset;
    protected int selected = -1;
    protected boolean showAll = true;
    protected boolean resized = false;
    protected boolean initialized = false;
    protected boolean loading = false;
    protected List<E> items = DefaultedList.of();
    protected ListItemRenderer<E> renderer = null;
    protected ItemClickListener<E> itemClickListener = null;
    protected Button btnUp;
    protected Button btnDown;
    protected Layout layoutLoading;
    protected int textColor = Color.WHITE.getRGB();
    protected Color backgroundColor = new Color(BaseDevice.getSystem().getSettings().getColourScheme().getSecondApplicationBarColour()).brighter();
    protected Color borderColor = new Color(BaseDevice.getSystem().getSettings().getColourScheme().getSecondApplicationBarColour()).darker().darker();
    private Comparator<E> sorter = null;

    /**
     * Default constructor for the item list. Should be noted that the
     * height is determined by how many visible items there are.
     *
     * @param left         how many pixels from the left
     * @param top          how many pixels from the top
     * @param width        width of the list
     * @param visibleItems how many items are visible
     */
    public ItemList(int left, int top, int width, int visibleItems) {
        super(left, top);
        this.width = width;
        this.visibleItems = visibleItems;
    }

    public ItemList(int left, int top, int width, int visibleItems, boolean showAll) {
        this(left, top, width, visibleItems);
        this.showAll = showAll;
    }

    @Override
    public void init(Layout layout) {
        btnUp = new Button(this.x + width - 12, this.y, Icons.CHEVRON_UP);
        btnUp.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) scrollUp();
        });
        btnUp.setEnabled(false);
        btnUp.setVisible(false);
        btnUp.setSize(12, 12);
        layout.addComponent(btnUp);

        btnDown = new Button(this.x + width - 12, this.y + getHeight() - 12, Icons.CHEVRON_DOWN);
        btnDown.setClickListener((mouseX, mouseY, mouseButton) ->
        {
            if (mouseButton == 0) scrollDown();
        });
        btnDown.setEnabled(false);
        btnDown.setVisible(false);
        btnDown.setSize(12, 12);
        layout.addComponent(btnDown);

        layoutLoading = new Layout(getWidth(), getHeight());
        layoutLoading.setLocation(this.x, this.y);
        layoutLoading.setVisible(loading);
        layoutLoading.addComponent(new Spinner((layoutLoading.width - 12) / 2, (layoutLoading.height - 12) / 2));
        layoutLoading.setBackground((x, y, panel) -> fill(x, y, x + panel.width, y + panel.height, LOADING_BACKGROUND));
        layout.addComponent(layoutLoading);

        updateButtons();
        updateComponent();

        initialized = true;
    }

    @Override
    public void render(BaseDevice laptop, MinecraftClient mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
        if (this.visible) {
            int height = 13;
            if (renderer != null) {
                height = renderer.getHeight();
            }

            int size = getSize();

            /* Fill */
            fill(this.x + 1, this.y + 1, this.x + width - 1, this.y + (size * height) + size, new Color(BaseDevice.getSystem().getSettings().getColourScheme().getSecondApplicationBarColour()).brighter().getRGB());

            /* Box */
            hLine(this.x, this.x + width - 1, this.y, borderColor.getRGB());

            vLine(this.x, this.y, this.y + (size * height) + size, borderColor.getRGB());
            vLine(this.x + width - 1, this.y, this.y + (size * height) + size, borderColor.getRGB());
            hLine(this.x, this.x + width - 1, this.y + (size * height) + size, borderColor.getRGB());

            /* Items */
            for (int i = 0; i < size - 1 && i < items.size(); i++) {
                E item = getItem(i);
                if (item != null) {
                    if (renderer != null) {
//                        renderer.render(item, this, mc, this.x + 1, this.y + (i * (renderer.getHeight())) + 1 + i, width - 2, renderer.getHeight(), (i + offset) == selected);
                        hLine(this.x + 1, this.x + width - 1, this.y + (i * height) + i + height + 1, borderColor.getRGB());
                    } else {
                        fill(this.x + 1, this.y + (i * 14) + 1, this.x + width - 1, this.y + 13 + (i * 14) + 1, (i + offset) != selected ? backgroundColor.getRGB() : new Color(BaseDevice.getSystem().getSettings().getColourScheme().getSecondApplicationBarColour()).darker().getRGB());
                        drawString(mc.textRenderer, item.toString(), this.x + 3, this.y + 3 + (i * 14), textColor);
                        hLine(this.x + 1, this.x + width - 2, this.y + (i * height) + i + height + 1, new Color(BaseDevice.getSystem().getSettings().getColourScheme().getSecondApplicationBarColour()).darker().getRGB());
                    }
                }
            }

            int i = size - 1;
            E item = getItem(i);
            if (item != null) {
                if (renderer != null) {
//                    renderer.render(laptop, null, item, mc, this.x + 1, this.y + (i * (renderer.getHeight())) + 1 + i, width - 2, renderer.getHeight(), (i + offset) == selected);
                    hLine(this.x + 1, this.x + width - 1, this.y + (i * height) + i + height + 1, borderColor.getRGB());
                } else {
                    fill(this.x + 1, this.y + (i * 14) + 1, this.x + width - 1, this.y + 13 + (i * 14) + 1, (i + offset) != selected ? backgroundColor.getRGB() : new Color(BaseDevice.getSystem().getSettings().getColourScheme().getSecondApplicationBarColour()).darker().getRGB());
                    RenderUtil.drawStringClipped(item.toString(), this.x + 3, this.y + 3 + (i * 14), width - 6, textColor, true);
                }
            }

            if (items.size() > visibleItems) {
                fill(this.x + width, this.y, this.x + width + 10, this.y + (size * height) + size, new Color(BaseDevice.getSystem().getSettings().getColourScheme().getSecondApplicationBarColour()).darker().getRGB());
                vLine(this.x + width + 10, this.y + 11, this.y + (size * height) + size - 11, borderColor.getRGB());
            }
        }
    }

    @Override
    public Component mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (!this.visible || !this.enabled || this.loading)
            return null;

        int height = renderer != null ? renderer.getHeight() : 13;
        int size = getSize();
        if (RenderUtil.isMouseInside(mouseX, mouseY, x, y, x + width, y + size * height + size)) {
            for (int i = 0; i < size && i < items.size(); i++) {
                if (RenderUtil.isMouseInside(mouseX, mouseY, x + 1, y + (i * height) + i, x + width - 1, y + (i * height) + i + height)) {
                    if (mouseButton == 0) this.selected = i + offset;
                    if (itemClickListener != null) {
                        itemClickListener.onClick(items.get(i + offset), i + offset, mouseButton);
                        return this;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void mouseScrolled(int mouseX, int mouseY, boolean direction) {
        if (!this.visible || !this.enabled || this.loading)
            return;

        int height = renderer != null ? renderer.getHeight() : 13;
        int size = getSize();
        if (RenderUtil.isMouseInside(mouseX, mouseY, x, y, x + width, y + size * height + size)) {
            if (direction) {
                scrollUp();
            } else {
                scrollDown();
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        int size = getSize();
        return (renderer != null ? renderer.getHeight() : 13) * size + size + 1;
    }

    private int getSize() {
        if (showAll) return visibleItems;
        return Math.max(1, Math.min(visibleItems, items.size()));
    }

    private void scrollUp() {
        if (offset > 0) {
            offset--;
            updateButtons();
        }
    }

    private void scrollDown() {
        if (getSize() + offset < items.size()) {
            offset++;
            updateButtons();
        }
    }

    private void updateButtons() {
        btnDown.setEnabled(getSize() + offset < items.size());
        btnUp.setEnabled(offset > 0);
    }

    private void updateComponent() {
        btnUp.setVisible(items.size() > visibleItems);
        btnDown.setVisible(items.size() > visibleItems);
        btnDown.x = this.y + getHeight() - 12;

        if (!resized && items.size() > visibleItems) {
            width -= 11;
            resized = true;
        } else if (resized && items.size() <= visibleItems) {
            width += 11;
            resized = false;
        }
    }

    private void updateScroll() {
        if (offset + visibleItems > items.size()) {
            offset = Math.max(0, items.size() - visibleItems);
        }
    }

    /**
     * Sets the custom item list renderer.
     *
     * @param renderer the custom renderer
     */
    public void setListItemRenderer(ListItemRenderer<E> renderer) {
        this.renderer = renderer;
    }

    /**
     * Sets the item click listener for when an item is clicked.
     *
     * @param itemClickListener the item click listener
     */
    public void setItemClickListener(ItemClickListener<E> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * Appends an item to the list
     *
     * @param e the item
     */
    public void addItem(E e) {
        items.add(e);
        sort();
        if (initialized) {
            updateButtons();
            updateComponent();
        }
    }

    /**
     * Removes an item at the specified index
     *
     * @param index the index to remove
     */
    public E removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            E e = items.remove(index);
            if (index == selected)
                selected = -1;
            if (initialized) {
                updateButtons();
                updateComponent();
                updateScroll();
            }
            return e;
        }
        return null;
    }

    /**
     * Gets the items at the specified index
     *
     * @param pos the item's index
     * @return the item
     */
    public E getItem(int pos) {
        if (pos >= 0 && pos + offset < items.size()) {
            return items.get(pos + offset);
        }
        return null;
    }

    /**
     * Gets the selected item
     *
     * @return the selected item
     */
    @Nullable
    public E getSelectedItem() {
        if (selected >= 0 && selected < items.size()) {
            return items.get(selected);
        }
        return null;
    }

    /**
     * Gets the selected item's index
     *
     * @return the index
     */
    public int getSelectedIndex() {
        return selected;
    }

    /**
     * Sets the selected item in the list using the index
     *
     * @param index the index of the item
     */
    public void setSelectedIndex(int index) {
        if (index < 0) index = -1;
        this.selected = index;
    }

    /**
     * Gets all items from the list. Do not use this to remove items from the item list, instead use
     * {@link #removeItem(int)} otherwise it will cause scroll issues.
     *
     * @return the items
     */
    public List<E> getItems() {
        return items;
    }

    /**
     * Appends an item to the list
     *
     * @param newItems the items
     */
    public void setItems(List<E> newItems) {
        items.clear();
        items.addAll(newItems);
        sort();
        if (initialized) {
            offset = 0;
            updateButtons();
            updateComponent();
        }
    }

    /**
     * Removes all items from the list
     */
    public void removeAll() {
        this.items.clear();
        this.selected = -1;
        if (initialized) {
            updateButtons();
            updateComponent();
            updateScroll();
        }
    }

    /**
     * Sets the text color for this component
     *
     * @param color the text color
     */
    public void setTextColor(Color color) {
        this.textColor = color.getRGB();
    }

    /**
     * Sets the background color for this component
     *
     * @param color the border color
     */
    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

    /**
     * Sets the border color for this component
     *
     * @param color the border color
     */
    public void setBorderColor(Color color) {
        this.borderColor = color;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        if (initialized) {
            layoutLoading.setVisible(loading);
        }
    }

    public void setVisibleItems(int visibleItems) {
        this.visibleItems = visibleItems;
    }

    /**
     * Sets the sorter for this item list and updates straight away
     *
     * @param sorter the comparator to sort the list by
     */
    public void sortBy(Comparator<E> sorter) {
        this.sorter = sorter;
        sort();
    }

    /**
     * Sorts the list
     */
    public void sort() {
        if (sorter != null) {
            items.sort(sorter);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return items.iterator();
    }
}
