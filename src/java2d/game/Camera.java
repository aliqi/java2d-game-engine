package java2d.game;

import java.awt.*;
import java.awt.geom.AffineTransform;

@Unique
public class Camera extends GameComponent {

    private final AffineTransform affineTransform = new AffineTransform();

    public void updateTransform() {
        gameObject.transform.invalidate();

        Dimension renderSize = gameObject.getScene().getGame().getRenderSize();
        AffineTransform t = gameObject.transform.globalTransform;

        affineTransform.setTransform(t);
        affineTransform.setTransform(t.getScaleX(), -t.getShearY(),
                -t.getShearX(), t.getScaleY(),
                -t.getTranslateX() + renderSize.width * 0.5, -t.getTranslateY() + renderSize.height * 0.5);
    }

    public AffineTransform getAffineTransform() {
        return affineTransform;
    }
}
