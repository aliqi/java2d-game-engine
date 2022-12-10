// Creator: Aliqi
// Create at: 2022/4/16
package java2d.game;

import java.awt.*;
import java.awt.geom.AffineTransform;

@Unique
public class SpriteRender extends GraphicsRender {

    public Sprite sprite;

    public static SpriteRender create(String spritePath) {
        SpriteRender render = new SpriteRender();
        render.sprite = Sprite.load(spritePath);
        return render;
    }

    @Override
    protected void updateTransform(GameScene scene, AffineTransform at) {
        if (sprite == null) {
            renderOriginX = renderOriginY = 0;
        } else {
            renderOriginX = sprite.getWidth() * originX;
            renderOriginY = sprite.getHeight() * originY;
        }

        super.updateTransform(scene, at);
    }

    @Override
    protected void render(GameScene scene, Graphics2D g) {
        if (sprite != null)
            g.drawImage(sprite.getImage(), affineTransform, null);
    }
}
