package java2d.game.ui;

import java2d.game.UIElement;

import java.awt.*;

public class Picture extends UIElement {

    public Image image;

    public Picture() {
        setSize(100, 100);
    }

    public Picture(Image image) {
        this.image = image;
        setSize(image.getWidth(null), image.getHeight(null));
    }

    @Override
    protected void onRender(Graphics2D g) {
        if (image != null)
            g.drawImage(image, 0, 0, getWidth(), getHeight(), backgroundColor,null);
        drawBorder(g);
    }
}
