package io.github.vampirestudios.hgm.api.app.component;

import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.StringUtils;
import io.github.vampirestudios.hgm.api.app.Component;

import java.util.ArrayList;
import java.util.List;

public class HoverIcon extends Component {
    protected final List<String> lines = new ArrayList<>();

    public HoverIcon(int x, int y, int width, int height, String key, Object... args) {
        super(x, y, width, height);
        this.setInfoLines(key, args);
    }

    protected void setInfoLines(String key, Object... args) {
        String[] split = StringUtils.translate(key, args).split("\\n");
        String[] var4 = split;
        int var5 = split.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String str = var4[var6];
            this.lines.add(str);
        }

    }

    public void addLines(String... lines) {
        String[] var2 = lines;
        int var3 = lines.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String line = var2[var4];
            line = StringUtils.translate(line, new Object[0]);
            String[] split = line.split("\\n");
            String[] var7 = split;
            int var8 = split.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                String str = var7[var9];
                this.lines.add(str);
            }
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
