package at.htl.flowstate;

import at.htl.flowstate.Components.EnemyComponent;
import at.htl.flowstate.Components.PlayerComponent;
import at.htl.flowstate.Factories.LevelFactory;
import at.htl.flowstate.Generation.LevelGeneration;
import at.htl.flowstate.Menu.SkillTree;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Game extends GameApplication {

    private Entity player;
    private LevelGeneration levelGeneration;

    private final double WINDOW_WIDTH  = 1920;
    private final double WINDOW_HEIGHT = 1080;

    private static final short CATEGORY_TERRAIN = 0x0001;
    private static final short CATEGORY_PLAYER  = 0x0002;
    private static final short CATEGORY_ENEMY   = 0x0004;

    private ProgressBar hpBar;
    private ProgressBar mpBar;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("FlowState");
        settings.setVersion("0.4.1");
        settings.setWidth((int) WINDOW_WIDTH);
        settings.setHeight((int) WINDOW_HEIGHT);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 800);
    }

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Left") {
            @Override protected void onAction()    { player.getComponent(PlayerComponent.class).moveLeft(); }
            @Override protected void onActionEnd() { player.getComponent(PlayerComponent.class).stop(); }
        }, KeyCode.A);

        getInput().addAction(new UserAction("Right") {
            @Override protected void onAction()    { player.getComponent(PlayerComponent.class).moveRight(); }
            @Override protected void onActionEnd() { player.getComponent(PlayerComponent.class).stop(); }
        }, KeyCode.D);

        getInput().addAction(new UserAction("Jump") {
            @Override protected void onActionBegin() { player.getComponent(PlayerComponent.class).jump(); }
            @Override protected void onActionEnd()   { player.getComponent(PlayerComponent.class).stopJump(); }
        }, KeyCode.W);

        onKeyDown(KeyCode.I, () -> {
            FXGL.getSceneService().pushSubScene(new SkillTree());
        });
    }

    @Override
    protected void initGame() {
        hpBar = new ProgressBar();
        mpBar = new ProgressBar();

        getGameWorld().addEntityFactory(new LevelFactory());
        getGameScene().setBackgroundColor(Color.LIGHTBLUE);

        levelGeneration = new LevelGeneration();

        FixtureDef playerFd = new FixtureDef().friction(0).density(1.0f);
        playerFd.getFilter().categoryBits = CATEGORY_PLAYER;
        playerFd.getFilter().maskBits     = CATEGORY_TERRAIN;

        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(playerFd);
        physics.setOnPhysicsInitialized(() -> physics.getBody().setFixedRotation(true));

        player = entityBuilder()
                .at(300, levelGeneration.getBaseY() - 150)
                .viewWithBBox(new Rectangle(40, 80, Color.DODGERBLUE))
                .with(physics)
                .with(new PlayerComponent())
                .collidable()
                .buildAndAttach();

        FixtureDef enemyFd = new FixtureDef().friction(0).density(1.0f);
        enemyFd.getFilter().categoryBits = CATEGORY_ENEMY;
        enemyFd.getFilter().maskBits     = CATEGORY_TERRAIN;

        PhysicsComponent pc = new PhysicsComponent();
        pc.setBodyType(BodyType.DYNAMIC);
        pc.setFixtureDef(enemyFd);

        entityBuilder()
                .at(300, levelGeneration.getBaseY() - 150)
                .viewWithBBox(new Rectangle(40, 80, Color.GREEN))
                .with(pc)
                .with(new EnemyComponent(player))
                .collidable()
                .buildAndAttach();

        getGameScene().getViewport().setBounds(0, 0, Integer.MAX_VALUE, (int) WINDOW_HEIGHT);

        initHealthBar();
        initManaBar();
    }

    @Override
    protected void onUpdate(double tpf) {
        if (player.getY() > WINDOW_HEIGHT + 100) {
            getGameController().exit();
        }
        updateCamera();
        levelGeneration.generateLevel(player.getX());
        cleanupPlatforms();

        hpBar.setCurrentValue(player.getComponent(PlayerComponent.class).getHealth());
        mpBar.setCurrentValue(player.getComponent(PlayerComponent.class).getMana());
    }

    //-----CAMERA-----

    private void updateCamera() {
        double vpX       = getGameScene().getViewport().getX();
        double threshold = vpX + WINDOW_WIDTH * 0.75;
        if (player.getX() > threshold) {
            getGameScene().getViewport().setX(player.getX() - WINDOW_WIDTH * 0.75);
        }
    }

    //-----HELPERS-----

    private void cleanupPlatforms() {
        double viewX           = getGameScene().getViewport().getX();
        List<Entity> toRemove  = getGameWorld().getEntitiesFiltered(e -> e.getX() < viewX - 3500);
        toRemove.forEach(Entity::removeFromWorld);
    }

    private void initHealthBar() {
        hpBar.setMinValue(0);
        hpBar.setMaxValue(player.getComponent(PlayerComponent.class).getMaxHealth());

        hpBar.setFill(Color.web("#921616"));
        hpBar.setHeight(15);
        hpBar.setWidth(300);

        hpBar.setLayoutX(WINDOW_WIDTH * 0.82);
        hpBar.setLayoutY(50);

        addUINode(hpBar);
    }

    private void initManaBar() {
        mpBar.setMinValue(0);
        mpBar.setMaxValue(player.getComponent(PlayerComponent.class).getMaxMana());

        mpBar.setFill(Color.web("#2300d5"));
        mpBar.setHeight(15);
        mpBar.setWidth(300);
        mpBar.setLabelVisible(false);

        mpBar.setLayoutX(WINDOW_WIDTH * 0.82);
        mpBar.setLayoutY(80);

        addUINode(mpBar);
    }

    public static void main(String[] args) { launch(args); }
}