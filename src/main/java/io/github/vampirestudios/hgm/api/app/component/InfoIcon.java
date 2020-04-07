package io.github.vampirestudios.hgm.api.app.component;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.hgm.api.IGuiIcon;
import net.minecraft.client.MinecraftClient;

public class InfoIcon extends HoverIcon {
    protected final IGuiIcon icon;

    public InfoIcon(int x, int y, IGuiIcon icon, String key, Object... args) {
        super(x, y, icon.getWidth(), icon.getHeight(), key, args);
        this.icon = icon;
    }

    public void render(int mouseX, int mouseY, boolean selected) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        MinecraftClient.getInstance().getTextureManager().bindTexture(this.icon.getTexture());
        this.icon.renderAt(this.x, this.y, (float)this.zLevel, false, selected);
    }
}
