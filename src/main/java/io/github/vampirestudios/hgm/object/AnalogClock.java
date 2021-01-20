package io.github.vampirestudios.hgm.object;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.hgm.api.app.Component;
import io.github.vampirestudios.hgm.core.BaseDevice;
import io.github.vampirestudios.hgm.utils.GLHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * Used to render an analog clock onto the screen. Uses the current minecraft time to display as the current time.
 *
 * @author Ocelot5836
 */
public class AnalogClock extends Component {

    protected int width;
    protected int height;
    protected double xScale;
    protected double yScale;

    public AnalogClock(int left, int top, int width, int height) {
        super(left, top);
        this.width = width;
        this.height = height;
        this.xScale = (double) this.width / 100.0;
        this.yScale = (double) this.height / 100.0;
    }

    @Override
    public void render(BaseDevice laptop, MinecraftClient mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
        double length = 40;
        double angle = 0;

        RenderSystem.pushMatrix();
        RenderSystem.translatef(this.x + this.width / 2, this.y + this.height / 2, 0);
        RenderSystem.scaled(this.xScale, this.yScale, 0);
        drawFilledCircle(0, 0, length + 5, Color.WHITE.getRGB());
        drawCircle(0, 0, length + 5, Color.DARK_GRAY.getRGB());
        RenderSystem.popMatrix();

        RenderSystem.pushMatrix();
        angle = Math.toRadians((getMinutes(mc.player.world.getTimeOfDay()) - 15.0) / 60.0 * 360.0);
        drawHand(this.x + this.width / 2 + calculateXPoint(angle, length - 4), this.y + this.height / 2 + calculateYPoint(angle, length - 4), 2.5 * this.xScale, Color.BLACK.getRGB());
        RenderSystem.popMatrix();

        RenderSystem.pushMatrix();
        angle = Math.toRadians((getHours(mc.player.world.getTimeOfDay()) - 3.0) / 12.0 * 360.0);
        drawHand(this.x + this.width / 2 + calculateXPoint(angle, length - 8), this.y + this.height / 2 + calculateYPoint(angle, length - 8), 5 * this.xScale, Color.BLACK.getRGB());
        RenderSystem.popMatrix();

        RenderSystem.pushMatrix();
        int numberColor = Color.DARK_GRAY.getRGB();
        RenderSystem.translatef(this.x, this.y, 0);
        drawNumber(1, 5.235987755982989, length, numberColor);
        drawNumber(2, 5.759586531581287, length, numberColor);
        drawNumber(3, 6.283185307179586, length, numberColor);
        drawNumber(4, 6.8067840827778845, length, numberColor);
        drawNumber(5, 7.3303828583761845, length, numberColor);
        drawNumber(6, 7.853981633974483, length, numberColor);
        drawNumber(7, 8.377580409572781, length, numberColor);
        drawNumber(8, 2.6179938779914944, length, numberColor);
        drawNumber(9, 3.141592653589793, length, numberColor);
        drawNumber(10, 3.6651914291880923, length, numberColor);
        drawNumber(11, 4.1887902047863905, length, numberColor);
        drawNumber(12, 4.71238898038469, length, numberColor);
        RenderSystem.popMatrix();
    }

    protected void drawHand(double toX, double toY, double size, int color) {
        toX -= this.x + this.width / 2;
        toY -= this.y + this.height / 2;
        RenderSystem.lineWidth((float) size);
        GLHelper.color(color);
        RenderSystem.translatef(this.x + this.width / 2, this.y + this.height / 2, 0);
        RenderSystem.scaled(this.xScale, this.yScale, 0);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        RenderSystem.disableTexture();
        bufferbuilder.begin(GL11.GL_LINES, VertexFormats.POSITION);
        bufferbuilder.vertex(0.0D, 0.0D, 0.0D).next();
        bufferbuilder.vertex(toX, toY, 0.0D).next();
        tessellator.draw();
        RenderSystem.enableTexture();
    }

    protected void drawCircle(double x, double y, double radius, int color) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GLHelper.color(color);
        RenderSystem.disableTexture();
        bufferbuilder.begin(GL11.GL_POINTS, VertexFormats.POSITION);
        for (double i = 0; i <= 360; i += 0.1) {
            bufferbuilder.vertex(x + radius * Math.cos(Math.toRadians(i)), y - radius * Math.sin(Math.toRadians(i)), 0).next();
        }
        tessellator.draw();
        RenderSystem.enableTexture();
    }

    protected void drawFilledCircle(double x, double y, double radius, int color) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        GLHelper.color(color);
        RenderSystem.disableTexture();
        buffer.begin(GL11.GL_TRIANGLE_FAN, VertexFormats.POSITION);
        {
            buffer.vertex(x, y, 0);
            for (double angle = 0.0f; angle < 2 * 3.14159; angle += 0.2) {
                double x2 = x + Math.sin(angle) * radius;
                double y2 = y + Math.cos(angle) * radius;
                buffer.vertex(x2, y2, 0).next();
            }
        }
        tessellator.draw();
        RenderSystem.enableTexture();
    }

    protected void drawNumber(int number, double angle, double length, int color) {
        RenderSystem.pushMatrix();
        TextRenderer fontRenderer = MinecraftClient.getInstance().textRenderer;
        RenderSystem.scaled(this.xScale, this.yScale, 0);
        RenderSystem.translated(this.width / 2 * (1 / this.xScale) + calculateXPoint(angle, length - 2.5) - fontRenderer.getWidth(Integer.toString(number)) / 2 * this.xScale, this.height / 2 * (1 / this.yScale) + calculateYPoint(angle, length - 2.5) - 4, 0);
        fontRenderer.draw(Integer.toString(number), 0, 0, color);
        RenderSystem.popMatrix();
    }

    protected double calculateXPoint(double angle, double length) {
        return length * Math.cos(angle);
    }

    protected double calculateYPoint(double angle, double length) {
        return length * Math.sin(angle);
    }

    protected double getHours(long time) {
        return (time / 1000.0 + 7) % 24;
    }

    protected double getMinutes(long time) {
        return ((time % 1000) / 1000.0 * 60);
    }
}