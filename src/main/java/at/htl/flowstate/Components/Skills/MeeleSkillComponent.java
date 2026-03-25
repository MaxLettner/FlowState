package at.htl.flowstate.Components.Skills;


import at.htl.flowstate.Components.PlayerComponent;
import at.htl.flowstate.Components.AttackAnimations.SwordAnimationComponent;
import at.htl.flowstate.Components.WeaponDamageComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.Nullable;


import java.net.URL;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.text;

public class MeeleSkillComponent extends SkillComponent {
    public MeeleSkillComponent() {}

    //-----Default Meele Skill-----
    @Override
    public void doDefault () {
        if(entity.getComponent(PlayerComponent.class).takeAttackStrength(10)) {
            entityBuilder()
                    .viewWithBBox(new Rectangle(10, 50, Color.GRAY))
                    .with(new WeaponDamageComponent(5))
                    .with(new SwordAnimationComponent(entity, 10, 1))
                    .zIndex(-1)
                    .buildAndAttach();
        }
    }

    //-----Skills of the Subtrees-----
    @Override
    public void doSub1() { // basic sword
        if(entity.getComponent(PlayerComponent.class).takeAttackStrength(10)) {
            entityBuilder()
                    .viewWithBBox(new Rectangle(10, 70, Color.GRAY))
                    .with(new WeaponDamageComponent(10))
                    .with(new SwordAnimationComponent(entity, 10, 1))
                    .zIndex(-1)
                    .buildAndAttach();
        }
    }

    @Override
    public void doSub2() { // basic fisticuff

    }

    @Override
    public void doSub3() { // basic blunt

    }

    //-----Skills of the Swords Tree-----
    @Override
    public void doSub1Skill1() { //shortsword
        if(entity.getComponent(PlayerComponent.class).takeAttackStrength(10)) {
            weaponBuilder(
                    "shortsword.png",
                    80,
                    -35,
                    10,
                    10,
                    85,
                    5,
                    10,
                    0.5,
                    false
            );
        }
    }

    @Override
    public void doSub1Skill2() { //dual wielding
        if(entity.getComponent(PlayerComponent.class).takeAttackStrength(5)) {
            entityBuilder()
                    .viewWithBBox(new Rectangle(10, 60, Color.GRAY))
                    .with(new WeaponDamageComponent(5))
                    .with(new SwordAnimationComponent(entity, 5, 0.8))
                    .zIndex(-1)
                    .buildAndAttach();
        }
    }

    @Override
    public void doSub1Skill3() { //zweihander
        if(entity.getComponent(PlayerComponent.class).takeAttackStrength(10)) {
            entityBuilder()
                    .viewWithBBox(new Rectangle(10, 90, Color.GRAY))
                    .with(new WeaponDamageComponent(20))
                    .with(new SwordAnimationComponent(entity, 10, 2))
                    .zIndex(-1)
                    .buildAndAttach();
        }

    }

    //-----Skills of the Fisticuffs Tree-----
    @Override
    public void doSub2Skill1() {

    }

    @Override
    public void doSub2Skill2() {

    }

    @Override
    public void doSub2Skill3() {

    }

    //-----Skills of the Blunt Tree-----
    @Override
    public void doSub3Skill1() {

    }

    @Override
    public void doSub3Skill2() {

    }

    @Override
    public void doSub3Skill3() {

    }

    private void weaponBuilder(String textureName, double textureScale, double textureOffsetX, double textureOffsetY, double weaponWidth, double weaponHeight, double weaponDamage, int attackWeight, double duration, Boolean debug) {
        URL url = MeeleSkillComponent.class.getResource("/assets/textures/" + textureName);
        Texture texture = new Texture(new Image(url.toExternalForm(), textureScale, textureScale, false, true));
        texture.setRotate(135);
        texture.setTranslateX(textureOffsetX);
        texture.setTranslateY(textureOffsetY);

        if(debug) {
            entityBuilder()
                    .bbox(new HitBox(BoundingShape.box(weaponWidth, weaponHeight)))
                    .view(new Rectangle(weaponWidth,weaponHeight))
                    .view(texture)
                    .with(new WeaponDamageComponent(weaponDamage))
                    .with(new SwordAnimationComponent(entity, attackWeight, duration))
                    .zIndex(-1)
                    .buildAndAttach();
        }else {
            entityBuilder()
                    .bbox(new HitBox(BoundingShape.box(weaponWidth, weaponHeight)))
                    .view(texture)
                    .with(new WeaponDamageComponent(weaponDamage))
                    .with(new SwordAnimationComponent(entity, attackWeight, duration))
                    .zIndex(-1)
                    .buildAndAttach();
        }



    }
}
