package io.github.vampirestudios.hgm.api.app.component;

import io.github.vampirestudios.hgm.api.app.Component;
import io.github.vampirestudios.hgm.utils.GuiIcon;
import io.github.vampirestudios.hgm.utils.RenderingUtils;
import net.minecraft.client.MinecraftClient;

public class Icon extends Component {
    protected final GuiIcon icon;

    public Icon(int x, int y, GuiIcon icon) {
        super(x, y);
        this.width = icon.getWidth();
        this.height = icon.getHeight();
        this.icon = icon;
    }

    public void render(boolean enabled, boolean selected) {
        RenderingUtils.color(1.0F, 1.0F, 1.0F, 1.0F);
        MinecraftClient.getInstance().getTextureManager().bindTexture(this.icon.getTexture());
        this.icon.renderAt(this.x, this.y, (float)this.zLevel, enabled, selected);
        if (selected) {
            RenderingUtils.drawOutlinedBox(this.x, this.y, this.width, this.height, 549503168, -520093697);
        }

    }
}
