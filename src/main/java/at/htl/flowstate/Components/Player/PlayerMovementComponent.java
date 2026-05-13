package at.htl.flowstate.Components.Player;

import at.htl.flowstate.Components.Player.Skills.MagicSkillComponent;
import at.htl.flowstate.Components.SpriteComponents.SpriteComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.geometry.Point2D;

import java.util.List;

public class PlayerMovementComponent extends Component {
    private PhysicsComponent physics;

    private static final double JUMP_FORCE = 600.0;
    private static final double START_MOVE_SPEED_PCT = 60.0;
    private static final double MAX_MOVE_SPEED = 400;
    private static final double MAX_SPEED_FRAMES = 80;
    private static final double MAX_SPEED_THRESHOLD = 20;
    private static final int COYOTE_FRAMES = 5;
    private static final double LEVITATION_SPEED = 300.0;

    private static final int LANDING_FRAMES_TEXTURES = 12;

    private static final double PLAYER_WIDTH = 40.0;
    private static final double PLAYER_HEIGHT = 80.0;
    private static final double STEP_HEIGHT = 20.0;
    private static final double STEP_LOOK_AHEAD = 12.0;
    private static final double STEP_FORWARD_NUDGE = STEP_LOOK_AHEAD + 2.0;

    private boolean isGrounded = false;
    private boolean wasGrounded = false;
    private boolean jumpConsumed = false;
    private int coyoteTimer = 0;
    private double currentRunningFrames = 0;
    private int lastMoveDirection = 0;
    private int currentWatchDirection = 1;
    private int landingTimer = 0;

    private boolean isLevitationActive = false;
    private boolean isLevitatingUp = false;
    private boolean isLevitatingDown = false;

    @Override
    public void onAdded() {
        physics = entity.getComponent(PhysicsComponent.class);
    }

    @Override
    public void onUpdate(double tpf) {
        isLevitationActive = entity.getComponent(MagicSkillComponent.class).isLevitationActive();

        wasGrounded = isGrounded;

        if (isLevitationActive) {
            if (isLevitatingUp) {
                physics.setVelocityY(-LEVITATION_SPEED);
            } else if (isLevitatingDown) {
                physics.setVelocityY(LEVITATION_SPEED);
            } else {
                physics.setVelocityY(2);
            }
            isGrounded = false;
        } else {
            updateGroundState();
            if (isGrounded && lastMoveDirection != 0) {
                tryStep(lastMoveDirection);
            }
        }

        keepOnScreen();
        updateSprite();
    }

    private void updateSprite() {
        SpriteComponent sprite = entity.getComponent(SpriteComponent.class);

        if (!wasGrounded && isGrounded) {
            landingTimer = LANDING_FRAMES_TEXTURES;
        }

        if (landingTimer > 0) {
            landingTimer--;
            sprite.setLand();
        } else if (!isGrounded) {
            sprite.setJump();
        } else if (lastMoveDirection != 0) {
            sprite.setWalk();
        } else {
            sprite.setIdle();
        }
    }

    private void tryStep(int direction) {
        double feetY = entity.getY() + PLAYER_HEIGHT;
        double leadingEdge = direction > 0 ? entity.getX() + PLAYER_WIDTH : entity.getX();

        List<Entity> platforms = FXGL.getGameWorld().getEntitiesFiltered(e -> {
            if (e == entity) return false;
            PhysicsComponent pc = e.getComponentOptional(PhysicsComponent.class).orElse(null);
            return pc != null && pc.getBody().getType() == BodyType.STATIC;
        });

        for (Entity platform : platforms) {
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
            double newX = entity.getX() + direction * STEP_FORWARD_NUDGE;
            double newY = platTop - PLAYER_HEIGHT;
            physics.overwritePosition(new Point2D(newX, newY));
            physics.setVelocityX(savedVelX);
            physics.setVelocityY(0);
            return;
        }
    }

    private void keepOnScreen() {
        double viewX = FXGL.getGameScene().getViewport().getX();
        if (entity.getX() < viewX) {
            entity.setX(viewX);
            if (physics.getVelocityX() < 0) {
                physics.setVelocityX(0);
            }
        }
    }

    public void startMoveRight() {
        lastMoveDirection =  1;
        move( 1);
        currentWatchDirection =  1;
    }

    public void startMoveLeft() {
        lastMoveDirection = -1;
        move(-1);
        currentWatchDirection = -1;
    }

    public void stopHorizontalMovement() {
        lastMoveDirection = 0;
        currentRunningFrames = 0;
        physics.setVelocityX(0);
    }

    private void move(int direction) {
        double speed = (MAX_MOVE_SPEED * entity.getComponent(MagicSkillComponent.class).getCurrentSpeedMult() / 100.0)
                * (START_MOVE_SPEED_PCT + ((100.0 - START_MOVE_SPEED_PCT) / 100.0) * (currentRunningFrames / MAX_SPEED_FRAMES) * 100.0);

        if (isGrounded) {
            if (currentRunningFrames < MAX_SPEED_FRAMES) currentRunningFrames++;
        } else {
            if (currentRunningFrames > MAX_SPEED_THRESHOLD) currentRunningFrames -= 0.5;
        }

        physics.setVelocityX(speed * direction);
    }

    public void startJump() {
        if (isLevitationActive) {
            isLevitatingUp = true;
        } else {
            if ((isGrounded || coyoteTimer > 0) && !jumpConsumed) {
                physics.setVelocityY(-JUMP_FORCE);
                jumpConsumed = true;
                isGrounded = false;
                coyoteTimer = 0;
            }
        }
    }

    public void stopJump() {
        if (isLevitationActive) {
            isLevitatingUp = false;
        } else {
            if (physics.getVelocityY() < 0) {
                physics.setVelocityY(physics.getVelocityY() * 0.45);
            }
        }
    }

    public void startDown() {
        if (!isLevitationActive) return;
        isLevitatingDown = true;
    }

    public void stopDown() {
        if (!isLevitationActive) return;
        isLevitatingDown = false;
    }

    private void updateGroundState() {
        boolean wasGroundedLocal = isGrounded;
        isGrounded = Math.abs(physics.getVelocityY()) < 0.1;

        if (wasGroundedLocal && !isGrounded) {
            coyoteTimer = COYOTE_FRAMES;
        } else if (isGrounded) {
            coyoteTimer = 0;
            jumpConsumed = false;
        } else if (coyoteTimer > 0) {
            coyoteTimer--;
        }
    }

    public int getCurrentWatchDirection() { return currentWatchDirection; }
}