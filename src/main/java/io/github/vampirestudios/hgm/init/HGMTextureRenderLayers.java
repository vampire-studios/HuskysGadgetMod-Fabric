package io.github.vampirestudios.hgm.init;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Comparator;

import static io.github.vampirestudios.hgm.HuskysGadgetMod.MOD_ID;

public class HGMTextureRenderLayers {

    public static final Identifier GAMING_DESKS_ATLAS_TEXTURE = new Identifier("hgm", "textures/atlas/gaming_desks.png");
    public static final Identifier LAPTOPS_ATLAS_TEXTURE = new Identifier("hgm", "textures/atlas/laptops.png");
    public static final Identifier DESKTOPS_ATLAS_TEXTURE = new Identifier("hgm", "textures/atlas/desktops.png");
    public static final Identifier THREE_DEE_PRINTERS_ATLAS_TEXTURE = new Identifier("hgm", "textures/atlas/three_dee_printers.png");

    private static final RenderLayer GAMING_DESKS_RENDER_LAYER = RenderLayer.getEntitySolid(GAMING_DESKS_ATLAS_TEXTURE);
    private static final RenderLayer LAPTOPS_RENDER_LAYER = RenderLayer.getEntitySolid(LAPTOPS_ATLAS_TEXTURE);
    private static final RenderLayer DESKTOPS_RENDER_LAYER = RenderLayer.getEntitySolid(DESKTOPS_ATLAS_TEXTURE);
    private static final RenderLayer THREE_DEE_PRINTERS_RENDER_LAYER = RenderLayer.getEntitySolid(THREE_DEE_PRINTERS_ATLAS_TEXTURE);

    public static final SpriteIdentifier[] GAMING_DESK_TEXTURES = Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::getId)).map((dyeColor) ->
            new SpriteIdentifier(GAMING_DESKS_ATLAS_TEXTURE, new Identifier(MOD_ID, "model/gaming_desk/" + dyeColor.getName()))).toArray(SpriteIdentifier[]::new);
    public static final SpriteIdentifier[] LAPTOP_TEXTURES = Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::getId)).map((dyeColor) ->
            new SpriteIdentifier(LAPTOPS_ATLAS_TEXTURE, new Identifier(MOD_ID, "model/laptop/" + dyeColor.getName()))).toArray(SpriteIdentifier[]::new);
    public static final SpriteIdentifier[] DESKTOP_TEXTURES = Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::getId)).map((dyeColor) ->
            new SpriteIdentifier(DESKTOPS_ATLAS_TEXTURE, new Identifier(MOD_ID, "model/desktop/" + dyeColor.getName()))).toArray(SpriteIdentifier[]::new);
    public static final SpriteIdentifier[] THREE_DEE_PRINTER_TEXTURES = Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::getId)).map((dyeColor) ->
            new SpriteIdentifier(THREE_DEE_PRINTERS_ATLAS_TEXTURE, new Identifier(MOD_ID, "model/3d_printer/" + dyeColor.getName()))).toArray(SpriteIdentifier[]::new);

    public static RenderLayer getGamingDesks() {
        return GAMING_DESKS_RENDER_LAYER;
    }

    public static RenderLayer getLaptops() {
        return LAPTOPS_RENDER_LAYER;
    }

    public static RenderLayer getDesktops() {
        return DESKTOPS_RENDER_LAYER;
    }

    public static RenderLayer getThreeDeePrinters() {
        return THREE_DEE_PRINTERS_RENDER_LAYER;
    }

}
