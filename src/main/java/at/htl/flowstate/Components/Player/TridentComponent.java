package at.htl.flowstate.Components.Player;

import at.htl.flowstate.Components.Identifier.EnemyIdentifierComponent;
import at.htl.flowstate.Components.Identifier.PlatformIdentifierComponent;
import at.htl.flowstate.Components.Player.Skills.RangedSkillComponent;
import at.htl.flowstate.Game;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

import java.util.ArrayList;
import java.util.List;

public class TridentComponent extends Component {
    private final Entity player;
    private final double damage;

    private final List<Entity> alreadyHit;

    protected boolean isOnGround;

    public TridentComponent(double damage) {
        this.damage = damage;
        player = Game.getPlayer();
        isOnGround = false;
        alreadyHit = new ArrayList<>();
    }

    @Override
    public void onRemoved() {
        player.getComponent(RangedSkillComponent.class).addAttackWeight(10);
    }

    @Override
    public void onUpdate(double tpf) {
        checkHit();
    }

    private void checkHit() {
        if(isOnGround) {
            if(entity.isColliding(player)) entity.removeFromWorld();
        }else {
            getEnemies().forEach(e -> {
                if(e.isColliding(entity) && ! alreadyHit.contains(e)) {
                    alreadyHit.add(e);
                    hitEnemy(e);
                }
            });

            getPlatforms().forEach(e -> {
                if(entity.isColliding(e)) {
                    hitGround();
                }
            });
        }
    }

    protected void hitGround() {
        entity.getComponent(ProjectileComponent.class).pause();
        isOnGround = true;
    }

    protected void hitEnemy(Entity e) {
        e.getComponent(HealthDoubleComponent.class).damage(damage);
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
