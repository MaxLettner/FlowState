package at.htl.flowstate.Components.Enemies;

import com.almasb.fxgl.entity.component.Component;

public class EnemyStunComponent extends Component {
    private boolean isCurrentlyStunned = false;
    private double stunDurationLeft = 0;

    @Override
    public void onUpdate(double tpf) {
        calculateStun(tpf);
    }

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
