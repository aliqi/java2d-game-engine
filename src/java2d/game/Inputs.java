// Creator: Ation
// Create at: 2022/4/20
package java2d.game;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inputs {

    private final GameFrame frame;

    final Map<Character, InputSystem.KeyState> keys = new HashMap<>();

    final Map<Integer, InputSystem.KeyState> mouses = new HashMap<>();

    final List<GameMouseEvent> mouseEvents = new ArrayList<>();

    private Point2D lastPosition = new Point2D.Double();

    boolean overUI;

    Inputs(GameFrame frame) {
        this.frame = frame;
    }

    public Point2D getMousePosition() {
        if (frame == null)
            return new Point2D.Float();

        Point2D position = frame.getMousePosition();

        if (position == null)
            return lastPosition;

        position.setLocation(position.getX() - frame.insets.left, position.getY() - frame.insets.top);
        lastPosition = position;

        return position;
    }

    public void addMouseEventListener(GameMouseEvent e) {
        if (e != null)
            mouseEvents.add(e);
    }

    public void removeMouseEventListener(GameMouseEvent e) {
        if (e != null)
            mouseEvents.remove(e);
    }

    public boolean isOverUI() {
        return overUI;
    }

    public boolean getKeyDown(char key) {
        InputSystem.KeyState state = keys.get(key);
        return state != null && state.pressed == InputSystem.STATE_TRIGGERED_ONCE;
    }

    public boolean getKey(char key) {
        InputSystem.KeyState state = keys.get(key);
        return state != null && state.current == InputSystem.KEY_STATE_DOWN;
    }

    public boolean getKeyUp(char key) {
        InputSystem.KeyState state = keys.get(key);
        return state != null && state.released == InputSystem.STATE_TRIGGERED_ONCE;
    }

    public boolean getMouseButtonDown(int mouseButton) {
        InputSystem.KeyState state = mouses.get(mouseButton);
        return state != null && state.pressed == InputSystem.KEY_STATE_DOWN;
    }

    public boolean getMouseButton(int mouseButton) {
        InputSystem.KeyState state = mouses.get(mouseButton);
        return state != null && state.current == InputSystem.KEY_STATE_DOWN;
    }

    public boolean getMouseButtonUp(int mouseButton) {
        InputSystem.KeyState state = mouses.get(mouseButton);
        return state != null && state.released == InputSystem.KEY_STATE_UP;
    }
}
