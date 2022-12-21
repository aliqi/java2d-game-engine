package java2d.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class UIElement extends GameObject {

    public boolean visible = true;

    public int renderOrder;

    public final Point2D origin = new Point2D.Double();

    public Color backgroundColor = Color.black;

    public Color borderColor = Color.darkGray;

    private int borderThickness = 0;

    private Dimension size = new Dimension();

    private Stroke stroke = new BasicStroke(borderThickness);

    private final List<UIElement> elements = new ArrayList<>();

    protected final AffineTransform affineTransform = new AffineTransform();

    public int getBorderThickness() {
        return borderThickness;
    }

    public void setBorderThickness(int borderThickness) {
        if (borderThickness != this.borderThickness) {
            this.borderThickness = borderThickness;
            stroke = new BasicStroke(borderThickness);
        }
    }

    public int getWidth() {
        return size.width;
    }

    public int getHeight() {
        return size.height;
    }

    public Dimension getSize() {
        return new Dimension(size);
    }

    public void setSize(Dimension size) {
        Dimension temp = new Dimension();

        if (size != null)
            temp.setSize(size);

        boolean changed = !temp.equals(this.size);
        this.size = temp;

        if (changed)
            onSizeChanged();
    }

    public void setSize(int width, int height) {
        boolean changed = width != size.width || height != size.height;
        size.setSize(width, height);

        if (changed)
            onSizeChanged();
    }

    protected void onSizeChanged() {
    }

    protected void onPrepare(Graphics2D g) {
    }

    void prepare(UIElement el, Graphics2D g) {
        onPrepare(g);

        affineTransform.setToIdentity();
        affineTransform.translate(-size.width * origin.getX(), -size.height * origin.getY());
        affineTransform.preConcatenate(transform.localTransform);

        if (el != null) {
            GameObject parent = el.getParent();

            if (parent instanceof UIElement) {
                affineTransform.preConcatenate(((UIElement) parent).affineTransform);
            }
        }
    }

    protected void beforeRender(Graphics2D g) {
        setupAntialias(g);
        g.transform(affineTransform);
    }

    protected void afterRender(Graphics2D g) {
    }

    protected void onRender(Graphics2D g) {
        drawBackground(g);
        drawBorder(g);
    }

    protected void drawBorder(Graphics2D g) {
        if (borderThickness > 0) {
            g.setColor(borderColor);
            g.setStroke(stroke);

            g.drawLine(0, 0, size.width, 0);
            g.drawLine(0, 0, 0, size.height);
            g.drawLine(size.width, 0, size.width, size.height);
            g.drawLine(0, size.height, size.width, size.height);
        }
    }

    protected void drawBackground(Graphics2D g) {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, size.width, size.height);
    }

    void renderElement(Graphics2D g) {
        if (visible) {
            AffineTransform at = g.getTransform();
            prepare(this, g);
            beforeRender(g);
            onRender(g);
            g.setTransform(at);
            renderChildren(g);
            afterRender(g);
        }
    }

    void renderChildren(Graphics2D g) {
        Color c = g.getColor();
        Stroke s = g.getStroke();
        Font f = g.getFont();

        elements.clear();

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            GameObject child = get(i);
            if (child instanceof UIElement)
                elements.add((UIElement) child);
        }

        elements.sort(Comparator.comparingInt(u -> u.renderOrder));

        for (UIElement element : elements)
            element.renderElement(g);

        g.setStroke(s);
        g.setColor(c);
        g.setFont(f);
    }
}
