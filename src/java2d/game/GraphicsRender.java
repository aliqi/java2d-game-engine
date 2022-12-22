package java2d.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public abstract class GraphicsRender extends GameComponent implements RenderOrderable {

    public static final Color transparent = new Color(0, 0, 0, 0);

    public boolean visible = true;

    public BasicStroke stroke = new BasicStroke(1f);

    public Color color = Color.white;

    private int renderOrder;

    protected double originX, originY;

    protected double renderOriginX, renderOriginY;

    protected final AffineTransform affineTransform = new AffineTransform();

    public Point2D getOrigin() {
        return new Point2D.Double(originX, originY);
    }

    public void setOrigin(double x, double y) {
        originX = x;
        originY = y;
    }

    public void setOrigin(Point2D origin) {
        originX = origin.getX();
        originY = origin.getY();
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

            Color c = g.getColor();
            Stroke s = g.getStroke();
            Font f = g.getFont();
            AffineTransform pt = g.getTransform();

            g.setColor(color);
            g.setStroke(stroke);

            prepare(scene, g, affineTransform);
            updateTransform(scene, affineTransform);
            render(scene, g);

            g.setTransform(pt);
            g.setStroke(s);
            g.setColor(c);
            g.setFont(f);
        }
    }

    protected void prepare(GameScene scene, Graphics2D g, AffineTransform affineTransform) {
        scene.setupAntialias(g);
    }

    protected abstract void render(GameScene scene, Graphics2D g);

    protected void updateTransform(GameScene scene, AffineTransform at) {
        if (gameObject != null) {
            Transform transform = gameObject.transform;
            at.translate(-renderOriginX, -renderOriginY);
            at.preConcatenate(transform.globalTransform);

            Camera camera = scene.getCamera();

            if (camera != null)
                at.preConcatenate(camera.getAffineTransform());
        }
    }
}
