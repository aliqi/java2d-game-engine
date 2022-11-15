package java2d.game;

@Unique
public class FrameAnimation extends GameComponent {

    public CompletedEvent completed;

    public int rate = 24;

    public boolean loop = true;

    public Sprite[] frames;

    private int frameIndex;

    private boolean isPlaying;

    private double timer;

    @Override
    protected void awake() {
        super.awake();
    }

    public void play() {
        if (rate > 0)
            timer = 1d / rate;
        isPlaying = true;
    }

    public void pause() {
        isPlaying = false;
    }

    public void stop() {
        isPlaying = false;
        frameIndex = 0;
    }

    @Override
    protected void update() {
        if (isPlaying && frames != null && frames.length > 0 && rate > 0) {

            double interval = 1d / rate;

            timer += Time.deltaTime;

            if (timer >= interval) {
                timer = 0;

                frameIndex = frameIndex % frames.length;

                SpriteRender spriteRender = getComponent(SpriteRender.class);

                if (spriteRender != null)
                    spriteRender.sprite = frames[frameIndex];

                ++frameIndex;

                if (!loop && frameIndex >= frames.length) {
                    stop();

                    if (completed != null)
                        completed.completed();
                }
            }
        }
    }

    @FunctionalInterface
    public interface CompletedEvent {
        void completed();
    }
}
