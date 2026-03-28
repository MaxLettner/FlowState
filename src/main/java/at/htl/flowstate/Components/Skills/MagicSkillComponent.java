package at.htl.flowstate.Components.Skills;

import at.htl.flowstate.Components.PlayerProjectileComponent;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.net.URL;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class MagicSkillComponent extends SkillComponent{
    @Override
    public void doDefault() { //magic projectile
        projectileBuilder(
                "MagicMissile.png",
                80,
                -30,
                -31.5,
                20,
                20,
                20,
                20,
                350,
                1,
                false
        );
    }

    @Override
    public void doSub1() { //Arcane

    }

    @Override
    public void doSub2() { //Elemental

    }

    @Override
    public void doSub3() { //Enchanting

    }

    //-----Arcane-----
    @Override
    public void doSub1Skill1() { //Magic Missile

    }

    @Override
    public void doSub1Skill2() { //Levitation

    }

    @Override
    public void doSub1Skill3() { //Mana Shield

    }

    //-----Elemental-----
    @Override
    public void doSub2Skill1() { //Fireball

    }

    @Override
    public void doSub2Skill2() { //Icecicle

    }

    @Override
    public void doSub2Skill3() { //Poison Darts

    }

    //-----Enchanting----
    @Override
    public void doSub3Skill1() { //Life Steal

    }

    @Override
    public void doSub3Skill2() { //Piercing

    }

    @Override
    public void doSub3Skill3() { //?

    }

    public void projectileBuilder(String textureName, double textureScale, double textureOffsetX, double textureOffsetY, double projWidth, double projHeight, double projDamage, int attackWeight, double projSpeed, int projPierce, boolean debug) {
        URL url = MeeleSkillComponent.class.getResource("/assets/textures/" + textureName);
        Texture texture = new Texture(new Image(url.toExternalForm(), textureScale, textureScale, false, true));
        texture.setRotate(180);
        texture.setTranslateX(textureOffsetX);
        texture.setTranslateY(textureOffsetY);

        if(debug) {
            entityBuilder()
                    .at(entity.getCenter())
                    .bbox(new HitBox(BoundingShape.box(projWidth, projHeight)))
                    .view(new Rectangle(projWidth, projHeight))
                    .view(texture)
                    .with(new ProjectileComponent(getInput().getVectorToMouse(entity.getCenter()), projSpeed))
                    .with(new PlayerProjectileComponent(projDamage, projPierce))
                    .with(new OffscreenCleanComponent())
                    .zIndex(-1)
                    .buildAndAttach();
        }else {
            entityBuilder()
                    .at(entity.getCenter())
                    .bbox(new HitBox(BoundingShape.box(projWidth, projHeight)))
                    .view(texture)
                    .with(new ProjectileComponent(getInput().getVectorToMouse(entity.getCenter()), projSpeed))
                    .with(new PlayerProjectileComponent(projDamage, projPierce))
                    .with(new OffscreenCleanComponent())
                    .zIndex(-1)
                    .buildAndAttach();
        }

    }
}
