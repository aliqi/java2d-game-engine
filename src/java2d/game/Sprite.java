// Creator: Aliqi
// Create at: 2022/4/16
package java2d.game;

import java.awt.*;

public class Sprite {

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
        return new Sprite(Images.load(path));
    }
}
