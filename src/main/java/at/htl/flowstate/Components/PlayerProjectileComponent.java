package at.htl.flowstate.Components;

import at.htl.flowstate.Components.Identifier.EnemyIdentifierComponent;
import at.htl.flowstate.Components.Identifier.PlatformIdentifierComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

import java.util.List;

public class PlayerProjectileComponent extends Component {
    private final double damage;

    public PlayerProjectileComponent(double damage) {
        this.damage = damage;
    }

    @Override
    public void onUpdate(double tpf) {
        checkHit();
    }

    private void checkHit() {
        getEnemies().forEach(e -> {
            if(entity.isColliding(e)) {
                e.getComponent(HealthDoubleComponent.class).damage(damage);
                entity.removeFromWorld();
            }
        });
        getPlatforms().forEach(e -> {
            if(entity.isColliding(e)) {
                entity.removeFromWorld();
            }
        });
    }

    private List<Entity> getEnemies() {
        return FXGL.getGameWorld().getEntitiesFiltered(
                e -> e.getComponentOptional(EnemyIdentifierComponent.class).isPresent()
        );
    }

    private List<Entity> getPlatforms() {
        return FXGL.getGameWorld().getEntitiesFiltered(
                e -> e.getComponentOptional(PlatformIdentifierComponent.class).isPresent()
        );
    }
}
