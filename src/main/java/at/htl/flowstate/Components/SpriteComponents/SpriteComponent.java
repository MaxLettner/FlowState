package at.htl.flowstate.Components.SpriteComponents;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;

import java.net.URL;

public class SpriteComponent extends Component {
    private final Texture idleTexture;
    private final Texture walkTexture;
    private final Texture jumpTexture;
    private final Texture landTexture;

    public SpriteComponent(String idleTexturePath, String walkTexturePath, String jumpTexturePath, String landTexturePath, int scale) {
        URL url = SpriteComponent.class.getResource("/assets/textures/" + idleTexturePath);
        assert url != null;
        idleTexture = new Texture(new Image(url.toExternalForm(), scale, scale, false, true));
        idleTexture.setTranslateX(-28);
        idleTexture.setTranslateY(-17);

        url = SpriteComponent.class.getResource("/assets/textures/" + walkTexturePath);
        assert url != null;
        walkTexture = new Texture(new Image(url.toExternalForm(), scale, scale, false, true));
        walkTexture.setTranslateX(-41);
        walkTexture.setTranslateY(-17);

        url = SpriteComponent.class.getResource("/assets/textures/" + jumpTexturePath);
        assert url != null;
        jumpTexture = new Texture(new Image(url.toExternalForm(), scale, scale, false, true));
        jumpTexture.setTranslateX(-30);
        jumpTexture.setTranslateY(-12);

        url = SpriteComponent.class.getResource("/assets/textures/" + landTexturePath);
        assert url != null;
        landTexture = new Texture(new Image(url.toExternalForm(), scale, scale, false, true));
        landTexture.setTranslateX(-25);
        landTexture.setTranslateY(-17);
    }

    public void setIdle() {
        removeOthers();
        entity.getViewComponent().addChild(idleTexture);
    }

    public void setWalk() {
        removeOthers();
        entity.getViewComponent().addChild(walkTexture);
    }

    public void setJump() {
        removeOthers();
        entity.getViewComponent().addChild(jumpTexture);
    }

    public void setLand() {
        removeOthers();
        entity.getViewComponent().addChild(landTexture);
    }

    private void removeOthers() {
        entity.getViewComponent().removeChild(idleTexture);
        entity.getViewComponent().removeChild(walkTexture);
        entity.getViewComponent().removeChild(jumpTexture);
        entity.getViewComponent().removeChild(landTexture);
    }
}
