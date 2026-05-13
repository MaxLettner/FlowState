package at.htl.flowstate.Components.Chests;

import at.htl.flowstate.Components.Player.PlayerStatsComponent;
import at.htl.flowstate.Game;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

public class ChestComponent extends Component {
    private boolean isClosed;
    private final Entity player;

    public ChestComponent() {
        isClosed = true;
        player = Game.getPlayer();
    }

    @Override
    public void onUpdate(double tpf) {
        if(entity.isColliding(player) && isClosed) {
            player.getComponent(PlayerStatsComponent.class).addSkillPoint();
            isClosed = false;
            //TODO: texture
        }
    }
}
