// Creator: Ation
// Create at: 2022/4/16
package java2d.game;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

class GraphicsRenderBatch {

    void render(GameScene scene, Graphics2D g) {
        if (scene != null) {
            List<RenderOrderable> components = scene.findRenderOrderables(scene);
            components.sort(Comparator.comparingInt(RenderOrderable::getRenderOrder));

            for (int i = 0; i < components.size(); i++) {
                RenderOrderable render = components.get(i);
                if (render instanceof GraphicsRender)
                    ((GraphicsRender) render).render(scene, g);
                else if (render instanceof SortGroup) {
                    List<RenderOrderable> group = scene.findRenderOrderables(((SortGroup) render).gameObject);
                    group.sort(Comparator.comparingInt(RenderOrderable::getRenderOrder));
                    components.addAll(i + 1, group);
                }
            }
        }
    }
}
