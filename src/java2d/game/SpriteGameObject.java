package java2d.game;

import java.awt.geom.Point2D;

public class SpriteGameObject extends GameObject {

    public SpriteRender getSpriteRender() {
        return getComponent(SpriteRender.class);
    }

    public Sprite getSprite() {
        SpriteRender render = getComponent(SpriteRender.class);
        return render == null ? null : render.sprite;
    }

    public SpriteGameObject(String spritePath, double ratioX, double ratioY) {
        this("SpriteGameObject", spritePath, ratioX, ratioY);
    }

    public SpriteGameObject(String name, String spritePath, double ratioX, double ratioY) {
        super(name);

        SpriteRender spriteRender = SpriteRender.create(spritePath);

        addComponent(spriteRender);

        setOrigin(ratioX, ratioY);
    }

    public SpriteGameObject(String name, String spritePath, Point2D originRatio) {
        this(name, spritePath, originRatio.getX(), originRatio.getY());
    }

    public SpriteGameObject(String name, String spritePath) {
        this(name, spritePath, 0, 0);
    }

    public void setOrigin(Point2D origin) {
        setOrigin(origin.getX(), origin.getY());
    }

    public void setOrigin(double ox, double oy) {
        SpriteRender render = getSpriteRender();

        if (render != null)
            render.setOrigin(ox, oy);
    }

    public void setRenderOrder(int renderOrder) {
        SpriteRender spriteRender = getSpriteRender();

        if (spriteRender != null)
            spriteRender.setRenderOrder(renderOrder);
    }
}
