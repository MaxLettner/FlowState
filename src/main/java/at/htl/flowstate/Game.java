package at.htl.flowstate;

import at.htl.flowstate.Components.PlayerComponent;
import at.htl.flowstate.Components.Skills.MeeleSkillComponent;
import at.htl.flowstate.Factories.EnemyFactory;
import at.htl.flowstate.Factories.LevelFactory;
import at.htl.flowstate.Generation.LevelGeneration;
import at.htl.flowstate.Menu.SkillTree.SkillTree;
import at.htl.flowstate.Menu.GameMenu;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Game extends GameApplication {

    private Entity player;
    private LevelGeneration levelGeneration;

    private final double WINDOW_WIDTH = 1920;
    private final double WINDOW_HEIGHT = 1080;

    private SkillTree skillTree;

    private static final short CATEGORY_TERRAIN = 0x0001;
    private static final short CATEGORY_PLAYER = 0x0002;
    private static final short CATEGORY_ENEMY = 0x0004;

    private ProgressBar hpBar;
    private ProgressBar mpBar;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("FlowState");
        settings.setVersion("0.4.1");
        settings.setWidth((int) WINDOW_WIDTH);
        settings.setHeight((int) WINDOW_HEIGHT);
        settings.setSceneFactory(new SceneFactory() {
            @Override
            public com.almasb.fxgl.app.scene.FXGLMenu newGameMenu() {
                return new GameMenu();
            }
        });
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
        getInput().addAction(new UserAction("Toggle Skill Tree") {
            @Override
            protected void onActionBegin() {
                if (skillTree.isOpen()) {
                    skillTree.close();
                } else {
                    skillTree.open();
                }
            }
        }, KeyCode.I);
        getInput().addAction(new UserAction("Attack") {
            @Override protected void onActionBegin() {
                //just for testing, needs to be overhauled
                player.getComponent(MeeleSkillComponent.class).doSub1Skill2();
            }
        }, MouseButton.PRIMARY);

    }

    @Override
    protected void initGame() {
        hpBar = new ProgressBar();
        mpBar = new ProgressBar();

        getGameWorld().addEntityFactory(new LevelFactory());
        getGameWorld().addEntityFactory(new EnemyFactory());
        getGameScene().setBackgroundColor(Color.LIGHTBLUE);

        levelGeneration = new LevelGeneration();

        FixtureDef playerFd = new FixtureDef().friction(0).density(1.0f);
        playerFd.getFilter().categoryBits = CATEGORY_PLAYER;
        playerFd.getFilter().maskBits = CATEGORY_TERRAIN;

        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(playerFd);
        physics.setOnPhysicsInitialized(() -> physics.getBody().setFixedRotation(true));

        player = entityBuilder()
                .at(300, levelGeneration.getBaseY() - 150)
                .viewWithBBox(new Rectangle(40, 80, Color.DODGERBLUE))
                .with(physics)
                .with(new PlayerComponent())
                .with(new MeeleSkillComponent())
                .collidable()
                .buildAndAttach();

        //---just for testing---
        spawnMeeleEnemy(200, levelGeneration.getBaseY() - 150);
        //-----

        getGameScene().getViewport().setBounds(0, 0, Integer.MAX_VALUE, (int) WINDOW_HEIGHT);

        skillTree = new SkillTree();

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
        double vpX = getGameScene().getViewport().getX();
        double threshold = vpX + WINDOW_WIDTH * 0.75;
        if (player.getX() > threshold) {
            getGameScene().getViewport().setX(player.getX() - WINDOW_WIDTH * 0.75);
        }
    }

    //-----HELPERS-----
    private void cleanupPlatforms() {
        double viewX = getGameScene().getViewport().getX();
        List<Entity> toRemove = getGameWorld().getEntitiesFiltered(e -> e.getX() < viewX - 3500);
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

    private void spawnMeeleEnemy(double x, double y) {
        //y = levelGeneration.getBaseY() - 150
        spawn("meeleEnemy", new SpawnData(x, y)
                .put("player", player));
    }

    private void spawnRangedEnemy(double x, double y) {
        //y = levelGeneration.getBaseY() - 150
        spawn("rangedEnemy", new SpawnData(x, y)
                .put("player", player));
    }

    public static void main(String[] args) { launch(args); }
}