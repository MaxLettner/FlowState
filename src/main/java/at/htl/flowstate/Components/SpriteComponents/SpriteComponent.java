package at.htl.flowstate.Components.SpriteComponents;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;

import java.net.URL;

public class SpriteComponent extends Component {
    private final Texture idleTextureRight;
    private final Texture walkTextureRight;
    private final Texture jumpTextureRight;
    private final Texture landTextureRight;
    private final Texture idleTextureLeft;
    private final Texture walkTextureLeft;
    private final Texture jumpTextureLeft;
    private final Texture landTextureLeft;

    public SpriteComponent(String idleTexturePath, String walkTexturePath, String jumpTexturePath, String landTexturePath, int scale) {
        URL url = SpriteComponent.class.getResource("/assets/textures/" + idleTexturePath);
        assert url != null;
        idleTextureRight = new Texture(new Image(url.toExternalForm(), scale, scale, false, true));
        idleTextureRight.setTranslateX(-28);
        idleTextureRight.setTranslateY(-17);
        idleTextureLeft = idleTextureRight.copy();
        idleTextureLeft.setScaleX(-1);
        idleTextureLeft.setTranslateX(-32);
        idleTextureLeft.setTranslateY(-17);


        url = SpriteComponent.class.getResource("/assets/textures/" + walkTexturePath);
        assert url != null;
        walkTextureRight = new Texture(new Image(url.toExternalForm(), scale, scale, false, true));
        walkTextureRight.setTranslateX(-41);
        walkTextureRight.setTranslateY(-17);
        walkTextureLeft = walkTextureRight.copy();
        walkTextureLeft.setScaleX(-1);
        walkTextureLeft.setTranslateX(-23);
        walkTextureLeft.setTranslateY(-17);

        url = SpriteComponent.class.getResource("/assets/textures/" + jumpTexturePath);
        assert url != null;
        jumpTextureRight = new Texture(new Image(url.toExternalForm(), scale, scale, false, true));
        jumpTextureRight.setTranslateX(-30);
        jumpTextureRight.setTranslateY(-12);
        jumpTextureLeft = jumpTextureRight.copy();
        jumpTextureLeft.setScaleX(-1);
        jumpTextureLeft.setTranslateX(-30);
        jumpTextureLeft.setTranslateY(-12);

        url = SpriteComponent.class.getResource("/assets/textures/" + landTexturePath);
        assert url != null;
        landTextureRight = new Texture(new Image(url.toExternalForm(), scale, scale, false, true));
        landTextureRight.setTranslateX(-25);
        landTextureRight.setTranslateY(-17);
        landTextureLeft = landTextureRight.copy();
        landTextureLeft.setScaleX(-1);
        landTextureLeft.setTranslateX(-25);
        landTextureLeft.setTranslateY(-17);
    }

    public void setIdleRight() {
        removeOthers();
        entity.getViewComponent().addChild(idleTextureRight);
    }

    public void setIdleLeft() {
        removeOthers();
        entity.getViewComponent().addChild(idleTextureLeft);
    }

    public void setWalkRight() {
        removeOthers();
        entity.getViewComponent().addChild(walkTextureRight);
    }

    public void setWalkLeft() {
        removeOthers();
        entity.getViewComponent().addChild(walkTextureLeft);
    }

    public void setJumpRight() {
        removeOthers();
        entity.getViewComponent().addChild(jumpTextureRight);
    }

    public void setJumpLeft() {
        removeOthers();
        entity.getViewComponent().addChild(jumpTextureLeft);
    }

    public void setLandRight() {
        removeOthers();
        entity.getViewComponent().addChild(landTextureRight);
    }

    public void setLandLeft() {
        removeOthers();
        entity.getViewComponent().addChild(landTextureLeft);
    }

    private void removeOthers() {
        entity.getViewComponent().removeChild(idleTextureRight);
        entity.getViewComponent().removeChild(walkTextureRight);
        entity.getViewComponent().removeChild(jumpTextureRight);
        entity.getViewComponent().removeChild(landTextureRight);
        entity.getViewComponent().removeChild(idleTextureLeft);
        entity.getViewComponent().removeChild(walkTextureLeft);
        entity.getViewComponent().removeChild(jumpTextureLeft);
        entity.getViewComponent().removeChild(landTextureLeft);
    }
}
