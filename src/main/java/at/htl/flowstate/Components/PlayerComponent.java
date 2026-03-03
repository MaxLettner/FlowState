package at.htl.flowstate.Components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

public class PlayerComponent extends Component {

    private PhysicsComponent physics;

    private static final double JUMP_FORCE = 600.0;
    private static final double START_MOVE_SPEED_PCT = 60.0;
    private static final double MAX_MOVE_SPEED = 400;
    private static final double MAX_SPEED_FRAMES = 80;
    private static final double MAX_SPEED_THRESHOLD = 20;
    private static final int COYOTE_FRAMES = 5;

    private boolean isGrounded = false;
    private boolean jumpConsumed = false;
    private int coyoteTimer = 0;
    private double currentRunningFrames = 0;


    @Override
    public void onAdded() {
        // This links the physics component from the entity to this variable
        physics = entity.getComponent(PhysicsComponent.class);
    }

    @Override
    public void onUpdate(double tpf) {
        updateGroundState();
    }

    public void moveRight() {
        move(1);
    }

    public void moveLeft() {
        move(-1);
    }

    public void stop() {
        currentRunningFrames = 0;
        physics.setVelocityX(0);
    }

    private void move(int direction) {
        double speed = (MAX_MOVE_SPEED / 100.0) * (START_MOVE_SPEED_PCT + ((100.0 - START_MOVE_SPEED_PCT) / 100.0) * (currentRunningFrames / MAX_SPEED_FRAMES) * 100.0);

        if (isGrounded) {
            if (currentRunningFrames < MAX_SPEED_FRAMES) {
                currentRunningFrames++;
            }
        } else {
            if (currentRunningFrames > MAX_SPEED_THRESHOLD) {
                currentRunningFrames -= 0.5;
            }
        }

        physics.setVelocityX(speed * direction);

        checkPlayerBounds(direction);
    }

    public void jump() {
        if ((isGrounded || coyoteTimer > 0) && !jumpConsumed) {
            physics.setVelocityY(-JUMP_FORCE);
            jumpConsumed = true;
            isGrounded = false;
            coyoteTimer = 0;
        }
    }

    public void stopJump() {
        if (physics.getVelocityY() < 0) {
            physics.setVelocityY(physics.getVelocityY() * 0.45);
        }
    }

    private void updateGroundState() {
        boolean wasGrounded = isGrounded;

        // Check if vertical velocity is near zero
        isGrounded = Math.abs(physics.getVelocityY()) < 0.1;

        if (wasGrounded && !isGrounded) {
            coyoteTimer = COYOTE_FRAMES;
        } else if (isGrounded) {
            coyoteTimer = 0;
            jumpConsumed = false;
        } else if (coyoteTimer > 0) {
            coyoteTimer--;
        }
    }

    private void checkPlayerBounds(int direction) {
        if (getEntity().getX() < 0 && direction < 0) {
            getEntity().setX(0);
            physics.setVelocityX(0);
        }
    }
}