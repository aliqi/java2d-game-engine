# java2d-game-engine
Java 2D game engine.

Simple game engine based on awt Graphics2D.

### How to use
1. Add **java2d-game-engine.jar** to build path.
2. import java2d.game.*;
3. Create Game and start
4. Load a scene.

### Simple demo
```java
public class Demo {

    public static void main(String[] args) {

        // Create game and start it.
        Game game = new Game("Hello");
        GameScene scene = game.getScene();

        Dimension renderSize = game.getFrame().getRenderSize();
        System.out.println("Render size: " + renderSize);

        // Create a square
        createSquare(scene);
    }

    private static void createSquare(GameScene scene) {
        GameObject square = new GameObject("square");

        // Create SpriteRender
        SpriteRender spriteRender = new SpriteRender();
        Sprite sprite = Sprite.load("classpath:assets/sprites/square.png");
        spriteRender.sprite = sprite;

        square.addComponent(spriteRender);

        scene.add(square);
    }
}
```

### game.properties
Put or create _game.properties_ file in the **resources** folder.
```properties
title=Game
version=1.0
width=1280
height=720
resizeable=false
icon=classpath:assets/sprites/profile.jpg
```