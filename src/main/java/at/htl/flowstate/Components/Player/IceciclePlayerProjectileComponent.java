package at.htl.flowstate.Components.Player;

import at.htl.flowstate.Components.Enemies.EnemyStatsComponent;
import com.almasb.fxgl.entity.Entity;

public class IceciclePlayerProjectileComponent extends PlayerProjectileComponent{
    private static final double STUN_DURATION = 0.5;

    public IceciclePlayerProjectileComponent(double damage, int pierce) {
        super(damage, pierce);
    }

    @Override
    protected void hitEnemy(Entity e) {
        e.getComponent(EnemyStatsComponent.class).stun(STUN_DURATION);
    }
}
