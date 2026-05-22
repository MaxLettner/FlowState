package at.htl.flowstate.Components.Chests;

import at.htl.flowstate.Components.Player.PlayerStatsComponent;
import at.htl.flowstate.Components.Player.Skills.MeleeSkillComponent;
import at.htl.flowstate.Game;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;

import java.net.URL;

public class ChestComponent extends Component {
    private boolean isClosed;
    private final Entity player;

    private final Texture closedTexture;
    private final Texture openTexture;

    public ChestComponent() {
        isClosed = true;
        player = Game.getPlayer();

        URL url = MeleeSkillComponent.class.getResource("/assets/textures/chest.png");
        assert url != null;
        closedTexture = new Texture(new Image(url.toExternalForm(), 90, 90, false, true));
        closedTexture.setTranslateY(-40);
        closedTexture.setTranslateX(-5);

        url = MeleeSkillComponent.class.getResource("/assets/textures/chest_opening.png");
        assert url != null;
        openTexture = new Texture(new Image(url.toExternalForm(), 90, 90, false, true));
        openTexture.setTranslateY(-40);
        openTexture.setTranslateX(-5);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(closedTexture);
    }

    @Override
    public void onUpdate(double tpf) {
        if(entity.isColliding(player) && isClosed) {
            player.getComponent(PlayerStatsComponent.class).addSkillPoint();
            isClosed = false;
            entity.getViewComponent().removeChild(closedTexture);
            entity.getViewComponent().addChild(openTexture);
        }
    }
}
