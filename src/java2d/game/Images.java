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
        if (Strings.isBlank(path))
            return null;

        if (CachedImages.instance.containsKey(path))
            return CachedImages.instance.get(path);

        Game.debug("load classpath image: " + path);

        try (InputStream inputStream = Images.class.getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null)
                return null;

            Image result = ImageIO.read(inputStream);
            CachedImages.instance.put(path, result);
            return result;
        } catch (IOException e) {
            throw new RuntimeException(path, e);
        }
    }

    static Image loadFromPath(String path) {
        if (Strings.isBlank(path))
            return null;

        if (CachedImages.instance.containsKey(path))
            return CachedImages.instance.get(path);

        Game.debug("load image: " + path);

        File file = new File(path);

        try {
            Image result = ImageIO.read(file);
            CachedImages.instance.put(path, result);
            return result;
        } catch (IOException e) {
            throw new RuntimeException(path, e);
        }
    }

    static Image load(String path) {
        if (Strings.isBlank(path))
            return null;

        String classpath = "classpath:";

        if (path.startsWith(classpath))
            return loadFromClasspath(path.substring(classpath.length()));
        return loadFromPath(path);
    }
}
