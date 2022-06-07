// Creator: Ation
// Create at: 2022/4/20
package java2d.game;

import java.awt.event.*;

class InputSystem implements KeyListener, MouseListener, MouseWheelListener {

    static class KeyState {
        int current;
        int send;
        int pressed;
        int released;
    }

    static final int KEY_STATE_DOWN = 1;
    static final int KEY_STATE_UP = 2;

    static final int STATE_NONE = 0;
    static final int STATE_TRIGGERED_ONCE = 1;
    static final int STATE_TRIGGERED = 2;

    InputSystem(GameFrame frame) {
        Inputs.frame = frame;
    }

    void beforeUpdateKeys() {
        for (KeyState state : Inputs.keys.values()) {
            state.current = state.send;

            if (state.pressed == STATE_NONE && state.current == KEY_STATE_DOWN)
                state.pressed = STATE_TRIGGERED_ONCE;

            if (state.released == STATE_NONE && state.current == KEY_STATE_UP)
                state.released = STATE_TRIGGERED_ONCE;
        }
    }

    void afterUpdateKeys() {
        for (KeyState state : Inputs.keys.values()) {
            if (state.pressed == STATE_TRIGGERED_ONCE)
                state.pressed = STATE_TRIGGERED;

            if (state.send == KEY_STATE_UP)
                state.pressed = STATE_NONE;

            if (state.released == STATE_TRIGGERED_ONCE)
                state.released = STATE_TRIGGERED;

            if (state.send == KEY_STATE_DOWN)
                state.released = STATE_NONE;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();
        KeyState state = Inputs.keys.get(c);

        if (state == null) {
            state = new KeyState();
            Inputs.keys.put(c, state);
        }

        state.send = KEY_STATE_DOWN;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        char c = e.getKeyChar();
        KeyState state = Inputs.keys.get(c);

        if (state != null)
            state.send = KEY_STATE_UP;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (GameMouseEvent me : Inputs.mouseEvents)
            me.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (GameMouseEvent me : Inputs.mouseEvents)
            me.mouseReleased(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
    }
}
