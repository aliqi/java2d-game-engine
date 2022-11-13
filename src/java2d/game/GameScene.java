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

    public boolean enabled = true;

    public String name = "Scene";

    Game game;

    boolean actived, destroyed;

    final List<GameObject> roots = new CopyOnWriteArrayList<>();

    private final GraphicsRenderBatch graphicsRenderBatch;

    private Camera camera;

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
        graphicsRenderBatch = new GraphicsRenderBatch();
    }

    final void active() {
        if (!actived) {
            actived = true;
            active(roots);
            loaded();
        }
    }

    protected void loaded() {
    }

    protected void unloaded() {
        destroy();
    }

    public final void destroy() {
        actived = false;
        destroyed = true;

        for (GameObject o : roots)
            o.destroy();

        clean();
    }

    public final <T extends GameComponent> T findComponent(Class<T> c) {
        for (GameObject o : roots) {
            T result = o.getComponentInChildren(c);
            if (result != null)
                return result;
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

        if (renders != null)
            list.addAll(renders);

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

                if (actived)
                    active(gameObject);
            }
        }
    }

    static void active(GameObject... gameObject) {
        Queue<GameObject> queue = new LinkedList<>();
        for (GameObject o : gameObject)
            queue.offer(o);
        active(queue);
    }

    static void active(List<GameObject> objects) {
        Queue<GameObject> queue = new LinkedList<>();
        for (GameObject o : objects)
            queue.offer(o);
        active(queue);
    }

    static void active(Queue<GameObject> queue) {
        while (queue.size() > 0) {
            GameObject o = queue.poll();
            o.active();

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

    final void update() {
        if (actived && enabled) {

            for (GameObject o : roots)
                if (o.enabled)
                    o.update();

            clean();

            for (GameObject o : roots)
                if (o.enabled)
                    o.lateUpdate();

            if (camera != null)
                camera.updateTransform();
        }
    }

    final void render(Graphics2D g) {
        if (actived)
            graphicsRenderBatch.render(this, g);
    }
}
