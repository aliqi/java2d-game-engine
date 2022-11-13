// Creator: Aliqi
// Create at: 2022/4/16
package java2d.game;

import java.awt.*;
import java.awt.geom.AffineTransform;

@Unique
public class SpriteRender extends GraphicsRender {

    public Sprite sprite;

    private final AffineTransform transform = new AffineTransform();

    public static SpriteRender create(String spritePath) {
        SpriteRender render = new SpriteRender();
        render.sprite = Sprite.load(spritePath);
        return render;
    }

    @Override
    protected void render(GameScene scene, Graphics2D g) {
        if (sprite != null) {
            updateTransform(scene, transform);
            g.drawImage(sprite.getImage(), transform, null);
        }
    }
}
