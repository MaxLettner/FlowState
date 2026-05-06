package at.htl.flowstate.Components.Enemies;
import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
public class EnemyStatsComponent extends Component {
    private double poisonDuration = 0;
    private final double POISON_DAMAGE = 5;
    private boolean isCurrentlyStunned = false;
    private double stunDurationLeft = 0;
    private double knockbackVelocity = 0;
    private final double KNOCKBACK_FRICTION = 600;

    @Override
    public void onUpdate(double tpf) {
        checkPoison(tpf);
        checkIfStillAlive();
        calculateStun(tpf);
        calculateKnockback(tpf);
    }

    //-----Health Check-----
    private void checkIfStillAlive() {
        if(entity.getComponent(HealthDoubleComponent.class).isZero()) {
            entity.removeFromWorld();
        }
    }

    //-----Poison-----
    public void poison(double time) {
        poisonDuration = time;
    }

    private void checkPoison(double tpf) {
        if(poisonDuration < 0) poisonDuration = 0;
        if(poisonDuration > 0) {
            poisonDuration -= tpf;
            entity.getComponent(HealthDoubleComponent.class).damage(POISON_DAMAGE*tpf);
        }
    }

    //-----Stun-----
    public boolean getIsCurrentlyStunned() {
        return isCurrentlyStunned;
    }

    public void stun(double duration) {
        stunDurationLeft = duration;
    }

    private void calculateStun(double tpf) {
        if(knockbackVelocity == 0) {
            stunDurationLeft -= tpf;
            if(stunDurationLeft < 0) stunDurationLeft = 0;
            isCurrentlyStunned = stunDurationLeft > 0;
        }
    }

    //-----Knockback-----
    public boolean isKnockedBack() {
        return knockbackVelocity != 0;
    }

    public void applyKnockback(double strength, int direction) {
        if(direction != 1 && direction != -1) return;
        knockbackVelocity = strength * direction;
        entity.getComponent(PhysicsComponent.class).setVelocityY(-strength * 0.3);
    }

    private void calculateKnockback(double tpf) {
        if(knockbackVelocity == 0) return;
        entity.getComponent(PhysicsComponent.class).setVelocityX(knockbackVelocity);
        if(knockbackVelocity > 0) {
            knockbackVelocity = Math.max(0, knockbackVelocity - KNOCKBACK_FRICTION * tpf);
        } else {
            knockbackVelocity = Math.min(0, knockbackVelocity + KNOCKBACK_FRICTION * tpf);
        }
    }
}