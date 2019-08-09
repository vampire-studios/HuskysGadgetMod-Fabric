package io.github.vampirestudios.hgm.utils;

import net.minecraft.util.math.MathHelper;

public class Vec2i {

   public final int x;
   public final int y;

   public Vec2i(int x, int y) {
      this.x = x;
      this.y = y;
   }

   public Vec2i(double x, double y) {
      this(MathHelper.floor(x), MathHelper.floor(y));
   }

   public boolean equals(Vec2i vec2I_1) {
      return this.x == vec2I_1.x && this.y == vec2I_1.y;
   }

}
