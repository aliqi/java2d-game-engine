package java2d.game;

import java.awt.geom.AffineTransform;

/**
 * Author:     Zhao Yan
 * DateTime:   2022/5/28 16:09
 */
@Unique
public class Camera extends GameComponent {

    private final AffineTransform transform = new AffineTransform();

    public void updateTransform() {
        AffineTransform t = gameObject.transform.globalTransform;
        transform.setTransform(t);
        transform.setTransform(t.getScaleX(), -t.getShearY(),
                -t.getShearX(), t.getScaleY(),
                -t.getTranslateX(), -t.getTranslateY());
    }

    public AffineTransform getTransform() {
        return transform;
    }
}
