//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.vampirestudios.hgm.api.app.component;

import io.github.vampirestudios.hgm.api.app.Component;
import io.github.vampirestudios.hgm.utils.GuiIcon;
import io.github.vampirestudios.hgm.utils.LeftRight;
import io.github.vampirestudios.hgm.utils.RenderingUtils;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.screen.Screen;

public class SearchBar extends Component {
    public final Icon iconSearch;
    public final LeftRight iconAlignment;
//    public final GuiTextFieldGeneric searchBox;
    public boolean searchOpen;

    public SearchBar(int x, int y, int width, int height, int searchBarOffsetX, GuiIcon iconSearch, LeftRight iconAlignment) {
        super(x, y, width, height);
        int iw = iconSearch.getWidth();
        int ix = iconAlignment == LeftRight.RIGHT ? x + width - iw - 1 : x + 2;
        int tx = iconAlignment == LeftRight.RIGHT ? x - searchBarOffsetX + 1 : x + iw + 6 + searchBarOffsetX;
        this.iconSearch = new Icon(ix, y + 1, iconSearch);
        this.iconAlignment = iconAlignment;
//        this.searchBox = new GuiTextFieldGeneric(tx, y, width - iw - 7 - Math.abs(searchBarOffsetX), height, this.textRenderer);
//        this.searchBox.setZLevel(this.zLevel);
    }

    /*public String getFilter() {
        return this.searchOpen ? this.searchBox.getText() : "";
    }

    public boolean hasFilter() {
        return this.searchOpen && !this.searchBox.getText().isEmpty();
    }*/

    public boolean isSearchOpen() {
        return this.searchOpen;
    }

    public void setSearchOpen(boolean isOpen) {
        this.searchOpen = isOpen;
        if (this.searchOpen) {
//            this.searchBox.setFocused(true);
        }

    }

    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        /*if (this.searchOpen && this.searchBox.mouseClicked((double)mouseX, (double)mouseY, mouseButton)) {
            return true;
        } else if (this.iconSearch.isMouseOver(mouseX, mouseY)) {
            this.setSearchOpen(!this.searchOpen);
            return true;
        } else {
            return false;
        }*/
        return false;
    }

    protected boolean onKeyTypedImpl(int keyCode, int scanCode, int modifiers) {
        if (this.searchOpen) {
            /*if (this.searchBox.keyPressed(keyCode, scanCode, modifiers)) {
                return true;
            }*/

            if (keyCode == 256) {
                if (Screen.hasShiftDown()) {
                    this.mc.currentScreen.onClose();
                }

                this.searchOpen = false;
//                this.searchBox.setFocused(false);
                return true;
            }
        }

        return false;
    }

    protected boolean onCharTypedImpl(char charIn, int modifiers) {
        if (this.searchOpen) {
//            return this.searchBox.charTyped(charIn, modifiers);
        } else if (SharedConstants.isValidChar(charIn)) {
            this.searchOpen = true;
            /*this.searchBox.setFocused(true);
            this.searchBox.setText("");
            this.searchBox.tick();
            this.searchBox.charTyped(charIn, modifiers);*/
            return true;
        }

        return false;
    }

    public void render(int mouseX, int mouseY, boolean selected) {
        RenderingUtils.color(1.0F, 1.0F, 1.0F, 1.0F);
//        this.iconSearch.render(false, this.iconSearch.isMouseOver(mouseX, mouseY));
        if (this.searchOpen) {
//            this.searchBox.render(mouseX, mouseY, 0.0F);
        }

    }
}
