package at.htl.flowstate.Components.Player;

import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.entity.Entity;

public class ExplosionBlastComponent extends PlayerBlastComponent{
    private final double damage;

    public ExplosionBlastComponent(double damage, double blastRadius, double duration, double fadeoutTime) {
        this.damage = damage;
        super(blastRadius, duration, fadeoutTime);
    }

    @Override
    protected void hitEnemy(Entity e) {
        e.getComponent(HealthDoubleComponent.class).damage(damage);
    }
}
