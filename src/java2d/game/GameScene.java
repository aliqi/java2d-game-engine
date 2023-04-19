// Creator: Aliqi
// Create at: 2022/4/16
package java2d.game;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameScene {

    public final Canvas ui = new Canvas();

    public boolean enabled = true;

    public boolean antialiasEnabled = true;

    public String name = "Scene";

    Game game;

    boolean actived, destroyed;

    final List<GameObject> roots = new CopyOnWriteArrayList<>();

    private final GraphicsRenderBatch graphicsRenderBatch = new GraphicsRenderBatch();

    private final CanvasRenderBatch canvasRenderBatch = new CanvasRenderBatch();

    private Camera camera = new Camera();

    public Game getGame() {
        return game;
    }

    public GameObject[] getRoots() {
        return roots.toArray(new GameObject[0]);
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public GameScene() {
        addCamera();
        add(ui);
    }

    private void addCamera() {
        GameObject c = new GameObject("camera");
        c.addComponent(camera);
        add(c);
    }

    void setupAntialias(Graphics2D g) {
        if (antialiasEnabled) g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        else g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }

    final void active() {
        if (!actived) {
            actived = true;

            if (ui.space == Canvas.RenderSpace.Overlay) ui.setSize(getGame().getRenderSize());

            active(roots);
            loaded();
        }
    }

    protected void loaded() {
    }

    protected void unloaded() {
    }

    public final void destroy() {
        if (destroyed) return;

        actived = false;
        destroyed = true;

        for (GameObject o : roots)
            o.destroy();

        clean();
    }

    public final <T extends GameObject> List<T> findGameObjects(Class<T> c) {
        List<T> list = new ArrayList<>();
        for (GameObject o : roots)
            if (c.isAssignableFrom(o.getClass())) list.add((T) o);
        return list;
    }

    public final <T extends GameObject> T findGameObject(Class<T> c) {
        for (GameObject o : roots)
            if (c.isAssignableFrom(o.getClass())) return (T) o;
        return null;
    }

    public final <T extends GameComponent> T findComponent(Class<T> c) {
        for (GameObject o : roots) {
            T result = o.getComponentInChildren(c);
            if (result != null) return result;
        }
        return null;
    }

    public final <T extends GameComponent> List<T> findComponents(Class<T> c) {
        List<T> list = new ArrayList<>();
        for (GameObject o : roots)
            o.getComponentsInChildren(c, list);
        return list;
    }

    List<RenderOrderable> findRenderOrderables(GameScene scene) {
        List<RenderOrderable> list = new LinkedList<>();
        Queue<GameObject> queue = new LinkedList<>();

        for (GameObject o : roots)
            queue.offer(o);

        findRenderOrderables(list, queue);
        return list;
    }

    List<RenderOrderable> findRenderOrderables(GameObject obj) {
        List<RenderOrderable> list = new LinkedList<>();
        Queue<GameObject> queue = new LinkedList<>();

        findGraphicsRender(list, queue, obj);
        findRenderOrderables(list, queue);
        return list;
    }

    private void findRenderOrderables(List<RenderOrderable> list, Queue<GameObject> queue) {
        while (queue.size() > 0) {
            GameObject o = queue.poll();
            SortGroup group = o.getComponent(SortGroup.class);

            if (group != null) {
                list.add(group);
                continue;
            }

            findGraphicsRender(list, queue, o);
        }
    }

    private void findGraphicsRender(List<RenderOrderable> list, Queue<GameObject> queue, GameObject o) {
        List<GraphicsRender> renders = o.getComponents(GraphicsRender.class);

        if (renders != null) list.addAll(renders);

        for (GameObject oc : o)
            queue.offer(oc);
    }

    public final void add(GameObject gameObject) {
        if (gameObject != null) {
            if (gameObject.destroyed)
                throw new RuntimeException("GameObject " + gameObject.name + " already destroyed");

            if (gameObject.getParent() != null)
                throw new RuntimeException("GameObject " + gameObject.name + " already has a parent");

            if (!roots.contains(gameObject)) {
                roots.add(gameObject);
                gameObject.transform.invalidate();

                if (actived) active(gameObject);
            }
        }
    }

    void active(GameObject... gameObject) {
        Queue<GameObject> queue = new LinkedList<>();
        for (GameObject o : gameObject)
            queue.offer(o);
        active(queue);
    }

    void active(List<GameObject> objects) {
        Queue<GameObject> queue = new LinkedList<>();
        for (GameObject o : objects)
            queue.offer(o);
        active(queue);
    }

    void active(Queue<GameObject> queue) {
        while (queue.size() > 0) {
            GameObject o = queue.poll();

            if (!o.destroyed) o.active(this);

            for (GameObject c : o)
                queue.offer(c);
        }
    }

    // clean up destroyed game objects
    private void clean() {
        int size = roots.size();
        for (int i = 0; i < size; i++) {
            GameObject g = roots.get(i);
            if (g.destroyed) {
                g.finalDestroy();
                roots.remove(g);
                i--;
                size--;
            }
        }
    }

    protected void update() {
    }

    final void internalUpdate() {
        if (actived && !destroyed && enabled) {
            for (GameObject o : roots)
                o.update();

            clean();

            update();
        }
    }

    protected void lateUpdate() {
    }

    final void internalLateUpdate() {
        if (actived && !destroyed && enabled) {
            for (GameObject o : roots)
                o.lateUpdate();

            if (camera != null)
                camera.updateTransform();

            lateUpdate();
        }
    }

    final void render(Graphics2D g) {
        if (actived && !destroyed) {
            graphicsRenderBatch.render(this, g);
            canvasRenderBatch.render(this, g);
        }
    }
}
