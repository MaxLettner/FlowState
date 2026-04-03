package at.htl.flowstate.Components.Player.Skills;


import at.htl.flowstate.Components.Player.AttackAnimations.SwordAnimationComponent;
import at.htl.flowstate.Components.Player.PlayerStatsComponent;
import at.htl.flowstate.Components.Player.WeaponDamageComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import java.net.URL;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class MeeleSkillComponent extends SkillComponent {
    public MeeleSkillComponent() {}

    //-----Start Weapon-----
    public void doStart () {
        weaponBuilder(
                "RustySword.png",
                80,
                -35,
                3,
                10,
                80,
                1,
                10,
                1,
                0,
                1,
                false
        );
    }

    //-----Default Meele Skill-----
    @Override
    public void doDefault () {
        if(entity.getComponent(PlayerStatsComponent.class).takeAttackWeight(10)) {
            entityBuilder()
                    .viewWithBBox(new Rectangle(10, 50, Color.GRAY))
                    .with(new WeaponDamageComponent(5, 0, 0))
                    .with(new SwordAnimationComponent(entity, 10, 1))
                    .zIndex(-1)
                    .buildAndAttach();
        }
    }

    //-----Skills of the Subtrees-----
    @Override
    public void doSub1() { // basic sword
        if(entity.getComponent(PlayerStatsComponent.class).takeAttackWeight(10)) {
            entityBuilder()
                    .viewWithBBox(new Rectangle(10, 70, Color.GRAY))
                    .with(new WeaponDamageComponent(10, 0, 0))
                    .with(new SwordAnimationComponent(entity, 10, 1))
                    .zIndex(-1)
                    .buildAndAttach();
        }
    }

    @Override
    public void doSub2() { // basic fisticuff

    }

    @Override
    public void doSub3() { // basic hammer
        weaponBuilder(
                "BasicHammer.png",
                100,
                -40,
                0,
                20,
                95,
                15,
                10,
                1.5,
                0.75,
                1,
                false
        );
    }

    //-----Skills of the Swords Tree-----
    @Override
    public void doSub1Skill1() { //shortsword
        weaponBuilder(
                "ShortSword.png",
                80,
                -35,
                10,
                10,
                85,
                5,
                10,
                0.5,
                0,
                3,
                false
        );

    }

    @Override
    public void doSub1Skill2() { //dual wielding
        weaponBuilder(
                "DualWieldingSword.png",
                100,
                -45,
                10,
                10,
                100,
                5,
                5,
                1.2,
                0,
                2,
                false
        );
    }

    @Override
    public void doSub1Skill3() { //zweihander
        weaponBuilder(
                "Zweihander.png",
                140,
                -60,
                0,
                20,
                160,
                35,
                10,
                2,
                0,
                2,
                false
        );

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
    public void doSub3Skill1() { //Big Hammer
        weaponBuilder(
                "BigHammer.png",
                120,
                -50,
                0,
                20,
                120,
                15,
                10,
                2,
                1.5,
                2,
                false
        );
    }

    @Override
    public void doSub3Skill2() { //Morningstar
        weaponBuilder(
                "BigMace.png",
                140,
                -60,
                2,
                20,
                140,
                30,
                10,
                1.5,
                0,
                4,
                false
        );
    }

    @Override
    public void doSub3Skill3() { //Spring Hammer
        weaponBuilder(
                "SpringHammer.png",
                100,
                -45,
                5,
                15,
                100,
                1,
                10,
                1,
                0,
                0,
                false
        );
    }

    private void weaponBuilder(String textureName, double textureScale, double textureOffsetX, double textureOffsetY, double weaponWidth, double weaponHeight, double weaponDamage, int attackWeight, double duration, double stunDuration, double critChance, boolean debug) {
        if(entity.getComponent(PlayerStatsComponent.class).takeAttackWeight(attackWeight)) {
            URL url = MeeleSkillComponent.class.getResource("/assets/textures/" + textureName);
            assert url != null;
            Texture texture = new Texture(new Image(url.toExternalForm(), textureScale, textureScale, false, true));
            texture.setRotate(135);
            texture.setTranslateX(textureOffsetX);
            texture.setTranslateY(textureOffsetY);

            if (debug) {
                entityBuilder()
                        .bbox(new HitBox(BoundingShape.box(weaponWidth, weaponHeight)))
                        .view(new Rectangle(weaponWidth, weaponHeight))
                        .view(texture)
                        .with(new WeaponDamageComponent(weaponDamage, stunDuration, critChance))
                        .with(new SwordAnimationComponent(entity, attackWeight, duration))
                        .zIndex(-1)
                        .buildAndAttach();
            } else {
                entityBuilder()
                        .bbox(new HitBox(BoundingShape.box(weaponWidth, weaponHeight)))
                        .view(texture)
                        .with(new WeaponDamageComponent(weaponDamage, stunDuration, critChance))
                        .with(new SwordAnimationComponent(entity, attackWeight, duration))
                        .zIndex(-1)
                        .buildAndAttach();
            }
        }


    }
}
