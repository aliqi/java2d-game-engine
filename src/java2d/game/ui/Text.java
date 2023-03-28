package java2d.game.ui;

import java2d.game.StringRender;
import java2d.game.UIElement;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Text extends UIElement {

    public String content;

    public Font font;

    public Color color = Color.white;

    public boolean antialiasEnabled = true;

    public boolean boundsVisible = false;

    public final Point2D offset = new Point2D.Double();

    private Rectangle2D bounds;

    @Override
    protected void onPrepare(Graphics2D g) {
        Font f = font;

        if (f == null)
            f = g.getFont();

        StringRender.setupTextAntialias(g, antialiasEnabled);
        bounds = StringRender.calculateBounds(content, g, f);
        setSize((int) bounds.getWidth(), (int) bounds.getHeight());
    }

    @Override
    protected void onRender(Graphics2D g) {
        if (content == null)
            return;

        Font f = font;

        if (f == null)
            f = g.getFont();

        g.setColor(color);
        g.setFont(f);

        g.drawString(content, (int) offset.getX(),
                (int) (offset.getY() + bounds.getHeight()));

        if (boundsVisible)
            g.drawRect((int) Math.floor(offset.getX()), (int) Math.floor(offset.getY()),
                    (int) Math.ceil(bounds.getWidth()), (int) Math.ceil(bounds.getHeight()));
    }
}
