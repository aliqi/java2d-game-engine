// Creator: Aliqi
// Create at: 2022/4/16
package java2d.game;

import java.awt.*;
import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameObject implements Iterable<GameObject> {

    public String name = "GameObject";

    public boolean enabled = true;

    public final Transform transform;

    public String tag;

    private final List<GameObject> objects = new CopyOnWriteArrayList<>();

    private final List<GameComponent> components = new CopyOnWriteArrayList<>();

    private GameObject parent;

    private GameScene scene;

    boolean activated, destroyed;

    public static boolean isDestroyed(GameComponent component) {
        return component == null || component.isDestroyed();
    }

    public static boolean isDestroyed(GameObject obj) {
        return obj == null || obj.isDestroyed();
    }

    public static boolean isDestroyed(Transform transform) {
        return transform == null || transform.getGameObject().isDestroyed();
    }

    public boolean isActivated() {
        return activated;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public boolean compareTag(String tag) {
        return Strings.equals(this.tag, tag);
    }

    public Inputs getInputs() {
        return scene == null ? null : scene.game.inputs;
    }

    public GameObject getParent() {
        return parent;
    }

    public int getChildCount() {
        return objects.size();
    }

    public GameScene getScene() {
        return scene;
    }

    public GameObject() {
        transform = new Transform(this);
        components.add(transform);
    }

    public GameObject(String name) {
        this();
        this.name = name;
    }

    void sortComponents() {
        Collections.sort(components);
    }

    public final void destroy() {
        if (destroyed) return;

        destroyed = true;
        activated = false;

        for (GameComponent c : components)
            c.beginDestroy();

        for (GameObject o : objects)
            o.destroy();

        onDestroyed();

        this.scene = null;
    }

    final void finalDestroy() {
        for (GameComponent c : components)
            c.destroy();

        for (GameObject o : objects)
            o.finalDestroy();
    }

    protected void onUpdate() {
    }

    void update() {
        if (activated && !destroyed && enabled) {
            onUpdate();

            for (GameComponent c : components)
                if (c.activated && !c.destroyed && c.enabled)
                    c.update();

            for (GameObject o : objects)
                o.update();
        }
    }

    protected void onLateUpdate() {
    }

    void lateUpdate() {
        if (activated && !destroyed && enabled) {
            onLateUpdate();

            for (GameComponent c : components)
                if (c.activated && !c.destroyed && c.enabled)
                    c.lateUpdate();

            for (GameObject o : objects)
                o.lateUpdate();
        }
    }

    public GameObject find(String name) {
        for (GameObject o : objects)
            if (Strings.equals(name, o.name))
                return o;
        return null;
    }

    public GameObject get(int index) {
        return objects.get(index);
    }

    final void active(GameScene scene) {
        if (!activated) {
            this.scene = scene;
            activated = true;
            transform.invalidate();

            Collections.sort(components);

            for (GameComponent c : components)
                c.active();

            onActivated();
        }
    }

    protected void onActivated() {
    }

    protected void onDestroyed() {
    }

    void setupAntialias(Graphics2D g) {
        getScene().setupAntialias(g);
    }

    public void add(GameObject child) {
        if (child == null)
            throw new NullPointerException("child is null");
        if (child.destroyed)
            throw new RuntimeException("child already destroyed");

        if (child.parent != null && child.parent != this) child.parent.remove(child);

        if (!objects.contains(child)) {
            child.parent = this;
            objects.add(child);
            if (activated) scene.active(child);
        }
    }

    public void remove(GameObject child) {
        if (child != null && child.parent == this) {
            child.parent = null;
            objects.remove(child);
        }
    }

    public <T extends GameComponent> T getComponent(Class<T> c) {
        for (GameComponent component : components)
            if (c.isAssignableFrom(component.getClass()))
                return (T) component;
        return null;
    }

    public <T extends GameComponent> List<T> getComponents(Class<T> c) {
        List<T> list = new ArrayList<>();
        for (GameComponent component : components)
            if (c.isAssignableFrom(component.getClass()))
                list.add((T) component);
        return list;
    }

    public <T extends GameComponent> void getComponents(Class<T> c, List<T> list) {
        for (GameComponent component : components)
            if (c.isAssignableFrom(component.getClass()))
                list.add((T) component);
    }

    public <T extends GameComponent> T getComponentInChildren(Class<T> c) {
        Queue<GameObject> queue = new LinkedList<>();

        queue.offer(this);

        while (queue.size() > 0) {
            GameObject o = queue.poll();
            T result = o.getComponent(c);

            if (result != null)
                return result;

            for (GameObject oc : o)
                queue.offer(oc);
        }

        return null;
    }

    public <T extends GameComponent> T[] getComponentsInChildren(Class<T> c) {
        List<T> list = new ArrayList<>();
        getComponentsInChildren(c, list);
        return (T[]) list.toArray();
    }

    public <T extends GameComponent> void getComponentsInChildren(Class<T> c, List<T> list) {
        Queue<GameObject> queue = new LinkedList<>();

        queue.offer(this);

        while (queue.size() > 0) {
            GameObject o = queue.poll();
            o.getComponents(c, list);
            for (GameObject oc : o)
                queue.offer(oc);
        }
    }

    public <T extends GameComponent> T addComponent(T instance) {
        if (instance != null) {

            if (instance.destroyed)
                throw new RuntimeException("Component already destroyed: " + instance);

            // check if the component already attach to others.
            if (instance.gameObject != null) {
                if (instance.gameObject == this) return instance;

                throw new RuntimeException("Component already attach to the gameObject: " + instance.gameObject.name);
            }

            Class<? extends GameComponent> instanceClass = instance.getClass();
            if (instanceClass.isAnnotationPresent(Unique.class))
                if (components.stream().anyMatch(s -> s.getClass() == instanceClass))
                    throw new RuntimeException("Component " + instanceClass.getName() + " must be unique");

            if (!components.contains(instance)) {
                instance.gameObject = this;
                components.add(instance);
                Collections.sort(components);
                if (activated) instance.active();
            }
        }

        return instance;
    }

    public <T extends GameComponent> void removeComponent(T instance) {
        if (instance != null && instance.gameObject == this) {
            instance.gameObject = null;
            instance.activated = false;
            components.remove(instance);
        }
    }

    @Override
    public Iterator<GameObject> iterator() {
        return objects.iterator();
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "name='" + name + '\'' +
                ", enabled=" + enabled +
                ", actived=" + activated +
                ", destroyed=" + destroyed +
                '}';
    }
}
