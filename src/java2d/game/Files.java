package java2d.game;

import java.io.*;

public interface Files {

    static InputStream loadFromClasspath(String path) {
        Game.debug("load classpath image: " + path);
        return Files.class.getClassLoader().getResourceAsStream(path);
    }

    static InputStream loadFromPath(String path) {
        File file = new File(path);
        Game.debug("load image: " + file.getAbsolutePath());

        try {
            if (file.exists())
                return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    static InputStream load(String path) {
        String clsp = "classpath:";
        if (path.startsWith(clsp))
            return loadFromClasspath(path.substring(clsp.length()));
        return loadFromPath(path);
    }

    static void close(Closeable closeable) {
        try {
            if (closeable != null)
                closeable.close();
        } catch (IOException e) {
        }
    }
}
