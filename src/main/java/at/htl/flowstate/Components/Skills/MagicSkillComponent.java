package at.htl.flowstate.Components.Skills;

import at.htl.flowstate.Components.FireballPlayerProjectileComponent;
import at.htl.flowstate.Components.IceciclePlayerProjectileComponent;
import at.htl.flowstate.Components.PlayerProjectileComponent;
import at.htl.flowstate.Components.PlayerStatsComponent;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

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
                180,
                20,
                20,
                20,
                600,
                new PlayerProjectileComponent(20, 1),
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
        projectileBuilder(
                "FireBall.png",
                130,
                -50,
                -52,
                0,
                30,
                30,
                30,
                500,
                new FireballPlayerProjectileComponent(30, 20, 200, 0.5),
                false
        );
    }

    @Override
    public void doSub2Skill2() { //Icecicle
        projectileBuilder(
                "Icecicle.png",
                80,
                -15,
                -31,
                0,
                50,
                15,
                15,
                700,
                new IceciclePlayerProjectileComponent(20, 5),
                false
        );
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

    public void projectileBuilder(String textureName, double textureScale, double textureOffsetX, double textureOffsetY, double textureRotation, double projWidth, double projHeight, double manaCost, double projSpeed, @NotNull PlayerProjectileComponent specificComponent, boolean debug) {
        if(entity.getComponent(PlayerStatsComponent.class).takeMana(manaCost)) {
            URL url = MeeleSkillComponent.class.getResource("/assets/textures/" + textureName);
            Texture texture = new Texture(new Image(url.toExternalForm(), textureScale, textureScale, false, true));
            texture.setRotate(textureRotation);
            texture.setTranslateX(textureOffsetX);
            texture.setTranslateY(textureOffsetY);

            if(debug) {
                entityBuilder()
                        .at(entity.getCenter())
                        .bbox(new HitBox(BoundingShape.box(projWidth, projHeight)))
                        .view(new Rectangle(projWidth, projHeight))
                        .view(texture)
                        .with(new ProjectileComponent(getInput().getVectorToMouse(entity.getCenter()), projSpeed))
                        .with(specificComponent)
                        .with(new OffscreenCleanComponent())
                        .zIndex(-1)
                        .buildAndAttach();
            }else {
                entityBuilder()
                        .at(entity.getCenter())
                        .bbox(new HitBox(BoundingShape.box(projWidth, projHeight)))
                        .view(texture)
                        .with(new ProjectileComponent(getInput().getVectorToMouse(entity.getCenter()), projSpeed))
                        .with(specificComponent)
                        .with(new OffscreenCleanComponent())
                        .zIndex(-1)
                        .buildAndAttach();
            }

        }

    }
}
