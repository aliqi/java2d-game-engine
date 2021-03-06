// Creator: Aliqi
// Create at: 2022/4/16
package java2d.game;

public abstract class GameComponent implements Comparable<GameComponent> {

    public boolean enabled = true;

    public int order = 1;

    boolean actived, destroyed;

    GameObject gameObject;

    public GameObject getGameObject() {
        return gameObject;
    }

    public <T extends GameComponent> T getComponent(Class<T> c) {
        return gameObject == null ? null : gameObject.getComponent(c);
    }

    final void active() {
        if (!actived) {
            actived = true;
            awake();
        }
    }

    // called when first added into the current scene
    protected void awake() {
        Game.debug("component[" + gameObject.name + "] awake: " + this);
    }

    final void beginDestroy() {
        actived = false;
        destroyed = true;
    }

    protected void destroy() {
        Game.debug("Component ["+ gameObject.name + "] destroyed: " + this);
    }

    protected void update() {
    }

    protected void lateUpdate() {

    }

    @Override
    public int compareTo(GameComponent o) {
        return Integer.compare(order, o.order);
    }
}
