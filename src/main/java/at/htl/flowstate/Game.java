package at.htl.flowstate;

import at.htl.flowstate.Components.PlayerComponent;
import at.htl.flowstate.Factories.PlatformFactory;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Game extends GameApplication {

    private Entity player;

    private final double WINDOW_WIDTH = 1960;
    private final double WINDOW_HEIGHT = 1080; //FXGL.getAppHeight()
    private final double PLAYER_W = 40;
    private final double PLAYER_H = 80;
    private final double FLOOR_H = 40;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("FlowState");
        settings.setVersion("0.2.3");
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
            @Override protected void onAction() {
                player.getComponent(PlayerComponent.class).moveLeft();
            }
            @Override protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.A);

        getInput().addAction(new UserAction("Right") {
            @Override protected void onAction() {
                player.getComponent(PlayerComponent.class).moveRight();
            }
            @Override protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.D);

        getInput().addAction(new UserAction("Jump") {
            @Override protected void onActionBegin() {
                player.getComponent(PlayerComponent.class).jump();
            }
            @Override protected void onActionEnd() {
                player.getComponent(PlayerComponent.class).stopJump();
            }
        }, KeyCode.W);
    }

    @Override
    protected void initGame() {
        getGameScene().setBackgroundColor(Color.LIGHTBLUE);

        getGameWorld().addEntityFactory(new PlatformFactory());

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
        spawn("platform", new SpawnData(0, WINDOW_HEIGHT - FLOOR_H)
                .put("color", Color.BROWN)
                .put("width", WINDOW_WIDTH)
                .put("height", FLOOR_H)
        );

        spawn("platform", new SpawnData(300, 850)
                .put("color", Color.RED)
                .put("width", 200.0)
                .put("height", FLOOR_H)
        );
        spawn("platform", new SpawnData(600, 550)
                .put("color", Color.RED)
                .put("width", 200.0)
                .put("height", FLOOR_H)
        );
    }

    @Override
    protected void onUpdate(double tpf) {
        checkIfPlayerFell();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void checkIfPlayerFell(){
        if(player.getY() > WINDOW_HEIGHT + WINDOW_HEIGHT*0.1) {
            getGameController().exit();
        }
    }
}