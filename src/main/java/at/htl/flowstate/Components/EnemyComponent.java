package at.htl.flowstate.Components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.geometry.Point2D;

import java.util.List;

public class EnemyComponent extends Component {

    private Entity player;
    private PhysicsComponent physics;

    private static final double MOVE_SPEED         = 250.0;
    private static final double JUMP_FORCE         = 600.0;
    private static final double STEP_HEIGHT        = 20.0;
    private static final double STEP_LOOK_AHEAD    = 12.0;
    private static final double STEP_FORWARD_NUDGE = STEP_LOOK_AHEAD + 2.0;
    private static final double JUMP_LOOK_AHEAD    = 30.0;
    private static final double MAX_JUMP_HEIGHT    = 300.0;

    private static final double ENEMY_WIDTH  = 40.0;
    private static final double ENEMY_HEIGHT = 80.0;

    private boolean isGrounded   = false;
    private boolean jumpConsumed = false;

    private double health    = 100;
    private double maxHealth = 100;

    public EnemyComponent(Entity player) {
        this.player = player;
    }

    @Override
    public void onAdded() {
        physics = entity.getComponent(PhysicsComponent.class);
        physics.setOnPhysicsInitialized(() -> physics.getBody().setFixedRotation(true));
    }

    @Override
    public void onUpdate(double tpf) {
        updateGroundState();
        chasePlayer();
    }

    private void chasePlayer() {
        double dx = player.getX() - entity.getX();
        int direction = dx > 0 ? 1 : -1;

        physics.setVelocityX(MOVE_SPEED * direction);

        if (isGrounded) {
            tryStep(direction);
            tryJump(direction);
        }
    }

    private void tryStep(int direction) {
        double feetY       = entity.getY() + ENEMY_HEIGHT;
        double leadingEdge = direction > 0 ? entity.getX() + ENEMY_WIDTH : entity.getX();

        for (Entity platform : getStaticPlatforms()) {
            double platTop   = platform.getY();
            double platLeft  = platform.getX();
            double platRight = platform.getX() + platform.getWidth();

            boolean adjacent = direction > 0
                    ? platLeft >= leadingEdge - 2.0 && platLeft <= leadingEdge + STEP_LOOK_AHEAD
                    : platRight <= leadingEdge + 2.0 && platRight >= leadingEdge - STEP_LOOK_AHEAD;

            if (!adjacent) continue;

            double stepSize = feetY - platTop;
            if (stepSize < 1.0 || stepSize > STEP_HEIGHT) continue;

            double savedVelX = physics.getVelocityX();
            physics.overwritePosition(new Point2D(entity.getX() + direction * STEP_FORWARD_NUDGE, platTop - ENEMY_HEIGHT));
            physics.setVelocityX(savedVelX);
            physics.setVelocityY(0);
            return;
        }
    }

    private void tryJump(int direction) {
        if (jumpConsumed) return;

        double feetY       = entity.getY() + ENEMY_HEIGHT;
        double leadingEdge = direction > 0 ? entity.getX() + ENEMY_WIDTH : entity.getX();

        for (Entity platform : getStaticPlatforms()) {
            double platTop   = platform.getY();
            double platLeft  = platform.getX();
            double platRight = platform.getX() + platform.getWidth();

            boolean ahead = direction > 0
                    ? platLeft >= leadingEdge - 2.0 && platLeft <= leadingEdge + JUMP_LOOK_AHEAD
                    : platRight <= leadingEdge + 2.0 && platRight >= leadingEdge - JUMP_LOOK_AHEAD;

            if (!ahead) continue;

            double obstacleHeight = feetY - platTop;
            if (obstacleHeight <= STEP_HEIGHT || obstacleHeight > MAX_JUMP_HEIGHT) continue;

            physics.setVelocityY(-JUMP_FORCE);
            jumpConsumed = true;
            return;
        }
    }

    private void updateGroundState() {
        isGrounded = Math.abs(physics.getVelocityY()) < 0.1;
        if (isGrounded) jumpConsumed = false;
    }

    private List<Entity> getStaticPlatforms() {
        return FXGL.getGameWorld().getEntitiesFiltered(e -> {
            if (e == entity) return false;
            PhysicsComponent pc = e.getComponentOptional(PhysicsComponent.class).orElse(null);
            return pc != null && pc.getBody().getType() == BodyType.STATIC;
        });
    }

    public double getHealth()    { return health; }
    public double getMaxHealth() { return maxHealth; }
}