package at.htl.flowstate;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.level.tiled.TiledMap;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Game extends GameApplication {

    private Entity player;

    private final int WINDOW_WIDTH = 1960;
    private final int WINDOW_HEIGHT = 1080; // FXGL.getAppHeight()
    private final double PLAYER_W = 40;
    private final double PLAYER_H = 80;
    private final double FLOOR_H = 40;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("FXGL Game");
        settings.setVersion("0.2.2");
        settings.setWidth(WINDOW_WIDTH);
        settings.setHeight(WINDOW_HEIGHT);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 800);
    }

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).moveLeft();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.A);

        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).moveRight();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.D);

        getInput().addAction(new UserAction("Jump") {
            @Override
            protected void onActionBegin() {
                player.getComponent(PlayerComponent.class).jump();
            }

            @Override
            protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stopJump();
            }
        }, KeyCode.W);
    }

    @Override
    protected void initGame() {
        getGameScene().setBackgroundColor(Color.LIGHTBLUE);

        // 1. Setup Player Physics
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(new FixtureDef().friction(0).density(1.0f));
        physics.setOnPhysicsInitialized(() -> physics.getBody().setFixedRotation(true));

        // 2. Build Player
        player = entityBuilder()
                .at(WINDOW_WIDTH / 2.0, WINDOW_HEIGHT - FLOOR_H - PLAYER_H)
                .viewWithBBox(new Rectangle(PLAYER_W, PLAYER_H, Color.DODGERBLUE))
                .with(physics)
                .with(new PlayerComponent())
                .collidable()
                .buildAndAttach();

        // 3. Build Platforms
        createPlatform(0, WINDOW_HEIGHT - FLOOR_H, WINDOW_WIDTH, FLOOR_H, Color.BROWN);
        createPlatform(300, WINDOW_HEIGHT - 200, 200, FLOOR_H, Color.RED);
        createPlatform(800, WINDOW_HEIGHT - 340, 200, FLOOR_H, Color.RED);

        // 4. Load test asset
        ImageView testasset = FXGL.texture("testasset.png");

        Entity e = FXGL.entityBuilder()
                .at(100, 100)
                .view(testasset)
                .with(new PhysicsComponent())
                .buildAndAttach();
    }

    private Entity createPlatform(double x, double y, double w, double h, Color color) {
        PhysicsComponent platPhysics = new PhysicsComponent();
        platPhysics.setBodyType(BodyType.STATIC);

        return entityBuilder()
                .at(x, y)
                .viewWithBBox(new Rectangle(w, h, color))
                .with(platPhysics)
                .collidable()
                .buildAndAttach();
    }

    public static void main(String[] args) {
        launch(args);
    }
}