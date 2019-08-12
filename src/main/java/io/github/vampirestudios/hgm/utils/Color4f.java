//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.vampirestudios.hgm.utils;

public class Color4f {
    public static final Color4f ZERO = new Color4f(0.0F, 0.0F, 0.0F, 0.0F);
    public final float r;
    public final float g;
    public final float b;
    public final float a;
    public final int intValue;

    public Color4f(float r, float g, float b) {
        this(r, g, b, 1.0F);
    }

    public Color4f(float r, float g, float b, float a) {
        if (r == -0.0F) {
            r = 0.0F;
        }

        if (g == -0.0F) {
            g = 0.0F;
        }

        if (b == -0.0F) {
            b = 0.0F;
        }

        if (a == -0.0F) {
            a = 0.0F;
        }

        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.intValue = (int)(a * 255.0F) << 24 | (int)(r * 255.0F) << 16 | (int)(g * 255.0F) << 8 | (int)(b * 255.0F);
    }

    public static Color4f fromColor(int color) {
        float alpha = (float)((color & -16777216) >>> 24) / 255.0F;
        return fromColor(color, alpha);
    }

    public static Color4f fromColor(int color, float alpha) {
        float r = (float)((color & 16711680) >>> 16) / 255.0F;
        float g = (float)((color & 1044480) >>> 8) / 255.0F;
        float b = (float)(color & 255) / 255.0F;
        return new Color4f(r, g, b, alpha);
    }

    public static Color4f fromColor(Color4f color, float alpha) {
        return new Color4f(color.r, color.g, color.b, alpha);
    }
}
