package io.github.vampirestudios.hgm.core.client;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.api.app.emojies.Icons;
import io.github.vampirestudios.hgm.api.utils.RenderUtil;
import io.github.vampirestudios.hgm.utils.Constants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

public class ClientNotification implements Toast {
    private static final Identifier TEXTURE_TOASTS = new Identifier(HuskysGadgetMod.MOD_ID, "textures/gui/toast.png");

    private Icons icon;
    private String title;
    private String subTitle;

    private ClientNotification() {
    }

    public static ClientNotification loadFromTag(CompoundTag tag) {
        ClientNotification notification = new ClientNotification();
        notification.icon = Icons.values()[tag.getInt("icon")];
        notification.title = tag.getString("title");
        if (tag.contains("subTitle", Constants.NBT.TAG_STRING)) {
            notification.subTitle = tag.getString("subTitle");
        }
        return notification;
    }

    @Override
    public Visibility draw(MatrixStack matrixStack, ToastManager toastGui, long delta) {
        RenderSystem.color3f(1.0F, 1.0F, 1.0F);
        toastGui.getGame().getTextureManager().bindTexture(TEXTURE_TOASTS);
        toastGui.drawTexture(matrixStack, 0, 0, 0, 0, 160, 32);

        toastGui.getGame().getTextureManager().bindTexture(icon.getIconAsset());
        RenderUtil.drawRectWithTexture(6, 6, icon.getU(), icon.getV(), 20, 20, 10, 10, 200, 200);

        if (subTitle == null) {
            toastGui.getGame().textRenderer.drawWithShadow(matrixStack, RenderUtil.clipStringToWidth(I18n.translate(title), 118), 38, 12, -1);
        } else {
            toastGui.getGame().textRenderer.drawWithShadow(matrixStack, RenderUtil.clipStringToWidth(I18n.translate(title), 118), 38, 7, -1);
            toastGui.getGame().textRenderer.draw(matrixStack, RenderUtil.clipStringToWidth(I18n.translate(subTitle), 118), 38, 18, -1);
        }

        return delta >= 5000L ? Visibility.HIDE : Visibility.SHOW;
    }

    public void push() {
        MinecraftClient.getInstance().getToastManager().add(this);
    }

}