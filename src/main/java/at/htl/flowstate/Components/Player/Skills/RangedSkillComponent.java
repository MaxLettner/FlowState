package at.htl.flowstate.Components.Player.Skills;

import at.htl.flowstate.Components.Player.PlayerStatsComponent;
import at.htl.flowstate.Components.Player.TridentComponent;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class RangedSkillComponent extends SkillComponent{
    Entity currentlyThrownTrident = null;

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
        tridentBuilder(300, new TridentComponent());
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

    }

    @Override
    public void doSub3Skill3() { //Recall Trident

    }

    private void tridentBuilder(double speed, TridentComponent tridentComponent) {
        if(entity.getComponent(PlayerStatsComponent.class).takeAttackWeight(10)) {
            currentlyThrownTrident = entityBuilder()
                    .at(entity.getCenter())
                    .viewWithBBox(new Rectangle(50, 20, Color.HOTPINK))
                    .zIndex(-1)
                    .with(new ProjectileComponent(getInput().getVectorToMouse(entity.getCenter()), speed))
                    .with(new OffscreenCleanComponent())
                    .with(tridentComponent)
                    .buildAndAttach();
        }
    }
}
