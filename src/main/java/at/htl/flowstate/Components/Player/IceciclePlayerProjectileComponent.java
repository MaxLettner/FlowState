package at.htl.flowstate.Components.Player;

import at.htl.flowstate.Components.Enemies.EnemyStatsComponent;
import com.almasb.fxgl.entity.Entity;

public class IceciclePlayerProjectileComponent extends PlayerProjectileComponent{
    public IceciclePlayerProjectileComponent(double damage, int pierce) {
        super(damage, pierce);
    }

    @Override
    protected void hitEnemy(Entity e) {
        e.getComponent(EnemyStatsComponent.class).stun(0.5);
    }
}
