package java2d.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class LineRender extends GraphicsRender {

    public final List<Point2D> points = new ArrayList<>();

    public Color color = Color.white;

    public boolean closed;

    public BasicStroke stroke = new BasicStroke(1f);

    private final AffineTransform transform = new AffineTransform();

    public LineRender() {
    }

    @Override
    protected void render(GameScene scene, Graphics2D g) {
        Color c = g.getColor();
        Stroke s = g.getStroke();
        AffineTransform t = g.getTransform();

        updateTransform(scene, transform);

        g.setColor(color);
        g.setStroke(stroke);
        g.transform(transform);

        int size = points.size();
        int last = size - 1;

        if (size > 1) {
            for (int i = 0; i < size; i++) {
                Point2D p = points.get(i);
                Point2D n;

                if (i == last) {
                    n = closed ? points.get(0) : null;
                } else {
                    n = points.get(i + 1);
                }

                if (n != null)
                    g.drawLine((int) p.getX(), (int) p.getY(), (int) n.getX(), (int) n.getY());
            }
        }

        g.setTransform(t);
        g.setStroke(s);
        g.setColor(c);
    }
}
