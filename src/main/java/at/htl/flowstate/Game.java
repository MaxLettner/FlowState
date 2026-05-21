package at.htl.flowstate;

import at.htl.flowstate.Components.Player.PlayerRouterComponent;
import at.htl.flowstate.Components.Player.PlayerMovementComponent;
import at.htl.flowstate.Components.Player.PlayerStatsComponent;
import at.htl.flowstate.Components.Player.Skills.MagicSkillComponent;
import at.htl.flowstate.Components.Player.Skills.MeleeSkillComponent;
import at.htl.flowstate.Components.Player.Skills.RangedSkillComponent;
import at.htl.flowstate.Components.SpriteComponents.SpriteComponent;
import at.htl.flowstate.Factories.EnemyFactory;
import at.htl.flowstate.Factories.LevelFactory;
import at.htl.flowstate.Generation.LevelGeneration;
import at.htl.flowstate.Menu.SkillTree.SkillTree;
import at.htl.flowstate.Menu.GameMenu;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class Game extends GameApplication {

    private static Entity player;
    private LevelGeneration levelGeneration;

    private final double WINDOW_WIDTH = 1920;
    private final double WINDOW_HEIGHT = 1080;

    private SkillTree skillTree;

    private static final short CATEGORY_TERRAIN = 0x0001;
    private static final short CATEGORY_PLAYER = 0x0002;
    private static final short CATEGORY_ENEMY = 0x0004;

    private ProgressBar hpBar;
    private ProgressBar mpBar;
    private ProgressBar epBar;

    private static final double BG_SCROLL_FACTOR = 0.05;
    private ImageView bg1;
    private ImageView bg2;
    private double bgWidth;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("FlowState");
        settings.setVersion("0.6.1");
        settings.setWidth((int) WINDOW_WIDTH);
        settings.setHeight((int) WINDOW_HEIGHT);
        settings.setSceneFactory(new SceneFactory() {
            @NotNull
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
        //------User Actions-----
        getInput().addAction(new UserAction("Left") {
            @Override protected void onAction() { player.getComponent(PlayerMovementComponent.class).startMoveLeft(); }
            @Override protected void onActionEnd() { player.getComponent(PlayerMovementComponent.class).stopHorizontalMovement(); }
        }, KeyCode.A);

        getInput().addAction(new UserAction("Right") {
            @Override protected void onAction() { player.getComponent(PlayerMovementComponent.class).startMoveRight(); }
            @Override protected void onActionEnd() { player.getComponent(PlayerMovementComponent.class).stopHorizontalMovement(); }
        }, KeyCode.D);

        getInput().addAction(new UserAction("Jump") {
            @Override protected void onActionBegin() { player.getComponent(PlayerMovementComponent.class).startJump(); }
            @Override protected void onActionEnd() { player.getComponent(PlayerMovementComponent.class).stopJump(); }
        }, KeyCode.W);
        getInput().addAction(new UserAction("Down") {
            @Override protected void onActionBegin() { player.getComponent(PlayerMovementComponent.class).startDown(); }
            @Override protected void onActionEnd() { player.getComponent(PlayerMovementComponent.class).stopDown(); }
        }, KeyCode.S);
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
                if(!skillTree.isOpen()) player.getComponent(PlayerRouterComponent.class).doCurrentAction();
            }
        }, MouseButton.PRIMARY);
    }

    @Override
    protected void initGame() {
        setupBars();
        setupFactories();
        levelGeneration = new LevelGeneration();
        setupPlayer();
        getGameScene().getViewport().setBounds(0, 0, Integer.MAX_VALUE, (int) WINDOW_HEIGHT); //setup bounds
        skillTree = new SkillTree();
        initHealthBar();
        initManaBar();
        initExpBar();
        setupBg();
    }

    //------SETUP-----
    private void setupBars() {
        hpBar = new ProgressBar();
        mpBar = new ProgressBar();
        epBar = new ProgressBar();
    }

    private void setupFactories() {
        getGameWorld().addEntityFactory(new LevelFactory());
        getGameWorld().addEntityFactory(new EnemyFactory());
    }

    private void setupPlayer() {
        FixtureDef playerFd = new FixtureDef().friction(0).density(1.0f);
        playerFd.getFilter().categoryBits = CATEGORY_PLAYER;
        playerFd.getFilter().maskBits = CATEGORY_TERRAIN;

        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(playerFd);
        physics.setOnPhysicsInitialized(() -> physics.getBody().setFixedRotation(true));

        player = entityBuilder()
                .at(300, levelGeneration.getBaseY() - 150)
                .bbox(new HitBox(BoundingShape.box(40, 80)))
                .with(physics)
                .with(new PlayerMovementComponent())
                .with(new MeleeSkillComponent())
                .with(new RangedSkillComponent())
                .with(new MagicSkillComponent())
                .with(new PlayerStatsComponent())
                .with(new PlayerRouterComponent())
                .with(new SpriteComponent("Player.png", "PlayerWalking.png", "PlayerAirborne.png", "PlayerLanding.png", 100))
                .collidable()
                .buildAndAttach();
    }

    private void setupBg() {
        URL url = MeleeSkillComponent.class.getResource("/assets/textures/bg.png");
        assert url != null;

        Image img = new Image(url.toExternalForm(), WINDOW_HEIGHT/80*250, WINDOW_HEIGHT, false, true);

        bg1 = new javafx.scene.image.ImageView(img);
        bg1.setFitHeight(WINDOW_HEIGHT * 1.05);
        bg1.setPreserveRatio(true);
        bg1.setSmooth(false);
        bg1.setLayoutX(0);
        bg1.setLayoutY(0);

        bg2 = new javafx.scene.image.ImageView(img);
        bg2.setFitHeight(WINDOW_HEIGHT * 1.05);
        bg2.setPreserveRatio(true);
        bg2.setSmooth(false);

        bgWidth = (WINDOW_HEIGHT * 1.05) * (img.getWidth() / img.getHeight());
        bg2.setLayoutX(bgWidth);
        bg2.setLayoutY(0);

        getGameScene().getRoot().getChildren().add(0, bg1);
        getGameScene().getRoot().getChildren().add(1, bg2);
    }

    @Override
    protected void onUpdate(double tpf) {
        if (player.getY() > WINDOW_HEIGHT + 100) {
            getGameController().exit();
        }
        updateCamera();
        levelGeneration.generateLevel(player.getX());
        cleanupPlatforms();
        scrollBg();
        checkIfPlayerIsDead();
        updateBars();
    }

    //-----BACKGROUND-----
    private void scrollBg() {
        double offset = -(player.getX() * BG_SCROLL_FACTOR) % bgWidth;
        if (offset > 0) offset -= bgWidth;
        bg1.setLayoutX(Math.round(offset));
        bg2.setLayoutX(Math.round(offset + bgWidth));
    }

    //-----CAMERA-----
    private void updateCamera() {
        double vpX = getGameScene().getViewport().getX();
        double threshold = vpX + WINDOW_WIDTH * 0.75;
        if (player.getX() > threshold) {
            getGameScene().getViewport().setX(player.getX() - WINDOW_WIDTH * 0.75);
        }
    }

    //-----BARS-----
    private void initHealthBar() {
        hpBar.setMinValue(0);
        hpBar.setMaxValue(player.getComponent(PlayerStatsComponent.class).getMaxHealth());

        hpBar.setFill(Color.web("#921616"));
        hpBar.setHeight(15);
        hpBar.setWidth(300);

        hpBar.setLayoutX(WINDOW_WIDTH * 0.82);
        hpBar.setLayoutY(50);

        addUINode(hpBar);
    }

    private void initManaBar() {
        mpBar.setMinValue(0);
        mpBar.setMaxValue(player.getComponent(PlayerStatsComponent.class).getMaxMana());

        mpBar.setFill(Color.web("#2300d5"));
        mpBar.setHeight(15);
        mpBar.setWidth(300);
        mpBar.setLabelVisible(false);

        mpBar.setLayoutX(WINDOW_WIDTH * 0.82);
        mpBar.setLayoutY(80);

        addUINode(mpBar);
    }

    private void initExpBar() {
        epBar.setMinValue(0);
        epBar.setMaxValue(player.getComponent(PlayerStatsComponent.class).getMaxExperience());

        epBar.setFill(Color.DARKGOLDENROD);
        epBar.setHeight(15);
        epBar.setWidth(300);

        epBar.setLayoutX(WINDOW_WIDTH * 0.82);
        epBar.setLayoutY(110);

        addUINode(epBar);
    }

    private void updateBars() {
        PlayerStatsComponent statsComponent = player.getComponent(PlayerStatsComponent.class);
        hpBar.setCurrentValue(statsComponent.getHealth());
        mpBar.setCurrentValue(statsComponent.getMana());
        mpBar.setMaxValue(statsComponent.getMaxMana());
        epBar.setMaxValue(statsComponent.getMaxExperience());
        epBar.setCurrentValue(statsComponent.getExperience());
    }

    private void checkIfPlayerIsDead() {
        if(player.getComponent(PlayerStatsComponent.class).getHealth() < 0) {
            getGameController().exit();
        }
    }

    //-----HELPERS-----
    private void cleanupPlatforms() {
        double viewX = getGameScene().getViewport().getX();
        List<Entity> toRemove = getGameWorld().getEntitiesFiltered(e -> e.getX() < viewX - 3500);
        toRemove.forEach(Entity::removeFromWorld);
    }

    public static void main(String[] args) {
        Thread.currentThread().setContextClassLoader(Game.class.getClassLoader());
        launch(args); }

    public static Entity getPlayer(){
        return player;
    }
}