package io.github.vampirestudios.hgm.utils;

import net.minecraft.client.MinecraftClient;

public class Point implements Cloneable {
    public int x;
    public int y;

    public Point() {
        this(0, 0);
    }

    public Point(Point p) {
        this(p.x, p.y);
    }

    public Point(double x, double y) {
        this((int) x, (int) y);
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Point fromMouse() {
        MinecraftClient client = MinecraftClient.getInstance();
        double mx = client.mouse.getX() * (double) client.getWindow().getScaledWidth() / (double) client.getWindow().getWidth();
        double my = client.mouse.getY() * (double) client.getWindow().getScaledHeight() / (double) client.getWindow().getHeight();
        return new Point(mx, my);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point getLocation() {
        return new Point(x, y);
    }

    @Override
    public Point clone() {
        return getLocation();
    }

    public void setLocation(double x, double y) {
        this.x = (int) Math.floor(x + 0.5);
        this.y = (int) Math.floor(y + 0.5);
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point pt = (Point) obj;
            return (x == pt.x) && (y == pt.y);
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getClass().getName() + "[x=" + x + ",y=" + y + "]";
    }
}