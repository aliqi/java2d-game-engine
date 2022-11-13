package java2d.game;

import java.awt.geom.Point2D;

/**
 * Author: ZhaoYan
 * Created: 2022/11/13 11:51
 */
public class SpriteGameObject extends GameObject {

    public SpriteRender getSpriteRender() {
        return getComponent(SpriteRender.class);
    }

    public Sprite getSprite() {
        SpriteRender render = getComponent(SpriteRender.class);
        return render == null ? null : render.sprite;
    }

    public SpriteGameObject(String name, String spritePath, double ratioX, double ratioY) {
        super(name);

        SpriteRender spriteRender = SpriteRender.create(spritePath);
        Sprite sprite = spriteRender.sprite;

        addComponent(spriteRender);

        transform.setOrigin(sprite.getWidth() * ratioX, sprite.getHeight() * ratioY);
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
        Sprite sprite = getSprite();

        if (sprite != null)
            transform.setOrigin(sprite.getWidth() * ox, sprite.getHeight() * oy);
    }

    public void setOrder(int order) {
        SpriteRender spriteRender = getSpriteRender();

        if (spriteRender != null)
            spriteRender.setOrder(order);
    }
}
