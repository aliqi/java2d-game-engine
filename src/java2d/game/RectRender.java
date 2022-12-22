package java2d.game;

import java.awt.*;
import java.awt.geom.Point2D;

public class RectRender extends GraphicsRender {

    public final Point2D offset = new Point2D.Double();

    public int width = 100;

    public int height = 100;

    public RectRender() {
        color = Color.green;
        stroke = new BasicStroke(2f);
    }

    @Override
    protected void render(GameScene scene, Graphics2D g) {
        g.transform(affineTransform);
        g.drawRect((int) offset.getX(), (int) offset.getY(), width, height);
    }
}
