package io.github.vampirestudios.hgm.api.app.component;

import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.StringUtils;
import io.github.vampirestudios.hgm.api.app.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HoverIcon extends Component {
    protected final List<String> lines = new ArrayList<>();

    public HoverIcon(int x, int y, int width, int height, String key, Object... args) {
        super(x, y, width, height);
        this.setInfoLines(key, args);
    }

    protected void setInfoLines(String key, Object... args) {
        String[] split = StringUtils.translate(key, args).split("\\n");
        int var5 = split.length;

        this.lines.addAll(Arrays.asList(split).subList(0, var5));
    }

    public void addLines(String... lines) {
        for (String s : lines) {
            String line = s;
            line = StringUtils.translate(line);
            String[] split = line.split("\\n");
            int var8 = split.length;

            this.lines.addAll(Arrays.asList(split).subList(0, var8));
        }
    }

    public List<String> getLines() {
        return this.lines;
    }

    public void render(int mouseX, int mouseY, boolean selected) {
    }

    public void postRenderHovered(int mouseX, int mouseY, boolean selected) {
        RenderUtils.drawHoverText(mouseX, mouseY, this.lines);
    }
}
