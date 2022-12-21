package java2d.game;

import java.awt.*;
import java.awt.geom.Point2D;

public class CircleRender extends GraphicsRender {

    public final Point2D offset = new Point2D.Double();

    public int radius = 10;

    public Point2D getLocalCenter() {
        return new Point2D.Double(-radius + offset.getX(),
                -radius + offset.getY());
    }

    @Override
    protected void render(GameScene scene, Graphics2D g) {
        int length = radius * 2;
        Point2D center = getLocalCenter();

        g.transform(affineTransform);
        g.drawOval((int) center.getX(), (int) center.getY(), length, length);
    }
}
