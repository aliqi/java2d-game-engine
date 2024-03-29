// Creator: Aliqi
// Create at: 2022/4/16
package java2d.game;

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

@Unique
public class Transform extends GameComponent {

    protected final AffineTransform localTransform = new AffineTransform();

    protected final AffineTransform globalTransform = new AffineTransform();

    private double px, py;  // position

    private double sx = 1, sy = 1;  // scale

    private double r;  // rotation

    private boolean isDirty = true;

    public AffineTransform getLocalTransform() {
        calculateLocalTransform();
        return localTransform;
    }

    public AffineTransform getGlobalTransform() {
        invalidate();
        return globalTransform;
    }

    public Point2D getPosition() {
        invalidate();
        return new Point2D.Double(globalTransform.getTranslateX(), globalTransform.getTranslateY());
    }

    public void setPosition(double x, double y) {
        setPosition(new Point2D.Double(x, y));
    }

    public void setPosition(Point2D p) {
        GameObject parent = gameObject.getParent();
        if (parent == null) {
            px = p.getX();
            py = p.getY();
        } else {
            try {
                Point2D localP = parent.transform.globalTransform.inverseTransform(p, null);
                px = localP.getX();
                py = localP.getY();
            } catch (NoninvertibleTransformException e) {
                e.printStackTrace();
            }
        }

        isDirty = true;
    }

    public Point2D getLocalPosition() {
        return new Point2D.Double(px, py);
    }

    public void setLocalPosition(Point2D localPosition) {
        px = localPosition.getX();
        py = localPosition.getY();
        isDirty = true;
    }

    public void setLocalPosition(double x, double y) {
        px = x;
        py = y;
        isDirty = true;
    }

    public Point2D getLocalScale() {
        return new Point2D.Double(sx, sy);
    }

    public void setLocalScale(double x, double y) {
        sx = x;
        sy = y;
        isDirty = true;
    }

    public void setLocalScale(Point2D scale) {
        sx = scale.getX();
        sy = scale.getY();
        isDirty = true;
    }

    public double getLocalRotation() {
        return r;
    }

    public void setLocalRotation(double rotation) {
        r = rotation;
        isDirty = true;
    }

    Transform(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public void invalidate() {
        calculateLocalTransform();
        globalTransform.setTransform(localTransform);

        GameObject parent = gameObject.getParent();

        if (parent != null) {
            // apply parent transform.
            globalTransform.preConcatenate(parent.transform.globalTransform);
        }
    }

    void calculateLocalTransform() {
        if (isDirty) {
            double rad = Math.toRadians(r);

            localTransform.setToIdentity();
            localTransform.translate(px, py);
            localTransform.rotate(rad);
            localTransform.scale(sx, sy);

            isDirty = false;
        }
    }

    public void translate(Point2D point) {
        px += point.getX();
        py += point.getY();
        isDirty = true;
    }

    public void translate(double x, double y) {
        px += x;
        py += y;
        isDirty = true;
    }

    @Override
    protected void update() {
        invalidate();
    }
}
