package java2d.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class Canvas extends UIElement {

    public RenderSpace space = RenderSpace.Overlay;

    boolean rendered;

    private Image canvasImage;

    private Graphics2D canvasGraphics;

    public Canvas() {
        backgroundColor = borderColor = new Color(0, 0, 0, 0);
    }

    @Override
    protected void onActivated() {
        createCanvasImage();
    }

    @Override
    protected void onSizeChanged() {
        createCanvasImage();
    }

    private void createCanvasImage() {
        Dimension size = getSize();

        if (canvasImage != null &&
                (canvasImage.getWidth(null) != size.width || canvasImage.getHeight(null) != size.height)) {
            canvasImage = null;

            if (canvasGraphics != null) {
                canvasGraphics.dispose();
                canvasGraphics = null;
            }
        }

        if (canvasImage == null) {
            if (size.width != 0 && size.height != 0) {
                canvasImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
                canvasGraphics = (Graphics2D) canvasImage.getGraphics();
            }
        }
    }

    private void renderCanvas(Graphics2D g) {
        if (space == RenderSpace.World) {
            Camera camera = getScene().getCamera();

            if (camera != null)
                affineTransform.preConcatenate(camera.getAffineTransform());
        }

        g.drawImage(canvasImage, affineTransform, null);
    }

    private void prepare() {
        setupAntialias(canvasGraphics);

        if (space == RenderSpace.Overlay)
            prepare(null, canvasGraphics);
        else if (space == RenderSpace.World)
            prepare(this, canvasGraphics);
    }

    void renderElement(Graphics2D g) {
        if (visible && !rendered && canvasGraphics != null) {
            prepare();
            onRender(canvasGraphics);
            renderChildren(canvasGraphics);
            renderCanvas(g);
            rendered = true;
        }
    }

    public enum RenderSpace {
        Overlay,
        World
    }
}
