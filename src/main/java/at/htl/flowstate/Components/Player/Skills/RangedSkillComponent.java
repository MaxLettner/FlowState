package at.htl.flowstate.Components.Player.Skills;

import at.htl.flowstate.Components.Player.IceTridentComponent;
import at.htl.flowstate.Components.Player.TridentComponent;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
                100,
                0,
                0,
                0,
                50,
                20,
                700,
                new TridentComponent(25)
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
    public void doSub3Skill1() { //Heavy Trident

    }

    @Override
    public void doSub3Skill2() { //Ice Trident
        tridentBuilder(
                "IceTrident.png",
                100,
                0,
                0,
                0,
                50,
                20,
                700,
                new IceTridentComponent(25, 0.8, 200, 0.1, 0.2)
        );
    }

    @Override
    public void doSub3Skill3() { //Recall Trident

    }

    private void tridentBuilder(String textureName, double textureScale, double textureOffsetX, double textureOffsetY, double textureRotation, double projWidth, double projHeight, double projSpeed, TridentComponent tridentComponent) {
        if(this.takeAttackWeight(10)) {
            entityBuilder()
                    .at(entity.getCenter())
                    .viewWithBBox(new Rectangle(projWidth, projHeight, Color.HOTPINK))
                    .zIndex(-1)
                    .with(new ProjectileComponent(getInput().getVectorToMouse(entity.getCenter()), projSpeed))
                    .with(new OffscreenCleanComponent())
                    .with(tridentComponent)
                    .buildAndAttach();
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
