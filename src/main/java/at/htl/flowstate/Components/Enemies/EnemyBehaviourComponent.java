package at.htl.flowstate.Components.Enemies;

import at.htl.flowstate.Components.Identifier.PlatformIdentifierComponent;
import at.htl.flowstate.Components.SpriteComponents.SpriteComponent;
import at.htl.flowstate.Game;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;

import java.util.List;

public abstract class EnemyBehaviourComponent extends Component {
    protected Entity player;
    protected PhysicsComponent physics;

    protected static final double MOVE_SPEED = 200.0;
    protected static final double JUMP_FORCE = 600.0;
    protected static final double STEP_HEIGHT = 20.0;
    protected static final double STEP_LOOK_AHEAD = 12.0;
    protected static final double STEP_FORWARD_NUDGE = STEP_LOOK_AHEAD + 2.0;
    protected static final double JUMP_LOOK_AHEAD = 30.0;
    protected static final double MAX_JUMP_HEIGHT = 300.0;

    protected final double specificMoveSpeed = MOVE_SPEED * FXGLMath.random(0.9, 1.1);
    protected final double specificJumpForce = JUMP_FORCE * FXGLMath.random(0.9, 1.1);

    protected static final double ENEMY_WIDTH = 40.0;
    protected static final double ENEMY_HEIGHT = 80.0;

    protected static final double DAMAGE = 10.0;

    protected boolean isGrounded = false;
    protected boolean wasGrounded = false;
    protected boolean jumpConsumed = false;
    protected int lastMoveDirection = 1;

    public EnemyBehaviourComponent() {
        this.player = Game.getPlayer();
    }

    @Override
    public void onAdded() {
        physics = entity.getComponent(PhysicsComponent.class);
        physics.setOnPhysicsInitialized(() -> physics.getBody().setFixedRotation(true));
    }

    @Override
    public void onUpdate(double tpf) {
        wasGrounded = isGrounded;
        updateGroundState();

        EnemyStatsComponent stats = entity.getComponent(EnemyStatsComponent.class);
        if (!stats.getIsCurrentlyStunned() && !stats.isKnockedBack()) {
            chasePlayer();
            attack();
        } else if (!stats.isKnockedBack()) {
            physics.setVelocityX(0);
        }

        updateSprite();
    }

    public abstract void attack();

    protected void chasePlayer() {
        double dx = player.getX() - entity.getX();
        int direction = dx < 10 && dx > -10 ? 0 : dx > 0 ? 1 : -1;

        if (direction != 0) lastMoveDirection = direction;
        physics.setVelocityX(specificMoveSpeed * direction);

        if (isGrounded) {
            tryStep(direction);
            tryJump(direction);
        }
    }

    private void updateSprite() {
        SpriteComponent sprite = entity.getComponent(SpriteComponent.class);

        if (!isGrounded) {
            if (lastMoveDirection >= 0) sprite.setJumpRight();
            else                        sprite.setJumpLeft();
        } else if (Math.abs(physics.getVelocityX()) > 1.0) {
            if (lastMoveDirection >= 0) sprite.setWalkRight();
            else                        sprite.setWalkLeft();
        } else {
            if (lastMoveDirection >= 0) sprite.setIdleRight();
            else                        sprite.setIdleLeft();
        }
    }

    protected void tryStep(int direction) {
        double feetY = entity.getY() + ENEMY_HEIGHT;
        double leadingEdge = direction > 0 ? entity.getX() + ENEMY_WIDTH : entity.getX();

        for (Entity platform : getPlatforms()) {
            double platTop = platform.getY();
            double platLeft = platform.getX();
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

    protected void tryJump(int direction) {
        if (jumpConsumed) return;

        double feetY = entity.getY() + ENEMY_HEIGHT;
        double leadingEdge = direction > 0 ? entity.getX() + ENEMY_WIDTH : entity.getX();

        for (Entity platform : getPlatforms()) {
            double platTop = platform.getY();
            double platLeft = platform.getX();
            double platRight = platform.getX() + platform.getWidth();

            boolean ahead = direction > 0
                    ? platLeft >= leadingEdge - 2.0 && platLeft <= leadingEdge + JUMP_LOOK_AHEAD
                    : platRight <= leadingEdge + 2.0 && platRight >= leadingEdge - JUMP_LOOK_AHEAD;

            if (!ahead) continue;

            double obstacleHeight = feetY - platTop;
            if (obstacleHeight <= STEP_HEIGHT || obstacleHeight > MAX_JUMP_HEIGHT) continue;

            physics.setVelocityY(-specificJumpForce);
            jumpConsumed = true;
            return;
        }
    }

    private void updateGroundState() {
        isGrounded = Math.abs(physics.getVelocityY()) < 0.1;
        if (isGrounded) jumpConsumed = false;
    }

    private List<Entity> getPlatforms() {
        return FXGL.getGameWorld().getEntitiesFiltered(
                e -> e.getComponentOptional(PlatformIdentifierComponent.class).isPresent()
        );
    }
}