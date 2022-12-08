package java2d.game;

import java.awt.*;

public class StringGameObject extends GameObject {

    public StringRender getStringRender() {
        return getComponent(StringRender.class);
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

    public StringGameObject(String text, Color color) {
        StringRender render = new StringRender();
        render.text = text;
        render.color = color;
        addComponent(render);
    }
}
