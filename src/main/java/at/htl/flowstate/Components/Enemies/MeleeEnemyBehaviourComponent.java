package at.htl.flowstate.Components.Enemies;

import at.htl.flowstate.Components.Player.PlayerStatsComponent;
import com.almasb.fxgl.entity.Entity;

public class MeleeEnemyBehaviourComponent extends EnemyBehaviourComponent {

    public MeleeEnemyBehaviourComponent(Entity player) {
        super(player);
    }

    public void attack() {
        // manual overlap since physics collision between enemy and player is disabled
        double ex = entity.getX(), ey = entity.getY();
        double px = player.getX(), py = player.getY();

        boolean overlapX = ex < px + ENEMY_WIDTH  && ex + ENEMY_WIDTH  > px;
        boolean overlapY = ey < py + ENEMY_HEIGHT && ey + ENEMY_HEIGHT > py;

        if (overlapX && overlapY) {
            player.getComponent(PlayerStatsComponent.class).takeDamage(DAMAGE);
        }
    }
}