package java2d.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public abstract class GraphicsRender extends GameComponent implements RenderOrderable {

    public boolean visible = true;

    public BasicStroke stroke = new BasicStroke(1f);

    public Color color = Color.white;

    private int renderOrder;

    private double ox, oy;

    protected final AffineTransform affineTransform = new AffineTransform();

    public Point2D getOrigin() {
        return new Point2D.Double(ox, oy);
    }

    public void setOrigin(double x, double y) {
        ox = x;
        oy = y;
    }

    public void setOrigin(Point2D origin) {
        ox = origin.getX();
        oy = origin.getY();
    }

    @Override
    public int getRenderOrder() {
        return renderOrder;
    }

    @Override
    public void setRenderOrder(int renderOrder) {
        this.renderOrder = renderOrder;
    }

    void internalRender(GameScene scene, Graphics2D g) {
        if (visible) {
            affineTransform.setToIdentity();
            updateTransform(scene, affineTransform);

            Color c = g.getColor();
            Stroke s = g.getStroke();

            g.setColor(color);
            g.setStroke(stroke);

            render(scene, g);

            g.setStroke(s);
            g.setColor(c);
        }
    }

    protected abstract void render(GameScene scene, Graphics2D g);

    protected void updateTransform(GameScene scene, AffineTransform at) {
        if (gameObject != null) {
            Transform transform = gameObject.transform;
            at.translate(-ox, -oy);
            at.preConcatenate(transform.globalTransform);

            Camera camera = scene.getCamera();

            if (camera != null)
                at.preConcatenate(camera.getTransform());
        }
    }
}
