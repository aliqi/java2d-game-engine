// Creator: Aliqi
// Create at: 2022/4/16
package java2d.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface Images {

    static Image loadFromClasspath(String path) {
        Game.debug("load classpath image: " + path);
        try (InputStream inputStream = Images.class.getClassLoader().getResourceAsStream(path)) {
            return inputStream == null ? null : ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Image loadFromPath(String path) {
        File file = new File(path);
        Game.debug("load image: " + file.getAbsolutePath());
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Image load(String path) {
        String clsp = "classpath:";
        if (path.startsWith(clsp))
            return loadFromClasspath(path.substring(clsp.length()));
        return loadFromPath(path);
    }
}
