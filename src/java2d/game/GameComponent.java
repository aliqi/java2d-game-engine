// Creator: Aliqi
// Create at: 2022/4/16
package java2d.game;

public abstract class GameComponent implements Comparable<GameComponent> {

    public boolean enabled = true;

    private int updateOrder = 0;

    boolean activated, destroyed;

    GameObject gameObject;

    public Inputs getInputs() {
        return gameObject == null ? null : gameObject.getInputs();
    }

    public GameScene getScene() {
        return gameObject == null ? null : gameObject.getScene();
    }

    public boolean isActivated() {
        return activated;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public static boolean isDestroyed(GameComponent component) {
        return GameObject.isDestroyed(component);
    }

    public static boolean isDestroyed(GameObject obj) {
        return GameObject.isDestroyed(obj);
    }

    public static boolean isDestroyed(Transform transform) {
        return GameObject.isDestroyed(transform);
    }

    public int getUpdateOrder() {
        return updateOrder;
    }

    public void setUpdateOrder(int updateOrder) {
        if (updateOrder != this.updateOrder) {
            this.updateOrder = updateOrder;

            if (gameObject != null)
                gameObject.sortComponents();
        }
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public Transform getTransform() {
        return gameObject == null ? null : gameObject.transform;
    }

    public <T extends GameComponent> T getComponent(Class<T> c) {
        return gameObject == null ? null : gameObject.getComponent(c);
    }

    final void active() {
        if (!activated) {
            activated = true;
            awake();
        }
    }

    // called when first added into the current scene
    protected void awake() {
        Game.debug("component[" + gameObject.name + "] awake: " + this);
    }

    final void beginDestroy() {
        activated = false;
        destroyed = true;
    }

    protected void destroy() {
        Game.debug("Component [" + gameObject.name + "] destroyed: " + this);
    }

    protected void update() {
    }

    protected void lateUpdate() {

    }

    @Override
    public int compareTo(GameComponent o) {
        return Integer.compare(updateOrder, o.updateOrder);
    }
}
