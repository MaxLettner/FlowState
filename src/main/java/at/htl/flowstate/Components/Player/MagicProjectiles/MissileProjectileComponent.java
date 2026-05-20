package at.htl.flowstate.Components.Player.MagicProjectiles;

import at.htl.flowstate.Components.Identifier.EnemyIdentifierComponent;
import at.htl.flowstate.Components.Player.Helpers.HomingProjectileComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;

import java.util.List;

public class MissileProjectileComponent extends PlayerProjectileComponent{
    private Double timeUntilSeeking;

    public MissileProjectileComponent(double damage, int pierce) {
        super(damage, pierce);
        timeUntilSeeking = 0.5;
    }

    @Override
    public void onUpdate(double tpf) {
        super.onUpdate(tpf);
        if(timeUntilSeeking != null) {
            if(timeUntilSeeking > 0) {
                timeUntilSeeking -= tpf;
            }else {
                timeUntilSeeking = null;
                entity.getComponent(ProjectileComponent.class).pause();
                Entity target = getNearestEnemy();
                if(target != null) {
                    HomingProjectileComponent homingProjectileComponent = new HomingProjectileComponent(target, entity.getComponent(ProjectileComponent.class).getSpeed()*1.5, 200);
                    entity.addComponent(homingProjectileComponent);
                    homingProjectileComponent.start();
                }else {
                    entity.removeFromWorld();
                }
            }
        }
    }

    private Entity getNearestEnemy() {
        List<Entity> enemies = getEnemies();
        if(enemies.isEmpty()) return null;
        double smallest = Double.MAX_VALUE;
        Entity smallestEntity = null;

        for(Entity e : enemies) {
            double distance = entity.distance(e);
            if(distance < smallest) {
                smallest = distance;
                smallestEntity = e;
            }
        }

        return smallestEntity;
    }

    private List<Entity> getEnemies() {
        return FXGL.getGameWorld().getEntitiesFiltered(
                e -> e.getComponentOptional(EnemyIdentifierComponent.class).isPresent()
        );
    }
}
