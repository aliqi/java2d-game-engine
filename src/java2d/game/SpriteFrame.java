package java2d.game;

public class SpriteFrame implements Comparable<SpriteFrame> {

    public final float time;

    public Sprite sprite;

    float animatingTime;

    public SpriteFrame(float time, Sprite sprite) {
        this.time = time;
        this.sprite = sprite;
    }

    @Override
    public int compareTo(SpriteFrame o) {
        if (o == null)
            return 0;
        return Float.compare(time, o.time);
    }
}
