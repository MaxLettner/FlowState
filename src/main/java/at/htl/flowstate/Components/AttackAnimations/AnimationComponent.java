package at.htl.flowstate.Components.AttackAnimations;

import at.htl.flowstate.Components.PlayerComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

public abstract class AnimationComponent extends Component {
    private final int attackWeight;
    protected Entity player;

    public AnimationComponent(Entity player, int attackWeight) {
        this.attackWeight = attackWeight;
        this.player = player;
    }

    protected void endAnimation() { //needs to be called from the childs to properly exit the animation and reset the attack cooldown
        entity.removeFromWorld();
        player.getComponent(PlayerComponent.class).addAttackStrength(attackWeight);
    }

    protected int getCurrentWatchDirection() {
        return player.getComponent(PlayerComponent.class).getCurrentWatchDirection() * -1;
    }

    protected abstract void animate(double tpf);
}
