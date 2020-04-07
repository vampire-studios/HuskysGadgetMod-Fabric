package io.github.vampirestudios.hgm.api.app.component;

import io.github.vampirestudios.hgm.api.app.Component;
import io.github.vampirestudios.hgm.api.app.listener.ClickListener;
import net.minecraft.client.render.DiffuseLighting;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class Container extends Component {
    protected final List<Component> subWidgets = new ArrayList<>();
    @Nullable
    protected Component hoveredSubWidget = null;

    public Container(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    protected <T extends Component> T addWidget(T widget) {
        this.subWidgets.add(widget);
        return widget;
    }

    protected <T extends Button> T addButton(T button, ClickListener listener) {
        button.setClickListener(listener);
        this.addWidget(button);
        return button;
    }

    protected void addLabel(int x, int y, int width, int height, int textColor, String... lines) {
        if (lines != null && lines.length >= 1) {
            if (width == -1) {
                String[] var7 = lines;
                int var8 = lines.length;

                for(int var9 = 0; var9 < var8; ++var9) {
                    String line = var7[var9];
                    width = Math.max(width, this.textRenderer.getStringWidth(line));
                }
            }

            for (String line : lines) {
                Label label = new Label(line);
                label.setLocation(x, y);
                this.addWidget(label);
            }

            /*Label label = new Label(x, y, width, height, textColor, lines);
            this.addWidget(label);*/
        }

    }

    /*public Component mouseClicked(int mouseX, int mouseY, int mouseButton) {
        boolean handled = false;
        if (this.isMouseOver(mouseX, mouseY)) {
            if (!this.subWidgets.isEmpty()) {

                for (Component widget : this.subWidgets) {
                    if (widget.isMouseOver(mouseX, mouseY) && widget.mouseClicked(mouseX, mouseY, mouseButton)) {
                        handled = true;
                    }
                }
            }

            if (!handled) {
                handled = this.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }

        return handled;
    }*/

    public Component mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (!this.subWidgets.isEmpty()) {
            for (Component widget : this.subWidgets) {
                widget.mouseReleased(mouseX, mouseY, mouseButton);
            }
        }

        return this.mouseReleased(mouseX, mouseY, mouseButton);
    }

    /*public void onMouseScrolled(int mouseX, int mouseY, double mouseWheelDelta) {
        if (!this.isMouseOver(mouseX, mouseY)) {
            return false;
        } else {
            if (!this.subWidgets.isEmpty()) {
                Iterator var5 = this.subWidgets.iterator();

                while(var5.hasNext()) {
                    Component widget = (Component)var5.next();
                    if (widget.mouseScrolled(mouseX, mouseY, mouseWheelDelta)) {
                        return true;
                    }
                }
            }

            return this.mouseScrolled(mouseX, mouseY, mouseWheelDelta);
        }
    }*/

    /*public boolean onKeyTyped(int keyCode, int scanCode, int modifiers) {
        boolean handled = false;
        if (!this.subWidgets.isEmpty()) {
            Iterator var5 = this.subWidgets.iterator();

            while(var5.hasNext()) {
                Component widget = (Component)var5.next();
                if (widget.keyPressed(keyCode, scanCode, modifiers)) {
                    handled = true;
                }
            }
        }

        if (!handled) {
            handled = this.keyPressed(keyCode, scanCode, modifiers);
        }

        return handled;
    }*/

    /*public boolean onCharTyped(char charIn, int modifiers) {
        boolean handled = false;
        if (!this.subWidgets.isEmpty()) {

            for (Component widget : this.subWidgets) {
                *//*if (widget.charTyped(charIn, modifiers)) {
                    handled = true;
                }*//*
            }
        }

        if (!handled) {
            handled = this.charTyped(charIn, modifiers);
        }

        return handled;
    }*/

    public void render(int mouseX, int mouseY, boolean selected) {
//        this.drawSubWidgets(mouseX, mouseY);
    }

    public void postRenderHovered(int mouseX, int mouseY, boolean selected) {
        this.drawHoveredSubWidget(mouseX, mouseY);
    }

    /*protected void drawSubWidgets(int mouseX, int mouseY) {
        this.hoveredSubWidget = null;
        if (!this.subWidgets.isEmpty()) {
            Iterator var3 = this.subWidgets.iterator();

            while(var3.hasNext()) {
                Component widget = (Component)var3.next();
//                widget.render(mouseX, mouseY, 1F);
                if (widget.isMouseOver(mouseX, mouseY)) {
                    this.hoveredSubWidget = widget;
                }
            }
        }

    }*/

    protected void drawHoveredSubWidget(int mouseX, int mouseY) {
        if (this.hoveredSubWidget != null) {
//            this.hoveredSubWidget.render(mouseX, mouseY, 1F);
            DiffuseLighting.disable();
        }

    }
}
