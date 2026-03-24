package at.htl.flowstate.Components.Skills;


import at.htl.flowstate.Components.SwingComponent;
import at.htl.flowstate.Components.WeaponDamageComponent;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class MeeleSkillComponent extends SkillComponent {
    public MeeleSkillComponent() {}

    //-----Default Meele Skill-----
    @Override
    public void doDefault () {

        entityBuilder()
                .viewWithBBox(new Rectangle(10, 60, Color.GRAY))
                .with(new WeaponDamageComponent(5))
                .with(new SwingComponent(entity, 1))
                .zIndex(-1)
                .buildAndAttach();
    }

    //-----Skills of the Subtrees-----
    @Override
    public void doSub1() {

    }

    @Override
    public void doSub2() {

    }

    @Override
    public void doSub3() {

    }

    //-----Skills of the Swords Tree-----
    @Override
    public void doSub1Skill1() {

    }

    @Override
    public void doSub1Skill2() {

    }

    @Override
    public void doSub1Skill3() {

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
}
