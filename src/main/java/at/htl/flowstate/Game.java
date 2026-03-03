package at.htl.flowstate;

import at.htl.flowstate.Components.PlayerComponent;
import at.htl.flowstate.Factories.PlatformFactory;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Game extends GameApplication {

    private Entity player;
    private final double WINDOW_WIDTH = 1920;
    private final double WINDOW_HEIGHT = 1080;
    private final double FLOOR_H = 40;

    // Endless logic
    private double lastGeneratedX = 0;
    private double lastGeneratedY = 950;
    private final double SPAWN_DISTANCE = 1200;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("FlowState Endless");
        settings.setVersion("0.3.1");
        settings.setWidth((int)WINDOW_WIDTH);
        settings.setHeight((int)WINDOW_HEIGHT);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 800);
    }

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Left") {
            @Override protected void onAction() { player.getComponent(PlayerComponent.class).moveLeft(); }
            @Override protected void onActionEnd() { player.getComponent(PlayerComponent.class).stop(); }
        }, KeyCode.A);

        getInput().addAction(new UserAction("Right") {
            @Override protected void onAction() { player.getComponent(PlayerComponent.class).moveRight(); }
            @Override protected void onActionEnd() { player.getComponent(PlayerComponent.class).stop(); }
        }, KeyCode.D);

        getInput().addAction(new UserAction("Jump") {
            @Override protected void onActionBegin() { player.getComponent(PlayerComponent.class).jump(); }
            @Override protected void onActionEnd() { player.getComponent(PlayerComponent.class).stopJump(); }
        }, KeyCode.W);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new PlatformFactory());
        getGameScene().setBackgroundColor(Color.LIGHTBLUE);

        // 1. Setup Player
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(new FixtureDef().friction(0).density(1.0f));
        physics.setOnPhysicsInitialized(() -> physics.getBody().setFixedRotation(true));

        player = entityBuilder()
                .at(300, WINDOW_HEIGHT - 200)
                .viewWithBBox(new Rectangle(40, 80, Color.DODGERBLUE))
                .with(physics)
                .with(new PlayerComponent())
                .collidable()
                .buildAndAttach();

        // 2. Initial Floor
        spawn("platform", new SpawnData(0, WINDOW_HEIGHT - FLOOR_H)
                .put("color", Color.BROWN)
                .put("width", WINDOW_WIDTH * 1.2)
                .put("height", FLOOR_H));

        lastGeneratedX = WINDOW_WIDTH * 1.2;

        // 3. Viewport setup
        getGameScene().getViewport().setBounds(0, 0, Integer.MAX_VALUE, (int)WINDOW_HEIGHT);
    }

    @Override
    protected void onUpdate(double tpf) {
        if (player.getY() > WINDOW_HEIGHT + 100) {
            getGameController().exit();
            lastGeneratedY = 950;
        }

        updateCamera();
        generateLevel();
        cleanupPlatforms();
    }

    private void updateCamera() {
        double currentVpX = getGameScene().getViewport().getX();

        // Threshold is 3/4 (75%) of the screen width
        double threshold = currentVpX + (WINDOW_WIDTH * 0.75);

        // Move the camera only if the player pushes past the threshold
        if (player.getX() > threshold) {
            getGameScene().getViewport().setX(player.getX() - (WINDOW_WIDTH * 0.75));
        }
    }

    private void generateLevel() {
        if (player.getX() + SPAWN_DISTANCE > lastGeneratedX) {
            double width = FXGLMath.random(200, 500);
            double x = lastGeneratedX + FXGLMath.random(50, 350);
            double y = lastGeneratedY + FXGLMath.random(-200, 200);

            // Boundary checks for vertical spawn height
            if(y < 300) y += FXGLMath.random(100, 250);
            if(y > 950) y -= FXGLMath.random(100, 250);

            spawn("platform", new SpawnData(x, y)
                    .put("color", Color.RED)
                    .put("width", width)
                    .put("height", FLOOR_H));

            lastGeneratedX = x + width;
            lastGeneratedY = y;
        }
    }

    private void cleanupPlatforms() {
        double viewX = getGameScene().getViewport().getX();
        List<Entity> toRemove = getGameWorld().getEntitiesFiltered(e -> e.getX() < viewX - 2000);
        toRemove.forEach(Entity::removeFromWorld);
    }

    public static void main(String[] args) { launch(args); }
}