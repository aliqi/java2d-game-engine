package java2d.game;

import java.awt.*;

/**
 * Author:     Zhao Yan
 * DateTime:   2022/6/2 15:56
 */
public abstract class Graphics2DRender extends GameComponent implements Orderable {

    private int order;

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public void setOrder(int order) {
        this.order = order;
    }

    protected abstract void render(GameScene scene, Graphics2D g);
}
