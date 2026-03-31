package at.htl.flowstate.Components.Player;

import at.htl.flowstate.Components.Enemies.EnemyComponent;
import at.htl.flowstate.Components.Enemies.EnemyStunComponent;
import at.htl.flowstate.Components.Identifier.EnemyIdentifierComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

import java.util.ArrayList;
import java.util.List;

public class WeaponDamageComponent extends Component {
    private final double damage;
    private final double stunDuration;
    List<Entity> alreadyHit = new ArrayList<>();

    public WeaponDamageComponent(double damage, double stunDuration) {
        this.damage = damage;
        this.stunDuration = stunDuration;
    }

    @Override
    public void onUpdate(double tpf) {
        getEnemies().forEach(e -> {
            if(e.isColliding(entity) && !alreadyHit.contains(e)) {
                hit(e);
            }
        });
    }

    private void hit(Entity e) {
        alreadyHit.add(e);
        e.getComponent(HealthDoubleComponent.class).damage(damage);
        if(stunDuration > 0) e.getComponent(EnemyStunComponent.class).stun(stunDuration);
        //TODO: implement knockback code
    }

    private List<Entity> getEnemies() {
        return FXGL.getGameWorld().getEntitiesFiltered(
                e -> e.getComponentOptional(EnemyIdentifierComponent.class).isPresent()
        );
    }
}
