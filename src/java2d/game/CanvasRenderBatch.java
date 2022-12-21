package java2d.game;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class CanvasRenderBatch {

    private final List<Canvas> worldCanvasList = new ArrayList<>();

    private final List<Canvas> overlayCanvasList = new ArrayList<>();

    void render(GameScene scene, Graphics2D g) {
        if (scene != null) {
            seperate(scene);
            render(g, worldCanvasList);
            render(g, overlayCanvasList);
        }
    }

    private void render(Graphics2D g, List<Canvas> canvasList) {
        canvasList.sort(Comparator.comparingInt(c -> c.renderOrder));
        for (Canvas canvas : canvasList)
            canvas.renderElement(g);
    }

    private void seperate(GameScene scene) {
        List<Canvas> canvasList = scene.findGameObjects(Canvas.class);
        worldCanvasList.clear();
        overlayCanvasList.clear();

        for (Canvas canvas : canvasList) {
            canvas.rendered = false;

            if (canvas.space == Canvas.RenderSpace.World)
                worldCanvasList.add(canvas);
            else if (canvas.space == Canvas.RenderSpace.Overlay)
                overlayCanvasList.add(canvas);
        }
    }
}
