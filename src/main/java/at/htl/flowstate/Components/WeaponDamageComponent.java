package at.htl.flowstate.Components;

import at.htl.flowstate.Components.Identifier.EnemyIdentifierComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

import java.util.List;

public class WeaponDamageComponent extends Component {
    double damage;

    public WeaponDamageComponent(double damage) {
        this.damage = damage;
    }

    @Override
    public void onUpdate(double tpf) {
        getEnemies().forEach(e -> {
            if(e.isColliding(entity)) {
                e.getComponent(HealthDoubleComponent.class).damage(damage);
                if(e.getComponent(HealthDoubleComponent.class).isZero()) {
                    e.removeFromWorld();
                }
            }
        });
    }

    private List<Entity> getEnemies() {
        return FXGL.getGameWorld().getEntitiesFiltered(
                e -> e.getComponentOptional(EnemyIdentifierComponent.class).isPresent()
        );
    }
}
