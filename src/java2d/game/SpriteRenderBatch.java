// Creator: Ation
// Create at: 2022/4/16
package java2d.game;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

class SpriteRenderBatch {

    void render(GameScene scene, Graphics2D g) {
        if (scene != null) {
            List<Orderable> components = scene.findOrderables(scene);
            components.sort(Comparator.comparingInt(Orderable::getOrder));

            for (int i = 0; i < components.size(); i++) {
                Orderable render = components.get(i);
                if (render instanceof Graphics2DRender) ((Graphics2DRender) render).render(scene, g);
                else if (render instanceof SortGroup) {
                    List<Orderable> group = scene.findOrderables(((SortGroup) render).gameObject);
                    group.sort(Comparator.comparingInt(Orderable::getOrder));
                    components.addAll(i + 1, group);
                }
            }
        }
    }
}
