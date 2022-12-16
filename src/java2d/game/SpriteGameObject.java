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

    public void setSprite(Sprite sprite) {
        SpriteRender render = getComponent(SpriteRender.class);

        if (render != null)
            render.sprite = sprite;
    }

    public Point2D getOrigin() {
        SpriteRender render = getComponent(SpriteRender.class);
        return render == null ? new Point2D.Double() : render.getOrigin();
    }

    public void setOrigin(Point2D origin) {
        setOrigin(origin.getX(), origin.getY());
    }

    public void setOrigin(double ox, double oy) {
        SpriteRender render = getComponent(SpriteRender.class);

        if (render != null)
            render.setOrigin(ox, oy);
    }

    public void setRenderOrder(int renderOrder) {
        SpriteRender render = getComponent(SpriteRender.class);

        if (render != null)
            render.setRenderOrder(renderOrder);
    }

    public int getRenderOrder() {
        SpriteRender render = getComponent(SpriteRender.class);
        return render == null ? 0 : render.getRenderOrder();
    }

    public SpriteGameObject(Sprite sprite) {
        this(null, sprite, 0, 0);
    }

    public SpriteGameObject(String name, Sprite sprite) {
        this(name, sprite, 0, 0);
    }

    public SpriteGameObject(Sprite sprite, double originX, double originY) {
        this(null, sprite, originX, originY);
    }

    public SpriteGameObject(String name, Sprite sprite, double originX, double originY) {
        this.name = name;

        SpriteRender render = new SpriteRender();
        render.sprite = sprite;
        render.setOrigin(originX, originY);

        addComponent(render);
    }

    public SpriteGameObject(String spritePath, double originX, double originY) {
        this("SpriteGameObject", spritePath, originX, originY);
    }

    public SpriteGameObject(String name, String spritePath, double originX, double originY) {
        super(name);

        SpriteRender spriteRender = SpriteRender.create(spritePath);
        spriteRender.setOrigin(originX, originY);

        addComponent(spriteRender);
    }

    public SpriteGameObject(String name, String spritePath, Point2D origin) {
        this(name, spritePath, origin.getX(), origin.getY());
    }

    public SpriteGameObject(String name, String spritePath) {
        this(name, spritePath, 0, 0);
    }
}
