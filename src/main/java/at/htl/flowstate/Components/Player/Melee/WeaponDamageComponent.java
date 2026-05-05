package at.htl.flowstate.Components.Player.Melee;

import at.htl.flowstate.Components.Enemies.EnemyStatsComponent;
import at.htl.flowstate.Components.Identifier.EnemyIdentifierComponent;
import at.htl.flowstate.Components.Player.PlayerStatsComponent;
import at.htl.flowstate.Components.Player.Skills.IconType;
import at.htl.flowstate.Components.Player.Skills.MagicSkillComponent;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

import java.util.ArrayList;
import java.util.List;

public class WeaponDamageComponent extends Component {
    private final double damage;
    private final double stunDuration;
    private final double critChance;
    private final Entity player;
    private static final double CRIT_MULT = 5;
    List<Entity> alreadyHit = new ArrayList<>();

    public WeaponDamageComponent(Entity player, double damage, double stunDuration, double critChance) {
        this.player = player;
        this.damage = damage;
        this.stunDuration = stunDuration;
        this.critChance = critChance;
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
        boolean isCrit = FXGLMath.random(0,100) <= critChance;
        if(player.getComponent(MagicSkillComponent.class).getCurrentActiveEnchantment() == IconType.Crit) isCrit = FXGLMath.random(0,100) <= critChance * MagicSkillComponent.ENCHANTMENT_CRITCHANCE_MULT;

        if(isCrit) {
            e.getComponent(HealthDoubleComponent.class).damage(damage * CRIT_MULT);
            if(player.getComponent(MagicSkillComponent.class).getCurrentActiveEnchantment() == IconType.Lifesteal) {
                player.getComponent(PlayerStatsComponent.class).heal(damage * CRIT_MULT * MagicSkillComponent.ENCHANTMENT_LIFESTEAL_RATE);
            }
        }else {
            e.getComponent(HealthDoubleComponent.class).damage(damage);
            if(player.getComponent(MagicSkillComponent.class).getCurrentActiveEnchantment() == IconType.Lifesteal) {
                player.getComponent(PlayerStatsComponent.class).heal(damage * MagicSkillComponent.ENCHANTMENT_LIFESTEAL_RATE);
            }
        }
        if(stunDuration > 0) e.getComponent(EnemyStatsComponent.class).stun(stunDuration);
        //TODO: implement knockback code
    }

    private List<Entity> getEnemies() {
        return FXGL.getGameWorld().getEntitiesFiltered(
                e -> e.getComponentOptional(EnemyIdentifierComponent.class).isPresent()
        );
    }
}
