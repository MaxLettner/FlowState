package at.htl.flowstate.Components.Player.Melee.AttackAnimations;

import at.htl.flowstate.Components.Player.PlayerMovementComponent;
import at.htl.flowstate.Components.Player.Skills.MeleeSkillComponent;
import at.htl.flowstate.Game;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

public abstract class AnimationComponent extends Component {
    private final int attackWeight;
    protected Entity player;

    public AnimationComponent(int attackWeight) {
        this.attackWeight = attackWeight;
        this.player = Game.getPlayer();
    }

    protected void endAnimation() { //needs to be called from the childs to properly exit the animation and reset the attack cooldown
        entity.removeFromWorld();
        player.getComponent(MeleeSkillComponent.class).addAttackWeight(attackWeight);
    }

    protected int getCurrentWatchDirection() {
        return player.getComponent(PlayerMovementComponent.class).getCurrentWatchDirection() * -1;
    }

    protected abstract void animate(double tpf);
}
