package at.htl.flowstate.Components.Skills;

import at.htl.flowstate.Components.PlayerProjectileComponent;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class MagicSkillComponent extends SkillComponent{
    @Override
    public void doDefault() { //magic projectile
        entityBuilder()
                .at(entity.getCenter())
                .viewWithBBox(new Rectangle(20, 20, Color.DARKBLUE))
                .with(new ProjectileComponent(getInput().getVectorToMouse(entity.getCenter()), 200))
                .with(new PlayerProjectileComponent(20))
                .with(new OffscreenCleanComponent())
                .zIndex(-1)
                .buildAndAttach();
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

    public void projectileBuilder() {

    }
}
