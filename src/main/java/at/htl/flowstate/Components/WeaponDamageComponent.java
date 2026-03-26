package at.htl.flowstate.Components;

import at.htl.flowstate.Components.Identifier.EnemyIdentifierComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

import java.util.ArrayList;
import java.util.List;

public class WeaponDamageComponent extends Component {
    double damage;
    List<Entity> alreadyHit = new ArrayList<>();

    public WeaponDamageComponent(double damage) {
        this.damage = damage;
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
        System.out.println(e.getComponent(HealthDoubleComponent.class).getValue());
        e.getComponent(HealthDoubleComponent.class).damage(damage);
        if(e.getComponent(HealthDoubleComponent.class).isZero()) {
            e.removeFromWorld();
        }
        //TODO: implement knockback code
    }

    private List<Entity> getEnemies() {
        return FXGL.getGameWorld().getEntitiesFiltered(
                e -> e.getComponentOptional(EnemyIdentifierComponent.class).isPresent()
        );
    }
}
