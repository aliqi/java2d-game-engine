package java2d.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class StringRender extends GraphicsRender {

    public String text;

    public Font font;

    public boolean boundsVisible = false;

    public final Point2D offset = new Point2D.Double();

    private Rectangle2D bounds;

    @Override
    protected void prepare(GameScene scene, Graphics2D g, AffineTransform affineTransform) {
        if (text == null)
            return;

        Font f = font;

        if (f == null)
            f = g.getFont();

        bounds = f.getStringBounds(text, g.getFontRenderContext());
        renderOriginX = bounds.getWidth() * originX;
        renderOriginY = bounds.getHeight() * originY;
    }

    @Override
    protected void render(GameScene scene, Graphics2D g) {
        if (text == null)
            return;

        AffineTransform pt = g.getTransform();
        Font f = font;

        if (f == null)
            f = g.getFont();

        g.setFont(f);
        g.transform(affineTransform);
        g.drawString(text, (int) offset.getX(), (int) (offset.getY() + bounds.getHeight()));

        if (boundsVisible)
            g.drawRect((int) Math.floor(offset.getX()), (int) Math.floor(offset.getY()),
                    (int) Math.ceil(bounds.getWidth()), (int) Math.ceil(bounds.getHeight()));

        g.setTransform(pt);
    }
}
