package java2d.game;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class GraphicsRender extends GameComponent implements RenderOrderable {

    private int renderOrder;

    @Override
    public int getRenderOrder() {
        return renderOrder;
    }

    @Override
    public void setRenderOrder(int renderOrder) {
        this.renderOrder = renderOrder;
    }

    protected abstract void render(GameScene scene, Graphics2D g);

    protected void updateTransform(GameScene scene, AffineTransform transform) {
        if (gameObject != null) {
            Camera camera = scene.getCamera();

            if (camera == null)
                transform.setTransform(gameObject.transform.globalTransform);
            else {
                AffineTransform ct = camera.getTransform();
                transform.setTransform(ct);
                transform.concatenate(gameObject.transform.globalTransform);
            }
        }
    }
}
