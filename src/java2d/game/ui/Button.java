package java2d.game.ui;

import java2d.game.Inputs;
import java2d.game.UIElement;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

public class Button extends UIElement {

    public ClickedEvent clickedEvent;

    public Image normal;

    public Image pressed;

    public Image hover;

    public int MouseButton = MouseEvent.BUTTON1;

    public final Text text = new Text();

    private Image image;

    private Inputs inputs;

    public Button() {
        resetSize();
        setupText();
    }

    public Button(Image normal) {
        if (normal != null) {
            this.normal = normal;
            resetSize();
        }
        setupText();
    }

    private void setupText() {
        text.origin.setLocation(0.5, 0.5);
        text.content = "Button";
        text.transform.setPosition(getWidth() * 0.5, getHeight() * 0.5);
        add(text);
    }

    public void resetSize() {
        if (normal == null)
            setSize(200, 100);
        else
            setSize(normal.getWidth(null), normal.getHeight(null));
    }

    @Override
    protected void onActivated() {
        inputs = getInputs();
        image = normal;
    }

    @Override
    protected void onRender(Graphics2D g) {
        if (image != null)
            g.drawImage(image, 0, 0, getWidth(), getHeight(), backgroundColor, null);
        drawBorder(g);
    }

    @Override
    protected void onUpdate() {
        if (overlap(inputs.getMousePosition())) {
            if (inputs.getMouseButtonDown(MouseButton)) {
                if (pressed != null)
                    image = pressed;
            } else {
                if (hover != null)
                    image = hover;
            }

            if (inputs.getMouseButtonUp(MouseButton)) {
                image = normal;
                onClicked();

                if (clickedEvent != null)
                    clickedEvent.clicked(this);
            }
        } else
            image = normal;
    }

    protected void onClicked() {
    }

    protected boolean overlap(Point2D point) {
        try {
            affineTransform.inverseTransform(point, point);

            return point.getX() >= 0 && point.getX() <= getWidth() &&
                    point.getY() >= 0 && point.getY() <= getHeight();
        } catch (NoninvertibleTransformException e) {
            throw new RuntimeException(e);
        }
    }

    @FunctionalInterface
    public interface ClickedEvent {

        void clicked(Button button);
    }
}
