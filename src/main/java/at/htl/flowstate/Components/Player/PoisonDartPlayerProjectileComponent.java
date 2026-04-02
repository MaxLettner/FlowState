package at.htl.flowstate.Components.Player;

import at.htl.flowstate.Components.Enemies.EnemyStatsComponent;
import com.almasb.fxgl.entity.Entity;

public class PoisonDartPlayerProjectileComponent extends PlayerProjectileComponent{
    private static final double POISON_DURATION = 5;

    public PoisonDartPlayerProjectileComponent(double damage) {
        super(damage, 1);
    }

    @Override
    protected void hitEnemy(Entity e) {
        e.getComponent(EnemyStatsComponent.class).poison(POISON_DURATION);
    }
}
