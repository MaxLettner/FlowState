package at.htl.flowstate.Components.Enemies;

import at.htl.flowstate.Components.Player.PlayerStatsComponent;
import com.almasb.fxgl.entity.Entity;

public class MeleeEnemyBehaviourComponent extends EnemyBehaviourComponent {
    public void attack() {
        if(player.isColliding(entity)) {
            player.getComponent(PlayerStatsComponent.class).takeDamage(DAMAGE);
        }
    }
}