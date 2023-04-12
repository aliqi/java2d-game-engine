package java2d.game;

import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author:     Zhao Yan
 * DateTime:   2023/4/12 7:25
 */
final class CachedImages extends ConcurrentHashMap<String, Image> {

    public static CachedImages instance = new CachedImages();

    private CachedImages() {
    }
}
