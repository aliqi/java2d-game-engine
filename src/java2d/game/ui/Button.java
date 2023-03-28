package java2d.game.ui;

import java2d.game.Inputs;
import java2d.game.UIElement;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Button extends UIElement {

    public Image normal;

    public Image pressed;

    public Image hover;

    public int MouseButton = MouseEvent.BUTTON1;

    public final Text text = new Text();

    private Image image;

    private Inputs inputs;

    private boolean isMouseOver;

    public Button() {
        resetSize();
        setup();
    }

    public Button(Image normal) {
        if (normal != null) {
            this.normal = normal;
            resetSize();
        }
        setup();
    }

    private void setup() {
        backgroundColor = Color.lightGray;

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

        System.out.println(getSize());
    }

    @Override
    protected void onActivated() {
        inputs = getInputs();
        image = normal;
    }

    @Override
    protected void onRender(Graphics2D g) {
        if (!isMouseOver)
            image = normal;

        if (image == null)
            drawBackground(g);
        else
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        drawBorder(g);
    }

    @Override
    protected void onMouseOver() {
        isMouseOver = true;

        if (inputs.getMouseButton(MouseButton))
            image = pressed == null ? normal : pressed;
        else
            image = hover == null ? normal : hover;
    }

    @Override
    protected void onMouseExit() {
        isMouseOver = false;
    }
}
