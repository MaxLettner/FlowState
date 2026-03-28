package at.htl.flowstate.Components.Player;

import at.htl.flowstate.Components.Enemies.EnemyStunComponent;
import com.almasb.fxgl.entity.Entity;

public class IceciclePlayerProjectileComponent extends PlayerProjectileComponent{
    public IceciclePlayerProjectileComponent(double damage, int pierce) {
        super(damage, pierce);
    }

    @Override
    protected void hitEnemy(Entity e) {
        e.getComponent(EnemyStunComponent.class).stun(0.5);
    }
}
