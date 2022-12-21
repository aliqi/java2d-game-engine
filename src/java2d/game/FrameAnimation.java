package java2d.game;

@Unique
public class FrameAnimation extends GameComponent {

    public CompletedEvent completed;

    public boolean loop = true;

    public SpriteFrames frames;

    private int frameIndex;

    private boolean isPlaying;

    private double timer;

    public void play() {
        if (frames != null)
            play(frames.totalTime);
    }

    private long elasped;

    public void play(float fixedTotalTime) {
        if (frames != null) {
            for (SpriteFrame f : frames.values)
                f.animatingTime = f.time / frames.totalTime * fixedTotalTime;
        }

        isPlaying = true;
        elasped = System.nanoTime();
    }

    public void pause() {
        isPlaying = false;
    }

    public void stop() {
        isPlaying = false;
        reset();
    }

    public void reset() {
        frameIndex = 0;
        timer = 0;
    }

    private SpriteFrame updateCurrentFrame() {
        frameIndex = frameIndex % frames.length;
        SpriteFrame c = frames.values[frameIndex];

        if (timer < c.animatingTime)
            return null;

        for (int i = frameIndex + 1; i < frames.length; i++) {
            SpriteFrame n = frames.values[i];

            if (timer < n.animatingTime)
                return c;

            frameIndex = i;
        }

        return frames.values[frameIndex];
    }

    @Override
    protected void update() {
        if (isPlaying && frames != null && frames.length > 0) {

            SpriteFrame f = updateCurrentFrame();
            SpriteRender spriteRender = getComponent(SpriteRender.class);

            if (spriteRender != null && f != null)
                spriteRender.sprite = f.sprite;

            if (timer >= frames.values[frames.length - 1].animatingTime) {
                if (loop) {
                    reset();
                } else {
                    stop();

                    if (completed != null)
                        completed.completed();
                }
            } else
                timer += Time.deltaTime;
        }
    }

    @FunctionalInterface
    public interface CompletedEvent {
        void completed();
    }
}
