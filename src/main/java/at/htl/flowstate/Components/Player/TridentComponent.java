package at.htl.flowstate.Components.Player;

import at.htl.flowstate.Components.Identifier.EnemyIdentifierComponent;
import at.htl.flowstate.Components.Identifier.PlatformIdentifierComponent;
import at.htl.flowstate.Game;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.sun.security.jgss.GSSUtil;

import java.util.List;

public class TridentComponent extends Component {
    private static final double DAMAGE = 25;
    private final Entity player;

    private boolean isOnGround;

    public TridentComponent() {
        player = Game.getPlayer();
        isOnGround = false;
    }

    @Override
    public void onRemoved() {
        player.getComponent(PlayerStatsComponent.class).addAttackWeight(10);
    }

    @Override
    public void onUpdate(double tpf) {
        checkHit();
    }

    private void checkHit() {
        getPlatforms().forEach(e -> {
            if(entity.isColliding(e)) {
                hitGround();
            }
        });

        if(isOnGround) {
            if(entity.isColliding(player)) entity.removeFromWorld();
        }else {
            getEnemies().forEach(e -> {
                hitEnemy(e);
            });
        }
    }

    protected void hitGround() {
        entity.getComponent(ProjectileComponent.class).pause();
        isOnGround = true;
    }

    protected void hitEnemy(Entity e) {
        e.getComponent(HealthDoubleComponent.class).damage(DAMAGE);
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
