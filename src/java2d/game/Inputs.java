// Creator: Ation
// Create at: 2022/4/20
package java2d.game;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inputs {

    static GameFrame frame;

    static Map<Character, InputSystem.KeyState> keys = new HashMap<>();

    static final List<GameMouseEvent> mouseEvents = new ArrayList<>();

    private Inputs() {
    }

    public static Point2D getMousePosition() {
        if (frame == null) return new Point2D.Float();
        return frame.getMousePosition();
    }

    public static void addMouseEventListener(GameMouseEvent e) {
        if (e != null)
            mouseEvents.add(e);
    }

    public static void removeMouseEventListener(GameMouseEvent e) {
        if (e != null)
            mouseEvents.remove(e);
    }

    public static boolean getKeyDown(char key) {
        InputSystem.KeyState state = keys.get(key);
        return state != null && state.pressed == InputSystem.STATE_TRIGGERED_ONCE;
    }

    public static boolean getKey(char key) {
        InputSystem.KeyState state = keys.get(key);
        return state != null && state.current == InputSystem.KEY_STATE_DOWN;
    }

    public static boolean getKeyUp(char key) {
        InputSystem.KeyState state = keys.get(key);
        return state != null && state.released == InputSystem.STATE_TRIGGERED_ONCE;
    }
}
