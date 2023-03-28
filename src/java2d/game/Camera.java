package java2d.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

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

    public Point2D screenToWorldPoint(Point2D point) {
        Point2D result = new Point2D.Double();
        try {
            affineTransform.inverseTransform(point, result);
        } catch (NoninvertibleTransformException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
