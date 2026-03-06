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

    //TEST FOR GITHUB
    //the floor of each ground segment fills from its top surface all the way
    //down to DEEP_FLOOR which is twice the window height well off-screen
    //so the player never sees the bottom edge
    private final double DEEP_FLOOR = WINDOW_HEIGHT * 1.5;

    //terrain parameters
    private final double BASE_Y = 950;  //the base level the terrain hovers around
    private final double MIN_Y = 800;  //highest the terrain can rise
    private final double MAX_Y = 1000; //lowest the terrain can sink before its a wall

    //pit parameters
    private final double PIT_CHANCE = 0.06;  //6% chance each segment
    private final double PIT_MIN_WIDTH = 160;
    private final double PIT_MAX_WIDTH = 280;

    //how far ahead the world is generated
    private final double SPAWN_DISTANCE = 1400;

    //generation state
    private double lastGeneratedX = 0;
    private double currentY = BASE_Y; //current terrain surface height

    //terrain drift gives hills their momentum so they rise and fall naturally
    //rather than randomly jumping around every segment.
    //positive drift = terrain going lower = downhill
    //negative drift = going higher = uphill
    private double terrainDrift = 0;

    //how many more segments the current slope should continue before reconsidering
    private int slopeSegmentsLeft = 0;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("FlowState Endless");
        settings.setVersion("0.4.0");
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

        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(new FixtureDef().friction(0).density(1.0f));
        physics.setOnPhysicsInitialized(() -> physics.getBody().setFixedRotation(true));

        player = entityBuilder()
                .at(300, BASE_Y - 150)
                .viewWithBBox(new Rectangle(40, 80, Color.DODGERBLUE))
                .with(physics)
                .with(new PlayerComponent())
                .collidable()
                .buildAndAttach();

        //long flat starting floor
        spawnGroundSegment(0, BASE_Y, WINDOW_WIDTH * 1.5);
        lastGeneratedX = WINDOW_WIDTH * 1.5;
        currentY       = BASE_Y;

        getGameScene().getViewport().setBounds(0, 0, Integer.MAX_VALUE, (int) WINDOW_HEIGHT);
    }

    @Override
    protected void onUpdate(double tpf) {
        if (player.getY() > WINDOW_HEIGHT + 100) {
            getGameController().exit();
        }
        updateCamera();
        generateLevel();
        cleanupPlatforms();
    }

    //─────CAMERA──────────────────────────────────────────────────────────────────

    private void updateCamera() {
        double vpX       = getGameScene().getViewport().getX();
        double threshold = vpX + WINDOW_WIDTH * 0.75;
        if (player.getX() > threshold) {
            getGameScene().getViewport().setX(player.getX() - WINDOW_WIDTH * 0.75);
        }
    }

    //───GENERATION───────────────────────────────────────────────────────────────

    private void generateLevel() {
        while (player.getX() + SPAWN_DISTANCE > lastGeneratedX) {
            spawnNextSegment();
        }
    }

    private void spawnNextSegment() {
        //pit
        if (Math.random() < PIT_CHANCE) {
            double pitWidth = FXGLMath.random((int) PIT_MIN_WIDTH, (int) PIT_MAX_WIDTH);
            lastGeneratedX += pitWidth;
            //terrain after a pit snaps back toward BASE_Y
            //EXPERIMENTAL MAY BE CHANGED
            currentY      = snapToward(currentY, BASE_Y, 60);
            terrainDrift  = 0;
            slopeSegmentsLeft = 0;
            return;
        }

        //segment witdh
        double segmentW = FXGLMath.random(180, 420);

        //update slope
        currentY = nextTerrainY(segmentW);

        spawnGroundSegment(lastGeneratedX, currentY, segmentW);
        lastGeneratedX += segmentW;
    }

    /**
     *Computes the next terrain Y using a momentum based drift system
     *
     *the terrain has a drift value that represents how fast and in which
     *direction it is currently sloping the drift persists for several segments
     *before randomly being reconsidered, producing smooth hills and valleys
     *rather than chaotic random noise
     *
     *most of the time the drift is tiny (relatively flat) sometimes a bigger
     *slope kicks in for a hill or valley
     */
    private double nextTerrainY(double segmentW) {
        if (slopeSegmentsLeft <= 0) {
            //pick a new slope direction and length
            //70% of the time: nearly flat
            //30% of the time: a real slope
            double roll = Math.random();

            if (roll < 0.70) {
                //nearly flat
                terrainDrift = FXGLMath.random(-12, 12) + (BASE_Y - currentY) * 0.05;
                slopeSegmentsLeft = FXGLMath.random(3, 7);
            } else if (roll < 0.90) {
                //gentle slope
                terrainDrift = FXGLMath.random(-30, 30);
                slopeSegmentsLeft = FXGLMath.random(4, 9);
            } else {
                //hill or valley
                terrainDrift = FXGLMath.random(-60, 60);
                slopeSegmentsLeft = FXGLMath.random(3, 6);
            }

            //if near a boundary slope back towards BASE_Y
            if (currentY < MIN_Y + 30)  terrainDrift = Math.abs(terrainDrift);  // force downward (higher Y)
            if (currentY > MAX_Y - 30)  terrainDrift = -Math.abs(terrainDrift); // force upward (lower Y)
        }

        slopeSegmentsLeft--;

        double newY = currentY + terrainDrift;

        //hard clamp
        newY = Math.max(MIN_Y, Math.min(MAX_Y, newY));

        return newY;
    }

    /**
     *spawns a single ground segment: a solid rectangle whose top surface is at
     *the given Y and whose bottom extends to DEEP_FLOOR
     *this way no matter how the camera angles the player never sees the bottom
     */
    private void spawnGroundSegment(double x, double y, double width) {
        double height = DEEP_FLOOR - y;
        spawn("platform", new SpawnData(x, y)
                .put("color", Color.SADDLEBROWN)
                .put("width", width)
                .put("height", height));
    }

    // ───HELPERS───────────────────────────────────────────────────────────────

    /**moves value toward target by at most maxStep*/
    private double snapToward(double value, double target, double maxStep) {
        double diff = target - value;
        if (Math.abs(diff) <= maxStep) return target;
        return value + Math.signum(diff) * maxStep;
    }

    /**
     *removes entities that have scrolled far off the left edge
     *500px buffer keeps Box2D stable for anything still on screen
     */
    private void cleanupPlatforms() {
        double viewX = getGameScene().getViewport().getX();
        List<Entity> toRemove = getGameWorld().getEntitiesFiltered(e -> e.getX() < viewX - 3500);
        toRemove.forEach(Entity::removeFromWorld);
    }

    public static void main(String[] args) { launch(args); }
}
