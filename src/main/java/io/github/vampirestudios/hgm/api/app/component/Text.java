package io.github.vampirestudios.hgm.api.app.component;

import io.github.vampirestudios.hgm.api.app.Component;
import io.github.vampirestudios.hgm.core.BaseDevice;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Formatting;

import java.awt.*;
import java.util.List;

public class Text extends Component {

    protected String rawText;
    protected List<String> lines;
    protected int width;
    protected int padding;
    protected boolean shadow = false;

    protected int textColor = Color.WHITE.getRGB();

    private WordListener wordListener = null;

    /**
     * Default text constructor
     *
     * @param text  the text to display
     * @param left  how many pixels from the left
     * @param top   how many pixels from the top
     * @param width the max width
     */
    public Text(String text, int left, int top, int width) {
        super(left, top);
        this.width = width;
        this.setText(text);
    }

    @Override
    public void render(BaseDevice laptop, MinecraftClient mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
        if (this.visible) {
            for (int i = 0; i < lines.size(); i++) {
                String text = lines.get(i);
                while (text != null && text.endsWith("\n")) {
                    text = text.substring(0, text.length() - 1);
                }
                if (shadow)
                    BaseDevice.fontRenderer.drawWithShadow(text, x + padding, y + (i * 10) + padding, textColor);
                else
                    BaseDevice.fontRenderer.draw(text, x + padding, y + (i * 10) + padding, textColor);
            }
        }
    }

    /**
     * Sets the text for this component
     *
     * @param text the text
     */
    public void setText(String text) {
        rawText = text;
        text = text.replace("\\n", "\n");
        this.lines = BaseDevice.fontRenderer.wrapStringToWidthAsList(text, width - padding * 2);
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
     * Sets the whether shadow should show under the text
     *
     * @param shadow the text color
     */
    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }

    /**
     * @param padding
     */
    public void setPadding(int padding) {
        this.padding = padding;
        this.updateLines();
    }

    private void updateLines() {
        this.setText(rawText);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (this.wordListener != null && lines.size() > 0) {
            int lineIndex = (int) ((mouseY - (y + padding)) / 10);
            if (lineIndex >= 0 && lineIndex < lines.size()) {
                int cursorX = (int) (mouseX - (x + padding));
                String line = lines.get(lineIndex);
                int index = BaseDevice.fontRenderer.trimToWidth(line, cursorX).length();
                String clickedWord = getWord(line, index);
                if (clickedWord != null) {
                    this.wordListener.onWordClicked(clickedWord, mouseButton);
                    return true;
                }
                return false;
            }
        }
        return true;
    }

    private String getWord(String line, int index) {
        if (index >= line.length() || line.charAt(index) == ' ')
            return null;

        int startIndex = index;
        while (startIndex > 0 && line.charAt(startIndex - 1) != ' ') --startIndex;

        int endIndex = index;
        while (endIndex + 1 < line.length() && line.charAt(endIndex + 1) != ' ') ++endIndex;

        endIndex = Math.min(endIndex + 1, line.length());

        return Formatting.getFormatAtEnd(line.substring(startIndex, endIndex));
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return lines.size() * MinecraftClient.getInstance().textRenderer.fontHeight + lines.size() - 1 + padding * 2;
    }

    public void setWordListener(WordListener wordListener) {
        this.wordListener = wordListener;
    }

    public interface WordListener {
        void onWordClicked(String word, int mouseButton);
    }
}
