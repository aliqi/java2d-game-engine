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

    static Point2D add(Point2D src, Point2D target) {
        return new Point2D.Double(src.getX() + target.getX(),
                src.getY() + target.getY());
    }

    static Point2D subtract(Point2D src, Point2D target) {
        return new Point2D.Double(src.getX() - target.getX(),
                src.getY() - target.getY());
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

    static void normalize(Point2D point) {
        if (point.getX() == 0 && point.getY() == 0)
            return;
        double length = point.distance(0, 0);
        point.setLocation(point.getX() / length, point.getY() / length);
    }

    static Point2D normalized(Point2D point) {
        Point2D n = new Point2D.Double(point.getX(), point.getY());
        normalize(n);
        return n;
    }

    static void setLength(Point2D point, double length) {
        normalize(point);
        point.setLocation(point.getX() * length, point.getY() * length);
    }

    static Point2D newLength(Point2D point, double length) {
        Point2D result = clone(point);
        setLength(result, length);
        return result;
    }

    static double distance(Point2D src, Point2D target) {
        double dx = target.getX() - src.getX();
        double dy = target.getY() - src.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    static Point2D clone(Point2D point) {
        return new Point2D.Double(point.getX(), point.getY());
    }

    static Point2D lerp(Point2D from, Point2D to, double amount) {
        Point2D d = subtract(to, from);
        normalize(d);
        d.setLocation(from.getX() + d.getX() * amount,
                from.getY() + d.getY() * amount);
        return d;
    }

    static Point2D moveTowards(Point2D current, Point2D target, double maxDistanceDelta) {
        double dx = target.getX() - current.getX();
        double dy = target.getY() - current.getY();
        double sqDist = dx * dx + dy * dy;

        if (sqDist == 0 || (maxDistanceDelta >= 0 && sqDist <= maxDistanceDelta * maxDistanceDelta))
            return target;

        var dist = Math.sqrt(sqDist);

        return new Point2D.Double(current.getX() + dx / dist * maxDistanceDelta,
                current.getY() + dy / dist * maxDistanceDelta);
    }
}
