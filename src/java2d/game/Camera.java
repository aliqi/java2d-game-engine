package java2d.game;

import java.awt.geom.AffineTransform;

@Unique
public class Camera extends GameComponent {

    private final AffineTransform affineTransform = new AffineTransform();

    public void updateTransform() {
        AffineTransform t = gameObject.transform.globalTransform;
        affineTransform.setTransform(t);
        affineTransform.setTransform(t.getScaleX(), -t.getShearY(),
                -t.getShearX(), t.getScaleY(),
                -t.getTranslateX(), -t.getTranslateY());
    }

    public AffineTransform getAffineTransform() {
        return affineTransform;
    }
}
