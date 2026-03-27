package at.htl.flowstate.Components;

import at.htl.flowstate.Components.Identifier.EnemyIdentifierComponent;
import at.htl.flowstate.Components.Identifier.PlatformIdentifierComponent;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

import java.util.ArrayList;
import java.util.List;

public class PlayerProjectileComponent extends Component {
    private final double damage;
    private List<Entity> alreadyHit = new ArrayList<>();
    private int pierce;

    public PlayerProjectileComponent(double damage, int pierce) {
        this.damage = damage;
        this.pierce = pierce;
    }

    @Override
    public void onUpdate(double tpf) {
        checkHit();
    }

    private void checkHit() {
        getEnemies().forEach(e -> {
            if(entity.isColliding(e) && !alreadyHit.contains(e)) {
                alreadyHit.add(e);
                e.getComponent(HealthDoubleComponent.class).damage(damage);
                pierce--;
                if(pierce == 0) entity.removeFromWorld();
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
