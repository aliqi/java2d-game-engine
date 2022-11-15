package java2d.game;

import java.awt.geom.Point2D;

public interface Maths {

    static double toRotation(double x, double y) {
        return Math.toDegrees(Math.atan2(y, x));
    }

    static double toRotation(Point2D direction) {
        return toRotation(direction.getX(), direction.getY());
    }

    static Point2D toDirection(double angle) {
        double radians = Math.toRadians(angle);
        return new Point2D.Double(Math.cos(radians), Math.sin(radians));
    }

    static Point2D multiple(Point2D p, double v) {
        return new Point2D.Double(p.getX() * v, p.getY() * v);
    }

    static int clamp(int value, int min, int max) {
        if (min > max) {
            int temp = max;
            max = min;
            min = temp;
        }

        return Math.min(Math.max(value, min), max);
    }

    static float clamp(float value, float min, float max) {
        if (min > max) {
            float temp = max;
            max = min;
            min = temp;
        }

        return Math.min(Math.max(value, min), max);
    }

    static double clamp(double value, double min, double max) {
        if (min > max) {
            double temp = max;
            max = min;
            min = temp;
        }

        return Math.min(Math.max(value, min), max);
    }
}