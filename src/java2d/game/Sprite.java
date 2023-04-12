// Creator: Aliqi
// Create at: 2022/4/16
package java2d.game;

import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;

public class Sprite {

    private static final ConcurrentHashMap<String, Sprite> cached = new ConcurrentHashMap<>();

    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getWidth() {
        return image == null ? 0 : image.getWidth(null);
    }

    public int getHeight() {
        return image == null ? 0 : image.getHeight(null);
    }

    private Sprite(Image image) {
        this.image = image;
    }

    public static Sprite load(String path) {
        if (cached.containsKey(path))
            return cached.get(path);

        Sprite sprite = new Sprite(Images.load(path));
        cached.put(path, sprite);
        return sprite;
    }
}
