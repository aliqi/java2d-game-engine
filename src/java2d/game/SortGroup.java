package java2d.game;

/**
 * Author:     Zhao Yan
 * DateTime:   2022/6/1 13:53
 */
@Unique
public class SortGroup extends GameComponent implements Orderable {

    private int order;

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public void setOrder(int order) {
        this.order = order;
    }
}
