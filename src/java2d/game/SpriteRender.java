// Creator: Aliqi
// Create at: 2022/4/16
package java2d.game;

import java.awt.*;
import java.awt.geom.AffineTransform;

@Unique
public class SpriteRender extends Graphics2DRender {

    public Sprite sprite;

    private final AffineTransform transform = new AffineTransform();

    @Override
    protected void render(GameScene scene, Graphics2D g) {
        if (sprite != null && gameObject != null) {
            Camera camera = scene.getCamera();

            if (camera == null)
                transform.setTransform(gameObject.transform.globalTransform);
            else {
                AffineTransform ct = camera.getTransform();
                transform.setTransform(ct);
                transform.concatenate(gameObject.transform.globalTransform);
            }

            g.drawImage(sprite.getImage(), transform, null);
        }
    }
}
