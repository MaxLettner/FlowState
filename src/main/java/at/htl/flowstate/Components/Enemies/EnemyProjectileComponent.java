package at.htl.flowstate.Components.Enemies;

import at.htl.flowstate.Components.Identifier.PlatformIdentifierComponent;
import at.htl.flowstate.Components.Player.PlayerStatsComponent;
import at.htl.flowstate.Game;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

import java.util.List;

public class EnemyProjectileComponent extends Component {
    private final Entity player;
    private final double damage;

    public EnemyProjectileComponent(double damage) {
        this.player = Game.getPlayer();
        this.damage = damage;
    }

    @Override
    public void onUpdate(double tpf) {
        checkHit();
    }

    private void checkHit() {
        if(entity.isColliding(player)) {
            player.getComponent(PlayerStatsComponent.class).takeDamage(damage);
            entity.removeFromWorld();
        }
        getPlatforms().forEach(e -> {
            if(entity.isColliding(e)) {
                entity.removeFromWorld();
            }
        });
    }

    private List<Entity> getPlatforms() {
        return FXGL.getGameWorld().getEntitiesFiltered(
                e -> e.getComponentOptional(PlatformIdentifierComponent.class).isPresent()
        );
    }
}
