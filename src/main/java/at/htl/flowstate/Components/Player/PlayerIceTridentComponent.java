package at.htl.flowstate.Components.Player;

import at.htl.flowstate.Components.Enemies.EnemyStatsComponent;
import com.almasb.fxgl.entity.Entity;

public class PlayerIceTridentComponent extends PlayerTridentComponent{
    private final double freezeDuration;
    public PlayerIceTridentComponent(double damage, double freezeDuration) {
        this.freezeDuration = freezeDuration;
        super(damage);
    }

    @Override
    protected void hitGround() {
        super.hitGround();
    }

    @Override
    protected void hitEnemy(Entity e) {
        e.getComponent(EnemyStatsComponent.class).stun(freezeDuration);
        super.hitEnemy(e);
    }
}
