// Creator: Aliqi
// Create at: 2022/4/16
package java2d.game;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Game {

    public static final String VERSION = "1.0";

    public static boolean debugEnabled = false;

    public final GameConfig config = new GameConfig();

    private final GameFrame frame;

    private final Properties ps = new Properties();

    private final InputSystem inputSystem;

    private GameScene scene;

    private long lastTime;

    public GameFrame getFrame() {
        return frame;
    }

    public GameScene getScene() {
        return scene;
    }

    public Game() {
        frame = new GameFrame(this);
        inputSystem = new InputSystem(frame);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                stop();
            }
        });
        frame.addKeyListener(inputSystem);
        frame.addMouseListener(inputSystem);
        frame.addMouseWheelListener(inputSystem);

        loadConfig();
        setup();
    }

    public static void debug(String message) {
        if (debugEnabled) System.out.println(message);
    }

    void update() {
        long currentTime = System.currentTimeMillis();
        long delta = currentTime - lastTime;
        Time.deltaTime = delta * 0.001f;

        inputSystem.beforeUpdateKeys();

        if (scene != null)
            scene.update();

        inputSystem.afterUpdateKeys();

        lastTime = currentTime;
    }

    void render(Graphics2D g) {
        if (scene != null && g != null)
            scene.render(g);
    }

    public GameScene load(GameScene scene) {
        if (scene != null && scene.destroyed)
            throw new RuntimeException("Game scene [" + scene.name + "] already destroyed");

        GameScene last = this.scene;
        this.scene = scene;

        if (last != null)
            last.unloaded();

        if (scene != null) {
            scene.enabled = true;
            scene.active();
        }

        return scene;
    }

    private void loadConfig() {
        try (InputStream inputStream =
                     Game.class.getClassLoader().getResourceAsStream("game.properties")) {
            if (inputStream != null) {
                ps.load(inputStream);

                String value = ps.getProperty("title");
                if (Strings.isNotEmpty(value))
                    config.title = value;

                value = ps.getProperty("version");
                if (Strings.isNotEmpty(value))
                    config.version = value;

                config.icon = ps.getProperty("icon");

                value = ps.getProperty("width");
                if (Strings.isNotEmpty(value))
                    config.width = Integer.parseInt(value);

                value = ps.getProperty("height");
                if (Strings.isNotEmpty(value))
                    config.height = Integer.parseInt(value);

                value = ps.getProperty("resizeable");
                if (Strings.isNotEmpty(value))
                    config.resizeable = Boolean.parseBoolean(value);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setup() {
        if (Strings.isNotBlank(config.icon)) {
            Image icon = Images.load(config.icon);
            if (icon != null) frame.setIconImage(icon);
        }

        String title = config.title;
        if (Strings.isNotEmpty(config.version)) title += " " + config.version;

        frame.setTitle(title);
        frame.setSize(config.width, config.height);
        frame.setResizable(config.resizeable);

        frame.setBackground(Color.BLACK);
        frame.setFocusable(true);
        frame.setEnabled(true);

        // center the window
        frame.setLocationRelativeTo(null);
    }

    public void start() {
        lastTime = System.currentTimeMillis();
        frame.open();
    }

    public void stop() {
        if (scene != null)
            scene.destroy();

        frame.close();
        System.exit(0);
    }

    public void pause() {
        if (scene != null)
            scene.enabled = false;
    }

    public void resume() {
        if (scene != null)
            scene.enabled = true;
    }
}
