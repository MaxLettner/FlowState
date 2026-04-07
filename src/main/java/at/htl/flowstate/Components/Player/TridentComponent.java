package at.htl.flowstate.Components.Player;

import at.htl.flowstate.Components.Identifier.PlatformIdentifierComponent;
import at.htl.flowstate.Game;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.sun.security.jgss.GSSUtil;

import java.util.List;

public class TridentComponent extends Component {
    private final Entity player;

    public TridentComponent() {
        player = Game.getPlayer();
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
                entity.getComponent(ProjectileComponent.class).pause();
                //entity.removeFromWorld();
            }
        });
    }

    private List<Entity> getPlatforms() {
        return FXGL.getGameWorld().getEntitiesFiltered(
                e -> e.getComponentOptional(PlatformIdentifierComponent.class).isPresent()
        );
    }


}
