package at.htl.flowstate.Components.Player.Skills;

import at.htl.flowstate.Components.Player.*;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.Nullable;

import java.net.URL;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class RangedSkillComponent extends SkillComponent{
    private int attackWeight = 10;
    private static final int MAX_ATTACK_WEIGHT = 10;

    @Override
    public void doDefault() { //Basic Bow

    }

    @Override
    public void doSub1() { //Bow

    }

    @Override
    public void doSub2() { //Crossbow

    }

    @Override
    public void doSub3() { //Trident
        tridentBuilder(
                "BasicTrident.png",
                130,
                -80,
                -50,
                45,
                30,
                30,
                1300,
                new TridentComponent(25),
                null,
                false
        );
    }

    //-----Arcane-----
    @Override
    public void doSub1Skill1() { //Short Bow

    }

    @Override
    public void doSub1Skill2() { //Bone Bow

    }

    @Override
    public void doSub1Skill3() { //War Bow

    }

    //-----Elemental-----
    @Override
    public void doSub2Skill1() { //Dual Crossbow

    }

    @Override
    public void doSub2Skill2() { //Heavy Crossbow

    }

    @Override
    public void doSub2Skill3() { //Poison Crossbow

    }

    //-----Enchanting----
    @Override
    public void doSub3Skill1() { //Recall Trident
        tridentBuilder(
                "RecallTrident.png",
                250,
                -160,
                -110,
                45,
                30,
                30,
                1300,
                new RecallTridentComponent(25),
                new HomingProjectileComponent(entity, 600, 200),
                false
        );
    }

    @Override
    public void doSub3Skill2() { //Ice Trident
        tridentBuilder(
                "IceTrident.png",
                150,
                -120,
                -60,
                45,
                10,
                30,
                1300,
                new IceTridentComponent(25, 0.8, 200, 0.1, 0.2),
                null,
                false
        );
    }

    @Override
    public void doSub3Skill3() { //Heavy Trident
        tridentBuilder(
                "HeavyTrident.png",
                160,
                -100,
                -64,
                45,
                30,
                30,
                1300,
                new HeavyTridentComponent(45),
                null,
                false
        );
    }

    private void tridentBuilder(String textureName, double textureScale, double textureOffsetX, double textureOffsetY, double textureRotation, double projWidth, double projHeight, double projSpeed, TridentComponent tridentComponent, @Nullable Component optionalComponent, boolean debug) {
        if(this.takeAttackWeight(10)) {
            URL url = MeleeSkillComponent.class.getResource("/assets/textures/" + textureName);
            Texture texture = new Texture(new Image(url.toExternalForm(), textureScale, textureScale, false, true));
            texture.setRotate(textureRotation);
            texture.setTranslateX(textureOffsetX);
            texture.setTranslateY(textureOffsetY);

            if(optionalComponent == null) {
                if(debug) {
                    entityBuilder()
                            .at(entity.getCenter())
                            .bbox(new HitBox(BoundingShape.box(projWidth, projHeight)))
                            .view(new Rectangle(projWidth, projHeight))
                            .view(texture)
                            .zIndex(-1)
                            .with(new ProjectileComponent(getInput().getVectorToMouse(entity.getCenter()), projSpeed))
                            .with(new OffscreenCleanComponent())
                            .with(tridentComponent)
                            .buildAndAttach();
                }else {
                    entityBuilder()
                            .at(entity.getCenter())
                            .bbox(new HitBox(BoundingShape.box(projWidth, projHeight)))
                            .view(texture)
                            .zIndex(-1)
                            .with(new ProjectileComponent(getInput().getVectorToMouse(entity.getCenter()), projSpeed))
                            .with(new OffscreenCleanComponent())
                            .with(tridentComponent)
                            .buildAndAttach();
                }
            }else {
                if(debug) {
                    entityBuilder()
                            .at(entity.getCenter())
                            .bbox(new HitBox(BoundingShape.box(projWidth, projHeight)))
                            .view(new Rectangle(projWidth, projHeight))
                            .view(texture)
                            .zIndex(-1)
                            .with(new ProjectileComponent(getInput().getVectorToMouse(entity.getCenter()), projSpeed))
                            .with(optionalComponent)
                            .with(new OffscreenCleanComponent())
                            .with(tridentComponent)
                            .buildAndAttach();
                }else {
                    entityBuilder()
                            .at(entity.getCenter())
                            .bbox(new HitBox(BoundingShape.box(projWidth, projHeight)))
                            .view(texture)
                            .zIndex(-1)
                            .with(new ProjectileComponent(getInput().getVectorToMouse(entity.getCenter()), projSpeed))
                            .with(optionalComponent)
                            .with(new OffscreenCleanComponent())
                            .with(tridentComponent)
                            .buildAndAttach();
                }
            }
        }
    }

    //-----Attack Checkers-----
    public void addAttackWeight(int v) {
        attackWeight += v;
        if(attackWeight > MAX_ATTACK_WEIGHT) attackWeight = MAX_ATTACK_WEIGHT;
    }

    public boolean takeAttackWeight(int v) {
        if(attackWeight - v < 0) return false;
        attackWeight -= v;
        return true;
    }
}
