// Creator: Aliqi
// Create at: 2022/4/16
package java2d.game;

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

@Unique
public class Transform extends GameComponent {

    protected final AffineTransform localNoOriginTransform = new AffineTransform();

    protected final AffineTransform localTransform = new AffineTransform();

    protected final AffineTransform globalTransform = new AffineTransform();

    private double px, py;  // position

    private double ox, oy;  // origin

    private double sx = 1, sy = 1;  // scale

    private double r;  // rotation

    private boolean isDirty = true;

    private final AffineTransform tempTransform = new AffineTransform();

    public AffineTransform getLocalTransform() {
        calculateLocalTransform();
        return localTransform;
    }

    public Point2D getPosition() {
        invalidate();
        return new Point2D.Double(tempTransform.getTranslateX(), tempTransform.getTranslateY());
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

    public Point2D getOrigin() {
        return new Point2D.Double(ox, oy);
    }

    public void setOrigin(double x, double y) {
        ox = x;
        oy = y;
        isDirty = true;
    }

    public void setOrigin(Point2D origin) {
        ox = origin.getX();
        oy = origin.getY();
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
        tempTransform.setTransform(localNoOriginTransform);

        GameObject parent = gameObject.getParent();

        if (parent != null) {
            globalTransform.preConcatenate(parent.transform.globalTransform);
            tempTransform.preConcatenate(parent.transform.globalTransform);
        }
    }

    void calculateLocalTransform() {
        if (isDirty) {
            double ax = ox * sx;
            double ay = oy * sy;
            double rad = Math.toRadians(r);

            localTransform.setToIdentity();
            localTransform.translate(px - ax, py - ay);
            localTransform.rotate(rad, ax, ay);
            localTransform.scale(sx, sy);

            localNoOriginTransform.setToIdentity();
            localNoOriginTransform.translate(px, py);
            localNoOriginTransform.scale(sx, sy);

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
