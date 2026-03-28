package at.htl.flowstate.Components.Player;

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

    private static final double PLAYER_WIDTH = 40.0;
    private static final double PLAYER_HEIGHT = 80.0;
    private static final double STEP_HEIGHT = 20.0;
    private static final double STEP_LOOK_AHEAD = 12.0;

    //after snapping push the player this many px forward so the ledge face
    //is no longer in the way next frame breaking the snap feedback loop
    private static final double STEP_FORWARD_NUDGE = STEP_LOOK_AHEAD + 2.0;

    private boolean isGrounded = false;
    private boolean jumpConsumed = false;
    private int coyoteTimer = 0;
    private double  currentRunningFrames = 0;
    private int lastMoveDirection = 0;
    private int currentWatchDirection = 1;

    @Override
    public void onAdded() {
        physics = entity.getComponent(PhysicsComponent.class);
    }

    @Override
    public void onUpdate(double tpf) {
        updateGroundState();
        keepOnScreen();
        if (isGrounded && lastMoveDirection != 0) {
            tryStep(lastMoveDirection);
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

            //snap up and nudge forward so the ledge face exits the look-ahead-zone
            //which prevents the feedback loop where gravity drops the player 1px and
            //triggers another snap next frame
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

    public void moveRight() {
        lastMoveDirection =  1;
        move( 1);
        currentWatchDirection = 1;
    }
    public void moveLeft() {
        lastMoveDirection = -1;
        move(-1);
        currentWatchDirection = -1;
    }

    public void stop() {
        lastMoveDirection = 0;
        currentRunningFrames = 0;
        physics.setVelocityX(0);
    }

    private void move(int direction) {
        double speed = (MAX_MOVE_SPEED / 100.0) * (START_MOVE_SPEED_PCT + ((100.0 - START_MOVE_SPEED_PCT) / 100.0) * (currentRunningFrames / MAX_SPEED_FRAMES) * 100.0);

        if (isGrounded) {
            if (currentRunningFrames < MAX_SPEED_FRAMES) currentRunningFrames++;
        } else {
            if (currentRunningFrames > MAX_SPEED_THRESHOLD) currentRunningFrames -= 0.5;
        }

        physics.setVelocityX(speed * direction);
    }

    public void jump() {
        if ((isGrounded || coyoteTimer > 0) && !jumpConsumed) {
            physics.setVelocityY(-JUMP_FORCE);
            jumpConsumed = true;
            isGrounded   = false;
            coyoteTimer  = 0;
        }
    }

    public void stopJump() {
        if (physics.getVelocityY() < 0) {
            physics.setVelocityY(physics.getVelocityY() * 0.45);
        }
    }

    private void updateGroundState() {
        boolean wasGrounded = isGrounded;
        isGrounded = Math.abs(physics.getVelocityY()) < 0.1;

        if (wasGrounded && !isGrounded) {
            coyoteTimer = COYOTE_FRAMES;
        } else if (isGrounded) {
            coyoteTimer  = 0;
            jumpConsumed = false;
        } else if (coyoteTimer > 0) {
            coyoteTimer--;
        }
    }

    public int getCurrentWatchDirection() { return currentWatchDirection; }

}