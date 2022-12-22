package java2d.game;

import java.awt.*;
import java.awt.geom.Point2D;

public class StringGameObject extends GameObject {

    public StringRender getStringRender() {
        return getComponent(StringRender.class);
    }

    public Point2D getOrigin() {
        StringRender stringRender = getStringRender();
        return stringRender == null ? new Point2D.Double() : stringRender.getOrigin();
    }

    public void setOrigin(double ox, double oy) {
        StringRender stringRender = getStringRender();

        if (stringRender != null)
            stringRender.setOrigin(ox, oy);
    }

    public void setOrigin(Point2D origin) {
        StringRender stringRender = getStringRender();

        if (stringRender != null)
            stringRender.setOrigin(origin);
    }

    public String getText() {
        StringRender render = getStringRender();
        return render == null ? null : render.text;
    }

    public void setText(String text) {
        StringRender render = getStringRender();

        if (render != null)
            render.text = text;
    }

    public Color getColor() {
        StringRender render = getStringRender();
        return render == null ? null : render.color;
    }

    public void setColor(Color color) {
        StringRender render = getStringRender();

        if (render != null)
            render.color = color;
    }

    public Font getFont() {
        StringRender render = getStringRender();
        return render == null ? null : render.font;
    }

    public void setFont(Font font) {
        StringRender render = getStringRender();

        if (render != null)
            render.font = font;
    }

    public StringGameObject() {
        addComponent(new StringRender());
    }

    public StringGameObject(String text) {
        this(text, Color.lightGray);
    }

    public StringGameObject(String text, Color color) {
        StringRender render = new StringRender();
        render.text = text;
        render.color = color;
        addComponent(render);
    }
}
