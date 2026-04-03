package at.htl.flowstate.Components.Enemies;

import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.entity.component.Component;

public class EnemyStatsComponent extends Component {
    private double poisonDuration = 0;
    private final double POISON_DAMAGE = 5;

    private boolean isCurrentlyStunned = false;
    private double stunDurationLeft = 0;

    @Override
    public void onUpdate(double tpf) {
        checkPoison(tpf);
        checkIfStillAlive();
        calculateStun(tpf);
    }

    //-----Health Check-----
    private void checkIfStillAlive() {
        if(entity.getComponent(HealthDoubleComponent.class).isZero()) {
            entity.removeFromWorld();
        }
    }

    //-----Poison
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
        stunDurationLeft -= tpf;
        if(stunDurationLeft < 0) stunDurationLeft = 0;
        isCurrentlyStunned = stunDurationLeft > 0;
    }
}
