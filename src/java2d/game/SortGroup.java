package java2d.game;

/**
 * Author:     Zhao Yan
 * DateTime:   2022/6/1 13:53
 */
@Unique
public class SortGroup extends GameComponent implements RenderOrderable {

    private int order;

    @Override
    public int getRenderOrder() {
        return order;
    }

    @Override
    public void setRenderOrder(int order) {
        this.order = order;
    }
}
