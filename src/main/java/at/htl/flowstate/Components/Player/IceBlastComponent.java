package at.htl.flowstate.Components.Player;

import at.htl.flowstate.Components.Enemies.EnemyStatsComponent;
import com.almasb.fxgl.entity.Entity;

public class IceBlastComponent extends BlastComponent {
    private final double freezeTime;

    public IceBlastComponent(double freezeTime, double blastRadius, double duration, double fadeoutTime) {
        this.freezeTime = freezeTime;
        super(blastRadius, duration, fadeoutTime);
    }

    @Override
    protected void hitEnemy(Entity e) {
        e.getComponent(EnemyStatsComponent.class).stun(freezeTime);
    }
}
