package at.htl.flowstate.Components.Player.Blasts;

import at.htl.flowstate.Components.Enemies.EnemyStatsComponent;
import at.htl.flowstate.Game;
import com.almasb.fxgl.entity.Entity;

public class KnockbackBlastComponent extends BlastComponent{
    private final double knockbackStrength;
    private final Entity trident;

    public KnockbackBlastComponent(Entity trident, double knockbackStrength, double blastRadius) {
        this.knockbackStrength = knockbackStrength;
        this.trident = trident;
        super(blastRadius, 0.1, 0.1);
    }

    @Override
    protected void hitEnemy(Entity e) {
        if(e.getX() > trident.getX()) {
            e.getComponent(EnemyStatsComponent.class).applyKnockback(knockbackStrength, 1);
        }else {
            e.getComponent(EnemyStatsComponent.class).applyKnockback(knockbackStrength, -1);
        }
    }
}
