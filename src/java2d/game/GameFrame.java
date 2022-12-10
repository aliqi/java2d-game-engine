// Creator: Aliqi
// Create at: 2022/4/16
package java2d.game;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Timer;
import java.util.TimerTask;

public class GameFrame extends Frame implements ComponentListener {

    public int interval = 60;

    private Image bufferImage;

    private Graphics bufferGraphics;

    private final Game game;

    private FramePaintThread paintThread;

    private int renderWidth;

    private int renderHeight;

    private Dimension size;

    private boolean isOpened;

    private boolean runnable = true;

    private Timer resizeThrottle;

    Insets insets;

    public Dimension getRenderSize() {
        return new Dimension(renderWidth, renderHeight);
    }

    GameFrame(Game game) {
        this.game = game;
        setLayout(null);
    }

    public void open() {
        if (isOpened) return;

        isOpened = true;

        setVisible(true);
        calculateSize();

        if (paintThread == null) {
            paintThread = new FramePaintThread(this);
            paintThread.start();
        }

        addComponentListener(this);
    }

    private void calculateSize() {
        insets = getInsets();
        size = getSize();
        renderWidth = size.width - insets.left - insets.right;
        renderHeight = size.height - insets.top - insets.bottom;
    }

    public void close() {
        removeComponentListener(this);
        setVisible(false);

        if (paintThread != null) {
            paintThread.started = false;
            paintThread = null;
        }
    }

    public void paint(Graphics g) {
        if (isOpened && runnable) {
            game.update();
            game.render((Graphics2D) g);
        }
    }

    void prepareBuffer() {
        if (bufferImage == null) {
            calculateSize();
            bufferImage = createImage(renderWidth, renderHeight);
            bufferGraphics = bufferImage.getGraphics();
        }
    }

    private void disposeBuffer() {
        if (bufferGraphics != null) {
            bufferGraphics.dispose();
            bufferGraphics = null;
        }

        bufferImage = null;
    }

    public void update(Graphics g) {
        if (isOpened && runnable) {
            prepareBuffer();

            bufferGraphics.setColor(getBackground());
            bufferGraphics.fillRect(0, 0, size.width, size.height);

            paint(bufferGraphics);

            g.drawImage(bufferImage, insets.left, insets.top, this);
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        runnable = false;

        if (resizeThrottle != null)
            resizeThrottle.cancel();

        resizeThrottle = new Timer();
        resizeThrottle.schedule(new TimerTask() {
            @Override
            public void run() {
                resizeThrottle.cancel();
                disposeBuffer();
                prepareBuffer();
                runnable = true;
//                System.out.println("resized.");
            }
        }, 200, 200);
    }


    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
}

class FramePaintThread extends Thread {

    GameFrame frame;

    boolean started = true;

    public FramePaintThread(GameFrame frame) {
        this.frame = frame;
    }

    public void run() {

        int interval = Math.max(0, frame.interval);

        if (interval > 0) {
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (started)
            frame.repaint();
    }
}