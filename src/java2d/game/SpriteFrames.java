package java2d.game;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpriteFrames {

    public final SpriteFrame[] values;

    public final float totalTime;

    public final int length;

    public SpriteFrames(SpriteFrame[] frames) {
        Arrays.sort(frames);
        values = frames;
        totalTime = frames[frames.length - 1].time;
        length = values.length;
    }

    public static SpriteFrames load(String path) {
        BufferedReader reader = null;

        try (InputStream inputStream = Files.load(path)) {
            String folder = Path.of(path).getParent().toFile().getPath();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            List<SpriteFrame> list = new ArrayList<>();
            reader.lines().forEach(l -> {
                String[] parts = l.split(":");
                if (parts.length == 2)
                    list.add(new SpriteFrame(
                            Float.parseFloat(parts[0].trim()), Sprite.load(Path.of(folder, parts[1].trim()).toString())));
            });
            return new SpriteFrames(list.toArray(new SpriteFrame[0]));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            Files.close(reader);
        }
    }
}
